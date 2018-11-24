package com.yixiao.mirror;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

//将获得的media id上传至服务器
public class StringTo {
	 public static void ToString(String MediaId) throws URISyntaxException, ClientProtocolException, IOException {
		 	
		    //建立客户端
		 	HttpClient httpclient = HttpClients.createDefault();
		 	
		 	//建立服务器连接
			URIBuilder builder = new URIBuilder("http://4dd8c9d8.ngrok.io/Weixin/test.do");
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            
            // Request body
            StringEntity reqEntity = new StringEntity(MediaId);
            request.setEntity(reqEntity);
            
           //获得服务器响应
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));

	}

}
