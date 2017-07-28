package net.risesoft.approve.service.gdbs;

import net.risesoft.approve.entity.jpa.gdbs.ShenBanProcess;


public interface ShenBanService {
	
	public ShenBanProcess findShenBanProcess(String sblsh);
}
