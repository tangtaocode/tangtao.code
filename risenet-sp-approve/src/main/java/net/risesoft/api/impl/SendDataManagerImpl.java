package net.risesoft.api.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Service;

import net.risesoft.api.SendDataManager;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.util.RisesoftUtil;


@Service(value="sendDataManager")
public class SendDataManagerImpl implements SendDataManager{

	@Override
	public void sendShenpiguocheng(String sblsh_short) {
		// TODO Auto-generated method stub
		/**x向市统一申办平台推送数据*/
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, RisesoftUtil.charset);
		PostMethod method = new PostMethod();
		try {
			//String url = ConstUtils.SZSURL+"service/httpapi/core/token/getXmlToken";
			String url = RisesoftCommonUtil.serviceURL+"/services/rest/szswj/shenpichuli/sendGuoCheng";
			method.setPath(url);
			method.addParameter("sblsh_short", sblsh_short);
			//method.addParameter("xmlStr", xmlStr.toString());
			int code = client.executeMethod(method);
			//String response = new String(method.getResponseBodyAsString().getBytes(RisesoftUtil.charset), RisesoftUtil.charset);
			if (code == HttpStatus.SC_OK) {
				System.out.println("推送数据成功");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		      method.releaseConnection();
	    }
	}

	@Override
	public void sendBanjie(String sblsh_short) {
		// TODO Auto-generated method stub
		/**x向市统一申办平台推送数据*/
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, RisesoftUtil.charset);
		PostMethod method = new PostMethod();
		try {
			//String url = ConstUtils.SZSURL+"service/httpapi/core/token/getXmlToken";
			String url = RisesoftCommonUtil.serviceURL+"/services/rest/szswj/shenpijieguo/sendJieGuo";
			method.setPath(url);
			method.addParameter("sblsh_short", sblsh_short);
			//method.addParameter("xmlStr", xmlStr.toString());
			int code = client.executeMethod(method);
			if (code == HttpStatus.SC_OK) {
				System.out.println("推送数据成功");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		      method.releaseConnection();
	    }
	}

	@Override
	public void sendTebieChengxu(int type,String sblsh_short,String xh) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, RisesoftUtil.charset);
		PostMethod method = new PostMethod();
		try {
			//String url = ConstUtils.SZSURL+"service/httpapi/core/token/getXmlToken";
			String url = RisesoftCommonUtil.serviceURL+"/services/rest/szswj/tebiechengxu/sendTebiechengxu";
			method.setPath(url);
			method.addParameter("type", type+"");
			method.addParameter("sblsh_short", sblsh_short);
			method.addParameter("xh", xh);
			int code = client.executeMethod(method);
			if (code == HttpStatus.SC_OK) {
				System.out.println("推送数据成功");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		      method.releaseConnection();
	    }
	}

	@Override
	public void sendBujiaogaozhi(String sblsh_short) {
		// TODO Auto-generated method stub
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, RisesoftUtil.charset);
		PostMethod method = new PostMethod();
		try {
			//String url = ConstUtils.SZSURL+"service/httpapi/core/token/getXmlToken";
			String url = RisesoftCommonUtil.serviceURL+"/services/rest/szswj/bujiaogaozhi/sendBujiaogaozhi";
			method.setPath(url);
			method.addParameter("sblsh_short", sblsh_short);
			int code = client.executeMethod(method);
			if (code == HttpStatus.SC_OK) {
				System.out.println("推送数据成功");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		      method.releaseConnection();
	    }
	}

	
}
