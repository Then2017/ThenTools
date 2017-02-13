package com.Main;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.derby.impl.sql.catalog.SYSSTATEMENTSRowFactory;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;

import com.Functions.Excute;
import com.Others.OthersAdbAssist;
import com.Others.OthersFunctions;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;

public class OthersUImain extends JPanel {

	OthersFunctions othersfunctions=new OthersFunctions();
	OthersAdbAssist othersadbassist=new OthersAdbAssist();
	public JButton btnInputText;
	
	JTextArea textAreaInputTxt;
	JFormattedTextField formattedTextFieldInput;
	/**
	 * Create the panel.
	 */
	public OthersUImain() {
		setSize(700,650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel lblOthers = new JLabel(getString("lblOthers"));
		lblOthers.setVerticalAlignment(SwingConstants.TOP);
		lblOthers.setFont(new Font("微软雅黑", Font.BOLD, 26));
		lblOthers.setBounds(10, 145, 126, 35);
		add(lblOthers);
		
		
		//SPC
		JButton btnSpc = new JButton(getString("btnSpc"));
		btnSpc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sn = JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame, "Pls enter your phone SN:","Input",JOptionPane.INFORMATION_MESSAGE); 
				if(sn!=null){
					
				if(sn.equals("")){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Input null with spc");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls enter SN.\n It's 000000 after format flash!", 
							"Message", JOptionPane.ERROR_MESSAGE);
				}else{	    			  
	    			  Clipboard  clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    			  String spc=othersfunctions.getSPC(sn);
	    			  StringSelection stringSelection = new StringSelection(spc);
	    			  clipboard.setContents(stringSelection, null);
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "SN="+sn+"\n"+"SPC="+spc+"Press Ctrl+V to paste!", 
						"Message", JOptionPane.INFORMATION_MESSAGE);
				}
				}else{
					com.Main.ThenToolsRun.logger.log(Level.INFO,"cancel Input with spc");
				}
			}
		});
		btnSpc.setBounds(184, 199, 100, 25);
		add(btnSpc);
		
		//IMEI
		JButton btnCreateImei = new JButton(getString("btnCreateImei"));
		btnCreateImei.setToolTipText(getString("btnCreateImeitip"));
		btnCreateImei.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
  			  Clipboard  clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
  			  String imei=othersfunctions.createIMEI();
  			  StringSelection stringSelection = new StringSelection(imei.replace(" ", ""));
  			  clipboard.setContents(stringSelection, null);
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "New IMEI="+imei+"\nPress Ctrl+V to paste!", 
						"Message", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		btnCreateImei.setBounds(184, 245, 100, 25);
		add(btnCreateImei);
		
		//MAC
		JButton btnCreateMac = new JButton(getString("btnCreateMac"));
		btnCreateMac.setToolTipText(getString("btnCreateMactip"));
		btnCreateMac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	  			  Clipboard  clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	  			  String mac=othersfunctions.createMAC();
	  			  StringSelection stringSelection = new StringSelection(mac.replace(" ", ""));
	  			  clipboard.setContents(stringSelection, null);
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "New MAC="+mac+"\nPress Ctrl+V to paste!", 
						"Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnCreateMac.setBounds(55, 245, 100, 25);
		add(btnCreateMac);
		
		//install thenhelper
//		JButton btnInstallThenhelper = new JButton(getString("btnInstallThenhelper"));
//		btnInstallThenhelper.setToolTipText(getString("btnInstallThenhelpertip"));
//		btnInstallThenhelper.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				//installthenhelperthreadrun true
//				if(othersfunctions.getInstallthenhelperthreadrun()){
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"installthenhelperthreadrun =true");
//					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
//							"Message", JOptionPane.ERROR_MESSAGE); 
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"install thenhelper button running");
//					return;
//				}
//				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
//				othersfunctions.installThenHelper();
//			}
//		});
//		btnInstallThenhelper.setBounds(55, 261, 100, 25);
//		add(btnInstallThenhelper);
		//direct spc by ue
		JButton btngetspc = new JButton(getString("btngetspc"));
		btngetspc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(com.Main.ThenToolsRun.selectedID!=null){
					String[] result=othersfunctions.getSPC();
					if(result[0].equals("")){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"no sn with spc");
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "It's 000000 after format flash!", 
								"Message", JOptionPane.INFORMATION_MESSAGE);
					}else{
			  			  Clipboard  clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			  			  StringSelection stringSelection = new StringSelection(result[1]);
			  			  clipboard.setContents(stringSelection, null);
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Device="+com.Main.ThenToolsRun.selectedID+"\nSN="+result[0]+"\n"+"SPC="+result[1]+"Press Ctrl+V to paste!", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No device checked!", 
							"Message", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btngetspc.setBounds(55, 199, 100, 25);
		add(btngetspc);
		//button cmd
		JButton btncmd = new JButton(getString("btncmd"));
		btncmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				othersfunctions.Runcmd();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"press btncmd button");
			}
		});
		btncmd.setBounds(321, 200, 100, 25);
		add(btncmd);
		//button killadb
		JButton btnkilladb = new JButton(getString("btnkilladb"));
		btnkilladb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				othersfunctions.KillADB();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"press killadb button");
			}
		});
		btnkilladb.setBounds(321, 246, 100, 25);
		add(btnkilladb);
		
		//ADB assister lbl		
		JLabel lblAdb = new JLabel(getString("lblAdb"));
		lblAdb.setBounds(10, 341, 126, 15);
		add(lblAdb);

		//start hicamconfig button
		JButton btnStartHicamconfig = new JButton(getString("btnStartHicamconfig"));
		btnStartHicamconfig.setToolTipText(getString("btnStartHicamconfigtip"));
		btnStartHicamconfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "no devices!", 
							"Message", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!com.Main.ThenToolsRun.selectedID.equals("Iris4G")){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Only support Iris4G!", 
							"Message", JOptionPane.ERROR_MESSAGE);
					return;
				}
				othersadbassist.Starthicamconfig();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"press btnStartHicamconfig button");
			}
		});
		btnStartHicamconfig.setBounds(55, 397, 100, 25);
		add(btnStartHicamconfig);
