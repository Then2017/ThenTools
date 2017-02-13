package com.Monkey;

public class PackageInfo {
	
	
	//if gms app
	public boolean getGMS(String com){
		switch(com){
		case "com.google.android.gms":			return true;
		case "com.google.android.backuptransport":			return true;
		case "com.google.android.feedback":			return true;
		case "com.google.android.gsf.login":			return true;
		case "com.google.android.onetimeinitializer":  		 return true;
		case "com.google.android.partnersetup":			return true;
		case "com.google.android.gsf":			return true;
		case "com.android.vending":			return true;
		case "com.google.android.setupwizard":			return true;
		case "com.google.android.googlequicksearchbox":			return true;
		case "com.google.android.androidforwork":			return true;
		case "com.google.android.tag":			return true;
		case "com.google.android.maps":			return true;
		case "com.google.android.media.effects":			return true;
		case "com.google.android.apps.books":			return true;
		case "com.android.browser.provider":			return true;
		case "com.android.chrome":			return true;
		case "com.google.android.configupdater":			return true;
		case "com.google.android.apps.docs":			return true;
		case "com.google.android.gm":			return true;
		case "com.google.android.syncadapters.calendar":			return true;
		case "com.google.android.syncadapters.contacts":			return true;
		case "com.google.android.talk":			return true;
		case "com.google.android.apps.maps":			return true;
		case "com.google.android.music":			return true;
		case "com.google.android.apps.magazines":			return true;
		case "com.google.android.apps.photos":			return true;
		case "com.google.android.play.games":			return true;
		case "com.google.android.apps.plus":			return true;
		case "com.google.android.street":			return true;
		case "com.google.android.videos":			return true;
		case "com.google.android.webview":			return true;
		case "com.google.android.youtube":			return true;
		case "com.google.android.apps.messaging":			return true;
		case "com.google.android.calendar":			return true;
		case "com.google.android.apps.cloudprint":			return true;
		case "com.google.android.deskclock":			return true;
		case "com.google.android.apps.enterprise.dmagent":			return true;
		case "com.google.android.apps.docs.editors.docs":			return true;
		case "com.google.android.apps.docs.editors.sheets":			return true;
		case "com.google.android.apps.docs.editors.slides":			return true;
		case "com.android.facelock":			return true;
		case "com.google.android.GoogleCamera":			return true;
		case "com.google.android.ears":			return true;
		case "com.google.earth":			return true;
		case "com.google.android.apps.inputmethod.hindi":			return true;
		case "com.google.android.launcher":			return true;
		case "com.google.android.inputmethod.pinyin":			return true;
		case "com.google.android.tts":			return true;
		case "com.google.android.inputmethod.japanese":			return true;
		case "com.google.android.keep":			return true;
		case "com.google.android.inputmethod.korean":			return true;
		case "com.google.android.inputmethod.latin":			return true;
		case "com.google.android.apps.genie.geniewidget":			return true;
		case "com.google.android.marvin.talkback":			return true;
		case "com.google.android.apps.walletnfcrel":			return true;
		default: return false;
		}
	}
	//GMS to known name
	public String getGMSname(String com){
		switch(com){
		case "com.android.vending":			return "<html><font color=\"#FF0000\">Google Play Store商店</font>=com.android.vending</html>";
		case "com.google.android.setupwizard":			return "<html><font color=\"#FF0000\">Setup Wizard开机向导</font>=com.google.android.setupwizard</html>";
		case "com.google.android.googlequicksearchbox":			return "<html><font color=\"#FF0000\">Google Search搜索</font>=com.google.android.googlequicksearchbox</html>";
		case "com.google.android.apps.books":			return "<html><font color=\"#FF0000\">Google Play Books图书</font>=com.google.android.apps.books</html>";
		case "com.android.chrome":			return "<html><font color=\"#FF0000\">Chrome浏览器</font>=com.android.chrome</html>";
		case "com.google.android.apps.docs":			return "<html><font color=\"#FF0000\">Drive</font>=com.google.android.apps.docs</html>";
		case "com.google.android.gm":			return "<html><font color=\"#FF0000\">Gmail</font>=com.google.android.gm</html>";
		case "com.google.android.talk":			return "<html><font color=\"#FF0000\">Hangouts环聊</font>=com.google.android.talk</html>";
		case "com.google.android.apps.maps":			return "<html><font color=\"#FF0000\">Maps地图</font>=com.google.android.apps.maps</html>";
		case "com.google.android.music":			return "<html><font color=\"#FF0000\">Google Play Music音乐</font>=com.google.android.music</html>";
		case "com.google.android.apps.magazines":			return "<html><font color=\"#FF0000\">Google Play Newsstand新闻</font>=com.google.android.apps.magazines</html>";
		case "com.google.android.apps.photos":			return "<html><font color=\"#FF0000\">Google Photos图库</font>=com.google.android.apps.photos</html>";
		case "com.google.android.play.games":			return "<html><font color=\"#FF0000\">Google Play Games游戏</font>=com.google.android.play.games</html>";
		case "com.google.android.apps.plus":			return "<html><font color=\"#FF0000\">Google+</font>=com.google.android.apps.plus</html>";
		case "com.google.android.videos":			return "<html><font color=\"#FF0000\">Google Play Movies电影</font>=com.google.android.videos</html>";
		case "com.google.android.youtube":			return "<html><font color=\"#FF0000\">YouTube视频</font>=com.google.android.youtube</html>";
		case "com.google.android.calendar":			return "<html><font color=\"#FF0000\">Calendar日历</font>=com.google.android.calendar</html>";
		case "com.google.android.apps.docs.editors.docs":			return "<html><font color=\"#FF0000\">Google Docs文档</font>=com.google.android.apps.docs.editors.docs</html>";
		case "com.google.android.apps.docs.editors.sheets":			return "<html><font color=\"#FF0000\">Google Sheet表格</font>=com.google.android.apps.docs.editors.sheets</html>";
		case "com.google.android.apps.docs.editors.slides":			return "<html><font color=\"#FF0000\">Google Slides幻灯片</font>=com.google.android.apps.docs.editors.slides</html>";
		case "com.google.android.GoogleCamera":			return "<html><font color=\"#FF0000\">Google Camera相机</font>=com.google.android.GoogleCamera</html>";
		case "com.google.android.keep":			return "<html><font color=\"#FF0000\">Keep</font>=com.google.android.keep</html>";
		case "com.google.android.apps.genie.geniewidget":			return "<html><font color=\"#FF0000\">NewsWeather天气</font>=com.google.android.apps.genie.geniewidget</html>";
		case "com.google.android.apps.walletnfcrel":			return "<html><font color=\"#FF0000\">Google Wallet钱包</font>=com.google.android.apps.walletnfcrel</html>";
		default: return com;
		}
	}
	
