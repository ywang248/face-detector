package com.yixiao.mirror;

//// This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

//训练LargeFaceList
public class Train
{
 public static void main(String[] args) 
 {
	 
	//建立客户端
     HttpClient httpclient = HttpClients.createDefault();

     try
     {
    	 //建立服务器连接
         URIBuilder builder = new URIBuilder("https://eastus.api.cognitive.microsoft.com/face/v1.0/largefacelists/viplist/train");
         URI uri = builder.build();
         HttpPost request = new HttpPost(uri);
         
         //设置header
         request.setHeader("Ocp-Apim-Subscription-Key", "14cf75366291489b94464de4075f3ed8");
         
         // Request body
         JSONObject obj = new JSONObject();
         obj.put("largeFaceListId","viplist");
         
         StringEntity reqEntity = new StringEntity(obj.toString());
         request.setEntity(reqEntity);
         
        //获得服务器响应
         HttpResponse response = httpclient.execute(request);
         HttpEntity entity = response.getEntity();

         if (entity != null) 
         {
             System.out.println(EntityUtils.toString(entity));
         }
     }
     catch (Exception e)
     {
         System.out.println(e.getMessage());
     }
 }
}
