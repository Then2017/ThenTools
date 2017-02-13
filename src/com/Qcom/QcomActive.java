package com.Qcom;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.Functions.CheckUE;
import com.Functions.Excute;
import com.Functions.LoggerUtil;
import com.Qcom.QcomGet.GetlogThread;

public class QcomActive {
	
	    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
	   // private SimpleDateFormat phoneDateFormat = new SimpleDateFormat("mm_ss_SSS");

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
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
			ActivelogThread activelogthread = new ActivelogThread();
			new Thread(activelogthread).start();
			com.Main.ThenToolsRun.logger.log(Level.INFO,"active log");
//			Excute.execcmd("mkdir -p "+adbfile,2,true);
//			String cmd_logcat="while true;do Datelogcat=`date +%Y%m%d_%H_%M_%S`;"
//					+ "logcat -v time >"+adbfile+"/${Datelogcat}main_system_log.txt &"
//					+ " echo $! >"+adbfile+"/logcatpid.txt;"
//					+ "logcat -v time -b radio >"+adbfile+"/${Datelogcat}radio_log.txt &"
//					+ " echo $! >>"+adbfile+"/logcatpid.txt;"
//					+ "logcat -v time -b events >"+adbfile+"/${Datelogcat}events_log.txt & "
//					+ "echo $! >>"+adbfile+"/logcatpid.txt;"
//					+ " sleep 1800;"
//					+ "kill -9 `cat "+adbfile+"/logcatpid.txt`;"
//					+ "done & ";
//			Excute.execcmd(cmd_logcat,2,false);
//			Excute.execcmd("mkdir -p "+dmesgfile,2,true);
//			String cmd_dmesg="while true;do Datedmesg=`date +%Y%m%d_%H_%M_%S`;"
//					+ "dmesg>"+dmesgfile+"/${Datedmesg}_dmesg.txt;"
//					+ "sleep 120;"
//					+ "done & ";
//			Excute.execcmd(cmd_dmesg,2,false);
			

			
//			ActivelogThread activelogthread = new ActivelogThread();
//			new Thread(activelogthread).start();
//			ActivelogThread1 activelogthread1 = new ActivelogThread1();
//			new Thread(activelogthread1).start();
			
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
			String cmd_logcat=com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "log2")+"Datelogcat=`date +%Y%m%d_%H_%M_%S`;"
					+ "logcat -v threadtime ^>"+adbfile+"/${Datelogcat}main_system_log.txt ^&"
					+ " echo $! ^>"+adbfile+"/logcatpid.txt;"
					+ "logcat -v threadtime -b radio ^>"+adbfile+"/${Datelogcat}radio_log.txt ^&"
					+ " echo $! ^>^>"+adbfile+"/logcatpid.txt;"
					+ "logcat -v threadtime -b events ^>"+adbfile+"/${Datelogcat}events_log.txt ^& "
					+ "echo $! ^>^>"+adbfile+"/logcatpid.txt;"
					+ " sleep 1800;"
					+ com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "log1")+adbfile+"/logcatpid.txt`;"
					+ "done ^& >"+com.Main.ThenToolsRun.datalocation+"/tempdata";
			Excute.execcmd(cmd_logcat,1,true);
		//	com.Main.ThenToolsRun.logger.log(Level.INFO,"active adb");
		}
		
		public void activekmsg(){
			Excute.execcmd("mkdir -p "+kmsgfile,2,true);
			String cmd_kmsg=com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "log2")+"Datekmsg=`date +%Y%m%d_%H_%M_%S`;"
					+ "cat /proc/kmsg ^>"+kmsgfile+"/${Datekmsg}_kmsg.txt ^& "
					+ "echo $! ^>"+kmsgfile+"/kmsgpid.txt; "
					+ "sleep 1800; "
					+ com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "log1")+kmsgfile+"/kmsgpid.txt`;"
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
				//com.Main.ThenToolsRun.logger.log(Level.INFO,"Check file ok "+adbresult[0].toString()+"  "+dmesgresult[0].toString()+"   "+kmsgresult[0].toString());
				exist=true;
			}else{
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Check file not ok: "+adbresult.toString()+"  "+dmesgresult.toString()+"   "+kmsgresult.toString());
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Error: can't find log file in UE", 
						"Message", JOptionPane.ERROR_MESSAGE); 
			}
			}else{
				if(adbresult.toString().equals("")){
					//com.Main.ThenToolsRun.logger.log(Level.INFO,"Check file ok "+adbresult[0].toString()+"  "+dmesgresult[0].toString()+"   "+kmsgresult[0].toString());
					exist=true;
				}else{
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Check file not ok: "+adbresult.toString()+"  "+dmesgresult.toString()+"   "+kmsgresult.toString());
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Error: can't find log file in UE", 
							"Message", JOptionPane.ERROR_MESSAGE); 
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
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
					activekmsg();
					Excute.execcmd("shell <"+com.Main.ThenToolsRun.datalocation+"/tempdata",3,false);
					whatlog=" adb & kmsg";
				}else{
					activeadb();
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
					if(checkdmesg()){
						activedmesg();
						whatlog=" adb & dmesg";
					}else{
						whatlog=" adb ";
					}
					Excute.execcmd("shell <"+com.Main.ThenToolsRun.datalocation+"/tempdata",3,false);
				}
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(70);//******************
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				}
				Excute.execcmd("echo.>"+com.Main.ThenToolsRun.datalocation+"/tempdata",1,true);
				if(checklogfile()){
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Active "+whatlog+" logs for successfully!");
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
		
		
//		class ActivelogThread implements Runnable {
//			boolean output=false;
//			public ActivelogThread() {
//				this.output =output;
//			}
//			public void run() {
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//				}
//				Excute.execcmd("mkdir -p "+adbfile,2,true);
//				String cmd_logcat="while true;do Datelogcat=`date +%Y%m%d_%H_%M_%S`;"
//						+ "logcat -v time >"+adbfile+"/${Datelogcat}main_system_log.txt &"
//						+ " echo $! >"+adbfile+"/logcatpid.txt;"
//						+ "logcat -v time -b radio >"+adbfile+"/${Datelogcat}radio_log.txt &"
//						+ " echo $! >>"+adbfile+"/logcatpid.txt;"
//						+ "logcat -v time -b events >"+adbfile+"/${Datelogcat}events_log.txt & "
//						+ "echo $! >>"+adbfile+"/logcatpid.txt;"
//						+ " sleep 1800;"
//						+ "kill -9 `cat "+adbfile+"/logcatpid.txt`;"
//						+ "done & ";
//				Excute.execcmd(cmd_logcat,2,false);
//				com.Main.ThenToolsRun.logger.log(Level.INFO,"ActivelogThread end,all is ok!");
//			}
//		}
//		
//		class ActivelogThread1 implements Runnable {
//			boolean output=false;
//			public ActivelogThread1() {
//				this.output =output;
//			}
//			public void run() {
//				Excute.execcmd1("mkdir -p "+dmesgfile,2,true);
//				String cmd_dmesg="while true;do Datedmesg=`date +%Y%m%d_%H_%M_%S`;"
//						+ "dmesg>"+dmesgfile+"/${Datedmesg}_dmesg.txt;"
//						+ "sleep 120;"
//						+ "done & ";
//				Excute.execcmd1(cmd_dmesg,2,false);
//				com.Main.ThenToolsRun.logger.log(Level.INFO,"ActivelogThread1 end,all is ok!");
//			}
//		}
		
}
