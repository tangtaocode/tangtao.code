package net.risesoft.services.webservice.sShare;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import exchange.adapter.base.IRequest;
import exchange.adapter.currency.AdapterComposite;
import exchange.adapter.currency.ResResponse;
import exchange.client.common.currency.DateTime;
import exchange.entity.apprequest.Condition;
import exchange.entity.apprequest.ConditionObject;
import exchange.entity.apprequest.Conditions;
import exchange.entity.businessdata.BusinessData;
import exchange.entity.businessdata.DataSet;
import exchange.entity.businessdata.RecordData;
import exchange.entity.businessdata.UnitData;
import exchange.entity.resresult.ResResult;


@Controller
public class Request {
	
	/**
	 * 建设用地规划许可证
	 * @param indexValue 证件编号 BA-2010-0121
	 * @return
	 */
	public Map GetJSYDGHXKZDataById(String indexValue){
		Map map = new HashMap();//存放返回值		
		String appId="2910342APPSZ0120120217152956380244172";
		String ipAdd="10.210.133.171:8080";
		String resId="2910342APPSZ012012021715295638024417220130929140921480787";
		String orgId="SZ011214";
		String resCode = "29105R203SZ0120130428150047965130848";
		String resName="建设用地规划许可证";
		String indexName="LU_PERMISSION_NO";
		String operation="=";
		
		System.out.println("Start request"+DateTime.getNowTime()+"###################");
		//引用api包中AdapterComposite类创建获取工厂，入参为APPREQUEST_FETCHRESDATA
		AdapterComposite composite = new AdapterComposite("APPREQUEST_FETCHRESDATA");
		//创建请求对象oRequest

		IRequest oRequest = composite.createRequest(appId,ipAdd);

		//开始构造获取对象
		//创建ConditionObject节点，此节点表示获取请求的相关信息
		ConditionObject oConditionObj=new ConditionObject();
		//设置ResCode节点
		oConditionObj.setResCode(resId);	//资源编号ID
		//设置OrgId节点
		oConditionObj.setOrgId(orgId);	//提供方单位ID
		//设置ResName节点
		oConditionObj.setResName(resName);	//资源名称
		oConditionObj.setResCode(resCode);//资源code
		//设置IsAllSelect节点
		oConditionObj.setIsAllSelect(true);//是否获取全部获取指标
		
		//设置ResComment节点
		oConditionObj.setResComment("备注信息");
		
	    //创建Conditions节点，此节点表示所有查询指标项信息
	    Conditions oConditions = new Conditions();
	    //创建Condition节点，此节点表示单个查询指标项信息
	    Condition oCondition=new Condition();
	    //设置ResIndexName节点
	    oCondition.setResIndexName(indexName);
	    //设置Operation节点
	    oCondition.setOperation(operation);
	    //设置RightValue节点
	    oCondition.setRightValue(indexValue);
	    oConditions.addCondition(oCondition);
	    oConditionObj.setConditions(oConditions);  
	     
	     //* 构造获取请求对象AppRequest
	     //* 业务系统编号存放在adapter.xml文件中
	     //* 请求编号由开发包自己构造，具体格式为：业务系统ID+yyyyMMddHHmmssSSS+3位随机码
	     //* 下面两种方式根据不同情况进行选择
	     
	    oRequest.setCondition(oConditionObj);
	    //若有其他业务系统ID，可以调用
	    //oRequest.setCondition(oConditionObj, "业务系统ID");
//	    oRequest.setCondition(oConditionObj, "2910952APPSZ0120110527150351924366630");
	    try
	    {
	    	//开始发送获取请求
	    	ResResponse rsbase = (ResResponse)oRequest.request();
	    	System.out.println("Request Over"+DateTime.getNowTime()+"###################");
	    	
	    	 // 返回对象有可能为空对象，说明中间有环节出错
	    	 // 若成功返回，可以从返回字符串中提取相关信息
	    	 
	    	try{
	    		//获取结果集对象ResResult
	    		ResResult rr = rsbase.getResResult();
	    		//获取EnvelopeId，可以用来查询日志
	    		rsbase.getEnvelopeId();
	    		//获取对象类型，获取请求成功返回有两种类型：1.ErrorMessage错误信息类型      2.ResResult获取的结果集类型
		    	String sBodyType = rsbase.getOBodyType();
		    	//若返回ErrorMessage错误信息类型
		    	if(sBodyType.equals("ErrorMessage")){
		    	
		    		 //* 则可以输出相关错误信息，错误信息为：错误编号+错误内容
		    		 //* getErrorInfo()只获取错误内容
		    		 //* getErrorNum()只获取错误编号
		    		 
		    		System.out.println(rsbase.getError());
		    		map.put("state", "2");//调用接口错误
		    		map.put("ErrorMessage", rsbase.getError());//错误信息
		    	}else{
		    		map.put("state", "1");//正常
		    		try {
						
						// * 如果包含文件，可以获取一个文件路径
						// * 通过这个路径可以调用isFinishTransfer()判断文件是否传输完毕
						
						String fileurl = rsbase.getFileUrl();
						System.out.println("判断文件是否传输完毕的文件路径..........\n" + fileurl);
						
						 // 文件下载路径，不包含文件名 因为一次获取请求可能包含多个文件
						 
						String filepath = rsbase.getFilePath();
						// 此节点中存放了这次获取中所有的文件的文件名
						String[] fileLists = rr.getResultInfo().getAttachment().getFileList().getFileList();
						// 遍历文件名信息，获取文件下载路径
//						for (int i = 0; i < fileLists.length; i++) {
//							System.out.println("下载文件" + i + "的地址为：\n" + filepath + ValueGeter.getFileName(fileLists[i]));
//						}
					} catch (Exception e) {
						//如果没有文件直接去获取文件下载地址会抛出异常
						System.out.println("没有文件下载");
					}
					//获取国标对象BusinessData
		    		BusinessData bd = rsbase.getOBusinessData();
		    		//获取结果集
		    		DataSet ds = bd.getDataSet();
			    	//开始遍历结果集进行展示
			    	RecordData[] rds = ds.getRecordData();
			    	
					
			    	//遍历获取的每行记录
			    	for(int i=0;i<rds.length;i++)
			    	{
			    		UnitData[] ud = rds[i].getUnitData();
			    		//遍历获取的每行的每个字段信息
			    		for(int j=0;j<ud.length;j++){
			    			String oValue = ud[j].getUnitValue().toString();
			    			String[] sValue = oValue.split("\\>");
			    			try{
			    				int endIndex = sValue[2].indexOf("<");
			    				oValue = sValue[2].substring(0, endIndex);
			    			}catch(Exception e){
			    				e.printStackTrace();
			    				oValue = "";
			    			}
			    			map.put(ud[j].getUnitEnglishName(), oValue);
			    			/*
			    			System.out.println("第" + i + "行\n"
			    					+ "指标ID：" + ud[j].getUnitIDName() 
			    					+ "\n指标中文名：" + ud[j].getUnitDisplayName()
			    					+ "\n指标英文名：" + ud[j].getUnitEnglishName()
			    					+ "\n指标对应值：" + oValue);*/
			    		}			   
			    	}
			    
		    	}
	    	}catch(NullPointerException e){
	    		map.put("state", "2");//调用接口错误
	    		map.put("ErrorMessage", "传输的对象有错误！");//错误信息	  
	    	}
	    }
	    catch(Exception ex)
	    {
	    	map.put("state", "2");//调用接口错误
    		map.put("ErrorMessage", "接口连接超时，无法请求！");//错误信息	  
	    	ex.printStackTrace();
	    } 
	    System.out.println("------end--------->"+new Date().getTime());
	    return map;
	}
	
