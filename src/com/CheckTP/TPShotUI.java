package com.CheckTP;


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




public class TPShotUI extends JPanel {
    /**
	 * 
	 */
	int Sizex=225,Sizey=400;
	private static final long serialVersionUID = 1L;
	private FBImage mFBImage;
    private SimpleDateFormat sDateFormatget = new SimpleDateFormat("yyyy_MMdd_HHmm_ss");
//    int srcHeight;
//    int srcWidth;
    //draw
    TPDrawing[] itemList =new TPDrawing[5000];
    int index = 0;//当前已经绘制的图形数目
    float stroke = 4.0f;//设置画笔的粗细 ，默认的是 1.0
    int x1,x2,y1,y2;
    int whichdraw;
    double xD,yD;
    int R1,R2,R3,G1,G2,G3,B1,B2,B3;
	JLabel lblMouse;//鼠标信息
	int tempWidth=0,tempHeight=0;
	
	boolean mPortrait=true;
	
    public TPShotUI( )
    {
    	//init color
		R1=255;G1=0;B1=0;
		R2=0;G2=255;B2=0;
		R3=0;G3=0;B3=255;
      setBackground(Color.WHITE);
      setLayout(null);
     // setSize(324,576);
      setSize(Sizex,Sizey);
      setLocation(20,190);//location ui
		// 把鼠标设置成十字形
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		// setCursor 设置鼠标的形状 ，getPredefinedCursor()返回一个具有指定类型的光标的对象
		addMouseListener(new MouseA());// 添加鼠标事件
		addMouseMotionListener(new MouseB());
		 createNewitem();
    }

    //paint
    protected void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      if (this.mFBImage == null) {
    	  com.Main.ThenToolsRun.logger.log(Level.INFO,"checktp paintComponent no image!");
    	  return;
      }
     int srcWidth = mFBImage.getWidth();
     int srcHeight = mFBImage.getHeight();
     if(tempWidth!=srcWidth||tempHeight!=srcHeight){
       	 tempWidth=srcWidth;
       	 tempHeight=srcHeight;
       	 	//portrait
    	        if((srcHeight==1280&&srcWidth==720)||(srcHeight==1920&&srcWidth==1080)||(srcHeight==720&&srcWidth==480)
    	        		||(srcHeight==800&&srcWidth==480)||(srcHeight==960&&srcWidth==540)||(srcHeight==854&&srcWidth==480)
    	        		||(srcHeight==480&&srcWidth==320)||(srcHeight==320&&srcWidth==240)){
    	        	index=0;
    	        	 com.Main.ThenToolsRun.mainFrame.getCheckTPUImain().setmPortraitMode(true);
    	        }
       	   //landspace 320 240 240 320  1080 1920 1080 1920 实际
    	     //   System.out.println(mFBImage.getRawHeight()+" "+mFBImage.getRawWidth()+" "+mFBImage.getHeight()+" "+ mFBImage.getWidth());
    	        else if((srcHeight==720&&srcWidth==1280)||(srcHeight==1080&&srcWidth==1920)||(srcHeight==480&&srcWidth==720)
    	        		||(srcHeight==480&&srcWidth==800)||(srcHeight==540&&srcWidth==960)||(srcHeight==480&&srcWidth==854)
    	        		||(srcHeight==320&&srcWidth==480)||(srcHeight==240&&srcWidth==320)){
    	        	index=0;
    	             com.Main.ThenToolsRun.mainFrame.getCheckTPUImain().setmPortraitMode(false);
    	        }
    	   }
     xD=(double)Sizex/srcWidth;
	  yD=(double)Sizey/srcHeight;
	 int  dstWidth = (int)(srcWidth * xD);
	int  dstHeight = (int)(srcHeight *yD);
//   if (mPortrait){
//	        if(srcHeight==1280&&srcWidth==720){
//	      	  xD=(double)Sizex/720;
//	      	  yD=(double)Sizey/1280;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==1920&&srcWidth==1080){
//	      	  xD=(double)Sizex/1080;
//	      	  yD=(double)Sizey/1920;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==720&&srcWidth==480){
//	      	  xD=(double)Sizex/480;
//	      	  yD=(double)Sizey/720;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==800&&srcWidth==480){
//	      	  xD=(double)Sizex/480;
//	      	  yD=(double)Sizey/800;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==960&&srcWidth==540){
//	      	  xD=(double)Sizex/540;
//	      	  yD=(double)Sizey/960;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==854&&srcWidth==480){
//	      	  xD=(double)Sizex/480;
//	      	  yD=(double)Sizey/854;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==320&&srcWidth==480){
//	      	  xD=(double)Sizex/480;
//	      	  yD=(double)Sizey/320;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==320&&srcWidth==240){
//	      	  xD=(double)Sizex/240;
//	      	  yD=(double)Sizey/320;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }
//       }else{
//    	   //320 240 240 320
//    	   //1080 1920 1080 1920 实际
//	     //   System.out.println(mFBImage.getRawHeight()+" "+mFBImage.getRawWidth()+" "+mFBImage.getHeight()+" "+ mFBImage.getWidth());
//	        if(srcHeight==720&&srcWidth==1280){
//	      	  xD=(double)Sizex/1280;
//	      	  yD=(double)Sizey/720;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==1080&&srcWidth==1920){
//	      	  xD=(double)Sizex/1920;
//	      	  yD=(double)Sizey/1080;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==480&&srcWidth==720){
//	      	  xD=(double)Sizex/720;
//	      	  yD=(double)Sizey/480;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==480&&srcWidth==800){
//	      	  xD=(double)Sizex/800;
//	      	  yD=(double)Sizey/480;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==540&&srcWidth==960){
//	      	  xD=(double)Sizex/960;
//	      	  yD=(double)Sizey/540;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==480&&srcWidth==854){
//	      	  xD=(double)Sizex/854;
//	      	  yD=(double)Sizey/480;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==480&&srcWidth==320){
//	      	  xD=(double)Sizex/320;
//	      	  yD=(double)Sizey/480;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }else if(srcHeight==240&&srcWidth==320){//just for iris4G
////		         srcWidth = mFBImage.getRawHeight();
////			     srcHeight = mFBImage.getRawWidth();
//	      	  xD=(double)Sizex/320;
//	      	  yD=(double)Sizey/240;
//	             dstWidth = (int)(srcWidth * xD);
//	             dstHeight = (int)(srcHeight *yD);
//	        }
//	   }
   
