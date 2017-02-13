package com.GetScreen;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;

import java.awt.Font;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.awt.Color;

public class ScreenRecordUI extends JFrame {

	private JPanel contentPane;
	JComboBox Resolutionlist;
	String WidthandHeight="WVGA 480X800";
	ScreenRecord screenrecord=new ScreenRecord();
	JButton btnStart;
	JButton btnGet;
	JLabel lblCountdown;
	boolean isstart=false;
	int timecount=0;
	Timer timer; 
	/**
	 * Create the frame.
	 */
	public ScreenRecordUI() {
		setResizable(false);
		setTitle(getString("title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 433, 295);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
		
		JLabel labelvideopathPC = new JLabel(getString("labelvideopathPC"));
		labelvideopathPC.setVerticalAlignment(SwingConstants.TOP);
		labelvideopathPC.setBounds(10, 221, 236, 36);
		contentPane.add(labelvideopathPC);
		
		//Start
		btnStart = new JButton(getString("btnStart"));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!isstart){
					btnGet.setEnabled(false);
					timecount=180;
					screenrecord.run(WidthandHeight);
					isstart=true;
					btnStart.setText(getString("btnStop")); 
						timer = new Timer();
					    timer.schedule(new TimerTask(){
					    	public void run(){
					    			SwingUtilities.invokeLater(new Runnable() {   
					    			      @Override  
					    			      public void run() {
					    			    	  if(timecount>=0){
								    			lblCountdown.setText(getString("lblCountdown2"));
								    			timecount--;
					    			    	  }else{
					    							screenrecord.setiscancel(true);
					    							isstart=false;
					    							btnStart.setText(getString("btnStart"));
					    							com.Main.ThenToolsRun.logger.log(Level.INFO,"timecout<0 stop auto");
					    							if(timer!=null){
					    								timer.cancel();
					    							}
					    			    	  }
					    			      }   
					    			  }); 
					    	}
					    },0,1000); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"start screen record button");
				}else{
					btnGet.setEnabled(true);
					if(timer!=null){
						timer.cancel();
					}
					screenrecord.setiscancel(true);
					isstart=false;
					btnStart.setText(getString("btnStart"));
					com.Main.ThenToolsRun.logger.log(Level.INFO,"stop screen record button");
				}

			}
		});
		btnStart.setBounds(306, 126, 100, 25);
		contentPane.add(btnStart);
		
		//Cancel
		JButton btnCancel = new JButton(getString("btnCancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGet.setEnabled(true);
				if(timer!=null){
					timer.cancel();
				}
				screenrecord.setiscancel(true);
				isstart=false;
				btnStart.setText("Start");
				com.Main.ThenToolsRun.logger.log(Level.INFO,"cancel screen record button");
				dispose();
			}
		});
		btnCancel.setBounds(306, 196, 100, 25);
		contentPane.add(btnCancel);
		
		//resolution list
		Resolutionlist = new JComboBox();
		Resolutionlist.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
			 if(arg0.getStateChange()==1){
				 	WidthandHeight = (String)Resolutionlist.getSelectedItem();
				 	com.Main.ThenToolsRun.logger.log(Level.INFO,"choose "+WidthandHeight);
		    	}
			}
		});
		Resolutionlist.setBounds(20, 24, 160, 23);
		Resolutionlist.setModel(new DefaultComboBoxModel(new String[] {"WVGA 480X800", "FHD 1080X1920", "HD 720x1280", "QHD 540X960", "FWVGA 480X854", "HVGA 320X480"}));
		Resolutionlist.setSelectedItem("WVGA 480X800");
		contentPane.add(Resolutionlist);
		
		JLabel lblResolution = new JLabel(getString("lblResolution"));
		lblResolution.setBounds(10, 7, 92, 15);
		contentPane.add(lblResolution);
		
		//countdown time
		lblCountdown = new JLabel(getString("lblCountdown"));
		lblCountdown.setForeground(Color.CYAN);
		lblCountdown.setFont(new Font("微软雅黑", Font.BOLD, 35));
		lblCountdown.setBounds(114, 57, 236, 60);
		contentPane.add(lblCountdown);
		
		//get video
		btnGet = new JButton(getString("btnGet"));
		btnGet.setToolTipText(getString("btnGettip"));
		//btnGet.setEnabled(false);
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//getvideofromuethreadrun true
				if(screenrecord.getVideofromUEthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"activelogthreadrun =true");
					JOptionPane.showMessageDialog(contentPane, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					return;
				}
				//device null
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(contentPane, "No devices checked", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"get screen record: no devices");
					return;
				}
				
				screenrecord.getVideofromUE();
				com.Main.ThenToolsRun.logger.log(Level.INFO,"get screen record button");
			}
		});
		btnGet.setBounds(306, 161, 100, 25);
		contentPane.add(btnGet);
		
		JLabel labelvideopathUE = new JLabel(getString("labelvideopathUE"));
		labelvideopathUE.setVerticalAlignment(SwingConstants.TOP);
		labelvideopathUE.setBounds(10, 175, 236, 36);
		contentPane.add(labelvideopathUE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(com.Main.ThenToolsRun.mainFrame);
		
		addWindowListener(new WindowAdapter() {


			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				btnGet.setEnabled(true);
				if(timer!=null){
					timer.cancel();
				}
				screenrecord.setiscancel(true);
				isstart=false;
				btnStart.setText(getString("btnStart"));
				com.Main.ThenToolsRun.logger.log(Level.INFO,"close screen record");
				dispose();
			}

		}); 
	}
	
	public JButton getbtnStart(){
		return btnStart;
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "屏幕录像";
		}else{
			return "Screen Record";
		}
		case "labelvideopathPC": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "<html>PC存储路径:<br>桌面/ThenLog/ScreenRecord</html>";
		}else{
			return "<html>The PC video directory :<br>%Desktop%/ThenLog/ScreenRecord</html>";
		}
		case "btnStart": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "开始";
		}else{
			return "Start";
		}
		case "btnStop": //==========btnStart
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "停止";
		}else{
			return "Stop";
		}
		case "btnCancel": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "取消";
		}else{
			return "Cancel";
		}
		case "lblResolution": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "分辨率";
		}else{
			return "Resolution:";
		}
		case "lblCountdown": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "最长: 180秒";
		}else{
			return "Max: 180S";
		}
		case "lblCountdown2": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+timecount+"秒</html>";
		}else{
			return "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+timecount+"S</html>";
		}
		case "btnGet": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "获取录像";
		}else{
			return "Get";
		}
		case "btnGettip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "提取设备中获取刚才录制的视频到PC";
		}else{
			return "Get the latest record video from device to PC";
		}
		case "labelvideopathUE": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
				return "<html>设备存储路径 :<br>/sdcard/Movies</html>";
		}else{
			return "<html>The UE video directory :<br>/sdcard/Movies</html>";
		}
			default: return "";
		}	
	}
	
}
