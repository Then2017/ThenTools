package com.Monkey;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.Functions.CheckUE;
import com.Functions.Excute;
import com.Functions.Helper;

public class MonkeyGet {

    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy,MM,dd");
    private SimpleDateFormat sDateFormatget = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
	String PCtime;
	String Mainlog;//path
	String gettime;
	String Otherlog;//path
	String logfolder;//path
	String platform;
	boolean getlogthreadrun=false;
	Helper helper=new Helper();
	
	
	public void run(boolean iscompression){
		File logfolderfile=new File(logfolder);
		if(logfolderfile.exists()){
			helper.delFolder(logfolder);
			com.Main.ThenToolsRun.logger.log(Level.INFO,logfolderfile.getAbsolutePath()+" Del!");
		}
		File file=new File(Mainlog);
		if (file.exists()){
			com.Main.ThenToolsRun.logger.log(Level.INFO,file.getAbsolutePath()+" exists!");
		}else{
			file.mkdirs();
		}  
		File Otherlogfile=new File(Otherlog);
		if (Otherlogfile.exists()){
			com.Main.ThenToolsRun.logger.log(Level.INFO,Otherlogfile.getAbsolutePath()+" exists!");
		}else{
			Otherlogfile.mkdirs();
		}
		com.Main.ThenToolsRun.logger.log(Level.INFO,"Monkey get log run()");
		//线程启动
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		GetlogThread getlogthread = new GetlogThread(iscompression);
		new Thread(getlogthread).start();
	}
	//Stop monkey
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
			}
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(20);//******************
	com.Main.ThenToolsRun.logger.log(Level.INFO,"Stop monkey in phone");
	}
	
	//create files
	public boolean filepathexist(){
		this.platform=com.Main.ThenToolsRun.platform;
		PCtime = sDateFormat.format(new Date());
		String[] PCtimearray=PCtime.split(",");
		PCtime=PCtimearray[0]+"Y"+PCtimearray[1]+"M"+PCtimearray[2]+"D";
		gettime=sDateFormatget.format(new Date());
		if(platform.contains("MT")){
			logfolder=com.Main.ThenToolsRun.ThenLogfile+"/Monkey/PCtime"+PCtime+"/MTK_Monkey_"+gettime;
			Mainlog=logfolder+"/MTKLog/";
			Otherlog=logfolder+"/Otherlog";
		}else{
		//	if(CheckUE.platform.contains("msm")){
				logfolder=com.Main.ThenToolsRun.ThenLogfile+"/Monkey/PCtime"+PCtime+"/Qcom_Monkey_"+gettime;
				Mainlog=logfolder+"/CatchLog/";
				Otherlog=logfolder+"/Otherlog";
	//		}
		}
		
		File file=new File(Mainlog);
		if (file.exists()){
			com.Main.ThenToolsRun.logger.log(Level.INFO,file.getAbsolutePath()+" exists!");
			return true;
		}else{
			file.mkdirs();
		}  
		return false;
	}
	
	public String getlog(){
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(15);//******************
		String returnstr="";
		//main
		if(platform.contains("MT")){
			Excute.execcmd("pull /sdcard/mtklog \""+Mainlog+"\"",3,true);//1
		}else{
		Excute.execcmd("pull /sdcard/CatchLog \""+Mainlog+"\"",3,true);//1
		}
		//BT
		Excute.execcmd("pull /sdcard/btsnoop_hci.log \""+Mainlog+"\"",3,true);//2
		//Moneky
		Excute.execcmd("pull /sdcard/monkeylog.txt \""+Mainlog+"\"",3,true);//3
		//recovery root
		Excute.execcmd("pull /cache/recovery/ \""+Otherlog+"\"",3,true);//4
		//systemreboot root
		Excute.execcmd("pull /sys/fs/pstore \""+Otherlog+"\"",3,true);//5
		//anr
		Excute.execcmd("pull /data/anr \""+Otherlog+"\"",3,true);//6
		//root
		Excute.execcmd("pull /data/tombstones \""+Otherlog+"\"tombstones",3,true);//7
		
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(20);//******************
		
		Excute.execcmd("pull /data/rtt_dump* \""+Otherlog+"\"",3,true);//8
		Excute.execcmd("pull /data/aee_exp \""+Otherlog+"\"data_aee_exp",3,true);//9
		Excute.execcmd("pull /data/mobilelog \""+Otherlog+"\"data_mobilelog",3,true);//10
		Excute.execcmd("pull /data/core \""+Otherlog+"\"data_core",3,true);//11
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(30);//******************
		//systemstatus
		Excute.execcmd("shell ps -t >\""+Otherlog+"\"/ps.txt",3,true);//12
		Excute.execcmd("shell top -t -m 5 -n 2 >\""+Otherlog+"\"/top.txt",3,true);//13
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(40);//******************
		Excute.execcmd("shell cat /proc/meminfo >\""+Otherlog+"\"/meminfo.txt",3,true);//14
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
		Excute.execcmd("shell cat /proc/buddyinfo >\""+Otherlog+"\"/buddyinfo.txt",3,true);//15
		Excute.execcmd("shell cat /proc/sched_debug >\""+Otherlog+"\"/sched+debug.txt",3,true);//16
		Excute.execcmd("shell cat proc/interrupts >\""+Otherlog+"\"/interrupts.txt",3,true);//17
		Excute.execcmd("shell getprop >\""+Otherlog+"\"/getprop.txt",3,true);//18
		Excute.execcmd("shell service list >\""+Otherlog+"\"/servicelist.txt",3,true);//19
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(60);//******************
		Excute.execcmd("shell dumpsys >\""+Otherlog+"\"/dumpsys.txt",3,true);//20
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(70);//******************
		Excute.execcmd("shell dumpstate >\""+Otherlog+"\"/dumpstate.txt",3,true);//21
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(80);//******************
		Excute.execcmd("shell pm list package >\""+Otherlog+"\"/pmlist.txt",3,true);//22
		
		//version23
		List<String> versionlist=Excute.returnlist2("getprop");
		for(String str:versionlist){
			if(str.equals("")){
				continue;
			}
			if(str.contains("internal.version")){
			//com.Main.ThenToolsRun.logger.log(Level.INFO,str);
			String[] version=str.split(": ");
			Excute.execcmd("echo internal version >\""+logfolder+"\"/Version"+version[1]+".txt",1,true);
			}
		}
		//getdmesg24
		List<String> timelist=Excute.returnlist2("date");
		for(String str:timelist){
			if(str.equals("")){
				continue;
			}
			com.Main.ThenToolsRun.logger.log(Level.INFO,"UE time is "+str+" when get dmesg log");
			String[] time=str.split("\\s+|:");
			Excute.execcmd("shell dmesg >\""+Mainlog+"\"dmesg_UEtime_"+time[7]+time[1]+time[2]+"_"+time[3]+"h"+time[4]+"m"+time[5]+"s.txt",3,true);
			
		}
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(90);//******************
		com.Main.ThenToolsRun.logger.log(Level.INFO,"get catchlog from phone");
		returnstr="OK";
		return returnstr;
	}
	
	public String getMainlog(){
		return Mainlog;
	}
	
	public boolean getGetlogthreadrun(){
		return getlogthreadrun;
	}
	
	class GetlogThread implements Runnable {
		boolean iscompression;
		public GetlogThread(boolean iscompression) {
			this.iscompression=iscompression;
		}

		public void run() {
			getlogthreadrun=true;
			getlog();
			//压缩文件25
			if(iscompression){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"start to compression log folder");
					String[] str=Mainlog.split("/|\\\\");
					//C:\Users\Then\Desktop\ThenLog\Qcom\PCtime2016Y03M14D\CatchLog_nonetime_000029
					StringBuffer sourcefilestr=new StringBuffer();
					StringBuffer targetfilestr=new StringBuffer();
					for(int i=0;i<str.length-1;i++){
						sourcefilestr.append(str[i]+"/");
					}
					for(int i=0;i<str.length-2;i++){
						targetfilestr.append(str[i]+"/");
					}
					targetfilestr.append(str[str.length-2]);
				//	com.Main.ThenToolsRun.logger.log(Level.INFO,targetfilestr.toString()+"  "+sourcefilestr.toString()+"   "+str.length);
					if(!helper.compression(targetfilestr.toString(), sourcefilestr.toString())){
						com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
						com.Main.ThenToolsRun.logger.log(Level.INFO,"Compression logs failed by 7z");
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Compression logs failed by 7z",
								"Message", JOptionPane.ERROR_MESSAGE);
					}else{
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Compression logs success by 7z");
					}
				}
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
				com.Main.ThenToolsRun.logger.log(Level.INFO,"GetlogThread end,all is ok!");
				getlogthreadrun=false;
				if(platform.contains("MT")){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Los is saved in "+Mainlog.substring(0,Mainlog.length()-8)+" ",
							"Message", JOptionPane.INFORMATION_MESSAGE);
				}else{
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Los is saved in "+Mainlog.substring(0,Mainlog.length()-10)+" ",
						"Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
	}
}