	/**
	 * 建设工程规划许可证
	 * @param indexValue 证件编号 LG-2013-0036
	 * @return
	 */
	public Map GetJSGCGHXKZDataById(String indexValue){
		Map map = new HashMap();//存放返回值
		String appId="2910342APPSZ0120120217152956380244172";
		String ipAdd="10.210.133.171:8080";
		String resId="2910342APPSZ012012021715295638024417220130929142728692742";
		String orgId="SZ011214";
		String resCode = "29105R203SZ0120130428150143936468062";
		String resName="建设工程规划许可证";
		String indexName="LICENSE_NO";
		String operation="=";
		
		System.out.println("Start request"+DateTime.getNowTime()+"###################");
		//引用api包中AdapterComposite类创建获取工厂，入参为APPREQUEST_FETCHRESDATA
		AdapterComposite composite = new AdapterComposite("APPREQUEST_FETCHRESDATA");
		//创建请求对象oRequest

		IRequest oRequest = composite.createRequest(appId,ipAdd);

		//开始构造获取对象
		//创建ConditionObject节点，此节点表示获取请求的相关信息
		ConditionObject oConditionObj=new ConditionObject();
		//设置ResCode节点
		oConditionObj.setResCode(resId);	//资源编号ID
		//设置OrgId节点
		oConditionObj.setOrgId(orgId);	//提供方单位ID
		//设置ResName节点
		oConditionObj.setResName(resName);	//资源名称
		oConditionObj.setResCode(resCode);//资源code
		//设置IsAllSelect节点
		oConditionObj.setIsAllSelect(true);//是否获取全部获取指标
		
		//设置ResComment节点
		oConditionObj.setResComment("备注信息");
		
	    //创建Conditions节点，此节点表示所有查询指标项信息
	    Conditions oConditions = new Conditions();
	    //创建Condition节点，此节点表示单个查询指标项信息
	    Condition oCondition=new Condition();
	    //设置ResIndexName节点
	    oCondition.setResIndexName(indexName);
	    //设置Operation节点
	    oCondition.setOperation(operation);
	    //设置RightValue节点
	    oCondition.setRightValue(indexValue);
	    oConditions.addCondition(oCondition);
	    oConditionObj.setConditions(oConditions); 
	     
	     //* 构造获取请求对象AppRequest
	     //* 业务系统编号存放在adapter.xml文件中
	     //* 请求编号由开发包自己构造，具体格式为：业务系统ID+yyyyMMddHHmmssSSS+3位随机码
	     //* 下面两种方式根据不同情况进行选择
	     
	    oRequest.setCondition(oConditionObj);
	    //若有其他业务系统ID，可以调用
	    //oRequest.setCondition(oConditionObj, "业务系统ID");
//	    oRequest.setCondition(oConditionObj, "2910952APPSZ0120110527150351924366630");
	    try
	    {
	    	//开始发送获取请求
	    	ResResponse rsbase = (ResResponse)oRequest.request();
	    	System.out.println("Request Over"+DateTime.getNowTime()+"###################");
	    	
	    	 // 返回对象有可能为空对象，说明中间有环节出错
	    	 // 若成功返回，可以从返回字符串中提取相关信息
	    	 
	    	try{
	    		//获取结果集对象ResResult
	    		ResResult rr = rsbase.getResResult();
	    		//获取EnvelopeId，可以用来查询日志
	    		rsbase.getEnvelopeId();
	    		//获取对象类型，获取请求成功返回有两种类型：1.ErrorMessage错误信息类型      2.ResResult获取的结果集类型
		    	String sBodyType = rsbase.getOBodyType();
		    	//若返回ErrorMessage错误信息类型
		    	if(sBodyType.equals("ErrorMessage")){
		    	
		    		 //* 则可以输出相关错误信息，错误信息为：错误编号+错误内容
		    		 //* getErrorInfo()只获取错误内容
		    		 //* getErrorNum()只获取错误编号
		    		map.put("state", "2");//调用接口错误
		    		map.put("ErrorMessage", rsbase.getError());//错误信息
		    		//System.out.println(rsbase.getError());
		    	}else{
		    		map.put("state", "1");//正常
		    		try {
						
						// * 如果包含文件，可以获取一个文件路径
						// * 通过这个路径可以调用isFinishTransfer()判断文件是否传输完毕
						
						String fileurl = rsbase.getFileUrl();
						System.out.println("判断文件是否传输完毕的文件路径..........\n" + fileurl);
						
						 // 文件下载路径，不包含文件名 因为一次获取请求可能包含多个文件
						 
						String filepath = rsbase.getFilePath();
						// 此节点中存放了这次获取中所有的文件的文件名
						String[] fileLists = rr.getResultInfo().getAttachment().getFileList().getFileList();
						// 遍历文件名信息，获取文件下载路径
//						for (int i = 0; i < fileLists.length; i++) {
//							System.out.println("下载文件" + i + "的地址为：\n" + filepath + ValueGeter.getFileName(fileLists[i]));
//						}
					} catch (Exception e) {
						//如果没有文件直接去获取文件下载地址会抛出异常
						System.out.println("没有文件下载");
					}
					//获取国标对象BusinessData
		    		BusinessData bd = rsbase.getOBusinessData();
		    		//获取结果集
		    		DataSet ds = bd.getDataSet();
			    	//开始遍历结果集进行展示
			    	RecordData[] rds = ds.getRecordData();
			    	
					
			    	//遍历获取的每行记录
			    	for(int i=0;i<rds.length;i++)
			    	{
			    		UnitData[] ud = rds[i].getUnitData();
			    		//遍历获取的每行的每个字段信息
			    		for(int j=0;j<ud.length;j++){
			    			String oValue = ud[j].getUnitValue().toString();
			    			String[] sValue = oValue.split("\\>");
			    			try{
			    				int endIndex = sValue[2].indexOf("<");
			    				oValue = sValue[2].substring(0, endIndex);
			    			}catch(Exception e){
			    				e.printStackTrace();
			    				oValue = "";
			    			}
			    			map.put(ud[j].getUnitEnglishName(), oValue);
			    			/*
			    			System.out.println("第" + i + "行\n"
			    					+ "指标ID：" + ud[j].getUnitIDName() 
			    					+ "\n指标中文名：" + ud[j].getUnitDisplayName()
			    					+ "\n指标英文名：" + ud[j].getUnitEnglishName()
			    					+ "\n指标对应值：" + oValue);*/
			    		}			   
			    	}
			    
		    	}
	    	}catch(NullPointerException e){
	    		map.put("state", "2");//调用接口错误
	    		map.put("ErrorMessage", "传输的对象有错误！");//错误信息	
	    	}
	    }
	    catch(Exception ex)
	    {	    	
    		map.put("state", "2");//调用接口错误
    		map.put("ErrorMessage", "接口连接超时，无法请求！");//错误信息
	    	ex.printStackTrace();
	    	
	    } 
	    System.out.println("------end--------->"+new Date().getTime());
	    return map;
	}
	
