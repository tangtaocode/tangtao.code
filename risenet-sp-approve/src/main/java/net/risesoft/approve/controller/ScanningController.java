package net.risesoft.approve.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.TBdexDoctype;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.ScanningService;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/scanning")
public class ScanningController {
	protected Logger log = LoggerFactory.getLogger(ScanningController.class);
	@Autowired
	public ScanningService scanningService;

	@Autowired
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	/**
	 * * 
	 * @param  证件扫描首页
	 * @return
	 */
	@RiseLog(operateName = "打开证件扫描首页",operateType = "查看")
	@RequestMapping(value = "/certificate", method = RequestMethod.GET)
	public String certShow() {
		return "certmanage/scanning";
	}
	
	/**
	 * 保存成功后跳到业务资料查询页面
	 */	
	@RiseLog(operateName = "打开业务资料查询页面",operateType = "查看")
	@RequestMapping(value = "/into", method = RequestMethod.GET)
	public String into() {
		return "certmanage/scanninginfo";
	}
	
	

	/**
	 * * 
	 * @param  跳转到证件扫描页面
	 * @return
	 */
	@RiseLog(operateName = "打开证件扫描页面",operateType = "查看")
	@RequestMapping(value = "/goscan", method = RequestMethod.GET)
	public ModelAndView  goscan(HttpServletRequest req) {
		 System.out.println("11");
		//得到当前审批事项instanceGuid,approveitemname
		 String instanceGuid = req.getParameter("instanceGuid");
         String doctypeguid = req.getParameter("doctypeguid");  
         String  DECLARESN = req.getParameter("certifyNumber");//证照编号
         String FAZDW =  req.getParameter("bureauName"); //办理单位
         String declarerperson =  req.getParameter("declarerperson"); //证照主体姓名
         String address =  req.getParameter("address"); //申请单位地址
         String jzrq=req.getParameter("jzrq");
         System.out.println(jzrq);
	    //根据approveitemname查询到当前事项基本信息项ID
 		
        //TBdexDoctype doctypeInfo =  scanningService.findBybasicifnoID(doctypeguid);
        OfficeSpiDeclareinfo info=officeSpiDeclareinfoService.findByGuid(instanceGuid);
        ModelAndView mav =null;
        String approveitemname=info.getApproveItemName();
        if (approveitemname.equals("取水许可")) {
        	mav = new ModelAndView("certmanage/qsxkforminfo");
		}else if (approveitemname.equals("城市排水许可")) {
			mav = new ModelAndView("certmanage/cspsxkforminfo");
		}
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    mav.addObject("DECLARESN", DECLARESN);
	    mav.addObject("FAZDW", FAZDW);
		mav.addObject("approveitemname", approveitemname);
		mav.addObject("doctypeguid", doctypeguid);
		mav.addObject("instanceGuid", instanceGuid);
		mav.addObject("zhengzztxm", declarerperson);
		mav.addObject("fazrq",sdf.format(new Date()));
		mav.addObject("address",address);
		mav.addObject("jzrq",jzrq);
		//打印办结单所需数据
		//var printurl=ctx+'/sp/pdfFile/banJieDanForm?
		//SPinstanceId='+SPinstanceId+'
		//		&processInstanceId='+processInstanceId+"
		//		&processSerialNumber="+processSerialNumber+"
		//		&taskId="+taskId,
		return mav;
	}
		
