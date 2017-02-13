package com.Functions;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

public class AdbBridge
{
  private AndroidDebugBridge mAndroidDebugBridge;
  boolean success = true;
  IDevice[] devices;
  IDevice device;
  public boolean initialize()
  {
//     adbLocation =  System.getProperty("com.android.screenshot.bindir");
//     if (success) {
//      if ((adbLocation != null) && (adbLocation.length() != 0)){
//          adbLocation = adbLocation + File.separator + "adb";
//      }
//      else {
//        adbLocation = "adb";
//      }
//      AndroidDebugBridge.init(false);
//      mAndroidDebugBridge = AndroidDebugBridge.createBridge(com.Main.MainRun.extraBinlocation+"/adb.exe",true);
//      if (this.mAndroidDebugBridge == null) {
//        success = false;
//      }
//    }

//    if (success) {
//      int count = 0;
//      while (!this.mAndroidDebugBridge.hasInitialDeviceList()) {
//        try {
//          Thread.sleep(100);
//          ++count;
//        } catch (InterruptedException localInterruptedException) {
//        }
//        if (count > 20) {
//          success = false;
//          break;
//        }
//      }
//    }
	  // http://javadox.com/com.android.tools.ddms/ddmlib/23.0.1/com/android/ddmlib/IDevice.html
	  //https://android.googlesource.com/platform/tools/base/+/master/ddmlib/src/main/java/com/android/ddmlib/ScreenRecorderOptions.java
		  AndroidDebugBridge.init(false);
		  mAndroidDebugBridge = AndroidDebugBridge.createBridge(com.Main.ThenToolsRun.extraBinlocation+"/adb.exe",true);
		  
	      if (this.mAndroidDebugBridge == null) {
	    	  com.Main.ThenToolsRun.logger.log(Level.INFO,"mAndroidDebugBridge success = false");
	    	  success = false;
	    	  return success;
	      }
	//  com.Main.ThenToolsRun.logger.log(Level.INFO,mAndroidDebugBridge.getAdbVersion(new File(com.Main.ThenToolsRun.extraBinlocation+"/adb.exe")).toString());
	  AndroidDebugBridge.addDeviceChangeListener(new IDeviceChangeListener(){
		@Override
		public void deviceChanged(IDevice arg0, int arg1) {
			// TODO Auto-generated method stub
			devices = mAndroidDebugBridge.getDevices();
		}

		@Override
		public void deviceConnected(IDevice arg0) {
			// TODO Auto-generated method stub
			devices = mAndroidDebugBridge.getDevices();
		}

		@Override
		public void deviceDisconnected(IDevice arg0) {
			// TODO Auto-generated method stub
			devices = mAndroidDebugBridge.getDevices();
		//	device=null;
			//com.Main.ThenToolsRun.selectedID=null;
		}
		  
	  });
    if (!success) {
      terminate();
    }
    com.Main.ThenToolsRun.logger.log(Level.INFO,"mAndroidDebugBridge success");
    return success;
  }

  public void terminate() {
	  com.Main.ThenToolsRun.logger.log(Level.INFO,"try to adb terminate");
	  //部分PC卡死 win7 64
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(
	        	   new Callable<String>() {//使用Callable接口作为构造参数
	        	       public String call() throws IOException {
	        	      //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型
	        	    	   AndroidDebugBridge.terminate();
	        	    	   return "ok";
						}
	        	   });
 		try {
			future.get(2000, TimeUnit.MILLISECONDS).equals("ok");
			com.Main.ThenToolsRun.logger.log(Level.INFO,"adb terminate ok");
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.INFO,"adb terminate failed");
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
  }

  public IDevice[] getDevices() {
//    if (mAndroidDebugBridge != null) {
//      devices = this.mAndroidDebugBridge.getDevices();
//    }
    return devices;
  }
  
	public IDevice getDevice(){
	    if (devices!=null&&devices.length > 0) {
		      for (int i = 0; i < devices.length; ++i) {
		        if(devices[i].toString().equals(com.Main.ThenToolsRun.selectedID)){
		        	device=devices[i];
		    		return device;
		        }
		      }
		    }
		return null;
	}
  
}