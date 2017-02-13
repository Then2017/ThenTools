package com.GetScreen;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import com.Functions.LoggerUtil;

import javax.swing.JLabel;




public class ScreenShotUI extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FBImage mFBImage;
    private SimpleDateFormat sDateFormatget = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
    EditToolsUI edittoolsui;
    //**********
    Drawing[] itemList =new Drawing[5000]; //����ͼ����
    
    private int currentChoice = 3;//����Ĭ�ϻ���ͼ��״̬Ϊ��ʻ�
    int index = 0;//��ǰ�Ѿ����Ƶ�ͼ����Ŀ
    private Color color = Color.black;//��ǰ���ʵ���ɫ
    int R=255,G=0,B=0;//������ŵ�ǰ��ɫ�Ĳ�ֵ
    int f1=1,f2;//������ŵ�ǰ����ķ��
    String stytle ;//��ŵ�ǰ����
    float stroke = 4.0f;//���û��ʵĴ�ϸ ��Ĭ�ϵ��� 1.0
    Pattern pattern = Pattern.compile("^\\+?[1-9][0-9]*$");
    ///************
    double xD,yD;
	int tempWidth=0,tempHeight=0;
	int Sizex=324,Sizey=567;
	
    public ScreenShotUI(EditToolsUI edittoolsui)
    {
      this.edittoolsui=edittoolsui;
      setBackground(Color.WHITE);
      setLayout(null);
     // setSize(324,576);
      setSize(500,576);
      setLocation(10,10);//location ui
      
		// ��������ó�ʮ����
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		JLabel labmask = new JLabel("ThenTools");
		labmask.setBounds(440, 555, 60, 21);
		add(labmask);
		// setCursor ����������״ ��getPredefinedCursor()����һ������ָ�����͵Ĺ��Ķ���
		addMouseListener(new MouseA());// �������¼�
		addMouseMotionListener(new MouseB());
		 createNewitem();
    }
    //sava image 
	  public void saveImage() {
		      BufferedImage outImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
			  Graphics2D  g2d = outImage.createGraphics();
			  this.paint(g2d);
		      if (outImage != null) {
		       	JFileChooser fileChooser = new JFileChooser(com.Main.ThenToolsRun.ThenLogfile+"/ScreenCap");
		        fileChooser.setFileFilter(new FileFilter()
		        {
		          public String getDescription() {
		            return "*.png";
		          }

		          public boolean accept(File f)
		          {
		            String ext = f.getName().toLowerCase();
		            return ext.endsWith(".png");
		          }
		        });
		        fileChooser.setSelectedFile(new File("Doodle_PCtime_"+sDateFormatget.format(new Date())));
		        if (fileChooser.showSaveDialog(com.Main.ThenToolsRun.mainFrame) != 0) return;
		        try {
		          File file = fileChooser.getSelectedFile();
		          String saveimagepath = file.getAbsolutePath();
		          if (!saveimagepath.endsWith(".png")) {
		            file = new File(saveimagepath + "." + "png");
		            saveimagepath=saveimagepath+".png";
		          }
		          ImageIO.write(outImage, "png", file);
		        } catch (Exception e) {
		        	com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
		          JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Failed to save a image.", "Message",JOptionPane.ERROR_MESSAGE);
		        }
		      }
	  }
		//clear 
		public void ClearNoodle(){
			index=0;
			createNewitem();
			repaint();
		}
    //paint
    protected void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      if (this.mFBImage == null) {
    	  com.Main.ThenToolsRun.logger.log(Level.INFO,"screenshotui paintComponent no image!");
    	  return;
      }
      int srcHeight=mFBImage.getHeight();
      int srcWidth=mFBImage.getWidth();
      if(tempWidth!=srcWidth||tempHeight!=srcHeight){
     	 tempWidth=srcWidth;
     	 tempHeight=srcHeight;
     	 	//portrait
 	        if((srcHeight==1280&&srcWidth==720)||(srcHeight==1920&&srcWidth==1080)||(srcHeight==720&&srcWidth==480)
 	        		||(srcHeight==800&&srcWidth==480)||(srcHeight==960&&srcWidth==540)||(srcHeight==854&&srcWidth==480)
 	        		||(srcHeight==480&&srcWidth==320)||(srcHeight==320&&srcWidth==240)){
 	        	setmPortraitSize(true);
 	        }
 	        else if((srcHeight==720&&srcWidth==1280)||(srcHeight==1080&&srcWidth==1920)||(srcHeight==480&&srcWidth==720)
 	        		||(srcHeight==480&&srcWidth==800)||(srcHeight==540&&srcWidth==960)||(srcHeight==480&&srcWidth==854)
 	        		||(srcHeight==320&&srcWidth==480)||(srcHeight==240&&srcWidth==320)){
 	        	setmPortraitSize(false);
 	        }
 	   }
 	  xD=(double)Sizex/srcWidth;
 	  yD=(double)Sizey/srcHeight;
 	 int  dstWidth = (int)(srcWidth * xD);
 	int  dstHeight = (int)(srcHeight *yD);

     // com.Main.ThenToolsRun.logger.log(Level.INFO,"UE height="+srcHeight+" width="+srcWidth);
