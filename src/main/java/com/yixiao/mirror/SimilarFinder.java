package com.yixiao.mirror;

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

public class SimilarFinder {
	 private static String result = null;
	 
	//对比获取的faceid和微软LargeFaceList里的所有ID，得到最相似的一张脸
	public static void FindSimilar(String faceid)
	{
		
		//建立客户端
		HttpClient httpclient = HttpClients.createDefault();
		try 
		{	
			
			//建立服务器连接
			URIBuilder builder = new URIBuilder("https://eastus.api.cognitive.microsoft.com/face/v1.0/findsimilars");
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			
			//设置header
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "14cf75366291489b94464de4075f3ed8");
            
            JSONObject obj = new JSONObject();
            obj.put("faceId",faceid);
            obj.put("largeFaceListId","viplist");
            obj.put("maxNumOfCandidatesReturned",1);
            obj.put("mode","matchPerson");
            
            // Request body
            StringEntity reqEntity = new StringEntity(obj.toString());
            request.setEntity(reqEntity);

            //获取服务器响应
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            
            //返回空数组
            if(entity.getContentLength() == 2)
            {
            	result = "识别不成功";
            }
            
            else {
            	
            	//解析返回的JSON
            	String trans = EntityUtils.toString(entity);
            	int i = trans.indexOf("{");
            	trans = trans.substring(i);
            	
            	//设置相似度confidence
            	JSONObject o = new JSONObject(trans);
            	if((Double)o.get("confidence") >= 0.6)
            	{
            		result = "登陆成功";
            		//System.out.println(result);
            		System.out.println(o.get("persistedFaceId"));
            		System.out.println(o.get("confidence"));
            	}
            	else {
            		result = "与库存照片相似度太低，识别不成功"; 
            		System.out.println(o.get("persistedFaceId"));
            		System.out.println(o.get("confidence"));
            	}
            }
		}
        catch (Exception e)
        {
            System.out.println("SimilarFinder exception :" + e.getMessage());
        }
		
	}
	public static String getResult() {
		return result;
	}
}
