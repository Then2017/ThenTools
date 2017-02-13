package com.CheckTP;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.Functions.LoggerUtil;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

public class CheckTP {
	TPShotUI TPshotui=new TPShotUI();
	private SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");
	public boolean checktpthreadrun=false;
	public FBImage mFBImage;
	boolean iscancelled=false;
	String[] splitstr;
	String x1,y1,x2,y2,temp1,temp2;
	JTextArea textAreaShowXY;
	JLabel lblResolution;
	boolean mPortrait=true;
	//Timer timer;
//	boolean haswrite=false;
	
	long currenttime=System.currentTimeMillis();
	String tempshow;
	long temptime;
	boolean tapx1=true,tapy1=true;
	
	public void run(){
		//线程启动
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		CheckTPThread checktpthread = new CheckTPThread();
		new Thread(checktpthread).start();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"check TP thread start");
		
//		timer = new Timer();
//	    timer.schedule(new TimerTask(){
//	    	public void run(){
//	    		haswrite=false;
//	    	}
//	    },0,3000); 
	}

	public void getTPinfo(){
		try {
			//getevent |grep -E \": 0003 .{4} (00000...|ffffffff)\"
			com.Main.ThenToolsRun.getdevices.getDevice().executeShellCommand("getevent", new MultiLineReceiver(){
				@Override
				public boolean isCancelled() {
					// TODO Auto-generated method stub
					return iscancelled;
				}
				@Override
				public void processNewLines(String[] arg0) {
					// TODO Auto-generated method stub
			        for(String line:arg0) { //将输出的数据缓存起来
			        	//System.out.println(line);
			        	if(line.equals("")){
			        		com.Main.ThenToolsRun.logger.log(Level.INFO,"plug out usb line for exception");
			        		iscancelled=true;
			        	}else{
				        	splitstr=line.split(" ");
			        	if(splitstr[2].equals("0035")){
			        		if(tapx1){
			        			x1=HEXtoDEC(splitstr[3]);
			        			tapx1=false;
			        		}
			        		x2=HEXtoDEC(splitstr[3]);
			        	}else if(splitstr[2].equals("0036")){
			        		if(tapy1){
			        			y1=HEXtoDEC(splitstr[3]);
			        			tapy1=false;
			        		}
			        		y2=HEXtoDEC(splitstr[3]);
			        	}
			        	//014a 00000000
			        	else if(splitstr[2].equals("014a")&&splitstr[3].equals("00000000")){
			        		ChangeXY();
			        		if(Math.abs(Integer.parseInt(x1)-Integer.parseInt(x2))>25||
			        				Math.abs(Integer.parseInt(y1)-Integer.parseInt(y2))>25){
			        			tempshow="Drag:("+x1+","+y1+"),("+x2+","+y2+")\n";
			        		}else{
			        		temptime=System.currentTimeMillis()-currenttime;
			        		if(temptime>500){
			        			tempshow="Long tap:("+x2+","+y2+"),"+temptime+"ms\n";
			        		}else{
			        			tempshow="Tap:("+x2+","+y2+")\n";
			        		}
			        		}
			        		//add word
			        		textAreaShowXY.append(tempshow);
			        		TPshotui.drawpicture(tempshow);
						    textAreaShowXY.setCaretPosition(textAreaShowXY.getText().length());
			        		tapx1=true;
			        		tapy1=true;
			        	}else if(splitstr[2].equals("014a")&&splitstr[3].equals("00000001")){
			        		 currenttime=System.currentTimeMillis();
			        	}
			        	//0039 ffffffff
			        	else if(splitstr[2].equals("0039")&&splitstr[3].equals("ffffffff")){
			        		ChangeXY();
			        		if(Math.abs(Integer.parseInt(x1)-Integer.parseInt(x2))>25||
			        				Math.abs(Integer.parseInt(y1)-Integer.parseInt(y2))>25){
			        			tempshow="Drag:("+x1+","+y1+"),("+x2+","+y2+")\n";
			        		}else{
			        		temptime=System.currentTimeMillis()-currenttime;
			        		if(temptime>500){
			        			tempshow="Long tap:("+x2+","+y2+"),"+temptime+"ms\n";
			        		}else{
			        			tempshow="Tap:("+x2+","+y2+")\n";
			        		}
			        		}
			        		//add word
			        		textAreaShowXY.append(tempshow);
			        		TPshotui.drawpicture(tempshow);
						    textAreaShowXY.setCaretPosition(textAreaShowXY.getText().length());
			        		tapx1=true;
			        		tapy1=true;
			        	}else if(splitstr[2].equals("0039")&&!splitstr[3].equals("ffffffff")&&!splitstr[3].equals("00000000")){
			        		 currenttime=System.currentTimeMillis();//重启后第一次点击长按无效
			        	}
	
			        }
			        }
			      //  count++;
			     //   System.out.println(x+" "+y+" count="+count);
				}
				
			},999999999,TimeUnit.SECONDS);
		} catch (TimeoutException | AdbCommandRejectedException | ShellCommandUnresponsiveException | IOException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
	}
	//landsapce mode x y
	public void ChangeXY(){
    	if(!mPortrait){
    			temp1=x1;
        		x1=y1;
        		y1=(mFBImage.getHeight()-Integer.parseInt(temp1))+"";
        		temp2=x2;
        		x2=y2;
        		y2=(mFBImage.getHeight()-Integer.parseInt(temp2))+"";

    	}
	}
		//Clear Screen
		public void ClearScreen(){
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
			textAreaShowXY.setText("");
			TPshotui.setindex(0);
			TPshotui.setFBImage(mFBImage);
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
		}
		//Refresh screen
		public void RefreshScreen(){
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
					mFBImage=getDeviceImage();
					TPshotui.setFBImage(mFBImage);
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Refresh Screen done!", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Refresh Screen ok");
				}
				
			}).start();
	
		}
			//16 to 10
			public String HEXtoDEC(String s){
				return Integer.parseInt(s,16)+"";
			}
			//set textAreaShowXY
			public void settextAreaShowXY(JTextArea textAreaShowXY){
				this.textAreaShowXY=textAreaShowXY;
			}
			//stop getevent
		    public void setiscancelled(boolean iscancelled){
		    	this.iscancelled=iscancelled;
		    }
		    public int getsrcHeight(){
		    	return mFBImage.getHeight();
		    }
		    public int getsrcWidth(){
		    	return mFBImage.getWidth();
		    }
			public boolean getChecktpthreadrun(){
				return checktpthreadrun;
			}
			public TPShotUI getScreenShotUI(){
				return TPshotui;
			}
		    //设置横竖屏
		    public void setmPortrait(boolean mPortrait){
		    	this.mPortrait=mPortrait;
		    }
		    //设置分辨率信息字符串
		    public void setlblResolution(JLabel lblResolution){
		    	this.lblResolution=lblResolution;
		    }
		class CheckTPThread implements Runnable {
	
			public CheckTPThread() {
			
			}
	
			public void run() {
				checktpthreadrun=true;
				mFBImage=getDeviceImage();
				iscancelled=false;
				com.Main.ThenToolsRun.logger.log(Level.INFO,"image y="+mFBImage.getHeight()+",x="+mFBImage.getWidth());
				textAreaShowXY.setText("");
				TPshotui.setindex(0);
				TPshotui.setFBImage(mFBImage);
				lblResolution.setText(getString("lblResolution")+mFBImage.getHeight()+"X"+mFBImage.getWidth());
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Screen Cap done!", 
						"Message", JOptionPane.INFORMATION_MESSAGE);
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
				getTPinfo();
				
				checktpthreadrun=false;
				iscancelled=true;
				com.Main.ThenToolsRun.logger.log(Level.INFO,"check tp and get image ok");
			}
		}
		//Language 
		public String getString(String flag){
			switch(flag){
			case "lblResolution": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "分辨率: ";
			}else{
				return "Resolution: ";
			}
				default: return "";
			}
		}
		//getFBimage
	    private FBImage getDeviceImage() {
		      boolean success = true;
		      boolean debug = false;
		      FBImage fbImage = null;
		      RawImage tmpRawImage = null;
		      RawImage rawImage = null;

		      if (success) {
		        try {
		          tmpRawImage = com.Main.ThenToolsRun.getdevices.getDevice().getScreenshot();
		          
		          if (tmpRawImage == null) {
		            success = false;
		          }
		          else if (!debug) {
		            rawImage = tmpRawImage;
		          } else {
		            rawImage = new RawImage();
		            rawImage.version = 1;
		            rawImage.bpp = 32;
		            rawImage.size = (tmpRawImage.width * tmpRawImage.height * 4);
		            rawImage.width = tmpRawImage.width;
		            rawImage.height = tmpRawImage.height;
		            rawImage.red_offset = 0;
		            rawImage.red_length = 8;
		            rawImage.blue_offset = 16;
		            rawImage.blue_length = 8;
		            rawImage.green_offset = 8;
		            rawImage.green_length = 8;
		            rawImage.alpha_offset = 0;
		            rawImage.alpha_length = 0;
		            rawImage.data = new byte[rawImage.size];

		            int index = 0;
		            int dst = 0;
		            for (int y = 0; y < rawImage.height; ++y) {
		              for (int x = 0; x < rawImage.width; ++x) {
		                int value = tmpRawImage.data[(index++)] & 0xFF;
		                value |= tmpRawImage.data[(index++)] << 8 & 0xFF00;
		                int r = (value >> 11 & 0x1F) << 3;
		                int g = (value >> 5 & 0x3F) << 2;
		                int b = (value >> 0 & 0x1F) << 3;

		                rawImage.data[(dst++)] = (byte)r;
		                rawImage.data[(dst++)] = (byte)g;
		                rawImage.data[(dst++)] = (byte)b;
		                rawImage.data[(dst++)] = -1;
		              }
		            }

		          }

		        }
		        catch (IOException | TimeoutException | AdbCommandRejectedException e)
		        {
		        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		        }
		        finally
		        {
		          if ((rawImage == null) || (
		            (rawImage.bpp != 16) && (rawImage.bpp != 32))) {
		            success = false;
		          }
		        }
		      }
		      if (success)
		      {
		        int imageHeight;
		        int imageWidth;
		        
		        if (!com.Main.ThenToolsRun.selectedID.equals("Iris4G")) {
		          imageWidth = rawImage.width;
		          imageHeight = rawImage.height;
		        } else {
		          imageWidth = rawImage.height;
		          imageHeight = rawImage.width;
		        }

		        fbImage = new FBImage(imageWidth, imageHeight, 1, rawImage.width, rawImage.height);
		        byte[] buffer = rawImage.data;
		        int redOffset = rawImage.red_offset;
		        int greenOffset = rawImage.green_offset;
		        int blueOffset = rawImage.blue_offset;
		        int alphaOffset = rawImage.alpha_offset;
		        int redMask = getMask(rawImage.red_length);
		        int greenMask = getMask(rawImage.green_length);
		        int blueMask = getMask(rawImage.blue_length);
		        int alphaMask = getMask(rawImage.alpha_length);
		        int redShift = 8 - rawImage.red_length;
		        int greenShift = 8 - rawImage.green_length;
		        int blueShift = 8 - rawImage.blue_length;
		        int alphaShift = 8 - rawImage.alpha_length;

		        int index = 0;
		        if (rawImage.bpp == 16)
		        {
		          int offset1;
		          int offset0;
		            offset0 = 0;
		            offset1 = 1;
		          if (!com.Main.ThenToolsRun.selectedID.equals("Iris4G")) {
		            for (int y = 0; y < rawImage.height; ++y)
		              for (int x = 0; x < rawImage.width; ++x) {
		                int value = buffer[(index + offset0)] & 0xFF;
		                value |= buffer[(index + offset1)] << 8 & 0xFF00;
		                int r = (value >>> redOffset & redMask) << redShift;
		                int g = (value >>> greenOffset & greenMask) << greenShift;
		                int b = (value >>> blueOffset & blueMask) << blueShift;
		                value = 0xFF000000 | r << 16 | g << 8 | b;
		                index += 2;
		                fbImage.setRGB(x, y, value);
		              }
		          }
		          else {
		            for (int y = 0; y < rawImage.height; ++y)
		              for (int x = 0; x < rawImage.width; ++x) {
		                int value = buffer[(index + offset0)] & 0xFF;
		                value |= buffer[(index + offset1)] << 8 & 0xFF00;
		                int r = (value >>> redOffset & redMask) << redShift;
		                int g = (value >>> greenOffset & greenMask) << greenShift;
		                int b = (value >>> blueOffset & blueMask) << blueShift;
		                value = 0xFF000000 | r << 16 | g << 8 | b;
		                index += 2;
		                fbImage
		                  .setRGB(y, rawImage.width - x - 1, 
		                  value);
		              }
		          }
		        }
		        else if (rawImage.bpp == 32)
		        {
		          int offset3;
		          int offset0;
		          int offset1;
		          int offset2;
		             offset0 = 0;
		             offset1 = 1;
		             offset2 = 2;
		            offset3 = 3;
		          
		          if (!com.Main.ThenToolsRun.selectedID.equals("Iris4G")) {
		            for (int y = 0; y < rawImage.height; ++y)
		              for (int x = 0; x < rawImage.width; ++x)
		              {
		                int value = buffer[(index + offset0)] & 0xFF;
		                value |= (buffer[(index + offset1)] & 0xFF) << 8;
		                value |= (buffer[(index + offset2)] & 0xFF) << 16;
		                value |= (buffer[(index + offset3)] & 0xFF) << 24;
		                int r = (value >>> redOffset & redMask) << redShift;
		                int g = (value >>> greenOffset & greenMask) << greenShift;
		                int b = (value >>> blueOffset & blueMask) << blueShift;
		                int a;
		                if (rawImage.alpha_length == 0)
		                  a = 255;
		                else {
		                  a = (value >>> alphaOffset & alphaMask) << alphaShift;
		                }
		                value = a << 24 | r << 16 | g << 8 | b;
		                index += 4;
		                fbImage.setRGB(x, y, value);
		              }
		          }
		          else {
		            for (int y = 0; y < rawImage.height; ++y) {
		              for (int x = 0; x < rawImage.width; ++x)
		              {
		                int value = buffer[(index + offset0)] & 0xFF;
		                value |= (buffer[(index + offset1)] & 0xFF) << 8;
		                value |= (buffer[(index + offset2)] & 0xFF) << 16;
		                value |= (buffer[(index + offset3)] & 0xFF) << 24;
		                int r = (value >>> redOffset & redMask) << redShift;
		                int g = (value >>> greenOffset & greenMask) << greenShift;
		                int b = (value >>> blueOffset & blueMask) << blueShift;
		                int a;
		                if (rawImage.alpha_length == 0)
		                  a = 255;
		                else {
		                  a = (value >>> alphaOffset & alphaMask) << alphaShift;
		                }
		                value = a << 24 | r << 16 | g << 8 | b;
		                index += 4;
		                fbImage
		                  .setRGB(y, rawImage.width - x - 1, 
		                  value);
		              }
		            }
		          }
		        }
		      }
		      return fbImage;
		    }

		    public int getMask(int length) {
		      int res = 0;
		      for (int i = 0; i < length; ++i) {
		        res = (res << 1) + 1;
		      }

		      return res;
		    }
}
