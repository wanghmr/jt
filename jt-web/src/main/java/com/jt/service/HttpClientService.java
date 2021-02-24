package com.jt.service;

import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {
	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig requestConfig;
	
	/**
	 * 用户调用工具方法.
	 * 通过工具方法返回服务器数据(json串)给用户
	 * 
	 * 参数:
	 * 	GET:http://www.jd.com;
	 * 	GET:http://www.jd.com?id=1&name="xxxx"
	 * 	难点:判断用户是否传参
	 * 
	 * 	url:用户通过工具API访问的网址.
	 * 	params:利用Map集合封装数据.
	 *  charset: 定义字符集编码.
	 */
	public String doGet(String url,Map<String,String> params,String charset) {
		//1.校验字符集编码是否为null
		if(StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		
		//2.校验参数是否为null
		if(params != null) {
			//如果不为null,需要拼接字符串
			url += "?";	//http://xxxx?id=1&name=xxx&
			for (Map.Entry<String,String> entry : params.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				url += key+"="+value+"&";
			}
			//去除最后一个字符
			url = url.substring(0,url.length()-1);
		}
		
		//3.发起请求
		HttpGet httpGet = new HttpGet(url);
		//设定请求的超时时间
		httpGet.setConfig(requestConfig);
		
		String result = null;
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			//校验用户请求是否正确.
			if(200 == httpResponse.getStatusLine().getStatusCode()) {
				result = EntityUtils.toString(httpResponse.getEntity(),charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	public String doGet(String url) {
		
		return doGet(url, null, null);
	}
	
	public String doGet(String url,Map<String,String> params) {
		
		return doGet(url, params, null);
	}

}
