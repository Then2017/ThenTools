package com.Qcom;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.CheckUE;
import com.Functions.Excute;
import com.Functions.LoggerUtil;

public class QcomActiveBootPC {
	    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
	    private SimpleDateFormat sDateFormatPath = new SimpleDateFormat("yyyy,MM,dd");
	    
		public boolean activelogthreadrun=false;
	 	boolean hasdmesg;
	 	boolean exitloop=false;
	 	
		String logpath;//folder path
		String logfolder;//
		String adbfile;
		String kmsgfile;
		String dmesgfile;
		
		public void run() {
			String PCtime = sDateFormatPath.format(new Date());
			String[] PCtimearray=PCtime.split(",");
			PCtime=PCtimearray[0]+"Y"+PCtimearray[1]+"M"+PCtimearray[2]+"D";
			logpath=com.Main.ThenToolsRun.ThenLogfile+"/QcomPlatform/PCtime"+PCtime;
			File logpathfile=new File(logpath);
			if(!logpathfile.exists()){
				logpathfile.mkdirs();
			}
			logfolder=logpath+"/BootLog_"+sDateFormat.format(new Date()) ;
			File logfolderfile=new File(logfolder);
			if(!logfolderfile.exists()){
				logfolderfile.mkdirs();
			}
			adbfile=logfolder+"/adb";
			kmsgfile=logfolder+"/kmsg";
			dmesgfile=logfolder+"/dmesg";
			File adbfolder=new File(adbfile);
			if(!adbfolder.exists()){
				adbfolder.mkdirs();
			}
			File kmsgfolder=new File(kmsgfile);
			if(!kmsgfolder.exists()){
				kmsgfolder.mkdirs();
			}
			File dmesgfolder=new File(dmesgfile);
			if(!dmesgfolder.exists()){
				dmesgfolder.mkdirs();
			}
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
					int count=0;
					do{
						String date=sDateFormat.format(new Date());
						Excute.execcmd("logcat -v threadtime -b radio >"+adbfile+"/"+count+"_radio_"+date+".txt",3,false);
						Excute.execcmd("logcat -v threadtime -b events >"+adbfile+"/"+count+"_events_"+date+".txt",3,false);
						Excute.execcmd("logcat -v threadtime >"+adbfile+"/"+count+"_main_system_"+date+".txt",3,true);
						count++;
					}while(!exitloop);
		//	com.Main.ThenToolsRun.logger.log(Level.INFO,"active adb");
		}
		
		public void activedmesg(){	
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int count=0;
				do{
					String date=sDateFormat.format(new Date());
					if (CheckUE.getisroot().equals("Yes")){
						activekmsg();
					}else{
						if(checkdmesg()){
							Excute.execcmd("shell dmesg >"+dmesgfile+"/"+count+"_dmesg_"+date+".txt",3,true);
						}
					}
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
				}while(!exitloop);
				}
			}).start();
		//	com.Main.ThenToolsRun.logger.log(Level.INFO,"active kernel_kmsg");
		}
		
		public void activekmsg(){
			int count=0;
				do{
					String date=sDateFormat.format(new Date());
					Excute.execcmd("shell kmsg >"+kmsgfile+"/"+count+"_kmsg_"+date+".txt",3,true);
					count++;
				}while(!exitloop);
		//	com.Main.ThenToolsRun.logger.log(Level.INFO,"active kernel_dmesg");
		}
		
		public String getlogfolder(){
			return logfolder;
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
				while(com.Main.ThenToolsRun.selectedID==null){
					if(exitloop){
						break;
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
					}
				}
				activelogthreadrun=false;
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
				com.Main.ThenToolsRun.logger.log(Level.INFO,"run boot to pc logs finish");
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
				if(!exitloop){
					activedmesg();
					activeadb();
				}
			}
		}
}
