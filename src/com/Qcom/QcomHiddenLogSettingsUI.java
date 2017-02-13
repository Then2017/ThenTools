package com.Qcom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class QcomHiddenLogSettingsUI extends JFrame {

	private JPanel contentPane;
	private JButton btnOk;
	private JButton btnCancel;
	private JButton btnReset;
	private JCheckBox chckbxOpendebug;
	JCheckBox chckbxOpen;
	boolean hiddenlog;
	boolean hiddenlogdebug;
	boolean autoroot;
	JTextArea textAreaLogSettings;
 	QcomHiddenLogSettings qcomHiddenLogSettings=new QcomHiddenLogSettings();
 	JFormattedTextField formattedTextFieldWaittime;
 	private JCheckBox chckbxRoot;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QcomHiddenLogSettingsUI frame = new QcomHiddenLogSettingsUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public QcomHiddenLogSettingsUI() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setResizable(false);
		setTitle(getString("title"));
		setContentPane(contentPane);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		
		textAreaLogSettings = new JTextArea(qcomHiddenLogSettings.getLoginfo());
		textAreaLogSettings.setWrapStyleWord(true);
		textAreaLogSettings.setLineWrap(true);
		textAreaLogSettings.setBorder(new LineBorder(new Color(0, 0, 0)));
		JScrollPane scrollPaneLogSettings = new JScrollPane(textAreaLogSettings);
		scrollPaneLogSettings.setBounds(21, 108, 400, 119);
		contentPane.add(scrollPaneLogSettings);
		
		hiddenlog=qcomHiddenLogSettings.getIsopen();
		hiddenlogdebug=qcomHiddenLogSettings.getIsopendebug();
		autoroot=qcomHiddenLogSettings.getIsroot();
		//ok button
				btnOk = new JButton(getString("btnOk"));
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String[] tempsplit=textAreaLogSettings.getText().split("\n");
						for(String str:tempsplit){
							if(!str.endsWith(";")){
								JOptionPane.showMessageDialog(contentPane, "Log Settings has some problems, pls check!", 
										"Message", JOptionPane.ERROR_MESSAGE); 
								return;
							}
						}
						if(formattedTextFieldWaittime.getText().equals("")||Integer.parseInt(formattedTextFieldWaittime.getText())<15000){
							JOptionPane.showMessageDialog(contentPane, "Waittime must be above 15000ms", 
									"Message", JOptionPane.ERROR_MESSAGE); 
							return;
						}
						if(hiddenlog){
							qcomHiddenLogSettings.setIsopen("true");	
						}else{
							qcomHiddenLogSettings.setIsopen("false");	
						}
						if(hiddenlogdebug){
							qcomHiddenLogSettings.setIsopendebug("true");	
						}else{
							qcomHiddenLogSettings.setIsopendebug("false");	
						}
						if(autoroot){
							qcomHiddenLogSettings.setIsroot("true");	
						}else{
							qcomHiddenLogSettings.setIsroot("false");	
						}
						qcomHiddenLogSettings.setWaittime(formattedTextFieldWaittime.getText());
						qcomHiddenLogSettings.setLoginfo(textAreaLogSettings.getText());
						dispose();
						com.Main.ThenToolsRun.logger.log(Level.INFO,"hidden log settring ok button");
					}
				});
				btnOk.setBounds(227, 237, 100, 25);
				contentPane.add(btnOk);
				//cancel button
				btnCancel = new JButton(getString("btnCancel"));
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						com.Main.ThenToolsRun.logger.log(Level.INFO,"hidden log settring cancel button");
					}
				});
				btnCancel.setBounds(334, 237, 100, 25);
				contentPane.add(btnCancel);
				//reset button
				btnReset = new JButton(getString("btnReset"));
				btnReset.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						qcomHiddenLogSettings.setIsopen("true");	
						qcomHiddenLogSettings.setLoginfo("setprop log.tag.Telecom V;\n"
								+ "setprop log.tag.InCall V;\n"
								+ "setprop log.tag.ContactsPref V;\n"
								+ "setprop log.tag.Telephony V;\n"
								+ "setprop log.tag.Mms V;\n");
						qcomHiddenLogSettings.setWaittime(20000+"");
						
						chckbxOpendebug.setSelected(true);
						chckbxOpen.setSelected(false);
						chckbxRoot.setSelected(true);
						formattedTextFieldWaittime.setText(20000+"");
						textAreaLogSettings.setText(qcomHiddenLogSettings.getLoginfo());
						com.Main.ThenToolsRun.logger.log(Level.INFO,"hidden log settring reset button");
					}
				});
				btnReset.setBounds(100, 237, 100, 25);
				contentPane.add(btnReset);
				//auto root
				chckbxRoot = new JCheckBox(getString("chckbxRoot"));
				if(qcomHiddenLogSettings.getIsroot()){
					chckbxRoot.setSelected(true);
				}else{
					chckbxRoot.setSelected(false);
				}
				chckbxRoot.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
							if(chckbxRoot.isSelected()){
								autoroot=true;
							}else{
								autoroot=false;
							}
							com.Main.ThenToolsRun.logger.log(Level.INFO,"set auto="+autoroot);
					}
				});
				chckbxRoot.setBounds(21, 35, 223, 23);
				contentPane.add(chckbxRoot);
				
				//checkbox open hidden log
				chckbxOpen = new JCheckBox(getString("chckbxOpen"));
				if(qcomHiddenLogSettings.getIsopen()){
					chckbxOpen.setSelected(true);
				}else{
					chckbxOpen.setSelected(false);
				}
				chckbxOpen.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
							if(chckbxOpen.isSelected()){
								hiddenlog=true;
							}else{
								hiddenlog=false;
							}
							com.Main.ThenToolsRun.logger.log(Level.INFO,"set isreconnect="+hiddenlog);
					}
				}); 
				chckbxOpen.setBounds(214, 10, 148, 23);
				contentPane.add(chckbxOpen);
				//open hidden log
				chckbxOpendebug = new JCheckBox(getString("chckbxOpendebug"));
				if(qcomHiddenLogSettings.getIsopendebug()){
					chckbxOpendebug.setSelected(true);
				}else{
					chckbxOpendebug.setSelected(false);
				}
				chckbxOpendebug.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
							if(chckbxOpendebug.isSelected()){
								hiddenlogdebug=true;
							}else{
								hiddenlogdebug=false;
							}
							com.Main.ThenToolsRun.logger.log(Level.INFO,"set debug isreconnect="+hiddenlogdebug);
					}
				}); 
				chckbxOpendebug.setBounds(21, 10, 154, 23);
				contentPane.add(chckbxOpendebug);
				//log txt
				formattedTextFieldWaittime = new JFormattedTextField();
				formattedTextFieldWaittime.setText(qcomHiddenLogSettings.getWaittime()+"");
				formattedTextFieldWaittime.addKeyListener(new KeyListener(){
					@Override
					public void keyTyped(KeyEvent e)
				    {
				     if ((e.getKeyChar() >= e.VK_0 && e.getKeyChar() <= e.VK_9) 
				      || e.getKeyChar() == e.VK_ENTER || e.getKeyChar() == e.VK_TAB
				      || e.getKeyChar() == e.VK_BACK_SPACE || e.getKeyChar() == e.VK_DELETE 
				      || e.getKeyChar() == e.VK_LEFT || e.getKeyChar() == e.VK_RIGHT 
				      || e.getKeyChar() == e.VK_ESCAPE){  return;   }else{e.consume();}
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
				formattedTextFieldWaittime.setBounds(245, 55, 50, 21);
				contentPane.add(formattedTextFieldWaittime);
				//lbl waittime
				JLabel lblWaittime = new JLabel(getString("lblWaittime"));
				lblWaittime.setBounds(21, 58, 214, 15);
				contentPane.add(lblWaittime);
				//lbl loginfo
				JLabel lblLoginfo = new JLabel(getString("lblLoginfo"));
				lblLoginfo.setBounds(21, 83, 214, 15);
				contentPane.add(lblLoginfo);



	}
	
	public QcomHiddenLogSettings getQcomHiddenLogSettings(){
		return qcomHiddenLogSettings;
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "隐藏Log设置:";
		}else{
			return "Hidden Log Settings";
		}
		case "btnCancel": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "取消";
		}else{
			return "Cancel";
		}
		case "btnOk": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "确定";
		}else{
			return "OK";
		}
		case "btnReset": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "恢复默认";
		}else{
			return "Reset";
		}
		case "chckbxOpendebug": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "Userdebug时开启";
		}else{
			return "Open when Userdebug";
		}
		case "chckbxOpen": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "User时开启";
		}else{
			return "Open when user";
		}
		case "lblWaittime": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "Adb重启后激活Log等待时间(毫秒):";
		}else{
			return "Active time after adb restart(ms):";
		}
		case "lblLoginfo": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "Log设置:";
		}else{
			return "Log Settings:";
		}
		case "chckbxRoot": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "自动 root(必须root才能开启)";
		}else{
			return "auto root(must root to open)";
		}
			default: return "";
		}	
	}
}
