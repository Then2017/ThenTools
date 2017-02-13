package com.Monkey;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.Functions.CheckUE;
import com.Functions.Excute;
import com.Functions.Helper;
import com.Functions.LoggerUtil;
import com.Monkey.MonkeyActive.ActiveMonkeyThread;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

public class MonkeyMonitor {
	boolean activemonkeythreadrun=false;
	private SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss MMdd-yyyy");
	private SimpleDateFormat sfileFormat = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
	//��������
	int viewrow; //Ԥ������
	int viewrowcount;//Ԥ����������
	int crashcount;//Crash��������
	boolean startprint=false;
	String logpath;
	Timer batterytimer;

	//battery
	String capacity;
	String brightness;
	String temp;
	StringBuffer strbattery=new StringBuffer();
	//�Ͽ�����
	boolean noconnect,terminated,isreconnect;
	
	public void run(String[] monkeyinfo,boolean isreconnect){
		this.isreconnect=isreconnect;
		String seed=monkeyinfo[0];
		String intervals=monkeyinfo[1];
		String monkeyradio=monkeyinfo[2];
		String packages=monkeyinfo[3];
		//�߳�����
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		ActiveMonkeyThread activemonkeythread = new ActiveMonkeyThread(seed,intervals,monkeyradio,packages);
		new Thread(activemonkeythread).start();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"active monkey monitor");
		
	}
	//set as original
	public void settooriginal(){
		viewrow=3; //Ԥ������
		viewrowcount=0;//Ԥ����������
		crashcount=0;//Crash��������
		startprint=false;
		noconnect=false;terminated=false;
		com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().setText("");
	}

	//check battery 
	public void checkbattery(){
//	    public static String capacity="/sys/class/power_supply/battery/capacity";
//	    public static  String current="/sys/class/power_supply/battery/current_now";
//	    public static  String voltage="/sys/class/power_supply/battery/voltage_now";
//	    public static String temp="/sys/class/power_supply/battery/temp";
//	    public static  String status="/sys/class/power_supply/battery/status";
//	    public static  String chargetype="/sys/class/power_supply/battery/charge_type";
//	    public static  String health="/sys/class/power_supply/battery/health";
//	    public static String brightness = "/sys/class/leds/lcd-backlight/brightness";
//
//	    //mt6735m
//	    public static  String mt6735m_current="/sys/devices/platform/battery/FG_Battery_CurrentConsumption";
//	    public static  String mt6735m_voltage="/sys/class/power_supply/battery/batt_vol";
//	    public static String mt6735m_temp="/sys/class/power_supply/battery/batt_temp";
//	    public static  String mt6735m_chargetype="NA";
		strbattery.setLength(0);
		//batterytimer=null;
		batterytimer=new Timer();
		batterytimer.schedule(new TimerTask(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(com.Main.ThenToolsRun.platform.contains("mt6735")){
					capacity=Excute.execcmd2("cat /sys/class/power_supply/battery/capacity");
					brightness=Excute.execcmd2("cat /sys/class/leds/lcd-backlight/brightness");
					temp=Excute.execcmd2("cat /sys/class/power_supply/battery/batt_temp");
				}else{
				capacity=Excute.execcmd2("cat /sys/class/power_supply/battery/capacity");
				brightness=Excute.execcmd2("cat /sys/class/leds/lcd-backlight/brightness");
				temp=Excute.execcmd2("cat /sys/class/power_supply/battery/temp");
				}
				capacity=capacity.replace("\n", "");
				brightness=brightness.replace("\n", "");
				temp=Integer.parseInt(temp.replace("\n", ""))/10+"";
				
				String strbatterytemp=sDateFormat.format(new Date())+":  "
				+"Battery Info: Capacity="+capacity+"%,"
						+ " Brightness="+brightness+", "
								+ "temperature="+temp+"��";
				//check monkey status
				if(!CheckUE.checkMonkeyrun()){
					strbatterytemp=strbatterytemp+",not run!";
					com.Main.ThenToolsRun.logger.log(Level.INFO,"check monkey run status failed "+strbatterytemp);
				}
				strbattery.append(strbatterytemp+"\n");
				com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append(strbatterytemp+"\n");
			}
			
		}, 3000,1800000);//1800000
	
	}
	//excute cmd
	public void monkeyexeccmd(String command){
		try {
			if(com.Main.ThenToolsRun.getdevices.getDevice()!=null){
				com.Main.ThenToolsRun.getdevices.getDevice().executeShellCommand(command, new MultiLineReceiver(){
					@Override
					public boolean isCancelled() {
						// TODO Auto-generated method stub
						return false;
					}
					@Override
					public void processNewLines(String[] arg0) {
						// TODO Auto-generated method stub
					    for(String line:arg0) { //����������ݻ�������   3//CRASH:       #Intent
					    	if(line.equals("Terminated")){//�ж���ֹͣ���ǶϿ�������
					    		noconnect=false;
					    		terminated=true;
					    	}else if(line.equals("")){
					    		noconnect=true;
					    	}else{
				        	if(line.contains("CRASH:")){
				        	startprint=true;
				        	crashcount++;
				        	com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append(
				        			"*****************************************"
				        			+ "Issue Num="+crashcount
				        			+"*****************************************\n");
				        	Helper.writetoFile(logpath, "*****************************************"
				        			+ "Issue Num="+crashcount
				        			+"*****************************************", true);
				        	}
				        	Helper.writetoFile(logpath, sDateFormat.format(new Date())+":  "+line, true);
					    	}
				        }

					    if(startprint==true){
						    for(String line:arg0) { //����������ݻ�������   3
						    	line=sDateFormat.format(new Date())+":  "+line;
					        	com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append(line+"\n");
					        	viewrowcount++;
							    //�ﵽԤ������
							    if(viewrowcount>=viewrow){
							    	startprint=false;
							    	//��β��һ��
						        	com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append("\n");
						        	 com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().setCaretPosition(
						        			 com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().getText().length());
						        	viewrowcount=0;
						        	break;
							    }
					        }
					    }
					   }
					 
					
				},999999999,TimeUnit.SECONDS);
			}
		} catch (TimeoutException | AdbCommandRejectedException
				| ShellCommandUnresponsiveException | IOException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
	  //  com.Main.ThenToolsRun.logger.log(Level.INFO, output.toString());
	}
	//reconnect run 
	
	//runSystem
	public void runSystem(String seed,String intervals,boolean append){
		//echo "monkey -s %seed% --ignore-crashes --ignore-timeouts --ignore-security-exceptions 
		//-v -v -v --throttle %t% 1200000000 > /storage/sdcard0/monkeylog.txt 2>&1 & ">tempmonkey.txt
		com.Main.ThenToolsRun.logger.log(Level.INFO,"System monkey seed="+seed+"  intervals="+intervals);
		if(!append){
		monkeyexeccmd("monkey -s "+seed+" --ignore-crashes --ignore-timeouts --ignore-security-exceptions "
				+ "-v -v -v --throttle "+intervals+" 1200000000 "+com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "monkey1"));
		}else{
			monkeyexeccmd("monkey -s "+seed+" --ignore-crashes --ignore-timeouts --ignore-security-exceptions "
					+ "-v -v -v --throttle "+intervals+" 1200000000 "+com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "monkey2"));
		}
	}
	//runPackages
	public void runPackages(String seed,String intervals,String packages,boolean append){
		com.Main.ThenToolsRun.logger.log(Level.INFO,"Packages monkey seed="+seed+"  intervals="+intervals+" packages="+packages);
		if(!append){
		monkeyexeccmd("monkey -s "+seed+" "+packages+" --ignore-crashes --ignore-timeouts --ignore-security-exceptions "
				+ "-v -v -v --throttle "+intervals+" 1200000000 "+com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "monkey1"));
		}else{
			monkeyexeccmd("monkey -s "+seed+" "+packages+" --ignore-crashes --ignore-timeouts --ignore-security-exceptions "
					+ "-v -v -v --throttle "+intervals+" 1200000000 "+com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "monkey2"));
		}
	}
	//runCustomize
	public void runCustomize(String seed,String intervals,String packages,boolean append){
		packages=packages.replace("\n", "");
		com.Main.ThenToolsRun.logger.log(Level.INFO,"Customize monkey seed="+seed+"  intervals="+intervals+" packages="+packages);
		if(!append){
		monkeyexeccmd("monkey -s "+seed+" "+packages
				+ " -v -v -v --throttle "+intervals+" 1200000000 "+com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "monkey1"));
		}else{
			monkeyexeccmd("monkey -s "+seed+" "+packages
					+ " -v -v -v --throttle "+intervals+" 1200000000 "+com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Command", "monkey2"));
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
	//get run flag
	public boolean getActiveMonkeythreadrun(){
		return activemonkeythreadrun;
	}
	//set isCancelled
	public void cancelbatterytimer(){
		if(batterytimer!=null){
			batterytimer.cancel();
			batterytimer=null;
		}
	}
	//get strbattery
	public String getstrbattery(){
		return strbattery.toString();
	}
	//set terminated
	public void setTerminated(boolean terminated){
		this.terminated=terminated;
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
			activemonkeythreadrun=true;
			//check monkey run

			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(30);//******************
			//create folder file
			File logfolder=new File(com.Main.ThenToolsRun.ThenLogfile+"/MonkeyMonitor");
			if(!logfolder.exists()){
				logfolder.mkdirs();
			}
			File logfile=new File(com.Main.ThenToolsRun.ThenLogfile+"/MonkeyMonitor/"+
					com.Main.ThenToolsRun.getdevices.getDevice().toString()+"_"+sfileFormat.format(new Date())+".log");
			//������log�ļ�
			if(!logfile.exists()){
				try {
					logfile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logpath=logfile.getAbsolutePath();
				com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().getlblLogpath().setText(logpath);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"create new monkey monitor log: "+logfile.getName());
			}
			//��ԭ����
			settooriginal();
			
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
			com.Main.ThenToolsRun.logger.log(Level.INFO,"Monkey Monitor active successful");
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
			activemonkeythreadrun=false;
			String[] time=getUEtime();
        	Helper.writetoFile(logpath, sDateFormat.format(new Date())+":  "+"This log created by Monkey Monitor of ThenTools, "
        			+ "UE time is "+time[1]+time[2]+" "+time[3]+":"+time[4], true);
        	com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append(
        			sDateFormat.format(new Date())+":  "+"This log created by Monkey Monitor of ThenTools, "
        			+ "UE time is "+time[1]+time[2]+" "+time[3]+":"+time[4]+"\n");
        	//check battery
        	if(batterytimer!=null){
        		batterytimer.cancel();
        	}
        	checkbattery();
        	//start monkey
			if(monkeyradio.equals("System")){
				runSystem(seed,intervals,false);
			}else if(monkeyradio.equals("Packages")){
				runPackages(seed,intervals,packages,false);
			}else if(monkeyradio.equals("Customize")){
				runCustomize(seed,intervals,packages,false);
			}
			//reconnect monkey
			int countReconnect=0;
			while(noconnect&&!terminated&&isreconnect){
				if(com.Main.ThenToolsRun.selectedID!=null&&CheckUE.checkMonkeyrun()){
					countReconnect++;
		        	com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append(
		        			sDateFormat.format(new Date())+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		        	com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append(
		        			sDateFormat.format(new Date())+":  "+"Reconnect="+countReconnect+", "
		        			+ "UE time is "+time[1]+time[2]+" "+time[3]+":"+time[4]+"\n");
		        	 com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().setCaretPosition(
		        			 com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().getText().length());
				if(monkeyradio.equals("System")){
					runSystem(seed,intervals,true);
				}else if(monkeyradio.equals("Packages")){
					runPackages(seed,intervals,packages,true);
				}else if(monkeyradio.equals("Customize")){
					runCustomize(seed,intervals,packages,true);
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"continue monkey monitor");
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				}
			}
			com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor end");
		}
	}
}
