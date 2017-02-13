package com.Monkey;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import com.Functions.CheckUE;
import com.Main.ThenToolsRun;
import com.Qcom.QcomADBRoot;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

public class MonkeylogUI extends JFrame {
	MonkeyGet monkeyget=new MonkeyGet();
	private JPanel contentPane;
	JCheckBox chckbxRoot;
	JCheckBox chckbxCompression;
	boolean isroot=false;
	boolean iscompression=true;
	/**
	 * Create the frame.
	 */
	public MonkeylogUI() {
		setResizable(false);
		setTitle(getString("title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 303, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		
		chckbxCompression = new JCheckBox(getString("chckbxCompression"));
		chckbxCompression.setSelected(true);
		chckbxCompression.setBounds(6, 16, 103, 23);
		chckbxCompression.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					if(chckbxCompression.isSelected()){
						iscompression=true;
					}else{
						iscompression=false;
					}
			}
		}); 
		contentPane.add(chckbxCompression);
		
		chckbxRoot = new JCheckBox("Root");
		chckbxRoot.setBounds(118, 16, 103, 23);
		chckbxRoot.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					if(chckbxRoot.isSelected()){
						isroot=true;
					}else{
						isroot=false;
					}
			}
		}); 
		contentPane.add(chckbxRoot);
		
		JLabel lbltheLogDirectory = new JLabel(getString("lbltheLogDirectory"));
		lbltheLogDirectory.setVerticalAlignment(SwingConstants.TOP);
		lbltheLogDirectory.setBounds(6, 105, 236, 36);
		contentPane.add(lbltheLogDirectory);
		
		//OK
		final QcomADBRoot qcomadbroot=new QcomADBRoot();
		
		JButton buttonOK = new JButton(getString("buttonOK"));
		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//device null
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(contentPane, "No devices checked", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey stop button: no devices");
					return;
				}

				//check monkey run
				if(CheckUE.checkMonkeyrun()){
					//stop first then get log
					int confirm=JOptionPane.showConfirmDialog(contentPane, "Do you want to stop monkey first?","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"stop monkey first");
						monkeyget.Stop();
					}else{
						com.Main.ThenToolsRun.logger.log(Level.INFO,"not stop monkey first");
					}
				}
				
				//root
				if(isroot){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"start root in monkeylogUI");
					if(com.Main.ThenToolsRun.platform.contains("MT")){
						
					}else{
						qcomadbroot.main();
					}
					chckbxRoot.setSelected(false);
					return;
				}
				//get log 
				if(monkeyget.filepathexist()){
					int confirm=JOptionPane.showConfirmDialog(contentPane, monkeyget.getMainlog()+"     is exist!\n"+
							"whether to del this folder to get log ? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"del exist monkey floder and get log");
					}else{
						com.Main.ThenToolsRun.logger.log(Level.INFO,"not del exist monkey floder");
						return;
					}
				};
				if(monkeyget.getGetlogthreadrun()){
					JOptionPane.showMessageDialog(contentPane, "ThenTools is getting logs from device : "+com.Main.ThenToolsRun.selectedID, 
							"Messge", JOptionPane.ERROR_MESSAGE); 
				}else{
					//开启抓取log
					monkeyget.run(iscompression);
					dispose();
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkeylogUI OK dispose");
			}
		});
		buttonOK.setBounds(77, 151, 100, 25);
		contentPane.add(buttonOK);
		
		//Cancel
		JButton buttonCancel = new JButton(getString("buttonCancel"));
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkeylogUI Cancel dispose");
			}
		});
		buttonCancel.setBounds(187, 151, 100, 25);
		contentPane.add(buttonCancel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);

	}
	//Language 
		public String getString(String flag){
			switch(flag){
			case "title": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "请设置:";
			}else{
				return "Pls set :";
			}
			case "chckbxCompression": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "7-Zip压缩";
			}else{
				return "7-Zip";
			}
			case "lbltheLogDirectory": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "<html>Log存储目录 :<br>桌面/ThenLog/Monkey/PCtime</html>";
			}else{
				return "<html>The log directory :<br>%Desktop%/ThenLog/Monkey/PCtime</html>";
			}
			case "buttonOK": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "提取";
			}else{
				return "Get";
			}
			case "buttonCancel": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "取消";
			}else{
				return "Cancel";
			}
				default: return "";
			}	
		}
}
