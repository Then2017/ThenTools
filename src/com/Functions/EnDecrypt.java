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
// 得到一个md5的消息摘要
MessageDigest alga = MessageDigest.getInstance("MD5");
// 添加要进行计算摘要的信息
alga.update(info.getBytes());
// 得到该摘要
digesta = alga.digest();

} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
}
// 将摘要转为字符串
String rs = byte2hex(digesta);
return rs;
}

public String encryptToSHA(String info) {
byte[] digesta = null;
try {
// 得到一个SHA-1的消息摘要
MessageDigest alga = MessageDigest.getInstance("SHA-1");
// 添加要进行计算摘要的信息
alga.update(info.getBytes());
// 得到该摘要
digesta = alga.digest();
} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
}
// 将摘要转为字符串
String rs = byte2hex(digesta);
return rs;
}

// //////////////////////////////////////////////////////////////////////////
public SecretKey createSecretKey(String algorithm) {
// 声明KeyGenerator对象
KeyGenerator keygen;
// 声明 密钥对象
SecretKey deskey = null;
try {
// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
keygen = KeyGenerator.getInstance(algorithm);
// 生成一个密钥
deskey = keygen.generateKey();
} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
}
// 返回密匙
return deskey;
}

public String encryptToDES(SecretKey key, String info) {
// 定义 加密算法,可用 DES,DESede,Blowfish
String Algorithm = "DES/ECB/PKCS5Padding";//DES/ECB/PKCS5Padding
// 加密随机数生成器 (RNG),(可以不写)
SecureRandom sr = new SecureRandom();
// 定义要生成的密文
byte[] cipherByte = null;
try {
// 得到加密/解密器
Cipher c1 = Cipher.getInstance(Algorithm);
// 用指定的密钥和模式初始化Cipher对象
// 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
c1.init(Cipher.ENCRYPT_MODE, key, sr);
// 对要加密的内容进行编码处理,
cipherByte = c1.doFinal(info.getBytes());
} catch (Exception e) {
e.printStackTrace();
}
// 返回密文的十六进制形式
return byte2hex(cipherByte);
}

public String decryptByDES(SecretKey key, String sInfo) {
// 定义 加密算法,
String Algorithm = "DES/ECB/PKCS5Padding";//DES/ECB/NoPadding 
// 加密随机数生成器 (RNG)
SecureRandom sr = new SecureRandom();
byte[] cipherByte = null;
try {
// 得到加密/解密器
Cipher c1 = Cipher.getInstance(Algorithm);
// 用指定的密钥和模式初始化Cipher对象
c1.init(Cipher.DECRYPT_MODE, key, sr);
// 对要解密的内容进行编码处理
cipherByte = c1.doFinal(hex2byte(sInfo));
} catch (Exception e) {
e.printStackTrace();
}
return new String(cipherByte);
}
/**
 * 将二进制转化为16进制字符串
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
 * 十六进制字符串转化为2进制
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
// * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF 
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
// 根据特定的算法一个密钥对生成器
KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
// 加密随机数生成器 (RNG)
SecureRandom random = new SecureRandom();
// 重新设置此随机对象的种子
random.setSeed(1000);
// 使用给定的随机源（和默认的参数集合）初始化确定密钥大小的密钥对生成器
keygen.initialize(512, random);// keygen.initialize(512);
// 生成密钥组
KeyPair keys = keygen.generateKeyPair();
// 得到公匙
PublicKey pubkey = keys.getPublic();
// 得到私匙
PrivateKey prikey = keys.getPrivate();
// 将公匙私匙写入到文件当中
doObjToFile(com.Main.ThenToolsRun.datalocation+"/mykeys.bat", new Object[] { prikey, pubkey });
} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
}
}

public void signToInfo(String info, String signfile) {
// 从文件当中读取私匙
PrivateKey myprikey = (PrivateKey) getObjFromFile(com.Main.ThenToolsRun.datalocation+"/mykeys.bat", 1);
// 从文件中读取公匙
PublicKey mypubkey = (PublicKey) getObjFromFile(com.Main.ThenToolsRun.datalocation+"/mykeys.bat", 2);
try {
// Signature 对象可用来生成和验证数字签名
Signature signet = Signature.getInstance("DSA");
// 初始化签署签名的私钥
signet.initSign(myprikey);
// 更新要由字节签名或验证的数据
signet.update(info.getBytes());
// 签署或验证所有更新字节的签名，返回签名
byte[] signed = signet.sign();

// 将数字签名,公匙,信息放入文件中
doObjToFile(signfile, new Object[] { signed, mypubkey, info });
} catch (Exception e) {
e.printStackTrace();
}
}

public boolean validateSign(String signfile) {
// 读取公匙
PublicKey mypubkey = (PublicKey) getObjFromFile(signfile, 2);
// 读取签名
byte[] signed = (byte[]) getObjFromFile(signfile, 1);
// 读取信息
String info = (String) getObjFromFile(signfile, 3);
try {
// 初始一个Signature对象,并用公钥和签名进行验证
Signature signetcheck = Signature.getInstance("DSA");
// 初始化验证签名的公钥
signetcheck.initVerify(mypubkey);
// 使用指定的 byte 数组更新要签名或验证的数据
signetcheck.update(info.getBytes());
// 验证传入的签名
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
	// 获取密钥数据
    byte rawKeyData[] = key.getEncoded();
    com.Main.ThenToolsRun.dbhandle.setSingleLineValue("Code", "KeyCode", new String(rawKeyData));
}
public void createstr2file2(SecretKey key){
	// 获取密钥数据
    byte rawKeyData[] = key.getEncoded();
    // 将获取到密钥数据保存到文件中，待解密时使用
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
	    // 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(rawKeyData.getBytes());
	    // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个 SecretKey对象
	    key = SecretKeyFactory.getInstance("DES").generateSecret(dks);
	} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return key;
}
public SecretKey getstr2fromfile2(){
	// DES算法要求有一个可信任的随机数源
  //  SecureRandom sr = new SecureRandom();
    SecretKey key=null;
	// 获得密匙数据
    FileInputStream fi;
	try {
		fi = new FileInputStream(new File(com.Main.ThenToolsRun.datalocation+"/Str2"));
	    byte rawKeyData[] = new byte[fi.available()];// = new byte[5];
	    fi.read(rawKeyData);
	    if(fi!=null){
	    fi.close();
	    }
	    // 从原始密匙数据创建一个DESKeySpec对象
	    DESKeySpec dks = new DESKeySpec(rawKeyData);
	    // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个 SecretKey对象
	    key = SecretKeyFactory.getInstance("DES").generateSecret(dks);
//	    // Cipher对象实际完成解密操作
//	    Cipher cipher = Cipher.getInstance("DES");
//	    // 用密匙初始化Cipher对象
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
//// 执行MD5加密"Hello world!"
//System.out.println("经过MD5:" + jiami.encryptToMD5("Hello"));
//// 生成一个DES算法的密匙
//SecretKey key = jiami.createSecretKey("DES");
//// 用密匙加密信息"Hello world!"
//String str1 = jiami.encryptToDES(key, "Hello");
//System.out.println("使用des加密信息为:" + str1);
//// 使用这个密匙解密
//String str2 = jiami.decryptByDES(key, str1);
//System.out.println("解密后为：" + str2);
//
//
//// 创建公匙和私匙
//jiami.createPairKey();
//// 对Hello world!使用私匙进行签名
//jiami.signToInfo("Hello", com.Main.ThenToolsRun.datalocation+"/mysign.bat");
//// 利用公匙对签名进行验证。
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

