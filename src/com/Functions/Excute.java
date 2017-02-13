package com.Functions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;


public class Excute {
	//cmd4 just execute
	public static void execcmd4(String command){
		try {
			if(com.Main.ThenToolsRun.getdevices.getDevice()!=null){
				com.Main.ThenToolsRun.getdevices.getDevice().executeShellCommand(command, new MultiLineReceiver(){
					@Override
					public boolean isCancelled() {
						// TODO Auto-generated method stub
						return false;
					}
					@Override
					public void processNewLines(String[] arg0) {
						// TODO Auto-generated method stub
				        for(String line:arg0) { //将输出的数据缓存起来
				        	System.out.println(line);
				        }
					}
					
				});
			}
		} catch (TimeoutException | AdbCommandRejectedException
				| ShellCommandUnresponsiveException | IOException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
	  //  com.Main.ThenToolsRun.logger.log(Level.INFO, output.toString());
	}
	//*********************************************************************************************************************
	 //cmd3 get info check UE only always run
	static StringBuilder execcmd3output = new StringBuilder("");
	public static String execcmd3(String command){    
		try {
			execcmd3output.setLength(0);
			if(com.Main.ThenToolsRun.getdevices.getDevice()!=null){
				com.Main.ThenToolsRun.getdevices.getDevice().executeShellCommand(command, new MultiLineReceiver(){

					@Override
					public boolean isCancelled() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void processNewLines(String[] arg0) {
						// TODO Auto-generated method stub
				        for(String line:arg0) { //将输出的数据缓存起来
				        	if(!line.startsWith("* daemon")||!line.startsWith("adb server is out of")){
				        		execcmd3output.append(line).append("\n");
				        	}
				        }
					}
					
				});
			}
		} catch (TimeoutException | AdbCommandRejectedException
				| ShellCommandUnresponsiveException | IOException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
	  //  com.Main.ThenToolsRun.logger.log(Level.INFO, output.toString());
		return execcmd3output.toString();
	}

   public static List returnlist3(String command) {
   	List<String> list = new ArrayList<String>();
   	String str=execcmd3(command);
   	String str1[]=str.split("\n");
   	for(int i=0;i<str1.length;i++){
   		if(!str1[i].equals("")){
       		list.add(str1[i]);
   		}
   	}
       return list;
   }
	//*********************************************************************************************************************
	 //cmd2 get info
	public static String execcmd2(String command){    
		final StringBuilder output = new StringBuilder("nodevice");
		try {
			if(com.Main.ThenToolsRun.getdevices.getDevice()!=null){
				output.setLength(0);
				com.Main.ThenToolsRun.getdevices.getDevice().executeShellCommand(command, new MultiLineReceiver(){

					@Override
					public boolean isCancelled() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void processNewLines(String[] arg0) {
						// TODO Auto-generated method stub
				        for(String line:arg0) { //将输出的数据缓存起来
				        	if(!line.startsWith("* daemon")||!line.startsWith("adb server is out of")){
							    output.append(line).append("\n");
				        	}
				        	//System.out.println(line);
				        }
					}
					
				});
			}
		} catch (TimeoutException | AdbCommandRejectedException
				| ShellCommandUnresponsiveException | IOException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
	  //  com.Main.ThenToolsRun.logger.log(Level.INFO, output.toString());
		return output.toString();
	}

    public static List returnlist2(String command) {
    	List<String> list = new ArrayList<String>();
    	String str=execcmd2(command);
    	String str1[]=str.split("\n");
    	for(int i=0;i<str1.length;i++){
    		if(!str1[i].equals("")){
        		list.add(str1[i]);
    		}
    	}
        return list;
    }
	
    //**************************************************************************************************************
	
    public static List returnlist(String command,int which,boolean wait) {
    	List<String> list = new ArrayList<String>();
    	String str[]=execcmd(command,which,wait);
    	String str1[]=str[0].toString().split("\n|\r");
    	for(String str3:str1){
    	//	com.Main.ThenToolsRun.logger.log(Level.INFO,str3);
    		list.add(str3);
    	}
        return list;
    }
    
//    public static boolean isroot()  {
//    	boolean isroot=false;
//        String str[]=execcmd("cd /data&&ls",2,true);
//        if(str[0].contains("denied")||str[1].contains("not found")){
//        	isroot=false;
//        }else{
//        	isroot=true;
//        }
//        return isroot;
//    }
    
    public static String[] execcmd(String commands,int which,boolean wait)
	{
		String output[] = {"", ""};
		try {
			Process p;
			if(which==1){
			 p = Runtime.getRuntime().exec("cmd /c "+commands);
			}else if(which==2){
			 p = Runtime.getRuntime().exec(com.Main.ThenToolsRun.extraBinlocation+"/adb.exe -s \""+com.Main.ThenToolsRun.selectedID+"\" shell \""+commands+"\"");
			}else if(which==3) {
			 p = Runtime.getRuntime().exec("cmd /c "+com.Main.ThenToolsRun.extraBinlocation+"/adb.exe -s \""+com.Main.ThenToolsRun.selectedID+"\" "+commands);
			}else {
				com.Main.ThenToolsRun.logger.log(Level.INFO,"execcmd error which");
				return output;
			}
			StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
			StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
			new Thread(errorStream).start();
			new Thread(outputStream).start();
			if(wait){
			p.waitFor();
			}
			String outputString = outputStream.output.toString();
			String errorString  = errorStream.output.toString();
			output[0] = outputString;
			output[1] = errorString;
			//com.Main.ThenToolsRun.logger.log(Level.INFO,output[0]);
			//com.Main.ThenToolsRun.logger.log(Level.INFO,output[1]);
		}  catch (InterruptedException |IOException e) {
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
		return output;
	}
    
}

class StreamCaptureThread implements Runnable {
	InputStream stream;
	StringBuilder output;
	BufferedReader br;
	public StreamCaptureThread(InputStream stream) {
		this.stream = stream;
		this.output = new StringBuilder();
	}

	public void run() {
		try {
			try {
			    br = new BufferedReader(	new InputStreamReader(this.stream));
				String line = br.readLine();
				while (line != null) {
					if (line.trim().length() > 0) {
						output.append(line).append("\n");
					}
					line = br.readLine();
				}
			} finally {
				if (stream != null) {
					stream.close();
				}
				if(br!=null){
					br.close();
				}
			}
		} catch (IOException e) {
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
	}
	
//	public static void execcmd(String command){
//	try {
//           final Process process = Runtime.getRuntime().exec("cmd /c "+command);  
//           final BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
//           final BufferedReader  errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//           new Thread(new Runnable() {
//               String line;
//                public void run() {
//                    try {
//                        while((line=inputStream.readLine()) != null) {
//            				if (line.equals("")){continue;}
//                           com.Main.ThenToolsRun.logger.log(Level.INFO,line);
//                       }
//                        while((line=errorReader.readLine()) != null) {
//                        	if (line.equals("")){continue;}
//	                           com.Main.ThenToolsRun.logger.log(Level.INFO,"error : "+line);
//	                       }
//                        process.waitFor();
//                    } catch (IOException | InterruptedException e) {
//                       //com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);  
//                    } finally {
//                        if (process!=null){
//                        	process.destroy();
//                        }
//                        if (inputStream!=null){
//                        	try {
//								inputStream.close();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//							}
//                        }
//                        if (errorReader!=null){
//                        	try {
//                        		errorReader.close();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//							}
//                        }
//                    }
//            	//	com.Main.ThenToolsRun.logger.log(Level.INFO,"execcmd end");
//                }
//           }).start();
////           int i = process.waitFor();
////           com.Main.ThenToolsRun.logger.log(Level.INFO,"i=" + i);
//       } catch (Exception e) {
//           com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//       }
//	}
//
//public static void execadbshell (String command){
//	try {
//        final Process process = Runtime.getRuntime().exec("adb shell "+"\""+command+"\"");  //adb shell
//        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        final BufferedReader  errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//           new Thread(new Runnable() {
//               String line;
//                public void run() {
//                 //  com.Main.ThenToolsRun.logger.log(Level.INFO,"listener started");
//                    try {
//	                       while((line=errorReader.readLine()) != null) {
//	                    	   if (line.equals("")){continue;}
//		                       com.Main.ThenToolsRun.logger.log(Level.INFO,"error : "+line);
//		                   }
//                        while((line=inputStream.readLine()) != null) {
//                        	if (line.equals("")){continue;}
//                           com.Main.ThenToolsRun.logger.log(Level.INFO,line);
//                       }
//					process.waitFor();
//                    } catch (IOException | InterruptedException e) {
//                       //com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);  
//                    } finally {
//                        if (process!=null){
//                        	process.destroy();
//                        }
//                        if (inputStream!=null){
//                        	try {
//								inputStream.close();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//							}
//                        }
//                        if (errorReader!=null){
//                        	try {
//                        		errorReader.close();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//							}
//                        }
//                    }
//
//            	//	com.Main.ThenToolsRun.logger.log(Level.INFO,"execadbshell end");
//                }
//           }).start();
//       } catch (Exception e) {
//           com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//       }
//    }
}

