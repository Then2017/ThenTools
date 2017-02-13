package com.Functions;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JLabel;

public class CheckPC {
	JLabel lblPCInfo;
	
	
	public void run(){
		lblPCInfo=com.Main.ThenToolsRun.mainFrame.getPCInfo();
		String text=getString("lblPCInfo");
		lblPCInfo.setText("<html>"+text+"</html>");
	}
	
	//adb
	public String checkadb(){
		String returnstr="no adb";
		List<String> result=Excute.returnlist("version", 3, true);
		for(String str:result){
			if(str.contains("Android Debug Bridge version")){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"adb version is "+str.substring(str.length()-7, str.length()));
				return str.substring(str.length()-6, str.length());
			}
		}
		return returnstr;
	}
	
	public String check7z(){
		String returnstr="no 7z";
		List<String> result=Excute.returnlist(com.Main.ThenToolsRun.extraBinlocation+"/7za.exe "+"version",1, true);
		for(String str:result){
			if(str.contains("7-Zip")){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"7z version is "+str.substring(15,20));
				return str.substring(15, 20);
			}
		}
		return returnstr;
	}
	
    //获取MAC地址的方法
    public static String getMACAddress(){
    	InetAddress ia;
	   	StringBuffer sb = new StringBuffer(); ;
		try {
			//获取本地IP对象
			ia = InetAddress.getLocalHost();
	        //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
	        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
	        //下面代码是把mac地址拼装成String

	        for(int i=0;i<mac.length;i++){
//	            if(i!=0){
//	                sb.append("-");
//	            }
	            //mac[i] & 0xFF 是为了把byte转化为正整数
	            String s = Integer.toHexString(mac[i] & 0xFF);
	            sb.append(s.length()==1?0+s:s);
	        }
		} catch (UnknownHostException | SocketException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
	//	com.Main.ThenToolsRun.logger.log(Level.WARNING,"MAC address: "+sb.toString().toUpperCase());
        //把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblPCInfo": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "adb 版本: "+checkadb()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "7z 版本: "+check7z()+"<br>";
		}else{
			return "adb version: "+checkadb()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "7z version: "+check7z()+"<br>";
		}
			default: return "";
		}	
	}
    
}
