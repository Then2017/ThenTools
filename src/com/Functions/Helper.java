package com.Functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
	
	//�ȴ��ֻ�
	public static void adbwaitfordevices(){
		Excute.execcmd("wait-for-device", 3, true);
		
		
	}
	//�Ƿ�������
	 public static final boolean isChinese(String strName) {  
	        char[] ch = strName.toCharArray();  
	        for (int i = 0; i < ch.length; i++) {  
	            char c = ch[i];  
	            if (isChinese(c)) {  
	                return true;  
	            }  
	        }  
	        return false;  
	    } 
  private static final boolean isChinese(char c) {  
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
	        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
	                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
	                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
	                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
	                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
	                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
	            return true;  
	        }  
	        return false;  
	  }
	 
	//�ж��Ƿ�����
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	//�ж��Ƿ�����
	public static boolean isDecimal(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*.[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	//7zѹ��
	public static boolean compression(String tregetfilepath,String sourcefilepath){
		boolean isok=false;
		String[] result=Excute.execcmd(com.Main.ThenToolsRun.extraBinlocation+"/7za.exe a "+tregetfilepath+".7z "+sourcefilepath, 1, true);
		if(result[0].contains("Everything is Ok")){
			isok=true;
		}else{
			isok=false;
		}
		return isok;
		
	}
	
	//del single file
	public static boolean delFile(String filePath){
		File file=new File(filePath);
		if(file.exists()){
			file.delete();
		}
		return !file.exists();
	}
	
	// ɾ���ļ���
	// param folderPath �ļ�����������·��
	public static void delFolder(String folderPath) {
	   try {
	    delAllFile(folderPath); // ɾ����������������
	    String filePath = folderPath;
	    filePath = filePath.toString();
	    java.io.File myFilePath = new java.io.File(filePath);
	    myFilePath.delete(); // ɾ�����ļ���
	   } catch (Exception e) {
	    com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
	   }
	}

	// ɾ��ָ���ļ����������ļ�
	// param path �ļ�����������·��
	public static boolean delAllFile(String path) {
	   boolean flag = false;
	   File file = new File(path);
	   if (!file.exists()) {
	    return flag;
	   }
	   if (!file.isDirectory()) {
	    return flag;
	   }
	   String[] tempList = file.list();
	   File temp = null;
	   for (int i = 0; i < tempList.length; i++) {
	    if (path.endsWith(File.separator)) {
	     temp = new File(path + tempList[i]);
	    } else {
	     temp = new File(path + File.separator + tempList[i]);
	    }
	    if (temp.isFile()) {
	     temp.delete();
	    }
	    if (temp.isDirectory()) {
	     delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
	     delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
	     flag = true;
	    }
	   }
	   return flag;
	}
	/** 
	* ���Ƶ����ļ� 
	* @param oldPath String ԭ�ļ�·�� �磺c:/fqf.txt 
	* @param newPath String ���ƺ�·�� �磺f:/fqf.txt 
	* @return boolean 
	*/ 
	public static boolean copyFile(String oldPath, String newPath) { 
	try { 
	int bytesum = 0; 
	int byteread = 0; 
	File oldfile = new File(oldPath); 
	if (oldfile.exists()) { //�ļ�����ʱ 
	InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ� 
	FileOutputStream fs = new FileOutputStream(newPath); 
	byte[] buffer = new byte[1444]; 
	int length; 
	while ( (byteread = inStream.read(buffer)) != -1) { 
	bytesum += byteread; //�ֽ��� �ļ���С 
	//com.Main.ThenToolsRun.logger.log(Level.INFO,bytesum); 
	fs.write(buffer, 0, byteread); 
	}
	if(inStream!=null){
	inStream.close(); 
	}
	if(fs!=null){
	fs.close();
	}
	} 
	} 
	catch (Exception e) { 
	com.Main.ThenToolsRun.logger.log(Level.INFO,"���Ƶ����ļ���������"); 
	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e); 

	} 
		return new File(newPath).exists();
	} 

	/** 
	* ���������ļ������� 
	* @param oldPath String ԭ�ļ�·�� �磺c:/fqf 
	* @param newPath String ���ƺ�·�� �磺f:/fqf/ff 
	* @return boolean 
	*/ 
	public static void copyFolder(String oldPath, String newPath) { 

	try { 
	(new File(newPath)).mkdirs(); //����ļ��в����� �������ļ��� 
	File a=new File(oldPath); 
	String[] file=a.list(); 
	File temp=null; 
	for (int i = 0; i < file.length; i++) { 
	if(oldPath.endsWith(File.separator)){ 
	temp=new File(oldPath+file[i]); 
	} 
	else{ 
	temp=new File(oldPath+File.separator+file[i]); 
	} 

	if(temp.isFile()){ 
	FileInputStream input = new FileInputStream(temp); 
	FileOutputStream output = new FileOutputStream(newPath + "/" + 
	(temp.getName()).toString()); 
	byte[] b = new byte[1024 * 5]; 
	int len; 
	while ( (len = input.read(b)) != -1) { 
	output.write(b, 0, len); 
	} 
	output.flush(); 
	output.close(); 
	input.close(); 
	} 
	if(temp.isDirectory()){//��������ļ��� 
	copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
	} 
	} 
	} 
	catch (Exception e) { 
	com.Main.ThenToolsRun.logger.log(Level.INFO,"���������ļ������ݲ�������"); 
	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e); 

	} 
	}
	//rename file
	public static boolean renameFile(String filePath){
		File file=new File(filePath);
		File newfile=new File(filePath.substring(0, filePath.length()-7));
		if(file.exists()){
			file.renameTo(newfile);
		}
		return newfile.exists();
	}
	//write all string to a file
	public static void writeAlltoFile(String filepath,String content,boolean isappend){
        FileOutputStream fileOutputStream;
		try {
			String[] spiltstr=content.split("\n");
			byte[] initline;
			fileOutputStream = new FileOutputStream(filepath,isappend);
            for(String line:spiltstr){
            	line=line+"\r\n";
                initline = line.getBytes("UTF-8");
                fileOutputStream.write(initline);
            }
            fileOutputStream.close();
            fileOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e); 
		}
}
	
	//write line string to a file
	public static void writetoFile(String filepath,String content,boolean isappend){
            FileOutputStream fileOutputStream;
			try {
				fileOutputStream = new FileOutputStream(filepath,isappend);
	            content=content+"\r\n";
	            byte[] initContent = content.getBytes("UTF-8");
	            fileOutputStream.write(initContent);
	            fileOutputStream.close();
	            fileOutputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e); 
			}
	}
	//read all string 
    public static StringBuffer readAllfromfile(String filepath) {  
        File file = new File(filepath);  
        BufferedReader reader = null;  
        StringBuffer strbuf=new StringBuffer();
        try {  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            while ((tempString = reader.readLine()) != null) {
            //	tempString=tempString.replaceAll("\r", "");
                strbuf.append(tempString+"\n");
            }  
            reader.close();  
        } catch (IOException e) {  
        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e); 
        } finally {  
            if (reader != null) {  
                    try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e); 
					}
            } 
        }  
        return strbuf;
    } 
	 /** 
     * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ� 
     */  
    public static void readFileByLines(String fileName) {  
        File file = new File(fileName);  
        BufferedReader reader = null;  
        try {  
           // System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 1;  
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����  
            while ((tempString = reader.readLine()) != null) {  
                // ��ʾ�к�  
                System.out.println("line " + line + ": " + tempString);  
                line++;  
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    } 
	
}
