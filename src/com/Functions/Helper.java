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
	
	//等待手机
	public static void adbwaitfordevices(){
		Excute.execcmd("wait-for-device", 3, true);
		
		
	}
	//是否含有中文
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
	 
	//判断是否数字
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	//判断是否数字
	public static boolean isDecimal(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*.[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	//7z压缩
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
	
	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
	   try {
	    delAllFile(folderPath); // 删除完里面所有内容
	    String filePath = folderPath;
	    filePath = filePath.toString();
	    java.io.File myFilePath = new java.io.File(filePath);
	    myFilePath.delete(); // 删除空文件夹
	   } catch (Exception e) {
	    com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
	   }
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
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
	     delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
	     delFolder(path + "/" + tempList[i]);// 再删除空文件夹
	     flag = true;
	    }
	   }
	   return flag;
	}
	/** 
	* 复制单个文件 
	* @param oldPath String 原文件路径 如：c:/fqf.txt 
	* @param newPath String 复制后路径 如：f:/fqf.txt 
	* @return boolean 
	*/ 
	public static boolean copyFile(String oldPath, String newPath) { 
	try { 
	int bytesum = 0; 
	int byteread = 0; 
	File oldfile = new File(oldPath); 
	if (oldfile.exists()) { //文件存在时 
	InputStream inStream = new FileInputStream(oldPath); //读入原文件 
	FileOutputStream fs = new FileOutputStream(newPath); 
	byte[] buffer = new byte[1444]; 
	int length; 
	while ( (byteread = inStream.read(buffer)) != -1) { 
	bytesum += byteread; //字节数 文件大小 
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
	com.Main.ThenToolsRun.logger.log(Level.INFO,"复制单个文件操作出错"); 
	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e); 

	} 
		return new File(newPath).exists();
	} 

	/** 
	* 复制整个文件夹内容 
	* @param oldPath String 原文件路径 如：c:/fqf 
	* @param newPath String 复制后路径 如：f:/fqf/ff 
	* @return boolean 
	*/ 
	public static void copyFolder(String oldPath, String newPath) { 

	try { 
	(new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
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
	if(temp.isDirectory()){//如果是子文件夹 
	copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
	} 
	} 
	} 
	catch (Exception e) { 
	com.Main.ThenToolsRun.logger.log(Level.INFO,"复制整个文件夹内容操作出错"); 
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
     * 以行为单位读取文件，常用于读面向行的格式化文件 
     */  
    public static void readFileByLines(String fileName) {  
        File file = new File(fileName);  
        BufferedReader reader = null;  
        try {  
           // System.out.println("以行为单位读取文件内容，一次读一整行：");  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 1;  
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                // 显示行号  
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
