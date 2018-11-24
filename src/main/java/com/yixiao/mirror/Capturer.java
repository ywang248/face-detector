package com.yixiao.mirror;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
//截取1帧然后转换成二进制数据
public class Capturer {
	
	public static byte[] capture() throws IOException {
		
		//得到默认摄像头
		Webcam webcam = Webcam.getDefault();
		
		//将摄像头信息存入缓冲区
		BufferedImage image = webcam.getImage();
		
		//存成二进制
		ByteArrayOutputStream bytee = new ByteArrayOutputStream();
		ImageIO.write(image,"PNG", bytee);
		byte[] im = bytee.toByteArray();
		
		return im;
	}

}
