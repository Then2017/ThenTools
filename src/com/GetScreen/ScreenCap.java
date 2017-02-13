package com.GetScreen;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.Functions.LoggerUtil;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.TimeoutException;


public class ScreenCap {
	EditToolsUI edittoolsui=new EditToolsUI();
	ScreenShotUI screenshotui=new ScreenShotUI(edittoolsui);
	boolean screencapthreadrun=false;
	private FBImage mFBImage;
    private SimpleDateFormat sDateFormatget = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
    String saveimagepath="";
    boolean andsave=false;
    
    public void run( ){
    	File screenfolder=new File(com.Main.ThenToolsRun.ThenLogfile+"/ScreenCap");
    	if(!screenfolder.exists()){
    		screenfolder.mkdirs();
    	}
    	ScreenCapThread screencapthread = new ScreenCapThread();
		new Thread(screencapthread).start();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"screen cap");
    }

    //sava image 
	  public boolean saveImage() {
		    FBImage inImage = this.mFBImage;
		    if (inImage != null) {
		      BufferedImage outImage = new BufferedImage((int)(inImage.getWidth() * 1), 
		    		  (int)(inImage.getHeight() * 1), inImage.getType());
		      if (outImage != null) {
		        AffineTransformOp op = new AffineTransformOp(
		        AffineTransform.getScaleInstance(1, 1),2);
		        op.filter(inImage, outImage);
		       	JFileChooser fileChooser = new JFileChooser(com.Main.ThenToolsRun.ThenLogfile+"/ScreenCap");
		        fileChooser.setFileFilter(new FileFilter()
		        {
		          public String getDescription() {
		            return "*.png";
		          }

		          public boolean accept(File f)
		          {
		            String ext = f.getName().toLowerCase();
		            return ext.endsWith(".png");
		          }
		        });
		        fileChooser.setSelectedFile(new File("Cap_PCtime_"+sDateFormatget.format(new Date())));
		        if (fileChooser.showSaveDialog(com.Main.ThenToolsRun.mainFrame) != 0) return false;
		        try {
		          File file = fileChooser.getSelectedFile();
		          saveimagepath = file.getAbsolutePath();
		          if (!saveimagepath.endsWith(".png")) {
		            file = new File(saveimagepath + "." + "png");
		            saveimagepath=saveimagepath+".png";
		          }
		          ImageIO.write(outImage, "png", file);
		          return true;
		        } catch (Exception e) {
		        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		          JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Failed to save a image.", "Message",JOptionPane.ERROR_MESSAGE);
		        }
		      }
		    }else{
		          JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "None image to save.", "Message",JOptionPane.ERROR_MESSAGE);	
		    }
	    	return false;
	  }
	    
	    public String getimagepath(){
	    	return saveimagepath;
	    }
	    public boolean getScreenCapThreadrun(){
	    	return screencapthreadrun;
	    }
	    
		public ScreenShotUI getScreenShotUI(){
			return screenshotui;
		}
		public EditToolsUI getEditTools(){
			return edittoolsui;
		}
		public void setAndsave(boolean andsave){
			this.andsave=andsave;
		}
		public FBImage getmFBImage(){
			return mFBImage;
		}
		//screencapthread
		class ScreenCapThread implements Runnable {

			public ScreenCapThread() {

			}

			public void run() { 
				screencapthreadrun=true;
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
				mFBImage=getDeviceImage();
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
				screenshotui.setIndex(0);
				screenshotui.createNewitem();
				screenshotui.setFBImage(mFBImage);
				if(andsave){
				    File file =new File(com.Main.ThenToolsRun.ThenLogfile+"/ScreenCap/Cap_PCtime_"+sDateFormatget.format(new Date())+".png");
			        try {
					      BufferedImage outImage = new BufferedImage((int)(mFBImage.getWidth() * 1), 
					    		  (int)(mFBImage.getHeight() * 1), mFBImage.getType());
						ImageIO.write(mFBImage, "png", file);
						saveimagepath=file.getAbsolutePath();
						com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Screen Cap done!\nPicture is saved in "+file.getAbsolutePath(), 
								"Message", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
						com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
					}
				}else{
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Screen Cap done!", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
				}
				screencapthreadrun=false;
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





















//	/**
//	 * copy from http://bbs.csdn.net/topics/390502035. modify by Geek_Soledad
//	 *http://javadox.com/com.android.tools.ddms/ddmlib/23.0.1/com/android/ddmlib/IDevice.html
//	 */
//		public static IDevice connect() {
//			// init the lib
//			// [try to] ensure ADB is running
//			String adbLocation = System.getProperty("com.android.screenshot.bindir"); //$NON-NLS-1$
//			if (adbLocation != null && adbLocation.length() != 0) {
//				adbLocation += File.separator + "adb"; //$NON-NLS-1$
//			} else {
//				adbLocation = "adb"; //$NON-NLS-1$
//			}
//
//			AndroidDebugBridge.init(false /* debugger support */);
//
//			AndroidDebugBridge bridge = AndroidDebugBridge.createBridge(adbLocation, true /* forceNewBridge */);
//
//			// we can't just ask for the device list right away, as the internal
//			// thread getting
//			// them from ADB may not be done getting the first list.
//			// Since we don't really want getDevices() to be blocking, we wait
//			// here manually.
//			int count = 0;
//			while (bridge.hasInitialDeviceList() == false) {
//				try {
//					Thread.sleep(100);
//					count++;
//				} catch (InterruptedException e) {
//					// pass
//				}
//
//				// let's not wait > 10 sec.
//				if (count > 100) {
//					System.err.println("Timeout getting device list!");
//					return null;
//				}
//			}
//
//			// now get the devices
//			IDevice[] devices = bridge.getDevices();
//
//			if (devices.length == 0) {
//				com.Main.ThenToolsRun.logger.log(Level.INFO,"No devices found!");
//				return null;
//			}
//			return devices[0];
//		}
//
//		public static BufferedImage screenShot(IDevice device) {
//			RawImage rawImage;
//			try {
//				rawImage = device.getScreenshot();
//			} catch (Exception ioe) {
//				com.Main.ThenToolsRun.logger.log(Level.INFO,"Unable to get frame buffer: " + ioe.getMessage());
//				return null;
//			}
//
//			// device/adb not available?
//			if (rawImage == null)
//				return null;
//
//			// convert raw data to an Image
//			BufferedImage image = new BufferedImage(rawImage.width, rawImage.height,
//					BufferedImage.TYPE_INT_ARGB);
//
//			int index = 0;
//			int IndexInc = rawImage.bpp >> 3;
//			for (int y = 0; y < rawImage.height; y++) {
//				for (int x = 0; x < rawImage.width; x++) {
//					int value = rawImage.getARGB(index);
//					index += IndexInc;
//					image.setRGB(x, y, value);
//				}
//			}
//			return image;
//		}
//
//		/**
//		 * Grab an image from an ADB-connected device.
//		 */
//		public static boolean screenShotAndSave(IDevice device, String filepath)  {
//
//			boolean result=false;
//			try {
//				result = ImageIO.write(screenShot(device), "png", new File(filepath));
//				if (result) {
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"file is saved in:" + filepath);
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//			}
//			return result;
//		}
//
//		public static void terminate() {
//			AndroidDebugBridge.terminate();
//		}
		//=====================================================================
//		private IChimpDevice mChimpDevice;
//		private AdbBackend adbBack;
//
//		public Robot() {
//			mImgHash = new ImageHash();
//			adbBack = new AdbBackend();
//			mChimpDevice = adbBack.waitForConnection();
//		}
//
//		/**
//		 * 截图
//		 */
//		public BufferedImage snapshot() {
//			IChimpImage img;
//			// 这里用一个while循环是有时截图时会抛出超时异常，导致返回的是null对象。
//			do {
//				img = mChimpDevice.takeSnapshot();
//			} while (img == null);
//			return img.getBufferedImage();
//		}

