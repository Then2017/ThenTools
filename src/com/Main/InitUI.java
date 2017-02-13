package com.Main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.Functions.LoggerUtil;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;

public class InitUI extends JFrame {

	private JPanel contentPane;
	ImageIcon image;
	Random random;
	JLabel lblAD1;
	/**
	 * Create the frame.
	 */
	public InitUI() {
		ImageIcon imagetemp = new ImageIcon(getClass().getResource("/icon/logo.jpg"));
		com.Main.ThenToolsRun.imagelogo=imagetemp.getImage();
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
		

		random=new Random();//random
		
		//AD
		JLabel lblAd = new JLabel(getString("lblAd"));
		lblAd.setForeground(Color.CYAN);
		lblAd.setFont(new Font("微软雅黑", Font.BOLD, 18));
		lblAd.setBounds(252, 582, 409, 58);
		contentPane.add(lblAd);
		
		//AD1
		lblAD1 = new JLabel();
		lblAD1.setHorizontalAlignment(SwingConstants.CENTER);
		lblAD1.setBounds(27, 10, 700, 550);
	//	lblAD1.setIcon(getRandomImage());
		contentPane.add(lblAD1);

		
	}
	//show 
	public void run(){
			lblAD1.setIcon(getRandomImage());
			setLocationRelativeTo(null);
			setVisible(true);
			setAlwaysOnTop(true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setVisible(false);
					dispose();
					image=null;
				}
			}).start();
	}
	//random ad picture show
	public ImageIcon getRandomImage(){
		int result=random.nextInt(100)%2;
		switch (result){
//		case 0: image = new ImageIcon(getClass().getResource("/Rescource/Wait.jpg"));break;
//		case 1: image = new ImageIcon(getClass().getResource("/AD/AD2.jpg"));break;
		default : image = new ImageIcon(getClass().getResource("/Rescource/Wait.jpg"));break;
		}
		return image;
	}
	
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblAd": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html>我们正在努力打开中...</html>";
		}else{
			return "<html>We are work hard for init!</html>";
		}
		case "closeButton": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "关闭";
		}else{
			return "Close";
		}
			default: return "";
		}	
	}
	
}
