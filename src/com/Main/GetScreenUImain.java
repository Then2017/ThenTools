package com.Main;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.Functions.Excute;
import com.GetScreen.AndroidScreenMonitor;
import com.GetScreen.EditToolsUI;
import com.GetScreen.ScreenCap;
import com.GetScreen.ScreenEdit;
import com.GetScreen.ScreenMirrorUI;
import com.GetScreen.ScreenRecord;
import com.GetScreen.ScreenRecordUI;
import com.GetScreen.ScreenShotUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.logging.Level;
import javax.swing.JCheckBox;
import java.awt.Color;

public class GetScreenUImain extends JPanel {
	ScreenCap screencap=new ScreenCap();
	ScreenRecord screenrecord=new ScreenRecord();
	private ScreenMirrorUI mMainFrame;
	ScreenShotUI screenshotui;
	EditToolsUI edittoolsui;
	ScreenRecordUI screenrecordui=new ScreenRecordUI();
	JButton btnShow;
	boolean showimage=false;
	ScreenEdit screenedit=new ScreenEdit();
	JLabel lblGetscreen;
	JButton btnEdit;
	JCheckBox chckbxAndSave;
	
	public GetScreenUImain() {
		setSize(750,650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		screenshotui=screencap.getScreenShotUI();
		edittoolsui=screencap.getEditTools();
		add(screenshotui);
		add(edittoolsui);
		screenshotui.setVisible(false);
		edittoolsui.setVisible(false);
		edittoolsui.setscreenshotui(screenshotui);
		
		lblGetscreen = new JLabel(getString("lblGetscreen"));
		lblGetscreen.setVerticalAlignment(SwingConstants.TOP);
		lblGetscreen.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 26));
		lblGetscreen.setBounds(10, 145, 157, 40);
		add(lblGetscreen);
		
		//Screen Cap
		JButton btnScreencap = new JButton(getString("btnScreencap"));
		btnScreencap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//acivelogthreadrun true
				if(screencap.getScreenCapThreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"getScreenCapThreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"screen cap button");
					return;
				}
				//device null
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"screen cap button: no devices");
					return;
				}
				btnShow.setText(getString("btnHidden"));
				com.Main.ThenToolsRun.mainFrame.contentPane.setVisible(false);
				screenshotui.setVisible(true);
				edittoolsui.setVisible(true);
				lblGetscreen.setVisible(false);
				showimage=true;

				screencap.run();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"screen cap button");
				}
		});
		btnScreencap.setBounds(529, 249, 100, 25);
		add(btnScreencap);
		//Screen Monitor
		JButton btnMirror = new JButton(getString("btnMirror"));
	//	btnMirror.setForeground(Color.RED);
		btnMirror.setToolTipText(getString("btnMirrortip"));
		btnMirror.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(!com.Main.ThenToolsRun.crypt.isvip()){
//					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "VIP feature!", 
//							"Message", JOptionPane.ERROR_MESSAGE);
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"btnMirror needs VIP");
//					return;
//				}
			    SwingUtilities.invokeLater(new Runnable() {
			        public void run() {
			            mMainFrame = new ScreenMirrorUI(null);
			            mMainFrame.setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
			            mMainFrame.setVisible(true);
			            mMainFrame.selectDevice();
			        }
			      });
			    com.Main.ThenToolsRun.logger.log(Level.INFO,"button Mirror start");
			}
		});
		btnMirror.setBounds(529, 471, 100, 25);
		add(btnMirror);
		
		//Show or hidden
		btnShow = new JButton(getString("btnShow"));
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(screencap.getmFBImage()==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls screen cap first!", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Pls screen cap first!tap show button");
					return;
				}
				if(showimage){
					//hidden
					btnShow.setText(getString("btnShow"));
					com.Main.ThenToolsRun.mainFrame.contentPane.setVisible(false);
					screenshotui.setVisible(false);
					//
					com.Main.ThenToolsRun.mainFrame.contentPane.setVisible(true);
					screenshotui.setVisible(false);
					edittoolsui.setVisible(false);
					lblGetscreen.setVisible(true);
					showimage=false;
				}else{
					//show
					btnShow.setText(getString("btnHidden"));
					com.Main.ThenToolsRun.mainFrame.contentPane.setVisible(false);
					screenshotui.setVisible(false);
					//
					com.Main.ThenToolsRun.mainFrame.contentPane.setVisible(false);
					screenshotui.setVisible(true);
					edittoolsui.setVisible(true);
					lblGetscreen.setVisible(false);
					showimage=true;
				}
			}
		});
		btnShow.setBounds(529, 284, 100, 25);
		add(btnShow);
		
		//Save image
		JButton btnSave = new JButton(getString("btnSave"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   if(screencap.saveImage()){
				   int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Do you want to edit picture? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						btnEdit.doClick();
					}else{
						return;
					}
				   }
				   com.Main.ThenToolsRun.logger.log(Level.INFO,"tap save button");
			}
		});
		btnSave.setBounds(529, 319, 100, 25);
		add(btnSave);
		
		//screen record
		JButton btnRecord = new JButton(getString("btnRecord"));
		btnRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				screenrecordui.setVisible(true);
				//edittoolsui.setVisible(true);
			}
		});
		btnRecord.setBounds(529, 435, 100, 25);
		add(btnRecord);
		
		JLabel lblScreenCap = new JLabel(getString("lblScreenCap"));
		lblScreenCap.setBounds(529, 214, 105, 25);
		add(lblScreenCap);
		
		JLabel lblScreenRecord = new JLabel(getString("lblScreenRecord"));
		lblScreenRecord.setBounds(529, 400, 105, 25);
		add(lblScreenRecord);
		//Edit 
		btnEdit = new JButton(getString("btnEdit"));
		btnEdit.setToolTipText(getString("btnEdittip"));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				DrawPad drawpad = new DrawPad();
