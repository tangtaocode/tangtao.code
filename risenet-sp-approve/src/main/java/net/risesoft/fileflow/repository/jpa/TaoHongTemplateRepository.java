package net.risesoft.fileflow.repository.jpa;

import java.util.List;

import net.risesoft.fileflow.entity.jpa.TaoHongTemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TaoHongTemplateRepository extends JpaRepository<TaoHongTemplate, String>, JpaSpecificationExecutor<TaoHongTemplate> {
	@Query("from TaoHongTemplate t order by t.tabIndex asc")
	public List<TaoHongTemplate> findByTenantId();

	@Query("from TaoHongTemplate t where t.bureau_guid=?1 order by t.tabIndex asc")
	public List<TaoHongTemplate> findByBureau_guid(String bureau_guid);
	
	/**
	 * 获取最大的tabIndex
	 * 
	 * @return
	 */
	@Query("select max(tabIndex) from TaoHongTemplate")
	Integer getMaxTabIndex();
}
