package net.risesoft.approve.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sun.misc.BASE64Decoder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.risesoft.approve.entity.jpa.SPMwssbcl;
import net.risesoft.approve.repository.jpa.SPMwssbclRepository;
import net.risesoft.approve.service.MaterialListService;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SPMwssbclService;
import net.risesoft.approve.service.SharestuffService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.utilx.StringX;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.org.bjca.client.exceptions.ApplicationNotFoundException;
import cn.org.bjca.client.exceptions.InitException;
import cn.org.bjca.client.exceptions.ParameterInvalidException;
import cn.org.bjca.client.exceptions.ParameterTooLongException;
import cn.org.bjca.client.exceptions.SVSConnectException;
import cn.org.bjca.client.exceptions.UnkownException;
import cn.org.bjca.security.szg.SecurityEngineDeal;



@Service(value="spMwssbclService")
@Transactional(readOnly=true)
public class SPMwssbclServiceImpl implements SPMwssbclService{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SPMwssbclRepository spMwssbclRepository;
	
	@Autowired
	private MaterialListService materialListService;
	
	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Autowired
	private SharestuffService sharestuffService;
	
	@javax.annotation.Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	@Override
	public List<SPMwssbcl> findByWorkflowinstanceguidAndDeclareannexguid(String workflowinstanceguid,String declareannexguid) {
		return spMwssbclRepository.findByWorkflowinstanceguidAndDeclareannexguid(workflowinstanceguid,declareannexguid);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void download(String id, HttpServletResponse response,HttpServletRequest request) throws Exception{
		
			String filename="";
			File file = null;
			SPMwssbcl spMwssbcl = this.findOne(id);
			String rootpath = spMwssbcl.getRootpath()+spMwssbcl.getFilename();
			String linuxpath = spMwssbcl.getLinuxpath()+spMwssbcl.getFilename();
			if(rootpath!=null){
				file = new File(rootpath);
			}else{
				file = new File(linuxpath);
			}
			InputStream input = new FileInputStream(file);  
		    byte[] data = IOUtils.toByteArray(input); 
		    if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
		    	filename = URLEncoder.encode(spMwssbcl.getFilename(), "UTF-8");//IE浏览器
		    }else{//request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0
		    	filename = new String(spMwssbcl.getFilename().getBytes("UTF-8"), "ISO8859-1");//火狐浏览器
		    }
		    response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=\""+filename+"\"" );
			response.setHeader("Content-Length", String.valueOf(file.length()));
			IOUtils.write(data, response.getOutputStream());
			response.flushBuffer();
		
	}

	@Override
	public SPMwssbcl findOne(String id) {
		return spMwssbclRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void upload(MultipartFile  attachmentFile,String declareannexguid, String processInstanceId) {
		if (attachmentFile != null) {
			Person person = (Person) ThreadLocalHolder.getPerson();
			String userId = person.getID();
			SPMwssbcl spMwssbcl = new SPMwssbcl();
			spMwssbcl.setGuid("{"+UUID.randomUUID().toString()+"}");
			spMwssbcl.setFilename(attachmentFile.getOriginalFilename());
			spMwssbcl.setWorkflowinstanceguid(processInstanceId);
			spMwssbcl.setApproveitemgguid(officeSpiDeclareinfoService.findByGuid(processInstanceId).getApproveitemguid());
			spMwssbcl.setDeclareannexguid(declareannexguid);
			spMwssbcl.setRootpath("E:/risefile/gmoa/risefile/wssb/"+processInstanceId+"/"+declareannexguid+"/");
			spMwssbcl.setUpdatetime(new Date());
			spMwssbcl.setUpuser(userId);
			spMwssbcl.setType("0");
			String path = "E:/risefile/gmoa/risefile/wssb/"+processInstanceId+"/"+declareannexguid+"/"+attachmentFile.getOriginalFilename();
			File targetFile = new File(path); 
			if (!targetFile.exists()) { 
				 targetFile.mkdirs();        
			}
			try {
				attachmentFile.transferTo(targetFile);
			}catch (IOException e) {
				e.printStackTrace();
			}
			spMwssbclRepository.save(spMwssbcl);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(String id) {
		spMwssbclRepository.delete(id);
	}

	@Override
	public List<Map<String, Object>> findSpMwssbclList(String sblsh,String stuffSeq,String version) {
		if(StringX.isBlank(version)){
			version="1";
		}
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<Object> params=new ArrayList<Object>();
		//String sql="select * from spm_wssbcl t where t.workflowinstance_guid=? and t.declareannexguid=?";
		//根据业务流水号，材料编号，数据版本号查询对应材料
		String sql="select * from EX_GDBS_SBCL t where t.sblsh_short=? and t.STUFF_SEQ=? and t.version=?";
		params.add(sblsh);
		params.add(stuffSeq);
		params.add(version);
		try {
			listmap = jdbcTemplate.queryForList(sql,params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listmap;
	}

	@Override
	public List<Map<String, Object>> getSpMwssbclList(String sblsh,String stuffSeq,String version,HttpServletRequest request) {
		String stuffSeq1=request.getParameter("stuffSeq");
		System.out.println(stuffSeq1);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> pageList = this.findSpMwssbclList(sblsh,stuffSeq,version);
		for (Map<String, Object> Map : pageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			/*map.put("id", Map.get("GUID"));
			map.put("fileName", Map.get("FILENAME"));
			map.put("uploadTime", new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(Map.get("UPDATETIME")));*/
			
			map.put("id", Map.get("seq"));
			map.put("stuffSeq", Map.get("STUFF_SEQ"));
			map.put("fileName", Map.get("ATTACH_NAME"));
			map.put("uploadTime", new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(Map.get("INSERTTIME")));
			map.put("filePath", Map.get("ATTACH_PATH"));
			
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 北京CA验签
	 * @param usercert
	 * @param signfiledata
	 * @param path
	 * @return
	 * @throws SVSConnectException
	 * @throws ParameterTooLongException
	 * @throws ParameterInvalidException
	 * @throws UnkownException
	 * @throws ApplicationNotFoundException
	 * @throws InitException
	 */
	public boolean signWssbcl(String usercert,String signfiledata,String path,HttpServletRequest request) throws SVSConnectException, ParameterTooLongException, ParameterInvalidException, UnkownException, ApplicationNotFoundException, InitException{
		File file = new File(path);
		if(!file.exists()){//文件不存在，直接返回false
			return false;
		}
		SecurityEngineDeal secEngineDeal = null;
		String rootPath = request.getSession().getServletContext().getRealPath("/WEB-INF/BJCAROOT");
		SecurityEngineDeal.SZG_setRootPath(rootPath);
		secEngineDeal = SecurityEngineDeal.SZG_getInstance("SVSDefault");
		
		/*usercert = "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";
		signfiledata = "NcqJsasyUqCz667x+67zYhyCVHbHXzApteioonQr4LbgME/FAPY03+pJXXJ48P9vYIvqrhp9OQzsGnLw9BEZ0CDQd2A5nh1MLuLREOX594p9XMGNgM5rtuuXpgkKGMIvr1YZIc4fMp68Ng7kFSTXWrNLYAluahddWZsfTHu85BY=";
		path = "E:\\risefile\\{AC100605-0000-0000-0102-E7B8FFFFFF96}.vpn.txt";*/
		//usercert = "MIIEBTCCA26gAwIBAgIIVvIYWEqDOokwDQYJKoZIhvcNAQEFBQAwezELMAkGA1UEBhMCQ04xEjAQBgNVBAgTCUd1YW5nZG9uZzERMA8GA1UEBxMIU2hlbnpoZW4xJzAlBgNVBAoTHlNoZW5aaGVuIENlcnRpZmljYXRlIEF1dGhvcml0eTENMAsGA1UECxMEc3pjYTENMAsGA1UEAxMEU1pDQTAeFw0xNTExMjcwNzM2MTRaFw0xNzExMjYwNzM2MTRaMG4xCzAJBgNVBAYTAkNOMQ8wDQYDVQQIHgZef04cdwExDzANBgNVBAceBm3xVzNeAjEPMA0GA1UECh4GjSJlP1xAMRswGQYDVQQLExI0MzAxMjQxOTg1MDMyODU4NzMxDzANBgNVBAMeBl0UXA9TTjCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA2i2g3Lmk/qVtFSA30UVcITXGlVW3OUMMx0o4c5bxop9nwA4LDRK/cbRcnRVeiXsAwlAQvADp5vdNgrwCygxWfeHvEJW4yx2pgahz+N9fQt2QHGJyIMZ0j4wZEavy33SMmVmZ8iYUZ3BkrUSKWwx3pNFdIc+GjCmd5DKYhXVN8jkCAwEAAaOCAZ0wggGZMAkGA1UdEwQCMAAwLQYGYIEchu8kBCMMITFANzAyNVNGMU5ETXdNVEkwTVRrNE5UQXpNamcxT0RjejBkBggrBgEFBQcBAQRYMFYwLQYIKwYBBQUHMAKGIWh0dHA6Ly9pc3N1ZS5zemNhLm5ldC9jYWlzc3VlLmh0bTAlBggrBgEFBQcwAYYZaHR0cDovL29jc3Auc3pjYS5uZXQ6ODA4NTALBgNVHQ8EBAMCBsAwHQYDVR0OBBYEFDgZW+B+i9LeshFid2nQBw78pM14MBoGCCpWCweDzOl7BA4MDGRFNDNVVzlpZFhFPTAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwHwYDVR0jBBgwFoAUegUE169phelgrX+x7cxlQdBMA0YwOwYDVR0gBDQwMjAwBgRVHSAAMCgwJgYIKwYBBQUHAgEWGmh0dHA6Ly8xNzIuMTYuMy4xMC9jcHMuaHRtMDIGA1UdHwQrMCkwJ6AloCOGIWh0dHA6Ly9jcmwuc3pjZXJ0LmNvbS9jcmwxODQyLmNybDANBgkqhkiG9w0BAQUFAAOBgQCE0Lx+gAFURX/173PxPHdYZCJT/GS6hD1yf9Av/a3/Ms0j2ptZAzMt1yC2AC5VnyBZRWLwP0n8G6Llme79RAN9JAArlsPqbBZqR+XvdR4TSklvI6F1SlaSR/y+BwX2LsX/CzYaw0hEbgloBBTqLPdegZFKVo+BVc0I/xhdZRNkEA==";
		//signfiledata = "flTNyAdc78H/xJZnL/6Za1lNzoXcneEijyDmng+86H0F21W0+fl5Geg1HxN39fvL4KtNJKZaf46pmQjtaXFe7y8AGeqrfmWR2HooKYfh6KFgVNVOz20kBdhGbmzW9oKFRaJ/RSiaJcgoAvkiLNqVmFtAIJ4XCweUH4f9g3kLKqU=";
				//"MIIFewYJKoZIhvcNAQcCoIIFbDCCBWgCAQExCzAJBgUrDgMCGgUAMBcGCSqGSIb3DQEHAaAKBAiyu82s0uKho6CCBAkwggQFMIIDbqADAgECAghW8hhYSoM6iTANBgkqhkiG9w0BAQUFADB7MQswCQYDVQQGEwJDTjESMBAGA1UECBMJR3Vhbmdkb25nMREwDwYDVQQHEwhTaGVuemhlbjEnMCUGA1UEChMeU2hlblpoZW4gQ2VydGlmaWNhdGUgQXV0aG9yaXR5MQ0wCwYDVQQLEwRzemNhMQ0wCwYDVQQDEwRTWkNBMB4XDTE1MTEyNzA3MzYxNFoXDTE3MTEyNjA3MzYxNFowbjELMAkGA1UEBhMCQ04xDzANBgNVBAgeBl5/Thx3ATEPMA0GA1UEBx4GbfFXM14CMQ8wDQYDVQQKHgaNImU/XEAxGzAZBgNVBAsTEjQzMDEyNDE5ODUwMzI4NTg3MzEPMA0GA1UEAx4GXRRcD1NOMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDaLaDcuaT+pW0VIDfRRVwhNcaVVbc5QwzHSjhzlvGin2fADgsNEr9xtFydFV6JewDCUBC8AOnm902CvALKDFZ94e8QlbjLHamBqHP4319C3ZAcYnIgxnSPjBkRq/LfdIyZWZnyJhRncGStRIpbDHek0V0hz4aMKZ3kMpiFdU3yOQIDAQABo4IBnTCCAZkwCQYDVR0TBAIwADAtBgZggRyG7yQEIwwhMUA3MDI1U0YxTkRNd01USTBNVGs0TlRBek1qZzFPRGN6MGQGCCsGAQUFBwEBBFgwVjAtBggrBgEFBQcwAoYhaHR0cDovL2lzc3VlLnN6Y2EubmV0L2NhaXNzdWUuaHRtMCUGCCsGAQUFBzABhhlodHRwOi8vb2NzcC5zemNhLm5ldDo4MDg1MAsGA1UdDwQEAwIGwDAdBgNVHQ4EFgQUOBlb4H6L0t6yEWJ3adAHDvykzXgwGgYIKlYLB4PM6XsEDgwMZEU0M1VXOWlkWEU9MB0GA1UdJQQWMBQGCCsGAQUFBwMCBggrBgEFBQcDBDAfBgNVHSMEGDAWgBR6BQTXr2mF6WCtf7HtzGVB0EwDRjA7BgNVHSAENDAyMDAGBFUdIAAwKDAmBggrBgEFBQcCARYaaHR0cDovLzE3Mi4xNi4zLjEwL2Nwcy5odG0wMgYDVR0fBCswKTAnoCWgI4YhaHR0cDovL2NybC5zemNlcnQuY29tL2NybDE4NDIuY3JsMA0GCSqGSIb3DQEBBQUAA4GBAITQvH6AAVRFf/Xvc/E8d1hkIlP8ZLqEPXJ/0C/9rf8yzSPam1kDMy3XILYALlWfIFlFYvA/SfwbouWZ7v1EA30kACuWw+psFmpH5e91HhNKSW8joXVKVpJH/L4HBfYuxf8LNhrDSERuCWgEFOos916BkUpWj4FVzQj/GF1lE2QQMYIBLjCCASoCAQEwgYcwezELMAkGA1UEBhMCQ04xEjAQBgNVBAgTCUd1YW5nZG9uZzERMA8GA1UEBxMIU2hlbnpoZW4xJzAlBgNVBAoTHlNoZW5aaGVuIENlcnRpZmljYXRlIEF1dGhvcml0eTENMAsGA1UECxMEc3pjYTENMAsGA1UEAxMEU1pDQQIIVvIYWEqDOokwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASBgH5UzcgHXO/B/8SWZy/+mWtZTc6F3J3hIo8g5p4PvOh9BdtVtPn5eRnoNR8Td/X7y+CrTSSmWn+OqZkI7WlxXu8vABnqq35lkdh6KCmH4eihYFTVTs9tJAXYRm5s1vaChUWif0UomiXIKAL5IizalZhbQCCeFwsHlB+H/YN5Cyql";
		System.out.println("开始验签");
		long begin = System.currentTimeMillis();
		boolean valid = secEngineDeal.SZG_verifySignedFile(usercert,path,signfiledata);//true表示验签成功
		long end = System.currentTimeMillis();
		System.out.println( "------验签花费时间-----"+((end-begin)/1000 )+"秒");
		System.out.println( "------结果-----"+ valid);
		return valid;
	}

}
