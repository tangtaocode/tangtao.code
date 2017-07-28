package net.risesoft.approve.repository.jpa;
/*package net.risesoft.fileflow.repository.jpa;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sun.xml.bind.v2.model.core.ID;

public interface SBApproveMaterialRepository<T> extends JpaRepository<T, ID> {

	
	 *//**
	 * 根据UserID和Status 查询网上办件列表
	 * @param UserID
	 * @param Status
	 * @return
	 *//*
	@Query("SELECT SP.usertype FROM SBApproveInstance SB, SpApprovewebUser SP WHERE SB.userid = SP.guid=?1")
	public List<SBApproveInstance>  findByUserID(String userID);
}
*/