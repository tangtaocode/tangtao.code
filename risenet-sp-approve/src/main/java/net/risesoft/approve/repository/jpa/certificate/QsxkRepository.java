package net.risesoft.approve.repository.jpa.certificate;



import net.risesoft.approve.entity.jpa.certificate.QsxkBean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QsxkRepository extends JpaRepository<QsxkBean, String>,JpaSpecificationExecutor<QsxkBean>{

}
