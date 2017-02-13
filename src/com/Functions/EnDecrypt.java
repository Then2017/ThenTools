package com.Functions;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.JOptionPane;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

public class EnDecrypt {
SecretKey newstr2=getstr2fromfile();
SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMdd");
int strday=0;
boolean isok=false;
boolean isvip=false;
String validday;
Pattern pattern = Pattern.compile("20[0-9]{6},.*,.*,([0-9A-Z]{12}|ThenTools),[0-9a-z]");//20160506,3,user,D4BED9991F00


public String encryptToMD5(String info) {
byte[] digesta = null;
try {
// �õ�һ��md5����ϢժҪ
MessageDigest alga = MessageDigest.getInstance("MD5");
// ���Ҫ���м���ժҪ����Ϣ
alga.update(info.getBytes());
// �õ���ժҪ
digesta = alga.digest();

} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
}
// ��ժҪתΪ�ַ���
String rs = byte2hex(digesta);
return rs;
}

public String encryptToSHA(String info) {
byte[] digesta = null;
try {
// �õ�һ��SHA-1����ϢժҪ
MessageDigest alga = MessageDigest.getInstance("SHA-1");
// ���Ҫ���м���ժҪ����Ϣ
alga.update(info.getBytes());
// �õ���ժҪ
digesta = alga.digest();
} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
}
// ��ժҪתΪ�ַ���
String rs = byte2hex(digesta);
return rs;
}

// //////////////////////////////////////////////////////////////////////////
public SecretKey createSecretKey(String algorithm) {
// ����KeyGenerator����
KeyGenerator keygen;
// ���� ��Կ����
SecretKey deskey = null;
try {
// ��������ָ���㷨��������Կ�� KeyGenerator ����
keygen = KeyGenerator.getInstance(algorithm);
// ����һ����Կ
deskey = keygen.generateKey();
} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
}
// �����ܳ�
return deskey;
}

public String encryptToDES(SecretKey key, String info) {
// ���� �����㷨,���� DES,DESede,Blowfish
String Algorithm = "DES/ECB/PKCS5Padding";//DES/ECB/PKCS5Padding
// ��������������� (RNG),(���Բ�д)
SecureRandom sr = new SecureRandom();
// ����Ҫ���ɵ�����
byte[] cipherByte = null;
try {
// �õ�����/������
Cipher c1 = Cipher.getInstance(Algorithm);
// ��ָ������Կ��ģʽ��ʼ��Cipher����
// ����:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
c1.init(Cipher.ENCRYPT_MODE, key, sr);
// ��Ҫ���ܵ����ݽ��б��봦��,
cipherByte = c1.doFinal(info.getBytes());
} catch (Exception e) {
e.printStackTrace();
}
// �������ĵ�ʮ��������ʽ
return byte2hex(cipherByte);
}

public String decryptByDES(SecretKey key, String sInfo) {
// ���� �����㷨,
String Algorithm = "DES/ECB/PKCS5Padding";//DES/ECB/NoPadding 
// ��������������� (RNG)
SecureRandom sr = new SecureRandom();
byte[] cipherByte = null;
try {
// �õ�����/������
Cipher c1 = Cipher.getInstance(Algorithm);
// ��ָ������Կ��ģʽ��ʼ��Cipher����
c1.init(Cipher.DECRYPT_MODE, key, sr);
// ��Ҫ���ܵ����ݽ��б��봦��
cipherByte = c1.doFinal(hex2byte(sInfo));
} catch (Exception e) {
e.printStackTrace();
}
return new String(cipherByte);
}
/**
 * ��������ת��Ϊ16�����ַ���
 */
public String byte2hex(byte[] b) {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
        stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
        if (stmp.length() == 1) {
            hs = hs + "0" + stmp;
        } else {
            hs = hs + stmp;
        }
    }
    return hs.toUpperCase();
}
/**
 * ʮ�������ַ���ת��Ϊ2����
 */
