package net.risesoft.approve.risefile.filter.appfilter;

import net.risesoft.approve.risefile.FileBox;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.filter.QueryFilter;
import net.risesoft.approve.risefile.filter.RiseFileContext;

public class OfficeFilter extends QueryFilter{

	public int getIndex() {
		return 30;
	}

	public FileBox excute(FileBox filebox, RiseFileContext context) throws RiseFileException {
		System.out.println("riseoffice filter RiseFile.......");
		String wfFolder=context.getParameter("wfFolder");
		if(wfFolder==null){
			filebox.setFileBoxPerm(FileBox.PERMISSION_READWRITE);
		}else if(wfFolder.equals("new") || wfFolder.equals("todo")){
			filebox.setFileBoxPerm(FileBox.PERMISSION_READWRITE);
		}else{
			filebox.setFileBoxPerm(FileBox.PERMISSION_READ);
		}
		return filebox;
	}

}
