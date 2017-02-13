package com.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import com.Functions.LoggerUtil;

public class DBHandle {
		private final static String DB_URL = "jdbc:derby:"+com.Main.ThenToolsRun.datalocation+"/DB";
		private final static String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
		Connection conn = null;
		Statement stat=null;
		
		//connect DB
		public boolean connectDB() {
			try {
				Class.forName(DERBY_DRIVER);
				Properties properties = new Properties();

				conn = DriverManager.getConnection(DB_URL, properties);
				stat = conn.createStatement();
				com.Main.ThenToolsRun.logger.log(Level.INFO, "connect to DB");
			} catch (ClassNotFoundException e) {
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				return false;
			} catch (SQLException e) {
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				return false;
			} 
			return true;
		}
		//close DB
		public void closeDB(){
				try {
					if(stat!=null){
						stat.close();
						stat=null;
					}
					if(conn!=null){
						conn.close();
						conn=null;
					}
					closeallDB();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				}
		}
		//close all DB
		public void closeallDB(){
			// 内嵌模式数据库操作用完之后需要关闭数据库,这里没有执行数据库名称则全部关闭.
			try {
				DriverManager.getConnection(DB_URL+";shutdown=true");
			} catch (SQLException e) {
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
			com.Main.ThenToolsRun.logger.log(Level.INFO, "close DB");
		}
		//get single line value from table
		public String getSingleLineValue(String Table,String column){
			ResultSet result = null;
			String resultstr="";
			try {
				result = stat.executeQuery("SELECT "+column+" FROM "+Table);
				while (result.next()) {
					resultstr=result.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}finally{
				if(result!=null){
					try {
						result.close();
						result=null;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
					}
				}
			}
			if(resultstr.equals("")){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"get Table="+Table+",column="+column+"failed,value=\"\"");
			}
			return resultstr;
		}

		//set single line value with table
		public boolean setSingleLineValue(String Table,String column,String value){
			int isok=0;
			try {
				 isok=stat.executeUpdate("UPDATE "+Table+" SET "+column+"='"+value+"' WHERE ID='1'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
			 if(isok==0){
				 com.Main.ThenToolsRun.logger.log(Level.INFO, "execute failed: "+"UPDATE "+Table+" SET "+column+"='"+value+"' WHERE ID='1'");
				 return false;
			 }
			return true;
		}
		
		//operate database
		public boolean Operate(String cmd){
			int isok=0;
			try {
				isok=stat.executeUpdate(cmd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
			}
			 if(isok==0){
				 com.Main.ThenToolsRun.logger.log(Level.INFO, "execute failed: "+cmd);
				 return false;
			 }
			 return true;
		}
}
		//http://www.ibm.com/developerworks/cn/opensource/os-ad-trifecta12/
		//call SYSCS_UTIL.SYSCS_COMPRESS_TABLE('APP','CODE',1);
		//dblook -d url
//CREATE TABLE "APP"."MONKEYMONITORSETTINGS" ("ID" VARCHAR(255), "AROW" VARCHAR(255), "AROWWORD" VARCHAR(255), "SHOWDUPLICATE" VARCHAR(255), "ISRECONNECT" VARCHAR(255), "DIYMONKEY" VARCHAR(3000));
//
//CREATE TABLE "APP"."COMMAND" ("ID" VARCHAR(255), "LOG1" VARCHAR(255), "LOG2" VARCHAR(255), "MONKEY1" VARCHAR(255), "MONKEY2" VARCHAR(255));
//
//CREATE TABLE "APP"."CODE" ("ID" VARCHAR(255), "CURRENTCODE" VARCHAR(255), "KEYCODE" VARCHAR(7000));
//
//CREATE TABLE "APP"."USERSETTINGS" ("ID" VARCHAR(255), "LANGUAGE" VARCHAR(255));
//
//CREATE TABLE "APP"."VERSIONINFO" ("ID" VARCHAR(255), "TOOLSVER" VARCHAR(255), "UPDATEVER" VARCHAR(255));
//CREATE TABLE "APP"."HIDDENLOGSETTINGS" ("ID" VARCHAR(255), "ISOPEN" VARCHAR(255),"ISOPENDEBUG" VARCHAR(255),"ISROOT" VARCHAR(255),   "LOGINFO" VARCHAR(3000),"WAITTIME" VARCHAR(255));

		//ij   connect'jdbc:derby:E:\ADTworkspace\ThenTools\data\DB';

		//Table MonkeyMonitorSettings
		//id arow arowword showduplicate isreconnect diymonkey
		//1 15 80 false true XXXX
		//insert into MonkeyMonitorSettings values('1','15','80','false','true','-p <package> -p <package> --ignore-crashes --ignore-timeouts --ignore-security-exceptions');
		//alter table MonkeyMonitorSettings add diymonkey varchar(3000)

		//Table Code
		//id currentcode keycode
		//insert into Code values('1','817B8CBADE0E7D9E9CA9DAE0C27D18D976B16A237EA33875BB836CB91751FBD5','x');

		//Table VersionInfo
		// id toolsver updatever
		//insert into VersionInfo values('1','ThenToolsVtest0311','UpdateVtest0311');
		
		//Table UserSettings
		//id Language
		//insert into UserSettings values('1','CN');
		
		//Table Command
		//id log1 log2 mongkey1
		//insert into Command values('1','kill -9 `cat ','echo while true;do ','|tee /sdcard/monkeylog.txt','|tee -a /sdcard/monkeylog.txt');

		//Table HiddenLogSettings
		//id isopen isopendebug Loginfo waittime
		//insert into HiddenLogSettings values('1','false','true','true','setprop log.tag.Telecom V;setprop log.tag.InCall V;setprop log.tag.ContactsPref V;setprop log.tag.Telephony V;setprop log.tag.Mms V;', '20000');