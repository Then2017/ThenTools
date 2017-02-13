package com.Qcom;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.Excute;
import com.Qcom.QcomGet.GetlogThread;

public class QcomStop {
		public boolean stoplogthreadrun=false;
		
		public void run(boolean dellog){
			//Ïß³ÌÆô¶¯
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
			StoplogThread stoplogthread = new StoplogThread(dellog);
			new Thread(stoplogthread).start();
		}
	
		public void stop(){
				List<String> list=Excute.returnlist2("ps |grep \"/system/bin/sh\"");
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
			com.Main.ThenToolsRun.logger.log(Level.INFO,"Stop catchlog in phone");
		}
	
		public void del(){
			Excute.execcmd2("rm -rf /sdcard/CatchLog");
			com.Main.ThenToolsRun.logger.log(Level.INFO,"del CatchLog folder in phone");
		}
		public boolean getstoplogthreadrun(){
			return stoplogthreadrun;
		}
		
		class StoplogThread implements Runnable {
			//boolean output=false;
			boolean dellog=false;
			public StoplogThread(boolean dellog) {
				this.dellog=dellog;
			}

			public void run() {
				stoplogthreadrun=true;
				if(!dellog){
						stop();
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop logs successfully!",
								"Message", JOptionPane.INFORMATION_MESSAGE);
						com.Main.ThenToolsRun.logger.log(Level.INFO,"stop log ");

				}else{
						stop();
						del();
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop and del logs successfully!",
								"Message", JOptionPane.INFORMATION_MESSAGE);
						com.Main.ThenToolsRun.logger.log(Level.INFO,"stop and del log ");
				}
				stoplogthreadrun=false;
			}
		}
}
