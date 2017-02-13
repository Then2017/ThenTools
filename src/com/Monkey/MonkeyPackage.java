package com.Monkey;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import com.Functions.Excute;

public class MonkeyPackage {
	PackageInfo packageinfo=new PackageInfo();
	ArrayList<String> arrayApps=new ArrayList();
	ArrayList<String> arrayGMS=new ArrayList();
	//get apps
	public ArrayList<String> getPMlistAPP(){
		if(com.Main.ThenToolsRun.selectedID!=null){
		arrayApps.clear();
		arrayGMS.clear();
		List<String> resultlist=Excute.returnlist2("pm list package");
		//List<String> resultlist=Excute.returnlist("pm list package", 2, true);
		String temp;
		if(com.Main.ThenToolsRun.platform.contains("msm")){
		for(int i=0;i<resultlist.size();i++){
			if(!resultlist.get(i).toString().equals("")&&resultlist.get(i).toString().length()>7){
				temp=resultlist.get(i).toString().substring(8, resultlist.get(i).length());
				if(!packageinfo.getGMS(temp)){
					temp=packageinfo.getQcomname(temp);
					arrayApps.add(temp);
				}else{
					temp=packageinfo.getGMSname(temp);
					arrayGMS.add(temp);	
					
				}
			}
		}
		}else{
			for(int i=0;i<resultlist.size();i++){
				if(!resultlist.get(i).toString().equals("")&&resultlist.get(i).toString().length()>7){
					temp=resultlist.get(i).toString().substring(8, resultlist.get(i).length());
					if(!packageinfo.getGMS(temp)){
						temp=packageinfo.getMTKname(temp);
						arrayApps.add(temp);
					}else{
						temp=packageinfo.getGMSname(temp);
						arrayGMS.add(temp);	
					}
				}
			}
			}
		}
		return arrayApps;
	}
	//get GMS
	public ArrayList<String> getPMlistGMS(){
		return arrayGMS;
	}
	
	
}
