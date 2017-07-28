package net.risesoft.approve.repository.jpa;
import net.risesoft.approve.entity.jpa.KqGeneralLogData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ZklogdataRepository extends JpaRepository<KqGeneralLogData,String>,JpaSpecificationExecutor<KqGeneralLogData>{

}
