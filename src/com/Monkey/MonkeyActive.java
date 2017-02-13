package com.Monkey;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.CheckUE;
import com.Functions.EnDecrypt;
import com.Functions.Excute;

public class MonkeyActive {
	boolean activemonkeythreadrun=false;
	private SimpleDateFormat sDateFormat = new SimpleDateFormat("ssSSS");
	
	public void run(String seed,String intervals,String monkeyradio,String packages){
		//Ïß³ÌÆô¶¯
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		ActiveMonkeyThread activemonkeythread = new ActiveMonkeyThread(seed,intervals,monkeyradio,packages);
		new Thread(activemonkeythread).start();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"active monkey");
		
	}
	//runSystem
	public void runSystem(String seed,String intervals){
		//echo "monkey -s %seed% --ignore-crashes --ignore-timeouts --ignore-security-exceptions 
		//-v -v -v --throttle %t% 1200000000 > /storage/sdcard0/monkeylog.txt 2>&1 & ">tempmonkey.txt
		com.Main.ThenToolsRun.logger.log(Level.INFO,"System monkey seed="+seed+"  intervals="+intervals);
//		Excute.execcmd2("monkey -s "+seed+" --ignore-crashes --ignore-timeouts --ignore-security-exceptions "
//				+ "-v -v -v --throttle "+intervals+" 1200000000 > /sdcard/monkeylog.txt 2>&1 &");
		String cmd="echo monkey -s "+seed+" --ignore-crashes --ignore-timeouts --ignore-security-exceptions "
				+ "-v -v -v --throttle "+intervals+" 1200000000 ^> /sdcard/monkeylog.txt 2^>^&1 ^& >"
				+com.Main.ThenToolsRun.datalocation+"/tempdata";
		Excute.execcmd(cmd,1,true);
	}
	//runPackages
	public void runPackages(String seed,String intervals,String packages){
		com.Main.ThenToolsRun.logger.log(Level.INFO,"Packages monkey seed="+seed+"  intervals="+intervals+" packages="+packages);
//		Excute.execcmd2("monkey -s "+seed+" "+packages+" --ignore-crashes --ignore-timeouts --ignore-security-exceptions "
//				+ "-v -v -v --throttle "+intervals+" 1200000000 > /sdcard/monkeylog.txt 2>&1 &");
		String cmd="echo monkey -s "+seed+" "+packages+" --ignore-crashes --ignore-timeouts --ignore-security-exceptions "
				+ "-v -v -v --throttle "+intervals+" 1200000000 ^> /sdcard/monkeylog.txt 2^>^&1 ^& >"
				+com.Main.ThenToolsRun.datalocation+"/tempdata";
		Excute.execcmd(cmd,1,true);
	}
	//runCustomize
	public void runCustomize(String seed,String intervals,String packages){
		packages=packages.replace("\n", "");
		com.Main.ThenToolsRun.logger.log(Level.INFO,"Customize monkey seed="+seed+"  intervals="+intervals+" packages="+packages);
//		Excute.execcmd2("monkey -s "+seed+" "+packages
//				+ " -v -v -v --throttle "+intervals+" 1200000000 > /sdcard/monkeylog.txt 2>&1 &");
		String cmd="echo monkey -s "+seed+" "+packages
				+ " -v -v -v --throttle "+intervals+" 1200000000 ^> /sdcard/monkeylog.txt 2^>^&1 ^& >"
				+com.Main.ThenToolsRun.datalocation+"/tempdata";
		Excute.execcmd(cmd,1,true);
	}
	
	public boolean getActiveMonkeythreadrun(){
		return activemonkeythreadrun;
	}
	
	class ActiveMonkeyThread implements Runnable {
		String seed,intervals,monkeyradio,packages;
		public ActiveMonkeyThread(String seed,String intervals,String monkeyradio,String packages) {
		this.seed=seed;
		this.intervals=intervals;
		this.monkeyradio=monkeyradio;
		this.packages=packages;
		}

		public void run() {
			EnDecrypt check=new EnDecrypt();
			if(!check.isok()){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"ERROR about check...");
				System.exit(0);
			}
			//check=null;
			activemonkeythreadrun=true;

			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
			if(monkeyradio.equals("System")){
				runSystem(seed,intervals);
			}else if(monkeyradio.equals("Packages")){
				runPackages(seed,intervals,packages);
			}else if(monkeyradio.equals("Customize")){
				runCustomize(seed,intervals,packages);
			}
			Excute.execcmd("shell <"+com.Main.ThenToolsRun.datalocation+"/tempdata",3,false);//run
			try {
				Thread.sleep(1800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(70);//******************
			Excute.execcmd("echo.>"+com.Main.ThenToolsRun.datalocation+"/tempdata",1,true);
			if(!CheckUE.checkMonkeyrun()){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Monkey active failed");
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Monkey active failed, Pls try again later!\n Some platforms has problems, Pls use monkey monitor!", 
						"Message", JOptionPane.ERROR_MESSAGE); 
			}else{
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Monkey active successful");
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Active "+monkeyradio+" Monkey successfully with seed="+seed+", intervals="+intervals, 
					"Message", JOptionPane.INFORMATION_MESSAGE);
			com.Main.ThenToolsRun.mainFrame.getMonkeyUImain().getformattedTextFieldSeed().setText(sDateFormat.format(new Date()));
			}
			activemonkeythreadrun=false;
		}
	}
	
}
