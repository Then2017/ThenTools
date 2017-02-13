package com.AD;

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
import com.Main.InitUI;
import com.Main.MainUI;
import com.Main.QcomUImain;
import com.android.ddmlib.IDevice;

public class tempThenToolsRun {
	public   Logger logger = Logger.getLogger("then"); 
	public static QcomUImain QcomUImain;
	public static MainUI mainFrame;
	public static String selectedID=null;
	public  String extraBinlocation=System.getProperty("user.dir");
	public  String datalocation=System.getProperty("user.dir")+"/extreBin";
	public  AdbBridge getdevices=new AdbBridge();
	public  String platform="msm0";
	public  String ThenLogfile=FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/ThenLogs";
	public  boolean hidenlog=false;
	public  DBHandle dbhandle=new DBHandle();
	public static EnDecrypt crypt;
	public static String Language="CN";//CN EN
	public static Image imagelogo;
	
	public static void main(String[] args) {
		tempThenToolsRun then=new tempThenToolsRun();
		//logger init
		InitUI initui=new InitUI();
		initui.run();
		LoggerUtil loggerutil=new LoggerUtil();
        try { 
        	loggerutil.setLogingProperties(then.logger); 
        } catch (SecurityException e) { 
            // TODO Auto-generated catch block 
        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
        } catch (IOException e) { 
            // TODO Auto-generated catch block 
        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
        }
		com.Main.ThenToolsRun.logger.log(Level.INFO, "**************ThenTools first run*********************");
		//connect DB
		if(!then.dbhandle.connectDB()){
			then.dbhandle.closeallDB();
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Can't connect to DB of tools, Pls close the old one, ThenTools will try again!", 
					"ThenTools", JOptionPane.ERROR_MESSAGE); 
			if(!then.dbhandle.connectDB()){
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Can't connect to DB, Pls close the old one!", 
						"ThenTools", JOptionPane.ERROR_MESSAGE); 
				com.Main.ThenToolsRun.logger.log(Level.WARNING,"Can't connect to DB!");
				System.exit(0);
			}
		}
		//check enterway
//		if(!args[0].equals(dbhandle.getSingleLineValue("Code", "currentcode"))){
//			com.Main.ThenToolsRun.logger.log(Level.WARNING,"Pls run start.bat to start!");
//			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls run start.bat to start!", 
//					"ThenTools", JOptionPane.INFORMATION_MESSAGE); 
//			System.exit(0);
//		}
		//init start
		Init init=new Init();
		init.run();
		//get Language
		Language=then.dbhandle.getSingleLineValue("UserSettings", "Language");
		com.Main.ThenToolsRun.logger.log(Level.INFO,"Get Language="+Language);
		//init UI
		mainFrame= new MainUI();
        //check update
		UpdateThread  checkthread=new UpdateThread();
		new Thread(checkthread).start();
		//deadline
		if(System.getProperty("user.dir").contains(" ")){
			com.Main.ThenToolsRun.logger.log(Level.INFO,"ThenTools can't run, pls install tool in the path without blank. "+then.extraBinlocation);
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Can't run, pls install tool in the path without blank.", 
					"ThenTools", JOptionPane.ERROR_MESSAGE); 
			return;
		}
//		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
//		if(Integer.parseInt(sDateFormat.format(new Date()))>20990101){
//			com.Main.ThenToolsRun.logger.log(Level.INFO,"8");
//			System.exit(0);
//		}
		//create folder in desktop
		File file=new File(then.ThenLogfile);
		if(!file.exists()){
			file.mkdirs();
		}
		mainFrame.setVisible(true);
		com.Main.ThenToolsRun.logger.log(Level.INFO, "current directory: "+then.extraBinlocation);
	}
	
	
}