//      int dstWidth = (int)(srcWidth * 0.5D);
//      int dstHeight = (int)(srcHeight * 0.5D);
//      
//      if(srcHeight==1280&&srcWidth==720){
//           dstWidth = (int)(srcWidth * 0.45D);
//           dstHeight = (int)(srcHeight * 0.45D);
//      }else if(srcHeight==1920&&srcWidth==1080){
//          dstWidth = (int)(srcWidth * 0.3D);
//          dstHeight = (int)(srcHeight * 0.3D);
//      }else if(srcHeight==720&&srcWidth==480){
//          dstWidth = (int)(srcWidth * 0.675D);
//          dstHeight = (int)(srcHeight * 0.8D);
//      }else if(srcHeight==800&&srcWidth==480){
//          dstWidth = (int)(srcWidth * 0.675D);
//          dstHeight = (int)(srcHeight * 0.72D);
//      }else if(srcHeight==960&&srcWidth==540){
//          dstWidth = (int)(srcWidth * 0.6D);
//          dstHeight = (int)(srcHeight * 0.6D);
//      }else if(srcHeight==854&&srcWidth==480){
//          dstWidth = (int)(srcWidth * 0.675D);
//          dstHeight = (int)(srcHeight * 0.67D);
//      }else if(srcHeight==320&&srcWidth==480){
//          dstWidth = (int)(srcWidth * 0.675D);
//          dstHeight = (int)(srcHeight * 1.8D);
//      }
      //324,576
    //  g.drawImage(this.mFBImage, 0, 0, dstWidth, dstHeight, 0, 0, srcWidth, srcHeight, null);
      g.drawImage(this.mFBImage.getScaledInstance(dstWidth, dstHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
      
      //***
      if(index!=0){
      Graphics2D g2d = (Graphics2D)g;//������ʻ�
		int  j = 0;
		while(j<=index)
		{
			draw(g2d,itemList[j]);
			j++;
	    }
      }
		//*********
    }
    //set size
    public void setmPortraitSize(boolean mPortrait){
    	if(mPortrait){
    		Sizex=324;
    		Sizey=576;
    	}else{
    		Sizex=498;
    		Sizey=280;
    	}
    }
    public void setFBImage(FBImage fbImage)
    {
      this.mFBImage = fbImage;
      repaint();
    }

    public FBImage getFBImage() {
      return this.mFBImage;
    }
    
    //***************
    void draw(Graphics2D g2d , Drawing i)
	{
		i.draw(g2d);//�����ʴ���������������У�������ɸ��ԵĻ�ͼ
	}
	
	//�½�һ��ͼ�εĻ�����Ԫ����ĳ����
	void createNewitem(){
		if(currentChoice == 14)//�������������Ӧ������Ϊ�ı������ʽ
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		else  	setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			switch(currentChoice){
			case 3: itemList[index] = new Pencil();break;
			case 4: itemList[index] = new Line();break;
			case 5: itemList[index] = new Rect();break;
			case 6: itemList[index] = new fillRect();break;
			case 7: itemList[index] = new Oval();break;
			case 8: itemList[index] = new fillOval();break;
			case 9: itemList[index] = new Circle();break;
			case 10: itemList[index] = new fillCircle();break;
			case 11: itemList[index] = new RoundRect();break;
			case 12: itemList[index] = new fillRoundRect();break;
			case 13: itemList[index] = new Rubber();break;
			case 14: itemList[index] = new Word();break;
		}
			itemList[index].x1=-200;//��ֹ��Ļ0,0���ֺڵ�
			itemList[index].x2=-200;
			itemList[index].y1=-200;
			itemList[index].y2=-200;
	  itemList[index].type = currentChoice;
	  itemList[index].R = R;
	  itemList[index].G = G;
	  itemList[index].B = B;
	  itemList[index].stroke = stroke ;
	 
	}
	
    public void setIndex(int x){//����index�Ľӿ�
    	index = x;
    }
    public int getIndex(){//����index�Ľӿ�
    	return index ;
    }
    public void setColor(Color color)//������ɫ��ֵ
    {
    	this.color = color; 
    }
    public void setStroke(float f)//���û��ʴ�ϸ�Ľӿ�
    {
    	stroke = f;
    }
	public void chooseColor()//ѡ��ǰ��ɫ
	{
		color = JColorChooser.showDialog(com.Main.ThenToolsRun.mainFrame, "Pls choose color.", color);
		try {
			if(color!=null){
			R = color.getRed();
			G = color.getGreen();
			B = color.getBlue();
			}
		} catch (Exception e) {
			R = 255;
			G = 0;
			B = 0;
		}
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
	}
	public void setStroke()//���ʴ�ϸ�ĵ���
	{
		String input ;
		input = JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame,"Pls input positive integer for the thickness of the brush(0<n<=200).");
	    if(input!=null){
		Matcher matcher = pattern.matcher(input);
	    if(matcher.matches()&&Integer.parseInt(input)<=200){
	    	try {
	    		stroke = Float.parseFloat(input);
				} catch (Exception e) {
					stroke = 1.0f;
				}

	    }else{
			JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls input positive integer(0<n<=200).", 
					"Message", JOptionPane.ERROR_MESSAGE); 
	    }
	    }
		itemList[index].stroke = stroke;
	}
	public String getStroke(){
		return ""+stroke;
	}
	public void setCurrentChoice(int i)//���ֵ�����
	{
		currentChoice = i;
	}
	
	public void setFont(int  i,int font)//��������
	{
		if(i == 1)
		{
			f1 = font; 
		}
		else 
			f2 = font;
	}

