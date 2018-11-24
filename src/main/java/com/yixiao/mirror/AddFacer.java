package com.yixiao.mirror;

import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class AddFacer
{
    public void AddFace(String name, byte[] image) 
    {
        HttpClient httpclient = HttpClients.createDefault();

        //获得用户照片二进制数据和名字后加入微软LargeFaceList
        //从LargeFaceList加入或者删除一个Faceid后一定要Train LargeFaceList
        try
        {
        	//建立URL
            URIBuilder builder = new URIBuilder("https://eastus.api.cognitive.microsoft.com/face/v1.0/largefacelists/viplist/persistedfaces");
            
            //添加用户参数
            builder.setParameter("userData", name);
            
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            
            //设置header
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "14cf75366291489b94464de4075f3ed8");


            // Request body
            ByteArrayEntity reqEntity = new ByteArrayEntity(image);
            request.setEntity(reqEntity);
            
            //获得服务器响应
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            String entityy = EntityUtils.toString(entity);
            if (entity != null) 
            {
            	JSONObject o = new JSONObject(entityy);
            	
            //获取error信息
            if(entityy.indexOf("error") != -1)
            {
            	o = (JSONObject) o.get("error");
            	System.out.println(o.getString("message"));           			
            }
            //获取添加得到的faceid
            else
            {
            	System.out.println(o.get("persistedFaceId"));
            }	
            }
        }
        catch (Exception e)
        {
            System.out.println("AddFacer exception: " + e.getMessage());
        }
    }
}
