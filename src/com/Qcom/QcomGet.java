package com.Qcom;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.Functions.EnDecrypt;
import com.Functions.Excute;
import com.Functions.Helper;
import com.Main.MainUI;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;

public class QcomGet {

    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy,MM,dd");
    private SimpleDateFormat sDateFormatnone = new SimpleDateFormat("HHmmss");
	String PCtime;
	String Mainlog;//path
	String happentime;
	String nonetime;
	String Otherlog;//path
	String logfolder;//path
	String errorgetlog;//getlog错误信息
	String timeornone;
	boolean getlogthreadrun=false;
	EnDecrypt check=new EnDecrypt();
	
	//Helper helper=new Helper();
	
	public void run(String timeornone,String happentime,boolean iscompression){
		File logfolderfile=new File(logfolder);
		if(logfolderfile.exists()){
			Helper.delFolder(logfolder);
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
		com.Main.ThenToolsRun.logger.log(Level.INFO,"get log run()");
		//线程启动
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		GetlogThread getlogthread = new GetlogThread(iscompression);
		new Thread(getlogthread).start();
	}
	
	
	public boolean filepathexist(String timeornone,String happentime){
		this.happentime=happentime;
		this.timeornone=timeornone;
		PCtime = sDateFormat.format(new Date());
		String[] PCtimearray=PCtime.split(",");
		PCtime=PCtimearray[0]+"Y"+PCtimearray[1]+"M"+PCtimearray[2]+"D";
		nonetime=sDateFormatnone.format(new Date());
		
		if(timeornone.equals("None")){
			logfolder=com.Main.ThenToolsRun.ThenLogfile+"/QcomPlatform/PCtime"+PCtime+"/CatchLog_nonetime_"+nonetime;
			Mainlog=logfolder+"/CatchLog/";
			Otherlog=logfolder+"/Otherlog";
		}else{
			logfolder=com.Main.ThenToolsRun.ThenLogfile+"/QcomPlatform/PCtime"+PCtime+"/CatchLog_happentime_"+happentime;
			Mainlog=logfolder+"/CatchLog/";
			Otherlog=logfolder+"/Otherlog";
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
	
	public boolean checkhappentime(){
		//				happentime=Mon+Day+"_"+Hour+"H"+Min+"M";0309_18h00m    03-09 20:14
		String time=happentime.substring(0, 2)+"-"+happentime.substring(2, 4)+" "+happentime.substring(5, 7)+":"+happentime.substring(8, 10);
		String[] result=Excute.execcmd("for /r \""+Mainlog+"\" %i in (*) do (findstr /r /c:\""+time+":[0-9][0-9]\" \"%i\")", 1, true);
		Pattern p=Pattern.compile(time+":[0-9][0-9].[0-9][0-9][0-9]");//03-09 22:04:03.779 
		Matcher m=p.matcher(result[0].toString());
		//com.Main.ThenToolsRun.logger.log(Level.INFO,result[0].toString());
		com.Main.ThenToolsRun.logger.log(Level.INFO,"check happentime "+time+" end");
		return m.find();
	}
	
	public String getlog(){
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(15);//******************
		String returnstr="";
		//main
		Excute.execcmd("pull /sdcard/CatchLog \""+Mainlog+"\"",3,true);//1
		if(timeornone.equals("Time")){
		if(!checkhappentime()){
			returnstr="nohappentime";
			return returnstr;
		}
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
	
	public String geterrorgetlog(){
		return errorgetlog;
	}
	
	class GetlogThread implements Runnable {
		//boolean output=false;
		boolean iscompression;
		public GetlogThread(boolean iscompression) {
			this.iscompression=iscompression;
		}

		public void run() {
			getlogthreadrun=true;
			errorgetlog=getlog();
			//nohappentime
			if(errorgetlog.equals("nohappentime")&&check.isok()){
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
				String time=happentime.substring(0, 2)+"-"+happentime.substring(2, 4)+" "+happentime.substring(5, 7)+":"+happentime.substring(8, 10);
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No happentime : "+time+" in CatchLog, pls try correct time again! ",
						"Message", JOptionPane.ERROR_MESSAGE);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"No happentime : "+time+" in CatchLog, pls try correct time again! ");
				//File file=new File(logfolder);
				Helper.delFolder(logfolder);
				getlogthreadrun=false;
			}else{
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
					if(!Helper.compression(targetfilestr.toString(), sourcefilestr.toString())){
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
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Los is saved in "+logfolder+" ",
						"Message", JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}
	//get ue current time 
	public String[] getUEtime(){
		String[] time=new String[8];
		List<String> timelist=Excute.returnlist2("date");
		for(String str:timelist){
			if(str.equals("")){
				continue;
			}
			time=str.split("\\s+|:");
			//Tue Apr  5 17:40:59 IST 2016
			String Mon=time[1];
			switch(Mon){
			case "Jan":   Mon="01";break;
			case "Feb":   Mon="02";break;
			case "Mar":   Mon="03";break;
			case "Apr":   Mon="04";break;
			case "May":   Mon="05";break;
			case "Jun":   Mon="06";break;
			case "Jul":   Mon="07";break;
			case "Aug":   Mon="08";break;
			case "Sep":   Mon="09";break;
			case "Oct":   Mon="10";break;
			case "Nov":   Mon="11";break;
			case "Dec":   Mon="12";break;
			default : Mon="00";break;
			}
			time[1]=Mon;
			String Day=time[2];
			switch(Day){
			case "1": Day="01";break;
			case "2": Day="02";break;
			case "3": Day="03";break;
			case "4": Day="04";break;
			case "5": Day="05";break;
			case "6": Day="06";break;
			case "7": Day="07";break;
			case "8": Day="08";break;
			case "9": Day="09";break;
			}
			time[2]=Day;
		}
		return time;
	}
	
}
