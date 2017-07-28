package net.risesoft.approve.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.HistoryServiceI;
import net.risesoft.approve.service.RisenetDepartmentService;
import net.risesoft.util.SendSms;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/scanninghistory")
public class ScanningHistoryController {
	
	public final String wsdl = "http://183.56.159.138:8090/services/Sms?wsdl";
	
	public final String userName = "AjkDbl09UjI=";// 用户名
	// 接口老地址：http://203.91.37.24:8080
	public final String password = "AkUDNl10UndXFFBnUz5XZFw7";// 密码
	
	private List<Map<String,Object>> tongjimap=new ArrayList<Map<String,Object>>();
	
	@Autowired
	private RisenetDepartmentService departmentService;
	
	@Autowired
	public HistoryServiceI checkService;
	
	/*
	 * 历史证照查询
	 */
	@RequestMapping(value = "/scanninghistorylist", method = RequestMethod.GET)
	public String certShow() {
		return "certmanage/scanninghistorylist";
	}
		
	/*
	 * 证照资料查询
	 */
	@RequestMapping(value = "/scanninginfo", method = RequestMethod.GET)
	public String serviceShow() {
		return "certmanage/scanninginfo";
	}
	
	/*
	 * 证照信息统计分析
	 */
	@RequestMapping(value = "/zhengzhaomessagetf", method = RequestMethod.GET)
	public String zzmessagetf() {
		return "certmanage/zhengzhaomessagetf";
	}
	
	/*
	 * 证照信息统计分析
	 */
	@RequestMapping(value = "/zzmessagetftwo")
	public ModelAndView zzmessagetftwo(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView("/certmanage/zhengzhaomessagetftwo");
		mav.addObject("BUREAUGUID",request.getParameter("BUREAUGUID"));
		mav.addObject("PRONAME",request.getParameter("PRONAME"));
		mav.addObject("NIANFEN",request.getParameter("NIANFEN"));
		return mav;
	}
	
	/*
	 * 查看影像
	 */
	@RequestMapping(value = "/getView", method = RequestMethod.GET)
	public String getView(HttpServletRequest req) {
		String gid=req.getParameter("gid");
		String approvname=req.getParameter("APPROVNAME");
		String name=req.getParameter("NAME");
		String CHENGNUORIQI=req.getParameter("CHENGNUORIQI");
		String tel=req.getParameter("TEL");
		String PERSON=req.getParameter("PERSON");
		String VALIDITYPERIOD=req.getParameter("VALIDITYPERIOD");
		req.setAttribute("tel", tel);
		req.setAttribute("approvname", approvname);
		req.setAttribute("name", name);
		req.setAttribute("CHENGNUORIQI", CHENGNUORIQI);
		req.setAttribute("guid", gid);
		req.setAttribute("PERSON", PERSON);
		req.setAttribute("VALIDITYPERIOD", VALIDITYPERIOD);
		return "certmanage/scanningimageView";
	}
	
	/**
	 * 
	  * @MethodName: getViewByInstanceid
	  * @Description: 审批过程中查看附件页面查看可重用的证照
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return String    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年7月13日  上午10:19:47
	 */
	@RiseLog(operateName = "查看证照",operateType = "查看")
	@RequestMapping(value = "/getViewByInstanceid")
	public String getViewByInstanceid(@RequestParam String instanceId,@RequestParam String declareannexguid, Model model) {		
		model.addAttribute("instanceId", instanceId);
		model.addAttribute("declareannexguid", declareannexguid);
		return "certmanage/lookZhengzhao";
	}
	
	/**
	 * 
	  * @MethodName: zhengzhaoList
	  * @Description: 查找某个申请人针对某个材料可用的证照列表
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年7月13日  下午2:48:51
	 */
	@RequestMapping(value = "/zhengzhaoList")
	@ResponseBody
	public Map<String, Object> zhengzhaoList(@RequestParam String declareannexguid,@RequestParam String instanceId){

		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> zhengzhaoList;
		zhengzhaoList = checkService.getZhengzhaoByInstanceid(instanceId,declareannexguid);		
		
		map.put("rows", zhengzhaoList);
		return map;
	}
	
