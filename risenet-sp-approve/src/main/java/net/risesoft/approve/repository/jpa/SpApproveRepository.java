package net.risesoft.approve.repository.jpa;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface SpApproveRepository extends CrudRepository<OfficeSpiDeclareinfo, String>,JpaRepository<OfficeSpiDeclareinfo, String>,
JpaSpecificationExecutor<OfficeSpiDeclareinfo>{

	public OfficeSpiDeclareinfo findByGuid(String guid);
}
