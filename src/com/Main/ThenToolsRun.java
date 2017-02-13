package com.Main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import com.AD.ADShow;
import com.Database.DBHandle;
import com.Functions.AdbBridge;
import com.Functions.AntiException;
import com.Functions.CheckPC;
import com.Functions.CheckUE;
import com.Functions.EnDecrypt;
import com.Functions.Excute;
import com.Functions.Helper;
import com.Functions.Init;
import com.Functions.LoggerUtil;
import com.Functions.UpdateThread;
import com.Help.AboutLisenceUI;
import com.android.ddmlib.IDevice;

public class ThenToolsRun {
	// jar==http://blog.csdn.net/xiaoping8411/article/details/6973887
    // ddmlib===http://javadox.com/com.android.tools.ddms/ddmlib/23.0.1/com/android/ddmlib/IDevice.html  
	//https://android.googlesource.com/platform/tools/base/+/master/ddmlib/src/main/java/com/android/ddmlib/ScreenRecorderOptions.java
	//api=http://tool.oschina.net/apidocs
	//record http://www.cnblogs.com/canselwicecs/archive/2012/12/24/2831540.html
	//exe4j=http://www.jb51.net/article/44392.htm
	public static Logger logger = Logger.getLogger("thentools"); 
	public static QcomUImain QcomUImain;
	public static MainUI mainFrame;
	public static String selectedID=null;
	public static String extraBinlocation=System.getProperty("user.dir")+"/extraBin";
	public static String datalocation=System.getProperty("user.dir")+"/data";
	public static AdbBridge getdevices=new AdbBridge();
	public static String platform="msm0";
	public static String ThenLogfile=FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/ThenLogs";
	public static boolean hidenlog=false;
	public static DBHandle dbhandle=new DBHandle();
	public static EnDecrypt crypt;
	public static String Language="CN";//CN EN
	public static Image imagelogo;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {//将界面设置为当前windows界面风格
//		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//		}
		//check enterway
//		if(!args[0].equals("zimakaimen")){
//			System.exit(0);
//		}
		//logger init
		InitUI initui=new InitUI();
		initui.run();
		LoggerUtil loggerutil=new LoggerUtil();
        try { 
        	loggerutil.setLogingProperties(logger); 
        } catch (SecurityException e) { 
            // TODO Auto-generated catch block 
        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
        } catch (IOException e) { 
            // TODO Auto-generated catch block 
        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
        }
		com.Main.ThenToolsRun.logger.log(Level.INFO, "**************ThenTools first run*********************");
		//connect DB
		if(!dbhandle.connectDB()){
			dbhandle.closeallDB();
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Can't connect to DB of tools, Pls close the old one, ThenTools will try again!", 
					"ThenTools", JOptionPane.ERROR_MESSAGE); 
			if(!dbhandle.connectDB()){
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Can't connect to DB, Pls close the old one!", 
						"ThenTools", JOptionPane.ERROR_MESSAGE); 
				com.Main.ThenToolsRun.logger.log(Level.WARNING,"Can't connect to DB!");
				System.exit(0);
			}
		}
		//init start
		Init init=new Init();
		init.run();
		//crypt
		crypt=new EnDecrypt();
		if(!crypt.isok()){
			com.Main.ThenToolsRun.logger.log(Level.WARNING,"Lisence has been expired! You see, pls support us!");
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Your Lisence has been expired!", 
					"ThenTools", JOptionPane.INFORMATION_MESSAGE); 
			AboutLisenceUI aboutlisence=new AboutLisenceUI();
			aboutlisence.setVisible(true);
			return;
		}
		//get Language
		Language=dbhandle.getSingleLineValue("UserSettings", "Language");
		com.Main.ThenToolsRun.logger.log(Level.INFO,"Get Language="+Language);
		//init UI
		QcomUImain= new QcomUImain();
		mainFrame= new MainUI();
        //check update
		UpdateThread  checkthread=new UpdateThread();
		new Thread(checkthread).start();
		//deadline
		if(System.getProperty("user.dir").contains(" ")||Helper.isChinese(System.getProperty("user.dir"))){
			com.Main.ThenToolsRun.logger.log(Level.INFO,"ThenTools can't run, pls install tool in the path without blank or Chinese. "+extraBinlocation);
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Can't run, pls install tool in the path without blank or Chinese.", 
					"ThenTools", JOptionPane.ERROR_MESSAGE); 
			System.exit(0);
			return;
		}
//		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
//		if(Integer.parseInt(sDateFormat.format(new Date()))>20990101){
//			com.Main.ThenToolsRun.logger.log(Level.INFO,"8");
//			System.exit(0);
//		}
		//create folder in desktop
		File file=new File(ThenLogfile);
		if(!file.exists()){
			file.mkdirs();
		}
		// check ue and pc 
		CheckUE checkue=new CheckUE();
		checkue.run();
		CheckPC checkpc=new CheckPC();
		checkpc.run();
		mainFrame.setVisible(true);
		com.Main.ThenToolsRun.logger.log(Level.INFO, "current directory: "+extraBinlocation);
	}
	
	
}
