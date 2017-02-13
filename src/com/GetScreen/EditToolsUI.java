package com.GetScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class EditToolsUI extends JPanel {
	ScreenShotUI screenshotui;
	private JToolBar toolBarDraw1;//���尴ť���
	//���幤����ͼ�������
	private String names[] = {"newfile","openfile","savefile","pen","line"
			   ,"rect","frect","oval","foval","circle","fcircle"
			   ,"roundrect","froundrect","rubber","color"
			   ,"stroke","word","revoke"};//���幤����ͼ�������
	private Icon icons[];//����ͼ������
	
	private String tiptext[] = getString("tiptext").split(",");
	 JButton buttonDraw[];//���幤�����еİ�ť��
	 JCheckBox chckbxBold;//bold
   	String[] fontName; 
	private JComboBox stytles ;//�������е��������ʽ�������б�
	JLabel lblCoordinate;//���λ��
	JLabel lblDrawinfo;//Draw info
	int tiptextnum=3;
	/**
	 * Create the panel.
	 */
	public EditToolsUI() {
	      setSize(300,200);
	      setLocation(529,10);//location ui
	      setLayout(null);
	      //lbl edit tools
	      JLabel lblEditTools = new JLabel(getString("lblEditTools"));
	      lblEditTools.setVerticalAlignment(SwingConstants.TOP);
	      lblEditTools.setFont(new Font("΢���ź�", Font.BOLD, 12));
	      lblEditTools.setBounds(0, 0, 110, 23);
	      add(lblEditTools);
	
	      //Tools bar draw1
	      toolBarDraw1 = new JToolBar(JToolBar.HORIZONTAL);
	      toolBarDraw1.setFloatable(false);
	      toolBarDraw1.setBounds(10, 20, 230, 28);
	      add(toolBarDraw1);
		   icons = new ImageIcon[names.length];
		   buttonDraw = new JButton[names.length];
		    for(int i = 3 ;i<8;i++)
		    {
		    	final int tempint=i;
		        icons[i] = new ImageIcon(getClass().getResource("/icon/"+names[i]+".jpg"));//���ͼƬ������·��Ϊ��׼��
		        buttonDraw[i] = new JButton("",icons[i]);//�����������еİ�ť
		        buttonDraw[i].setToolTipText(tiptext[i]);//����������Ƶ���Ӧ�İ�ť�ϸ�����Ӧ����ʾ
		        buttonDraw[i].setSize(35, 25);
		        toolBarDraw1.add(buttonDraw[i]);
		    //	buttonDraw[i].setBackground(Color.red);
		    	buttonDraw[i].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						screenshotui.setCurrentChoice(tempint);
						screenshotui.createNewitem();
						screenshotui.repaint();
						tiptextnum=tempint;
						setlblDrawinfo(tiptext[tempint]);
					}
		    		
		    	});
		    }
		      //Tools bar draw2
		    JToolBar toolBarDraw2 = new JToolBar(SwingConstants.HORIZONTAL);
		    toolBarDraw2.setFloatable(false);
		    toolBarDraw2.setBounds(10, 53, 230, 28);
		    add(toolBarDraw2);
		    for(int i = 8 ;i<13;i++){
		    		final int tempint=i;
			        icons[i] = new ImageIcon(getClass().getResource("/icon/"+names[i]+".jpg"));//���ͼƬ������·��Ϊ��׼��
			        buttonDraw[i] = new JButton("",icons[i]);//�����������еİ�ť
			        buttonDraw[i].setToolTipText(tiptext[i]);//����������Ƶ���Ӧ�İ�ť�ϸ�����Ӧ����ʾ
			        buttonDraw[i].setSize(35, 25);
			        toolBarDraw2.add(buttonDraw[i]);
			    //	buttonDraw[i].setBackground(Color.red);
			    	buttonDraw[i].addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							screenshotui.setCurrentChoice(tempint);
							screenshotui.createNewitem();
							screenshotui.repaint();
							tiptextnum=tempint;
							setlblDrawinfo(tiptext[tempint]);
						}
			    		
			    	});
			}
		     //button draw13 Eraser
		      JButton btnDraw13 = new JButton("",new ImageIcon(getClass().getResource("/icon/"+names[13]+".jpg")));
		      btnDraw13.setToolTipText(tiptext[13]);
		      btnDraw13.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						screenshotui.setCurrentChoice(13);
						screenshotui.createNewitem();
						screenshotui.repaint();
						tiptextnum=13;
						setlblDrawinfo(tiptext[tiptextnum]);
					    com.Main.ThenToolsRun.logger.log(Level.INFO,"tap draw13 Eraser button");
					}
				});
		      btnDraw13.setBounds(45, 81, 35, 25);
		      add(btnDraw13);
		      //button draw14 color
		      JButton btnDraw14Color = new JButton("",new ImageIcon(getClass().getResource("/icon/"+names[14]+".jpg")));
		      btnDraw14Color.setToolTipText(tiptext[14]);
		      btnDraw14Color.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						screenshotui.chooseColor();
						setlblDrawinfo(tiptext[tiptextnum]);
					    com.Main.ThenToolsRun.logger.log(Level.INFO,"tap draw14 color button");
					}
				});
		      btnDraw14Color.setBounds(150, 81, 35, 25);
		      add(btnDraw14Color);
		      //button draw15 Thickness
		      JButton btnDraw15Thickness = new JButton("",new ImageIcon(getClass().getResource("/icon/"+names[15]+".jpg")));
		      btnDraw15Thickness.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						screenshotui.setStroke();
						setlblDrawinfo(tiptext[tiptextnum]);
					    com.Main.ThenToolsRun.logger.log(Level.INFO,"tap draw15 Thickness button");
					}
				});
		      btnDraw15Thickness.setToolTipText(tiptext[15]);
		      btnDraw15Thickness.setBounds(115, 81, 35, 25);
		      add(btnDraw15Thickness);
		      //button draw16 Text
		      JButton btnDraw16Text = new JButton("",new ImageIcon(getClass().getResource("/icon/"+names[16]+".jpg")));
		      btnDraw16Text.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
