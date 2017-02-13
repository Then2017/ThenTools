package com.Qcom;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class WaitUI extends JFrame {

	private JPanel contentPane;
	QcomActiveBoot qcomactiveboot=new QcomActiveBoot();
	/**
	 * Create the frame.
	 */
	public WaitUI() {
		setResizable(false);
		setTitle(getString("title"));
		setSize(200,100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setAlwaysOnTop(true);
		//show label
		JLabel lblShow = new JLabel(getString("lblShow"));
		lblShow.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 14));
		lblShow.setBounds(26, 10, 136, 51);
		contentPane.add(lblShow);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		
		qcomactiveboot.run();
		
		//rewrite close windows
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			qcomactiveboot.setexitloop(true);
			com.Main.ThenToolsRun.logger.log(Level.INFO,"close boot active log");
			dispose();
			}
			}); 
	}
	
	public void close(){
		com.Main.ThenToolsRun.logger.log(Level.INFO,"close boot active log with successful active");
		dispose();
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ÇëµÈ´ý...";
		}else{
			return "Pls wait...";
		}
		case "lblShow": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "ÕýÔÚ³¢ÊÔ¼¤»î...";
		}else{
			return "Trying to active...";
		}
			default: return "";
		}	
	}
}