// TODO ����¼�MouseA��̳���MouseAdapter 
//�������������Ӧ�¼��Ĳ��������İ��¡��ͷš��������ƶ����϶�����ʱ����һ���������ʱ�˳�����ʱ���������� )
class MouseA extends MouseAdapter
{
	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO ������
		edittoolsui.setlblCoordinate(getString("Enter")+me.getX()+" ,"+me.getY()+"]");
	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO ����˳�
		edittoolsui.setlblCoordinate(getString("Exit")+me.getX()+" ,"+me.getY()+"]");
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO ��갴��
		edittoolsui.setlblCoordinate(getString("Press")+me.getX()+" ,"+me.getY()+"]");//����״̬����ʾ
		
		itemList[index].x1 = itemList[index].x2 = me.getX();
		itemList[index].y1 = itemList[index].y2 = me.getY();
		
		//�����ǰѡ��Ϊ��ʻ�����Ƥ�� �����������Ĳ���
		if(currentChoice == 3||currentChoice ==13){
			itemList[index].x1 = itemList[index].x2 = me.getX();
			itemList[index].y1 = itemList[index].y2 = me.getY();
			index++;
			createNewitem();//�����µ�ͼ�εĻ�����Ԫ����
		}
		//���ѡ��ͼ�ε��������룬���������Ĳ���
		if(currentChoice == 14){
			itemList[index].x1 = me.getX();
			itemList[index].y1 = me.getY();
			String input ;
			input = JOptionPane.showInputDialog(com.Main.ThenToolsRun.mainFrame,"Pls input your text!");
			itemList[index].s1 = input;
			itemList[index].x2 = f1;
			itemList[index].y2 = f2;
			itemList[index].s2 = stytle;
			
			index++;
			currentChoice = 14;
			createNewitem();//�����µ�ͼ�εĻ�����Ԫ����
			repaint();
		}
			
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO ����ɿ�
		edittoolsui.setlblCoordinate(getString("Release")+me.getX()+" ,"+me.getY()+"]");
		if(currentChoice == 3||currentChoice ==13){
			itemList[index].x1 = me.getX();
			itemList[index].y1 = me.getY();
		}
		itemList[index].x2 = me.getX();
		itemList[index].y2 = me.getY();
		repaint();
		index++;
		createNewitem();//�����µ�ͼ�εĻ�����Ԫ����
	}

}

	// ����¼�MouseB�̳���MouseMotionAdapter
	// �����������Ĺ������϶�
	class MouseB extends MouseMotionAdapter {
      public void mouseDragged(MouseEvent me)//�����϶�
      {
    	  edittoolsui.setlblCoordinate(getString("Drag")+me.getX()+" ,"+me.getY()+"]");
    	  if(currentChoice == 3||currentChoice ==13){
    		  itemList[index-1].x1 = itemList[index].x2 = itemList[index].x1 =me.getX();
    		  itemList[index-1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
    		  index++;
    		  createNewitem();//�����µ�ͼ�εĻ�����Ԫ����
    	  }
    	  else 
    	  {
    		  itemList[index].x2 = me.getX();
    		  itemList[index].y2 = me.getY();
    	  }
    	  repaint();
      }
      public void mouseMoved(MouseEvent me)//�����ƶ�
      {
    	  edittoolsui.setlblCoordinate(getString("Move")+me.getX()+" ,"+me.getY()+"]");
      }
	}
	
	//Language 
	public String getString(String flag){
		switch(flag){
		case "Enter": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "����: [";
		}else{
			return "Enter��[";
		}
		case "Exit": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "�뿪: [";
		}else{
			return "Exit��[";
		}
		case "Press": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "����: [";
		}else{
			return "Press��[";
		}
		case "Release": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "̧��: [";
		}else{
			return "Release��[";
		}
		case "Drag": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "�϶�: [";
		}else{
			return "Drag��[";
		}
		case "Move": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "�ƶ�: [";
		}else{
			return "Move��[";
		}

			default: return "";
		}
	}

}




