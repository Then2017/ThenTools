package com.Qcom;

import org.jfree.chart.labels.IntervalCategoryItemLabelGenerator;

public class QcomHiddenLogSettings {
	//get
		public boolean getIsopen(){
			if(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("HiddenLogSettings","isopen").equals("false")){
				return false;
			}
			return true;
		}
		public boolean getIsopendebug(){
			if(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("HiddenLogSettings","isopendebug").equals("false")){
				return false;
			}
			return true;
		}
		public boolean getIsroot(){
			if(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("HiddenLogSettings","isroot").equals("false")){
				return false;
			}
			return true;
		}
		public String getLoginfo(){
			return com.Main.ThenToolsRun.dbhandle.getSingleLineValue("HiddenLogSettings","Loginfo");
		}
		public int getWaittime(){
			return Integer.parseInt(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("HiddenLogSettings","Waittime"));
		}
		//set
		public void setIsopen(String value){
			 com.Main.ThenToolsRun.dbhandle.setSingleLineValue("HiddenLogSettings","isopen", value);
		}
		public void setIsopendebug(String value){
			 com.Main.ThenToolsRun.dbhandle.setSingleLineValue("HiddenLogSettings","isopendebug", value);
		}
		public void setIsroot(String value){
			 com.Main.ThenToolsRun.dbhandle.setSingleLineValue("HiddenLogSettings","isroot", value);
		}
		public void setLoginfo(String value){
			 com.Main.ThenToolsRun.dbhandle.setSingleLineValue("HiddenLogSettings","Loginfo", value);
		}
		public void setWaittime(String value){
			 com.Main.ThenToolsRun.dbhandle.setSingleLineValue("HiddenLogSettings","Waittime", value);
		}
}
