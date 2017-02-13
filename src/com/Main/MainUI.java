package com.Main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.SwingConstants;

import com.AD.ADShow;
import com.Functions.CheckPC;
import com.Functions.Excute;
import com.Functions.ItemUpdate;
import com.Functions.LoggerUtil;
import com.Help.AboutAdbDriver;
import com.Help.AboutDialogUI;
import com.Help.AboutLisenceUI;
import com.Qcom.QcomADBRoot;
import com.Qcom.QcomActive;
import com.Qcom.QcomGet;
import com.Qcom.QcomStop;
import com.Qcom.QcomhappentimeUI;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JProgressBar;
import java.awt.Color;

public class MainUI extends JFrame {
	
	public static MTKUImain MTKUImain;
	public static MonkeyUImain MonkeyUImain;
	public static OthersUImain OthersUImain;
	public static GetScreenUImain GetScreenUImain;
	public static CheckTPUImain ChecktpUImain;
	public static AutoScriptUImain autoscriptUImain;
	public static ThenHelperAPKUImain ThenHelperAPKUImain;
	public static ADShow adshow=new ADShow();
	public JPanel contentPane;
	
	/**
	 * Launch the application.
	 */

	 JComboBox deviceslist; //设备列表
	 JLabel lblDevicestatus;//设备状态显示
	 JLabel lblDeviceInfo;//
	 JLabel lblPCInfo;//
	 JLabel lblBarstatus;//bar
	 boolean dellog=true;
	 ItemUpdate itemupdate=new ItemUpdate();
	 private JMenuBar menuBar;
	 public JProgressBar progressBarmain;
	 int barvalue=0;
	 long itemchange=0;
	 AboutAdbDriver aboutadbdriver;
		int lbllogcount=0;
		
