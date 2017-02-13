package com.AutoScript;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import com.Functions.Helper;
import com.Functions.LoggerUtil;

public class EditScript {
    private SimpleDateFormat sDateFormatget = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
	JTextArea textAreaShowScript;
	String saveScriptpath;
	//insert Sleep
	public void Sleep(String time){
		textAreaShowScript.append("Sleep:("+time+"ms)\n");
	}
	//insert button
	public void Pressbutton(String button){
		textAreaShowScript.append("Press:("+button+"),1\n");
	}
	//start loop
	public void Startloop(String count){
		textAreaShowScript.append("==Start Loop:("+count+")==\n");
	}
	//end loop
	public void Endloop(){
		textAreaShowScript.append("==End Loop==\n");
	}
	//Screen cap
	public void Screencap(){
		textAreaShowScript.append("Screen Cap\n");
	}
	//Notes
	public void Notes(String notes){
		textAreaShowScript.append("**"+notes+"**\n");
	}
	//reboot
	public void Reboot(String time){
		textAreaShowScript.append("Reboot:("+time+"ms)\n");
	}
	//log
	public void Startlog(){
		textAreaShowScript.append("Active Log\n");
	}
	//wake
	public void Wake(){
		textAreaShowScript.append("Wake,1\n");
	}
	//wake
	public void Type(String str){
		textAreaShowScript.append("Type:("+str+"),1\n");
	}
	//save script 
	public boolean SaveScript(){
		File folder=new File(com.Main.ThenToolsRun.ThenLogfile+"/Script");
		if(!folder.exists()){
			folder.mkdirs();
		}
		       	JFileChooser fileChooser = new JFileChooser(com.Main.ThenToolsRun.ThenLogfile+"/Script");
		        fileChooser.setFileFilter(new FileFilter()
		        {
		          public String getDescription() {
		            return "*.script";
		          }

		          public boolean accept(File f)
		          {
		            String ext = f.getName().toLowerCase();
		            return ext.endsWith(".script");
		          }
		        });
		        fileChooser.setSelectedFile(new File("Script_PCtime"+sDateFormatget.format(new Date())));
		        if (fileChooser.showSaveDialog(com.Main.ThenToolsRun.mainFrame) != 0) return false;
		        try {
		          File file = fileChooser.getSelectedFile();
		          saveScriptpath = file.getAbsolutePath();
		          if (!saveScriptpath.endsWith(".script")) {
		            file = new File(saveScriptpath + "." + "script");
		            saveScriptpath=saveScriptpath+".script";
		          }
		          if(file.exists()){
						int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "The file is exist, Do you want to delete and continue? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
						if(confirm==0){
							file.delete();
						}else{
							com.Main.ThenToolsRun.logger.log(Level.INFO,"script exist and do not save");
							return false;
						}
		          }else{
		        	  file.createNewFile();
		          }
		         Helper.writeAlltoFile(saveScriptpath, textAreaShowScript.getText(), true);
		          return true;
		        } catch (Exception e) {
		        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		          JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Failed to save a script.", "Message",JOptionPane.ERROR_MESSAGE);
		        }
	    	return false;
	}
	//load script 
	public boolean LoadScript(){
		String filepath=SelectScript(saveScriptpath);
		if(!filepath.equals("")){
			textAreaShowScript.setText(Helper.readAllfromfile(filepath).toString()+"");
		}
		return true;
	}
	public String SelectScript(String saveScriptpath){
		if(saveScriptpath==null){
			saveScriptpath="";
		}
		String path="";
	  	JFileChooser fileChooser = new JFileChooser(com.Main.ThenToolsRun.ThenLogfile+"/Script");
        fileChooser.setFileFilter(new FileFilter()
        {
          public String getDescription() {
            return "*.script";
          }

          public boolean accept(File f)
          {
            String ext = f.getName().toLowerCase();
            return ext.endsWith(".script");
          }
        });
        fileChooser.setSelectedFile(new File(saveScriptpath));
        if (fileChooser.showOpenDialog(null) != 0) {
        	com.Main.ThenToolsRun.logger.log(Level.INFO,"No file select");
        	return path;
        }
        File file = fileChooser.getSelectedFile();
        if(!file.exists()){
        	com.Main.ThenToolsRun.logger.log(Level.INFO,"Invalid file, Pls select correct file!");
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Invalid file, Pls select correct file!", 
					"Message", JOptionPane.ERROR_MESSAGE); 
			return path;
        }else{
         path = file.getAbsolutePath();
        }
        com.Main.ThenToolsRun.logger.log(Level.INFO,"select file: "+path);
        return path;
	}
	
	
	
	//set textAreaShowScript
	public void settextAreaShowScript(JTextArea textAreaShowScript){
		this.textAreaShowScript=textAreaShowScript;
	}
}