//				drawpad.setVisible(true);
				screenedit.EditByWin(screencap.getimagepath());
			    com.Main.ThenToolsRun.logger.log(Level.INFO,"tap Edit button");
			}
		});
		btnEdit.setBounds(529, 354, 100, 25);
		add(btnEdit);
		//and save checkbox
		chckbxAndSave = new JCheckBox(getString("chckbxAndSave"));
		chckbxAndSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					if(chckbxAndSave.isSelected()){
						screencap.setAndsave(true);
					}else{
						screencap.setAndsave(false);
					}
			}
		}); 
		chckbxAndSave.setBounds(635, 250, 101, 23);
		add(chckbxAndSave);
		
	}

	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblGetscreen": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ÆÁÄ»»ñÈ¡:";
		}else{
			return "GetScreen:";
		}
		case "btnScreencap": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "½ØÍ¼";
		}else{
			return "Screen Cap";
		}
		case "btnMirror": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "¾µÏñ";
		}else{
			return "Mirror";
		}
		case "btnMirrortip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ÆÁÄ»Ó³Éä,Ê±Ê±»ñÈ¡µ±Ç°Éè±¸Í¼Ïñ";
		}else{
			return "Display your device from time to time";
		}
		case "btnShow": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ÏÔÊ¾½ØÍ¼";
		}else{
			return "Show";
		}
		case "btnHidden": //====btnShow
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "Òþ²Ø½ØÍ¼";
		}else{
			return "Hidden";
		}
		case "btnSave": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "±£´æ½ØÍ¼";
		}else{
			return "Save";
		}
		case "btnRecord": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "Â¼Ïñ";
		}else{
			return "Record";
		}
		case "lblScreenCap": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ÆÁÄ»½ØÍ¼:";
		}else{
			return "Screen Cap: ";
		}
		case "lblScreenRecord": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ÆÁÄ»Â¼Ïñ:";
		}else{
			return "Screen Record:";
		}
		case "btnEdit": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "±à¼­½ØÍ¼";
		}else{
			return "Edit";
		}
		case "btnEdittip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ÔËÐÐwindows×Ô´øÍ¼Æ¬±à¼­¹¤¾ß±à¼­µ«ÉÏ´Î±£´æµÄ½ØÍ¼Í¼Æ¬";
		}else{
			return "Edit picture by windows picture tool, which you saves last time";
		}
		case "chckbxAndSave": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "²¢±£´æ";
		}else{
			return "and save";
		}
			default: return "";
		}
	}
}