		JLabel lblLeftday;
		int day;
	/**
	 * Create the frame.
	 */
	public MainUI() {
		setResizable(false);
		//Check UE status
		setTitle("ThenTools");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 650);
		getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLocation(0, 0);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setSize(740,150);
		getContentPane().add(contentPane);
		getContentPane().add(com.Main.ThenToolsRun.QcomUImain);
		com.Main.ThenToolsRun.QcomUImain.setVisible(true);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		checktimefromnet();
		//设备列表
	    deviceslist = new JComboBox();
//		deviceslist.addActionListener(new ActionListener(){
//			 public void actionPerformed(ActionEvent e) {   
//				// deviceslist = (JComboBox)e.getSource();   
//		         String str = (String)deviceslist.getSelectedItem();
//		         mainRun.selectedID=str;
//		         com.Main.ThenToolsRun.logger.log(Level.INFO,"select devices ID="+str);
//		     }   
//		});
	    //devices list 
		deviceslist.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
			 if(arg0.getStateChange()==1){ //插入
				 if (System.currentTimeMillis() - itemchange < 1000) {
					// itemchange=System.currentTimeMillis();
					 deviceslist.setSelectedIndex(0);
					 JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Don't fast switching devices!", 
						"Message", JOptionPane.ERROR_MESSAGE); 
					 //
			    		String str = (String)deviceslist.getSelectedItem();
			    		ThenToolsRun.selectedID=str;
			    		com.Main.ThenToolsRun.logger.log(Level.INFO,"!!plug in ID="+str+" 1000");
			    		itemchange=System.currentTimeMillis();
						itemupdate.run();
				 } else {
		    		String str = (String)deviceslist.getSelectedItem();
		    		ThenToolsRun.selectedID=str;
		    		com.Main.ThenToolsRun.logger.log(Level.INFO,"plug in devices ID="+str);
		    		itemchange=System.currentTimeMillis();
					itemupdate.run();
		    	}
			 	}else{//拔出
					itemupdate.run();
		    		com.Main.ThenToolsRun.logger.log(Level.INFO,"cancel one devices");
			 	}
			}
			
		});
		deviceslist.setBounds(10, 32, 160, 23);
		contentPane.add(deviceslist);
		
	    lblDevicestatus = new JLabel("No device checked");
	    lblDevicestatus.setVerticalAlignment(SwingConstants.TOP);
		lblDevicestatus.setBounds(10, 65, 160, 45);
		contentPane.add(lblDevicestatus);
		
		lblDeviceInfo = new JLabel("No device info checked");
		lblDeviceInfo.setVerticalAlignment(SwingConstants.TOP);
		lblDeviceInfo.setBounds(180, 65, 552, 45);
		contentPane.add(lblDeviceInfo);
		
		JLabel lblDevicesList = new JLabel(getString("lblDevicesList"));
		lblDevicesList.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblDevicesList.setBounds(10, 10, 148, 15);
		contentPane.add(lblDevicesList);
		
		lblPCInfo = new JLabel("PC environment checking");
		lblPCInfo.setVerticalAlignment(SwingConstants.TOP);
		lblPCInfo.setBounds(180, 10, 266, 33);
		contentPane.add(lblPCInfo);
		
		//Progressbar
		progressBarmain = new JProgressBar();
		progressBarmain.setValue(0);
		progressBarmain.setStringPainted(true);
		progressBarmain.setMinimum(0);
		progressBarmain.setMaximum(100);
		progressBarmain.setBounds(124, 109, 406, 25);
		contentPane.add(progressBarmain);
		progressBarmain.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				int value=progressBarmain.getValue();
		      if (arg0.getSource() == progressBarmain) {
		    	  if(value==0){
		    		  lblBarstatus.setText(getString("lblBarstatus"));
		    	  }else if(value==100){
		    		  lblBarstatus.setText(getString("lblBarstatus1"));  
		    	  }else if(value==10) {
		    		  adshow.run();//show ad
		    		  lblBarstatus.setText(getString("lblBarstatus2"));
		    	  }
		     }  
			}
			
		});
		
		lblBarstatus = new JLabel("");
		lblBarstatus.setBounds(538, 109, 182, 25);
		contentPane.add(lblBarstatus);
		lblBarstatus.setFont(new Font("微软雅黑", Font.BOLD, 18));
		
		JLabel lblLine = new JLabel("_______________________________________________________________________________________________");
		lblLine.setVerticalAlignment(SwingConstants.TOP);
		lblLine.setBounds(10, 130, 722, 15);
		contentPane.add(lblLine);
		
		JLabel lblPragressbar = new JLabel(getString("lblPragressbar"));
		lblPragressbar.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblPragressbar.setBounds(10, 109, 148, 25);
		contentPane.add(lblPragressbar);
		//lbl open log
		JLabel lbldebug = new JLabel("");
		lbldebug.setBounds(679, 70, 51, 64);
		contentPane.add(lbldebug);
		lbldebug.setIcon(new ImageIcon(getClass().getResource("/Rescource/DVAlogo.jpg")));
		lbldebug.addMouseListener(new MouseListener()
	    {
		      public void mouseReleased(MouseEvent arg0) {
		      }

		      public void mousePressed(MouseEvent arg0) {
		      }

		      public void mouseExited(MouseEvent arg0) {
		      }

		      public void mouseEntered(MouseEvent arg0) {
		      }

		      public void mouseClicked(MouseEvent arg0) {
		    	  if(lbllogcount>=5){
		    			  com.Main.ThenToolsRun.hidenlog=true;
		    			  com.Main.ThenToolsRun.logger.log(Level.INFO,"open hiden log successful by DVAlogo");
							JOptionPane.showMessageDialog(getContentPane(), "You have gone to developer mode!", 
									"Message", JOptionPane.INFORMATION_MESSAGE); 
							lbllogcount=0;
		    		  }
		    	  lbllogcount++;
		      }
		    });
		
		
		//MENU
		menuBar = new JMenuBar();
		menuBar.setToolTipText("Menu");
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu(getString("mnMenu"));
		menuBar.add(mnMenu);
		
		JMenuItem mntmQcom = new JMenuItem(getString("mntmQcom"));
		mntmQcom.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(com.Main.ThenToolsRun.QcomUImain==null){
					com.Main.ThenToolsRun.QcomUImain= new QcomUImain();
					com.Main.ThenToolsRun.QcomUImain.setVisible(true);
				}else{
					com.Main.ThenToolsRun.QcomUImain.setVisible(true);
				}
				if(MonkeyUImain!=null){
					MonkeyUImain.setVisible(false);
				}
				if(OthersUImain!=null){
					OthersUImain.setVisible(false);
				}
				if(MTKUImain!=null){
					MTKUImain.setVisible(false);
				}
				if(GetScreenUImain!=null){
					GetScreenUImain.setVisible(false);
				}
				if(ChecktpUImain!=null){
					ChecktpUImain.setVisible(false);
				}
				if(autoscriptUImain!=null){
					autoscriptUImain.setVisible(false);
				}
				if(ThenHelperAPKUImain!=null){
					ThenHelperAPKUImain.setVisible(false);
				}
				contentPane.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"go to QcomUImain by menu");
			}
			
		});

		JMenuItem mntmMtk = new JMenuItem(getString("mntmMtk"));
		mntmMtk.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(MTKUImain==null){
				MTKUImain= new MTKUImain();
				getContentPane().add(MTKUImain);
				}
				if(MTKUImain!=null){
					MTKUImain.setVisible(true);
				}
				if(MonkeyUImain!=null){
					MonkeyUImain.setVisible(false);
				}
				if(OthersUImain!=null){
					OthersUImain.setVisible(false);
				}
				if(com.Main.ThenToolsRun.QcomUImain!=null){
					com.Main.ThenToolsRun.QcomUImain.setVisible(false);
				}
				if(GetScreenUImain!=null){
					GetScreenUImain.setVisible(false);
				}
				if(ChecktpUImain!=null){
					ChecktpUImain.setVisible(false);
				}
				if(autoscriptUImain!=null){
					autoscriptUImain.setVisible(false);
				}
				if(ThenHelperAPKUImain!=null){
					ThenHelperAPKUImain.setVisible(false);
				}
				contentPane.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"go to MTKUImain by menu");
			}
			
		});
		
		JMenu mnOption = new JMenu(getString("mnOption"));
		menuBar.add(mnOption);
		
		JMenuItem mntmMonkey = new JMenuItem(getString("mntmMonkey"));
		mntmMonkey.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(MonkeyUImain==null){
					MonkeyUImain= new MonkeyUImain();
					getContentPane().add(MonkeyUImain);
				}
				if(MonkeyUImain!=null){
					MonkeyUImain.setVisible(true);
				}
				if(MTKUImain!=null){
					MTKUImain.setVisible(false);
				}
				if(OthersUImain!=null){
					OthersUImain.setVisible(false);
				}
				if(com.Main.ThenToolsRun.QcomUImain!=null){
					com.Main.ThenToolsRun.QcomUImain.setVisible(false);
				}
				if(GetScreenUImain!=null){
					GetScreenUImain.setVisible(false);
				}
				if(ChecktpUImain!=null){
					ChecktpUImain.setVisible(false);
				}
				if(autoscriptUImain!=null){
					autoscriptUImain.setVisible(false);
				}
				if(ThenHelperAPKUImain!=null){
					ThenHelperAPKUImain.setVisible(false);
				}
				contentPane.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"go to MonkeyUImain by menu");
			}
			
		});

		JMenuItem mntmGetscreen = new JMenuItem(getString("mntmGetscreen"));
		mntmGetscreen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(GetScreenUImain==null){
					GetScreenUImain= new GetScreenUImain();
					getContentPane().add(GetScreenUImain);
				}
				if(GetScreenUImain!=null){
					if(GetScreenUImain.showimage){
						GetScreenUImain.btnShow.doClick();	
					}
					GetScreenUImain.setVisible(true);
				}
				if(MTKUImain!=null){
					MTKUImain.setVisible(false);
				}
				if(MonkeyUImain!=null){
					MonkeyUImain.setVisible(false);
				}
				if(com.Main.ThenToolsRun.QcomUImain!=null){
					com.Main.ThenToolsRun.QcomUImain.setVisible(false);
				}
				if(OthersUImain!=null){
					OthersUImain.setVisible(false);
				}
				if(ChecktpUImain!=null){
					ChecktpUImain.setVisible(false);
				}
				if(autoscriptUImain!=null){
					autoscriptUImain.setVisible(false);
				}
				if(ThenHelperAPKUImain!=null){
					ThenHelperAPKUImain.setVisible(false);
				}
				contentPane.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"go to GetScreenUImain by menu");
			}
		});
		
		JMenuItem mntmChecktp = new JMenuItem(getString("mntmChecktp"));
		mntmChecktp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(ChecktpUImain==null){
					ChecktpUImain= new CheckTPUImain();
					getContentPane().add(ChecktpUImain);
				}
				if(ChecktpUImain!=null){
					ChecktpUImain.setVisible(true);
				}
				if(MTKUImain!=null){
					MTKUImain.setVisible(false);
				}
				if(MonkeyUImain!=null){
					MonkeyUImain.setVisible(false);
				}
				if(com.Main.ThenToolsRun.QcomUImain!=null){
					com.Main.ThenToolsRun.QcomUImain.setVisible(false);
				}
				if(GetScreenUImain!=null){
					GetScreenUImain.setVisible(false);
				}
				if(OthersUImain!=null){
					OthersUImain.setVisible(false);
				}
				if(autoscriptUImain!=null){
					autoscriptUImain.setVisible(false);
				}
				if(ThenHelperAPKUImain!=null){
					ThenHelperAPKUImain.setVisible(false);
				}
				contentPane.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"go to ChecktpUImain by menu");
			}
			
		});
		//Auto Script
		JMenuItem mntmAutoScript = new JMenuItem(getString("mntmAutoScript"));
		//mntmAutoScript.setForeground(Color.RED);
		mntmAutoScript.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(autoscriptUImain==null){
					autoscriptUImain= new AutoScriptUImain();
					getContentPane().add(autoscriptUImain);
				}
				if(autoscriptUImain!=null){
					autoscriptUImain.setVisible(true);
				}
				if(OthersUImain!=null){
					OthersUImain.setVisible(false);
				}
				if(MTKUImain!=null){
					MTKUImain.setVisible(false);
				}
				if(MonkeyUImain!=null){
					MonkeyUImain.setVisible(false);
				}
				if(com.Main.ThenToolsRun.QcomUImain!=null){
					com.Main.ThenToolsRun.QcomUImain.setVisible(false);
				}
				if(GetScreenUImain!=null){
					GetScreenUImain.setVisible(false);
				}
				if(ChecktpUImain!=null){
					ChecktpUImain.setVisible(false);
				}
				if(ThenHelperAPKUImain!=null){
					ThenHelperAPKUImain.setVisible(false);
				}
