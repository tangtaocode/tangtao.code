package net.risesoft.approve.repository.jpa;


import net.risesoft.approve.entity.jpa.Score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
//持久层操作办结表
public interface ScoreRepository extends JpaRepository<Score, String>,JpaSpecificationExecutor<Score> {
	//通过用户名去判断是否属于管理员帐号
	@Query("from Score where time=?1 and userguid=?2")
	public Score find(String time,String userguid);
}
