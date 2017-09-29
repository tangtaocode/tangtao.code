package invengo.cn.pay.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

import invengo.cn.pay.config.AccessConfig;

/**
 * 云中台API请求帮助类
 * */
public class YZTRequestUtils {
	public static final String COMMON_PARAM_SIGN = "sign";
	
	public static Map<String, String> createParamsHelperMap(Map<String, String> applicationParams,AccessConfig accessConfig) {
		Map<String, String> paramMap = applicationParams;
		paramMap.put("app_id",accessConfig.getApp_id());
		paramMap.put("sign",EncryptUtil.signRequest(applicationParams, accessConfig.getApp_secret(),COMMON_PARAM_SIGN));  // 临时测试使用
		return paramMap;
	}
	/**
     * 
     * @Title: doPost   
     * @Description: TODO(原生态键值对提交数据)   
     */
    public static String doConPost(String url, Map parameterMap) throws Exception {
      StringBuffer parameterBuffer = new StringBuffer();
      if (parameterMap != null) {
          //参数迭代
          Iterator iterator = parameterMap.keySet().iterator();
          String key = null;
          String value = null;
          //将MAP参数的key和value组合
          while (iterator.hasNext()) {
              key = (String)iterator.next();
              //获取key对应的value
              if (parameterMap.get(key) != null) {
                value = (String)parameterMap.get(key);
              } else {
                  value = "";
              }
              //组数据
              parameterBuffer.append(key).append("=").append(value);
              if (iterator.hasNext()) {
                  parameterBuffer.append("&");
              }
          }
      }

      //将url转化为HttpURLConnection类型的对象
      URL localURL = new URL(url);

      URLConnection connection = localURL.openConnection();;

      HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
      //是否输出
      httpURLConnection.setDoOutput(true);
      //请求方式
      httpURLConnection.setRequestMethod("POST");
      //传送类型为可序化java对象
      httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
      httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));

      //准备输出输入流的读写与存储
      OutputStream outputStream = null;
      OutputStreamWriter outputStreamWriter = null;
      InputStream inputStream = null;
      InputStreamReader inputStreamReader = null;
      BufferedReader reader = null;
      StringBuffer resultBuffer = new StringBuffer();
      String tempLine = null;
      //System.out.println("111111111"+parameterBuffer.toString());
      try {
          //输出请求流
          outputStream = httpURLConnection.getOutputStream();
          outputStreamWriter = new OutputStreamWriter(outputStream);

          outputStreamWriter.write(parameterBuffer.toString());
          outputStreamWriter.flush();
          //响应失败
          if (httpURLConnection.getResponseCode() >= 300) {
              throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
          }
          //接收响应流
          inputStream = httpURLConnection.getInputStream();
          inputStreamReader = new InputStreamReader(inputStream);
          reader = new BufferedReader(inputStreamReader);

          while ((tempLine = reader.readLine()) != null) {
              resultBuffer.append(tempLine);
          }
          //关闭流，释放资源
      } finally {

          if (outputStreamWriter != null) {
              outputStreamWriter.close();
          }

          if (outputStream != null) {
              outputStream.close();
          }

          if (reader != null) {
              reader.close();
          }

          if (inputStreamReader != null) {
              inputStreamReader.close();
          }

          if (inputStream != null) {
              inputStream.close();
          }

      }

      return resultBuffer.toString();
  }
}