//				if(!com.Main.ThenToolsRun.crypt.isvip()){
//					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "VIP feature!", 
//							"Message", JOptionPane.ERROR_MESSAGE);
//					autoscriptUImain.setVisible(false);
//					com.Main.ThenToolsRun.QcomUImain.setVisible(true);
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"autoscriptUImain needs VIP");
//				}
				contentPane.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"go to autoscriptUImain by menu");
			}
			
		});
		
		JMenuItem mntmOthers = new JMenuItem(getString("mntmOthers"));
		mntmOthers.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(OthersUImain==null){
					OthersUImain= new OthersUImain();
					getContentPane().add(OthersUImain);
				}
				if(OthersUImain!=null){
					OthersUImain.setVisible(true);
				}
				if(autoscriptUImain!=null){
					autoscriptUImain.setVisible(false);
				}
				if(MTKUImain!=null){
					MTKUImain.setVisible(false);
				}
				if(MonkeyUImain!=null){
					MonkeyUImain.setVisible(false);
				}
				if(com.Main.ThenToolsRun.QcomUImain!=null){
					com.Main.ThenToolsRun.QcomUImain.setVisible(false);
				}
				if(GetScreenUImain!=null){
					GetScreenUImain.setVisible(false);
				}
				if(ChecktpUImain!=null){
					ChecktpUImain.setVisible(false);
				}
				if(ThenHelperAPKUImain!=null){
					ThenHelperAPKUImain.setVisible(false);
				}
				contentPane.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"go to OthersUImain by menu");
			}
			
		});
		//ThenHelperAPK item
		JMenuItem mntmThenHelperAPK = new JMenuItem(getString("mntmThenHelperAPK"));
		mntmThenHelperAPK.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(ThenHelperAPKUImain==null){
					ThenHelperAPKUImain= new ThenHelperAPKUImain();
					getContentPane().add(ThenHelperAPKUImain);
				}
				if(ThenHelperAPKUImain!=null){
					ThenHelperAPKUImain.setVisible(true);
				}
				if(OthersUImain!=null){
					OthersUImain.setVisible(false);
				}
				if(autoscriptUImain!=null){
					autoscriptUImain.setVisible(false);
				}
				if(MTKUImain!=null){
					MTKUImain.setVisible(false);
				}
				if(MonkeyUImain!=null){
					MonkeyUImain.setVisible(false);
				}
				if(com.Main.ThenToolsRun.QcomUImain!=null){
					com.Main.ThenToolsRun.QcomUImain.setVisible(false);
				}
				if(GetScreenUImain!=null){
					GetScreenUImain.setVisible(false);
				}
				if(ChecktpUImain!=null){
					ChecktpUImain.setVisible(false);
				}
				contentPane.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"go to ThenHelperAPKUImain by menu");
			}
			
		});
		
		//Help menu
		JMenu mnHelp = new JMenu(getString("mnHelp"));
		menuBar.add(mnHelp);
		//Help-----Language
		JMenuItem mntmLanguage = new JMenuItem(getString("mntmLanguage"));
		mntmLanguage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Object[] LanguageOption = {"Chinese","English"};
				int response=JOptionPane.showOptionDialog(com.Main.ThenToolsRun.mainFrame, "Pls select UI language:", "Message",JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, LanguageOption, LanguageOption[0]);
				if(response==0){ 
					com.Main.ThenToolsRun.dbhandle.setSingleLineValue("UserSettings", "Language", "CN");
					 JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Set Language as Chinese successfully, Pls restart!", 
						"Message", JOptionPane.INFORMATION_MESSAGE); 
						com.Main.ThenToolsRun.logger.log(Level.INFO,"Set Language as Chinese");
						dispose();
						System.exit(0);
				}else if(response==1){ 
					com.Main.ThenToolsRun.dbhandle.setSingleLineValue("UserSettings", "Language", "EN");
					 JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Set Language as English successfully, Pls restart!", 
						"Message", JOptionPane.INFORMATION_MESSAGE); 
						com.Main.ThenToolsRun.logger.log(Level.INFO,"Set Language as English");
						dispose();
						System.exit(0);
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"tap language button");
			}
		});

		
		//Help---Lisence item
		JMenuItem mntmLisence = new JMenuItem(getString("mntmLisence"));
		mntmLisence.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AboutLisenceUI aboutlisence=new AboutLisenceUI();
				aboutlisence.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"tap lisence button");
			}
		});
		//help--adbdriver
		JMenuItem mntmADBdriver = new JMenuItem(getString("mntmADBdriver"));
		mntmADBdriver.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//acivelogthreadrun true
				int result=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Do you want to start to download adb driver?",
				"Pls confirm:",JOptionPane.YES_NO_OPTION);
				if(result==0){
					aboutadbdriver=new AboutAdbDriver();
					if(aboutadbdriver.getAboutadbdriverthreadrun()){
						com.Main.ThenToolsRun.logger.log(Level.INFO,"getAboutadbdriverthreadrun =true");
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
								"Message", JOptionPane.ERROR_MESSAGE); 
						com.Main.ThenToolsRun.logger.log(Level.INFO,"tap mntmADBdriver button");
						return;
					}
					aboutadbdriver.run();
					com.Main.ThenToolsRun.logger.log(Level.INFO,"tap mntmADBdriver button");
				}else{
					com.Main.ThenToolsRun.logger.log(Level.INFO,"tap mntmADBdriver button");
					return;
				}

			}
		});
		
		//Help---About item
		JMenuItem mntmAbout = new JMenuItem(getString("mntmAbout"));
		mntmAbout.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AboutDialogUI aboutdialog=new AboutDialogUI();
				aboutdialog.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"tap About button");
			}
		});
		//Generel sort
		mnMenu.add(mntmQcom);
		mnMenu.add(mntmMtk);
		mnMenu.add(mntmThenHelperAPK);
		mnMenu.add(mntmGetscreen);
		//assist sort
		mnOption.add(mntmMonkey);
		mnOption.add(mntmChecktp);
		mnOption.add(mntmAutoScript);
		mnOption.add(mntmADBdriver);
		mnOption.add(mntmOthers);
		//help sort
		mnHelp.add(mntmLanguage);
		mnHelp.add(mntmLisence);
		mnHelp.add(mntmAbout);
		//rewrite close windows
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			com.Main.ThenToolsRun.logger.log(Level.INFO,"close ThenTools");
			com.Main.ThenToolsRun.getdevices.terminate();
			com.Main.ThenToolsRun.dbhandle.closeDB();
			dispose();
			System.exit(0);
			}
			}); 
	}
	//get time from net
	public void checktimefromnet(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String[] server=new String[]{"time-a.nist.gov","time-b.nist.gov","time-nw.nist.gov","time.nist.gov","time.windows.com"};
			    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); 
		        String timeServerUrl;
		        InetAddress timeServerAddress;
		        TimeInfo timeInfo;  
		        TimeStamp timeStamp;
		        int day=0;
		  try {
		        while(true){
				    NTPUDPClient timeClient = new NTPUDPClient();  
				    Random random=new Random();//random
		        	timeServerUrl = server[random.nextInt(4)];
		        	timeServerAddress = InetAddress.getByName(timeServerUrl);  
		        	timeInfo = timeClient.getTime(timeServerAddress);  
		        	 timeStamp = timeInfo.getMessage().getTransmitTimeStamp();  
		        	 day = (int) ((new Date().getTime()-timeStamp.getTime())/(24*60*60*1000));
			        	System.out.println("check "+day);
		        	// if(Integer.parseInt(com.Main.ThenToolsRun.crypt.getvalidday())-day<=0){
		        	 if(Math.abs(day)>0){
		        		System.out.println("GGG"+dateFormat.format(timeStamp.getDate())+timeFormat.format(timeStamp.getDate()));
			        	com.Main.ThenToolsRun.logger.log(Level.INFO,"Now is "+dateFormat.format(timeStamp.getDate())+timeFormat.format(timeStamp.getDate())+"!");
			        	//change sys date time
			        	Excute.execcmd("date "+dateFormat.format(timeStamp.getDate()), 1, true);
			        	Excute.execcmd("time "+timeFormat.format(timeStamp.getDate()), 1, true);
		        		System.exit(0);
		        	 }
		 	        try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
					}
		        }
		    } catch (UnknownHostException e) {  
		    	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		    } catch (IOException e) {  
		    	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		    }  
			}
		}).start();
		
		//lblvip
		JLabel lblVIP = new JLabel("VIP");
		lblVIP.setForeground(Color.RED);
		lblVIP.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblVIP.setBounds(679, 0, 44, 23);
		contentPane.add(lblVIP);
		lblVIP.setVisible(false);
		if(com.Main.ThenToolsRun.crypt.isvip()){
			lblVIP.setVisible(true);
		}
		//left days
		day=com.Main.ThenToolsRun.crypt.getstrday();//get left day
		lblLeftday = new JLabel();
		if(day>0){
			lblLeftday.setText(getString("lblLeftday"));
		}else{
			lblLeftday.setText(getString("lblLeftday2"));
		}
		lblLeftday.setForeground(Color.RED);
		lblLeftday.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblLeftday.setBounds(600, 22, 140, 38);
		contentPane.add(lblLeftday);
	}
	//设备列表按钮
	public JComboBox getDeviceslist(){
		return deviceslist;
	}
	
	//设备状态
	public JLabel getDevicestatus(){
		return lblDevicestatus;
	}
	//设备状态
	public JLabel getDeviceInfo(){
		return lblDeviceInfo;
	}
	//PC信息
	public JLabel getPCInfo(){
		return lblPCInfo;
	}
	//进度条设置
	public int getbarvalue(){
		return barvalue;
	}
	//MonkeyUI
	public MonkeyUImain getMonkeyUImain(){
		return MonkeyUImain;
	}
	//
	//AutoScriptUImain
	public AutoScriptUImain getAutoScriptUImain(){
		return autoscriptUImain;
	}
	//CheckTPUImain
	public CheckTPUImain getCheckTPUImain(){
		return ChecktpUImain;
	}
	
	//GetScreenUImain
	public GetScreenUImain getGetScreenUImain(){
		return GetScreenUImain;
	}
	public void setbarvalue(int value){
		this.barvalue=value;
		progressBarmain.setValue(value);
	}
	
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblDevicesList": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "设备列表:";
		}else{
			return "Devices List :";
		}
		case "lblPragressbar": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "进度条:";
		}else{
			return "PragressBar :";
		}
		case "mnMenu": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "常用功能";
		}else{
			return "Generel";
		}
		case "mntmQcom": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "高通Log";
		}else{
			return "QcomLog";
		}
		case "mntmMtk": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "MTKLog";
		}else{
			return "MTKLog";
		}
		case "mnOption": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "辅助功能";
		}else{
			return "Assist";
		}	
		case "mntmMonkey": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "Monkey";
		}else{
			return "Monkey";
		}
		case "mntmGetscreen": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "屏幕获取";
		}else{
			return "GetScreen";
		}
		case "mntmOthers": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "更多";
		}else{
			return "More";
		}
		case "mnHelp": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "帮助";
		}else{
			return "Help";
		}
		case "mntmLanguage": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "Language";
		}else{
			return "Language";
		}
		case "mntmLisence": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "关于证书";
		}else{
			return "Lisence";
		}
		case "mntmAbout": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "关于我们";
		}else{
			return "About";
		}
		case "lblBarstatus": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html><font color=\"#FF0000\">失败!</font></html>";
		}else{
			return "<html><font color=\"#FF0000\">Failed!</font></html>";
		}
		case "lblBarstatus1": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html><font color=\"#09F7F7\">完成!</font></html>";
		}else{
			return "<html><font color=\"#09F7F7\">Complete!</font></html>";
		}
		case "lblBarstatus2": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "努力中...";
		}else{
			return "Working hard...";
		}
		case "mntmChecktp": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "TP监控";
		}else{
			return "TPMonitor";
		}
		case "mntmAutoScript": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "自动化脚本";
		}else{
			return "AutoScript";
		}
		case "mntmADBdriver": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "下载Adb驱动";
		}else{
			return "AdbDriver";
		}
		case "mntmThenHelperAPK": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ThenHelper";
		}else{
			return "ThenHelper";
		}
		case "lblLeftday": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "有效期剩余: "+day+" 天";
		}else{
			return "Lisence: "+day+" days left";
		}
		case "lblLeftday2": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "有效期剩余: "+day+" 天!";
		}else{
			return "Lisence: "+day+" days left, Pls active!";
		}
			default: return "";
		}
		
	}
}

