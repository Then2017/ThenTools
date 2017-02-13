package com.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.Functions.CheckUE;
import com.Functions.Excute;
import com.Qcom.QcomADBRoot;
import com.Qcom.QcomActive;
import com.Qcom.QcomActiveWithHiddenlog;
import com.Qcom.QcomGet;
import com.Qcom.QcomHiddenLogSettings;
import com.Qcom.QcomHiddenLogSettingsUI;
import com.Qcom.QcomStop;
import com.Qcom.QcomhappentimeUI;
import com.Qcom.WaitUI;
import com.Qcom.WaitUItoPC;

import java.awt.Font;
import java.util.logging.Level;

import javax.swing.JProgressBar;
import javax.swing.UIManager;
import java.awt.Color;

public class QcomUImain extends JPanel {
	
	 JComboBox deviceslist; //设备列表
	 JLabel lblDevicestatus;//设备状态显示
	 JLabel lblDeviceInfo;//
	 JCheckBox chckbxdel;
	 boolean dellog=true;
	 QcomActive qcomactive=new QcomActive();
	 QcomStop buttonStop=new QcomStop();
	 QcomGet buttonGet=new QcomGet();
	 QcomADBRoot buttonADBRoot=new QcomADBRoot();
	 QcomActiveWithHiddenlog qcomActiveWithHiddenlog=new QcomActiveWithHiddenlog();
	 QcomhappentimeUI happentimeUI;
	 WaitUI waitui;
	 WaitUItoPC waituitoPC;
	 	QcomHiddenLogSettings qcomHiddenLogSettings=new QcomHiddenLogSettings();
	/**
	 * Create the panel.
	 */
	public QcomUImain() {
		setSize(700,650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		happentimeUI= new QcomhappentimeUI();
		//激活
		JButton btnActive = new JButton(getString("btnActive"));
		btnActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//acivelogthreadrun true
				if(qcomactive.getActivelogthreadrun()||qcomActiveWithHiddenlog.getActivelogthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"activelogthreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"active log button running");
					return;
				}
				//check log status
				if(CheckUE.getlogstatus().equals("Not Running!")){
					if(qcomHiddenLogSettings.getIsopen()||(qcomHiddenLogSettings.getIsopendebug()&&CheckUE.getisdebug().equals("Yes"))){
						qcomActiveWithHiddenlog.start();
					}else{
						qcomactive.start();	
					}
			}else if(CheckUE.getlogstatus().equals("<font color=\"#FF0000\">Running!</font>")){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Logs are running");
				int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Logs are running, Do you want to continue to active log? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
				if(confirm==0){
					if(qcomHiddenLogSettings.getIsopen()||(qcomHiddenLogSettings.getIsopendebug()&&CheckUE.getisdebug().equals("Yes"))){
						qcomActiveWithHiddenlog.start();
					}else{
						qcomactive.start();	
					}
				}else{
					return;
				}
			}else if(CheckUE.getlogstatus().equals("No device!")){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"No devices checked");
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked", 
						"Message", JOptionPane.ERROR_MESSAGE); 
			}else if(CheckUE.getlogstatus().equals("<font color=\"#FF0000\">Active Repeat!</font>")){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Active Repeat!");
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Active repeat!", 
						"Message", JOptionPane.ERROR_MESSAGE); 
			}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"active log button");
			}
		});
		btnActive.setBounds(480, 232, 100, 25);
		add(btnActive);
		
		//获取
		JButton btnGet = new JButton(getString("btnGet"));
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				happentimeUI.updatetime();
				happentimeUI.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"qcom get log button");
			}
		});
		btnGet.setBounds(480, 277, 100, 25);
		add(btnGet);
		
		//停止
		JButton btnStop = new JButton(getString("btnStop"));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(buttonStop.getstoplogthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"stoplogthreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"stop and del log button1");
					return;
				}
				//check devices
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked!", 
							"Messge", JOptionPane.ERROR_MESSAGE); 	
					return;
				}
				if(!dellog){
					int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Do you want to stop log? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						buttonStop.run(dellog);
					}else{
						return;
					}

				}else{
					int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Do you want to stop and del logs? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						buttonStop.run(dellog);
					}else{
						return;
					}
				}	
				com.Main.ThenToolsRun.logger.log(Level.INFO,"stop and del log button2");
			}
		});
		btnStop.setBounds(480, 322, 100, 25);
		add(btnStop);
		//删除
	    chckbxdel = new JCheckBox(getString("chckbxdel"));
	    chckbxdel.setSelected(true);
		chckbxdel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					if(chckbxdel.isSelected()){
						dellog=true;
					}else{
						dellog=false;
					}
			}
		}); 
		chckbxdel.setBounds(599, 323, 95, 23);
		add(chckbxdel);
		
		//adb root button
		JButton btnAdbRoot = new JButton(getString("btnAdbRoot"));
		btnAdbRoot.setToolTipText(getString("btnAdbRoottip"));
		btnAdbRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonADBRoot.main();
			}
		});
		btnAdbRoot.setBounds(480, 186, 100, 25);
		add(btnAdbRoot);
		
		JLabel lblQcom = new JLabel(getString("lblQcom"));
		lblQcom.setVerticalAlignment(SwingConstants.TOP);
		lblQcom.setFont(new Font("微软雅黑", Font.BOLD, 26));
		lblQcom.setBounds(10, 145, 126, 35);
		add(lblQcom);
		
		//active logs after boot,save logs in UE
		JButton btnBootlogactive = new JButton(getString("btnBootlogactive"));
		//btnBootlogactive.setForeground(Color.RED);
		btnBootlogactive.setToolTipText(getString("btnBootlogactivetip"));
		btnBootlogactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(!com.Main.ThenToolsRun.crypt.isvip()){
//					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "VIP feature!", 
//							"Message", JOptionPane.ERROR_MESSAGE);
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"btnBootlogactive needs VIP");
//					return;
//				}
				waitui=null;
				waitui=new WaitUI();
				waitui.setVisible(true);	
				com.Main.ThenToolsRun.logger.log(Level.INFO,"active boot log button");
			}
		});
		btnBootlogactive.setBounds(480, 466, 100, 25);
		add(btnBootlogactive);
		//active logs after boot ,save logs in PC
		JButton btnBootlogactivePC = new JButton(getString("btnBootlogactivePC"));
		//btnBootlogactivePC.setForeground(Color.RED);
		btnBootlogactivePC.setToolTipText(getString("btnBootlogactivePCtip"));
		btnBootlogactivePC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				if(!com.Main.ThenToolsRun.crypt.isvip()){
//					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "VIP feature!", 
//							"Message", JOptionPane.ERROR_MESSAGE);
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"btnBootlogactivePC needs VIP");
//					return;
//				}
				waituitoPC=null;
				waituitoPC=new WaitUItoPC();
				waituitoPC.setVisible(true);	
				com.Main.ThenToolsRun.logger.log(Level.INFO,"active boot log to PC button");
				
			}
		});
		btnBootlogactivePC.setBounds(480, 423, 100, 25);
		add(btnBootlogactivePC);
		//label boot up logs
		JLabel lblbootuplogs = new JLabel(getString("lblbootuplogs"));
		lblbootuplogs.setBounds(428, 396, 172, 17);
		add(lblbootuplogs);
		//lbl catchlgo
		JLabel lblCatchLog = new JLabel(getString("lblCatchLog"));
		lblCatchLog.setBounds(428, 171, 272, 15);
		add(lblCatchLog);
		//lbl picture
		JLabel lblPicture = new JLabel("");
		lblPicture.setIcon(new ImageIcon(getClass().getResource("/Rescource/Qcom.jpg")));
		lblPicture.setBounds(10, 200, 400, 300);
		add(lblPicture);
		
		//hiddenlog setting button
		JButton btnHiddenlog = new JButton(getString("btnHiddenlog"));
		btnHiddenlog.setToolTipText(getString("btnHiddenlog"));
		btnHiddenlog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QcomHiddenLogSettingsUI qcomhiddenlogsettingsui=new QcomHiddenLogSettingsUI();
				qcomhiddenlogsettingsui.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"press btnHiddenlog button");
			}
		});
		btnHiddenlog.setBounds(480, 361, 100, 25);
		add(btnHiddenlog);
		
	}
	public WaitUI getwaitui(){
		return waitui;
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "btnActive": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "激活Log";
		}else{
			return "Active Logs";
		}
		case "btnGet": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "提取Log";
		}else{
			return "Get Logs";
		}
		case "btnStop": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "停止Log";
		}else{
			return "Stop Logs";
		}
		case "chckbxdel": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "并删除";
		}else{
			return "and Del";
		}
		case "btnAdbRoot": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ADB Root";
		}else{
			return "ADB Root";
		}
		case "btnAdbRoottip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "root adb,只适用于某些平台";
		}else{
			return "root adb, just for some platform!";
		}
		case "lblQcom": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "高通平台:";
		}else{
			return "Qcom:";
		}
		case "btnBootlogactive": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "存储到UE";
		}else{
			return "Save inUE";
		}
		case "btnBootlogactivetip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "运行后将持续激活log,当检测到在手机存储激活成功后停止";
		}else{
			return "It will active log until success, which saves in device";
		}
		case "btnBootlogactivePC": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "存储到PC";
		}else{
			return "Save inPC";
		}
		case "btnBootlogactivePCtip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "运行后将持续激活log,并保存在PC桌面/ThenLogs/xYxMxD/BootLog_xx,关闭对话框后才会停止";
		}else{
			return "It will active log until close the chat box, which saves in PC%desk%/ThenLogs/xYxMxD/BootLog_xx";
		}
		case "lblbootuplogs": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "开机Log激活:";
		}else{
			return "Bootup log actives:";
		}
		case "lblCatchLog": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "Catchlog:(适用于所有支持adb调试平台)";
		}else{
			return "Catchlog:(used for all adb-debug platfroms)";
		}
		case "btnHiddenlog": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "隐藏Log";
		}else{
			return "HiddenLog";
		}
		case "btnHiddenlogtip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "首先必须root,可以开启更多更详细的log信息";
		}else{
			return "First must be root, and then can open more detail logs";
		}
			default: return "";
		}	
	}
}
