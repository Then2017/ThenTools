package com.Qcom;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.Functions.CheckUE;
import com.Functions.Excute;
import com.Functions.LoggerUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class WaitUItoPC extends JFrame {

	private JPanel contentPane;
	QcomActiveBootPC qcomactivebootpc=new QcomActiveBootPC();
	JLabel lblShow;
	/**
	 * Create the frame.
	 */
	public WaitUItoPC() {
		setResizable(false);
		setTitle(getString("title"));
		setSize(200,100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setAlwaysOnTop(true);
		//show label
		 lblShow = new JLabel(getString("lblShow"));
		lblShow.setFont(new Font("微软雅黑", Font.BOLD, 14));
		lblShow.setBounds(26, 10, 136, 51);
		contentPane.add(lblShow);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		
		qcomactivebootpc.run();

		//rewrite close windows
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			qcomactivebootpc.setexitloop(true);
			com.Main.ThenToolsRun.logger.log(Level.INFO,"close boot active to PC log");
			dispose();
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls plug out USB to abort logs!\n"
					+ "Logs are saved in "+qcomactivebootpc.getlogfolder(), 
					"Message", JOptionPane.INFORMATION_MESSAGE); 
			}
			}); 
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				do{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
					}
					if(!qcomactivebootpc.getActivelogthreadrun()){
						lblShow.setText(getString("lblShow1"));
					}
				}while(!qcomactivebootpc.getexitloop());
				com.Main.ThenToolsRun.logger.log(Level.INFO,"check lblShow over!");
			}
		}).start();
	}
	
	public void close(){
		com.Main.ThenToolsRun.logger.log(Level.INFO,"close boot active log to PC with successful active");
		dispose();
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "请等待...";
		}else{
			return "Pls wait...";
		}
		case "lblShow": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "正在等待设备...";
		}else{
			return "Wait for device...";
		}
		case "lblShow1": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html><font color=\"#FF0000\">正在尝试获取...</font></html>";
		}else{
			return "<html><font color=\"#FF0000\">Trying to get...</font></html>";
		}
			default: return "";
		}	
	}
}
