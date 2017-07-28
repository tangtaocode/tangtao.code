package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.XzqlXzspListsService;
import net.risesoft.util.GUID;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONObject;

@Service(value="xzqlXzspListsService")
public class XzqlXzspListsServiceImpl implements XzqlXzspListsService{

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	

	/*
	 * 材料明细列表
	 * 
	 */
	@Override
	public List<Map<String, Object>> findById(String itemid) {
		List<Map<String, Object>> alllistmap = new ArrayList<Map<String, Object>>();
		try{
			
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			//获取最大version号
			List<Object> params = new ArrayList<Object>();
			params.add(itemid);
			String vsql = " select max(version) from XZQL_XZSP_BASELOG where  itemid = ?";
			String version =jdbcTemplate.queryForObject(vsql,params.toArray() ,String.class);
			
			//获取该类别的详细材料
			String sql = "select s.declareannexguid id, s.approveitemguid itemid, s.declareannextabindex orderno, "
					+ "s.declareannexname materialname, s.declareannextype materiallx, "
					+ "to_char(s.doctypeguid) doctypeguid, to_char(s.doctypename) doctypename, to_char(s.declareannexdesc) describe "
					+ "from spm_declareannexrelation r, spm_declareannextype t, spm_declareannex s "
					+ "where r.approveitemguid=? and r.declareannextypeguid=t.declareannextypeguid "
					+ "and r.declareannexguid=s.declareannexguid order by s.declareannextabindex";
			List<Object> params1 = new ArrayList<Object>();
			params1.add(itemid);
  		    //params1.add(version);
			listmap= jdbcTemplate.queryForList(sql,params1.toArray());
			//拼装关联材料共享

			for (Map<String, Object> map2 : listmap){
				Map<String, Object> map = new HashMap<>();
			     //遍历取出详细材料，组合上共享材料绑定
			List<Object> params2 = new ArrayList<Object>();
    		String glsql = "SELECT t.listid,f. STUFFDEFINENAME || '('|| f .code ||')' as STUFFDEFINENAME,f.STUFFDEFINEID from  SS_STUFFSHAREITEMDEF t,SS_STUFFDEFINE f  where  t.STUFFDEFINEID = f.STUFFDEFINEID  and t.LISTID = ? ";
    		params2.add(map2.get("ID"));
    		List<Map<String, Object>> listmap2 = jdbcTemplate.queryForList(glsql,params2.toArray());
    		Iterator<Map<String, Object>> it = listmap2.iterator();
			String STUFFDEFINEID ="";
			String STUFFDEFINENAME ="";
    		while(it.hasNext()){
        	//循环取出材料信息项
    		Map<String, Object> rece = (Map<String, Object>) it.next();
    		STUFFDEFINEID += (String)rece.get("STUFFDEFINEID") +",";
    		STUFFDEFINENAME += (String)rece.get("STUFFDEFINENAME") +",";
    		 map.put("STUFFDEFINEID", STUFFDEFINEID);
             map.put("STUFFDEFINENAME", STUFFDEFINENAME);
    		  } 
    		 map.put("ID", map2.get("ID"));
    		 map.put("ITEMID", map2.get("ITEMID"));
    		 map.put("VERSION", version);
    		 map.put("ORDERNO", map2.get("ORDERNO"));
    		 map.put("DESCRIBE", map2.get("DESCRIBE"));
    		 map.put("MATERIALNAME", map2.get("MATERIALNAME"));
    		 map.put("MATERIALLX", map2.get("MATERIALLX")); 
    		 map.put("DOCTYPEGUID", map2.get("DOCTYPEGUID"));
    		 map.put("DOCTYPENAME", map2.get("DOCTYPENAME"));
    		alllistmap.add(map);
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		return alllistmap;
	}
	
	
	
	/*
	 * 修改材料明细信息
	 * 
	 */
	@Override
	public int rmupdate(String ID,String MATERIALLX,String MATERIALNAME,String DESCRIBE,String STUFFDEFINENAME,
			String DOCTYPENAME,String DOCTYPEGUID,String ORDERNO) {
		List<Object> params = new ArrayList<Object>();
		int b = 0 ;
		//判断不为空项
		if(!StringX.isBlank(ID)&&!StringX.isBlank(DOCTYPENAME)){
			String sql = "UPDATE  XZQL_XZSP_LISTS t set  t.MATERIALLX = ? ,t.MATERIALNAME = ?,t.DESCRIBE = ? ,t.ORDERNO = ?,t.DOCTYPEGUID = ? ,t.DOCTYPENAME = ? where t.ID = ?  ";
            params.add(MATERIALLX);
			params.add(MATERIALNAME);
			params.add(DESCRIBE);
			params.add(ORDERNO);
			params.add(DOCTYPEGUID);
			params.add(DOCTYPENAME);
			params.add(ID);
			//插入关联材料共享
          /*params.add(STUFFDEFINENAME);*/
			
         b = jdbcTemplate.update(sql,params.toArray());
		}
		//往log表执行修改留痕
		if(b>0){
			//获取最大version号
			List<Object> params2 = new ArrayList<Object>();
			params2.add(ID);
			String vsql = " select max(version) from XZQL_XZSP_BASELOG where  itemid = ?";
			String version =jdbcTemplate.queryForObject(vsql,params2.toArray() ,String.class);
			List<Object> params1 = new ArrayList<Object>();
			String sql = "UPDATE  XZQL_XZSP_LISTSLOG t set  t.MATERIALLX = ? ,t.MATERIALNAME = ?,t.DESCRIBE = ? ,t.ORDERNO = ?,t.DOCTYPEGUID = ? ,t.DOCTYPENAME = ? where t.ID = ?  and t.VERSION = ?";
            params1.add(MATERIALLX);
			params1.add(MATERIALNAME);
			params1.add(DESCRIBE);
			params1.add(ORDERNO);
			params1.add(DOCTYPEGUID);
			params1.add(DOCTYPENAME);
			params1.add(ID);
			params1.add(version);
			//插入关联材料共享
          params1.add(STUFFDEFINENAME);
			
         jdbcTemplate.update(sql,params1.toArray());
		}
		return b;
	}

	
	
	/*
	 * 获取材料类别
	 * 
	 */
	@Override
	public Pager findTableById(String itemid,Pager page) {
		int pageNum = page.getPageNo();
		int pageSize= page.getPageSize();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			//获取最大version号
			List<Object> params = new ArrayList<Object>();
			params.add(itemid);
			String vsql = " select max(version) from XZQL_XZSP_BASELOG where  itemid = ?";
			String version =jdbcTemplate.queryForObject(vsql,params.toArray() ,String.class);
			 
			//查询材料类型
			String sql = "select rownum, t.typeorder orderno, t.declareannextypename typename, t.approveitemguid itemid, t.declareannextypeguid typeguid "
					+ "from spm_declareannextype t where t.approveitemguid=? order by t.typeorder ";
			List<Object> params1 = new ArrayList<Object>();
			params1.add(itemid);
			listmap= jdbcTemplate.queryForList(sql,params1.toArray());
			page.setTotalRows(listmap.size());
			page.setPageList(jdbcTemplate.queryForList(page.setPageSql(sql, pageNum, pageSize),params1.toArray()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return page;
	}
	
	/*
	 * 获取材料详细信息
	 * 
	 */
	@Override
	public List<Map<String, Object>> findmaterialById(String itemid,String typeguid) {

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			if(itemid!=null){
			//获取最大version号
			List<Object> params = new ArrayList<Object>();
			params.add(itemid);
			String vsql = " select max(version) from XZQL_XZSP_BASELOG where  itemid = ?";
			String version =jdbcTemplate.queryForObject(vsql,params.toArray() ,String.class);
			
			//获取该类别的详细材料
			String sql = "select t.declareannexguid id, t.approveitemguid itemid, t.declareannextabindex orderno, t.declareannexdesc describe, "
					+ "t.declareannexname materialname, d.declareannextypename materiallx "
					+ "from spm_declareannex t, spm_declareannextype d where t.approveitemguid=? "
					+ "and t.declareannextypeguid(+)=d.declareannextypeguid order by t.declareannextabindex";
			List<Object> params1 = new ArrayList<Object>();
			params1.add(itemid);
			//params1.add(version);
			listmap= jdbcTemplate.queryForList(sql,params1.toArray());
			
			//获取详细材料勾选状态
			for (Map<String, Object> map2 : listmap){
				Map<String, Object> map = new HashMap<>();
			     //按照typeguid取出xzql_xzsp_listtypelog表listguid字段与详细材料ID对比，
			List<Object> params2 = new ArrayList<Object>();
    		String glsql = "select t.declareannexguid from spm_declareannexrelation t where t.declareannextypeguid=?  ";
    		params2.add(typeguid);
    		//params2.add(version);
    		List<Map<String, Object>> listmap2 = jdbcTemplate.queryForList(glsql,params2.toArray());
    		Iterator<Map<String, Object>> it = listmap2.iterator();
			String materialcheck="";
			while(it.hasNext()){
			Map<String, Object> rece = (Map<String, Object>) it.next();
			//判断该类别下材料是否选中
			if(rece.get("listguid").equals(map2.get("ID"))){
				  materialcheck = "checked";			
    		  } 		 
			}
			map.put("CHECK", materialcheck);
   		    map.put("ID", map2.get("ID"));
   		    map.put("ITEMID", map2.get("ITEMID"));
   		    map.put("ORDERNO", map2.get("ORDERNO"));
   		    map.put("DESCRIBE", map2.get("DESCRIBE"));
   		    map.put("MATERIALNAME", map2.get("MATERIALNAME"));
   		    map.put("MATERIALLX", map2.get("MATERIALLX"));  
   		    map.put("VERSION", version); 
    		 list.add(map);
            }
		  }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/*
	 * 删除材料
	 * 
	 */
	public int deleteById(String id){
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from XZQL_XZSP_LISTS t  where t.id=? ";
		params.add(id);
		return jdbcTemplate.update(sql,params.toArray());
	}
	
	/*
	 * 新增材料
	 * 
	 */
	@Override
	public int add(String id,String itemid,String version,String materiallx,String  materialname,String describe,int orderno) {
		List<Object> params = new ArrayList<Object>();
		
		String sql = "insert into XZQL_XZSP_LISTS(id,itemid,materiallx,materialname,describe,orderno) values(?,?,,?,?,?,?)";

		params.add(id);
		params.add(itemid);
		//params.add(version);
		params.add(materiallx);
		params.add(materialname);
		params.add(describe);
		params.add(orderno);
		int a = jdbcTemplate.update(sql,params.toArray());
		//执行成功后往log表新增一条数据
		if(a>0){
			List<Object> params1 = new ArrayList<Object>();
		 String sqllog = "insert into XZQL_XZSP_LISTS(id,itemid,version,materiallx,materialname,describe,orderno) values(?,?,?,?,?,?,?)";	
		 params1.add(id);
		 params1.add(itemid);
		 params1.add(version);
		 params1.add(materiallx);
		 params1.add(materialname);
		 params1.add(describe);
		 params1.add(orderno);	
		 jdbcTemplate.update(sqllog,params1.toArray());
		} 
		 
		 return a;
	}

	
	/*
	 * 修改材料分类信息
	 * 
	 */
	@Override
	public int mupdate(String typeguid, String typename, String orderno,
			String banben) {
		List<Object> params1 = new ArrayList<Object>();
		int b = 0;
		// 判断不为空项
		if (!StringX.isBlank(typeguid) && !StringX.isBlank(typename)
				&& !StringX.isBlank(banben)) {
			String sql = "UPDATE  XZQL_XZSP_LISTTYPE t set  t.TYPENAME = ? ,t.ORDERNO = ? where t.TYPEGUID = ? ";
			params1.add(typename);
			params1.add(orderno);
			params1.add(typeguid);
			b = jdbcTemplate.update(sql, params1.toArray());
			// 执行成功后往log表新增一条数据

			// 判断不为空项
			if (b > 0) {
				List<Object> params = new ArrayList<Object>();

				String sqllog = "UPDATE  XZQL_XZSP_LISTTYPELOG t set  t.TYPENAME = ? ,t.ORDERNO = ? where t.TYPEGUID = ? and t.VERSION = ? ";
				params.add(typename);
				params.add(orderno);
				params.add(typeguid);
				params.add(banben);
				jdbcTemplate.update(sqllog, params.toArray());
			}
		}
		return b;
	}


	/*
	 * 删除已选中材料勾选项
	 * 
	 */
	@Override
	public int materialdel(String itemid,String version) {
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		int a = 0 ;
		//先删除该typeguid里所有已勾选ID，然后再insert
		if(!StringUtils.isBlank(itemid)&&!StringUtils.isBlank(version)){
			List<Object> params1 = new ArrayList<Object>();
			String del = "DELETE FROM XZQL_XZSP_LISTOFTYPE t where  t.itemid = ? ";	
                params1.add(itemid);
		       
		  a =jdbcTemplate.update(del,params1.toArray());
		}
		
		return a;
	}
	
	/*
	 * 修改材料勾选项
	 * 
	 */
	@Override
	public int materialupdata(String typeguid,String itemid,String version,String  listguid) {
		List<Object> params = new ArrayList<Object>();
		int b = 0 ;
		//先删除该typeguid里所有已勾选ID，然后再insert
		if(!StringX.isBlank(typeguid)&&!StringX.isBlank(itemid)&&!StringX.isBlank(version)&&!StringX.isBlank(listguid)){
			String sql = "insert into XZQL_XZSP_LISTOFTYPE (typeguid,itemid,listguid,id) values(?,?,?,?)";
		    params.add(typeguid);
			params.add(itemid);
			//params.add(version);
			params.add(listguid);
			params.add(new GUID().toString());
         b = jdbcTemplate.update(sql,params.toArray());
		}	
		return b;
	}

	/*
	 * 删除分类
	 * 
	 */
	@Override
	public int materialdelete(String typeguid,String version) {
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		int a = 0 ;
		//先删除该分类
		if(!StringUtils.isBlank(typeguid)){
			List<Object> params1 = new ArrayList<Object>();
			String del = "DELETE  XZQL_XZSP_LISTTYPE t where  t.typeguid = ? ";	
                params1.add(typeguid);
                
		  a =jdbcTemplate.update(del,params1.toArray());
		}
		
		return a;
	}
	
	
	/*
	 * 新增材料类别
	 * 
	 */
	@Override
	public int materialinsert(String TYPEGUID,String ITEMID,String TYPENAME,String ORDERNO,String  version) {
		List<Object> params = new ArrayList<Object>();
		int b = 0 ;
		//先删除该typeguid里所有已勾选ID，然后再insert
		if(!StringX.isBlank(ITEMID)&&!StringX.isBlank(TYPENAME)&&!StringX.isBlank(ORDERNO)){
			//String sqllog = "insert into XZQL_XZSP_LISTTYPELOG (typeguid,itemid,version,typename,orderno) values(?,?,?,?,?)";
			String sql = "insert into XZQL_XZSP_LISTTYPE (typeguid,itemid,typename,orderno) values(?,?,?,?)";
			params.add(TYPEGUID);
			params.add(ITEMID);
			//params.add(version);
			params.add(TYPENAME);
			params.add(ORDERNO);
        b = jdbcTemplate.update(sql,params.toArray());
		}return b;
	}

	/*
	 * 多选框材料树列表
	 * 
	 */
	@Override
	public String findStuffTree() {
		StringBuilder param=new StringBuilder();
		param.append("[");
		String sql="select t.STUFFDEFINEID as Id ,t.STUFFDEFINEID as pid ,t.STUFFDEFINENAME || '('|| t .code ||')'  as name from SS_STUFFDEFINE t  order by t.tabindex ";
	//	String sql="select t.superior_guid as pid,t.department_guid as gid,m.doctypename as name  from RISENET_DEPARTMENT t  right join T_BDEX_DOCTYPE m on m.bguid=t.department_guid start with  t.department_guid='{BFA7BA9B-FFFF-FFFF-AD1B-40DE0000000F}' connect by t.superior_guid=prior t.department_guid";
		List<Map<String, Object>> mapList=jdbcTemplate.queryForList(sql);
		int i=0;
		for(Map<String,Object> e : mapList){
			String pids = "" ; 
			if(i==mapList.size()-1){
				param.append("{id:'"+e.get("ID")+"',");
				param.append("parentId:'"+e.get("PID")+"',");
				//param.append("docutypeGid:'"+e.get("ID")+"',");
				param.append("name:'"+e.get("NAME")+"'}]");
				//param.append("click:'clickNode();'}]");
			}else{
				param.append("{id:'"+e.get("ID")+"',");
				param.append("parentId:'"+e.get("PID")+"',");
				//param.append("docutypeGid:'"+e.get("ID")+"',");
				param.append("name:'"+e.get("NAME")+"'},");
				//param.append("click:'clickNode(this);'},");
				i++;
			}
		}
		JSONObject  json= new JSONObject();
		json.put("treeNodes", param.toString());
		return json.toString();
	}
	
	/*
	 * 绑定关联材料
	 * 
	 */
	@Override
	public int stuffupdate(String STUFFDEFINEID,String CLTYPEID,String LISTID){
		List<Object> params = new ArrayList<Object>();
		int b = 0 ;
		//判断不为空项
		if(!StringX.isBlank(STUFFDEFINEID)&&!StringX.isBlank(CLTYPEID)&&!StringX.isBlank(LISTID)){
			String sql = "insert into SS_STUFFSHAREITEMDEF (GUID,STUFFDEFINEID,CLTYPEID,LISTID) values(?,?,?,?) ";
            params.add(new GUID().toString());
			params.add(STUFFDEFINEID);
			params.add(CLTYPEID);
			params.add(LISTID);
         b = jdbcTemplate.update(sql,params.toArray());
		}	
		return b;
	}
	
	/*
	 * 多选框证照树列表
	 * 
	 */
	@Override
	public String finddoctypeTree() {
		StringBuilder param=new StringBuilder();
		//String sql="select t.ID as Id ,t.PID as pid ,t.NAME as name from VIEW_XZSP_ITEMTREEDIR t where t.ID not in (select h.guid from xzql_hide h where h.status='-1') and pid is not null order by t.ORDERNO ";
		String sql=" select s.bureauguid Id, '999999999' pid,s.bureauname  as NAME, s.BUREAUTABINDEX as orderno from spm_bureau  s where s.isstreet = '0' or  s.isstreet = '1' union all  select t.guid as Id, s.bureauguid as pid, t.doctypename  as NAME,s.BUREAUTABINDEX as orderno  from t_bdex_doctype t, spm_bureau  s  where t.bguid = s.bureauguid order by orderno";
		//String sql = "select Id, pid, NAME, bureausn  from (select s.bureauguid Id, s.department_guids   as pid,  s.bureauname as NAME,  s.bureausn   from spm_bureau s where 1=1 union all   select t.guid   Id, s.bureauguid as pid, t.doctypename     as NAME,  s.bureausn   from t_bdex_doctype t,spm_bureau s     where  rtrim(t.bguid) = rtrim(s.bureauguid)) order by bureausn";
		List<Map<String, Object>> mapList=jdbcTemplate.queryForList(sql);
		int i=0;
		param.append("[");
		for(Map<String,Object> e : mapList){
			String pids = "" ; 
			//父节点添加nocheck状态
			if(e.get("PID")!=""&&e.get("PID")!=null&&e.get("PID").equals("999999999")){	
				pids = "parentId:'"+e.get("PID")+"', nocheck: true,";
			}else{	
				pids = "parentId:'"+e.get("PID")+"',";
			}
			if(i==mapList.size()-1){
				param.append("{id:'"+e.get("ID")+"',");
				param.append(pids);
				//param.append("docutypeGid:'"+e.get("ID")+"',");
				param.append("name:'"+e.get("NAME")+"'}]");
				//param.append("click:'clickNode();'}]");
			}else{
				param.append("{id:'"+e.get("ID")+"',");
				param.append(pids);
				//param.append("docutypeGid:'"+e.get("ID")+"',");
				param.append("name:'"+e.get("NAME")+"'},");
				//param.append("click:'clickNode(this);'},");
				i++;
			}
		}
		JSONObject  json= new JSONObject();
		json.put("treeNodes", param.toString());
		return json.toString();
	}
	
	
	
	@Override
	public int stuffdel(String LISTID) {
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		int a = 0 ;
		//先删除该typeguid里所有已勾选ID，然后再insert
		if(!StringUtils.isBlank(LISTID)){
			List<Object> params1 = new ArrayList<Object>();
			String del = "DELETE  SS_STUFFSHAREITEMDEF t where  t.listid = ? ";	
                params1.add(LISTID);
		  a =jdbcTemplate.update(del,params1.toArray());
		}
		
		return a;
	}


	/*
	 * 绑定关联证照
	 * 
	 */
	@Override
	public int doctypeupdate(String ID,String ALLNAME,String ALLID){
		List<Object> params = new ArrayList<Object>();
		int b = 0 ;
		//判断不为空项
		if(!StringX.isBlank(ID)){
//			String sql = "UPDATE  XZQL_XZSP_LISTS@luohu84 t set  t.DOCTYPENAME = ? ,t.DOCTYPEGUID = ? where t.ID = ? ";
			String sql = "update spm_declareannex t set t.doctypename=?, t.doctypeguid=? where t.declareannexguid=? ";
            params.add(ALLNAME);
			params.add(ALLID);
			params.add(ID);
			try {
				b = jdbcTemplate.update(sql,params.toArray());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}	
		return b;
	}
}
