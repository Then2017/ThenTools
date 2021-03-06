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
    Drawing[] itemList =new Drawing[5000]; //绘制图形类
    
    private int currentChoice = 3;//设置默认基本图形状态为随笔画
    int index = 0;//当前已经绘制的图形数目
    private Color color = Color.black;//当前画笔的颜色
    int R=255,G=0,B=0;//用来存放当前颜色的彩值
    int f1=1,f2;//用来存放当前字体的风格
    String stytle ;//存放当前字体
    float stroke = 4.0f;//设置画笔的粗细 ，默认的是 1.0
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
      
		// 把鼠标设置成十字形
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		JLabel labmask = new JLabel("ThenTools");
		labmask.setBounds(440, 555, 60, 21);
		add(labmask);
		// setCursor 设置鼠标的形状 ，getPredefinedCursor()返回一个具有指定类型的光标的对象
		addMouseListener(new MouseA());// 添加鼠标事件
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
      Graphics2D g2d = (Graphics2D)g;//定义随笔画
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
		i.draw(g2d);//将画笔传到个各类的子类中，用来完成各自的绘图
	}
	
	//新建一个图形的基本单元对象的程序段
	void createNewitem(){
		if(currentChoice == 14)//字体的输入光标相应的设置为文本输入格式
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
			itemList[index].x1=-200;//防止屏幕0,0出现黑点
			itemList[index].x2=-200;
			itemList[index].y1=-200;
			itemList[index].y2=-200;
	  itemList[index].type = currentChoice;
	  itemList[index].R = R;
	  itemList[index].G = G;
	  itemList[index].B = B;
	  itemList[index].stroke = stroke ;
	 
	}
	
    public void setIndex(int x){//设置index的接口
    	index = x;
    }
    public int getIndex(){//设置index的接口
    	return index ;
    }
    public void setColor(Color color)//设置颜色的值
    {
    	this.color = color; 
    }
    public void setStroke(float f)//设置画笔粗细的接口
    {
    	stroke = f;
    }
	public void chooseColor()//选择当前颜色
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
	public void setStroke()//画笔粗细的调整
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
	public void setCurrentChoice(int i)//文字的输入
	{
		currentChoice = i;
	}
	
	public void setFont(int  i,int font)//设置字体
	{
		if(i == 1)
		{
			f1 = font; 
		}
		else 
			f2 = font;
	}

// TODO 鼠标事件MouseA类继承了MouseAdapter 
//用来完成鼠标的响应事件的操作（鼠标的按下、释放、单击、移动、拖动、何时进入一个组件、何时退出、何时滚动鼠标滚轮 )
class MouseA extends MouseAdapter
{
	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO 鼠标进入
		edittoolsui.setlblCoordinate(getString("Enter")+me.getX()+" ,"+me.getY()+"]");
	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO 鼠标退出
		edittoolsui.setlblCoordinate(getString("Exit")+me.getX()+" ,"+me.getY()+"]");
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO 鼠标按下
		edittoolsui.setlblCoordinate(getString("Press")+me.getX()+" ,"+me.getY()+"]");//设置状态栏提示
		
		itemList[index].x1 = itemList[index].x2 = me.getX();
		itemList[index].y1 = itemList[index].y2 = me.getY();
		
		//如果当前选择为随笔画或橡皮擦 ，则进行下面的操作
		if(currentChoice == 3||currentChoice ==13){
			itemList[index].x1 = itemList[index].x2 = me.getX();
			itemList[index].y1 = itemList[index].y2 = me.getY();
			index++;
			createNewitem();//创建新的图形的基本单元对象
		}
		//如果选择图形的文字输入，则进行下面的操作
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
			createNewitem();//创建新的图形的基本单元对象
			repaint();
		}
			
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO 鼠标松开
		edittoolsui.setlblCoordinate(getString("Release")+me.getX()+" ,"+me.getY()+"]");
		if(currentChoice == 3||currentChoice ==13){
			itemList[index].x1 = me.getX();
			itemList[index].y1 = me.getY();
		}
		itemList[index].x2 = me.getX();
		itemList[index].y2 = me.getY();
		repaint();
		index++;
		createNewitem();//创建新的图形的基本单元对象
	}

}

	// 鼠标事件MouseB继承了MouseMotionAdapter
	// 用来处理鼠标的滚动与拖动
	class MouseB extends MouseMotionAdapter {
      public void mouseDragged(MouseEvent me)//鼠标的拖动
      {
    	  edittoolsui.setlblCoordinate(getString("Drag")+me.getX()+" ,"+me.getY()+"]");
    	  if(currentChoice == 3||currentChoice ==13){
    		  itemList[index-1].x1 = itemList[index].x2 = itemList[index].x1 =me.getX();
    		  itemList[index-1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
    		  index++;
    		  createNewitem();//创建新的图形的基本单元对象
    	  }
    	  else 
    	  {
    		  itemList[index].x2 = me.getX();
    		  itemList[index].y2 = me.getY();
    	  }
    	  repaint();
      }
      public void mouseMoved(MouseEvent me)//鼠标的移动
      {
    	  edittoolsui.setlblCoordinate(getString("Move")+me.getX()+" ,"+me.getY()+"]");
      }
	}
	
	//Language 
	public String getString(String flag){
		switch(flag){
		case "Enter": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "进入: [";
		}else{
			return "Enter：[";
		}
		case "Exit": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "离开: [";
		}else{
			return "Exit：[";
		}
		case "Press": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "按下: [";
		}else{
			return "Press：[";
		}
		case "Release": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "抬起: [";
		}else{
			return "Release：[";
		}
		case "Drag": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "拖动: [";
		}else{
			return "Drag：[";
		}
		case "Move": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "移动: [";
		}else{
			return "Move：[";
		}

			default: return "";
		}
	}

}




