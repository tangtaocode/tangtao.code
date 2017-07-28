package net.risesoft.approve.repository.jpa;

import java.util.List;

import net.risesoft.approve.entity.jpa.SpmTemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpmTemplateRepository extends CrudRepository<SpmTemplate, String>,JpaRepository<SpmTemplate, String>,
JpaSpecificationExecutor<SpmTemplate>{
	//根据事项id获取一个对象
	@Query("from SpmTemplate t where t.templateGuid=?1")
	public SpmTemplate findByguid(String guid);
	
	//根据事项id获取一个对象
	@Query("from SpmTemplate t where t.approveItemGuid=?1")
	public List<SpmTemplate> findByapproveitemGuid(String approveitemguid);
	
}
