package com.yixiao.mirror;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONObject;

//使用者需要先在本地存一张人脸照片，通过TestAdd.java上传到Azure LargeFaceList。然后运行
//Train.java训练LargeFaceList以便人脸检测，最后再运行本程序自拍。
public class Test {
	
	static JSONObject o = new JSONObject();
	static String id;
	public static void main(String[] args) throws Exception
	{
		//-------------------------------------------------
//		Connection conn = MysqlUtils.getConnection();
//		String sql = "insert into user (photo) values (?)";
//		PreparedStatement pst=conn.prepareStatement(sql);
		//-------------------------------------------------
		
		//打开摄像头
		CamOpener.open();
		
		//通过while使得程序持续运行
		while(true)
		{
		System.out.println("成功截取");
		
		//将每一帧截图变成二进制
		byte[] im = Capturer.capture();
		//-------------------------------------------------
//		pst.setBytes(1, im);
//		pst.executeUpdate();
		//-------------------------------------------------
		
		//检测是否有人脸在画面内
		String faceid = FaceDetector.DetectFace(im);
		
		//有人脸时
		if(faceid != "未检测到人脸"){
			int num = 0;
		System.out.println("检测中，请稍后。。。");
		
		//寻找LargeFaceList里是否有相似人脸
		SimilarFinder.FindSimilar(faceid);
		
		//找到相似人脸
		if(SimilarFinder.getResult() == "登陆成功")
		{
			System.out.println(SimilarFinder.getResult());
			
			//通过循环自拍3张照片并上传至服务器
			while(num != 3)
			{
				
			//文件名迭代
			LogoMarker.setNumber(num+1);
			
			System.out.println("请摆好姿势");
			Thread.sleep(1000);
			System.out.println("3");
			Thread.sleep(1000);
			System.out.println("2");
			Thread.sleep(1000);
			System.out.println("1");	
			
			//拍摄自拍并存到本地
			LogoMarker.AddLogo(CamOpener.getWebcam());
			System.out.println("拍摄成功，准备拍下一张");
			
			//将本地自拍上传至微信临时素材库
			String address = String.format("C:\\Users\\王一骁\\Desktop\\Java\\mirror\\pose%s.jpg", String.valueOf(num+1));
			o = UpLoader.UploadMedia("image",address);
			
		    //Writer可以把media_id写进本地
			//Writer.StringToText(id);
			
			//获得微信客户端返回的临时素材id
		    id = (String) o.get("media_id");
		    
		    //将media id上传至服务器
			StringTo.ToString(id);
			num += 1;
			
			//检测用户是否中途离开
			im = Capturer.capture();
			faceid = FaceDetector.DetectFace(im);
			if(faceid == "未检测到人脸")
			{
				System.out.println("检测到用户已经离开");
				break;
			}
			}
			num = 0;
		}
		else
		{
		System.out.println(SimilarFinder.getResult());
		}
		
		}	
		}
}
}