	//Qcom app to known name
	public String getQcomname(String com){
		switch (com){
		case "com.android.browser": return "<html><font color=\"#FF0000\">Browser原生浏览器</font>=com.android.browser</html>";
		case "com.android.dialer": return "<html><font color=\"#FF0000\">Dialer拨号</font>=com.android.dialer</html>";
		case "com.android.contacts": return "<html><font color=\"#FF0000\">Contacts联系人</font>=com.android.contacts</html>";
		case "com.android.mms": return "<html><font color=\"#FF0000\">Messaging短信</font>=com.android.mms</html>";
		case "com.android.gallery3d": return "<html><font color=\"#FF0000\">Gallery图库</font>=com.android.gallery3d</html>";
		case "com.android.calendar": return "<html><font color=\"#FF0000\">Calendar原生日历</font>=com.android.calendar</html>";
		case "com.android.email": return "<html><font color=\"#FF0000\">Email原生邮件</font>=com.android.email</html>";
		case "com.android.deskclock": return "<html><font color=\"#FF0000\">Clock时钟</font>=com.android.deskclock</html>";
		case "com.android.calculator2": return "<html><font color=\"#FF0000\">Calculator计算器</font>=com.android.calculator2</html>";
		case "com.cyanogenmod.filemanager": return "<html><font color=\"#FF0000\">FileManagerCM文件管理器</font>=com.cyanogenmod.filemanager</html>";
		case "com.caf.fmradio": return "<html><font color=\"#FF0000\">FMRadio收音机</font>=com.caf.fmradio</html>";
		case "com.android.qrdfileexplorer": return "<html><font color=\"#FF0000\">FileManager原生文件管理器</font>=com.android.qrdfileexplorer</html>";
		case "com.android.music": return "<html><font color=\"#FF0000\">Music音乐</font>=com.android.music</html>";
		case "com.android.soundrecorder": return "<html><font color=\"#FF0000\">SoundRecorder录音机</font>=com.android.soundrecorder</html>";
		case "com.android.settings": return "<html><font color=\"#FF0000\">Settings设置</font>=com.android.settings</html>";
		case "com.android.systemui": return "<html><font color=\"#FF0000\">SystemUI状态栏和通知中心</font>=com.android.systemui</html>";
		case "com.android.camera2": return "<html><font color=\"#FF0000\">Camera原生相机</font>=com.android.camera2</html>";
		case "org.codeaurora.snapcam": return "<html><font color=\"#FF0000\">SnapCamera骁龙相机</font>=org.codeaurora.snapcam</html>";
		default: return com;
		}
	}
	
