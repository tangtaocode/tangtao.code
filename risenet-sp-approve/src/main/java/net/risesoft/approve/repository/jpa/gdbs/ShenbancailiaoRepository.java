package net.risesoft.approve.repository.jpa.gdbs;

import java.util.List;

import net.risesoft.approve.entity.jpa.gdbs.ShenBanCailiaoProcess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

//持久层操作特别程序申请表
public interface ShenbancailiaoRepository extends JpaRepository<ShenBanCailiaoProcess, String>,JpaSpecificationExecutor<ShenBanCailiaoProcess> {
	
	//查找材料相关信息
	@Query("from ShenBanCailiaoProcess s where instr(?1,s.seq)>0 and s.sblshShort=?2")
	public List<ShenBanCailiaoProcess> findByClids(String clids,String sblsh);
}
