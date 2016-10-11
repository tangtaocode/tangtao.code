package net.risesoft.dao;


import net.risesoft.model.RiseEmployee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IEmployeeDao extends JpaRepository<RiseEmployee, String>, JpaSpecificationExecutor<RiseEmployee> {
	
}
