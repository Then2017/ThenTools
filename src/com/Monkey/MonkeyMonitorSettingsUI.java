package com.Monkey;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class MonkeyMonitorSettingsUI extends JFrame {

	private JPanel contentPane;
	JCheckBox chckbxFillter;//显示过滤重复
	boolean showduplicate;
	private JLabel lblViewRow;
	private JFormattedTextField formattedTextFieldViewrow;
	private JLabel lblAnalysisRow;
	private JFormattedTextField formattedTextFieldAnalysiswords;
	private JLabel lblShowDuplicateIssue;
	private JButton btnOk;
	private JButton btnCancel;
	int arow,arowword;
	MonkeyMonitorSettings monkeymonitorsettings=new MonkeyMonitorSettings();
	private JButton btnReset;
	JCheckBox chckbxReconnect;
	boolean isreconnect;
	/**
	 * Create the frame.
	 */
	public MonkeyMonitorSettingsUI() {
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
		
		JLabel lblAnalysisSettings = new JLabel(getString("lblAnalysisSettings"));
		lblAnalysisSettings.setBounds(10, 10, 128, 15);
		contentPane.add(lblAnalysisSettings);
		//show duplicate checkbox
		chckbxFillter = new JCheckBox("");
		//get showduplicate value
		if(showduplicate){
			chckbxFillter.setSelected(true);
		}else{
			chckbxFillter.setSelected(false);
		}
		chckbxFillter.setBounds(208, 35, 50, 15);
		chckbxFillter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					if(chckbxFillter.isSelected()){
						showduplicate=true;
					}else{
						showduplicate=false;
					}
					com.Main.ThenToolsRun.logger.log(Level.INFO,"set showduplicate="+showduplicate);
			}
		}); 
		contentPane.add(chckbxFillter);
		
		lblViewRow = new JLabel(getString("lblViewRow"));
		lblViewRow.setBounds(25, 65, 173, 15);
		contentPane.add(lblViewRow);
		//view and analysis row value
		formattedTextFieldViewrow = new JFormattedTextField();
		//get arow value
		formattedTextFieldViewrow.setText(arow+"");
		formattedTextFieldViewrow.addKeyListener(new KeyListener(){
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
		formattedTextFieldViewrow.setBounds(208, 62, 50, 21);
		contentPane.add(formattedTextFieldViewrow);
		
		lblAnalysisRow = new JLabel(getString("lblAnalysisRow"));
		lblAnalysisRow.setBounds(25, 91, 173, 15);
		contentPane.add(lblAnalysisRow);
		//analysis words per line value
		formattedTextFieldAnalysiswords = new JFormattedTextField();
		//get arowword value
		formattedTextFieldAnalysiswords.setText(arowword+"");
		formattedTextFieldAnalysiswords.addKeyListener(new KeyListener(){
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
		formattedTextFieldAnalysiswords.setBounds(208, 88, 50, 21);
		contentPane.add(formattedTextFieldAnalysiswords);
		
		lblShowDuplicateIssue = new JLabel(getString("lblShowDuplicateIssue"));
		lblShowDuplicateIssue.setBounds(25, 35, 173, 15);
		contentPane.add(lblShowDuplicateIssue);
		//ok button
		btnOk = new JButton(getString("btnOk"));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temparow=Integer.parseInt(formattedTextFieldViewrow.getText());
				int temparowword=Integer.parseInt(formattedTextFieldAnalysiswords.getText());
				if(temparow<50&&temparow>0){
					arow=temparow;
					monkeymonitorsettings.setArow(temparow+"");
					com.Main.ThenToolsRun.logger.log(Level.INFO,"set arow="+arow);
				}else{
					JOptionPane.showMessageDialog(contentPane, "View and analysis row must be between 1 and 50", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					return;
				}
				if(temparowword>0&&temparowword<500){
					arowword=temparowword;
					monkeymonitorsettings.setArowword(temparowword+"");
					com.Main.ThenToolsRun.logger.log(Level.INFO,"set arowword="+arowword);
				}else{
					JOptionPane.showMessageDialog(contentPane, "Analysis of words per row must be between 1 and 500", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					return;
				}
				if(showduplicate){
					monkeymonitorsettings.setShowDuplicate("true");
				}else{
					monkeymonitorsettings.setShowDuplicate("false");
				}
				if(isreconnect){
					monkeymonitorsettings.setIsreconnect("true");
				}else{
					monkeymonitorsettings.setIsreconnect("false");
				}
				dispose();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor settring ok button");
			}
		});
		btnOk.setBounds(227, 218, 100, 25);
		contentPane.add(btnOk);
		//cancel button
		btnCancel = new JButton(getString("btnCancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor settring cancel button");
			}
		});
		btnCancel.setBounds(337, 218, 100, 25);
		contentPane.add(btnCancel);
		//reset button
		btnReset = new JButton(getString("btnReset"));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monkeymonitorsettings.setArow(15+"");
				monkeymonitorsettings.setArowword(""+80);
				monkeymonitorsettings.setShowDuplicate("false");
				monkeymonitorsettings.setIsreconnect("true");
				initvalue();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor settring reset button");
			}
		});
		btnReset.setBounds(104, 219, 100, 25);
		contentPane.add(btnReset);
		//Global setting
		JLabel lblSettings = new JLabel(getString("lblSettings"));
		lblSettings.setBounds(10, 136, 144, 15);
		contentPane.add(lblSettings);
		//reconnect check box
		chckbxReconnect = new JCheckBox(getString("chckbxReconnect"));
		if(isreconnect){
			chckbxReconnect.setSelected(true);
		}else{
			chckbxReconnect.setSelected(false);
		}
		chckbxReconnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					if(chckbxReconnect.isSelected()){
						isreconnect=true;
					}else{
						isreconnect=false;
					}
					com.Main.ThenToolsRun.logger.log(Level.INFO,"set isreconnect="+isreconnect);
			}
		}); 
		chckbxReconnect.setBounds(25, 157, 190, 23);
		contentPane.add(chckbxReconnect);
		
		//init value
		initvalue();
	}
	//init 
	public void initvalue(){
		arow=monkeymonitorsettings.getArow();
		arowword=monkeymonitorsettings.getArowword();
		showduplicate=monkeymonitorsettings.getShowDuplicate();
		isreconnect=monkeymonitorsettings.getIsreconnect();
		chckbxReconnect.setSelected(isreconnect);
		chckbxFillter.setSelected(showduplicate);
		formattedTextFieldViewrow.setText(arow+"");
		formattedTextFieldAnalysiswords.setText(arowword+"");
		com.Main.ThenToolsRun.logger.log(Level.INFO,"get arow="+arow+" arowword="+arowword+" showduplicate="+showduplicate+" Isreconnect="+isreconnect);
		
	}
	//get arow
	public int getArow(){
		return arow;
	}
	//get arowword
	public int getArowword(){
		return arowword;
	}
	//get showduplicate
	public boolean getShowduplicate(){
		return showduplicate;
	}
	//get isreconnect
	public boolean getIsreconnect(){
		return isreconnect;
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "Monkey监控设置:";
		}else{
			return "Monkey Monitor Settings";
		}
		case "lblAnalysisSettings": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "分析设置:";
		}else{
			return "Analysis Settings:";
		}
		case "lblViewRow": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "分析和显示行数:";
		}else{
			return "View and analysis row:";
		}
		case "lblAnalysisRow": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "每行分析字数:";
		}else{
			return "Analysis of words per row:";
		}
		case "lblShowDuplicateIssue": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "显示重复的issue:";
		}else{
			return "Show duplicate issue:";
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
		case "lblSettings": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "全局设置:";
		}else{
			return "Global settings:";
		}
		case "chckbxReconnect": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "断线重连";
		}else{
			return "Reconnect after offline";
		}
			default: return "";
		}	
	}
}
