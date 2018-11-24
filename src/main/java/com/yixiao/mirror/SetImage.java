package com.yixiao.mirror;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;

public class SetImage {
	//把本地路径照片转换成二进制
    public byte[] SetImageToByteArray(String filepath) throws FileNotFoundException, IOException {
    	
    	//设置文件路径
    	File n = new File(filepath);
    	
    	//创建输入流
    	FileImageInputStream fs = new FileImageInputStream(n);
    	int streamLength = (int)fs.length();
    	byte[] image = new byte[streamLength]; 
    	fs.read(image, 0, streamLength); 
    	fs.close(); 
    	return image;
	}
    

}
