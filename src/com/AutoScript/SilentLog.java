package com.AutoScript;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.Functions.CheckUE;
import com.Functions.Excute;

public class SilentLog {
	
	    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
	    String PCtime;
		String adbfile;
		String kmsgfile;
		String dmesgfile;
		String whatlog="null";
		public boolean activelogthreadrun=false;
	 	boolean hasdmesg;
	 	
		public void start() {
		    PCtime = sDateFormat.format(new Date());
			adbfile="/sdcard/CatchLog/PCtime"+PCtime+"/adb";
			kmsgfile="/sdcard/CatchLog/PCtime"+PCtime+"/kmsg";
			dmesgfile="/sdcard/CatchLog/PCtime"+PCtime+"/dmesg";
			
			//Ïß³ÌÆô¶¯
			ActivelogThread activelogthread = new ActivelogThread();
			new Thread(activelogthread).start();
			com.Main.ThenToolsRun.logger.log(Level.INFO,"active silent log");
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
					+ "sleep 120;"
					+ "done ^& >>"+com.Main.ThenToolsRun.datalocation+"/tempdata";
			Excute.execcmd(cmd_dmesg,1,true);
		//	com.Main.ThenToolsRun.logger.log(Level.INFO,"active kernel_dmesg");
		}
		
		public boolean checklogfile(){
			boolean exist=false;
			String adbresult=Excute.execcmd2("cd "+adbfile);
			String dmesgresult=Excute.execcmd2("cd "+dmesgfile);
			String kmsgresult=Excute.execcmd2("cd "+kmsgfile);
			if(hasdmesg){
			if(adbresult.toString().equals("")&&(dmesgresult.toString().equals("")||kmsgresult.toString().equals(""))){
				exist=true;
			}else{
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Check file not ok: "+adbresult.toString()+"  "+dmesgresult.toString()+"   "+kmsgresult.toString());
			}
			}else{
				if(adbresult.toString().equals("")){
					exist=true;
				}else{
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Check file not ok: "+adbresult.toString()+"  "+dmesgresult.toString()+"   "+kmsgresult.toString());
				}
			}
			return exist;
		}
		
		public boolean getActivelogthreadrun(){
			return activelogthreadrun;
		}
		
		class ActivelogThread implements Runnable {

			public ActivelogThread() {
			
			}

			public void run() {
				activelogthreadrun=true;
				if (CheckUE.getisroot().equals("Yes")){
					activeadb();
					activekmsg();
					Excute.execcmd("shell <"+com.Main.ThenToolsRun.datalocation+"/tempdata",3,false);
					whatlog=" adb & kmsg";
				}else{
					activeadb();
					if(checkdmesg()){
						activedmesg();
						whatlog=" adb & dmesg";
					}else{
						whatlog=" adb ";
					}
					Excute.execcmd("shell <"+com.Main.ThenToolsRun.datalocation+"/tempdata",3,false);
				}
				if(checklogfile()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Active "+whatlog+" logs with silent for successfully!");
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
