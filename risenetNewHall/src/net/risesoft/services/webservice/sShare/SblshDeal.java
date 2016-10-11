package net.risesoft.services.webservice.sShare;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.restlet.data.CharacterSet;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.springframework.stereotype.Service;

import net.risesoft.utils.base.ICodeMapUtil;
import net.sf.json.JSONSerializer;

@Service
public class SblshDeal {
	@Resource
	private ICodeMapUtil codeMapUtil;
	
	/**
	 * 通过市接口获取申办流水号
	 * @param sxbm 事项编码
	 * @return
	 */
	public String getSblshBySxbm(String sxbm){
		String sblsh = "";
		try{
			JsonRepresentation jr = null;			
			ClientResource cr = new ClientResource("http://203.91.57.66/shenzhen");
//			ClientResource cr = new ClientResource("http://wsbs.sz.gov.cn/shenzhen");
			JSONObject f = new JSONObject();
			f.put("access_token", "qU7hhSNI5j5oCKxDvA3ZN7aXV41SK/8j");
			f.put("sxbm", sxbm);				
			cr.setReference("http://203.91.57.66/shenzhen"+"/c/api.businessIndex/createSerialNum");
//			cr.setReference("http://wsbs.sz.gov.cn/shenzhen"+"/c/api.businessIndex/createSerialNum");
			// 这个用来解决411问题
			cr.setRequestEntityBuffering(true);
			// 返回数据封装
			Representation data = cr.post(f);							
			// 设置编码，解决转化乱码问题
			data.setCharacterSet(CharacterSet.UTF_8);
			jr = new JsonRepresentation(data);
			JSONObject backjson = jr.getJsonObject();	
			System.out.println(backjson);
									
			if(backjson.getInt("state")==0){
				System.out.println(backjson.getString("message"));
//				sblsh=codeMapUtil.getDeclaresnByBureauGuid("{AC100A59-FFFF-FFFF-B039-EAFC00000012}");
			}else{
				net.sf.json.JSONObject backjson1 = (net.sf.json.JSONObject) JSONSerializer
				.toJSON(backjson.get("data").toString());
				sblsh = backjson1.getString("sblsh");
			}
		}catch(Exception ex){
//			sblsh=codeMapUtil.getDeclaresnByBureauGuid("{AC100A59-FFFF-FFFF-B039-EAFC00000012}");
			ex.printStackTrace();
		}
		return sblsh;
	}
}
