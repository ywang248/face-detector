package com.yixiao.mirror;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	private static boolean status = true;
	public static void StringToText(String id) throws IOException
	{
	    FileWriter fw = new FileWriter("C:\\Users\\王一骁\\Desktop\\id.txt",status);    
	    fw.write(id);
	    fw.write("\r\n");
	    fw.flush();
	    fw.close();
	}
	public static void setStatus(boolean status)
	{
		Writer.status = status;
	}
}