	/*
	 * 证照扫描列表
	 */
	@RequestMapping(value = "/selectData", method = RequestMethod.POST)
	@ResponseBody
	public String selectData(HttpServletRequest req) {
		String pageNo = req.getParameter("pager.pageNo");
		String pageSize = req.getParameter("pager.pageSize");
		String approveName = req.getParameter("approveName");
		String applyName = req.getParameter("applyName");
		String yujing = req.getParameter("yujing");
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager pager = new Pager();
		try {
			if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
				pageSize = "20";
				pageNo = "1";
			}
			pager.setPageNo(Integer.parseInt(pageNo));
			pager.setPageSize(Integer.parseInt(pageSize));
			Pager page = checkService.getPage(applyName, approveName, pager,yujing);
			for (Map e : page.getPageList()) {
				if (e.containsKey("DECLDATE")) {
					if (e.get("DECLDATE") != null) {
						String date = sdf.format(sdf.parse(e.get("DECLDATE")
								.toString()));
						e.remove(e);
						e.put("DECLDATE", date);
					}
				}
				if (e.containsKey("ADDDATE")) {
					if (e.get("ADDDATE") != null) {
						String date = sdf.format(sdf.parse(e.get("ADDDATE")
								.toString()));
						e.remove(e);
						e.put("ADDDATE", date);
					}
				}

			}
			map.put("pager.pageNo", page.getPageNo());
			map.put("pageSize", page.getPageSize());
			map.put("pager.totalRows", page.getTotalRows());
			map.put("rows", page.getPageList());
		
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}
	

	/*
	 * 证照资料查询
	 */
	@RequestMapping(value = "/select", method = RequestMethod.POST)
	@ResponseBody
	public String select(HttpServletRequest req) {
		String pageNo = req.getParameter("pager.pageNo");
		String pageSize = req.getParameter("pager.pageSize");
		String approveName = req.getParameter("approveName");//业务名称
		String applyName = req.getParameter("applyName");//申请人姓名
		String serviceCode = req.getParameter("ServiceCode");//业务编号
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Pager pager = new Pager();
		try {
			if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
				pageSize = "20";
				pageNo = "1";
			}
			pager.setPageNo(Integer.parseInt(pageNo));
			pager.setPageSize(Integer.parseInt(pageSize));
			Pager page = checkService.selectPage(applyName, approveName, pager,serviceCode);
			for (Map e : page.getPageList()) {
			  try{
				if (e.containsKey("ADDDATE")) {
					if (e.get("ADDDATE") != null) {
						String date = sdf.format(sdf.parse(e.get("ADDDATE")
								.toString()));
						e.remove(e);
						e.put("ADDDATE", date);
					}
				}if (e.containsKey("CHENGNUORIQI")) {
					if (e.get("CHENGNUORIQI") != null) {
						String date = sdf.format(sdf.parse(e
								.get("CHENGNUORIQI").toString()));
						e.remove(e);
						e.put("CHENGNUORIQI", date);
						
					}
				}
			  }catch(Exception e1){
					
				}
			}
			map.put("pager.pageNo", page.getPageNo());
			map.put("pageSize", page.getPageSize());
			map.put("pager.totalRows", page.getTotalRows());
			map.put("rows", page.getPageList());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}
	

