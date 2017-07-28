package net.risesoft.approve.repository.jpa;


import net.risesoft.approve.entity.jpa.SpmApproveitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface SpmBureauRepository extends CrudRepository<SpmApproveitem, String>,JpaRepository<SpmApproveitem, String>,
JpaSpecificationExecutor<SpmApproveitem>{
	
}
