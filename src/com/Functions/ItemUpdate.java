package com.Functions;

import java.util.Date;
import java.util.logging.Level;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;

import com.Main.MonkeyUImain;
import com.Monkey.MonkeyPackage;


public class ItemUpdate {
	MonkeyPackage monkeypackage=new MonkeyPackage();
	
	public void run( ){
		//Ïß³ÌÆô¶¯
		ItemUpdateThread itemupdatethread = new ItemUpdateThread();
		new Thread(itemupdatethread).start();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"Item Update");
		
	}
	
	public void updateMonkeyUI(){
		if(com.Main.ThenToolsRun.mainFrame.getMonkeyUImain()!=null){
			com.Main.ThenToolsRun.mainFrame.getMonkeyUImain().setlistpackageAPP();
			com.Main.ThenToolsRun.mainFrame.getMonkeyUImain().setlistpackageGMS();
			com.Main.ThenToolsRun.mainFrame.getMonkeyUImain().setlistpackageRun();
			com.Main.ThenToolsRun.logger.log(Level.INFO,"ItemUpdate MonkeyUI");
		}
	}
	public void updateAutoscriptUImain(){
		if(com.Main.ThenToolsRun.mainFrame.getAutoScriptUImain()!=null){
				if(com.Main.ThenToolsRun.mainFrame.getAutoScriptUImain().getisstartrecord()){
					com.Main.ThenToolsRun.mainFrame.getAutoScriptUImain().getbtnStartRecord().doClick();
				}
//				if(com.Main.ThenToolsRun.mainFrame.getAutoScriptUImain().getisstartplayback()){
//					com.Main.ThenToolsRun.mainFrame.getAutoScriptUImain().getbtnbtnPlayback().doClick();
//				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"ItemUpdate AutoscriptUImain");
		}
	}
	
	public void updateCheckTPUImain(){
		if(com.Main.ThenToolsRun.mainFrame.getCheckTPUImain()!=null){
				if(com.Main.ThenToolsRun.mainFrame.getCheckTPUImain().getisstartrecord()){
					com.Main.ThenToolsRun.mainFrame.getCheckTPUImain().getbtnStart().doClick();
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"ItemUpdate CheckTPUImain");
		}
	}
	class ItemUpdateThread implements Runnable {

		public ItemUpdateThread( ) {

		}

		public void run() {
			updateMonkeyUI();
			updateAutoscriptUImain();
			updateCheckTPUImain();
		}
	}
}
