package com.Others;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.Excute;
import com.Functions.LoggerUtil;
import com.android.ddmlib.InstallException;

public class OthersFunctions {
	public boolean installthenhelperthreadrun=false;
	//get spc
	public String getSPC(String sn){
		String spc=null;
		String[] result=Excute.execcmd(com.Main.ThenToolsRun.extraBinlocation+"/spc.exe "+sn, 1, true);
		spc=result[0].toString();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"sn="+sn+"  "+spc);
		String[] spcnum=spc.split(":");
		return spcnum[1].substring(1,spcnum[1].length());
	}
	//get spc by ue 
	public String[] getSPC(){
		String[] result=new String[2];
		String spc=null;
		String sn=Excute.execcmd2("getprop ro.nv.factory_data_I");
		sn=sn.replace("\n", "");
		String[] resulttemp=Excute.execcmd(com.Main.ThenToolsRun.extraBinlocation+"/spc.exe "+sn, 1, true);
		spc=resulttemp[0].toString();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"sn="+sn+"  "+spc);
		String[] spcnum=spc.split(":");
		result[0]=sn;
		result[1]=spcnum[1].substring(1,spcnum[1].length());
		return result;
	}
	//create IMEI
    public String createIMEI() {
    	try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
        SimpleDateFormat dftime = new SimpleDateFormat("mmssSSS");
        String time = dftime.format(new Date());
        String imeiString="3519010"+time;//
        char[] imeiChar=imeiString.toCharArray();
        int resultInt=0;
        for (int i = 0; i < imeiChar.length; i++) {
            int a=Integer.parseInt(String.valueOf(imeiChar[i]));
            i++;
            final int temp=Integer.parseInt(String.valueOf(imeiChar[i]))*2;
            final int b=temp<10?temp:temp-9;
            resultInt+=a+b;
        }
        resultInt%=10;
        resultInt=resultInt==0?0:10-resultInt;
		com.Main.ThenToolsRun.logger.log(Level.INFO,"new imei="+resultInt);
        return imeiString.substring(0,6)+" "+imeiString.substring(6,8)+" "+imeiString.substring(8,14)+" "+resultInt;
    }
    //create MAC
    public String createMAC(){
    	try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
        SimpleDateFormat dftime = new SimpleDateFormat("mmssSSS");
        String time = dftime.format(new Date());
        String fixstr="B8649 "+time;
		com.Main.ThenToolsRun.logger.log(Level.INFO,"new mac="+fixstr);
        return fixstr;
    }
    //install ThenHelper
    public void installThenHelper(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				installthenhelperthreadrun=true;
				File file=new File(com.Main.ThenToolsRun.datalocation);
				String[] filelist=file.list();
				String apkname=null;
				if(file.isDirectory()){
					for(int i=0;i<filelist.length;i++){
						if(filelist[i].toString().startsWith("ThenHelper")){
							apkname=filelist[i].toString();
						}
					}
				}
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
				if(apkname!=null){
		    	try {
					com.Main.ThenToolsRun.getdevices.getDevice().installPackage(com.Main.ThenToolsRun.datalocation+"/"+apkname, true, "");
				} catch (InstallException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				}
		    	com.Main.ThenToolsRun.logger.log(Level.INFO,"install "+apkname+" ok");
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
		    	JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Install "+apkname+" successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
		    	installthenhelperthreadrun=false;
				}else{
			    	installthenhelperthreadrun=false;
			    	com.Main.ThenToolsRun.logger.log(Level.INFO,"install "+apkname+" failed, no apk");
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
			    	JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Install ThenHelper.apk failed, can't find apk file!",
			    			"Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}).start();
    }
    public boolean getInstallthenhelperthreadrun(){
    	return installthenhelperthreadrun;
    }
    
    //cmd
    public void Runcmd(){
    	Excute.execcmd("start cmd /k cd "+com.Main.ThenToolsRun.extraBinlocation, 1, true);
    }
    //killadb
    public void KillADB(){
    	String[] temppid=Excute.execcmd("netstat -ano|findstr 5037.*LISTENING.*", 1, true);
    	if(temppid[0].toString().contains("LISTENING")){
    	temppid=temppid[0].split("\\s+"); //  TCP    127.0.0.1:5037         0.0.0.0:0              LISTENING       1552
    	String pid=temppid[5];
    	String[] temptask=Excute.execcmd("tasklist|findstr "+pid, 1, true);
    	temptask=temptask[0].split("\\s+");//adb.exe                       3752 Console                    1     15,144 K
    	String task=temptask[0];
    	
    	String[] result=Excute.execcmd("taskkill /f /pid "+pid, 1, true);
    	Excute.execcmd(com.Main.ThenToolsRun.extraBinlocation+"/adb.exe devices", 1, false);
    	if(result[0].contains("PID")){
        	JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pid="+pid+"\nName="+task+"\nKill successful!Restarting...",
        			"Message", JOptionPane.INFORMATION_MESSAGE);	
    	}else{
        	JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pid="+pid+"\nName="+task+"\nKill failed!",
        			"Message", JOptionPane.INFORMATION_MESSAGE);	
    	}
    	}else{
        	JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Restart failed!",
        			"Message", JOptionPane.INFORMATION_MESSAGE);	
    	}
    }
}
