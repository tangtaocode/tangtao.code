package net.risesoft.common.base;
import java.util.List;
import java.util.Map;


/**
 * qui分页
 * @author Administrator
 *
 */
public class Pager {
	private int totalRows; // 总行数
	private int pageSize = 20; // 每页显示的行数
	private int pageNo = 1; // 当前页号
	private int totalPages = 0; // 总页数
	private int startRow; // 当前页在数据库中的起始行
	
	private List<Map<String,Object>> pageList;
	private String pageSql;
	private  static final String initSql = "select * from (";
	private  static final String endSql=")";

	public Pager() {
	
	}
	public Pager(int pageNo, int pageSize) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}
	public List<Map<String, Object>> getPageList() {
		return pageList;
	}
	public void setPageList(List<Map<String, Object>> pageList) {
		this.pageList = pageList;
	}
	
	public String getPageSql() {
		return pageSql;
	}
	
	public String setPageSql(String sql,int pageNum,int pageSize) {
		if(pageSize==-1){//如果size传入-1则默认每页显示20条，可自定义显示条数
			pageSize=this.pageSize;
		}
		//+ " and rownum>=" + ((pageNum-1)*pageSize);
		String endSql = ")t where rownum<=" + (pageNum*pageSize);
		String str = "select t.*,ROWNUM RN from ("+sql+endSql;
		str = initSql+str+") where RN>"+((pageNum-1)*pageSize);
		return str;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
}