public byte[] hex2byte(String src) {  
    int l = src.length() / 2;  
    byte[] ret = new byte[l];  
    for (int i = 0; i < l; i++) {  
        ret[i] = (byte) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();  
    }  
    return ret;  
}  
//public byte[] hex2byte(String hex) {  
//    byte[] ret = new byte[8];  
//    byte[] tmp = hex.getBytes();  
//    for (int i = 0; i < 8; i++) {  
//        ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);  
//    }  
//    return ret;  
//}  
///** 
// * ������ASCII�ַ��ϳ�һ���ֽڣ� �磺"EF"--> 0xEF 
// *  
// * @param src0 
// *            byte 
// * @param src1 
// *            byte 
// * @return byte 
// */  
//public static byte uniteBytes(byte src0, byte src1) {  
//    byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))  
//            .byteValue();  
//    _b0 = (byte) (_b0 << 4);  
//    byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))  
//            .byteValue();  
//    byte ret = (byte) (_b0 ^ _b1);  
//    return ret;  
//}  

// /////////////////////////////////////////////////////////////////////////////
public void createPairKey() {
try {
// �����ض����㷨һ����Կ��������
KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
// ��������������� (RNG)
SecureRandom random = new SecureRandom();
// �������ô�������������
random.setSeed(1000);
// ʹ�ø��������Դ����Ĭ�ϵĲ������ϣ���ʼ��ȷ����Կ��С����Կ��������
keygen.initialize(512, random);// keygen.initialize(512);
// ������Կ��
KeyPair keys = keygen.generateKeyPair();
// �õ�����
PublicKey pubkey = keys.getPublic();
// �õ�˽��
PrivateKey prikey = keys.getPrivate();
// ������˽��д�뵽�ļ�����
doObjToFile(com.Main.ThenToolsRun.datalocation+"/mykeys.bat", new Object[] { prikey, pubkey });
} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
}
}

public void signToInfo(String info, String signfile) {
// ���ļ����ж�ȡ˽��
PrivateKey myprikey = (PrivateKey) getObjFromFile(com.Main.ThenToolsRun.datalocation+"/mykeys.bat", 1);
// ���ļ��ж�ȡ����
PublicKey mypubkey = (PublicKey) getObjFromFile(com.Main.ThenToolsRun.datalocation+"/mykeys.bat", 2);
try {
// Signature ������������ɺ���֤����ǩ��
Signature signet = Signature.getInstance("DSA");
// ��ʼ��ǩ��ǩ����˽Կ
signet.initSign(myprikey);
// ����Ҫ���ֽ�ǩ������֤������
signet.update(info.getBytes());
// ǩ�����֤���и����ֽڵ�ǩ��������ǩ��
byte[] signed = signet.sign();

// ������ǩ��,����,��Ϣ�����ļ���
doObjToFile(signfile, new Object[] { signed, mypubkey, info });
} catch (Exception e) {
e.printStackTrace();
}
}

public boolean validateSign(String signfile) {
// ��ȡ����
PublicKey mypubkey = (PublicKey) getObjFromFile(signfile, 2);
// ��ȡǩ��
byte[] signed = (byte[]) getObjFromFile(signfile, 1);
// ��ȡ��Ϣ
String info = (String) getObjFromFile(signfile, 3);
try {
// ��ʼһ��Signature����,���ù�Կ��ǩ��������֤
Signature signetcheck = Signature.getInstance("DSA");
// ��ʼ����֤ǩ���Ĺ�Կ
signetcheck.initVerify(mypubkey);
// ʹ��ָ���� byte �������Ҫǩ������֤������
signetcheck.update(info.getBytes());
// ��֤�����ǩ��
return signetcheck.verify(signed);
} catch (Exception e) {
e.printStackTrace();
return false;
}
}

public void doObjToFile(String file, Object[] objs) {
ObjectOutputStream oos = null;
try {
FileOutputStream fos = new FileOutputStream(file);
oos = new ObjectOutputStream(fos);
for (int i = 0; i < objs.length; i++) {
oos.writeObject(objs[i]);

}
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
oos.flush();
oos.close();
} catch (IOException e) {
e.printStackTrace();
}
}
}

