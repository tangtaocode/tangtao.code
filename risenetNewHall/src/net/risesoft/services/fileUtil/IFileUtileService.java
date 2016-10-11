package net.risesoft.services.fileUtil;

import java.util.List;

import net.risesoft.beans.risefile.UpFileBean;


public interface IFileUtileService {
	public List<UpFileBean> getFileBeanList(String sql,Object[] obj)throws Exception;
	public UpFileBean getFileBean(String sql,Object[] obj)throws Exception;
}
