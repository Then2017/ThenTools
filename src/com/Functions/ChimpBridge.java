package com.Functions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.AutoScript.FBImage;
import com.AutoScript.SilentLog;
import com.android.chimpchat.ChimpChat;
import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.adb.AdbChimpDevice;
import com.android.chimpchat.core.IChimpDevice;
import com.android.chimpchat.core.IChimpImage;
import com.android.chimpchat.core.PhysicalButton;
import com.android.chimpchat.core.TouchPressType;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.TimeoutException;
import com.android.ide.common.rendering.api.Result;

public class ChimpBridge {
    //http://www.mamicode.com/info-detail-1142035.html
     private  AdbBackend adbBack;  
     private IChimpDevice ichimpDevice;
     private ChimpChat chimpchat;
    // private AdbChimpDevice adbchimpdevice;
     boolean isok=true;

     private SimpleDateFormat sDateFormatget = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
     //button
     PhysicalButton physicalbutton;
	public void connect(){
	//	adbBack = new AdbBackend();  
//		TreeMap<String, String> options = new TreeMap<String, String>();  
//        options.put("backend", "adb");  
//        options.put("adbLocation", com.Main.ThenToolsRun.extraBinlocation+"/adb.exe");  
//        mChimpchat = ChimpChat.getInstance(options);  
//        mChimpDevice = mChimpchat.waitForConnection(); 
	//	adbchimpdevice=new AdbChimpDevice(com.Main.ThenToolsRun.getdevices.getDevice());
		if(com.Main.ThenToolsRun.getdevices.getDevice()!=null&&com.Main.ThenToolsRun.selectedID!=null){
			cancel();
			try {
			try{
			ichimpDevice=new AdbChimpDevice(com.Main.ThenToolsRun.getdevices.getDevice());
			}catch(Exception e){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"connect fail");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Connect failed, pls press ok to try again!", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					Thread.sleep(3000);
					try{
					ichimpDevice=new AdbChimpDevice(com.Main.ThenToolsRun.getdevices.getDevice());
					}catch(Exception ee){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"connect chimpbridge device fail");
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Connect device failed, pls check!", 
								"Message", JOptionPane.ERROR_MESSAGE); 
					}
			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
	    	//ichimpDevice=adbchimpdevice;
		}else{
			com.Main.ThenToolsRun.logger.log(Level.INFO,"no devices checked!");
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "no devices checked!", 
					"Message", JOptionPane.ERROR_MESSAGE); 
		}
	}
	public void setOK(boolean isok){
		this.isok=isok;
	}
	//wake
	public void Wake(String wait){
		if(isok){
		ichimpDevice.wake();

		try {
			if(wait.equals("1")){
			Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	//Touch
	public void Tap(String x,String y,String wait){
		if(isok){
		try {
			ichimpDevice.getManager().touch(Integer.parseInt(x), Integer.parseInt(y));
			if(wait.equals("1")){
			Thread.sleep(2000);
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
		}
	}
	//Long Touch
	public void LongTap(String x,String y,String time,String wait){
		if(isok){
		try {
			ichimpDevice.getManager().touchDown(Integer.parseInt(x), Integer.parseInt(y));
			Thread.sleep(Integer.parseInt(time));
			ichimpDevice.getManager().touchUp(Integer.parseInt(x), Integer.parseInt(y));
			if(wait.equals("1")){
			Thread.sleep(2000);
			}
		} catch (IOException | NumberFormatException | InterruptedException  e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
		}
	}
	//Drag
	int stepx,stepy,step;
	public void Drag(String x1,String y1,String x2,String y2,String wait){
		if(isok){
			 stepx=Math.abs(Integer.parseInt(x1)-Integer.parseInt(x2));
			 stepy=Math.abs(Integer.parseInt(y1)-Integer.parseInt(y2));
			if(stepx>stepy){
				step=stepx;
			}else{
				step=stepy;
			}
			ichimpDevice.drag(Integer.parseInt(x1), Integer.parseInt(y1), Integer.parseInt(x2), Integer.parseInt(y2), 
					step/25, step/2);
			try {
				if(wait.equals("1")){
					Thread.sleep(2000);
					}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
		}
	}
	//Press button
	public void Pressbutton(String button,String wait){
		if(isok){
		try {
			if(button.equals("HOME")){
				ichimpDevice.getManager().press(physicalbutton.HOME);
			}else if(button.equals("BACK")){
				ichimpDevice.getManager().press(physicalbutton.BACK);
			}else if(button.equals("MENU")){
				ichimpDevice.getManager().press(physicalbutton.MENU);
			}else if(button.equals("VOLUME_UP")){
				ichimpDevice.press("KEYCODE_VOLUME_UP", TouchPressType.DOWN_AND_UP);
			}else if(button.equals("VOLUME_DOWN")){
				ichimpDevice.press("KEYCODE_VOLUME_DOWN", TouchPressType.DOWN_AND_UP);
			}else if(button.equals("POWER")){
				ichimpDevice.press("KEYCODE_POWER", TouchPressType.DOWN_AND_UP);
			}
			if(wait.equals("1")){
				Thread.sleep(2000);
				}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
		}
	}
	//sleep
	public void Sleep(String time){
		if(isok){
		try {
			Thread.sleep(Integer.parseInt(time));
		} catch (NumberFormatException | InterruptedException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
		}
	}
	//Screen cap
	public void Screencap(){
		if(isok){
			BufferedImage image=ichimpDevice.takeSnapshot().createBufferedImage();
			  File file =new File(com.Main.ThenToolsRun.ThenLogfile+"/Script/ScreenCap/Script_PCtime_"+sDateFormatget.format(new Date())+".png");
			   try {
				ImageIO.write(image, "png", file);
				Thread.sleep(2000);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
		}
	}
	//Type str
	public void Type(String str,String wait){
		if(isok){
				try {
					ichimpDevice.getManager().type(str);
					if(wait.equals("1")){
					Thread.sleep(2000);
					}
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				}
		}
	}
	//Start Log
	public void Startlog(){
		if(isok){
			if(com.Main.ThenToolsRun.platform.contains("msm")){
				SilentLog silentlog=new SilentLog();
				silentlog.start();
			}else if(com.Main.ThenToolsRun.platform.contains("MT")){
				Excute.execcmd2("am broadcast -a com.mediatek.mtklogger.ADB_CMD -e cmd_name start --ei cmd_target 7");
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
		}
	}
	//Reboot 
	public void Reboot(String time){
		if(isok){
		try {
			ichimpDevice.reboot("Script needs reboot");
			Thread.sleep(Integer.parseInt(time));
			while(com.Main.ThenToolsRun.getdevices.getDevice()==null||com.Main.ThenToolsRun.selectedID==null){
				Thread.sleep(1000);
			}
			String[] result;
			do{
				result=Excute.execcmd(com.Main.ThenToolsRun.extraBinlocation+"/adb.exe shell cd /sdcard",1,true);
				Thread.sleep(1000);
			}while(!result[0].equals(""));
			try{
			ichimpDevice=new AdbChimpDevice(com.Main.ThenToolsRun.getdevices.getDevice());
			}catch(Exception e){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"connect fail");
				Thread.sleep(15000);
				ichimpDevice=new AdbChimpDevice(com.Main.ThenToolsRun.getdevices.getDevice());
			}
			Wake(0+"");
		} catch (NumberFormatException |InterruptedException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		} 
		}
	}
	//Test 
	public void Test(String str){

			IChimpImage a=ichimpDevice.takeSnapshot();
			IChimpImage b=ichimpDevice.takeSnapshot();
			a.sameAs(b, 1.0);
			


	}
	//end
	public void cancel(){
		if(ichimpDevice!=null){
			com.Main.ThenToolsRun.logger.log(Level.INFO,"ichimpDevice dispose");
			ichimpDevice.dispose();
			ichimpDevice=null;
		}
//		if(adbchimpdevice!=null){
//			adbchimpdevice=null;
//		}
	}
	
	//get ichimpDevice
	public IChimpDevice getichimpDevice(){
		return ichimpDevice;
	}

}
