package com.Main;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;

import com.Functions.CheckUE;
import com.Functions.EnDecrypt;
import com.Functions.Excute;
import com.Monkey.MonkeyActive;
import com.Monkey.MonkeyGet;
import com.Monkey.MonkeyMonitorUI;
import com.Monkey.MonkeyPackage;
import com.Monkey.MonkeyStop;
import com.Monkey.MonkeylogUI;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JScrollPane;

import java.awt.Component;

import javax.swing.ListModel;
import javax.swing.JTextArea;

public class MonkeyUImain extends JPanel {
	private SimpleDateFormat sDateFormat = new SimpleDateFormat("ssSSS");
	JRadioButton rdbtnCustomizeMonkey;
	JRadioButton rdbtnPackageMonkey;
	JRadioButton rdbtnSystemMonkey;
	JButton btnResetCustomize;
	JFormattedTextField formattedTextFieldTime;
	JFormattedTextField formattedTextFieldSeed;
	String monkeyradio="System";
	MonkeyActive monkeyacitve=new MonkeyActive();
	MonkeyStop monkeystop=new MonkeyStop();
	MonkeyPackage monkeypackage=new MonkeyPackage();
	JTextArea textAreaCustomize;
	
	MonkeyMonitorUI monkeymonitorui=new MonkeyMonitorUI();
	
	DefaultListModel packagelistAPP=new DefaultListModel();
	DefaultListModel packagelistGMS=new DefaultListModel();
	DefaultListModel packagelistRun=new DefaultListModel();
	JList<String> listpackageAPP;
	JList<String> listpackageGMS;
	JList<String> listpackageRun;
	JScrollPane scrollPaneAPP;
	JScrollPane scrollPaneGMS;
	JScrollPane scrollPaneRun;
	JScrollPane scrollPaneTxt;
	JButton btnGms;
	boolean isGMS=false;
	
	MonkeylogUI monkeylogUI= new MonkeylogUI();
	EnDecrypt check=new EnDecrypt();
	private JButton btnSaveCustomize;
	 
	/**
	 * Create the panel.
	 */
	public MonkeyUImain() {
		setSize(700,650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel lblMonkey = new JLabel(getString("lblMonkey"));
		lblMonkey.setVerticalAlignment(SwingConstants.TOP);
		lblMonkey.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 26));
		lblMonkey.setBounds(10, 145, 126, 35);
		add(lblMonkey);

