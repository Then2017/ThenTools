package com.Help;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.Functions.CheckPC;

import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.awt.Font;
import java.awt.Color;

public class AboutLisenceUI extends JFrame {

	private JPanel contentPane;
	JFormattedTextField formattedTextFieldCode;
	JLabel lblLeftday;
	int day;
	/**
	 * Create the frame.
	 */
	public AboutLisenceUI() {
		setTitle(getString("title"));
		setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 552);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		
		//left days
		day=com.Main.ThenToolsRun.crypt.getstrday();//get left day
		lblLeftday = new JLabel();
		if(day>0){
			lblLeftday.setText(getString("lblLeftday"));
		}else{
			lblLeftday.setText(getString("lblLeftday2"));
		}
		lblLeftday.setForeground(Color.RED);
		lblLeftday.setFont(new Font("΢���ź�", Font.BOLD, 16));
		lblLeftday.setBounds(23, 10, 317, 38);
		contentPane.add(lblLeftday);
		//TXT Code
		formattedTextFieldCode = new JFormattedTextField();
		formattedTextFieldCode.setBounds(110, 464, 310, 21);
		contentPane.add(formattedTextFieldCode);
		
		JLabel lblActive = new JLabel(getString("lblActive"));
		lblActive.setBounds(23, 467, 77, 15);
		contentPane.add(lblActive);

		//Active button
		JButton button = new JButton(getString("button"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(com.Main.ThenToolsRun.crypt.updatestr(formattedTextFieldCode.getText())){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Active lisence ok");
					JOptionPane.showMessageDialog(getContentPane(), "Thanks! Active successfully, Pls restart tool!", 
							"Message", JOptionPane.INFORMATION_MESSAGE); 
					System.exit(0);
				}else{
					com.Main.ThenToolsRun.logger.log(Level.INFO,"Active lisence fail");
					JOptionPane.showMessageDialog(getContentPane(), "Wrong Code! Pls try again later.", 
							"Message", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		button.setBounds(110, 489, 100, 25);
		contentPane.add(button);
		//TXT ID
		JFormattedTextField formattedTextFieldID = new JFormattedTextField();
		String mac=CheckPC.getMACAddress();//get mac
		String encryptmac=com.Main.ThenToolsRun.crypt.encryptToDES(mac);//encrypt mac
		com.Main.ThenToolsRun.logger.log(Level.INFO," ID: "+encryptmac);
		formattedTextFieldID.setText(encryptmac);
		formattedTextFieldID.setBounds(110, 433, 310, 21);
		contentPane.add(formattedTextFieldID);
		
		JLabel lblPcid = new JLabel(getString("lblPcid"));
		lblPcid.setBounds(23, 436, 54, 15);
		contentPane.add(lblPcid);
		//ad
		JLabel label = new JLabel(getString("label"));
		label.setForeground(Color.MAGENTA);
		label.setFont(new Font("΢���ź�", Font.BOLD, 12));
		label.setBounds(23, 51, 326, 86);
		contentPane.add(label);
		//about tools
		JButton btnAbouttools = new JButton(getString("btnAbouttools"));
		btnAbouttools.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AboutDialogUI aboutdialog=new AboutDialogUI();
				aboutdialog.setVisible(true);
			}
		});
		btnAbouttools.setBounds(320, 489, 100, 25);
		contentPane.add(btnAbouttools);
		
		JLabel lblCurrentCode = new JLabel(getString("lblCurrentCode"));
		lblCurrentCode.setBounds(23, 405, 86, 15);
		contentPane.add(lblCurrentCode);
		
		//Current Code
		JFormattedTextField formattedTextFieldCurrentCode = new JFormattedTextField();
		formattedTextFieldCurrentCode.setText(com.Main.ThenToolsRun.crypt.getcurrentstr());
		formattedTextFieldCurrentCode.setBounds(110, 402, 310, 21);
		contentPane.add(formattedTextFieldCurrentCode);
		//lblvip
		JLabel lblVIP = new JLabel("VIP");
		lblVIP.setForeground(Color.RED);
		lblVIP.setFont(new Font("΢���ź�", Font.BOLD, 16));
		lblVIP.setBounds(348, 10, 86, 38);
		contentPane.add(lblVIP);
//		//lbl 10CNY
		JLabel lbl10CNY = new JLabel("10CNY");
		lbl10CNY.setIcon(new ImageIcon(getClass().getResource("/Rescource/10CNY.jpg")));
		lbl10CNY.setBounds(140, 145, 200, 225);
		contentPane.add(lbl10CNY);
		//lbl 5CNY
//		JLabel lbl5CNY = new JLabel("5CNY");
//		lbl5CNY.setIcon(new ImageIcon(getClass().getResource("/Rescource/5CNY.jpg")));
//		lbl5CNY.setBounds(122, 131, 200, 220);
//		contentPane.add(lbl5CNY);
		
		
		lblVIP.setVisible(false);
		if(com.Main.ThenToolsRun.crypt.isvip()){
			lblVIP.setVisible(true);
		}
		//rewrite close windows
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			if(com.Main.ThenToolsRun.mainFrame==null||!com.Main.ThenToolsRun.mainFrame.isVisible()){
			com.Main.ThenToolsRun.logger.log(Level.INFO,"close ThenTools with no lisence");
			com.Main.ThenToolsRun.getdevices.terminate();
			com.Main.ThenToolsRun.dbhandle.closeDB();
			dispose();
			System.exit(0);
			}else{
			dispose();
			com.Main.ThenToolsRun.logger.log(Level.INFO,"close AboutLisenceUI");
			}
			
			}
			}); 
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "����֤��";
		}else{
			return "About Lisence";
		}
		case "lblLeftday": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "��Ч��ʣ��: "+day+" ��";
		}else{
			return "Lisence: "+day+" days left";
		}
		case "lblLeftday2": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "��Ч��ʣ��: "+day+" ��,�뼰ʱ����!";
		}else{
			return "Lisence: "+day+" days left, Pls active!";
		}
		case "lblActive": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "�¼�����:";
		}else{
			return "New Code:";
		}
		case "button": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "����";
		}else{
			return "Active";
		}
		case "lblPcid": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "���:";
		}else{
			return "ID:";
		}
		case "btnAbouttools": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "����";
		}else{
			return "Detial";
		}
		case "lblCurrentCode": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "��ǰ������:";
		}else{
			return "Current Code:";
		}
		case "label": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html>"
				    + "����:<br>"
				    + "&nbsp;&nbsp;&nbsp;&nbsp;��Ա: 10Ԫ/30��/1PC (û�й��,����ʶ)<br>"
				    +"&nbsp;&nbsp;&nbsp;&nbsp;��������֧��,���ǵĲ�Ʒ���ܸ��õĿ�����ά��!<br>"
				    + "&nbsp;&nbsp;&nbsp;&nbsp;֧��֧��������,��ɨ��֧������ϵ����..."
				    + "</html>";
		}else{
			return "<html>"
				    + "Want:<br>"
				    + "&nbsp;&nbsp;&nbsp;&nbsp;VIP: 10 CNY/30days/1PC (No AD)<br>"
				    +"&nbsp;&nbsp;&nbsp;&nbsp;We can develop and maintain better with your support!<br>"
				    + "</html>";
		}
			default: return "";
		}	
	}
}
