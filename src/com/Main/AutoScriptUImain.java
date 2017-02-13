package com.Main;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.AutoScript.AutoScriptShotUI;
import com.AutoScript.EditScript;
import com.AutoScript.PlaybackScript;
import com.AutoScript.RecordScript;
import com.Functions.Helper;
import com.Functions.LineNumberHeaderView;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class AutoScriptUImain extends JPanel {
	AutoScriptShotUI autoscriptshotui;
	RecordScript recordscript=new RecordScript();
	PlaybackScript playbackscript=new PlaybackScript();
	JTextArea textAreaShowScript;
	EditScript editscript=new EditScript();
	JLabel lblMouse;
	JLabel lblResolution;
	JLabel lblLandspcaceMode;
	JCheckBox chckbxlandscapeMode;
	JScrollPane scrollPaneShowXY;
	
	boolean isstartrecord=false;
	JButton btnStartRecord;
	boolean isstartplayback=false;
	JButton btnPlayback;
	/**
	 * Create the panel.
	 */
	public AutoScriptUImain() {
		setSize(700,650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		autoscriptshotui=recordscript.getAutoscriptshotui();
		add(autoscriptshotui);
		autoscriptshotui.setVisible(false);
		//textAreaShowScript
		textAreaShowScript = new JTextArea("**Script interpreter version=1.0**\n");
		textAreaShowScript.setWrapStyleWord(true);
		textAreaShowScript.setLineWrap(true);
		textAreaShowScript.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPaneShowXY = new JScrollPane(textAreaShowScript);
		scrollPaneShowXY.setBounds(260, 190, 230, 400);
		add(scrollPaneShowXY);
		//行号
		LineNumberHeaderView lineNumberHeader = new LineNumberHeaderView();
		scrollPaneShowXY.setRowHeaderView(lineNumberHeader);
		
		recordscript.settextAreaShowScript(textAreaShowScript);//recordscript add textArea
		editscript.settextAreaShowScript(textAreaShowScript);
		//鼠标信息
		lblMouse = new JLabel("");
		lblMouse.setBounds(169, 175, 78, 15);
		add(lblMouse);
		autoscriptshotui.setlblMouse(lblMouse);
		//分辨率
		lblResolution = new JLabel("");
		lblResolution.setBounds(260, 175, 153, 15);
		add(lblResolution);
		recordscript.setlblResolution(lblResolution);
		//向左横屏提示
		lblLandspcaceMode = new JLabel(getString("lblLandspcaceMode"));
		lblLandspcaceMode.setBounds(494, 175, 153, 15);
		lblLandspcaceMode.setVisible(false);
		add(lblLandspcaceMode);
		//lable AutoScript
		JLabel lblAutoScript = new JLabel(getString("lblAutoScript"));
		lblAutoScript.setVerticalAlignment(SwingConstants.TOP);
		lblAutoScript.setFont(new Font("微软雅黑", Font.BOLD, 26));
		lblAutoScript.setBounds(10, 145, 162, 35);
		add(lblAutoScript);
		//start record button
		 btnStartRecord = new JButton(getString("btnStartRecord"));
		 btnStartRecord.setForeground(Color.BLACK);
		btnStartRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!isstartrecord){
				//acivelogthreadrun true
				if(recordscript.getAutoscriptthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"getAutoscriptthreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"start ScriptRecord button");
					return;
				}
				//acivelogthreadrun true
				if(playbackscript.getPlaybackscriptthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"getPlaybackscriptthreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls wait for playback finished!", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"start ScriptRecord button");
					return;
				}
				//device null
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"start ScriptRecord button");
					return;
				}
				recordscript.setiscancelled(true);
				recordscript.run();
			//	lblisrecord.setText(getString("lblisrecord"));
				autoscriptshotui.setVisible(true);
				//change
				lblLandspcaceMode.setVisible(true);
				isstartrecord=true;
				btnStartRecord.setText(getString("btnStop"));
				btnStartRecord.setForeground(Color.RED);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"start Script Record button");
				}else{
					recordscript.setiscancelled(true);
					//change 
					lblLandspcaceMode.setVisible(false);
					isstartrecord=false;
					btnStartRecord.setText(getString("btnStartRecord"));
					btnStartRecord.setForeground(Color.BLACK);
				//	lblisrecord.setText(getString("lblisrecord1"));
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop record script!", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
					com.Main.ThenToolsRun.logger.log(Level.INFO,"stop record script button");
				}
				
			}
		});
		btnStartRecord.setBounds(494, 190, 100, 25);
		add(btnStartRecord);
		
		//stop button