      g.drawImage(this.mFBImage.getScaledInstance(dstWidth, dstHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
      //draw point
      if(index!=0){
      Graphics2D g2d = (Graphics2D)g;//定义随笔画
		int  j = 1;
		while(j<=index)
		{
			draw(g2d,itemList[j]);
			j++;
	    }
      }
    }

    void draw(Graphics2D g2d , TPDrawing i)
	{
		i.draw(g2d);//将画笔传到个各类的子类中，用来完成各自的绘图
	}
    
    public void drawpicture(String info){
    	info=info.replaceAll("\\(|\\)|ms|\n", "");
    	info=info.replaceAll(":", ",");
    	String[] drawinfo=info.split(",");
		index++;
    	if(drawinfo[0].equals("Tap")){
    		whichdraw=2;
    		createNewitem();
    		x2=Integer.parseInt(drawinfo[1]);
    		y2=Integer.parseInt(drawinfo[2]);
    		itemList[index].x2=(int)(x2*xD);
    		itemList[index].y2=(int)(y2*yD);
    		itemList[index].R = R1;
    		itemList[index].G = G1;
    		itemList[index].B = B1;
    		itemList[index].stroke=stroke;
    		repaint();
    	}else if(drawinfo[0].equals("Long tap")){
    		whichdraw=3;
    		createNewitem();
    		x2=Integer.parseInt(drawinfo[1]);
    		y2=Integer.parseInt(drawinfo[2]);
    		itemList[index].x2=(int)(x2*xD);
    		itemList[index].y2=(int)(y2*yD);
    		itemList[index].R = R2;
    		itemList[index].G = G2;
    		itemList[index].B = B2;
    		itemList[index].stroke=stroke;
    		repaint();
    	}else if(drawinfo[0].equals("Drag")){
    		whichdraw=1;
    		createNewitem();
    		x1=Integer.parseInt(drawinfo[1]);
    		y1=Integer.parseInt(drawinfo[2]);
    		x2=Integer.parseInt(drawinfo[3]);
    		y2=Integer.parseInt(drawinfo[4]);
    		itemList[index].x1=(int)(x1*xD);
    		itemList[index].y1=(int)(y1*yD);
    		itemList[index].x2=(int)(x2*xD);
    		itemList[index].y2=(int)(y2*yD);
    		itemList[index].R = R3;
    		itemList[index].G = G3;
    		itemList[index].B = B3;
    		itemList[index].stroke=stroke;
    		repaint();
    	}
    	
    }
    //set color
    int color=2;
    public int setColor(){
    	if(color==1){
    		R1=255;G1=0;B1=0;
    		R2=0;G2=255;B2=0;
    		R3=0;G3=0;B3=255;
    		color++;
    		return 1;
    	}else if(color==2){
    		R1=0;G1=255;B1=0;
    		R2=0;G2=0;B2=255;
    		R3=255;G3=0;B3=0;
    		color++;
    		return 2;
    	}else if(color==3){
    		color=1;
    		R1=0;G1=0;B1=255;
    		R2=255;G2=0;B2=0;
    		R3=0;G3=255;B3=0;
    		return 3;
    	}
    	return color;
    }
  //新建一个图形的基本单元对象的程序段
  	void createNewitem(){
  			switch(whichdraw){
  			case 1: itemList[index] = new Drag();break;
  			case 2: itemList[index] = new Tap();break;
  			case 3: itemList[index] = new Longtap();break;
  			}
  	}
    public void setindex(int index){
    	this.index=index;
    }
    public void setFBImage(FBImage fbImage)
    {
      this.mFBImage = fbImage;
      repaint();
    }
    //设置横竖屏
    public void setmPortrait(boolean mPortrait){
    	this.mPortrait=mPortrait;
    }
    //set size
    public void setmPortraitSize(boolean mPortrait){
    	if(mPortrait){
    		Sizex=225;
    		Sizey=400;
    		setSize(Sizex,Sizey);
    	}else{
    		Sizex=400;
    		Sizey=225;
    		setSize(Sizex,Sizey);
    	}
    }
    public FBImage getFBImage() {
      return this.mFBImage;
    }
//    public int getsrcHeight(){
//    	return srcHeight;
//    }
//    public int getsrcWidth(){
//    	return srcWidth;
//    }
    //设置鼠标信息字符串
    public void setlblMouse(JLabel lblMouse){
    	this.lblMouse=lblMouse;
    }

 // TODO 鼠标事件MouseA类继承了MouseAdapter 
  //用来完成鼠标的响应事件的操作（鼠标的按下、释放、单击、移动、拖动、何时进入一个组件、何时退出、何时滚动鼠标滚轮 )
  class MouseA extends MouseAdapter
  {
  	@Override
  	public void mouseEntered(MouseEvent me) {
  		// TODO 鼠标进入
  		lblMouse.setText("("+(int)(me.getX()/xD)+","+(int)(me.getY()/yD)+")");
  	}

  	@Override
  	public void mouseExited(MouseEvent me) {
  		// TODO 鼠标退出
  		lblMouse.setText("("+(int)(me.getX()/xD)+","+(int)(me.getY()/yD)+")");
  	}

  	@Override
  	public void mousePressed(MouseEvent me) {
  		// TODO 鼠标按下
  		lblMouse.setText("("+(int)(me.getX()/xD)+","+(int)(me.getY()/yD)+")");
  			
  	}

  	@Override
  	public void mouseReleased(MouseEvent me) {
  		// TODO 鼠标松开
  		lblMouse.setText("("+(int)(me.getX()/xD)+","+(int)(me.getY()/yD)+")");

  	}

  }

  	// 鼠标事件MouseB继承了MouseMotionAdapter
  	// 用来处理鼠标的滚动与拖动
  	class MouseB extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent me)//鼠标的拖动
        {

        }
        public void mouseMoved(MouseEvent me)//鼠标的移动
        {
        	lblMouse.setText("("+(int)(me.getX()/xD)+","+(int)(me.getY()/yD)+")");
        }
  	}
	

}




