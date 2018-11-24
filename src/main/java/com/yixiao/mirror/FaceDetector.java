package com.yixiao.mirror;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
//获得截取的二进制数据后获得监测到的faceid
public class FaceDetector
{   
	@SuppressWarnings("unused")
	public static String DetectFace(byte[] a) 
    {
		//建立客户端
        HttpClient httpclient = HttpClients.createDefault();
        try
        {
        	//建立服务器连接
            URIBuilder builder = new URIBuilder("https://eastus.api.cognitive.microsoft.com/face/v1.0/detect");
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            
            //设置header
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "14cf75366291489b94464de4075f3ed8");
   		
            // Request body
            ByteArrayEntity reqEntity = new ByteArrayEntity(a);
            request.setEntity(reqEntity);
            
            //获得服务器响应
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if(entity.getContentLength() == 2)
            {
            	return "未检测到人脸";
            }
            if (entity != null) 
            {	
            	
            	//将得到的JSON对象解析出来
            	String trans = EntityUtils.toString(entity);
            	int i = trans.indexOf("{");
            	trans = trans.substring(i);
            	JSONObject o = new JSONObject(trans);
            	String faceid;
            	faceid = o.getString("faceId");
                return faceid;
            }
        }
        catch (Exception e)
        {
            return "FaceDetector exception: " + e.getMessage();
        }
        return "FaceDetector return";
    }
}