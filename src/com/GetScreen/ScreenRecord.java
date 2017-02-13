package com.GetScreen;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.Excute;
import com.Functions.LoggerUtil;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.ScreenRecorderOptions;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;

public class ScreenRecord {
	private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
	int bitrate=8;
	int width=480;
	int height=800;
	boolean iscancel=false;
	boolean VideofromUEthreadrun=false;
	String videotime;
	
	public void run(String WidthandHeight){
		int[] size=getWidthandHeight(WidthandHeight);
		width=size[0];
		height=size[1];
		setiscancel(false);
		videotime=sDateFormat.format(new Date());
    	File screenfolder=new File(com.Main.ThenToolsRun.ThenLogfile+"/ScreenRecord");
    	if(!screenfolder.exists()){
    		screenfolder.mkdirs();
    	}
		//Ïß³ÌÆô¶¯
		StartRecordThread startrecordthread = new StartRecordThread();
		new Thread(startrecordthread).start();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"start startrecordthread");
	}
	
	public void setiscancel(boolean iscancel){
		this.iscancel=iscancel;
	}
	public void startrecord(){
		try {  
			ScreenRecorderOptions options=new ScreenRecorderOptions.Builder().setBitRate(bitrate).setSize(width, height).build();
			com.Main.ThenToolsRun.getdevices.getDevice().executeShellCommand("if [ ! -d \"/sdcard/Movies\" ];then mkdir /sdcard/Movies;fi", new IShellOutputReceiver(){

				@Override
				public void addOutput(byte[] arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void flush() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public boolean isCancelled() {
					// TODO Auto-generated method stub
					return false;
				}});
			
			com.Main.ThenToolsRun.getdevices.getDevice().startScreenRecorder("/sdcard/Movies/Record_PCtime"+videotime+".mp4",
					options,  new IShellOutputReceiver() { 
			    @Override 
			    public boolean isCancelled() { 
			        return iscancel; 
			    } 

			    @Override 
			    public void flush() { 
			    	com.Main.ThenToolsRun.logger.log(Level.INFO,"screen record flush");
			    } 

			    @Override 
			    public void addOutput(byte[] data, int offset, int length) { 
			        String Message; 
			        if (data != null) { 
			        	Message = new String(data); 
			        } else { 
			        	Message = ""; 
			        } 
			        com.Main.ThenToolsRun.logger.log(Level.INFO,Message);
			    } 
			});
			
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		} catch (AdbCommandRejectedException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		} catch (ShellCommandUnresponsiveException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
	}
	//get video from ue
	public void getVideofromUE(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
					VideofromUEthreadrun=true;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
					}
					if(videotime!=null){
						com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(30);//******************
					com.Main.ThenToolsRun.getdevices.getDevice().pullFile("/sdcard/Movies/Record_PCtime"+videotime+".mp4", com.Main.ThenToolsRun.ThenLogfile+"/ScreenRecord/Record_PCtime"+videotime+".mp4");
					com.Main.ThenToolsRun.logger.log(Level.INFO,"get record finished");
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Screen Record done!\nVideo is saved in "+com.Main.ThenToolsRun.ThenLogfile+
							"/ScreenRecord"+"/Record_PCtime"+videotime+".mp4", "Message", JOptionPane.INFORMATION_MESSAGE);
					}else{
						com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls start record first!", "Message", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SyncException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				} catch (AdbCommandRejectedException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				}
				VideofromUEthreadrun=false;
			}
		}).start();
	}
	
	public int[] getWidthandHeight(String str){
		switch(str){
		case "WVGA 480X800": return new int[]{480,800};
		case "FHD 1080X1920": return new int[]{1080,1920};
		case "HD 720x1280": return new int[]{720,1280};
		case "QHD 540X960": return new int[]{540,960};
		case "FWVGA 480X854": return new int[]{480,854};
		case "HVGA 320X480": return new int[]{320,480};
		default: return new int[]{480,800};
		}
	}
	//
	public boolean getVideofromUEthreadrun(){
		return VideofromUEthreadrun;
	}
	
	class StartRecordThread implements Runnable {
		public StartRecordThread() {
		}

		public void run() {
			startrecord();
			com.Main.ThenToolsRun.logger.log(Level.INFO,"screen record finished");
		}
	}
	
}
