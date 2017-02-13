package com.ThenHelper;

import java.io.File;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.LoggerUtil;
import com.android.ddmlib.InstallException;

public class ThenHelper {
	public boolean installthenhelperthreadrun=false;
    //install ThenHelper
    public void installThenHelper(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				installthenhelperthreadrun=true;
				File file=new File(com.Main.ThenToolsRun.datalocation);
				String[] filelist=file.list();
				String apkname=getAPKname();
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
    
    //get apk name
    public String getAPKname(){
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
		return apkname;
    }
    public boolean getInstallthenhelperthreadrun(){
    	return installthenhelperthreadrun;
    }
}
