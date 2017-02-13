package com.AD;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.Functions.LoggerUtil;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class ADShow extends JFrame {

	private JPanel contentPane;
	ImageIcon image;
	Random random;
	JLabel lblAD1;
	JButton closeButton;
	JLabel lblCountdown;
	/**
	 * Create the frame.
	 */
	public ADShow() {
		setBounds(100, 100, 750, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(contentPane);
		//setAlwaysOnTop(true);
		setUndecorated(true);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		//this.setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
		
		//关闭按钮 
		random=new Random();//random
		closeButton = new JButton(getString("closeButton"));
		closeButton.setVisible(false);
		closeButton.setBounds(random.nextInt(600), random.nextInt(520), 100, 25);
		closeButton.addActionListener(new ActionListener() { 
			@Override  
			public void actionPerformed(ActionEvent e) { 
					setVisible(false);
					dispose();
					image=null;
				}
			}); 
		getContentPane().add(closeButton);
		//AD
		JLabel lblAd = new JLabel(getString("lblAd"));
		lblAd.setForeground(Color.RED);
		lblAd.setFont(new Font("微软雅黑", Font.BOLD, 18));
		lblAd.setBounds(103, 582, 605, 58);
		contentPane.add(lblAd);
		
		//AD1
		lblAD1 = new JLabel();
		lblAD1.setHorizontalAlignment(SwingConstants.CENTER);
		lblAD1.setBounds(27, 10, 700, 550);
	//	lblAD1.setIcon(getRandomImage());
		contentPane.add(lblAD1);
		
		//lbl countdown
		lblCountdown = new JLabel("");
		lblCountdown.setForeground(Color.RED);
		lblCountdown.setFont(new Font("微软雅黑", Font.BOLD, 14));
		lblCountdown.setBounds(619, 497, 121, 33);
		contentPane.add(lblCountdown);

		
	}
	//show 
	public void run(){
		if(!com.Main.ThenToolsRun.crypt.isvip()){//not vip ,show ad
		//	if(!this.isVisible()){
			setVisible(false);
			setVisible(true);
				closeButton.setVisible(false);
				lblCountdown.setVisible(true);
				lblAD1.setIcon(getRandomImage());
				setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
				setVisible(true);
				lblCountdown.setText("");
				setAlwaysOnTop(true);
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int i=random.nextInt(5)+5;
						while(i>=0){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
						}
						lblCountdown.setText(getString("lblCountdown")+i+getString("lblCountdown1"));
						i--;
						}
						closeButton.setVisible(true);
						lblCountdown.setVisible(false);
						setAlwaysOnTop(false);
					}
				}).start();
//			}else{
//				setVisible(false);
//				setVisible(true);
//			}
		}
	}
	//random ad picture show
	public ImageIcon getRandomImage(){
//		int result=random.nextInt(100)%2;
//		switch (result){
//		case 0: image = new ImageIcon(getClass().getResource("/AD/AD1.jpg"));break;
//		case 1: image = new ImageIcon(getClass().getResource("/AD/AD2.jpg"));break;
//		default : image = new ImageIcon(getClass().getResource("/AD/AD2.jpg"));break;
//		}
		int result=random.nextInt(12)+1;
		image = new ImageIcon(getClass().getResource("/AD/"+result+".jpg"));
		return image;
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblAd": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html>欢迎投资黄金广告位!<br>"
					+ "仅需多付十块钱,没有广告,尊贵标识,VIP值得拥有!</html>";
		}else{
			return "<html>Golden Advertisement, welcom to your invest!<br>"
					+ "Just add more 10 CNY to be VIP, you won't see AD again!</html>";
		}
		case "closeButton": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "跳过";
		}else{
			return "Skip";
		}
		case "lblCountdown": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "广告剩余";
		}else{
			return "AD left ";
		}
		case "lblCountdown1": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "秒";
		}else{
			return "S";
		}
			default: return "";
		}	
	}
}
