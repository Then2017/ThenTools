package com.Main;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.SwingConstants;

import com.MTK.MTKADBRoot;
import com.MTK.MTKLogger;
import com.MTK.MTKhappentimeUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.JCheckBox;

public class MTKUImain extends JPanel {
	
	 MTKhappentimeUI happentimeUI= new MTKhappentimeUI();
	 MTKADBRoot adbroot=new MTKADBRoot();
	 MTKLogger mtklogger=new MTKLogger();
	 
	 JCheckBox chckbxdel;
	 boolean dellog=true;
	/**
	 * Create the panel.
	 */
	public MTKUImain() {
		setSize(700,650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		//Get logs button
		JButton btnGet = new JButton(getString("btnGet"));
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				happentimeUI.updatetime();
				happentimeUI.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"mtk get log button");
			}
		});
		btnGet.setBounds(480, 277, 100, 25);
		add(btnGet);
		
		JLabel lblMtk = new JLabel(getString("lblMtk"));
		lblMtk.setVerticalAlignment(SwingConstants.TOP);
		lblMtk.setFont(new Font("微软雅黑", Font.BOLD, 26));
		lblMtk.setBounds(10, 145, 126, 40);
		add(lblMtk);
		
		//adb root button
		JButton btnAdbroot = new JButton(getString("btnAdbroot"));
		btnAdbroot.setVisible(false);
		btnAdbroot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				com.Main.ThenToolsRun.logger.log(Level.INFO,"root mtk button");
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "inputs *#*#13646633#*#*->Log and Debugging->User2Root->Root,run root.", 
						"Messge", JOptionPane.INFORMATION_MESSAGE); 
				return;
				//adbroot.main();
			}
		});
		btnAdbroot.setBounds(20, 186, 100, 25);
		add(btnAdbroot);
		
		//Active log button
		JButton btnActiveLogs = new JButton(getString("btnActiveLogs"));
		btnActiveLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//check devices
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked!", 
							"Messge", JOptionPane.ERROR_MESSAGE); 	
					return;
				}
				//if mtk platform
				if(com.Main.ThenToolsRun.platform.contains("msm")){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Only work for MTK platform!", 
							"Messge", JOptionPane.ERROR_MESSAGE); 	
					return;
				}
				mtklogger.activemtklog();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"active mtk log button");
			}
		});
		btnActiveLogs.setBounds(480, 232, 100, 25);
		add(btnActiveLogs);
		//Stop log button
		JButton btnStopLogs = new JButton(getString("btnStopLogs"));
		btnStopLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check devices
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked!", 
							"Messge", JOptionPane.ERROR_MESSAGE); 	
					return;
				}
				//if mtk platform
				if(com.Main.ThenToolsRun.platform.contains("msm")){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Only work for MTK platform!", 
							"Messge", JOptionPane.ERROR_MESSAGE); 	
					return;
				}
				if(!dellog){
					int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Do you want to stop log? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						mtklogger.stopmtklog(dellog);
					}else{
						return;
					}

				}else{
					int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Do you want to stop and del logs? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						mtklogger.stopmtklog(dellog);
					}else{
						return;
					}
				}	
				com.Main.ThenToolsRun.logger.log(Level.INFO,"stop mtk log button");
			}
		});
		btnStopLogs.setBounds(480, 322, 100, 25);
		add(btnStopLogs);
		//Show UI
		JButton btnShowUi = new JButton(getString("btnShowUi"));
		btnShowUi.setToolTipText(getString("btnShowUitip"));
		btnShowUi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check devices
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked!", 
							"Messge", JOptionPane.ERROR_MESSAGE); 	
					return;
				}
				//if mtk platform
				if(com.Main.ThenToolsRun.platform.contains("msm")){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Only work for MTK platform!", 
							"Messge", JOptionPane.ERROR_MESSAGE); 	
					return;
				}
				mtklogger.showui();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"show mtk log ui button");
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "MTKLogger is opened in your UE, pls check.", 
						"Messge", JOptionPane.INFORMATION_MESSAGE); 
			}
		});
		btnShowUi.setBounds(480, 186, 100, 25);
		add(btnShowUi);
		//and del logs
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
		//lbl Mtklogger
		JLabel lblMTKLogger = new JLabel(getString("lblMTKLogger"));
		lblMTKLogger.setBounds(424, 170, 95, 15);
		add(lblMTKLogger);
		//lbl picture
		JLabel lblPicture = new JLabel("");
		lblPicture.setIcon(new ImageIcon(getClass().getResource("/Rescource/MTK.jpg")));
		lblPicture.setBounds(10, 200, 400, 300);
		add(lblPicture);
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "btnActiveLogs": 
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
		case "btnStopLogs": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "停止Log";
		}else{
			return "Stop Logs";
		}
		case "chckbxdel": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "删除";
		}else{
			return "and Del";
		}
		case "btnAdbRoot": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ADB Root";
		}else{
			return "ADB Root";
		}
		case "lblMtk": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "MTK平台:";
		}else{
			return "MTK:";
		}
		case "btnShowUi": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "显示界面";
		}else{
			return "Show UI";
		}
		case "btnShowUitip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "在设备上运行显示MTKlogger";
		}else{
			return "Show MTKlogger in device";
		}
		case "lblMTKLogger": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "MTKLogger:";
		}else{
			return "MTKLogger:";
		}
			default: return "";
		}
	}
}
