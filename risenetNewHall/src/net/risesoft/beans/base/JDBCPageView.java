package net.risesoft.beans.base;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;


import net.risesoft.utils.base.GenericsUtils;

/**
 * 
  * @ClassName: JDBCPageView
  * @Description: 分页数据包装，包括分页信息和List数据
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:01:16 PM
  *
  * @param <T>
 */
public class JDBCPageView<T>
{
	/** 分页数据 **/
	private List<T> records;
	/** 页码开始索引和结束索引 **/
	private PageIndex pageIndex;
	/** 总页数 **/
	private int totalPage = 1;
	/** 每页显示记录数 **/
	private int maxResult = 10;
	/** 当前页 **/
	private int currentPage = 1;
	/** 总记录数 **/
	private int totalRecord;
	/** 每次显示多少页，必须保证大于3页，保证左右链接都可以使用 **/
	private int viewPageCount = 10;
	/**codeMap列键值转换map**/
	private TreeMap<String,TreeMap<String, String>> columnMaps;
	
	/**增加列键值转换map**/
	public void addColumnMaps(String keyName, TreeMap<String, String> columnMap) {
		if (columnMaps == null) {
			columnMaps = new TreeMap<String,TreeMap<String, String>>();
		}
		columnMaps.put(keyName, columnMap);
	}
	
	public void initColumn(){
		if(columnMaps!=null){
			Set<Entry<String,TreeMap<String, String>>> ent = columnMaps.entrySet();
			for(Entry<String,TreeMap<String, String>> entry:ent){
				for(int i=0;i<records.size();i++){
					Object obj = records.get(i);
					try {
						GenericsUtils.populate(obj, entry.getValue(), entry.getKey());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	/** 要获取记录的开始索引 **/
	public int getFirstResult()
	{
		return (this.currentPage - 1) * this.maxResult;
	}

	public int getViewPageCount()
	{
		return viewPageCount;
	}

	public void setViewPageCount(int viewPageCount)
	{
		this.viewPageCount = viewPageCount;
	}

	public JDBCPageView(int maxResult, int currentPage)
	{
		this.maxResult = maxResult;
		this.currentPage = (currentPage <= 0 ? 1 : currentPage);
	}

	public JDBCPageView(int currentPage)
	{
		this.currentPage = (currentPage <= 0 ? 1 : currentPage);
	}

	public void setQueryResult(QueryResult<T> qr)
	{
		setTotalRecord(qr.getTotalRecord());
		setRecords(qr.getResultList());
	}
	
	public int getTotalRecord()
	{
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord)
	{
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord % this.maxResult == 0 ? this.totalRecord / this.maxResult : this.totalRecord / this.maxResult + 1);
	}

	public List<T> getRecords()
	{
		return records;
	}
	
	public void setRecords(List<T> records)
	{
		this.records = records;
	}

	public PageIndex getPageIndex()
	{
		return pageIndex;
	}

	public int getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
		this.pageIndex = PageIndex.getPageIndex(viewPageCount, currentPage, totalPage);
	}

	public int getMaxResult()
	{
		return maxResult;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}
}