//						JOptionPane.showMessageDialog(null, "�뵥��������ȷ���������ֵ�λ�ã�","��ʾ"
//								,JOptionPane.INFORMATION_MESSAGE); 
						screenshotui.setCurrentChoice(14);
						screenshotui.createNewitem();
						screenshotui.repaint();
						tiptextnum=16;
						setlblDrawinfo(tiptext[tiptextnum]);
					    com.Main.ThenToolsRun.logger.log(Level.INFO,"tap draw16 Text button");
					}
				});
		      btnDraw16Text.setToolTipText(tiptext[16]);
		      btnDraw16Text.setBounds(80, 81, 35, 25);
		      add(btnDraw16Text);
			   //Draw revoke17
			   JButton btnDrawBack = new JButton("", new ImageIcon(getClass().getResource("/icon/"+names[17]+".jpg")));
			   btnDrawBack.addActionListener(new ActionListener() {
			   	public void actionPerformed(ActionEvent arg0) {
			   		if(screenshotui.getIndex()>0){
			   		screenshotui.setIndex(screenshotui.getIndex()-1);
			   		screenshotui.createNewitem();
			   		screenshotui.repaint();
			   		}
			   	 com.Main.ThenToolsRun.logger.log(Level.INFO,"tap draw back doodle button: "+screenshotui.getIndex());
			   	}
			   });
			   btnDrawBack.setToolTipText(tiptext[17]);
			   btnDrawBack.setBounds(10, 81, 35, 25);
			   add(btnDrawBack);
		      //text bold 
		      chckbxBold = new JCheckBox(getString("chckbxBold"));
		      chckbxBold.setFont(new Font(Font.DIALOG,Font.BOLD,10));//��������
		      chckbxBold.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
							if(chckbxBold.isSelected()){
								screenshotui.setFont(1, Font.BOLD);//2=б��
							}else{
								screenshotui.setFont(1, Font.PLAIN);
							}
							 com.Main.ThenToolsRun.logger.log(Level.INFO,"tap bold or plain checkbox");
					}
				}); 
		      chckbxBold.setSelected(true);
		      chckbxBold.setBounds(10, 107, 50, 23);
		      add(chckbxBold);
		      //����ѡ��
			   GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//�������������õ�����
		       fontName = ge.getAvailableFontFamilyNames();
		       stytles = new JComboBox(fontName);//�����б�ĳ�ʼ��
			   stytles.addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					screenshotui.stytle = fontName[stytles.getSelectedIndex()];
				}
				   
			   });//stytlesע�����
			   stytles.setMaximumSize(new Dimension(400,50));//���������б�����ߴ�
			   stytles.setMinimumSize(new  Dimension(250,40));
			   stytles.setBounds(65, 106, 144, 23);
			   add(stytles);
			   
			   //coordinate
			   lblCoordinate = new JLabel("Coordinate");
			   lblCoordinate.setFont(new Font("΢���ź�", Font.PLAIN, 12));
			   lblCoordinate.setBounds(10, 155, 242, 15);
			   add(lblCoordinate);
			   
			   //Save doodle
			   JButton btnSavedoodle = new JButton(getString("btnSavedoodle"));
			   btnSavedoodle.addActionListener(new ActionListener() {
			   	public void actionPerformed(ActionEvent arg0) {
			   		screenshotui.saveImage();
			   	 com.Main.ThenToolsRun.logger.log(Level.INFO,"tap save doodle button");
			   	}
			   });
			   btnSavedoodle.setBounds(0, 175, 100, 25);
			   add(btnSavedoodle);
			   //Draw info
			   lblDrawinfo = new JLabel(getString("lblDrawinfo"));
			   lblDrawinfo.setFont(new Font("΢���ź�", Font.PLAIN, 12));
			   lblDrawinfo.setBounds(10, 133, 280, 15);
			   add(lblDrawinfo);
			   //clear button
			   JButton btnClear = new JButton(getString("btnClear"));
			   btnClear.addActionListener(new ActionListener() {
			   	public void actionPerformed(ActionEvent e) {
			   		screenshotui.ClearNoodle();
			   		com.Main.ThenToolsRun.logger.log(Level.INFO,"tap clear doodle button");
			   	}
			   });
			   btnClear.setBounds(109, 175, 100, 25);
			   add(btnClear);

	}
	public void setscreenshotui(ScreenShotUI screenshotui){
		this.screenshotui=screenshotui;
	}
	//set lblCoordinate
	public void setlblCoordinate(String text){
		lblCoordinate.setText(text);
	}
	//set draw info
	public void setlblDrawinfo(String tiptext){
		lblDrawinfo.setText(getString("lblDrawinfo1"));
	}
	//Language 
	public String getString(String flag){
		switch(flag){
		case "lblEditTools": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "Ϳѻ���ߺ�:";
		}else{
			return "Doodle Tools:";
		}
		case "tiptext": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "�½�һ��ͼƬ,��ͼƬ,����ͼƬ,����,ֱ��,����,ʵ�ľ���,��Բ,ʵ����Բ,Բ,ʵ��Բ,Բ�Ǿ���,ʵ��Բ�Ǿ���,��Ƥ��,��ɫ,ѡ�������Ĵ�ϸ,����,����";
		}else{
			return "newfile,openfile,savefile,pen,line,rect,frect,oval,foval,circle,fcircle,roundrect,froundrect,rubber,color,stroke,word,revoke";
		}
		case "chckbxBold": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "����";
		}else{
			return "Bold";
		}
		case "btnSavedoodle": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "����Ϳѻ";
		}else{
			return "SaveDoodle";
		}
		case "lblDrawinfo": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "����,��ϸ=4,��ɫ=255,0,0";
		}else{
			return "pen,Thickness=4,Color=255,0,0";
		}
		case "btnClear": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "���Ϳѻ";
		}else{
			return "CleanDoodle";
		}
		case "lblDrawinfo1": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return tiptext[tiptextnum]+",��ϸ="+(int)(Float.parseFloat(screenshotui.getStroke()))+
					",��ɫ="+screenshotui.R+","+screenshotui.G+","+screenshotui.B;
		}else{
			return tiptext[tiptextnum]+",Thickness="+(int)(Float.parseFloat(screenshotui.getStroke()))+
				",Color="+screenshotui.R+","+screenshotui.G+","+screenshotui.B;
		}
			default: return "";
		}
	
	}
}
