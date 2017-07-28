package net.risesoft.tenant.aop;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;

import net.risesoft.common.util.GuidUtil;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.entity.AccessLog;
import net.risesoft.tenant.entity.Tenant;
import net.risesoft.tenant.log.RiseLogMDC;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.tenant.service.TenantService;

public class RiseLogAdvice implements MethodInterceptor {
	private static final Logger logger = LogManager.getLogger(RiseLogAdvice.class);

	@Autowired
	private TenantService tenantService;

	private String serverIp = "";

	public RiseLogAdvice() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			this.serverIp = addr.getHostAddress();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		RiseLog a = method.getAnnotation(RiseLog.class);

		long start = System.nanoTime();
		String errorMessage = "";
		String throwable = "";
		String success = "成功";
		long end = System.nanoTime();
		long elapsedTime = 0L;
		Tenant tenant = null;

		Object ret = null;
		try {
			ret = invocation.proceed();
		} catch (Exception e) {
			success = "出错";
			errorMessage = e.getMessage();

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			//throwable = stringWriter.toString().replace("\r\n", "<br/>");
			throwable = stringWriter.toString();
			throw e;
		} finally {
			end = System.nanoTime();
			elapsedTime = end - start;

			AccessLog log = new AccessLog();
			try {
				log.setLogTime(new Date());
				log.setMethodName(method.getDeclaringClass().getName() + "." + method.getName());
				log.setElapsedTime(String.valueOf(elapsedTime));
				log.setSuccess(success);
				log.setLogMessage(errorMessage);
				log.setThrowable(throwable);
				log.setId(GuidUtil.genGuid());
				//log.setUserId("system");
				//log.setUserName("system");
				log.setServerIp(this.serverIp);

				if (a == null) {
					log.setModularName(method.getDeclaringClass().getName());
					log.setOperateName(method.getName());
					log.setOperateType("查看");
				} else {
					log.setModularName(StringUtils.isBlank(a.modularName()) ? method.getDeclaringClass().getName() : a.modularName());
					log.setOperateName(StringUtils.isBlank(a.operateName()) ? method.getName() : a.operateName());
					log.setOperateType(a.operateType());
				}

				String tenantId = ThreadLocalHolder.getTenantId();
				if (StringUtils.isNotBlank(tenantId)) {
					tenant = tenantService.findOne(ThreadLocalHolder.getTenantId());
					if (tenant != null) {
						log.setTenantId(tenantId);
						log.setTenantName(tenant.getName());
					}
				}

				Person person = ThreadLocalHolder.getPerson();
				if (null != person) {
					log.setUserId(ThreadLocalHolder.getPerson().getID());
					log.setUserName(ThreadLocalHolder.getPerson().getName());
				}

				RiseLogMDC.putLogEntity(log);
				Marker marker = MarkerManager.getMarker("rslog");
				Level level = Level.forName("RSLOG", 110);
				logger.log(level, marker, "MethodInvocation");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}

		return ret;
	}
}
