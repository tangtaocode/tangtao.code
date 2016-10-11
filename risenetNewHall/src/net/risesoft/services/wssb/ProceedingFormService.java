package net.risesoft.services.wssb;

import java.util.List;

import net.risesoft.beans.wssb.ProceedingForm;

public interface ProceedingFormService {
	public List<ProceedingForm> getFormsList(String sql,Object[] param)throws Exception;
	public List<ProceedingForm> getFormsList(String sql)throws Exception;
}