//		JButton btnStop = new JButton(getString("btnStop"));
//		btnStop.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				recordscript.setiscancelled(true);
//				lblisrecord.setText(getString("lblisrecord1"));
//				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop record script!", 
//						"Message", JOptionPane.INFORMATION_MESSAGE);
//				com.Main.ThenToolsRun.logger.log(Level.INFO,"stop record script button");
//			}
//		});
//		btnStop.setBounds(600, 190, 100, 25);
//		add(btnStop);
		//Playback button
		btnPlayback = new JButton(getString("btnPlayback"));
		btnPlayback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!isstartplayback){
				//acivelogthreadrun true
				if(recordscript.getAutoscriptthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"getAutoscriptthreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls stop record first!", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Playback button");
					return;
				}
				//acivelogthreadrun true
				if(playbackscript.getPlaybackscriptthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"getPlaybackscriptthreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Playback button");
					return;
				}
				//device null
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Playback button");
					return;
				}
				if(textAreaShowScript.getText().equals("")){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No Script!", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Playback button");
					return;
				}
//				playbackscript.setxD(autoscriptshotui.getxD());
//				playbackscript.setyD(autoscriptshotui.getyD());
				playbackscript.settextAreaShowScript(textAreaShowScript);
				playbackscript.run();
				//change
				isstartplayback=true;
				btnPlayback.setForeground(Color.RED);
				btnPlayback.setText(getString("btnPlayback1"));
				}else{
					playbackscript.Cancelplayback();
					//change
					isstartplayback=false;
					btnPlayback.setForeground(Color.BLACK);
					btnPlayback.setText(getString("btnPlayback"));
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Playback button");
			}
				
		});
		btnPlayback.setBounds(600, 189, 100, 25);
		add(btnPlayback);
		
		//clear button
		JButton btnClear = new JButton(getString("btnClear"));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(recordscript.mFBImage!=null){
					recordscript.ClearScreen();	
				}else{
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls start record first!", 
							"Message", JOptionPane.ERROR_MESSAGE);
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"clear record screen button");
			}
		});
		btnClear.setBounds(494, 225, 100, 25);
		add(btnClear);
		//sleep button
		JButton btnESleep = new JButton(getString("btnESleep"));
		btnESleep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String time = JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame, "Pls enter sleep time(ms):","Input",JOptionPane.INFORMATION_MESSAGE); 
				if(time!=null){
					if(!time.equals("")&&Helper.isNumeric(time)){
							editscript.Sleep(time);
					}else{
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls input numbers!", 
								"Message", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnESleep.setBounds(494, 343, 100, 25);
		add(btnESleep);
		//pressbutton button
		JButton btnEPressbutton = new JButton(getString("btnEPressbutton"));
		btnEPressbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] buttonOption = {"BACK","HOME","MENU","POWER","VOLUME_DOWN","VOLUME_UP"};
				int response=JOptionPane.showOptionDialog(com.Main.ThenToolsRun.mainFrame, "Pls select the KEY:", 
						"Message",JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null,
						buttonOption, buttonOption[0]);
				if(response==0){ 
					editscript.Pressbutton("BACK");
				}else if(response==1){ 
					editscript.Pressbutton("HOME");
				}else if(response==2){ 
					editscript.Pressbutton("MENU");
				}else if(response==3){ 
					editscript.Pressbutton("POWER");
				}else if(response==4){ 
					editscript.Pressbutton("VOLUME_DOWN");
				}else if(response==5){ 
					editscript.Pressbutton("VOLUME_UP");
				}
			}
		});
		btnEPressbutton.setBounds(600, 343, 100, 25);
		add(btnEPressbutton);
		//start loop button
		JButton btnEStartLoop = new JButton(getString("btnEStartLoop"));
		btnEStartLoop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String time = JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame, "Pls enter loop times:","Input",JOptionPane.INFORMATION_MESSAGE); 
				if(time!=null){
					if(!time.equals("")&&Helper.isNumeric(time)&&!time.equals("0")){
							editscript.Startloop(time);
					}else{
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls input numbers!", 
								"Message", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnEStartLoop.setBounds(494, 378, 100, 25);
		add(btnEStartLoop);
		//end loop button
		JButton btnEEndLoop = new JButton(getString("btnEEndLoop"));
		btnEEndLoop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editscript.Endloop();
			}
		});
		btnEEndLoop.setBounds(600, 378, 100, 25);
		add(btnEEndLoop);
		//screen cap button
		JButton btnScreencap = new JButton(getString("btnScreencap"));
		btnScreencap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editscript.Screencap();
			}
		});
		btnScreencap.setBounds(494, 442, 100, 25);
		add(btnScreencap);
		//lab edit
		JLabel lblEdit = new JLabel(getString("lblEdit"));
		lblEdit.setBounds(494, 318, 124, 15);
		add(lblEdit);
		//reboot button
		JButton btnReboot = new JButton(getString("btnReboot"));
		btnReboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String time = JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame, "Pls enter the time(ms) after reboot:","Input",JOptionPane.INFORMATION_MESSAGE); 
				if(time!=null){
					if(!time.equals("")&&Helper.isNumeric(time)){
							editscript.Reboot(time);
					}else{
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls input numbers!", 
								"Message", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnReboot.setBounds(494, 413, 100, 25);
		add(btnReboot);
		//Notes button
		JButton btnNotes = new JButton(getString("btnNotes"));
		btnNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String notes = JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame, "Pls enter your notes:","Input",JOptionPane.INFORMATION_MESSAGE); 
				if(notes!=null){
					editscript.Notes(notes);
				}
			}
		});
		btnNotes.setBounds(600, 477, 100, 25);
		add(btnNotes);
		//Save script button
		JButton btnSaveScript = new JButton(getString("btnSaveScript"));
		btnSaveScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editscript.SaveScript();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"save script button");
			}
		});
		btnSaveScript.setBounds(600, 224, 100, 25);
		add(btnSaveScript);
		//load script button
		JButton btnLoadScript = new JButton(getString("btnLoadScript"));
		btnLoadScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editscript.LoadScript();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"load script button");
			}
		});
		btnLoadScript.setBounds(494, 262, 100, 25);
		add(btnLoadScript);
		//log button
		JButton btnlog = new JButton(getString("btnlog"));
		btnlog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editscript.Startlog();
			}
		});
		btnlog.setBounds(600, 413, 100, 25);
		add(btnlog);
		//detail button
		JButton detail = new JButton(getString("detail"));
		detail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, getString("detail1"), 
						"Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		detail.setBounds(547, 512, 100, 25);
		add(detail);
		//wake button
		JButton btnWake = new JButton(getString("btnWake"));
		btnWake.setToolTipText(getString("btnWaketip"));
		btnWake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editscript.Wake();
			}
		});
		btnWake.setBounds(494, 477, 100, 25);
		add(btnWake);
		//landscapeMode checkbox
