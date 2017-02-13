package com.MTK;

import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.CheckUE;
import com.Functions.Excute;
import com.Functions.LoggerUtil;

public class MTKADBRoot {
	
	public void main(){
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		if(com.Main.ThenToolsRun.selectedID!=null){
		if(CheckUE.getisroot().equals("Yes")){
			com.Main.ThenToolsRun.logger.log(Level.INFO,"devices has been root");
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "device has been root with adb!", 
					"Message", JOptionPane.WARNING_MESSAGE);
		}else{
		int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Do you want to root devices ? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
		if(confirm==0){
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(adbroot()){
						com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
						com.Main.ThenToolsRun.logger.log(Level.INFO,"root devices end with successful");
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ADB Root device successfully!", 
								"Message", JOptionPane.INFORMATION_MESSAGE);
					}else{
						com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
						com.Main.ThenToolsRun.logger.log(Level.INFO,"root devices end with falied");
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ADB Root device falied!", 
								"Message", JOptionPane.ERROR_MESSAGE);
					}
				}
			}).start();
			com.Main.ThenToolsRun.logger.log(Level.INFO,"start to root devices");
		}else{
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
			com.Main.ThenToolsRun.logger.log(Level.INFO,"cancel root devices");
			return;
		}
	}
		}else{
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
			com.Main.ThenToolsRun.logger.log(Level.INFO,"no devices checked with ADBROOT");
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "no devices checked", 
					"Message", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	//adb root
	public boolean adbroot(){
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		Excute.execcmd2("setprop ro.debuggable 1");
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(20);//******************
		JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls plug out usb cable and plug it in !", 
				"Message", JOptionPane.INFORMATION_MESSAGE);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(40);//******************
		String[] resultroot=Excute.execcmd("root", 3, true);
		com.Main.ThenToolsRun.logger.log(Level.INFO,resultroot[0].toString());
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(60);//******************
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(90);//******************
		return CheckUE.getisroot().equals("Yes");
	}
	
}
