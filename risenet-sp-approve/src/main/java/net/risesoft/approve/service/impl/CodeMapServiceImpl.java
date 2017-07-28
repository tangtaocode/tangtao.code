package net.risesoft.approve.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.service.CodeMapService;
import net.risesoft.utilx.database.Conn;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
/**
 * 受理单编号自动生成规则： 为了考虑从触摸屏输入方便，以及今后利用小键盘进行满意度的评议，受理单编号应该全部采用数字进行编号，规则如下：
 * 受理单编号总长度为12位，采用定长编码， 1、 1-2位，委办局唯一编码，对应SPM_Bureau
 * 表BureauSN字段。从可读性的角度来说，应该保留委办局的唯一编码。 2、
 * 3-8位，年月日标识，如：050713，为了使受理单编号尽可能的短，年为2位，从本系统的使用寿命来说，完全没有千年虫问题。采用年月日标识是为了提高受理单编号的可读性，方便进行查询，所以没有采用8位时间戳。
 * 3、
 * 9-12位，当天的事件流水号，从0001开始顺序生成，作废不补，允许中间跳号。按照每个委办局走流水，不是大流水，否则委办局唯一编码的意义就不是很大了。
 */
/**
 * update by cbn
 * 2015年11月13日
 * 修改内容：前两位委办局唯一编码扩充为4位
 */
@Service("codeMapUtil")
public class CodeMapServiceImpl implements CodeMapService{
	private static final Logger log = LogManager.getLogger(CodeMapServiceImpl.class);
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	} 
	
	public String getDeclaresnByBureauGuid(String bureauGuid) {
		String bureauStr = "";
		StringBuffer result = new StringBuffer();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String datestr = sdf.format(date).substring(2);
		String sql = "select to_char(t.bureausn) code,t.bureauname value from spm_bureau t where trim(t.bureauguid)=?";
		try{
			Map<String, Object> code = jdbcTemplate.queryForMap(sql, new String[] { bureauGuid});
			int bureauSn = 0;
			if (code != null)
				bureauSn = Integer.parseInt(code.get("code").toString());
			if (bureauSn < 10) {
				bureauStr = "000" + bureauSn;
			} else if(bureauSn < 100){
				bureauStr = "00" + bureauSn;
			}else if(bureauSn <1000) {
				bureauStr = "0" + bureauSn;	
			}else {
				bureauStr = Integer.toString(bureauSn);
			}
			result = new StringBuffer(bureauStr);
			result.append(datestr);
			String serialStr = "";
			int serialnumber = 1;
			String state=  "";
			sql = "select to_char(SERIALNUMBER) code,'1' value from SPI_BUREAUSERIALNUMBERS where BUREAUSN=? and CURRENTDATE=?";
			List<Map<String,Object>> list = (List<Map<String,Object>>)jdbcTemplate.queryForList(sql, new String[] { bureauSn + "",datestr});
			code = null;
			if(list.size()>0){
				code = list.get(0);
			}
			if (code != null) {
				state = "update";
				serialnumber = Integer.parseInt(code.get("code").toString()) + 1;
				sql = "update SPI_BUREAUSERIALNUMBERS set SERIALNUMBER=? where BUREAUSN=? and CURRENTDATE=?";
				jdbcTemplate.update(sql,new Object[]{serialnumber,bureauSn,datestr});
			} else{
				state = "insert";
				jdbcTemplate.update("insert into SPI_BUREAUSERIALNUMBERS (SERIALNUMBER,BUREAUSN,CURRENTDATE) values(?,?,?)",new Object[]{serialnumber,bureauSn,datestr});
			}
			/*Connection conn = null;
			try {
				//同步数据到内网
				conn = jdbcTemplate.getDataSource().getConnection();
				//CallableStatement cs= conn.prepareCall("{call move_SPI_BUREAUSERIALNUMBERS(?,?,?,?)}");
				/*cs.registerOutParameter(1, Types.VARCHAR);
				cs.setInt(1,bureauSn);
				cs.setString(2, datestr);
				cs.setInt(3, serialnumber);
				cs.setString(4, state);
				cs.execute();
			} catch(Exception e)  {
				e.printStackTrace();
			}finally{
				Conn.closeConnection(conn, null, null, null);
			}*/
			// 此处已经修改：wqb
			if (serialnumber < 10) {
				serialStr = "000" + serialnumber;
			} else if (serialnumber >= 10 && serialnumber < 100) {
				serialStr = "00" + serialnumber;
			} else if (serialnumber >= 100 && serialnumber < 1000) {
				serialStr = "0" + serialnumber;
			} else {
				serialStr = "" + serialnumber;
			}
			result.append(serialStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new String(result);
	}

	@Override
	public List<Map<String, Object>> getDataSourceList(String type) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select t.code value,t.value key from xzql_codemap t where t.type=?";
		list = jdbcTemplate.queryForList(sql,new Object[]{type});
		return list;
	}

	

	

	
	
}
