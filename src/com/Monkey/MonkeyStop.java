package com.Monkey;

import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.CheckUE;
import com.Functions.Excute;

public class MonkeyStop {
		
	public void run(){
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);
		Stop();
		//check monkey status
		if(!CheckUE.checkMonkeyrun()){
			com.Main.ThenToolsRun.logger.log(Level.INFO,"MonkeyStop check no monkey run  ");
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame.getMonkeyUImain().getmonkeymonitorui(), "Stop Monkey successfully!",
					"Message", JOptionPane.INFORMATION_MESSAGE);
		}else{
			com.Main.ThenToolsRun.logger.log(Level.INFO,"MonkeyStop check  monkey run");
		JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame.getMonkeyUImain().getmonkeymonitorui(), "Stop Monkey faild, pls check device!",
				"Message", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public void Stop(){
		List<String> list=Excute.returnlist2("ps |grep \"com.android.commands.monkey\"");
		for( String str : list){
			if (str.equals("")){
				continue;
				}
			if (str.contains(" ")) {
			String[] strArray = str.split("\\s+");
			Excute.execcmd2("kill "+strArray[1]);
			com.Main.ThenToolsRun.logger.log(Level.INFO,"adb shell kill "+strArray[1]);
			}
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
			}
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
	com.Main.ThenToolsRun.logger.log(Level.INFO,"Stop monkey in phone");
	}
}
