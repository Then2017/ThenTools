package com.Monkey;

public class MonkeyMonitorSettings {
	//Table MonkeyMonitorSettings   
	//ID  |AROW       |AROWWORD   |SHOWduplicate
	//1     15         |80         |false
	
	//get
	public int getArow(){
		return Integer.parseInt(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("MonkeyMonitorSettings","arow"));
	}
	public int getArowword(){
		return Integer.parseInt(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("MonkeyMonitorSettings","arowword"));
	}
	public boolean getShowDuplicate(){
		if(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("MonkeyMonitorSettings","showduplicate").equals("false")){
			return false;
		}
		return true;
	}
	public boolean getIsreconnect(){
		if(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("MonkeyMonitorSettings","isreconnect").equals("false")){
			return false;
		}
		return true;
	}
	//set
	public void setArow(String value){
		 com.Main.ThenToolsRun.dbhandle.setSingleLineValue("MonkeyMonitorSettings","AROW", value);
	}
	public void setArowword(String value){
		 com.Main.ThenToolsRun.dbhandle.setSingleLineValue("MonkeyMonitorSettings","AROWWORD", value);
	}
	public void setShowDuplicate(String value){
		 com.Main.ThenToolsRun.dbhandle.setSingleLineValue("MonkeyMonitorSettings","SHOWDUPLICATE", value);
	}
	public void setIsreconnect(String value){
		 com.Main.ThenToolsRun.dbhandle.setSingleLineValue("MonkeyMonitorSettings","Isreconnect", value);
	}
}