	/*
	 * 证书图片显示
	 */
	@RequestMapping(value = "/read",method = RequestMethod.POST)
	@ResponseBody
	public String readImages(HttpServletRequest req,HttpServletResponse response) throws IOException {
			String instanceGuid=req.getParameter("gid");
			Person person = ThreadLocalHolder.getPerson();
			List<Map<String,Object>> list=checkService.getImage(instanceGuid,person);
			List<String> route=new ArrayList<String>();
			Map<String,Object> names=new HashMap<String,Object>();
			//暂时使用固定路径
			for(Map e:list){
				String r=e.get("REALFULLPATH").toString();
				String filename=e.get("FILENAME").toString();
				filename = filename.substring(0, filename.length()-4);
				String v=r.replaceAll("\\\\","/");
				v=v.substring(2, v.length());
				route.add(v);
				names.put(v, filename);

			}
			JSONObject json=new JSONObject();
			json.put("info", route);
			json.put("names", names);
			return json.toString();
			
		}
		
	
		@RequestMapping(value = "/view", method = RequestMethod.POST)
		@ResponseBody
		public void viewImages(HttpServletRequest req,HttpServletResponse rep) {
			String instanceGuid=req.getParameter("gid");
			rep.setContentType("image/jpeg");
			String str=checkService.getPhoto(instanceGuid,rep);
			try {
				//Image image = ImageIO.read(new File(rout));
				BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
				byte[] buffer = new byte[1024];
				int length = 0;
				OutputStream out = rep.getOutputStream();
				while ((length = bufferedInputStream.read(buffer)) > 0) {
					out.write(buffer, 0, length);
					out.flush();
				}
				bufferedInputStream.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		/**
		 * 发送信息预警
		 * @param req
		 * @return
		 */
		@RequestMapping(value = "/messageYuJing")
		public String messageYuJing(HttpServletRequest req) {
			String gid=req.getParameter("gid");
			req.setAttribute("guid", gid);
			String tel=req.getParameter("TEL");
			req.setAttribute("tel", tel);
			String daycount=req.getParameter("dayCount");
			req.setAttribute("dayCount", daycount);
			String vcotent = req.getParameter("vcotent");
			String name=req.getParameter("name");
			String code=req.getParameter("code");
			//短信机长号
			String oritel ="106573017777";
			//发送短信
			SendSms ta = new SendSms();
			String resmess ="发送成功";
		    int res = ta.smssend(tel, name+"您好；罗湖区网上行政服务大厅提醒您：您的编号为 '"+code+"' 的证照还有'"+daycount+"'天过期。",oritel);
				if(res >0){
				//成功发送短信，更新数据库表状态
					
				}else{
					resmess="发送失败，请稍候再操作";	
				}
			return resmess;
		}
		
		
		/*
		 * 证照信息统计分析按局统计
		 */
		@RequestMapping(value = "/zzmessagetongjifx", method = RequestMethod.POST)
		@ResponseBody
		public String zzmessagetongjifx(HttpServletRequest req) {
			String pageNo = req.getParameter("pager.pageNo");
			String pageSize = req.getParameter("pager.pageSize");
			String produceOrgan = req.getParameter("produceOrgan");
			String docTypeName = req.getParameter("docTypeName");
			String nianfen = req.getParameter("nianfen");
			Calendar a=Calendar.getInstance();
			String sysnianfen = a.get(Calendar.YEAR) + "";
			if(nianfen!=null && !"".equals(nianfen)){
				sysnianfen = nianfen;
			}
			Person person = ThreadLocalHolder.getPerson();
			Map<String, Object> map = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Pager pager = new Pager();

			//
			try {
				if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
					pageSize = "20";
					pageNo = "1";
				}
				pager.setPageNo(Integer.parseInt(pageNo));
				pager.setPageSize(Integer.parseInt(pageSize));
				Pager page = checkService.getPageZzTongJi(person,pager,produceOrgan,docTypeName,nianfen);
				tongjimap=page.getPageList();
				for (Map e : page.getPageList()) {
					if (e.containsKey("PRODUCEDATE")) {
						if (e.get("PRODUCEDATE") != null) {
							String date = sdf.format(sdf.parse(e.get("PRODUCEDATE")
									.toString()));
							e.remove(e);
							e.put("PRODUCEDATE", date);
						}
					}
					
				}
				map.put("pager.pageNo", page.getPageNo());
				map.put("pageSize", page.getPageSize());
				map.put("pager.totalRows", page.getTotalRows());
				map.put("rows", page.getPageList());
											
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			return jsonObject.toString();
			
		}
		
		
		/*
		 * 证照信息统计分析按事项统计
		 */
		@RequestMapping(value = "/zzmessagetongjitwo", method = RequestMethod.POST)
		@ResponseBody
		public String zzmessagetongjitwo(HttpServletRequest req) {
			
			String pageNo = req.getParameter("pager.pageNo");
			String pageSize = req.getParameter("pager.pageSize");
			String produceOrgan = req.getParameter("BUREAUGUID");
			String docTypeName = req.getParameter("PRONAME");
			String nianfen = req.getParameter("NIANFEN");
			Calendar a=Calendar.getInstance();
			String sysnianfen = a.get(Calendar.YEAR) + "";
			if(nianfen!=null && !"".equals(nianfen)){
				sysnianfen = nianfen;
			}
			Person person = ThreadLocalHolder.getPerson();
			Map<String, Object> map = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Pager pager = new Pager();
			//
			try {
				if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
					pageSize = "20";
					pageNo = "1";
				}
				pager.setPageNo(Integer.parseInt(pageNo));
				pager.setPageSize(Integer.parseInt(pageSize));
				Pager page = checkService
						.getPageZzTongJitwo(person,pager,produceOrgan,docTypeName,nianfen);
				tongjimap=page.getPageList();
				for (Map e : page.getPageList()) {
					if (e.containsKey("PRODUCEDATE")) {
						if (e.get("PRODUCEDATE") != null) {
							String date = sdf.format(sdf.parse(e.get("PRODUCEDATE").toString()));
							e.remove(e);
							e.put("PRODUCEDATE", date);
						}
					}
				}
				map.put("pager.pageNo", page.getPageNo());
				map.put("pageSize", page.getPageSize());
				map.put("pager.totalRows", page.getTotalRows());
				map.put("rows", page.getPageList());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(map);
			return jsonObject.toString();
		}
		
		/*
		 * 加载出证部门
		 * 
		 */
		@RequestMapping(value="/wbjSelect",method = RequestMethod.GET)
		@ResponseBody
		public String wbjSelect(){
			List<Map<String,Object>> spmList = departmentService.findAll();
			List<Map<String,Object>> listtemp=new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map:spmList){
				Map<String,Object> maptemp=new HashMap<String, Object>();
				maptemp.put("value", map.get("value"));
				maptemp.put("key", map.get("KEY"));

				listtemp.add(maptemp);
			}
			JSONArray json = JSONArray.fromObject(listtemp);
			//map.put("list", json.toString());
			return json.toString();
		}
		
		
		
		/*
		 * 加载证照类型
		 * 
		 */
		@RequestMapping(value="/zztypeSelect",method = RequestMethod.GET)
		@ResponseBody
		public String zztypeSelect(HttpServletRequest req){
			String produceOrgan= req.getParameter("produceOrgan");
			List<Map<String,Object>> spmList = departmentService.findAllZZType(produceOrgan);
			List<Map<String,Object>> listtemp=new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map:spmList){
				Map<String,Object> maptemp=new HashMap<String, Object>();
				maptemp.put("key", map.get("KEY"));
				maptemp.put("value", map.get("value"));
				listtemp.add(maptemp);
			}
			JSONArray json = JSONArray.fromObject(listtemp);
			//map.put("list", json.toString());
			return json.toString();
		}
		
		
		/*
		 * 获得线性图List,按照局来统计
		 */
		@RequestMapping(value = "/xianxingtuList", method = RequestMethod.POST)
		@ResponseBody
		public String xianxingtuList(HttpServletRequest req) {
			//统计图
			StringBuilder param=new StringBuilder();
			List<Map<String, Object>> mapList= null;
			mapList=tongjimap;

			int i=0;
			JSONObject  json= new JSONObject();
			param.append("[");
			for(Map<String,Object> e : mapList){
					param.append("{'name':'"+e.get("PRODUCEORGAN")+"',");
					param.append("'data':["+e.get("YIYUE")+", "+e.get("ERYUE")+", "+e.get("SANYUE")+","+e.get("SIYUE")+", "+e.get("WUYUE")+", "+e.get("LIUYUE")+", "+e.get("QIYUE")+", "+e.get("BAYUE")+", "+e.get("JIUYUE")+", "+e.get("SHIYUE")+", "+e.get("SHIYIYUE")+", "+e.get("SHIERYUE")+"]}");
					if(mapList.size()-i>1){
						param.append(",");
					}
				i++;
			}
			param.append("]");
			json.put("tongjitu", param.toString());
			return json.toString();
		}
		
