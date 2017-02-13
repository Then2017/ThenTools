package com.Main;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.CheckTP.CheckTP;
import com.CheckTP.TPShotUI;
import com.Functions.ChimpBridge;
import com.Functions.LineNumberHeaderView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

public class CheckTPUImain extends JPanel {
	CheckTP checktp=new CheckTP();
	TPShotUI TPshotui;
	JTextArea textAreaShowXY;
	JLabel lblColorinfo;
	String red="<font color=\"#FF0000\">红色</font>";
	String green="<font color=\"#00FF00\">绿色</font>";
	String blue="<font color=\"#0000FF\">蓝色</font>";
	JLabel lblMouse;
	JLabel lblResolution;
	JLabel lblLandspcaceMode;
	JCheckBox chckbxlandscapeMode;
	JScrollPane scrollPaneShowXY;
	
	boolean isstartrecord=false;
	JButton btnStart;
	/**
	 * Create the panel.
	 */
	public CheckTPUImain() {
		setSize(700,650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		TPshotui=checktp.getScreenShotUI();
		add(TPshotui);
		TPshotui.setVisible(false);
		//show XY textArea
		textAreaShowXY = new JTextArea();
		textAreaShowXY.setWrapStyleWord(true);
		textAreaShowXY.setLineWrap(true);
		textAreaShowXY.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPaneShowXY = new JScrollPane(textAreaShowXY);
		scrollPaneShowXY.setBounds(262, 194, 245, 398);
		add(scrollPaneShowXY);
		//行号
		LineNumberHeaderView lineNumberHeader = new LineNumberHeaderView();
		scrollPaneShowXY.setRowHeaderView(lineNumberHeader);
		checktp.settextAreaShowXY(textAreaShowXY);//checkTP add textArea
		//鼠标信息
		lblMouse = new JLabel("");
		lblMouse.setBounds(191, 175, 78, 15);
		add(lblMouse);
		TPshotui.setlblMouse(lblMouse);
		//分辨率
		lblResolution = new JLabel("");
		lblResolution.setBounds(262, 175, 153, 15);
		add(lblResolution);
		checktp.setlblResolution(lblResolution);
		//向左横屏提示
		lblLandspcaceMode = new JLabel(getString("lblLandspcaceMode"));
		lblLandspcaceMode.setBounds(532, 175, 153, 15);
		add(lblLandspcaceMode);
		//lable check tp
		JLabel lblCheckTP = new JLabel(getString("lblCheckTP"));
		lblCheckTP.setVerticalAlignment(SwingConstants.TOP);
		lblCheckTP.setFont(new Font("微软雅黑", Font.BOLD, 26));
		lblCheckTP.setBounds(10, 145, 162, 35);
		add(lblCheckTP);
		
		//monitor button
		 btnStart = new JButton(getString("btnStart"));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isstartrecord){
				//acivelogthreadrun true
				if(checktp.getChecktpthreadrun()){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"getChecktpthreadrun =true");
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "ThenTools are working hard, pls wait...", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"start monitor button");
					return;
				}
				//device null
				if(com.Main.ThenToolsRun.selectedID==null){
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "No devices checked", 
							"Message", JOptionPane.ERROR_MESSAGE); 
					com.Main.ThenToolsRun.logger.log(Level.INFO,"start monitor button");
					return;
				}
				checktp.setiscancelled(true);
				checktp.run();
				//lblisrecord.setText(getString("lblisrecord"));
				TPshotui.setVisible(true);
				//change
				isstartrecord=true;
				btnStart.setText(getString("btnStop"));
				btnStart.setForeground(Color.RED);
				com.Main.ThenToolsRun.logger.log(Level.INFO,"start monitor button");
				}else{
					checktp.setiscancelled(true);
					//change 
					isstartrecord=false;
					btnStart.setText(getString("btnStart"));
					btnStart.setForeground(Color.BLACK);
				//	lblisrecord.setText(getString("lblisrecord1"));
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop TP Monitor!", 
							"Message", JOptionPane.INFORMATION_MESSAGE);
					com.Main.ThenToolsRun.logger.log(Level.INFO,"stop monitor button");
				}
				
			}
		});
		btnStart.setBounds(532, 272, 100, 25);
		add(btnStart);
		//stop button
