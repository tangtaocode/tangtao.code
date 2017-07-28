package net.risesoft.approve.service.gdbs;

import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;


public interface IShouLiService {
	
	public int updateShouliStatus(String status,String sblsh_short);
	
	public ShouliProcess findBySblsh(String sblsh);
}
