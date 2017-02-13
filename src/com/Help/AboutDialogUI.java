package com.Help;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;

public class AboutDialogUI extends JDialog
{
    int count=0;
  public AboutDialogUI()
  {
   // super(owner, modal);

    setTitle(getString("title"));
    setBounds(0, 0, 350, 140);
    setResizable(false);
	setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
	getContentPane().setLayout(null);
	setIconImage(com.Main.ThenToolsRun.imagelogo);
	//OK button
    JButton buttonOK = new JButton(getString("buttonOK"));
    buttonOK.setBounds(113, 87, 100, 25);
    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AboutDialogUI.this.onOK();
      }
    });

    Container containger = getContentPane();
    containger.add(buttonOK);
    //app
    JLabel labelApp = new JLabel(com.Main.ThenToolsRun.dbhandle.getSingleLineValue("VersionInfo", "ToolsVer"));
    labelApp.setVerticalAlignment(SwingConstants.TOP);
    labelApp.setForeground(Color.RED);
    labelApp.setFont(new Font("微软雅黑", Font.BOLD, 12));
    labelApp.setBounds(10, 10, 152, 15);
    getContentPane().add(labelApp);
    //copyright
    JLabel labelCopyright = new JLabel(getString("labelCopyright"));
    labelCopyright.setFont(new Font("微软雅黑", Font.BOLD, 12));
    labelCopyright.setBounds(10, 51, 324, 15);
    getContentPane().add(labelCopyright);
    
    //hiden 
    JTextField labelUrl = new JTextField("");
    labelUrl.setFont(new Font("微软雅黑", Font.BOLD, 12));
    labelUrl.setBounds(0, 92, 36, 15);
    getContentPane().add(labelUrl);
    labelUrl.setEditable(false);
    labelUrl.setBorder(new EmptyBorder(0, 0, 0, 0));
    //label suggesttions
    JLabel labelcomments = new JLabel(getString("labelcomments"));
    labelcomments.setForeground(Color.MAGENTA);
    labelcomments.setFont(new Font("微软雅黑", Font.BOLD, 12));
    labelcomments.setBounds(10, 27, 207, 25);
    getContentPane().add(labelcomments);
    //lbl dvalogo
    JLabel lblflag = new JLabel("");
    lblflag.setBounds(293, 2, 51, 64);
    lblflag.setIcon(new ImageIcon(getClass().getResource("/Rescource/Logo.jpg")));
    getContentPane().add(lblflag);

    labelUrl.addMouseListener(new MouseListener()
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
//        JTextField textField = (JTextField)arg0.getSource();
//        textField.selectAll();
    	  if(count>=5){
    		  String str=JOptionPane.showInputDialog(getContentPane(),"","Message",JOptionPane.INFORMATION_MESSAGE);
    		  if(str!=null){
    		  if(str.equals("thentools1")){//打开隐藏log
    			  com.Main.ThenToolsRun.hidenlog=true;
    			  com.Main.ThenToolsRun.logger.log(Level.INFO,"open hiden log successful");
					JOptionPane.showMessageDialog(getContentPane(), "Open hiden log successful", 
							"Message", JOptionPane.INFORMATION_MESSAGE); 
    		  }else if(str.equals("thentools2")){//创建新的有效code  20160505,10,user,MACCODE,1
    			  String temp=JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame,"code","Message",JOptionPane.INFORMATION_MESSAGE);
    			  if(temp!=null){
    			  temp=com.Main.ThenToolsRun.crypt.createstr(temp);
    			  // 系统剪贴板
    			  StringSelection stringSelection = new StringSelection( temp);
    			  Clipboard  clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    			  clipboard.setContents(stringSelection, null);
					JOptionPane.showMessageDialog(getContentPane(), "new Code="+temp, 
							"Message", JOptionPane.INFORMATION_MESSAGE); 
    			  }
    		  }else if(str.equals("thentools3")){//解密Code
    			  String temp=JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame,"code","Message",JOptionPane.INFORMATION_MESSAGE);
    			  if(temp!=null){
    			  temp=com.Main.ThenToolsRun.crypt.decryptByDES(temp);
					JOptionPane.showMessageDialog(getContentPane(), "Decrypt="+temp, 
							"Message", JOptionPane.INFORMATION_MESSAGE); 
    			  }
    		  }
    		  else{
    			  com.Main.ThenToolsRun.logger.log(Level.INFO,"exit: error in about dialog");
    			  System.exit(0);
    		  }
    		  }
    	  }
		  count++;
    //	 com.Main.ThenToolsRun.logger.log(Level.INFO,"Open smart functions");
      }
    });

    AbstractAction actionOK = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        AboutDialogUI.this.onOK();
      }
    };
    AbstractAction actionCancel = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        AboutDialogUI.this.onCancel();
      }
    };
//    JComponent targetComponent = getRootPane();
//    InputMap inputMap = targetComponent.getInputMap();
//    inputMap.put(KeyStroke.getKeyStroke(10, 0), "OK");
//    inputMap.put(KeyStroke.getKeyStroke(27, 0), "Cancel");
//    targetComponent.setInputMap(1, inputMap);
//    targetComponent.getActionMap().put("OK", actionOK);
//    targetComponent.getActionMap().put("Cancel", actionCancel);
  }

  private void onOK()
  {
    dispose();
  }

  private void onCancel() {
    dispose();
  }
//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "关于 ThenTools";
		}else{
			return "About ThenTools";
		}
		case "buttonOK": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "了解!";
		}else{
			return "Got it";
		}
		case "labelCopyright": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "Copyright (C) 2016-2999 Then保留所有权利...";
		}else{
			return "Copyright (C) 2016-2999 Then All rights reserved.";
		}
		case "labelcomments": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "欢迎提供任何建议!";
		}else{
			return "Any suggestions, pls send us!";
		}
			default: return "";
		}	
	}
}