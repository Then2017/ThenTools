package com.Monkey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.Functions.Helper;
import com.Functions.LoggerUtil;
import com.Monkey.MonkeyActive.ActiveMonkeyThread;

public class MonkeyAnalysisFile {
	boolean monkeyanalysisthreadrun=false;
	private SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss MMdd-yyyy");
	
	int arow;//��������
	int arowword;//ÿ�з�������
	int arowcount;//������������
	int crashcount;
	String filepath;
	List<String> crashlist=new ArrayList<String>();//crash string after substring
	boolean startprint;
	boolean logbymonitor;
	boolean showduplicate;
    StringBuffer crashstrbuffer=new StringBuffer();//������crash�ַ���
    StringBuffer crashstrOriginalbuffer=new StringBuffer();//������crash�ַ���
    List<String> crashnumlist=new ArrayList<String>();//crash issue num
    List<String> crashOriginallist=new ArrayList<String>();//crash string before substring
    
	public void run(int arow,int aorwword,boolean showduplicate){
		this.arow=arow;
		this.arowword=aorwword;
		this.showduplicate=showduplicate;
		com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
		MonkeyAnalysisThread monkeyanalysisthread = new MonkeyAnalysisThread();
		new Thread(monkeyanalysisthread).start();
		com.Main.ThenToolsRun.logger.log(Level.INFO,"start monkey analysis...");
	}
	
	//set as original
	public void settooriginal(){
	//	this.arow=arow; //��������
	//	this.arowword=arowword;//ÿ�з�������
		arowcount=0;//������������
		crashcount=0;
		startprint=false;
		logbymonitor=false;//just for time judge
		crashlist.clear();
		crashnumlist.clear();
		crashOriginallist.clear();
		crashstrbuffer.setLength(0);
	}
	