	/**
	 * 营业许可证
	 * @param indexValue 组织机构代码 192197759
	 * @return
	 */
	public Map GetYYXKZDataById(String indexValue){
		Map map = new HashMap();//存放返回值
		String appId="2910342APPSZ0120120217152956380244172";
		String ipAdd="10.210.133.171:8080";
		String resId="2910342APPSZ012012021715295638024417220131012165020694360";
		String orgId="SZ011218";
		String resCode = "291083203SZ0120110725165458906802033VZgk";

		String resName="市场经济主体基本信息";
		String indexName="EntOrgCode";//组织机构代码

		String operation="=";
		
		System.out.println("Start request"+DateTime.getNowTime()+"###################");
		//引用api包中AdapterComposite类创建获取工厂，入参为APPREQUEST_FETCHRESDATA
		AdapterComposite composite = new AdapterComposite("APPREQUEST_FETCHRESDATA");
		//创建请求对象oRequest

		IRequest oRequest = composite.createRequest(appId,ipAdd);

		//开始构造获取对象
		//创建ConditionObject节点，此节点表示获取请求的相关信息
		ConditionObject oConditionObj=new ConditionObject();
		//设置ResCode节点
		oConditionObj.setResCode(resId);	//资源编号ID
		//设置OrgId节点
		oConditionObj.setOrgId(orgId);	//提供方单位ID
		//设置ResName节点
		oConditionObj.setResName(resName);	//资源名称
		oConditionObj.setResCode(resCode);//资源code
		//设置IsAllSelect节点
		oConditionObj.setIsAllSelect(true);//是否获取全部获取指标
		
		//设置ResComment节点
		oConditionObj.setResComment("备注信息");
		
	    //创建Conditions节点，此节点表示所有查询指标项信息
	    Conditions oConditions = new Conditions();
	    //创建Condition节点，此节点表示单个查询指标项信息
	    Condition oCondition=new Condition();
	    //设置ResIndexName节点
	    oCondition.setResIndexName(indexName);
	    //设置Operation节点
	    oCondition.setOperation(operation);
	    //设置RightValue节点
	    oCondition.setRightValue(indexValue);
	    oConditions.addCondition(oCondition);
	    oConditionObj.setConditions(oConditions); 
	     
	     //* 构造获取请求对象AppRequest
	     //* 业务系统编号存放在adapter.xml文件中
	     //* 请求编号由开发包自己构造，具体格式为：业务系统ID+yyyyMMddHHmmssSSS+3位随机码
	     //* 下面两种方式根据不同情况进行选择
	     
	    oRequest.setCondition(oConditionObj);
	    //若有其他业务系统ID，可以调用
	    //oRequest.setCondition(oConditionObj, "业务系统ID");
//	    oRequest.setCondition(oConditionObj, "2910952APPSZ0120110527150351924366630");
	    try
	    {
	    	//开始发送获取请求
	    	ResResponse rsbase = (ResResponse)oRequest.request();
	    	System.out.println("Request Over"+DateTime.getNowTime()+"###################");
	    	
	    	 // 返回对象有可能为空对象，说明中间有环节出错
	    	 // 若成功返回，可以从返回字符串中提取相关信息
	    	 
	    	try{
	    		//获取结果集对象ResResult
	    		ResResult rr = rsbase.getResResult();
	    		//获取EnvelopeId，可以用来查询日志
	    		rsbase.getEnvelopeId();
	    		//获取对象类型，获取请求成功返回有两种类型：1.ErrorMessage错误信息类型      2.ResResult获取的结果集类型
		    	String sBodyType = rsbase.getOBodyType();
		    	//若返回ErrorMessage错误信息类型
		    	if(sBodyType.equals("ErrorMessage")){
		    	
		    		 //* 则可以输出相关错误信息，错误信息为：错误编号+错误内容
		    		 //* getErrorInfo()只获取错误内容
		    		 //* getErrorNum()只获取错误编号
		    		map.put("state", "2");//调用接口错误
		    		map.put("ErrorMessage", rsbase.getError());//错误信息
		    		//System.out.println(rsbase.getError());
		    	}else{
		    		map.put("state", "1");//正常
		    		try {
						
						// * 如果包含文件，可以获取一个文件路径
						// * 通过这个路径可以调用isFinishTransfer()判断文件是否传输完毕
						
						String fileurl = rsbase.getFileUrl();
						System.out.println("判断文件是否传输完毕的文件路径..........\n" + fileurl);
						
						 // 文件下载路径，不包含文件名 因为一次获取请求可能包含多个文件
						 
						String filepath = rsbase.getFilePath();
						// 此节点中存放了这次获取中所有的文件的文件名
						String[] fileLists = rr.getResultInfo().getAttachment().getFileList().getFileList();
						// 遍历文件名信息，获取文件下载路径
//						for (int i = 0; i < fileLists.length; i++) {
//							System.out.println("下载文件" + i + "的地址为：\n" + filepath + ValueGeter.getFileName(fileLists[i]));
//						}
					} catch (Exception e) {
						//如果没有文件直接去获取文件下载地址会抛出异常
						System.out.println("没有文件下载");
					}
					//获取国标对象BusinessData
		    		BusinessData bd = rsbase.getOBusinessData();
		    		//获取结果集
		    		DataSet ds = bd.getDataSet();
			    	//开始遍历结果集进行展示
			    	RecordData[] rds = ds.getRecordData();
			    	
					
			    	//遍历获取的每行记录
			    	for(int i=0;i<rds.length;i++)
			    	{
			    		UnitData[] ud = rds[i].getUnitData();
			    		//遍历获取的每行的每个字段信息
			    		for(int j=0;j<ud.length;j++){
			    			String oValue = ud[j].getUnitValue().toString();
			    			String[] sValue = oValue.split("\\>");
			    			try{
			    				int endIndex = sValue[2].indexOf("<");
			    				oValue = sValue[2].substring(0, endIndex);
			    			}catch(Exception e){
			    				e.printStackTrace();
			    				oValue = "";
			    			}
			    			map.put(ud[j].getUnitEnglishName(), oValue);
			    			/*
			    			System.out.println("第" + i + "行\n"
			    					+ "指标ID：" + ud[j].getUnitIDName() 
			    					+ "\n指标中文名：" + ud[j].getUnitDisplayName()
			    					+ "\n指标英文名：" + ud[j].getUnitEnglishName()
			    					+ "\n指标对应值：" + oValue);*/
			    		}			   
			    	}
			    
		    	}
	    	}catch(NullPointerException e){
	    		map.put("state", "2");//调用接口错误
	    		map.put("ErrorMessage", "传输的对象有错误！");//错误信息	
	    	}
	    }
	    catch(Exception ex)
	    {	    	
    		map.put("state", "2");//调用接口错误
    		map.put("ErrorMessage", "接口连接超时，无法请求！");//错误信息
	    	ex.printStackTrace();
	    	
	    } 
	    System.out.println("------end--------->"+new Date().getTime());
	    return map;
	}

}