		/*
		 * 获得线性图List,按照项目来统计
		 */
		@RequestMapping(value = "/xianxingtuListtwo", method = RequestMethod.POST)
		@ResponseBody
		public String xianxingtuListtwo(HttpServletRequest req) {
			//统计图
			StringBuilder param=new StringBuilder();
			List<Map<String, Object>> mapList= null;
			mapList=tongjimap;

			int i=0;
			JSONObject  json= new JSONObject();
			param.append("[");
			for(Map<String,Object> e : mapList){
					param.append("{'name':'"+e.get("DOCNAME")+"',");
					param.append("'data':["+e.get("YIYUE")+", "+e.get("ERYUE")+", "+e.get("SANYUE")+","+e.get("SIYUE")+", "+e.get("WUYUE")+", "+e.get("LIUYUE")+", "+e.get("QIYUE")+", "+e.get("BAYUE")+", "+e.get("JIUYUE")+", "+e.get("SHIYUE")+", "+e.get("SHIYIYUE")+", "+e.get("SHIERYUE")+"]}");
					if(mapList.size()-i>1){
						param.append(",");
					}
				i++;
			}
			param.append("]");
			json.put("tongjitu", param.toString());
			return json.toString();
		}
		
		// 导出当前页
		@RequestMapping(value = "/exportData", method = RequestMethod.GET)
		@ResponseBody
		public void exportData(HttpServletRequest req,HttpServletResponse response)throws Exception {
			int i=0;
			String produceOrgan=req.getParameter("produceOrgan");
			String docTypeName=req.getParameter("docTypeName");
			String nianfen=req.getParameter("nianfen");
			Person person = ThreadLocalHolder.getPerson();
			List<Map<String, Object>> mapList= new ArrayList<Map<String, Object>>();
			mapList=checkService.getDaoChuList(produceOrgan,docTypeName,nianfen,person);
			
			 // 第一步，创建一个webbook，对应一个Excel文件  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
 	        HSSFSheet sheet = wb.createSheet("证照统计表");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        HSSFRow row = sheet.createRow((int) 0);  
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	  
	        HSSFCell cell = row.createCell((short) 0);  
	        cell.setCellValue("发证单位");  
	        cell.setCellStyle(style);  
	        //cell = row.createCell((short) 1);  
	        //cell.setCellValue("证照名称");  
	        //cell.setCellStyle(style);  
	        /*cell = row.createCell((short) 2);  
	        cell.setCellValue("证照类型"); */ 
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("一月");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("二月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("三月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("一季度合计");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("四月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("五月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("六月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("二季度合计");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 9);  
	        cell.setCellValue("七月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 10);  
	        cell.setCellValue("八月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 11);  
	        cell.setCellValue("九月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 12);  
	        cell.setCellValue("三季度合计");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 13);  
	        cell.setCellValue("十月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 14);  
	        cell.setCellValue("十一月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 15);  
	        cell.setCellValue("十二月");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 16);  
	        cell.setCellValue("四季度合计");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 17);  
	        cell.setCellValue("年度合计");  
	        cell.setCellStyle(style);
	        
	        
	     // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  

	        for(Map<String,Object> e : mapList){
	        	row = sheet.createRow(i + 1);  
	        	row.createCell((short) 0).setCellValue(e.get("PRODUCEORGAN").toString());
	        	//row.createCell((short) 1).setCellValue(e.get("DOCTYPENAME").toString());
	        	row.createCell((short) 1).setCellValue(Double.parseDouble(e.get("YIYUE").toString()));
	        	row.createCell((short) 2).setCellValue(Double.parseDouble(e.get("ERYUE").toString()));
	        	row.createCell((short) 3).setCellValue(Double.parseDouble(e.get("SANYUE").toString()));
	        	row.createCell((short) 4).setCellValue(Double.parseDouble(e.get("YIJIDU").toString()));
	        	row.createCell((short) 5).setCellValue(Double.parseDouble(e.get("SIYUE").toString()));
	        	row.createCell((short) 6).setCellValue(Double.parseDouble(e.get("WUYUE").toString()));
	        	row.createCell((short) 7).setCellValue(Double.parseDouble(e.get("LIUYUE").toString()));
	        	row.createCell((short) 8).setCellValue(Double.parseDouble(e.get("ERJIDU").toString()));
	        	row.createCell((short) 9).setCellValue(Double.parseDouble(e.get("QIYUE").toString()));
	        	row.createCell((short) 10).setCellValue(Double.parseDouble(e.get("BAYUE").toString()));
	        	row.createCell((short) 11).setCellValue(Double.parseDouble(e.get("JIUYUE").toString()));
	        	row.createCell((short) 12).setCellValue(Double.parseDouble(e.get("SANJIDU").toString()));
	        	row.createCell((short) 13).setCellValue(Double.parseDouble(e.get("SHIYUE").toString()));
	        	row.createCell((short) 14).setCellValue(Double.parseDouble(e.get("SHIYIYUE").toString()));
	        	row.createCell((short) 15).setCellValue(Double.parseDouble(e.get("SHIERYUE").toString()));
	        	row.createCell((short) 16).setCellValue(Double.parseDouble(e.get("SIJIDU").toString()));
	        	row.createCell((short) 17).setCellValue(Double.parseDouble(e.get("YEARTOTAL").toString()));
	        	i++;
	        }
	        
	     // 第六步，将文件存到指定位置  
	        try  
	        {  
	        	/*  FileOutputStream fout = new FileOutputStream("D:/证照信息统计分析.xls");  
	            wb.write(fout);  
	   
	            fout.close(); */ 
	        	String filename = "zzxxtjb.xls";//设置下载时客户端Excel的名称
	        	//filename = Util.encodeFilename(filename, req);   
	            response.setContentType("application/vnd.ms-excel");   
	            response.setHeader("Content-disposition", "attachment;filename=" + filename);   
	            OutputStream ouputStream = response.getOutputStream();   
	            wb.write(ouputStream);   
	            ouputStream.flush();   
	            ouputStream.close();   
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        }  

		}
		
		//选择年份准备数据
		@RequestMapping(value = "/nianfenSelect", method = RequestMethod.GET)
		@ResponseBody
		public String nianfenSelect(HttpServletRequest req){
			Calendar a=Calendar.getInstance();
			int sysnianfen = a.get(Calendar.YEAR);
			int minnianfen=2014;
			
			List<Map<String,Object>> listnianfenmap=new ArrayList<Map<String,Object>>();
			Map<String,Object> nianfenmap=new HashMap<String, Object>();
			nianfenmap.put("key", minnianfen+"");
			nianfenmap.put("value",minnianfen+"");
			listnianfenmap.add(nianfenmap);
			while(minnianfen<sysnianfen){
				Map<String,Object> nianfenmap2=new HashMap<String, Object>();
				nianfenmap2.put("key",(minnianfen+1)+"");
				nianfenmap2.put("value",(minnianfen+1)+"");
				listnianfenmap.add(nianfenmap2);
				minnianfen++;
			}
			JSONArray json = JSONArray.fromObject(listnianfenmap);
			return json.toString();
			
		}
		
		
		/*
		 * 逐个显示图片
		 */
		@RequestMapping(value = "/readeveryone")
		@ResponseBody
		public void readeveryone(HttpServletRequest req,HttpServletResponse response) throws IOException {
				String instanceGuid=req.getParameter("gid");
				int geshu=Integer.parseInt(req.getParameter("geshu"));
				Person person = ThreadLocalHolder.getPerson();
				List<Map<String,Object>> list=checkService.getImage(instanceGuid,person);


					String r = list.get(geshu).get("REALFULLPATH").toString();

					String v=r.replaceAll("\\\\","/");

					String filenamet=v.substring(2, v.length());
					File file = new File(filenamet);  
					if (file.exists() && file.canRead()) {  
						FileInputStream inputStream = new FileInputStream(file);  
						byte[] data = new byte[(int) file.length()];  
						int length = inputStream.read(data);  
						inputStream.close(); 
						response.setContentType("image/png");
						OutputStream stream = response.getOutputStream();  
						stream.write(data);  
						stream.flush();  
						stream.close();
					} 
				
			}
	
}
