package com.Functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JOptionPane;

public class UpdateThread extends Thread{
	  /*6,run shell
	   *5,del folder 
	   *4,create folder
	   *3,update
	   *2,del
	   *1,copy 
	   *num,/target file
	   * */
	String Systemlocation=System.getProperty("user.dir");
    String CurrentVersion = Systemlocation+"/data/CurrentVersion.txt";
    String FTPVersion="\\\\10.120.10.100/ckt_cd_share/Test/ThenService/ThenTools/UpdateInfo.txt";
    String FTPfilelocation="\\\\10.120.10.100/ckt_cd_share/Test/ThenService/ThenTools/Update";
    String NetVersion = "url";
	boolean isUpdate=false;
	boolean allisok=true;
    String targetVersion;
    
      public UpdateThread(){
    	  
      }
    
	  @SuppressWarnings("deprecation")
	public void run() {
		//  com.Main.ThenToolsRun.dbhandle.setSingleLineValue("VersionInfo", "UpdateVer", "UpdateV120160612");
		  com.Main.ThenToolsRun.dbhandle.setSingleLineValue("VersionInfo", "ToolsVer", "ThenToolsV220160817");
		  String localVerStr = getNowVer(); //current ver
		  String newversion=null; //new ver
		  //check network 
		  if(checkFTP()){
			  newversion=getFTPVer();
			  com.Main.ThenToolsRun.logger.log(Level.INFO,"get new version by FTP "+newversion);
		  }else if(checkNetBaidu()){
			  if(checkNet()){
				  newversion=getNetVer();
				  com.Main.ThenToolsRun.logger.log(Level.INFO,"get new version by Net "+newversion);
				  }else{
			        	com.Main.ThenToolsRun.logger.log(Level.INFO,"exit: can't connect to service");
						//System.exit(0);
				  }
		  }
		  //if new verison get
		  if(newversion==null){
        	  com.Main.ThenToolsRun.logger.log(Level.INFO,"no Net or FTP");
        	  return;
          }else if (newversion.equals(localVerStr)) {
              isUpdate = false;
	        	com.Main.ThenToolsRun.logger.log(Level.INFO,"no new version");
          } else{
        	  isUpdate = true;
              targetVersion = newversion; 
          }
          //if update
          if(isUpdate){
        	  //copy
    		  if(Helper.copyFile(FTPfilelocation+"/Start.exe", Systemlocation+"/Start.exe.update")){
    		  }else {
    			  allisok=false;
        		  com.Main.ThenToolsRun.logger.log(Level.INFO,"copy file /Start.exe failed!!!");
    		  }
    		  //del
       		  if(Helper.delFile(Systemlocation+"/Start.exe")){
    		  }else{
    			  allisok=false;
        		  com.Main.ThenToolsRun.logger.log(Level.INFO,"del file Start.exe failed!!!");
    		  }
       		  //rename
    		  if(Helper.renameFile(Systemlocation+"/Start.exe.update")){
    		  }else{
    			  allisok=false;
        		  com.Main.ThenToolsRun.logger.log(Level.INFO,"update file /Start.exe failed!!!");
    		  }
    		  
        	  //update fial
        	  if(!allisok){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Update Error, pls try again later!", 
							"Message", JOptionPane.ERROR_MESSAGE); 
      	        	com.Main.ThenToolsRun.logger.log(Level.INFO,"exit with update error");
      				//System.exit(0);
        	  }else{
        		  allisok=updateVersion(newversion);//update DB
            	  //update fial
            	  if(!allisok){
    					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Update version Error, pls try again later!", 
    							"Message", JOptionPane.ERROR_MESSAGE); 
          	        	com.Main.ThenToolsRun.logger.log(Level.INFO,"exit with update version error");
            	  }else{
        		  com.Main.ThenToolsRun.logger.log(Level.INFO,"update ok");
            	  }
        	  }
          }else{
        	  com.Main.ThenToolsRun.logger.log(Level.INFO,"No need to update by FTP");
          }
          
	  }
	  //update version
	  public boolean updateVersion(String newversion){
		  boolean isok=false;
		  com.Main.ThenToolsRun.dbhandle.setSingleLineValue("VersionInfo", "UpdateVer", newversion);
		  if(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("VersionInfo", "UpdateVer").equals(newversion)){
			  isok=true;
		  }
		  return isok; 
	  }
	  	//get ftp ver
	  	public String getFTPVer(){
            //���ذ汾�ļ�
            File verFile = new File(FTPVersion);
            String ver="";
            FileReader is = null;
            BufferedReader br = null;

            //��ȡ���ذ汾
            try {
                is = new FileReader(verFile);
                br = new BufferedReader(is);
                br.readLine();//skip1
                ver = br.readLine();
                com.Main.ThenToolsRun.logger.log(Level.INFO,"FTPVer="+ver);
            } catch (FileNotFoundException e) {
            	com.Main.ThenToolsRun.logger.log(Level.INFO,"FTP version can't be found.\n");
            } catch (IOException e) {
            	com.Main.ThenToolsRun.logger.log(Level.INFO,"FTP version error\n");
            } finally {
                //�ͷ���Դ
                try {
                	if(br!=null){
                    br.close();
                	}
                	if(is!=null){
                    is.close();
                	}
                } catch (IOException e) {
                	  com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
                }
            }
            if(ver!=null){
            	return ver;
            }
            return "";
	  	}
	  	
	  	//get now ver 
	  	//from DB
	  	public String getNowVer() {
	  		String ver=com.Main.ThenToolsRun.dbhandle.getSingleLineValue("VersionInfo", "UpdateVer");
            com.Main.ThenToolsRun.logger.log(Level.INFO,"NowVer="+ver);
            return ver;
        }
	  	public String getNowVer2() {
            //���ذ汾�ļ�
            File verFile = new File(CurrentVersion);
            FileReader is = null;
            BufferedReader br = null;
            String ver = null;
            //��ȡ���ذ汾
            try {
                is = new FileReader(verFile);
                br = new BufferedReader(is);
                br.readLine();//skip1
                ver = br.readLine();
                com.Main.ThenToolsRun.logger.log(Level.INFO,"NowVer="+ver);
            } catch (FileNotFoundException e) {
            	com.Main.ThenToolsRun.logger.log(Level.INFO,"Now version can't be found.\n");
            } catch (IOException e) {
            	com.Main.ThenToolsRun.logger.log(Level.INFO,"Now version error.\n");
            } finally {
                //�ͷ���Դ
                try {
                	if(br!=null){
                        br.close();
                    	}
                    	if(is!=null){
                        is.close();
                    	}
                } catch (IOException e) {
                	  com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
                }
            }
            if(ver!=null){
                return ver;
            }
            return ver;
        }
    
	  	//get net ver
		public String getNetVer(){
	         //�����ļ��汾��ʶURL
			/*
			������ͨ��HTTP����һ��ҳ��,��ȡ�������ϵİ汾��
			����������������ҳ��ֱ�Ӵ�ӡ�� 6.19.1.1
			Ȼ�������汾�űȶԱ��صİ汾��,����汾�Ų�ͬ�Ļ�,�ʹ������������µĳ��򲢸������г���
			*/
	          URL url = null;
	          InputStream is = null;
	          InputStreamReader isr = null;
	          BufferedReader netVer = null;
	          //��ȡ�����ϵİ汾��
	          try {
	              url = new URL(NetVersion);
	              is = url.openStream();
	              isr = new InputStreamReader(is);

	              netVer = new BufferedReader(isr);
	              String netVerStr = netVer.readLine();
	              com.Main.ThenToolsRun.logger.log(Level.INFO,"get netVer="+netVerStr);
	              //return netVerStr;
	          } catch (MalformedURLException e) {
	        	  com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
	          } catch (IOException e) {
	        	  com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
	          } finally {
	              //�ͷ���Դ
	              try {
	            	  if(netVer!=null){
	                  netVer.close();
	            	  }
	            	  if(isr!=null){
	                  isr.close();
	            	  }
	            	  if(is!=null){
	                  is.close();
	            	  }
	              } catch (IOException e) {
	            	  com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
	              }
	          }
	          return null;
	  	}
		
		//check net to baidu
		public boolean checkNetBaidu(){
			URL url = null;
			try {
			url = new URL("http://www.baidu.com");
			InputStream in = url.openStream();
			in.close();
			return true;
			} catch (IOException e) {
			com.Main.ThenToolsRun.logger.log(Level.INFO,"Net to baidu fial:��" + url.toString());
			return false;
			}
		}
		//check net
		public boolean checkNet(){
			URL url = null;
			try {
			url = new URL("http://www.baidu.com");
			InputStream in = url.openStream();
			in.close();
			return true;
			} catch (IOException e) {
			com.Main.ThenToolsRun.logger.log(Level.INFO,"Net to service fial:��" + url.toString());
			return false;
			}
		}
		//check ftp
		public boolean checkFTP(){
			try
			{
				InetAddress ad = InetAddress.getByName("10.120.10.100");//10.120.10.100\ckt_cd_share\Test\ThenService\ThenTools
				boolean state = ad.isReachable(2000);//�����Ƿ���Դﵽ�õ�ַ
				if(state){
				}else{
					com.Main.ThenToolsRun.logger.log(Level.INFO,"FTP connect fail");
				}
				return state;
			}catch(IOException e){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"FTP connect error");
				return false;
			}
		}
		//run thentools
		public void runtools(){
				
		}
}
