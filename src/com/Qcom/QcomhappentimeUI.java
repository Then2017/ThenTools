package com.Qcom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;

import com.Functions.Excute;
import com.Main.ThenToolsRun;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Font;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class QcomhappentimeUI extends JFrame {

	private JPanel contentPane;
	String timeornone="None";
	String Mon,Day,Hour,Min,happentime;
	boolean isroot=false;
	boolean iscompression=true;
	QcomGet qcomGet=new QcomGet();
	private SimpleDateFormat sDateFormat = new SimpleDateFormat("MMddHHmm");
	File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
	String desktopPath = desktopDir.getAbsolutePath();
	
	JRadioButton rdbtnNone;
	JRadioButton rdbtnTime;
	
	JComboBox comboBoxMon;
	JComboBox comboBoxDay;
	JComboBox comboBoxHour;
	JComboBox comboBoxMin;
	JLabel lblFolderPath;
	JCheckBox chckbxCompression;
	JCheckBox chckbxRoot;
	/**
	 * Create the frame.
	 */
	public QcomhappentimeUI() {
		setResizable(false);
		setTitle(getString("title"));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 433, 295);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		
		//Cancel
		JButton btnCancel = new JButton(getString("btnCancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"happentimeUI Cancel dispose");
			}
		});
		btnCancel.setBounds(312, 222, 100, 25);
		contentPane.add(btnCancel);
		
		//OK
		final QcomADBRoot qcomadbroot=new QcomADBRoot();
		
		JButton btnOK = new JButton(getString("btnOK"));
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//root
				if(isroot){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"start root in happentimeUI");
					qcomadbroot.main();
					chckbxRoot.setSelected(false);
					return;
				}
				
				//None time
				if(timeornone.equals("None")){
					int confirm=JOptionPane.showConfirmDialog(contentPane,"whether to get log with nonetime ? ",
							"Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"continue to get log with nonetime");
					}else{
						com.Main.ThenToolsRun.logger.log(Level.INFO,"not continue to get log with nonetime");
						return;
					}
				}
				
				happentime=Mon+Day+"_"+Hour+"H"+Min+"M";
				if(qcomGet.filepathexist(timeornone, happentime)){
					int confirm=JOptionPane.showConfirmDialog(contentPane, qcomGet.getMainlog()+"     is exist!\n"+
							"whether to del this folder to get log ? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"del exist hapentime floder and get log");
					}else{
						com.Main.ThenToolsRun.logger.log(Level.INFO,"not del exist hapentime floder");
						return;
					}
				};
				if(qcomGet.getGetlogthreadrun()){
					JOptionPane.showMessageDialog(contentPane, "ThenTools is getting logs from device : "+com.Main.ThenToolsRun.selectedID, 
							"Messge", JOptionPane.ERROR_MESSAGE); 
				}else{
					//开启抓取log
				qcomGet.run(timeornone, happentime,iscompression);
				dispose();
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"happentimeUI OK dispose");
			}
		});
		btnOK.setBounds(204, 222, 100, 25);
		contentPane.add(btnOK);
		
		
		//None radiobutton
		rdbtnNone = new JRadioButton(getString("rdbtnNone"));
		rdbtnNone.setForeground(Color.RED);
		rdbtnNone.setSelected(true);
		rdbtnNone.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				timeornone="None";
				rdbtnNone.setForeground(Color.RED);
				rdbtnTime.setForeground(Color.BLACK);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"select None option in happentimeUI");
			}
			
		});
		rdbtnNone.setBounds(6, 16, 103, 23);
		contentPane.add(rdbtnNone);
		
		
		//Time radiobutton
		rdbtnTime = new JRadioButton(getString("rdbtnTime"));
		rdbtnTime.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				timeornone="Time";
				rdbtnTime.setForeground(Color.RED);
				rdbtnNone.setForeground(Color.BLACK);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"select Time option in happentimeUI");
			}
			
		});
		rdbtnTime.setBounds(6, 41, 215, 23);
		contentPane.add(rdbtnTime);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNone);
		group.add(rdbtnTime);
		
		//happentime set
		String MMddHHmm=sDateFormat.format(new Date());
		Mon=MMddHHmm.substring(0, 2);
		Day=MMddHHmm.substring(2, 4);
		Hour=MMddHHmm.substring(4, 6);
		Min=MMddHHmm.substring(6, 8);
	    comboBoxMon = new JComboBox();
		comboBoxMon.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		comboBoxMon.setSelectedItem(Mon);
		comboBoxMon.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {    
		         Mon = (String)comboBoxMon.getSelectedItem();

		     }   
		});
		comboBoxMon.setBounds(16, 66, 45, 21);
		contentPane.add(comboBoxMon);
		
		
	    comboBoxDay = new JComboBox();
		comboBoxDay.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
	    comboBoxDay.setSelectedItem(Day);
		comboBoxDay.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {    
		         Day = (String)comboBoxDay.getSelectedItem();

		     }   
		});
		comboBoxDay.setBounds(64, 66, 45, 21);
		contentPane.add(comboBoxDay);
		
	    comboBoxHour = new JComboBox();
		comboBoxHour.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
	    comboBoxHour.setSelectedItem(Hour);
		comboBoxHour.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {     
		         Hour = (String)comboBoxHour.getSelectedItem();

		     }   
		});
		comboBoxHour.setBounds(119, 66, 47, 21);
		contentPane.add(comboBoxHour);
		
		comboBoxMin = new JComboBox();
		comboBoxMin.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
		comboBoxMin.setSelectedItem(Min);
		comboBoxMin.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {     
		         Min = (String)comboBoxMin.getSelectedItem();

		     }   
		});
		comboBoxMin.setBounds(176, 66, 45, 21);
		contentPane.add(comboBoxMin);
		
		
		JLabel label = new JLabel(":");
		label.setFont(new Font("宋体", Font.BOLD, 18));
		label.setBounds(168, 67, 20, 15);
		contentPane.add(label);
		
	    lblFolderPath = new JLabel(getString("lblFolderPath"));
	    lblFolderPath.setVerticalAlignment(SwingConstants.TOP);
		lblFolderPath.setBounds(10, 176, 236, 36);
		contentPane.add(lblFolderPath);
		
		//root
		chckbxRoot = new JCheckBox("Root");
		chckbxRoot.setBounds(309, 155, 103, 23);
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
		//compression
		chckbxCompression = new JCheckBox(getString("chckbxCompression"));
		chckbxCompression.setSelected(true);
		chckbxCompression.setBounds(309, 130, 103, 23);
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
		
		//Syn ue time
		JButton btnSynUe = new JButton(getString("btnSynUe"));
		btnSynUe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] time=qcomGet.getUEtime();
				if(time[1]!=null){
				Mon=time[1];
				Day=time[2];
				Hour=time[3];
				Min=time[4];
				comboBoxMon.setSelectedItem(Mon);
				comboBoxDay.setSelectedItem(Day);
				comboBoxHour.setSelectedItem(Hour);
				comboBoxMin.setSelectedItem(Min);
				}else{
					JOptionPane.showMessageDialog(contentPane, "Get UE time unsuccessfully! Pls check.", 
							"Messge", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		btnSynUe.setBounds(251, 64, 100, 25);
		contentPane.add(btnSynUe);
		
	}
	
	public void updatetime(){
		String MMddHHmm=sDateFormat.format(new Date());
		Mon=MMddHHmm.substring(0, 2);
		Day=MMddHHmm.substring(2, 4);
		Hour=MMddHHmm.substring(4, 6);
		Min=MMddHHmm.substring(6, 8);
		
		comboBoxMon.setSelectedItem(Mon);
		comboBoxDay.setSelectedItem(Day);
		comboBoxHour.setSelectedItem(Hour);
		comboBoxMin.setSelectedItem(Min);
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "请设置发生时间:";
		}else{
			return "When does the issue happen ?";
		}
		case "btnCancel": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "取消";
		}else{
			return "Cancel";
		}
		case "btnOK": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "获取";
		}else{
			return "Get";
		}
		case "rdbtnNone": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "无发生时间";
		}else{
			return "None Time";
		}
		case "rdbtnTime": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "有发生时间(24小时制)";
		}else{
			return "Happen Time (24-hour format)";
		}
		case "chckbxCompression": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "7-Zip压缩";
		}else{
			return "7-Zip";
		}
		case "lblFolderPath": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "<html>Log存储目录 :<br>桌面/ThenLog/MTK/PCtime</html>";
		}else{
			return "<html>The log directory :<br>%Desktop%/ThenLog/MTK/PCtime</html>";
		}
		case "btnSynUe": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "同步设备";
		}else{
			return "Syn UE";
		}

			default: return "";
		}	
	}
}
