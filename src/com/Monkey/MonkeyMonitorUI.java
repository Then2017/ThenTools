package com.Monkey;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.Functions.CheckUE;
import com.Functions.LineNumberHeaderView;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MonkeyMonitorUI extends JFrame {

	private JPanel contentPane;
	JTextArea textAreaShowlog;
	JScrollPane scrollPaneTxt;
	MonkeyMonitor monkeymonitor=new MonkeyMonitor();
	MonkeyStop monkeystop=new MonkeyStop();
	//MonkeyAnalysisFile monkeyanalysisfile=new MonkeyAnalysisFile();
	MonkeyAnalysisTEMP monkeyanalysis=new MonkeyAnalysisTEMP();
	MonkeyMonitorSettingsUI monkeymonitorsetUI=new MonkeyMonitorSettingsUI();
	MonkeylogUI monkeylogUI= new MonkeylogUI();
	JLabel lblLogpath;
	private JButton btnAnalysis;
	String logfilepath;
	private JLabel lblSelect;
	private JButton btnGetLogs;
	
	boolean isstart=false;
	JButton btnActive;
	
	/**
	 * Create the frame.
	 */
	public MonkeyMonitorUI() {
		setBounds(100, 100, 750, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setResizable(false);
		setTitle(getString("title"));
		setContentPane(contentPane);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		
		//button active
		btnActive = new JButton(getString("btnActive"));
		btnActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!isstart){
				//device null
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(contentPane, "No devices checked", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor active button: no devices");
					return;
				}
				//running
				if(monkeymonitor.getActiveMonkeythreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"getActiveMonkeythreadrun =true");
					JOptionPane.showMessageDialog(contentPane, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor active button: running");
					return;
				}
				if(com.Main.MainUI.MonkeyUImain.getMonkeyInfo()[3]==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls select packages to right box", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor active button");
					return;
				}
				if(CheckUE.checkMonkeyrun()){
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Monkey Monitor is running!", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Monkey Monitor is running!");
					return;
				}
				//check sim
				if(CheckUE.checkSIMstatus()){
					int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "UE has SIM cards! Do you want to continue? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"UE has sim and continue");
					}else{
						com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
						com.Main.ThenToolsRun.logger.log(Level.INFO,"UE has sim and not to continue");
						return;
					}
				}
				//check log
				if(!CheckUE.getlogstatus().equals("<font color=\"#FF0000\">Running!</font>")){
					int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "No logs running! Do you want to continue? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"UE has no log and continue");
					}else{
						com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
						com.Main.ThenToolsRun.logger.log(Level.INFO,"UE has no log and not to continue");
						return;
					}
				}
				monkeymonitor.run(com.Main.MainUI.MonkeyUImain.getMonkeyInfo(),monkeymonitorsetUI.getIsreconnect());
				//change 
				isstart=true;
				btnActive.setText(getString("btnStop"));
				btnActive.setForeground(Color.RED);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor active button");
				}else{
					int confirm=JOptionPane.showConfirmDialog(contentPane, "Do you want to stop Monkey? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						monkeystop.run();
						monkeymonitor.cancelbatterytimer();
						monkeymonitor.setTerminated(true);
						//change 
						isstart=false;
						btnActive.setText(getString("btnActive"));
						btnActive.setForeground(Color.BLACK);
					}else{
						com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor stop button: no");
						return;
					}
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor stop button: yes");
				}
			}
		});
		btnActive.setBounds(634, 457, 100, 25);
		contentPane.add(btnActive);
		//button stop
