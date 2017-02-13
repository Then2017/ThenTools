package com.GetScreen;

import java.io.File;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.Functions.Excute;

public class ScreenEdit {
		//use picture edit by windows
		public void EditByWin(String filepath){
			if(filepath.endsWith(".png")){
			File file=new File(filepath);
			if(file.exists()){
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
				Excute.execcmd("start mspaint.exe "+filepath, 1, true);	
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
			}
			}else{
				 com.Main.ThenToolsRun.logger.log(Level.INFO,"pls save image first! "+filepath);
			  JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls save image first!", "Message",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
}
