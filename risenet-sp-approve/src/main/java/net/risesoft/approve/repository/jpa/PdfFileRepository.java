package net.risesoft.approve.repository.jpa;


import java.util.List;

import net.risesoft.approve.entity.jpa.PdfFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PdfFileRepository extends JpaRepository<PdfFile, String>,JpaSpecificationExecutor<PdfFile> {

	/**
	 * 查找文件信息
	 * @param sPinstanceId
	 * @param fileType
	 * @return
	 */
	@Query("from PdfFile t where t.instanceGuid=?1 and t.fileType=?2")
	public List<PdfFile> findByInstanceGuidAndFileType(String sPinstanceId,String fileType);
}
