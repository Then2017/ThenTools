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
		lblThenHelperAPK.setFont(new Font("微软雅黑", Font.BOLD, 26));
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
			return "安装";
		}else{
			return "Install";
		}
		case "lblIntroduce": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html>**************主要功能(支持Android 5.0及以上版本)**************<br>"
					+ "1.主页:提供手机基本信息<br>"
					+ "<font color=\"#0000FF\">2.电池监控:采用前台Service,防止被kill,提供记录电池信息,分析电池信息,折线图,是否休眠,是否常亮屏幕等功能.</font><br>"
					+ "3.辅助功能:提供创建合法的IMEI/MAC地址/一直震动/调节亮度等.<br>"
					+ "4.填充联系人/通话记录:提供各种自定义选项<br>"
					+ "5.填充短信/彩信/音乐/视频/图片:提供各种自定义选项<br>"
					+ "6.填充SD卡/手机存储/Data分区<br>"
					+ "7.Sensor监控:提供手机所有Sensor数据的监控,并可绘制图形<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;a.PRESSURE sensor额外提供海拔数据.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;b.ORIENTATION sensor额外提供指南针.<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;c.LIGHT sensor额外提供屏幕亮度值数据.<br>"
					+ "8.悬浮窗监控:<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;a.屏幕亮度/当前电流/平均电流/卡1信号强度,网络类型,运营商,LAC/CID基站信息<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;b.WIFI信号强度,频段,信道,加密方式,蓝牙信号强度,系统内存/CPU使用率,涂鸦功能<br>"
					+ "<font color=\"#0000FF\">9.屏幕录制:提供屏幕录制功能,录音,分辨率,显示触摸轨迹,比特率,文件名,倒计时,录制延迟,以悬浮窗形式启动(额外有涂鸦功能).</font><br>"
					+ "</html>";
		}else{
			return "Install ThenHelper.apk";
		}
			default: return "";
		}
	}
}
