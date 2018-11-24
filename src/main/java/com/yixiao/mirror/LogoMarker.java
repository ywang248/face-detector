package com.yixiao.mirror;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

//在图片上加logo并保存下来
public class LogoMarker {
	
	//pose为保存的文件名
	private static String pose = "pose";
	private static int number = 1;
	private static BufferedImage image;

	public static void AddLogo(Webcam webcam) throws IOException
	{
		
		//获取摄像头图像
		image = webcam.getImage();
		
		//设置画笔绘画环境
		Graphics g = image.createGraphics();
		
		//读取logo图像
		Image logo = ImageIO.read(new File("C:\\Users\\王一骁\\Desktop\\QR.jpg"));
		
		//设置logo大小
		logo = logo.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		
		int lwidth = logo.getWidth(null);
		int lheight = logo.getHeight(null);
		
		//画在图片上
	    g.drawImage(logo,5,375,lwidth,lheight,null);
	    
	    //保存在本地
		pose  = pose.concat(String.valueOf(number));
		File f=new File(pose.concat(".jpg"));
		try {
			ImageIO.write(image, "jpg", f);
		} catch (IOException e) {
			e.printStackTrace();}
		
        	pose = "pose";
	}
	
	public static void setNumber(int number)
	{
		LogoMarker.number = number;
	}
	
	public static int getNumber() {
		return number;
	}
}
