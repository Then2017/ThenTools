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
	
    //��ȡMAC��ַ�ķ���
    public static String getMACAddress(){
    	InetAddress ia;
	   	StringBuffer sb = new StringBuffer(); ;
		try {
			//��ȡ����IP����
			ia = InetAddress.getLocalHost();
	        //�������ӿڶ��󣨼������������õ�mac��ַ��mac��ַ������һ��byte�����С�
	        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
	        //��������ǰ�mac��ַƴװ��String

	        for(int i=0;i<mac.length;i++){
//	            if(i!=0){
//	                sb.append("-");
//	            }
	            //mac[i] & 0xFF ��Ϊ�˰�byteת��Ϊ������
	            String s = Integer.toHexString(mac[i] & 0xFF);
	            sb.append(s.length()==1?0+s:s);
	        }
		} catch (UnknownHostException | SocketException e) {
			// TODO Auto-generated catch block
			com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		}
	//	com.Main.ThenToolsRun.logger.log(Level.WARNING,"MAC address: "+sb.toString().toUpperCase());
        //���ַ�������Сд��ĸ��Ϊ��д��Ϊ�����mac��ַ������
        return sb.toString().toUpperCase();
    }
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblPCInfo": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "adb �汾: "+checkadb()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "7z �汾: "+check7z()+"<br>";
		}else{
			return "adb version: "+checkadb()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "7z version: "+check7z()+"<br>";
		}
			default: return "";
		}	
	}
    
}
