package net.risesoft.approve.repository.jpa.certificate;

import net.risesoft.approve.entity.jpa.certificate.XzxkjdsBean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface XzxkjdsRepository extends JpaRepository<XzxkjdsBean, String>,JpaSpecificationExecutor<XzxkjdsBean>
{

}
