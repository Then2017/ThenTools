package com.Functions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.ConsoleHandler; 
import java.util.logging.XMLFormatter;

public class LoggerUtil {
    /** 
     * �õ�Ҫ��¼����־��·�����ļ����� 
     * @return 
     */ 
    private String getLogName() { 
        StringBuffer logPath = new StringBuffer(); 
        File file = new File(System.getProperty("user.dir")+"/Log"); 
        if (!file.exists()) {
            file.mkdir();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMdd_HHmm_ss"); 
        logPath.append(System.getProperty("user.dir")+"/Log"+"/"+sdf.format(new Date())+".log"); 
        return logPath.toString(); 
    } 
    //clear log when >50
    public int clearLogs(){
    	int count=0;
    	File file=new File(System.getProperty("user.dir")+"/Log");
    	File tempfile=null;
    	if(file.exists()){
    		 String[] templist = file.list();
    		 if(templist.length>50){
    			 for (int i = 0; i < templist.length-10; i++) {
    			 tempfile=new File(file.getAbsolutePath()+"/"+templist[i].toString());
    			 if(tempfile.isFile()){
    				 tempfile.delete();
    				 count++;
    			 }
    			 }
    		 }
    	}
    	return count;
    }
    
    //log formatter
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd"); 
    class MyLogHander extends Formatter { 
        @Override 
        public String format(LogRecord record) { 
        	if(com.Main.ThenToolsRun.hidenlog==true){
                return sdf.format(record.getMillis())+" "+record.getSourceClassName()+" ("+
                		record.getSourceMethodName()+") "+record.getThreadID()+"\r\n"
                		+record.getLevel() + ": " +record.getMessage()+"\r\n"; 
        	}else{
        		 return sdf.format(record.getMillis())+" "
                 		+record.getLevel() + ": " +record.getMessage()+"\r\n"; 
        	}
        }
    }
    
    //print e
    public static void printException(Exception e) {
        try {
        	if(com.Main.ThenToolsRun.hidenlog==true){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            com.Main.ThenToolsRun.logger.log(Level.WARNING,sw.toString());
        	}
        } catch (Exception e2) {
        	com.Main.ThenToolsRun.logger.log(Level.INFO,"print exception error");
        }
    }
    /** 
     * ����Logger���������־�ļ�·�� 
     * @param logger  
     * @throws SecurityException 
     * @throws IOException 
     */ 
    public void setLogingProperties(Logger logger) throws SecurityException, IOException { 
            setLogingProperties(logger,Level.ALL); 
            com.Main.ThenToolsRun.logger.log(Level.INFO,"log folder delete: "+clearLogs());
        } 
         
        /** 
         * ����Logger���������־�ļ�·�� 
         * @param logger 
         * @param level ����־�ļ������level�������ϵ���Ϣ 
         * @throws SecurityException 
         * @throws IOException 
         */ 
//	SEVERE�����ֵ��
//	WARNING
//	INFO
//	CONFIG
//	FINE
//	FINER
//	FINEST�����ֵ��
        public void setLogingProperties(Logger logger,Level level) { 
            FileHandler fh; 
            try { 
//            	ConsoleHandler consoleHandler =new ConsoleHandler(); 
//                consoleHandler.setLevel(Level.ALL); 
//                logger.addHandler(consoleHandler);//���������̨ 
                fh = new FileHandler(getLogName(),true); 
                fh.setFormatter(new MyLogHander());//�����ʽ SimpleFormatter
                logger.addHandler(fh);//��־����ļ� 
                logger.setLevel(Level.INFO); 
            } catch (SecurityException e) { 
                logger.log(Level.SEVERE, "��ȫ�Դ���", e); 
            } catch (IOException e) { 
                logger.log(Level.SEVERE,"��ȡ�ļ���־����", e); 
            } 
        }

         

    }
