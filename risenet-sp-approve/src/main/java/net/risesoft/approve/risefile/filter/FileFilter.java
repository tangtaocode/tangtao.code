package net.risesoft.approve.risefile.filter;

import net.risesoft.approve.risefile.FileBox;
import net.risesoft.approve.risefile.exception.RiseFileException;

public interface FileFilter {

	public int getFilterIndex();

	public int getIndex();

	public FileBox excute(FileBox filebox,RiseFileContext context)throws RiseFileException;
}
