package com.Functions;

import java.util.logging.Level;

import javax.swing.JOptionPane;

public class Init {
	boolean allisok=true;
	public void run(){
		
		if(!getInitflag()){
			//init start
			InitContext();
			//check allisok
			if(allisok==false){
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Init failed2, pls check!", 
						"ThenTools", JOptionPane.ERROR_MESSAGE); 
				System.exit(0);
			}
			//set flag to yes
			if(setInitflag()){
				
			}else{
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Init failed3, pls check!", 
						"ThenTools", JOptionPane.ERROR_MESSAGE); 
				System.exit(0);
			}
		}

	}
	
	public void InitContext(){
//		com.Main.ThenToolsRun.dbhandle.Operate("CREATE TABLE \"APP\".\"HIDDENLOGSETTINGS\" (\"ID\" VARCHAR(255), \"ISOPEN\" VARCHAR(255), \"LOGINFO\" VARCHAR(3000),\"WAITTIME\" VARCHAR(255))");
//		if(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("HIDDENLOGSETTINGS", "ID").equals("1")){
//			
//		}else{
//			com.Main.ThenToolsRun.dbhandle.Operate("insert into HiddenLogSettings values('1','true','setprop log.tag.Telecom V;setprop log.tag.InCall V;setprop log.tag.ContactsPref V;setprop log.tag.Telephony V;setprop log.tag.Mms V;', '20000')");
//		}
		JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "V220160817版本更新介绍:\n"
				+ "1.修复外网环境下主文件被误删BUG!\n", 
				"ThenTools", JOptionPane.INFORMATION_MESSAGE); 
	}
	
	public boolean getInitflag(){
		String flag=Helper.readAllfromfile(com.Main.ThenToolsRun.datalocation+"/init").toString();
		flag=flag.replace("\n", "");
		com.Main.ThenToolsRun.logger.log(Level.WARNING,"read initflag="+flag);
		if(flag.contains("no")){
			return false;
		}else if(flag.contains("yes")){
			return true;
		}
		return false;
	}
	
	public boolean setInitflag(){
		Helper.writetoFile(com.Main.ThenToolsRun.datalocation+"/init", "yes", false);
		String flag=Helper.readAllfromfile(com.Main.ThenToolsRun.datalocation+"/init").toString();
		if(flag.contains("no")){
			com.Main.ThenToolsRun.logger.log(Level.WARNING,"write initflag failed");
			return false;
		}else if(flag.contains("yes")){
			return true;
		}
		return false;
	}
	//update database
	
}
