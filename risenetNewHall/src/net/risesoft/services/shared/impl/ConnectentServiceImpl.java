package net.risesoft.services.shared.impl;

import java.sql.Connection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.services.shared.IConnectentService;
@Service
public class ConnectentServiceImpl implements IConnectentService {
	@Resource
	private ISimpleJdbcDao<String> simpleJdbcDao;

	@Override
	public Connection getConnection() {
		return simpleJdbcDao.getNativeConn();
	}

}
