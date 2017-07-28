package net.risesoft.approve.repository.jpa;
import net.risesoft.approve.entity.jpa.ZkUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ZkAttendanceRepository extends JpaRepository<ZkUserInfo, String>,JpaSpecificationExecutor<ZkUserInfo> {
	
}