//		//input Txt 
//		textAreaInputTxt = new JTextArea("");
//		textAreaInputTxt.setWrapStyleWord(true);
//		textAreaInputTxt.setLineWrap(true);
//		textAreaInputTxt.setBorder(new LineBorder(new Color(0, 0, 0)));
//		JScrollPane scrollPaneInputTxt=new JScrollPane(textAreaInputTxt);
//		scrollPaneInputTxt.setBounds(55, 366, 148, 69);
//		add(scrollPaneInputTxt);
//		//input button
//		JButton btnInputTxt = new JButton(getString("btnInputTxt"));
//		btnInputTxt.setToolTipText(getString("btnInputTxttip"));
//		btnInputTxt.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(othersadbassist.getInputtxtrunnable()){
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"getInputtxtrunnable =true");
//					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
//							"Message", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				othersadbassist.InputTxt(textAreaInputTxt.getText());
//				com.Main.ThenToolsRun.logger.log(Level.INFO,"press btnInputTxt button");
//			}
//		});
//		btnInputTxt.setBounds(220, 386, 100, 25);
//		add(btnInputTxt);
		//input button 2 by enter
		formattedTextFieldInput = new JFormattedTextField();
		formattedTextFieldInput.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e)
		    {
		     if (e.getKeyChar() == e.VK_ENTER){ 
					if(othersadbassist.getInputtxtrunnable()){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"getInputtxtrunnable =true");
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
								"Message", JOptionPane.ERROR_MESSAGE);
						return;
					}
		    	 	othersadbassist.InputTxtbyEnter(formattedTextFieldInput.getText());
		    	 }else if(e.getKeyChar()==e.VK_ESCAPE){
		    		 formattedTextFieldInput.setText("");
		    	 }
		    }
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		formattedTextFieldInput.setBounds(55, 366, 163, 21);
		add(formattedTextFieldInput);
		
		btnInputText = new JButton(getString("btnInputText"));
		btnInputText.setToolTipText(getString("btnInputTexttip"));
		btnInputText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(othersadbassist.getInputtxtrunnable()){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"getInputtxtrunnable =true");
				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
						"Message", JOptionPane.ERROR_MESSAGE);
				return;
			}
				othersadbassist.InputTxtbyEnter(formattedTextFieldInput.getText());
				com.Main.ThenToolsRun.logger.log(Level.INFO,"press btnInputText button");
			}
		});
		btnInputText.setBounds(228, 364, 100, 25);
		add(btnInputText);
	}
	
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblOthers": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "更多功能:";
		}else{
			return "Others:";
		}
		case "btnSpc": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "计算SPC";
		}else{
			return "Get SPC";
		}
		case "btnCreateImei": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "生成IMEI";
		}else{
			return "New IMEI";
		}
		case "btnCreateImeitip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "生成合法的IMEI";
		}else{
			return "create vaild IMEI";
		}
		case "btnCreateMac": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "生成MAC";
		}else{
			return "New MAC";
		}
		case "btnCreateMactip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "生成合法的MAC";
		}else{
			return "create vaild MAC";
		}
		case "btnInstallThenhelper": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ThenHelper";
		}else{
			return "ThenHelper";
		}
		case "btnInstallThenhelpertip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "安装功能强大的ThenHelper.apk";
		}else{
			return "install ThenHelper.apk";
		}
		case "btngetspc": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "本机SPC";
		}else{
			return "UE SPC";
		}
		case "btncmd": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "CMD";
		}else{
			return "CMD";
		}
		case "btnkilladb": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "重启5037";
		}else{
			return "Restart5037";
		}
		case "btnStartHicamconfig": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "camconfig";
		}else{
			return "camconfig";
		}
		case "btnStartHicamconfigtip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "启动hicamconfig配置";
		}else{
			return "show UI of HicamConfig";
		}
		case "lblAdb": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "Adb辅助:";
		}else{
			return "Adb Assiter:";
		}
		case "btnInputTxt": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "输入";
		}else{
			return "Input";
		}
		case "btnInputTxttip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "仅支持中文";
		}else{
			return "Only support english words";
		}
		case "btnInputText": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "输入";
		}else{
			return "Input";
		}
		case "btnInputTexttip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "按回车键输入,按ESC键清空";
		}else{
			return "Press enter key to input and esc key to clear";
		}
			default: return "";
		}
	}
}
