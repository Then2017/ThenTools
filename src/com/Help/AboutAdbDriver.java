package com.Help;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.HttpUtil;
import com.Functions.LoggerUtil;

public class AboutAdbDriver {
	//http://adbdriver.com/upload/adbdriver.zip
	boolean aboutadbdriverthreadrun=false;
	
	public void run(){
		//Ïß³ÌÆô¶¯
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		AboutAdbDriverThread aboutadbdriverthread = new AboutAdbDriverThread();
		new Thread(aboutadbdriverthread).start();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"start adb driver download");
	}
	//get aboutadbdriverthreadrun
	public boolean getAboutadbdriverthreadrun(){
		return aboutadbdriverthreadrun;
	}
	class AboutAdbDriverThread implements Runnable {
		public AboutAdbDriverThread() {

		}

		public void run() {
			aboutadbdriverthreadrun=true;
			File file=new File(com.Main.ThenToolsRun.ThenLogfile+"/Adbdriver.zip");
			if(file.exists()){
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
				aboutadbdriverthreadrun=false;
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Adb driver is checked in "+com.Main.ThenToolsRun.ThenLogfile+"/Adbdriver.zip");
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Adb driver is checked in "+com.Main.ThenToolsRun.ThenLogfile+"/Adbdriver.zip", 
						"Message", JOptionPane.INFORMATION_MESSAGE); 
				return;
			}
			try {
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(40);//******************
				Thread.sleep(1500);
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(60);//******************
				Thread.sleep(1500);
				if(HttpUtil.downloadFile(com.Main.ThenToolsRun.ThenLogfile+"/Adbdriver.zip", "http://adbdriver.com/upload/adbdriver.zip")){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Download adb driver successfully!");
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Download adb driver successfully!", 
							"Message", JOptionPane.INFORMATION_MESSAGE);  
				}else{
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Download adb driver failded, pls check your network!");
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Download adb driver failded, pls check your network!", 
							"Message", JOptionPane.ERROR_MESSAGE);  
				}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
			aboutadbdriverthreadrun=false;
		}
	}
}
