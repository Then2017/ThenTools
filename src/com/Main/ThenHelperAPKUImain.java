package com.Main;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ThenHelper.ThenHelper;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.SwingConstants;
import java.awt.Color;

public class ThenHelperAPKUImain extends JPanel {
	ThenHelper thenhelper=new ThenHelper();
	/**
	 * Create the panel.
	 */
	public ThenHelperAPKUImain() {
		setSize(700,650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel lblThenHelperAPK = new JLabel(getString("lblThenHelperAPK"));
		lblThenHelperAPK.setVerticalAlignment(SwingConstants.TOP);
		lblThenHelperAPK.setFont(new Font("΢���ź�", Font.BOLD, 26));
		lblThenHelperAPK.setBounds(10, 145, 245, 40);
		add(lblThenHelperAPK);
		
		//install thenhelper
		JButton btnInstallThenhelper = new JButton(getString("btnInstallThenhelper")+" "+thenhelper.getAPKname());
		btnInstallThenhelper.setForeground(Color.BLACK);
		btnInstallThenhelper.setToolTipText(getString("btnInstallThenhelpertip"));
		btnInstallThenhelper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//installthenhelperthreadrun true
				if(thenhelper.getInstallthenhelperthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"installthenhelperthreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"install thenhelper button running");
					return;
				}
				if(com.Main.ThenToolsRun.getdevices.getDevice().getApiLevel()<21){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"API="+com.Main.ThenToolsRun.getdevices.getDevice().getApiLevel()+" can't install");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenHelper.apk needs Android L or later version! (API>=21)", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"install thenhelper button running");
					return;
				}
				com.Main.ThenToolsRun.mainFrame.progressBarmain.setValue(10);//******************
				thenhelper.installThenHelper();
			}
		});
		btnInstallThenhelper.setBounds(337, 196, 320, 25);
		add(btnInstallThenhelper);
		//lbl picture
		JLabel lblPicture = new JLabel("");
		lblPicture.setIcon(new ImageIcon(getClass().getResource("/Rescource/ThenHelper.jpg")));
		lblPicture.setBounds(10, 191, 269, 422);
		add(lblPicture);
		//lbl lblIntroduce
		JLabel lblIntroduce = new JLabel(getString("lblIntroduce"));
		lblIntroduce.setVerticalAlignment(SwingConstants.TOP);
		lblIntroduce.setBounds(324, 231, 366, 382);
		add(lblIntroduce);
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblThenHelperAPK": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ThenHelper:";
		}else{
			return "ThenHelper:";
		}
		case "btnInstallThenhelper": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "��װ";
		}else{
			return "Install";
		}
		case "lblIntroduce": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html>**************��Ҫ����(֧��Android 5.0�����ϰ汾)**************<br>"
					+ "1.��ҳ:�ṩ�ֻ�������Ϣ<br>"
					+ "<font color=\"#0000FF\">2.��ؼ��:����ǰ̨Service,��ֹ��kill,�ṩ��¼�����Ϣ,���������Ϣ,����ͼ,�Ƿ�����,�Ƿ�����Ļ�ȹ���.</font><br>"
					+ "3.��������:�ṩ�����Ϸ���IMEI/MAC��ַ/һֱ��/�������ȵ�.<br>"
					+ "4.�����ϵ��/ͨ����¼:�ṩ�����Զ���ѡ��<br>"
					+ "5.������/����/����/��Ƶ/ͼƬ:�ṩ�����Զ���ѡ��<br>"
					+ "6.���SD��/�ֻ��洢/Data����<br>"
					+ "7.Sensor���:�ṩ�ֻ�����Sensor���ݵļ��,���ɻ���ͼ��<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;a.PRESSURE sensor�����ṩ��������.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;b.ORIENTATION sensor�����ṩָ����.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;c.LIGHT sensor�����ṩ��Ļ����ֵ����.<br>"
					+ "8.���������:<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;a.��Ļ����/��ǰ����/ƽ������/��1�ź�ǿ��,��������,��Ӫ��,LAC/CID��վ��Ϣ<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;b.WIFI�ź�ǿ��,Ƶ��,�ŵ�,���ܷ�ʽ,�����ź�ǿ��,ϵͳ�ڴ�/CPUʹ����,Ϳѻ����<br>"
					+ "<font color=\"#0000FF\">9.��Ļ¼��:�ṩ��Ļ¼�ƹ���,¼��,�ֱ���,��ʾ�����켣,������,�ļ���,����ʱ,¼���ӳ�,����������ʽ����(������Ϳѻ����).</font><br>"
					+ "</html>";
		}else{
			return "Install ThenHelper.apk";
		}
			default: return "";
		}
	}
}