	//MTK app to known name
	public String getMTKname(String com){
		switch(com){
		case "com.android.browser": return "<html><font color=\"#FF0000\">Browser原生浏览器</font>=com.android.browser</html>";
		case "com.android.dialer": return "<html><font color=\"#FF0000\">Dialer拨号</font>=com.android.dialer</html>";
		case "com.android.contacts": return "<html><font color=\"#FF0000\">Contacts联系人</font>=com.android.contacts</html>";
		case "com.android.mms": return "<html><font color=\"#FF0000\">Messaging短信</font>=com.android.mms</html>";
		case "com.android.gallery3d": return "<html><font color=\"#FF0000\">Gallery图库</font>=com.android.gallery3d</html>";
		case "com.android.calendar": return "<html><font color=\"#FF0000\">Calendar原生日历</font>=com.android.calendar</html>";
		case "com.android.email": return "<html><font color=\"#FF0000\">Email原生邮件</font>=com.android.email</html>";
		case "com.android.deskclock": return "<html><font color=\"#FF0000\">Clock时钟</font>=com.android.deskclock</html>";
		case "com.android.calculator2": return "<html><font color=\"#FF0000\">Calculator计算器</font>=com.android.calculator2</html>";
		case "com.mediatek.videoplayer": return "<html><font color=\"#FF0000\">Video视频</font>=com.mediatek.videoplayer</html>";
		case "com.mediatek.FMRadio": return "<html><font color=\"#FF0000\">FMRadio收音机</font>=com.mediatek.FMRadio</html>";
		case "com.mediatek.filemanager": return "<html><font color=\"#FF0000\">FileManager文件管理器</font>=com.mediatek.filemanager</html>";
		case "com.android.music": return "<html><font color=\"#FF0000\">Music音乐</font>=com.android.music</html>";
		case "com.android.soundrecorder": return "<html><font color=\"#FF0000\">SoundRecorder录音机</font>=com.android.soundrecorder</html>";
		case "com.android.settings": return "<html><font color=\"#FF0000\">Settings设置</font>=com.android.settings</html>";
		case "com.android.systemui": return "<html><font color=\"#FF0000\">SystemUI状态栏和通知中心</font>=com.android.systemui</html>";
		case "com.android.camera2": return "<html><font color=\"#FF0000\">Camera原生相机</font>=com.android.camera2</html>";
		default:return com;
		}
	}
}