//		JButton btnStop = new JButton(getString("btnStop"));
//		btnStop.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				checktp.setiscancelled(true);
//				lblisrecord.setText(getString("lblisrecord1"));
//				JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Stop TP Monitor!", 
//						"Message", JOptionPane.INFORMATION_MESSAGE);
//				com.Main.ThenToolsRun.logger.log(Level.INFO,"stop monitor button");
//			}
//		});
//		btnStop.setBounds(532, 412, 100, 25);
//		add(btnStop);
		
		//clear button
		JButton btnClear = new JButton(getString("btnClear"));
		btnClear.setToolTipText(getString("btnCleartip"));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checktp.mFBImage!=null){
					checktp.ClearScreen();	
				}else{
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls start monitor first!", 
							"Message", JOptionPane.ERROR_MESSAGE);
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"clear screen button");
			}
		});
		btnClear.setBounds(532, 342, 100, 25);
		add(btnClear);
		
		//lbl Colorinfo
		lblColorinfo = new JLabel(getString("lblColorinfo"));
		lblColorinfo.setVerticalAlignment(SwingConstants.TOP);
		lblColorinfo.setBounds(532, 194, 110, 66);
		add(lblColorinfo);
		
		//change color button
		JButton btnChangeColor = new JButton(getString("btnChangeColor"));
		btnChangeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int color=TPshotui.setColor();
		    	if(color==1){
					lblColorinfo.setText(getString("lblColorinfo"));
		    	}else if(color==2){
					lblColorinfo.setText(getString("lblColorinfo1"));
		    	}else if(color==3){
					lblColorinfo.setText(getString("lblColorinfo2"));
		    	}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"change color button");
			}
		});
		btnChangeColor.setBounds(532, 307, 100, 25);
		add(btnChangeColor);
		//refresh image button
		JButton btnRefresh = new JButton(getString("btnRefresh"));
		btnRefresh.setToolTipText(getString("btnRefreshtip"));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(checktp.mFBImage!=null){
					checktp.RefreshScreen();
				}else{
					JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls start monitor first!", 
							"Message", JOptionPane.ERROR_MESSAGE);
				}
				com.Main.ThenToolsRun.logger.log(Level.INFO,"refresh image button");
			}
		});
		btnRefresh.setBounds(532, 377, 100, 25);
		add(btnRefresh);
		
		//landscapeMode checkbox
//		chckbxlandscapeMode = new JCheckBox(getString("chckbxlandscapeMode"));
//		chckbxlandscapeMode.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e){
//					if(chckbxlandscapeMode.isSelected()){
//						TPshotui.setmPortrait(false);
//						TPshotui.setSize(false);
//						checktp.setmPortrait(false);
//						textAreaShowXY.setBounds(20, 420, 400, 170);
//						scrollPaneShowXY.setBounds(20, 420, 400, 170);
//					}else{
//						TPshotui.setmPortrait(true);
//						checktp.setmPortrait(true);
//						TPshotui.setSize(true);
//						textAreaShowXY.setBounds(260, 190, 230, 400);
//						scrollPaneShowXY.setBounds(260, 190, 230, 400);
//					}
//			}
//		}); 
//		chckbxlandscapeMode.setBounds(391, 162, 180, 23);
//		add(chckbxlandscapeMode);
		

		

	}
	//setLandscapeMode
	public void setmPortraitMode(boolean mPortrait){
		if(!mPortrait){
			TPshotui.setmPortrait(false);
			TPshotui.setmPortraitSize(false);
			checktp.setmPortrait(false);
			textAreaShowXY.setBounds(20, 420, 400, 170);
			scrollPaneShowXY.setBounds(20, 420, 400, 170);
			textAreaShowXY.setCaretPosition(textAreaShowXY.getText().length());
		}else{
			TPshotui.setmPortrait(true);
			checktp.setmPortrait(true);
			TPshotui.setmPortraitSize(true);
			textAreaShowXY.setBounds(260, 190, 230, 400);
			scrollPaneShowXY.setBounds(260, 190, 230, 400);
			textAreaShowXY.setCaretPosition(textAreaShowXY.getText().length());
		}
	}
	//get isstartrecord
	public boolean getisstartrecord(){
		return isstartrecord;
	}
	//get Btn startrecord
	public JButton getbtnStart(){
		return btnStart;
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblCheckTP": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "TP监控:";
		}else{
			return "TPMonitor:";
		}
		case "btnStart": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "开始监控";
		}else{
			return "StartMonitor";
		}
			
		case "btnStop": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "停止监控";
		}else{
			return "StopMonitor";
		}
		case "btnClear": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "清除";
		}else{
			return "Clear";
		}
		case "btnCleartip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "清除图像上的痕迹和显示的触摸数据";
		}else{
			return "Clear the marks of image and the data of touch";
		}
		case "lblColorinfo": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html>Tap点击:"+red+"<br>"
					+ "Long tap长安:"+green+"<br>"
					+ "Drag滑动: "+blue+"</html>";
		}else{
			return "<html>Tap:"+red+"<br>"
					+ "Long tap: "+green+"<br>"
					+ "Drag: "+blue+"</html>";
		}
		case "lblColorinfo1": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html>Tap点击:"+green+"<br>"
					+ "Long tap长安:"+blue+"<br>"
					+ "Drag滑动: "+red+"</html>";
		}else{
			return "<html>Tap:"+green+"<br>"
					+ "Long tap: "+blue+"<br>"
					+ "Drag: "+red+"</html>";
		}
		case "lblColorinfo2": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html>Tap点击:"+blue+"<br>"
					+ "Long tap长安:"+red+"<br>"
					+ "Drag滑动: "+green+"</html>";
		}else{
			return "<html>Tap:"+blue+"<br>"
					+ "Long tap: "+red+"<br>"
					+ "Drag: "+green+"</html>";
		}
		case "btnChangeColor": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "改变颜色";
		}else{
			return "Color";
		}
		case "btnRefresh": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "刷新";
		}else{
			return "Refresh";
		}
		case "btnRefreshtip": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "重新截图设备的图片";
		}else{
			return "Refresh the image from device";
		}
		case "lblLandspcaceMode": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "<html><font color=\"#FF0000\">支持向左横屏</font></html>";
		}else{
			return "<html><font color=\"#FF0000\">Support Left Landspace</font></html>";
		}
		case "chckbxlandscapeMode": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "向左横屏模式";
		}else{
			return "Left Landspace";
		}
			default: return "";
		}	
	}
}