//		JButton btnStop = new JButton(getString("btnStop"));
//		btnStop.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				//device null
//				if(com.Main.ThenToolsRun.selectedID==null){
//					JOptionPane.showMessageDialog(contentPane, "No devices checked", 
//							"Message", JOptionPane.ERROR_MESSAGE); 
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor stop button: no devices");
//					return;
//				}
//				int confirm=JOptionPane.showConfirmDialog(contentPane, "Do you want to stop Monkey? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
//				if(confirm==0){
//					monkeystop.run();
//					monkeymonitor.cancelbatterytimer();
//				}else{
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor stop button: no");
//					return;
//				}
//				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor stop button: yes");
//			}
//		});
//		btnStop.setBounds(634, 438, 100, 25);
//		contentPane.add(btnStop);
		
		
		//textAreaShowlog
		textAreaShowlog = new JTextArea("");
		textAreaShowlog.setWrapStyleWord(true);
		textAreaShowlog.setLineWrap(true);
		textAreaShowlog.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPaneTxt=new JScrollPane(textAreaShowlog);
		scrollPaneTxt.setBounds(10, 35, 724, 358);
		contentPane.add(scrollPaneTxt);
		//行号
		LineNumberHeaderView lineNumberHeader = new LineNumberHeaderView();
		scrollPaneTxt.setRowHeaderView(lineNumberHeader);
		//log path lable
		lblLogpath = new JLabel("---");
		lblLogpath.setBounds(71, 10, 612, 15);
		contentPane.add(lblLogpath);
		
		//Analysis button
		btnAnalysis = new JButton(getString("btnAnalysis"));
		btnAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//no selected
				if(lblSelect.getText().equals("---")){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"no selected file to analysis");
					JOptionPane.showMessageDialog(contentPane, "Pls select a file to analysis...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor analysis button: no file selected");
					return;
				}
				//running
				if(monkeyanalysis.getmonkeyanalysisthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"getmonkeyanalysisthreadrun =true");
					JOptionPane.showMessageDialog(contentPane, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor analysis button: running");
					return;
				}else{
					monkeyanalysis.run(monkeymonitorsetUI.getArow(),monkeymonitorsetUI.getArowword(),monkeymonitorsetUI.getShowduplicate());
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor analysis button");
			}
		});
		btnAnalysis.setBounds(53, 492, 100, 25);
		contentPane.add(btnAnalysis);
		//lbl select string
		lblSelect = new JLabel("---");
		lblSelect.setBounds(71, 403, 519, 44);
		contentPane.add(lblSelect);
		
		//analysis Settings
		JButton btnSettings = new JButton(getString("btnSettings"));
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monkeymonitorsetUI.initvalue();
				monkeymonitorsetUI.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor settings button");
			}
		});
		btnSettings.setBounds(176, 492, 100, 25);
		contentPane.add(btnSettings);
		//get logs
		btnGetLogs = new JButton(getString("btnGetLogs"));
		btnGetLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monkeylogUI.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Monkey monitor get button");
			}
		});
		btnGetLogs.setBounds(634, 492, 100, 25);
		contentPane.add(btnGetLogs);
		//select
		JButton btnSelect = new JButton(getString("btnSelect"));
		btnSelect.setToolTipText(getString("btnSelecttip"));
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String latestlog=lblLogpath.getText();
				if(latestlog.length()>4){
					latestlog=latestlog.substring(3,latestlog.length());
				}else{
					latestlog="";
				}
				File[] selectfile=monkeyanalysis.selectfile(latestlog);
				if(selectfile!=null){
				if(selectfile.length>1){
				StringBuffer showfile=new StringBuffer();
				showfile.append(selectfile[0].getParent()+"/<font color=\"#FF0000\" size=10>"+selectfile.length+"</font> files selected");
				lblSelect.setText("<html>"+showfile.toString()+"</html>");
				}else if(selectfile.length==1){
					lblSelect.setText(selectfile[0].getAbsolutePath());
				}
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor select folder button");
			}
		});
		btnSelect.setBounds(53, 457, 100, 25);
		contentPane.add(btnSelect);
		//lbl filpath
		JLabel lblLogFilePath = new JLabel(getString("lblLogFilePath"));
		lblLogFilePath.setBounds(10, 10, 91, 15);
		contentPane.add(lblLogFilePath);
		//lbl select files
		JLabel lblSelectFiles = new JLabel(getString("lblSelectFiles"));
		lblSelectFiles.setBounds(10, 418, 87, 15);
		contentPane.add(lblSelectFiles);
		
		//notes
//		JLabel lblNotes = new JLabel(getString("lblNotes"));
//		lblNotes.setBounds(580, 497, 164, 20);
//		contentPane.add(lblNotes);
		
		//battery button
		JButton btnBattery = new JButton(getString("btnBattery"));
		btnBattery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str=monkeymonitor.getstrbattery();
				if(str!=null&&!str.equals("")){
					int count=0;
					String[] splitstr=str.split("\n");
					for(String line :splitstr){
						if(!line.contains("not run")){
							count++;
						}
					}
					double time=count*0.5;
				JOptionPane.showMessageDialog(contentPane,"Monkey total runs about "+time+" hours.\n"+monkeymonitor.getstrbattery(), 
						"Message", JOptionPane.INFORMATION_MESSAGE); 
				}else{
					JOptionPane.showMessageDialog(contentPane,"Pls active first!", 
							"Message", JOptionPane.INFORMATION_MESSAGE); 
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor battery button");	
			}
		});
		btnBattery.setBounds(176, 457, 100, 25);
		contentPane.add(btnBattery);
	}
	
	//get textAreaShowlog
	public JTextArea gettextAreaShowlog(){
		return textAreaShowlog;
	}
	//get lable log path
	public JLabel getlblLogpath(){
		return lblLogpath;
	}
	//get lable select 
	public JLabel getlblSelect(){
		return lblSelect;
	}
	
	//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "Monkey监控:";
		}else{
			return "Monkey Monitor";
		}
		case "btnActive": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "激活";
		}else{
			return "Active";
		}
		case "btnStop": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "停止";
		}else{
			return "Stop";
		}
		case "btnAnalysis": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "分析";
		}else{
			return "Analysis";
		}
		case "btnSettings": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "设置";
		}else{
			return "Settings";
		}
		case "btnGetLogs": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "获取Log";
		}else{
			return "Get Logs";
		}
		case "btnSelect": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "选择文件";
		}else{
			return "Select";
		}
		case "btnSelecttip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "可以选择多个文件一起分析";
		}else{
			return "Can select serveral files";
		}
		case "lblLogFilePath": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "Log路径:";
		}else{
			return "Log File Path:";
		}
		case "lblSelectFiles": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "分析文件:";
		}else{
			return "Select files:";
		}
		case "lblNotes": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拔掉usb后监控会停止!</html>";
		}else{
			return "Monitor stops after pluging out usb";
		}
		case "btnBattery": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "查看电量";
		}else{
			return "Check Battery";
		}
			default: return "";
		}	
	}
}
