package net.risesoft.approve.risefile.manager;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;





import javax.annotation.Resource;




import org.springframework.jdbc.core.JdbcTemplate;

//import net.risesoft.assistance.Util;
//import net.risesoft.commons.database.Conn;


import net.risesoft.approve.risefile.RiseFile;
import net.risesoft.approve.risefile.exception.RiseFileException;

public class MeetingTopicFileManager {
	@Resource
	private JdbcTemplate jdbctemplate;
	
	public int getMeetTopicFiles(String fileGUID) throws RiseFileException{
		int count = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from risenet_file where appinstguid = (select appinstguid from risenet_file where fileguid = '" + 
					 fileGUID+  "')";
		
		try {
			count = jdbctemplate.queryForObject(sql,Integer.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return count;
	}
	
	public RiseFile getTopicFile(String fileGUID) throws RiseFileException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		RiseFile riseFile = null;
		String sql = "select * from risenet_file where fileGuid = '" + fileGUID + "'";
		try {
//			conn = Conn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				riseFile = new RiseFile();
				riseFile.setFileGUID(rs.getString("fileGuid"));
				riseFile.setFilename(rs.getString("fileName"));
				riseFile.setFileNameExt(rs.getString("fileNameExt"));
				riseFile.setTitile(rs.getString("titile"));
				riseFile.setAppName(rs.getString("appName"));
				riseFile.setFileboxName(rs.getString("fileboxName"));
				riseFile.setAppInstGUID(rs.getString("appInstGUID"));
				riseFile.setCreatorGUID(rs.getString("creatorGuid"));
				riseFile.setRealFullPath(rs.getString("realFullPath"));
				riseFile.setFileType(rs.getString("fileType"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			Util.closeConn(conn, stmt, rs);
		}
		return riseFile;
	}
	
	public void deleteMeetTopicFile(RiseFile riseFile) throws RiseFileException{
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		try {
//			conn = Conn.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = "delete from meet_meetingtopic where topic_Guid = '" + riseFile.getAppInstGUID() + "'";
			stmt.addBatch(sql);
			sql = "delete from meet_topic where topic_Guid = '" + riseFile.getAppInstGUID() + "'";
			stmt.addBatch(sql);
			sql = "delete from risenet_file where fileGuid = '" + riseFile.getFileGUID() + "'";
			stmt.addBatch(sql);
			
			stmt.executeBatch();
			conn.commit();
			File oldFile = new File(riseFile.getRealFullPath());
			oldFile.delete();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
//			Util.closeConn(conn, stmt, null);
		}
	}
	
	public void deleteTopicFile(RiseFile riseFile) throws RiseFileException{
		Connection conn = null;
		Statement stmt = null;
		String sql = "delete from risenet_file where fileGuid = '" + riseFile.getFileGUID() + "'";
		try {
//			conn = Conn.getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
			File oldFile = new File(riseFile.getRealFullPath());
			oldFile.delete();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			Util.closeConn(conn, stmt, null);
		}
	}
}
