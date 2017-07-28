/**
 * @Project Name:risenet-sp-approve
 * @File Name: OrderServiceImpl.java
 * @Package Name: net.risesoft.approve.service.impl
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年12月24日 下午4:19:52
 */
package net.risesoft.approve.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.jpa.PdfFile;
import net.risesoft.approve.entity.jpa.SpmWordFile;
import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;
import net.risesoft.approve.entity.jpa.gdbs.SpjgData;
import net.risesoft.approve.repository.jpa.PdfFileRepository;
import net.risesoft.approve.repository.jpa.gdbs.ShenPiJieGuoRespository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.PdfFileService;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.util.Md5EncoderUtil;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.utilx.FTSuperviseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.misc.BASE64Decoder;

import com.szca.caau.webservice.SZCASafeService;
import com.szca.caau.webservice.SZCASafeServiceService;



@Service("pdfFileService")
@Transactional
public class PdfFileServiceImpl implements PdfFileService {

	@Autowired
	private PdfFileRepository pdfFileRepository;
	
	@Resource
	private ShenPiJieGuoRespository shenPiJieGuoRespository;
	
	@Resource(name="officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Resource(name = "routerJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void savePdfFile(byte[] content,String sPinstanceId, String filePath,String fileName,String type,String docid,String uuid) {
		Person person = ThreadLocalHolder.getPerson();
		try {
			if(RisesoftCommonUtil.print_banjieDan.equals(type)){
				String sblsh = officeSpiDeclareinfoService.findByGuid(sPinstanceId).getDeclaresn();
				ShouliProcess shouli = FTSuperviseUtil.findBySblsh(sblsh);
				SpjgData shenpijieguo = new SpjgData();
				shenpijieguo.setSeq(uuid);
				shenpijieguo.setSblshShort(sblsh);
				shenpijieguo.setSblsh(shouli.getSblsh());
				shenpijieguo.setAttachName(fileName);
				shenpijieguo.setAttachId(docid);
				shenpijieguo.setAttachSign(Md5EncoderUtil.generateMd5(filePath));
				shenpijieguo.setAttachType(type);
				shenpijieguo.setSaveType("3");
				shenpijieguo.setAttachBody(content);
				shenpijieguo.setAttachPath(RisesoftCommonUtil.diskShenpijieguoFolder+"/"+fileName);
				shenpijieguo.setFxzqhdm("440300");
				shenpijieguo.setVersion("1");
				shenpijieguo.setDzzjgdm("440300");
				shenpijieguo.setInserttime(new Date());
				shenPiJieGuoRespository.save(shenpijieguo);
			}
			
			List<PdfFile> pdfFileList = pdfFileRepository. findByInstanceGuidAndFileType(sPinstanceId, type);
			PdfFile pdfFile = new PdfFile(); 
			if(pdfFileList!=null&&pdfFileList.size()>0){ //更新
				pdfFile = pdfFileList.get(0);
				pdfFile.setContent(content);
				pdfFile.setFilePath(filePath);
				pdfFile.setFileName(fileName);
				pdfFile.setFileType(type);
				pdfFile.setUserId(person.getID());
				pdfFile.setUserName(person.getName());
				pdfFile.setModifyDate(new Date());
				pdfFileRepository.save(pdfFile);
				return;
			}
			pdfFile.setGuid(GuidUtil.genGuid()); //新增
			pdfFile.setInstanceGuid(sPinstanceId);
			pdfFile.setContent(content);
			pdfFile.setFilePath(filePath);
			pdfFile.setFileName(fileName);
			pdfFile.setFileType(type);
			pdfFile.setUserId(person.getID());
			pdfFile.setUserName(person.getName());
			pdfFile.setModifyDate(new Date());
			pdfFile.setCreateDate(new Date());
			pdfFileRepository.save(pdfFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("restriction")
	@Override
	public boolean pdfFileSign(String CertTxt, String SignTxt) {
		boolean pdfFileSign = false;
		try{
			BASE64Decoder decoder = new BASE64Decoder();
			SZCASafeServiceService ss = new SZCASafeServiceService();
			SZCASafeService service = ss.getSZCASafeServicePort();
			//验证签名（用公钥验证）
			String result = service.szcaWSSignatureValidatePkcs7String(SignTxt);
			String decoderResult = new String(decoder.decodeBuffer(result));
			if(decoderResult.equals("1")) {
				//验证证书 .1 证书有效, -1 证书无效，不是所信任的根 -2 证书无效，超过有效期 -3 证书无效，已加入黑名单
				String cert64 = service.szcaWSCertificateValidateString(CertTxt);
				if(cert64.equals("E405")) {
					System.out.print("#######################根证书或吊销列表为空#######################");
					return pdfFileSign;
				}else if(cert64.equals("E404")){
					System.out.print("#######################参数为空#######################");
					return pdfFileSign;
				}else if(cert64.equals("E403")){
					System.out.print("#######################验签失败#######################");
					return pdfFileSign;
				}else{
					String cert = new String(decoder.decodeBuffer(cert64)); //转码
					String[] arrResult=cert.split(",");
					if(arrResult[0].equals("1")){
						System.out.println("#######################证书有效，签名成功#######################");
						pdfFileSign = true;//签名成功
					} else if(arrResult[0].equals("-1")) {
						System.out.print("#######################证书无效，不是所信任的根#######################");
						return pdfFileSign;
					} else if(arrResult[0].equals("-2")) {
						System.out.println("#######################证书无效，超过有效期#######################");
						return pdfFileSign;
					} else if(arrResult[0].equals("-3")) {
						System.out.println("#######################证书无效，已加入黑名单#######################");
						return pdfFileSign;
					}
				}
			} else {
				System.out.println("#######################验证失败，非法的签名#######################");
				return pdfFileSign;
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("#######################异常："+e.getMessage());
			return pdfFileSign;
		}
		return pdfFileSign;
	}

	@Override
	public void savePdfFileSign(String sPinstanceId, String certTxt,String signTxt) {
		PdfFile pdfFile= new PdfFile();//this.findByInstanceGuid(sPinstanceId);
		if(pdfFile!=null){
			pdfFile.setCertTxt(certTxt);
			pdfFile.setSignTxt(signTxt);
			pdfFileRepository.save(pdfFile);
		}
	}

	@Override
	public boolean deletePDF(String processSerialNumber, String sPinstanceId,String fileType) {
		List<PdfFile> list = pdfFileRepository.findByInstanceGuidAndFileType(sPinstanceId,fileType);
		if(list!=null&&list.size()>0){
			pdfFileRepository.delete(list.get(0));
			return true;
		}
		return false;
	}

	@Override
	public int isWordOrPDF(String instanceId,String printType) {
		// TODO Auto-generated method stub
		String sql="select count(1) from ff_pdffile t where t.instanceguid=? and t.filetype=?";
		try {
			return jdbcTemplate.queryForObject(sql, Integer.class, instanceId,printType);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getWord(String instanceId, String printType) {
		// TODO Auto-generated method stub
		String path = "";
		String type="";
		if(RisesoftCommonUtil.print_banjieDan.equals(printType)){
			type="1";
		}else if(RisesoftCommonUtil.print_shouliDan.equals(printType)){
			type="2";
		}
		try{
			String sql = "select * from spm_word_file t where and t.guid=? and t.step=? ";
			List<SpmWordFile> list = jdbcTemplate.queryForList(sql,SpmWordFile.class, new Object[]{instanceId,printType});
			if(list.size()>0){
				for(SpmWordFile word:list){
					if("1".equals(word.getType())){
						return word.getFilepath();
					}else{
						path = word.getFilepath();
					}
				}
			}else{
				sql = "select filepath from SPM_TEMPLATE t where t.templatetype=?";
				path = jdbcTemplate.queryForObject(sql, String.class, new Object[]{type});
			}
		}catch (DataAccessException e) {
			String sql = "select filepath from SPM_TEMPLATE t where t.templatetype=?";
			try {
				path = jdbcTemplate.queryForObject(sql, String.class, new Object[]{type});
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return path;
	}
	
	@Override
	public Map<String,Object> getZZTemplate(String guid) {
		Map<String,Object> result=new HashMap<String,Object>();
		String sql = "select filepath filepath,execute_sql exesql from SPM_TEMPLATE t where t.approveitemguid=? and t.templatetype=3";
		try {
			result = jdbcTemplate.queryForMap(sql, guid);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public Map<String,Object> findMarks(String sql,String sblsh){
		System.out.println(sql);
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			result = jdbcTemplate.queryForMap(sql, sblsh);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}

