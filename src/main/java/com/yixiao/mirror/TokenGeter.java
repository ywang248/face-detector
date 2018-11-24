package com.yixiao.mirror;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

//获取微信公众号Access Token
public class TokenGeter {
	
	private static String token = null;
	public static void GetToken() throws URISyntaxException, ClientProtocolException, IOException
	{
		//建立客户端
	 	HttpClient httpclient = HttpClients.createDefault();
	 	
	 	//建立服务器连接
		URIBuilder builder = new URIBuilder("http://4dd8c9d8.ngrok.io/Weixin/token.do");
        URI uri = builder.build();
        HttpGet request = new HttpGet(uri);
        
       //获得服务器响应
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        
        //解析并获得token
        token = EntityUtils.toString(entity);
	}
	
	public static String getToken()
	{
		return token;
	}
}
