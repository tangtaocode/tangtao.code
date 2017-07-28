package net.risesoft.approve.service;

import java.util.Map;

import net.risesoft.approve.entity.jpa.PdfFile;



public interface PdfFileService {

	/**
	 * 保存pdf文件信息（受理单，办结单）
	 * @param sPinstanceId
	 * @param filePath
	 * @param fileName
	 */
	void savePdfFile(byte[] content,String sPinstanceId, String filePath, String fileName,String fileType,String docid,String uuid);


	/**
	 * pdf文件签名
	 * @param CertTxt
	 * @param SignTxt
	 * @param DataTxt
	 * @return
	 */
	boolean pdfFileSign(String CertTxt,String SignTxt);

	/**
	 * 保存文件签名信息
	 * @param sPinstanceId
	 * @param certTxt
	 * @param signTxt
	 */
	void savePdfFileSign(String sPinstanceId, String certTxt, String signTxt);

	/**
	 * 删除pdf
	 * @param processSerialNumber
	 * @param sPinstanceId
	 * @param printType
	 * @return
	 */
	boolean deletePDF(String processSerialNumber, String sPinstanceId,String fileType);

	/**
	 * 判断是否存在pdf文件
	 * @param printType
	 * @return
	 */
	public int isWordOrPDF(String instanceId,String printType);
	
	
	/**
	 * 查询word模板
	 */
	public String getWord(String instanceId,String printType);
	
	/**
	 * 根据事项id查询证照模板
	 * @param guid
	 * @return
	 */
	public Map<String, Object> getZZTemplate(String guid);
	
	/**
	 * 查询证照模板中书签对应的值
	 * @param sblsh
	 * @return
	 */
	public Map<String,Object> findMarks(String sql,String sblsh);
}
