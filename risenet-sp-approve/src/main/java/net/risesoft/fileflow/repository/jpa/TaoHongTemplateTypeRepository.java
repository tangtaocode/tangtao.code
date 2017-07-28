package net.risesoft.fileflow.repository.jpa;

import java.util.List;

import net.risesoft.fileflow.entity.jpa.TaoHongTemplateType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TaoHongTemplateTypeRepository extends JpaRepository<TaoHongTemplateType, String>,JpaSpecificationExecutor<TaoHongTemplateType> {
	@Query("from TaoHongTemplateType t order by t.tabIndex")
	List<TaoHongTemplateType> findByTenantId();
	
	@Query("select max(tabIndex) from TaoHongTemplateType")
	Integer getMaxTabIndex();
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("update TaoHongTemplateType t set t.tabIndex=?1 where t.id=?2")
	void update4Order(Integer tabIndex, String id);
}
