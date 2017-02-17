package com.yy.springboot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * 工具类
 * @author zhaojl
 *
 */
public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	/**
	 * 调用接口工具类
	 * @param postUrl请求地址
	 * @param requestMessage请求参数JSON
	 * @param connectTimeOut连接超时时间（毫秒）
	 * @param inMessageCode输入编码
	 * @param outMessageCode输出编码
	 * @return
	 */
	public static String getInterfaceData(String postUrl, String requestMessage,
			Integer connectTimeOut, String inMessageCode, String outMessageCode) {
		String paramResult = null;  
		logger.debug("getInterfaceData..send:postUrl=" + postUrl);
		
		HttpClient dHttpClient = HttpClients.createDefault();
		HttpPost httpClient = new HttpPost(postUrl);
		try {
			// 将参数解析为JSON
			logger.debug("getInterfaceData..requestUrl:" + postUrl);
			logger.debug("getInterfaceData..requestMessageJSON:" + requestMessage);
			// 设置发送参数
			StringEntity sEntity = new StringEntity(requestMessage,inMessageCode);
			// 设置发送文本头信息
			sEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE));
			sEntity.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			logger.debug("getInterfaceData..sendRequest");
			// 设置连接超时时间为3秒
			RequestConfig rConfig = RequestConfig.custom().setSocketTimeout(connectTimeOut).setConnectionRequestTimeout(connectTimeOut).build();
			httpClient.setConfig(rConfig);

			httpClient.setEntity(sEntity);
			// 执行POST请求，并返回结果
			HttpResponse response = dHttpClient.execute(httpClient);
			paramResult = getResponseValue(response, outMessageCode);
		} catch (UnsupportedEncodingException e) {
			logger.error("getInterfaceData..sendResponseCode.fail", e);
		} catch (ClientProtocolException e) {
			logger.error("getInterfaceData..sendResponseCode.fail", e);
		} catch (IOException e) {
			logger.error("getInterfaceData..sendResponseCode.fail", e);
		} finally {
			if (httpClient != null) {
				httpClient.releaseConnection();
			}
			if (dHttpClient != null) {
				dHttpClient.getConnectionManager().shutdown();
			}
		}
		return paramResult;
	}
	
	
	/**
	 * 解析接口返回信息
	 * @param response
	 * @param outMessageCode 输出编码
	 * @return
	 */
	public static String getResponseValue(HttpResponse response,String outMessageCode){
		String resultValue = "";
		logger.debug("getInterfaceData..sendResponseCode" + response.getStatusLine().getStatusCode());
		try{
		if (response.getStatusLine().getStatusCode() == 200) {
			// 获取返回的数据
			logger.debug("getInterfaceData..sendResponseCode.success");
			InputStreamReader isr = new InputStreamReader(response
					.getEntity().getContent(), outMessageCode);
			// 解析返回参数
			resultValue = getData(isr);
			logger.debug("getInterfaceData..responseMessage=" + resultValue);
		} else {
			logger.error("getInterfaceData..reponsefail:" + response.getStatusLine().getStatusCode());
			throw new ClientProtocolException("getInterfaceData...setConnectRequestTimeOut");
		}
		}catch(Exception e){
			logger.error("getInterfaceData..reponsefail:" + e);
		}
		return resultValue;
	}
	
	// 将流转换为字符串
	public static String getData(InputStreamReader isr) throws IOException {
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}
	
}


