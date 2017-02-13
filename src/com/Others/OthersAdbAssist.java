package com.Others;

import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.Functions.Excute;

public class OthersAdbAssist {
	boolean inputtxtrunnable=false;
	
	//start hicam config
	public void Starthicamconfig(){
		Excute.execcmd2("am start com.hicam.hicamconfig/.HiCamConfig");
	}
	
	//input txt
//	public void InputTxt(final String str){
//		if(!str.equals("")){
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				inputtxtrunnable=true;
//				com.Main.ThenToolsRun.logger.log(Level.INFO,"input txt: "+str);
//				String[] txt=str.split("\n");
//				System.out.println(txt.length);
//				for(int i=0;i<txt.length;i++){
//					Excute.execcmd2("input text \""+txt[i]+"\"");
//					if(i<txt.length-1){
//					Excute.execcmd2("input keyevent 66");
//					}
//				}
//				inputtxtrunnable=false;
//			}
//		}).start();
//		}
//	}
//	public boolean getInputtxtrunnable(){
//		return inputtxtrunnable;
//	}
	//input txt by enter key
	public void InputTxtbyEnter(final String str){
		new Thread(new Runnable() {
			@Override
			public void run() {
				inputtxtrunnable=true;
				com.Main.ThenToolsRun.mainFrame.OthersUImain.btnInputText.setText(getString("btnInputText"));
				if(str.equals("")){
					Excute.execcmd2("input keyevent 66");
					com.Main.ThenToolsRun.logger.log(Level.INFO,"  input txt: input key 66");
				}else{
					Excute.execcmd2("input text \""+str+"\"");
					com.Main.ThenToolsRun.logger.log(Level.INFO,"input txt: "+str);
				}
				inputtxtrunnable=false;
				com.Main.ThenToolsRun.mainFrame.OthersUImain.btnInputText.setText(getString("btnInputText"));
			}
		}).start();
	}
	public boolean getInputtxtrunnable(){
		return inputtxtrunnable;
	}
	
	//Language 
	public String getString(String flag){
		switch(flag){
		case "btnInputText": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				if(inputtxtrunnable){
					return "正在输入...";
				}else{
					return "输入";
				}
		}else{
			if(inputtxtrunnable){
				return "Inputtings..";
			}else{
				return "Input";
			}
		}
			default: return "";
		}
	}
}