		if(!check.isok()){
			com.Main.ThenToolsRun.logger.log(Level.INFO,"ERROR about check...");
			System.exit(0);
		}
		//Active
		JButton btnActive = new JButton(getString("btnActive"));
		btnActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//device null
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey active button: no devices");
					return;
				}
				//running
				if(monkeyacitve.getActiveMonkeythreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"getActiveMonkeythreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey active button: running");
					return;
				}
				//time null
				if(formattedTextFieldTime.getText().equals("")){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Intervals should be between 500 ms and 5000 ms", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey active button: time=0");
					return;
				}
				//time
				if(Long.parseLong(formattedTextFieldTime.getText())>5000||Long.parseLong(formattedTextFieldTime.getText())<500){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Intervals should be between 500 ms and 5000 ms", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey active button: time >5000 or <500");
					return;
				}
				//check monkey run
				if(CheckUE.checkMonkeyrun()){
					com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(0);//******************
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Monkey is running!", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Monkey is running!");
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
				//run
				String tempstr;
				StringBuffer packages=new StringBuffer();
				String[] tempsplit;
				if(monkeyradio.equals("Packages")){
					if(packagelistRun.getSize()==0){
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls select packages to right box", 
								"Message", JOptionPane.ERROR_MESSAGE); 
						com.Main.ThenToolsRun.logger.log(Level.INFO,"Pls select packages to right box");
						return;
					}
					for(int i=0;i<packagelistRun.getSize();i++){
						tempstr=packagelistRun.getElementAt(i).toString();
						if(tempstr.startsWith("<html>APP: ")||tempstr.startsWith("<html>GMS: ")){
							tempsplit=tempstr.split("=");
							packages.append("-p "+tempsplit[2].substring(0, tempsplit[2].length()-7)+" ");
						}else if(tempstr.startsWith("APP: ")){
							packages.append("-p "+tempstr.substring(5,tempstr.length())+" ");
						}else if(tempstr.startsWith("GMS: ")){
							packages.append("-p "+tempstr.substring(5,tempstr.length())+" ");
						}
					}
				}else if(monkeyradio.equals("Customize")&&check.isok()){
					packages.append(textAreaCustomize.getText());
					com.Main.ThenToolsRun.dbhandle.setSingleLineValue("MonkeyMonitorSettings", "diymonkey",textAreaCustomize.getText());
				}
				monkeyacitve.run(formattedTextFieldSeed.getText(),formattedTextFieldTime.getText(), monkeyradio, packages.toString());
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey active button");
			}
		});
		btnActive.setBounds(590, 156, 100, 25);
		add(btnActive);
		
		//Stop
		JButton btnStop = new JButton(getString("btnStop"));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//device null
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey stop button: no devices");
					return;
				}
				int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Do you want to stop Monkey? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
				if(confirm==0){
					monkeystop.run();
				}else{
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey stop button: no");
					return;
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey stop button: yes");
			}
		});
		btnStop.setBounds(590, 191, 100, 25);
		add(btnStop);
		
		//Get log
		JButton btnGetLogs = new JButton(getString("btnGetLogs"));
		btnGetLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				monkeylogUI.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Monkey get button");
			}
		});
		btnGetLogs.setBounds(590, 226, 100, 25);
		add(btnGetLogs);
		
		//System monkey
		rdbtnSystemMonkey = new JRadioButton(getString("rdbtnSystemMonkey"));
		rdbtnSystemMonkey.setForeground(Color.RED);
		rdbtnSystemMonkey.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 12));
		rdbtnSystemMonkey.setSelected(true);
		rdbtnSystemMonkey.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				monkeyradio="System";
				rdbtnSystemMonkey.setForeground(Color.RED);
				rdbtnPackageMonkey.setForeground(Color.BLACK);
				rdbtnCustomizeMonkey.setForeground(Color.BLACK);
				btnResetCustomize.setVisible(false);
				btnSaveCustomize.setVisible(false);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"select System option in MonkeyUI");
			}
			
		});
		rdbtnSystemMonkey.setBounds(32, 186, 132, 23);
		add(rdbtnSystemMonkey);
		//package monkey
		rdbtnPackageMonkey = new JRadioButton(getString("rdbtnPackageMonkey"));
		rdbtnPackageMonkey.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 12));
		rdbtnPackageMonkey.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				monkeyradio="Packages";
				rdbtnPackageMonkey.setForeground(Color.RED);
				rdbtnSystemMonkey.setForeground(Color.BLACK);
				rdbtnCustomizeMonkey.setForeground(Color.BLACK);
				btnResetCustomize.setVisible(false);
				btnSaveCustomize.setVisible(false);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"select Packages option in MonkeyUI");
			}
			
		});
		rdbtnPackageMonkey.setBounds(32, 286, 149, 23);
		add(rdbtnPackageMonkey);
		
		//customize
		rdbtnCustomizeMonkey = new JRadioButton(getString("rdbtnCustomizeMonkey"));
		rdbtnCustomizeMonkey.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 12));
		rdbtnCustomizeMonkey.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				monkeyradio="Customize";
				rdbtnPackageMonkey.setForeground(Color.BLACK);
				rdbtnSystemMonkey.setForeground(Color.BLACK);
				rdbtnCustomizeMonkey.setForeground(Color.RED);
				btnResetCustomize.setVisible(true);
				btnSaveCustomize.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"select Customize option in MonkeyUI");
			}
			
		});
		rdbtnCustomizeMonkey.setBounds(32, 211, 164, 23);
		add(rdbtnCustomizeMonkey);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnSystemMonkey);
		group.add(rdbtnPackageMonkey);
		group.add(rdbtnCustomizeMonkey);
		
		//Time intervals
		formattedTextFieldTime = new JFormattedTextField();
		formattedTextFieldTime.setText("1000");
		formattedTextFieldTime.addKeyListener(new KeyListener(){
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
		
		formattedTextFieldTime.setBounds(483, 158, 50, 21);
		add(formattedTextFieldTime);
		//Seed
		formattedTextFieldSeed = new JFormattedTextField(sDateFormat.format(new Date()));
		formattedTextFieldSeed.setBounds(483, 187, 50, 21);
		formattedTextFieldSeed.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e)
		    {
//			     if(Integer.parseInt(formattedTextFieldTime.getText())>5000){
//			    	 e.consume();
//			    	 formattedTextFieldTime.setText("5000");
//						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Interval time is between 500 and 99999!", 
//								"Message", JOptionPane.ERROR_MESSAGE); 
//			     }
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
		add(formattedTextFieldSeed);
		
		JLabel labelSeed = new JLabel(getString("labelSeed"));
		labelSeed.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 12));
		labelSeed.setBounds(431, 190, 50, 15);
		add(labelSeed);
		JLabel lblIntervals = new JLabel(getString("lblIntervals"));
		lblIntervals.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 12));
		lblIntervals.setBounds(431, 161, 149, 15);
		add(lblIntervals);
		
		
		//listpackageall
		listpackageAPP = new JList(packagelistAPP);
		listpackageAPP.setValueIsAdjusting(true);
		listpackageAPP.setCellRenderer(new DefaultListCellRenderer() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.black);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        });
		scrollPaneAPP = new JScrollPane(listpackageAPP);
		scrollPaneAPP.setBounds(38, 350, 250, 200);
		add(scrollPaneAPP);

		//listpackageGMS
		listpackageGMS = new JList(packagelistGMS);
		listpackageGMS.setValueIsAdjusting(true);
		listpackageGMS.setCellRenderer(new DefaultListCellRenderer() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.black);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        });
		scrollPaneGMS = new JScrollPane(listpackageGMS);
		scrollPaneGMS.setBounds(38, 350, 250, 200);
		add(scrollPaneGMS);
		scrollPaneGMS.setVisible(false);
		
		//listpackageRun
		listpackageRun = new JList(packagelistRun);
		listpackageRun.setValueIsAdjusting(true);
		listpackageRun.setCellRenderer(new DefaultListCellRenderer() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.black);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        });
		scrollPaneRun = new JScrollPane(listpackageRun);
		scrollPaneRun.setBounds(424, 350, 250, 200);
		add(scrollPaneRun);
		
		
		//move right
		JButton moveright = new JButton("->");
		moveright.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		moveright.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp;
				if(!isGMS){
					int[] ints=listpackageAPP.getSelectedIndices();
					for(int i=0;i<ints.length;i++){
						temp=listpackageAPP.getModel().getElementAt(ints[i]-i);
						if(temp.startsWith("<html>")){
					    	packagelistRun.addElement("<html>APP: "+temp.substring(6,temp.length()));
						}else{
					    	packagelistRun.addElement("APP: "+temp);
						}
			    	packagelistAPP.remove(ints[i]-i);
					} 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"APP move "+ints.length+" to Run");
				}else{
					int[] ints=listpackageGMS.getSelectedIndices();
				    for(int i=0;i<ints.length;i++){
						temp=listpackageGMS.getModel().getElementAt(ints[i]-i);
						if(temp.startsWith("<html>")){
					    	packagelistRun.addElement("<html>GMS: "+temp.substring(6,temp.length()));
						}else{
					    	packagelistRun.addElement("GMS: "+temp);
						}
				    	packagelistGMS.remove(ints[i]-i);
				    } 
				    com.Main.ThenToolsRun.logger.log(Level.INFO,"GMS move "+ints.length+" to Run");
				}

			}
		});
		moveright.setBounds(318, 378, 59, 25);
		add(moveright);
		//move left
		JButton moveleft = new JButton("<-");
		moveleft.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		moveleft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp;
				int[] ints=listpackageRun.getSelectedIndices();
				for(int i=0;i<ints.length;i++){
					temp=listpackageRun.getModel().getElementAt(ints[i]-i).toString();
				if(temp.startsWith("<html>APP: ")){
					packagelistAPP.addElement("<html>"+temp.substring(12, temp.length()));
				}else if(temp.startsWith("APP: ")){
					packagelistAPP.addElement(temp.substring(5, temp.length()));
				}else if(temp.startsWith("<html>GMS: ")){
					packagelistGMS.addElement("<html>"+temp.substring(11, temp.length()));
				}else if(temp.startsWith("GMS: ")){
					packagelistGMS.addElement(temp.substring(5, temp.length()));
				}
		    	packagelistRun.remove(ints[i]-i);
				} 
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Run move "+ints.length+" to GMS and APP");
			}
		});
		moveleft.setBounds(317, 410, 60, 25);
		add(moveleft);
		
		//go to Gms
		btnGms = new JButton(getString("btnGms"));
		btnGms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!isGMS){
					isGMS=true;
					scrollPaneAPP.setVisible(false);
					scrollPaneGMS.setVisible(true);
					btnGms.setText(getString("btnAPP"));
					com.Main.ThenToolsRun.logger.log(Level.INFO,"change to GMS list");
				}else{
					isGMS=false;
					scrollPaneGMS.setVisible(false);
					scrollPaneAPP.setVisible(true);
					btnGms.setText(getString("btnGms"));
					com.Main.ThenToolsRun.logger.log(Level.INFO,"change to APP list");
				}
			}
		});
		btnGms.setBounds(117, 315, 100, 25);
		add(btnGms);
		
		//move left all
		JButton moveleftall = new JButton("<<-");
		moveleftall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp;
				int size=listpackageRun.getModel().getSize();
				for(int i=0;i<size;i++){
					temp=listpackageRun.getModel().getElementAt(0).toString();
					if(temp.startsWith("<html>APP: ")){
						packagelistAPP.addElement("<html>"+temp.substring(12, temp.length()));
					}else if(temp.startsWith("APP: ")){
						packagelistAPP.addElement(temp.substring(5, temp.length()));
					}else if(temp.startsWith("<html>GMS: ")){
						packagelistGMS.addElement("<html>"+temp.substring(11, temp.length()));
					}else if(temp.startsWith("GMS: ")){
						packagelistGMS.addElement(temp.substring(5, temp.length()));
					}
		    	packagelistRun.remove(0);
				} 
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Run move all to APP and GMS: "+size);
			}
		});
		moveleftall.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 12));
		moveleftall.setBounds(318, 497, 59, 25);
		add(moveleftall);
		
		//move right all
		JButton moverightall = new JButton("->>");
		moverightall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp;
				if(!isGMS){
					int size=listpackageAPP.getModel().getSize();
					for(int i=0;i<size;i++){
						temp=listpackageAPP.getModel().getElementAt(0);
						if(temp.startsWith("<html>")){
					    	packagelistRun.addElement("<html>APP: "+temp.substring(6,temp.length()));
						}else{
					    	packagelistRun.addElement("APP: "+temp);
						}
			    	packagelistAPP.remove(0);
					} 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"APP move all to Run: "+size);
				}else{
					int size=listpackageGMS.getModel().getSize();
				    for(int i=0;i<size;i++){
						temp=listpackageGMS.getModel().getElementAt(0);
						if(temp.startsWith("<html>")){
					    	packagelistRun.addElement("<html>GMS: "+temp.substring(6,temp.length()));
						}else{
					    	packagelistRun.addElement("GMS: "+temp);
						}
				    	packagelistGMS.remove(0);
				    } 
				    com.Main.ThenToolsRun.logger.log(Level.INFO,"GMS move all to Run: "+size);
				}
			}
		});
		moverightall.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 12));
		moverightall.setBounds(318, 464, 59, 25);
		add(moverightall);
		//customize txt
		textAreaCustomize = new JTextArea(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("MonkeyMonitorSettings", "diymonkey"));
		textAreaCustomize.setWrapStyleWord(true);
		textAreaCustomize.setLineWrap(true);
		textAreaCustomize.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPaneTxt=new JScrollPane(textAreaCustomize);
		scrollPaneTxt.setBounds(59, 240, 508, 45);
		add(scrollPaneTxt);
		//Monkey monitor
		JButton btnMonitor = new JButton(getString("btnMonitor"));
	//	btnMonitor.setForeground(Color.RED);
		btnMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				if(!com.Main.ThenToolsRun.crypt.isvip()){
//					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "VIP feature!", 
//							"Message", JOptionPane.ERROR_MESSAGE);
//					com.Main.ThenToolsRun.logger.log(Level.INFO,"btnMonitor needs VIP");
//					return;
//				}
				//time null
				if(formattedTextFieldTime.getText().equals("")){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Intervals should be between 500 ms and 5000 ms", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor button: time=0");
					return;
				}
				//time
				if(Long.parseLong(formattedTextFieldTime.getText())>5000||Long.parseLong(formattedTextFieldTime.getText())<500){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Intervals should be between 500 ms and 5000 ms", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor button: time >5000 or <500");
					return;
				}
				monkeymonitorui.setVisible(true);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"monkey monitor button");
			}
		});
		btnMonitor.setBounds(590, 261, 100, 25);
		add(btnMonitor);
		//clear Customize button
		btnResetCustomize = new JButton(getString("btnClearCustomize"));
		btnResetCustomize.setVisible(false);
		btnResetCustomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textAreaCustomize.getText().equals("-p <package> -p <package> --ignore-crashes --ignore-timeouts --ignore-security-exceptions")){
					int confirm=JOptionPane.showConfirmDialog(com.Main.ThenToolsRun.mainFrame, "Do you want to restore txt? ","Pls confirm :", JOptionPane.YES_NO_OPTION);
					if(confirm==0){
						textAreaCustomize.setText("-p <package> -p <package> --ignore-crashes --ignore-timeouts --ignore-security-exceptions");
						com.Main.ThenToolsRun.dbhandle.setSingleLineValue("MonkeyMonitorSettings", "diymonkey","-p <package> -p <package> --ignore-crashes --ignore-timeouts --ignore-security-exceptions");
					}else{
						com.Main.ThenToolsRun.logger.log(Level.INFO,"no restore textAreaCustomize txt");
					}
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"reset Customize button button");
			}
		});
		btnResetCustomize.setBounds(398, 285, 75, 25);
		add(btnResetCustomize);
		//save Customize button
		btnSaveCustomize = new JButton(getString("btnSaveCustomize"));
		btnSaveCustomize.setVisible(false);
		btnSaveCustomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				com.Main.ThenToolsRun.dbhandle.setSingleLineValue("MonkeyMonitorSettings", "diymonkey",textAreaCustomize.getText());
				com.Main.ThenToolsRun.logger.log(Level.INFO,"save Customize button button");
			}
		});
		btnSaveCustomize.setBounds(491, 285, 75, 25);
		add(btnSaveCustomize);
		
		//setlist
		setlistpackageAPP();
		setlistpackageGMS();
	}
	
	//set listmode
	public void setlistpackageAPP(){
		SwingUtilities.invokeLater(new Runnable() {   
	      @Override  
	      public void run() {   
	    	packagelistAPP.clear();
	  		ArrayList<String> arrayAPP=monkeypackage.getPMlistAPP();
	  		for(String str:arrayAPP){
	  			packagelistAPP.addElement(str);
	  		}
	      }   
		}); 
	}
	public void setlistpackageGMS(){
		SwingUtilities.invokeLater(new Runnable() {   
		      @Override  
		      public void run() {   
			   packagelistGMS.clear();
			   ArrayList<String> arrayGMS=monkeypackage.getPMlistGMS();
			   for(String str:arrayGMS){
				  packagelistGMS.addElement(str);
				 }
		      }   
		  }); 
	}
	public void setlistpackageRun(){
		SwingUtilities.invokeLater(new Runnable() {   
		      @Override  
		      public void run() {   
		  		packagelistRun.clear();
		      }   
		  }); 
	}
	//getseed
	public JFormattedTextField getformattedTextFieldSeed(){
		return formattedTextFieldSeed;
	}
	//get monkeymonitorui
	public MonkeyMonitorUI getmonkeymonitorui(){
		return monkeymonitorui;
	}
	//get monkey info
	public String[] getMonkeyInfo(){
		String[] str=new String[4];
		str[0]=formattedTextFieldSeed.getText();
		str[1]=formattedTextFieldTime.getText();
		str[2]=monkeyradio;
		String tempstr;
		StringBuffer packages=new StringBuffer();
		String[] tempsplit;
		if(monkeyradio.equals("Packages")){
			if(packagelistRun.getSize()==0){
				com.Main.ThenToolsRun.logger.log(Level.INFO,"Pls select packages to right box");	
				return str;
			}
			for(int i=0;i<packagelistRun.getSize();i++){
				tempstr=packagelistRun.getElementAt(i).toString();
				if(tempstr.startsWith("<html>APP: ")||tempstr.startsWith("<html>GMS: ")){
					tempsplit=tempstr.split("=");
					packages.append("-p "+tempsplit[2].substring(0, tempsplit[2].length()-7)+" ");
				}else if(tempstr.startsWith("APP: ")){
					packages.append("-p "+tempstr.substring(5,tempstr.length())+" ");
				}else if(tempstr.startsWith("GMS: ")){
					packages.append("-p "+tempstr.substring(5,tempstr.length())+" ");
				}
			}
		}else if(monkeyradio.equals("Customize")&&check.isok()){
			packages.append(textAreaCustomize.getText());
		}
		str[3]=packages.toString();
		return str;
	}
	
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblMonkey": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "Monkey:";
		}else{
			return "Monkey:";
		}
		case "btnActive": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "º§ªÓ";
		}else{
			return "Active";
		}
		case "btnStop": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "Õ£÷π";
		}else{
			return "Stop";
		}
		case "btnGetLogs": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ªÒ»°Log";
		}else{
			return "Get Logs";
		}
		case "rdbtnSystemMonkey": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "œµÕ≥º∂";
		}else{
			return "System Monkey";
		}
		case "rdbtnPackageMonkey": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ƒ£øÈº∂";
		}else{
			return "Packages Monkey";
		}
		case "rdbtnCustomizeMonkey": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "◊‘∂®“Â";
		}else{
			return "Customize Monkey";
		}
		case "labelSeed": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "÷÷◊”:";
		}else{
			return "Seed :";
		}
		case "lblIntervals": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return " ±º‰º‰∏Ù:              ms";
		}else{
			return "Interval :              ms";
		}
		case "btnGms": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "µΩπ»∏Ë¡–±Ì";
		}else{
			return "To GMS";
		}
		case "btnAPP"://==========btnGms
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "µΩ”¶”√¡–±Ì";
		}else{
			return "To APP";
		}
		case "btnMonitor":
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "º‡øÿ";
		}else{
			return "Monitor";
		}	
		case "btnClearCustomize":
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "÷ÿ÷√";
		}else{
			return "Reset";
		}
		case "btnSaveCustomize":
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "±£¥Ê";
		}else{
			return "Save";
		}
			default: return "";
		}
	}
}
