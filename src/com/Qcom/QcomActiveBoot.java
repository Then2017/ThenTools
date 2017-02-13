package com.Qcom;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.CheckUE;
import com.Functions.Excute;
import com.Qcom.QcomActive.ActivelogThread;

public class QcomActiveBoot {
    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
    String PCtime;
	String adbfile;
	String kmsgfile;
	String dmesgfile;
	String whatlog="null";
	public boolean activelogthreadrun=false;
 	boolean hasdmesg;
 	boolean exitloop=false;
 	
	public void run() {
	    PCtime = sDateFormat.format(new Date());
		adbfile="/sdcard/CatchLog/PCtime"+PCtime+"/adb";
		kmsgfile="/sdcard/CatchLog/PCtime"+PCtime+"/kmsg";
		dmesgfile="/sdcard/CatchLog/PCtime"+PCtime+"/dmesg";
		
		//Ïß³ÌÆô¶¯
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		ActivelogThread activelogthread = new ActivelogThread();
		new Thread(activelogthread).start();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"active log");
	}
	
	public boolean checkdmesg(){
			hasdmesg=true;
			String userdebuglist=Excute.execcmd2("dmesg |grep \"klogctl: Operation not permitted\"");
	    	//com.Main.ThenToolsRun.logger.log(Level.INFO,userdebuglist[0]);
	        if(userdebuglist.contains("klogctl: Operation not permitted")){
	        	hasdmesg=false;
	        	com.Main.ThenToolsRun.logger.log(Level.INFO,"can't get dmesg without root");
	        }
			return hasdmesg;
	}
	
	public void activeadb(){
		Excute.execcmd("mkdir -p "+adbfile,2,true);
		String cmd_logcat="echo while true;do Datelogcat=`date +%Y%m%d_%H_%M_%S`;"
				+ "logcat -v threadtime ^>"+adbfile+"/${Datelogcat}main_system_log.txt ^&"
				+ " echo $! ^>"+adbfile+"/logcatpid.txt;"
				+ "logcat -v threadtime -b radio ^>"+adbfile+"/${Datelogcat}radio_log.txt ^&"
				+ " echo $! ^>^>"+adbfile+"/logcatpid.txt;"
				+ "logcat -v threadtime -b events ^>"+adbfile+"/${Datelogcat}events_log.txt ^& "
				+ "echo $! ^>^>"+adbfile+"/logcatpid.txt;"
				+ " sleep 1800;"
				+ "kill -9 `cat "+adbfile+"/logcatpid.txt`;"
				+ "done ^& >"+com.Main.ThenToolsRun.datalocation+"/tempdata";
		Excute.execcmd(cmd_logcat,1,true);
	//	com.Main.ThenToolsRun.logger.log(Level.INFO,"active adb");
	}
	
	public void activekmsg(){
		Excute.execcmd("mkdir -p "+kmsgfile,2,true);
		String cmd_kmsg="echo while true;do Datekmsg=`date +%Y%m%d_%H_%M_%S`;"
				+ "cat /proc/kmsg ^>"+kmsgfile+"/${Datekmsg}_kmsg.txt ^& "
				+ "echo $! ^>"+kmsgfile+"/kmsgpid.txt; "
				+ "sleep 1800; "
				+ "kill -9 `cat "+kmsgfile+"/kmsgpid.txt`;"
		    	+ "done ^& >>"+com.Main.ThenToolsRun.datalocation+"/tempdata";
		Excute.execcmd(cmd_kmsg,1,true);
	//	com.Main.ThenToolsRun.logger.log(Level.INFO,"active kernel_kmsg");
	}
	
	public void activedmesg(){
		Excute.execcmd("mkdir -p "+dmesgfile,2,true);
		String cmd_dmesg="echo while true;do Datedmesg=`date +%Y%m%d_%H_%M_%S`;"
				+ "dmesg^>"+dmesgfile+"/${Datedmesg}_dmesg.txt;"
				+ "sleep 30;"
				+ "done ^& >>"+com.Main.ThenToolsRun.datalocation+"/tempdata";
		Excute.execcmd(cmd_dmesg,1,true);
	//	com.Main.ThenToolsRun.logger.log(Level.INFO,"active kernel_dmesg");
	}
	
	public boolean checklogfile(){
		boolean exist=false;
		if(com.Main.ThenToolsRun.selectedID!=null){
		String[] adbresult=Excute.execcmd(com.Main.ThenToolsRun.extraBinlocation+"/adb.exe shell cd "+adbfile,1,true);
		String[] dmesgresult=Excute.execcmd(com.Main.ThenToolsRun.extraBinlocation+"/adb.exe shell cd "+dmesgfile,1,true);
		String[] kmsgresult=Excute.execcmd(com.Main.ThenToolsRun.extraBinlocation+"/adb.exe shell cd "+kmsgfile,1,true);
		if(hasdmesg){
		if(adbresult[0].toString().equals("")&&(dmesgresult[0].toString().equals("")||kmsgresult[0].toString().equals(""))){
			exist=true;
		}
		}else{
			if(adbresult[0].toString().equals("")){
				exist=true;
			}
		}
		}
		//com.Main.ThenToolsRun.logger.log(Level.INFO,"Check "+adbresult[0].toString()+"  "+dmesgresult[0].toString()+"   "+kmsgresult[0].toString());
		return exist;
	}
	
	public boolean getActivelogthreadrun(){
		return activelogthreadrun;
	}
	public boolean getexitloop(){
		return exitloop;
	}
	public void setexitloop(boolean exitloop){
		this.exitloop=exitloop;
	}
	class ActivelogThread implements Runnable {

		public ActivelogThread() {
		
		}

		public void run() {
			activelogthreadrun=true;
			int count=0;
			while(com.Main.ThenToolsRun.selectedID==null){
				if(exitloop){
					break;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
			do{
				if(exitloop){
					break;
				}
				if (CheckUE.getisroot().equals("Yes")){
					activeadb();
					activekmsg();
					whatlog=" adb & kmsg";
				}else{
					activeadb();
					if(checkdmesg()){
						activedmesg();
						whatlog=" adb & dmesg";
					}else{
						whatlog=" adb ";
					}	
				}
				Excute.execcmd("shell <"+com.Main.ThenToolsRun.datalocation+"/tempdata",3,false);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}while(!checklogfile());
			com.Main.ThenToolsRun.logger.log(Level.INFO,"loop "+count+" times");
			if(exitloop){
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
			}else{
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
					com.Main.ThenToolsRun.QcomUImain.getwaitui().close();
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Active "+whatlog+" logs for successfully with boot!");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Active "+whatlog+" logs for successfully!", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
			}
				File file=new File(com.Main.ThenToolsRun.datalocation+"/tempdata");
				if(file.exists()){
					file.deleteOnExit();
					com.Main.ThenToolsRun.logger.log(Level.INFO,"del tempdata when exit!");
				}

			
			activelogthreadrun=false;
		}
	}
}