	//read log and create temp file
	public void readlogfile(String filePath){
			File file = new File(filePath);  
			File tempfile=new File(filePath+"_all");
			if(!tempfile.exists()){
				try {
					tempfile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				tempfile.delete();
				if(!tempfile.exists()){
					try {
						tempfile.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	        BufferedReader reader = null;  
	        try {  
	            reader = new BufferedReader(new FileReader(file));  
	            String tempString = null;  
	          //  long line = 1;  
	            // һ�ζ���һ�У�ֱ������nullΪ�ļ����� 
	            //if this log is created by monkey monitor
	            if((tempString = reader.readLine()) != null){
	            	if(tempString.contains("This log created by Monkey Monitor of ThenTools")){
	            	logbymonitor=true;
	            	}
	            }
	            while ((tempString = reader.readLine()) != null) {  
	           //     System.out.println(line + ": " + tempString);
	            	if(tempString.contains("CRASH:")){ //#Intent 
		                if(arowcount>=1){//����ڶ���crash����С��arow
		                	crashlist.add(crashstrbuffer.toString());//��Ӵ������crash�ַ�����list
		                	crashOriginallist.add(crashstrOriginalbuffer.toString());//���ԭʼ��crash�ַ�����list
		                }
	            		startprint=true;
	            		arowcount=0;//����ڶ���crash����С��arow
	            		crashcount++;
	            		crashstrbuffer.setLength(0);//���crash�ַ���
	            		crashstrbuffer.append("*****************************************"
			        			+ "Issue Num="+crashcount
			        			+"*****************************************\n");
	            		crashstrOriginalbuffer.setLength(0);
	            		crashstrOriginalbuffer.append("*****************************************"
			        			+ "Issue Num="+crashcount
			        			+"*****************************************\n");
	            		Helper.writetoFile(tempfile.getAbsolutePath(), "*****************************************"
			        			+ "Issue Num="+crashcount
			        			+"*****************************************", true);
	            	}
	            	
	            	if(startprint){
	            		crashstrOriginalbuffer.append(tempString+"\n");//��ӷ���ǰ��Crash�ַ���
	            		Helper.writetoFile(tempfile.getAbsolutePath(), tempString, true);//������ӡcrash
	            		//ȥ�����������
	            		String[] spitstr=tempString.split("\\(");
	            		tempString=spitstr[0];
	            		//�ж��Ƿ���ʱ����21���ַ�,��ȡǰarowword���ַ�
	                if(tempString.length()>arowword&&logbymonitor){
	                	tempString=tempString.substring(21, arowword); //21 for time count
	                }else if(tempString.length()>arowword&&!logbymonitor){
	                	tempString=tempString.substring(0, arowword);
	                }
	                //ȥ��Build ��
	                if(!tempString.contains("// Build")){
	                	crashstrbuffer.append(tempString+"\n");//��ӷ������crash�ַ���
	                }
	                arowcount++;
	                //�ﵽ��������
	                if(arowcount>=arow){
	                	startprint=false;
	                	arowcount=0;
	                	crashlist.add(crashstrbuffer.toString());//��Ӵ������crash�ַ�����list
	                	crashOriginallist.add(crashstrOriginalbuffer.toString());//���ԭʼ��crash�ַ�����list
	                //	System.out.println(crashstrbuffer.toString());
	                }
	            	}
	          //      line++;  
	            }   
	        } catch (IOException e) {  
	        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);  
	        } finally {  
	            if (reader != null) {  
	                try {  
	                    reader.close();  
	                } catch (IOException e) {  
	                	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e); 
	                }  
	            }  
	        }  
	}
	
	//compare list, set repeat as "",get issue num
	public void comparelist(List<String> crashlist){
		String tempi,tempj;
		List<String> result=new ArrayList<String>();
		for(int i=0;i<crashlist.size();i++){
			if(crashlist.get(i).equals("")){//�����հ�ֱ������
				continue;
			}
			tempi=crashlist.get(i);
			for(int j=i+1;j<crashlist.size();j++){
				tempj=crashlist.get(j);
				if(tempi.substring(tempi.indexOf("\n")+1).equals(tempj.substring(tempj.indexOf("\n")+1))){
					crashlist.set(j, "");//�ظ�������Ϊ�հ�
				}
			}
		}
		//��ȡ���ظ���issue num
		for(int i=0;i<crashlist.size();i++){
			tempi=crashlist.get(i);
			if(!tempi.equals("")){
				crashnumlist.add(tempi.substring(tempi.indexOf("=")+1,tempi.indexOf("*", tempi.indexOf("="))));
			}
		}
	}
	//get original crash string via issue num
	public void getOriginalIssue(List<String> crashnumlist,List<String> crashOriginallist){
		String tempi,tempj;
		boolean isok=false;
		for(int i=0;i<crashOriginallist.size();i++){
			tempi=crashOriginallist.get(i);//�õ�original issue
			tempi=tempi.substring(tempi.indexOf("=")+1,tempi.indexOf("*", tempi.indexOf("=")));
			
			for(int j=0;j<crashnumlist.size();j++){
					tempj=crashnumlist.get(j);//�õ�issue num
					if(tempi.equals(tempj)){
						isok=true;
						break;//��������ͬ��,����
					}
					isok=false;
			}
			if(!isok){
				crashOriginallist.set(i, "");//�ظ�������Ϊ�հ�
			}
		}
	}
	//print to UI
	public void printtoUI(List<String> crashOriginallist){
		String tempi;
		File tempfile=new File(filepath+"_filter");
		if(!tempfile.exists()){
			try {
				tempfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			tempfile.delete();
			if(!tempfile.exists()){
				try {
					tempfile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//print
		com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().setText("");//clear
		int showcount=0;
		for(int i=0;i<crashOriginallist.size();i++){
			if(!crashOriginallist.get(i).equals("")){
				showcount++;
			}
		}
		if(!showduplicate){
		com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append(sDateFormat.format(new Date())+":  "+
					"The result of filter duplication issues, total="+showcount+" issues\n");
		}else{
			com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append(sDateFormat.format(new Date())+":  "+
					"The result of all issues, total="+showcount+" issues\n");
		}
		for(int i=0;i<crashOriginallist.size();i++){
			tempi=crashOriginallist.get(i);
			if(!tempi.equals("")){
			com.Main.MainUI.MonkeyUImain.getmonkeymonitorui().gettextAreaShowlog().append(tempi);//��ӡ��UI
			tempi=tempi.replaceAll("\n", "\r\n");
			Helper.writetoFile(tempfile.getAbsolutePath(),tempi, true);//���˺�Ĵ�ӡ���ļ�
			}
		}
		
	}
	
	
	//select file
	public String selectfile(String latestlog){
		String path="";
	  	JFileChooser fileChooser = new JFileChooser(com.Main.ThenToolsRun.ThenLogfile+"/MonkeyMonitor");
        fileChooser.setFileFilter(new FileFilter()
        {
          public String getDescription() {
            return "*.log";
          }

          public boolean accept(File f)
          {
            String ext = f.getName().toLowerCase();
            return ext.endsWith(".log");
          }
        });
        fileChooser.setSelectedFile(new File(latestlog));
        if (fileChooser.showOpenDialog(null) != 0) {
        	com.Main.ThenToolsRun.logger.log(Level.INFO,"No file select");
        	 filepath=path;
        	return path;
        }
        File file = fileChooser.getSelectedFile();
        if(!file.exists()){
        	com.Main.ThenToolsRun.logger.log(Level.INFO,"Invalid file, Pls select correct file!");
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Invalid file, Pls select correct file!", 
					"Message", JOptionPane.ERROR_MESSAGE); 
			 filepath=path;
			return path;
        }else{
         path = file.getAbsolutePath();
        }
        com.Main.ThenToolsRun.logger.log(Level.INFO,"select file: "+path);
        filepath=path;
        return path;
	}
	
	public boolean getmonkeyanalysisthreadrun(){
		return monkeyanalysisthreadrun;
	}
	
	class MonkeyAnalysisThread implements Runnable {

		public MonkeyAnalysisThread() {

		}

		public void run() {
			monkeyanalysisthreadrun=true;
			//�ָ�Ĭ������
			settooriginal();
			//��ȡlog�ļ�,���ַ���
			readlogfile(filepath);
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(30);//******************
			if(!showduplicate){
			//�Ƚ���ʱlist,�õ�num
			comparelist(crashlist);
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(50);//******************
			//ʹ�õõ���num,�õ�ԭʼissue
			getOriginalIssue(crashnumlist,crashOriginallist);
			}
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(70);//******************
			//��ԭʼissue��ӡ��UI
			printtoUI(crashOriginallist);
			com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(100);//******************
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Analysis monkey log file compeleted!", 
					"Message", JOptionPane.INFORMATION_MESSAGE);
			monkeyanalysisthreadrun=false;
		}
	}
}
