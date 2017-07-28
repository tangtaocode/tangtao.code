package net.risesoft.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import net.risesoft.api.attention.AttentionManager;
import net.risesoft.api.attention.AttnCommentManager;
import net.risesoft.api.attention.impl.AttentionManagerImpl;
import net.risesoft.api.attention.impl.AttnCommentManagerImpl;
import net.risesoft.api.cms.ChannelManager;
import net.risesoft.api.cms.impl.ChannelManagerImpl;
import net.risesoft.api.sendMsg.SendMsgManager;
import net.risesoft.api.sendMsg.impl.SendMsgManagerImpl;
import net.risesoft.api.sendSms.SendSmsManager;
import net.risesoft.api.sendSms.impl.SendSmsManagerImpl;
import net.risesoft.api.syncTableSchema.SyncTableSchemaManager;
import net.risesoft.api.syncTableSchema.ipml.SyncTableSchemaManagerImpl;
import net.risesoft.api.todo.TodoTaskManager;
import net.risesoft.api.todo.impl.TodoTaskManagerImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 常量类
 * @author shenqiang
 *
 */
public class RisesoftCommonUtil {
	public static final String charset = "UTF-8";

	public static boolean isMultitenant = false;

	public static boolean todoSwitch = false;

	public static Integer formEngineType = 1;

	//打印表单类型
	public static final String print_banjieDan="banJieDan";
	public static final String print_shouliDan="shouLiDan";
	public static final String print_buQiBuZhengDan="buQiBuZhengDan";
	public static final String print_zhengZhao="zhengZhao";
	
	public static String baseURL = "";
	public static String adminBaseURL = "";
	public static String portalBaseURL = "";
	public static String desktopBaseURL = "";
	public static String bpmBaseURL = "";
	public static String cmsBaseURL = "";
	public static String todoBaseURL = "";
	public static String msgpushURL = "";
	public static String todoTaskURLPrefix = "";
	public static String systemCNName4Risebpm = "";
	public static String casServerUrlPrefix = "";
	public static String serviceURL="";
	public static String printURL="";
	public static String templateURL="";
	public static String print_word="";
	public static String print_pdf="";
	public static String diskURL="";
	public static String diskShenpijieguoFolder="";
	
	
	public static ObjectMapper objectMapper = new ObjectMapper();

	static Properties properties = new Properties();
	static {
		InputStream inputStream = RisesoftCommonUtil.class.getClassLoader().getResourceAsStream("properties/application.properties");
		try {
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("orgBaseURL cannt be empty");
			System.exit(0);
		}
		isMultitenant = Boolean.valueOf(properties.getProperty("isMultitenant"));
		todoSwitch = Boolean.valueOf(properties.getProperty("risebpm7.todoSwitch"));
		formEngineType = Integer.valueOf(properties.getProperty("formEngineType"));
		msgpushURL = properties.getProperty("msgpushURL");
		casServerUrlPrefix = properties.getProperty("casServerUrlPrefix");
		todoTaskURLPrefix = properties.getProperty("todoTaskURLPrefix")==null?"":properties.getProperty("todoTaskURLPrefix");
		systemCNName4Risebpm = properties.getProperty("risebpm7.systemCNName");
		baseURL = getProperty(properties, "orgBaseURL");
		desktopBaseURL = getProperty(properties, "desktopBaseURL");
		adminBaseURL = getProperty(properties, "adminBaseURL");
		portalBaseURL = getProperty(properties, "portalBaseURL");
		bpmBaseURL = getProperty(properties, "bpmBaseURL");
		cmsBaseURL = getProperty(properties, "cmsBaseURL");
		todoBaseURL = getProperty(properties, "todoBaseURL");
		serviceURL = properties.getProperty("serviceURL");
		printURL = properties.getProperty("printURL");
		templateURL = properties.getProperty("templateURL");
		print_word = properties.getProperty("print_word");
		print_pdf = properties.getProperty("print_pdf");
		diskURL = properties.getProperty("diskURL");
		diskShenpijieguoFolder = properties.getProperty("diskShenpijieguoFolder");
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	public static String getProperty(Properties props, String prop) {
		String url = props.getProperty(prop);
		if (url == null) {
			url = "";
		}

		if (url.endsWith("/")) {
			url += "services/rest";
		} else {
			url += "/services/rest";
		}

		return url;
	}

	public static TodoTaskManager getTodoManager() {
		return TodoTaskManagerImpl.getInstance();
	}

	public static AttentionManager getAttentionManager() {
		return AttentionManagerImpl.getInstance();
	}

	public static AttnCommentManager getAttnCommentManager() {
		return AttnCommentManagerImpl.getInstance();
	}

	public static ChannelManager getChannelManager() {
		return ChannelManagerImpl.getInstance();
	}

	public static SyncTableSchemaManager getSyncTableSchemaManager() {
		return SyncTableSchemaManagerImpl.getInstance();
	}

	public static SendMsgManager getSendMsgManager() {
		return SendMsgManagerImpl.getInstance();
	}

	public static SendSmsManager getSendSmsManager() {
		return SendSmsManagerImpl.getInstance();
	}
}