	/**
	 * * 
	 * @param  查询证照扫描基本信息
	 * @return
	 */
	@RiseLog(operateName = "查询证照扫描基本信息",operateType = "查看")
	@RequestMapping(value="/basicinfo",method = RequestMethod.GET)
	@ResponseBody
	public String bcjSelect(HttpServletRequest req){
		String doctypeguid = req.getParameter("doctypeguid");
		List<Map<String,Object>> scantemp = scanningService.findbasicinfo(doctypeguid);
		List<Map<String,Object>> listtemp=new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : scantemp) {
			Map<String,Object> maptemp=new HashMap<String, Object>();

			maptemp.put("INFOCODE",map.get("INFOCODE"));
			maptemp.put("INFOMEMO",map.get("INFOMEMO"));
			listtemp.add(maptemp);
		}
		JSONArray json = JSONArray.fromObject(listtemp);
		return json.toString();
	}	
	
	/**
	 * 得到待扫描信息列表
	 */	
	@RiseLog(operateName = "获取待扫描信息列表",operateType = "查看")
	@RequestMapping(value = "/getData", method = RequestMethod.POST)
	@ResponseBody
	public String getData(HttpServletRequest req) {
		String param = req.getParameter("approveName");
		String code = req.getParameter("code");
		String applyName = req.getParameter("applyName");
		String pageNo = req.getParameter("pager.pageNo");
		String pageSize = req.getParameter("pager.pageSize");
		Map<String, Object> map = new HashMap<>();
		//List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Person person = ThreadLocalHolder.getPerson();
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now =new Date();
		String nowStr=sdf.format(now);
		Pager pager=new Pager();
		try {
			Date nowDate=sdf.parse(nowStr);
			if(StringX.isBlank(pageSize) || StringX.isBlank(pageNo)){
				pageSize="20";
				pageNo = "1";
			}
			pager.setPageNo(Integer.parseInt(pageNo));
			pager.setPageSize(Integer.parseInt(pageSize));
			Pager page = scanningService.findByUserID(person, param,pager,code,applyName);
			Date c=null;
			String w="";
			for (Map e : page.getPageList()) {
				if (e.containsKey("VALIDITYPERIOD")) {
					if (e.get("VALIDITYPERIOD") != null) {
						try{
						String date = sdf.format(sdf.parse(e
								.get("VALIDITYPERIOD").toString()));
						e.remove(e);
						e.put("VALIDITYPERIOD", date);
						//判断办结时间和系统时间对比，提醒预警时间    剩余天数显示绿色  超过的显示红色
						long a=(sdf.parse(e.get("VALIDITYPERIOD").toString()).getTime()-nowDate.getTime())/(1000*3600*24);//化成天数
						if(a>0){//剩余多少天
							w=String.valueOf(a);
						}else{
							w=String.valueOf(a);
						}
						e.put("WARN", w);
						}catch(Exception e1){
							
						}
					}
				}

			}
			map.put("pager.pageNo", page.getPageNo());
			map.put("pageSize", page.getPageSize());
			map.put("pager.totalRows", page.getTotalRows());
			map.put("rows",page.getPageList());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}

	/**
	 * 跳转证照扫描上传页面
	 */
	@RiseLog(operateName = "打开证照扫描上传页面",operateType = "查看")
	@RequestMapping(value = "/upData", method = RequestMethod.GET)
	public ModelAndView goPage(HttpServletRequest request) {
		String banjtime = request.getParameter("time").toString();
		if (banjtime == null || "null".equals(banjtime)) {
			banjtime = "";
		}
		String unit = request.getParameter("unit").toString();
		if (unit == null || "null".equals(unit)) {
			unit = "";
		}
		ModelAndView mav = new ModelAndView("certmanage/scanForm");
		mav.addObject("id", request.getParameter("id"));
		mav.addObject("code", request.getParameter("code"));
		mav.addObject("name", request.getParameter("name"));
		mav.addObject("unit", unit);
		mav.addObject("time", banjtime);
		mav.addObject("instanceguid", request.getParameter("instanceguid"));
		mav.addObject("VALIDITYPERIOD", request.getParameter("VALIDITYPERIOD"));
		return mav;
	}

	/**
	 * 保存
	 */
	@RiseLog(operateName = "保存证照信息",operateType = "增加")
	@RequestMapping(value = "/saveData", method = RequestMethod.POST)
	@ResponseBody
	public String saveData(HttpServletRequest req) {
		String id = req.getParameter("gid");
		String result = scanningService.save(id);
		JSONObject json = new JSONObject();
		if ("SUCCESS".equals(result)) {
			json.put("info", "保存成功");
		} else {
			json.put("info", "保存失败");
		}
		return json.toString();
	}
	
	/**
	 * 页面初始化
	 */
	@RequestMapping(value = "/getSelection")
	@ResponseBody
	public String getSelection(HttpServletRequest req) {
		String result = scanningService.getSelecction();
		JSONObject j=new JSONObject();
		j.put("info", result);
		return j.toString();
	}

	
	/**
	 * * 
	 * @param  保存证照信息
	 * @return
	 */
	@RiseLog(operateName = "保存证照信息",operateType = "增加")
	@RequestMapping(value="/savecon",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savecon(HttpServletRequest req){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		String instanceGuid = req.getParameter("instanceGuid");
		String doctypeguid = req.getParameter("doctypeguid");
		String infocondata=req.getParameter("infocondata");
	
		Map<String,Object> infomap=new HashMap<String, Object>();
		infomap.put("instanceGuid",instanceGuid);
		infomap.put("doctypeguid",doctypeguid);

		infomap.put("zhengzbh",req.getParameter("zhengzbh"));
		infomap.put("zhengzyxq",req.getParameter("zhengzyxq"));
		infomap.put("fazrq",req.getParameter("fazrq"));
		infomap.put("fazdw",req.getParameter("fazdw"));
		infomap.put("zhengzztxm",req.getParameter("zhengzztxm"));
		infomap.put("zhucdz",req.getParameter("zhucdz"));
		infomap.put("jingydz",req.getParameter("jingydz"));
		infomap.put("zhengznr",req.getParameter("zhengznr"));
		infomap.put("zhengzlx",req.getParameter("zhengzlx"));
		
		if(infocondata.length()>0 && infocondata !=null){
			JSONObject jsonObject = JSONObject.fromObject(infocondata);
		    Iterator it = jsonObject.keys();  
		    String zzxx = "";
		       // 遍历jsonObject数据，添加到Map对象  
		       while (it.hasNext())  
		       {  
		           String infocode = String.valueOf(it.next());  
		           String infocon = (String) jsonObject.get(infocode);
		           scanningService.savedocdate(instanceGuid,infocode,infocon);
		           //组装详细内容
		           zzxx+="<"+infocode+">"+infocon+"</"+infocode+">";
		       }
		    String approveitemname=req.getParameter("approveitemname");
		    infomap.put("zzxx",zzxx);
			if(instanceGuid!=null){
					scanningService.savedocinfodate(infomap);
			}
		   
		}
		map.put("success", true);
		return map;
	}
	
	/**
	 * * 
	 * @param  保存证照信息
	 * @return
	 */
	@RiseLog(operateName = "保存证照信息",operateType = "增加")
	@RequestMapping(value="/saveconnew",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveconNew(HttpServletRequest req){
		Map<String, Object> map = new HashMap<String, Object>();
		
		scanningService.savedocdateNew(req);
			
		
		map.put("success", true);
		return map;
	}
}
