package com.MainGate;

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

public class ThenToolsRuntemp {

	public static Logger logger; 
	public static String selectedID;
	public static String extraBinlocation;
	public static String datalocation;
	public static AdbBridge getdevices;
	public static String platform;
	public static String ThenLogfile;
	public static boolean hidenlog;
	public static DBHandle dbhandle;
	public static String Language;//CN EN
	public static Image imagelogo;
	
	public static void main(String[] args) {
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
			dbhandle.closeDB();
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
		//check enterway
		if(!args[0].equals(dbhandle.getSingleLineValue("Code", "currentcode"))){
			com.Main.ThenToolsRun.logger.log(Level.WARNING,"Pls run start.bat to start!");
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls run start.bat to start!", 
					"ThenTools", JOptionPane.INFORMATION_MESSAGE); 
			System.exit(0);
		}
		//get Language
		Language=dbhandle.getSingleLineValue("UserSettings", "Language");
		com.Main.ThenToolsRun.logger.log(Level.INFO,"Get Language="+Language);
        //check update
		UpdateThread  checkthread=new UpdateThread();
		new Thread(checkthread).start();
		//deadline
		if(System.getProperty("user.dir").contains(" ")){
			com.Main.ThenToolsRun.logger.log(Level.INFO,"ThenTools can't run, pls install tool in the path without blank. "+extraBinlocation);
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
		File file=new File(ThenLogfile);
		if(!file.exists()){
			file.mkdirs();
		}
		// check ue and pc 
		CheckUE checkue=new CheckUE();
		checkue.run();
		CheckPC checkpc=new CheckPC();
		checkpc.run();
		com.Main.ThenToolsRun.logger.log(Level.INFO, "current directory: "+extraBinlocation);
	}
	
	
}
