/**
 * @Project Name:risenet-sp-approve
 * @File Name: StuffdataRepository.java
 * @Package Name: net.risesoft.approve.repository.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月19日 上午11:55:34
 */
package net.risesoft.approve.repository.jpa;

import java.util.Date;

import net.risesoft.approve.entity.jpa.Stuffdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: StuffdataRepository.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年3月19日 上午11:55:34
 * @version 
 * @since JDK 1.7
 */
public interface StuffdataRepository extends JpaRepository<Stuffdata, String>,JpaSpecificationExecutor<Stuffdata> {

	public Stuffdata findByGuid(String stuffdataguid);

	@Transactional(readOnly = false)
	@Modifying
	@Query("update Stuffdata t set t.stuffdataname =?2, t.certifyperson=?3, t.sync='0' where t.guid=?1 ")
	public int updateStuffdataByName(String guid, String columnValue, String certifyPerson);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("update Stuffdata t set t.gxtime=sysdate, t.limitforever =?2, t.certifyperson=?3, t.sync='0' where t.guid=?1 ")
	public int updateStuffdataByForever(String guid, String columnValue, String certifyPerson);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("update Stuffdata t set t.gxtime=sysdate, t.limitbegin =?2, t.certifyperson=?3, t.sync='0' where t.guid=?1 ")
	public int updateStuffdataByBegin(String guid, Date columnValue, String certifyPerson);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("update Stuffdata t set t.gxtime=sysdate, t.limitend =?2, t.certifyperson=?3, t.sync='0' where t.guid=?1 ")
	public int updateStuffdataByEnd(String guid, Date columnValue, String certifyPerson);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("update Stuffdata t set t.state ='3', t.gxtime = sysdate, t.certifytime=sysdate, t.remark=?2, t.certifyperson=?3, t.sync='0' where t.guid=?1 ")
	public int updateStuffdataByState(String guid, String remark, String certifyPerson);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("update Stuffdata t set t.state ='2', t.remark='', t.gxtime = sysdate, t.certifytime=sysdate, t.certifyperson=?2, t.sync='0' where t.guid=?1 ")
	public int updateStuffdataByState(String guid, String certifyPerson);

}