//		chckbxlandscapeMode = new JCheckBox(getString("chckbxlandscapeMode"));
//		chckbxlandscapeMode.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e){
//					if(chckbxlandscapeMode.isSelected()){
//						autoscriptshotui.setmPortrait(false);
//						autoscriptshotui.setSize(false);
//						recordscript.setmPortrait(false);
//						textAreaShowScript.setBounds(20, 420, 400, 170);
//						scrollPaneShowXY.setBounds(20, 420, 400, 170);
//					}else{
//						autoscriptshotui.setmPortrait(true);
//						recordscript.setmPortrait(true);
//						autoscriptshotui.setSize(true);
//						textAreaShowScript.setBounds(260, 190, 230, 400);
//						scrollPaneShowXY.setBounds(260, 190, 230, 400);
//					}
//			}
//		}); 
//		chckbxlandscapeMode.setBounds(391, 162, 180, 23);
//		add(chckbxlandscapeMode);
		//type button
		JButton btnType = new JButton(getString("btnType"));
		btnType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame, "Pls input your string:","Input",JOptionPane.INFORMATION_MESSAGE); 
				if(str!=null&&!str.equals("")){
						editscript.Type(str);
				}
			}
		});
		btnType.setBounds(600, 442, 100, 25);
		add(btnType);

	}
	//setLandscapeMode
	public void setmPortraitMode(boolean mPortrait){
		if(!mPortrait){
			autoscriptshotui.setmPortrait(false);
			autoscriptshotui.setmPortraitSize(false);
			recordscript.setmPortrait(false);
			textAreaShowScript.setBounds(20, 420, 400, 170);
			scrollPaneShowXY.setBounds(20, 420, 400, 170);
			textAreaShowScript.setCaretPosition(textAreaShowScript.getText().length());
		}else{
			autoscriptshotui.setmPortrait(true);
			recordscript.setmPortrait(true);
			autoscriptshotui.setmPortraitSize(true);
			textAreaShowScript.setBounds(260, 190, 230, 400);
			scrollPaneShowXY.setBounds(260, 190, 230, 400);
			textAreaShowScript.setCaretPosition(textAreaShowScript.getText().length());
		}
	}

	//get isstartrecord
	public boolean getisstartrecord(){
		return isstartrecord;
	}
	//get Btn startrecord
	public JButton getbtnStartRecord(){
		return btnStartRecord;
	}
	//get isstartplayback
	public boolean getisstartplayback(){
		return isstartplayback;
	}
	//get Btn btnPlayback
	public JButton getbtnbtnPlayback(){
		return btnPlayback;
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblAutoScript": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "自动化脚本:";
		}else{
			return "AutoScript:";
		}
		case "btnStartRecord": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "开始录制";
		}else{
			return "StartRecord";
		}
		case "btnStop": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "停止录制";
		}else{
			return "StopRecord";
		}
		case "btnPlayback": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "回放脚本";
		}else{
			return "Playback";
		}
		case "btnPlayback1": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "停止回放";
		}else{
			return "StopPlayback";
		}
		case "btnClear": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "清除";
		}else{
			return "Clear";
		}
		case "btnESleep": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "睡眠(毫秒)";
		}else{
			return "Sleep(ms)";
		}
		case "btnEPressbutton": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "物理按键";
		}else{
			return "Button";
		}
		case "btnEStartLoop": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "=开始循环=";
		}else{
			return "StartLoop";
		}
		case "btnEEndLoop": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "=结束循环=";
		}else{
			return "EndLoop";
		}
		case "lblLandspcaceMode": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html><font color=\"#FF0000\">支持向左横屏</font></html>";
		}else{
			return "<html><font color=\"#FF0000\">Support Left Landspace</font></html>";
		}
		case "btnScreencap": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "截图";
		}else{
			return "ScreenCap";
		}
		case "lblEdit": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "插入命令:";
		}else{
			return "Inser:";
		}
		case "btnReboot": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "重启";
		}else{
			return "Reboot";
		}
		case "btnNotes": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "**备注**";
		}else{
			return "**Notes**";
		}
		case "btnSaveScript": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "保存脚本";
		}else{
			return "SaveScript";
		}
		case "btnLoadScript": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "载入脚本";
		}else{
			return "LoadScript";
		}
		case "btnlog": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "激活Log";
		}else{
			return "ActiveLog";
		}
		case "detail": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "脚本说明";
		}else{
			return "Detail";
		}
		case "btnWake": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "亮屏";
		}else{
			return "Screen On";
		}
		case "btnWaketip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "唤醒屏幕,1表示等待2秒,0表示不等待";
		}else{
			return "Screen On, '1'mean wait for 2s, '0'mean no wait";
		}
		case "chckbxlandscapeMode": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "向左横屏模式";
		}else{
			return "Left Landspace";
		}
		case "btnType": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "输入字符串";
		}else{
			return "Type";
		}
		case "detail1": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "语法说明:\n"
					+ "1.如Tap:(x,x),1最后面的1表示执行后等待2秒,0表示不等待\n"
					+ "2.Screen Cap截图保存在桌面/ThenLogs/Script/ScreenCap中\n"
					+ "3.Reboot:(xxxms)表示重启后等待xxx+15000毫秒后执行后面的\n"
					+ "4.Active Log表示Qcom平台激活catchlog,MTK平台激活mtklogger\n"
					+ "5.向左横屏模式表示手机屏幕向左旋转后的模式,如短信界面向左横屏手机.";
		}else{
			return "Script detail:\n"
					+ "1.\"Tap:(x,x),1\": final '1' mean it will wait for 2s, '0' mean not wait\n"
					+ "2.Images from \"Screen Cap\" saves in %desk%/ThenLogs/Script/ScreenCap\n"
					+ "3.\"Reboot:(xxxms)\"mean it will wait for xxx+15000ms after reboot, and then start others\n"
					+ "4.\"Active Log\"mean Qcom actives catchlog, MTK actives mtklogger\n"
					+ "5.\"Left Landspace\"mean turn left device ";
			
		}
			default: return "";
		}	
	}
}
