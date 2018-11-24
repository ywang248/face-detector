package com.yixiao.mirror;

import com.github.sarxos.webcam.*;
import java.io.IOException;
import javax.swing.JFrame;


	//打开默认摄像头
public class CamOpener {
	private static JFrame window = null;
	private static WebcamPanel panel = null;
	private static Webcam webcam = null;
	public static void open() throws IOException {
		
		//得到默认摄像头
		webcam = Webcam.getDefault();
		
		//设置窗口大小
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		
		//创建panel显示摄像头内容
		panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setMirrored(true);
		panel.setLayout(null);
		
		//创建窗口
        window = new JFrame("mirror");
        window.setContentPane(panel);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		
	}
	public static JFrame getJFrame() {
		return window;
	}
	
	public static WebcamPanel getWebcamPanel() {
		return panel;
	}
	
	public static Webcam getWebcam() {
		return webcam;
	}
}


