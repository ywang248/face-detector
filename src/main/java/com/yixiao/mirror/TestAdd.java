package com.yixiao.mirror;

import java.io.FileNotFoundException;
import java.io.IOException;

//将本地照片加入Azure LargeFaceList
public class TestAdd {
	
	public static void main(String []args) throws FileNotFoundException, IOException
	{
		SetImage n = new SetImage();
		byte[] a = n.SetImageToByteArray("C:\\Users\\王一骁\\Desktop\\face.png");
		
		AddFacer b = new AddFacer();
		b.AddFace("王一骁",a);
				
	}
}
