package net.risesoft.form.dao;

import java.sql.Connection;
import java.util.List;

import net.risesoft.beans.onlineservice.CorpGasStation;

public interface CorpStationDao{
	
	public List<CorpGasStation>queryInfo(String insGuid,Connection conn);

}
