package net.risesoft.approve.repository.jpa.certificate;


import net.risesoft.approve.entity.jpa.certificate.CspsxuBean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CspsxuRepository extends JpaRepository<CspsxuBean, String>,JpaSpecificationExecutor<CspsxuBean>{

}