public Object getObjFromFile(String file, int i) {
ObjectInputStream ois = null;
Object obj = null;
try {
FileInputStream fis = new FileInputStream(file);
ois = new ObjectInputStream(fis);
for (int j = 0; j < i; j++) {
obj = ois.readObject();
}
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
ois.close();
} catch (IOException e) {
e.printStackTrace();
}
}
return obj;
}
////////////////////////////////////////////////////////////////
//createkey file
public void createstr2file(SecretKey key){
	// ��ȡ��Կ����
    byte rawKeyData[] = key.getEncoded();
    com.Main.ThenToolsRun.dbhandle.setSingleLineValue("Code", "KeyCode", new String(rawKeyData));
}
public void createstr2file2(SecretKey key){
	// ��ȡ��Կ����
    byte rawKeyData[] = key.getEncoded();
    // ����ȡ����Կ���ݱ��浽�ļ��У�������ʱʹ��
    FileOutputStream fo;
	try {
		fo = new FileOutputStream(new File(com.Main.ThenToolsRun.datalocation+"/Str2"));
	    fo.write(rawKeyData);
	    if(fo!=null){
		    fo.close();
	    }
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

//get key from file
public SecretKey getstr2fromfile(){
    SecretKey key=null;
    String rawKeyData=com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Code", "KeyCode");
	try {
	    // ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����
		DESKeySpec dks = new DESKeySpec(rawKeyData.getBytes());
	    // ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����һ�� SecretKey����
	    key = SecretKeyFactory.getInstance("DES").generateSecret(dks);
	} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return key;
}
public SecretKey getstr2fromfile2(){
	// DES�㷨Ҫ����һ�������ε������Դ
  //  SecureRandom sr = new SecureRandom();
    SecretKey key=null;
	// ����ܳ�����
    FileInputStream fi;
	try {
		fi = new FileInputStream(new File(com.Main.ThenToolsRun.datalocation+"/Str2"));
	    byte rawKeyData[] = new byte[fi.available()];// = new byte[5];
	    fi.read(rawKeyData);
	    if(fi!=null){
	    fi.close();
	    }
	    // ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����
	    DESKeySpec dks = new DESKeySpec(rawKeyData);
	    // ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����һ�� SecretKey����
	    key = SecretKeyFactory.getInstance("DES").generateSecret(dks);
//	    // Cipher����ʵ����ɽ��ܲ���
//	    Cipher cipher = Cipher.getInstance("DES");
//	    // ���ܳ׳�ʼ��Cipher����
//	    cipher.init(Cipher.DECRYPT_MODE, key, sr);
	} catch (IOException | InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return key;
}
//encrypt
public String encryptToDES(String str){
	String encryptstr = encryptToDES(newstr2, str);
	return encryptstr;
}
//decrypt
public String decryptByDES(String str){
	String encryptstr = decryptByDES(newstr2, str);
	return encryptstr;
}
//get lisence
public String getstr(){
	return decryptByDES(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Code", "CurrentCode"));
}
public String getstr2(){
	 FileInputStream fi;
	 String str="";
		try {
			fi = new FileInputStream(new File(com.Main.ThenToolsRun.datalocation+"/Str"));
		    byte rawKeyData[] = new byte[fi.available()];// = new byte[5];
		    fi.read(rawKeyData);
		    str=new String(rawKeyData);
		    str=decryptByDES(str);//....!
		    if(fi!=null){
		    fi.close();
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return str;
}

//get left day
public int getstrday(String str,String str1){
	int day=0;
	try {  
		day = (int) ((new Date().getTime()-sdformat.parse(str).getTime())/(24*60*60*1000));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	strday=Integer.parseInt(str1)-day;
	return strday;
}
////get time from net
//public void gettimefromnet(final String str1){
//	new Thread(new Runnable() {
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//	  try {  
//		    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");  
//	        NTPUDPClient timeClient = new NTPUDPClient();  
//	        String timeServerUrl = "time.nist.gov";// 210.72.145.44
//	        InetAddress timeServerAddress = InetAddress.getByName(timeServerUrl);  
//	        TimeInfo timeInfo;  
//	        TimeStamp timeStamp;
//	        int day=0;
//	        while(true){
//	        	timeInfo = timeClient.getTime(timeServerAddress);  
//	        	 timeStamp = timeInfo.getMessage().getTransmitTimeStamp();  
//	        	 day = (int) ((new Date().getTime()-timeStamp.getTime())/(24*60*60*1000));
//	        	 System.out.println("TTTT"+dateFormat.format(timeStamp.getDate()));
//	        	 if(Integer.parseInt(str1)-day<=0){
//	        		System.out.println("GGG"+dateFormat.format(timeStamp.getDate()));
//	        		System.exit(0);
//	        	 }
//	 	        try {
//					Thread.sleep(60000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	        }
//	    } catch (UnknownHostException e) {  
//	        e.printStackTrace();  
//	    } catch (IOException e) {  
//	        e.printStackTrace();  
//	    }  
//		}
//	}).start();
//}
public int getstrday(){
	return strday;
}
public boolean isok(){
	isok=checkstr();
	return isok;
}
public boolean isvip(){
	return isvip;
}
public String getvalidday(){
	return validday;
}
//get current lisece
public String getcurrentstr(){
		return com.Main.ThenToolsRun.dbhandle.getSingleLineValue("Code", "CurrentCode");
}
public String getcurrentstr2(){
	 FileInputStream fi;
	 String str="";
		try {
			fi = new FileInputStream(new File(com.Main.ThenToolsRun.datalocation+"/Str"));
		    byte rawKeyData[] = new byte[fi.available()];// = new byte[5];
		    fi.read(rawKeyData);
		    str=new String(rawKeyData);
		    if(fi!=null){
		    fi.close();
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
}
//check lisence
public boolean checkstr(){
	//new key
//	SecretKey key = createSecretKey("DES");
//	createstr2file(key);
//	System.out.println("pass");
//	encrypt: 4EF52AB6DEAFB08C9A43169C3C8D2D204D3C433C768953D2EFD7E148048106C8  user
//	encrypt: 1E93CF582F5C0BC69A43169C3C8D2D204D3C433C768953D2E0EAEFB47112624A
//	String encryptstr=encryptToDES("ThenTools");//0/4/8/C
//	String encryptstr1=encryptToDES("20160620,25,vip,ThenTools,1");
//	System.out.println("encrypt: " + encryptstr);
//	System.out.println("encrypt: " + encryptstr1);
	String decryptstr = getstr();
	String[] str=decryptstr.split(",");
	validday=str[1];
	if(!str[4].equals("2")){//version flag
		return false;
	}
	if(str[3].equals("ThenTools")){
		
	}else if(!str[3].equals(CheckPC.getMACAddress())){
		return false;
	}
	if(str[2].equals("vip")){
		isvip=true;
	}
	if(getstrday(str[0],str[1])<=0){
		return false;
	}
	return true;
}

//update lisence
public boolean updatestr(String newstr){
	String decrypt=decryptByDES(newstr);
    Matcher matcher = pattern.matcher(decrypt);
    if(!matcher.matches()){
    	return false;
    }
	com.Main.ThenToolsRun.dbhandle.setSingleLineValue("Code", "CurrentCode", newstr);
	return true;
}
public boolean updatestr2(String newstr){
	String decrypt=decryptByDES(newstr);
    Matcher matcher = pattern.matcher(decrypt);
    if(!matcher.matches()){
    	return false;
    }
    byte Data[] = newstr.getBytes();
    FileOutputStream fo;
	try {
		fo = new FileOutputStream(new File(com.Main.ThenToolsRun.datalocation+"/Str"),false);
	    fo.write(Data);
	    if(fo!=null){
		    fo.close();
	    }
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return true;
}

//create lisence
public String createstr(String str){
	//ThenTools 76B16A237EA33875B0A1E16FCF51DE69
	String[] strsplit=str.split(",");
	String mac=decryptByDES(strsplit[3]);
	String newstr=strsplit[0]+","+strsplit[1]+","+strsplit[2]+","+mac+","+strsplit[4];
	newstr=encryptToDES(newstr);
	return newstr;
}

//public void test() {
//CryptLisence jiami = new CryptLisence();
//// ִ��MD5����"Hello world!"
//System.out.println("����MD5:" + jiami.encryptToMD5("Hello"));
//// ����һ��DES�㷨���ܳ�
//SecretKey key = jiami.createSecretKey("DES");
//// ���ܳ׼�����Ϣ"Hello world!"
//String str1 = jiami.encryptToDES(key, "Hello");
//System.out.println("ʹ��des������ϢΪ:" + str1);
//// ʹ������ܳ׽���
//String str2 = jiami.decryptByDES(key, str1);
//System.out.println("���ܺ�Ϊ��" + str2);
//
//
//// �������׺�˽��
//jiami.createPairKey();
//// ��Hello world!ʹ��˽�׽���ǩ��
//jiami.signToInfo("Hello", com.Main.ThenToolsRun.datalocation+"/mysign.bat");
//// ���ù��׶�ǩ��������֤��
//if (jiami.validateSign(com.Main.ThenToolsRun.datalocation+"/mysign.bat")) {
//System.out.println("Success!");
//} else {
//System.out.println("Fail!");
//}
//
////System.out.println(0Xff^0xf1);
////System.out.println(0xf1);
//}
}

