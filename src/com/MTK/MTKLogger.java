package com.MTK;

import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.Excute;
import com.Functions.LoggerUtil;

public class MTKLogger {
	
	
	//http://blog.chinaunix.net/uid-29728680-id-5185788.html
	//active log
	public void activemtklog(){
		Excute.execcmd2("am broadcast -a com.mediatek.mtklogger.ADB_CMD -e cmd_name start --ei cmd_target 7");
		String mtk=Excute.execcmd2("getprop debug.MB.running");
		if(mtk.contains("0")){
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Active MTKLogger failed!","Message",JOptionPane.ERROR_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Active MTKLogger successful!","Message",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//stop log
	public void stopmtklog(boolean dellog){
		if(!dellog){
			Excute.execcmd("am broadcast -a com.mediatek.mtklogger.ADB_CMD -e cmd_name stop --ei cmd_target 7", 2, true);
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
			String mtk=Excute.execcmd2("getprop debug.MB.running");
			if(!mtk.contains("0")){
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop MTKLogger failed!","Message",JOptionPane.ERROR_MESSAGE);
			}
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop mtklogger successfully!",
					"Message", JOptionPane.INFORMATION_MESSAGE);
			com.Main.ThenToolsRun.logger.log(Level.INFO,"stop log ");

		}else{
			Excute.execcmd2("am broadcast -a com.mediatek.mtklogger.ADB_CMD -e cmd_name stop --ei cmd_target 7");
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
			String mtk=Excute.execcmd2("getprop debug.MB.running");
			if(!mtk.contains("0")){
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop MTKLogger failed!","Message",JOptionPane.ERROR_MESSAGE);
			}
			delmtklog();//del
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop and del mtklogs successfully!",
					"Message", JOptionPane.INFORMATION_MESSAGE);
			com.Main.ThenToolsRun.logger.log(Level.INFO,"stop and del log ");
			}
	}
	
	//and del logs
	public void delmtklog(){
		Excute.execcmd2("rm -rf /sdcard/mtkLog");
	}
	//show mtklogger ui
	public void showui(){
		Excute.execcmd2("am start -n com.mediatek.mtklogger/com.mediatek.mtklogger.MainActivity");
	}
	
}
