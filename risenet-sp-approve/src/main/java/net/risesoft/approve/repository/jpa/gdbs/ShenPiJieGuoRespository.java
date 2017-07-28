package net.risesoft.approve.repository.jpa.gdbs;

import net.risesoft.approve.entity.jpa.gdbs.SpjgData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ShenPiJieGuoRespository extends CrudRepository<SpjgData, String>,JpaRepository<SpjgData, String>,JpaSpecificationExecutor<SpjgData>{

}
