package com.Functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.Main.MainUI;
import com.android.ddmlib.IDevice;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import jdk.internal.dynalink.beans.StaticClass;

public class CheckUE {
	static String[] IDlist={""};
	static JComboBox deviceslist;
	static JLabel lblDevicestatus;
	static JLabel lblDeviceInfo;
	static boolean nodevice;
	static IDevice[] devices;
	static IDevice device;
	
	static String logstatus="No device!";
	static String isroot="No device!";
	static String isdebug="No device!";
	//mainUI frame = new mainUI();
	
	public void run(){
	    if (!com.Main.ThenToolsRun.getdevices.initialize()) {
	    	com.Main.ThenToolsRun.getdevices.initialize();
		 }
//		Timer timer;
//	    timer = new Timer();  
//	    timer.schedule(new TimerTask(){
//	    		public void run(){
//    				checkinfo();
//	    			checkstatus();
//    				nodevice=true;
//	    			if(!Arrays.equals(IDlist, getdeviceslist())){
//	    				IDlist=getdeviceslist();
//	    				deviceslist =com.Main.ThenToolsRun.mainFrame.getDeviceslist();
//	    				deviceslist.removeAllItems();
//	    				for(String str:IDlist){
//	    					deviceslist.addItem(str);
//		    				com.Main.ThenToolsRun.logger.log(Level.INFO,"update devices ID="+str);
//		    				nodevice=false;
//	    				}
//	    				if(nodevice){
//	    				com.Main.ThenToolsRun.selectedID=null;
//	    				com.Main.ThenToolsRun.logger.log(Level.INFO,"select devices ID="+com.Main.ThenToolsRun.selectedID);
//	    				}
//	    			//	checkinfo();
//	    			}
//	    		}
//	},0,1000); 
	    new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				}
				checkinfo();
    			checkstatus();
				nodevice=true;
    			if(!Arrays.equals(IDlist, getdeviceslist())){
    				IDlist=getdeviceslist();
    				deviceslist =com.Main.ThenToolsRun.mainFrame.getDeviceslist();
    				deviceslist.removeAllItems();
    				for(String str:IDlist){
    					deviceslist.addItem(str);
	    				com.Main.ThenToolsRun.logger.log(Level.INFO,"update devices ID="+str);
	    				nodevice=false;
    				}
    				if(nodevice){
    				com.Main.ThenToolsRun.selectedID=null;
    				com.Main.ThenToolsRun.logger.log(Level.INFO,"select devices ID="+com.Main.ThenToolsRun.selectedID);
    				}
    			}
			}
			}
		}).start();
	}
	public static void checkstatus(){
		lblDevicestatus=com.Main.ThenToolsRun.mainFrame.getDevicestatus();
		String str=getString("lblDevicestatus");
          lblDevicestatus.setText("<html>"+str+"</html>"); 

	}
	
	public static void checkinfo(){
		lblDeviceInfo=com.Main.ThenToolsRun.mainFrame.getDeviceInfo();
		String str=getString("lblDeviceInfo");
       	lblDeviceInfo.setText("<html>"+str+"</html>");

	}
	//get deivces list
	public static String[] getdeviceslist(){
	    ArrayList array = new ArrayList();
		devices=com.Main.ThenToolsRun.getdevices.getDevices();
	    if (devices != null) {
		      for (int i = 0; i < devices.length; ++i) {
		    	  if(devices[i].isOnline()){
		    	  array.add(devices[i].toString());
		    	  }
		      } 
		    }
		String[] str=(String [])array.toArray(new String[0]); 
		return str;
	}
	
	public static String[] checkdevices____(){
		List<String> list=Excute.returnlist("devices", 3,true);
		ArrayList<String> array = new ArrayList<String>(); 
		for(String str:list){
			if(str.contains("attached")||str.equals("")){
				continue;
			}
			String[] devicesID=str.split("\\s+");
			if(!devicesID[1].toString().contains("off") && !devicesID[1].contains("un")){
			array.add(devicesID[0]);
			}
		}
		
		String[] str=(String [])array.toArray(new String[0]);  
	//	com.Main.ThenToolsRun.logger.log(Level.INFO,str[0]);
	//	System.out.print(array.toString());
		return str;
	}
	
	static int logcount;
	public static String checklog(){
		if(com.Main.ThenToolsRun.platform.contains("msm")){
		List<String> list=Excute.returnlist3("ps|grep '^[shell^|root].*logcat$'");
	//	List<String> list=Excute.returnlist("ps|grep '^[shell^|root].*logcat$'", 2,true);
		logcount=0;
		for(String str:list){
			//com.Main.ThenToolsRun.logger.log(Level.INFO,str+111);
			if(str.equals("")||str.contains("0000000000")){//排除系统Logcat
				continue;
			}
			logcount++;
		}
		//com.Main.ThenToolsRun.logger.log(Level.INFO,count);
		if(logcount>5){
			logstatus="<font color=\"#FF0000\">Active Repeat!</font>";
		}else if(logcount>=3){
			logstatus="<font color=\"#FF0000\">Running!</font>";
		}else if(logcount<3&&com.Main.ThenToolsRun.selectedID!=null){
			logstatus="Not Running!";
		}else if(com.Main.ThenToolsRun.selectedID==null){
			logstatus="No device!";
		}
		}else if(com.Main.ThenToolsRun.platform.contains("MT")){
			String mtk=Excute.execcmd3("getprop debug.MB.running");
			if(mtk.contains("0")){
				logstatus="Not Running!";
			}else if(mtk.contains("1")) {
				logstatus="<font color=\"#FF0000\">Running!</font>";
			}else{
				logstatus="No device!";
			}
		}
		return logstatus;
	}
	
	public static String checkversion(){
		String[] version={"","No device!"};
		List<String> versionlist=Excute.returnlist3("getprop |grep \"internal.version\"");
		//List<String> versionlist=Excute.returnlist("getprop |grep \"internal.version\"",2,true );
		if(!versionlist.toString().equals("[]")){
		for(String str:versionlist){
			if(str.contains(":")){
				version=str.split(": ");
			 }else{
			    version[1]="Unkonw";
			 }
		}
		}
		return version[1];
	}
	
	public static String checkuserdebug(){
		isdebug="No device!";
		String userdebuglist=Excute.execcmd3("getprop ro.build.type");
		//String[] userdebuglist=Excute.execcmd("getprop ro.build.type |grep \"userdebug\"", 2, true);
    	//com.Main.ThenToolsRun.logger.log(Level.INFO,userdebuglist[0]);
        if(userdebuglist.startsWith("userdebug")){
        	isdebug="Yes";
        }else if(userdebuglist.startsWith("user")){
        	isdebug="No";
        }
		return isdebug;
	}
	
	public static String checkroot(){
        String str=Excute.execcmd3("cd /data&&ls");
        if(str.contains("denied")){
        	isroot="No";
        }else if(str.contains("app")){
        	isroot="Yes";
        }
        return isroot;
	}
	
	public static String checkplatform(){
		String Qcomstr=Excute.execcmd3("getprop ro.product.board ");
		String MTKstr=Excute.execcmd3("getprop ro.mediatek.platform ");
    	//com.Main.ThenToolsRun.logger.log(Level.INFO,userdebuglist[0]);
        if(Qcomstr.contains("msm")){
        	com.Main.ThenToolsRun.platform=Qcomstr;
        }else if(MTKstr.contains("MT")){
        	com.Main.ThenToolsRun.platform=MTKstr;
        }else {
        	com.Main.ThenToolsRun.platform="msm0";	
        }
		return com.Main.ThenToolsRun.platform;
		
	}
	public static String checkAPI(){
			String api="No device!";
			api=Excute.execcmd3("getprop ro.build.version.sdk");
			if(api.equals("")){
				return "No device!";
			}else{
				return api;
			}
	}
	//~~~~~~~~~~
	public static boolean checkSIMstatus(){
		boolean hassim=false;
		if(com.Main.ThenToolsRun.platform.contains("MT")){
			String MTK1=Excute.execcmd2("getprop gsm.sim.state");
			String MTK2=Excute.execcmd2("getprop gsm.sim.state.2");
			if(MTK1.contains("READY")||MTK2.contains("READY")){
				hassim=true;
			}
		}else{
//			if(platform.contains("msm")){
				String Qcom=Excute.execcmd2("getprop gsm.sim.state");
				if(Qcom.contains("READY")){
					hassim=true;
				}
	//			}
		}
		return hassim;
	}
	//~~~~~~~
	public static boolean checkMonkeyrun(){
		boolean isrun=false;
		List<String> list=Excute.returnlist2("ps |grep \"com.android.commands.monkey\"");
		for(String str : list){
			if(str.equals("")){
				continue;
			}
			isrun=true;
		}
		return isrun;
	}
	//get logstatus
	public static String  getlogstatus(){
		return  logstatus;
	}
	//get root
	public static String getisroot(){
		return isroot;
	}
	//get isdebug
	public static String getisdebug(){
		return isdebug;
	}
	//Language 
	public static String getString(String flag){
		switch(flag){
		case "lblDevicestatus": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "Log状态: "+checklog()+"<br>"+
						"Root: "+checkroot()+"<br>";
		}else{
			return "Log status: "+checklog()+"<br>"+
					"Root: "+checkroot()+"<br>";
		}
		case "lblDeviceInfo": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "版本: "+checkversion()+"<br>"+
						"Userdebug: "+checkuserdebug()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "平台: "+checkplatform()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "API: "+checkAPI()+"<br>";
		}else{
			return "Version: "+checkversion()+"<br>"+
					"Userdebug: "+checkuserdebug()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "Platform: "+checkplatform()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "API: "+checkAPI()+"<br>";
		}
			default: return "";
		}	
	}
}
