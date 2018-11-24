package com.yixiao.mirror;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

//将本地图片上传至微信公众号临时素材库
public class UpLoader {
    public static JSONObject UploadMedia(String fileType,String filePath) throws Exception{
       
    	//返回结果
        String result=null;
        
        //检测文件是否存在
        File file=new File(filePath);
        if(!file.exists()||!file.isFile()){
            throw new IOException("文件不存在");
        }
        
        //获得微信公众号服务器access token
        TokenGeter.GetToken();
        String token= TokenGeter.getToken();
        //System.out.println(token);
        
        //建立URL连接
        String urlString="https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+token+"&type="+fileType;
        URL url=new URL(urlString);
        HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
        
        //以POST方式提交表单
        conn.setRequestMethod("POST");
        
        conn.setDoInput(true);
        conn.setDoOutput(true);
        
        //POST方式不能使用缓存
        conn.setUseCaches(false);
        
        //设置请求头信息
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        
        //设置边界
        String BOUNDARY="----------"+System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
      
        //请求正文信息
        //第一部分
        StringBuilder sb=new StringBuilder();
        
        //必须多两条道
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\"; filename=\"" + file.getName()+"\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        //获得输出流
        OutputStream out=new DataOutputStream(conn.getOutputStream());
        
        //输出表头
        out.write(sb.toString().getBytes("UTF-8"));
        
        //文件正文部分
        //把文件以流的方式 推送道URL中
        DataInputStream din=new DataInputStream(new FileInputStream(file));
        
        int bytes=0;
        byte[] buffer=new byte[1024];
        while((bytes=din.read(buffer))!=-1){
            out.write(buffer,0,bytes);
        }
        din.close();
        
        //结尾部分
        byte[] foot=("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
        
        //定义数据最后分割线
        out.write(foot);
        
        out.flush();
        out.close();
        
        //获取服务器响应
        if(HttpsURLConnection.HTTP_OK==conn.getResponseCode()){

            StringBuffer strbuffer=null;
            BufferedReader reader=null;
            
            try {
            	//获得返还的JSON数据
            	//"type","media_id","create_time"
                strbuffer=new StringBuffer();
                reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String lineString=null;
                while((lineString=reader.readLine())!=null){
                    strbuffer.append(lineString);

                }
                if(result==null){
                    result=strbuffer.toString();
                    System.out.println("result:"+result);
                }
            } catch (IOException e) {
                System.out.println("发送POST请求出现异常！"+e);
                e.printStackTrace();
            }finally{
                if(reader!=null){
                    reader.close();
                }
            }

        }
        JSONObject jsonObject=new JSONObject(result);
        return jsonObject;

    }

}
