package com.GetScreen;

import com.Functions.AdbBridge;
import com.Functions.Helper;
import com.Functions.LoggerUtil;
import com.Main.ThenToolsRun;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.TimeoutException;
import com.sun.org.apache.xpath.internal.functions.FuncTrue;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileFilter;

import java.awt.EventQueue;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

public class ScreenMirrorUI extends JFrame {


	//  private static final int DEFAULT_WIDTH = 320;
	//  private static final int DEFAULT_HEIGHT = 480;
	//  private static final String EXT_PNG = "png";
	  private MainPanel mPanel;
	  private JPopupMenu mPopupMenu;
	  private int mRawImageWidth = 720;
	  private int mRawImageHeight = 1280;
	  private boolean mPortrait = true;
	  private double mZoom = 0.5D;
	  private boolean mAdjustColor = false;
	  private JCheckBoxMenuItem mAdjustColorCheckBoxMenuItem;
	 // private GetDevices mADB;
	  private IDevice[] mDevices;
	  private IDevice mDevice;
	//  private MonitorThread mMonitorThread;
	  boolean monitorthreadrunnable=false;
	  String title="Screen Mirror";
//	  private MouseListener mMouseListener = new MouseListener() {
//	    public void mouseReleased(MouseEvent e) {
//	    }
//
//	    public void mousePressed(MouseEvent e) {
//	    }
//
//	    public void mouseExited(MouseEvent e) {
//	    }
//
//	    public void mouseEntered(MouseEvent e) {
//	    }
//
//	    public void mouseClicked(MouseEvent e) {
//	      if (SwingUtilities.isRightMouseButton(e))
//	        ScreenMirrorUI.this.mPopupMenu.show(e.getComponent(), e.getX(), e.getY());
//	    }
//	  };

	  private WindowListener mWindowListener = new WindowListener() {
	    public void windowOpened(WindowEvent arg0) {
	    }

	    public void windowIconified(WindowEvent arg0) {
	    }

	    public void windowDeiconified(WindowEvent arg0) {
	    }

	    public void windowDeactivated(WindowEvent arg0) {
	    }

	    public void windowClosing(WindowEvent arg0) {
	      com.Main.ThenToolsRun.logger.log(Level.INFO,"Screen Mirror windowClosing");
		  stopMonitor();
	      dispose();
	    }

	    public void windowClosed(WindowEvent arg0)
	    {
	    }

	    public void windowActivated(WindowEvent arg0)
	    {
	    }
	  };

	  public ScreenMirrorUI(String[] args)
	  {
	    initializeall(args);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(com.Main.ThenToolsRun.imagelogo);
	  }
	  
	  //开始线程
	  public void startMonitor() {
		  MonitorThread monitorThread = new MonitorThread();
		  new Thread(monitorThread).start();
		  com.Main.ThenToolsRun.logger.log(Level.INFO,"Start to mirror thread");
	  }
	  //停止线程
	  public void stopMonitor() {
		  monitorthreadrunnable = false;
	  }
	  //选择设备
	  public void selectDevice() {
	    stopMonitor();//先停止
	    mDevices = com.Main.ThenToolsRun.getdevices.getDevices();
	    if (mDevices != null) {
	      ArrayList list = new ArrayList();
	      for (int i = 0; i < mDevices.length; ++i) {
	        list.add(mDevices[i].toString());
	      }
	      SelectDeviceDialog dialog = new SelectDeviceDialog(this, true, list);
	      dialog.setLocationRelativeTo(this);
	      dialog.setVisible(true);
	      if (dialog.isOK()) {
	        int selectedIndex = dialog.getSelectedIndex();
	        if (selectedIndex >= 0) {
	          mDevice = mDevices[selectedIndex];
	          title=getString("title")+mDevice.toString();
	          setTitle(title);
	          setImage(null);
	          com.Main.ThenToolsRun.logger.log(Level.INFO,"Start to mirror with device: "+mDevice.toString());
	        }
	      }
	    }
	    startMonitor();
	  }

	  public void setOrientation(boolean portrait) {
	    if (this.mPortrait != portrait) {
	      this.mPortrait = portrait;
	      updateSize();
	    }
	  }

	  public void setZoom(double zoom) {
	    if (this.mZoom != zoom) {
	      this.mZoom = zoom;
	      updateSize();
	    }
	  }

	  public void saveImage() {
	    FBImage inImage = this.mPanel.getFBImage();
	    if (inImage != null) {
	      BufferedImage outImage = new BufferedImage((int)(inImage.getWidth() * 1), //z
	    		  (int)(inImage.getHeight() * 1), inImage.getType());//z
	      if (outImage != null) {
	        AffineTransformOp op = new AffineTransformOp(
	          AffineTransform.getScaleInstance(1, 1),2);//z
	        op.filter(inImage, outImage);
	       	JFileChooser fileChooser = new JFileChooser(com.Main.ThenToolsRun.ThenLogfile);
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
	        if (fileChooser.showSaveDialog(this) != 0) return;
	        try {
	          File file = fileChooser.getSelectedFile();
	          String path = file.getAbsolutePath();
	          if (!path.endsWith(".png")) {
	            file = new File(path + "." + "png");
	          }
	          ImageIO.write(outImage, "png", file);
	        } catch (Exception e) {
	          com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
	          JOptionPane.showMessageDialog(this, "Failed to save a image.", "Message",JOptionPane.ERROR_MESSAGE);
	        }
	      }
	    }else{
	          JOptionPane.showMessageDialog(this, "None image to save.", "Message",JOptionPane.ERROR_MESSAGE);	
	    }
	  }

//	  public void about()
//	  {
//	    AboutDialog dialog = new AboutDialog(this, true);
//	    dialog.setLocationRelativeTo(this);
//	    dialog.setVisible(true);
//	  }

	  public void updateSize()
	  {
	    int height;
	    int width;
	    if (this.mPortrait) {
	       width = this.mRawImageWidth;
	      height = this.mRawImageHeight;
	    } else {
	      width = this.mRawImageHeight;
	      height = this.mRawImageWidth;
	    }
	    Insets insets = getInsets();
	    int newWidth = (int)(width * this.mZoom) + insets.left + insets.right;
	    int newHeight = (int)(height * this.mZoom) + insets.top + insets.bottom;

	    if ((getWidth() != newWidth) || (getHeight() != newHeight))
	      setSize(newWidth, newHeight);
	  }

	  public void setImage(FBImage fbImage)
	  {
	    if (fbImage != null) {
	      this.mRawImageWidth = fbImage.getRawWidth();
	      this.mRawImageHeight = fbImage.getRawHeight();
	    }
	    this.mPanel.setFBImage(fbImage);
	    updateSize();
	  }

	  private void initializeall(String[] args) {
//	    this.mADB = new GetDevices();
//	    if (!this.mADB.initialize()) {
//	      JOptionPane.showMessageDialog(this, "Init adb failed, pls try again", "Message", JOptionPane.ERROR_MESSAGE);
//	    }

	    parseArgs(args);

	    initializeFrame();
	    initializePanel();
	    initializeMenu();
	    initializeActionMap();

	//    addMouseListener(this.mMouseListener);
	    addWindowListener(this.mWindowListener);

	    pack();
	    setImage(null);
	  }

	  private void parseArgs(String[] args) {
	    if (args != null)
	      for (int i = 0; i < args.length; ++i) {
	        String arg = args[i];
	        if (arg.equals("-a"))
	          this.mAdjustColor = true;
	      }
	  }

	  private void initializeFrame()
	  {
	    setTitle("Screen Mirror");
	  //  setIconImage(Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("icon.png")));
	    setDefaultCloseOperation(3);
	    setResizable(false);
	  }

	  private void initializePanel() {
	    this.mPanel = new MainPanel();
	    add(this.mPanel);
	  }

	  private void initializeMenu() {
	    this.mPopupMenu = new JPopupMenu();

	    initializeSelectDeviceMenu();
	    this.mPopupMenu.addSeparator();
	    initializeOrientationMenu();
	    initializeZoomMenu();
	    initializeAdjustColor();
	    this.mPopupMenu.addSeparator();
	    initializeSaveImageMenu();
	    this.mPopupMenu.addSeparator();
	    initializeExit();

	    this.mPopupMenu.addPopupMenuListener(new PopupMenuListener() {
	      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
	      }

	      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	      }

	      public void popupMenuCanceled(PopupMenuEvent e) {
	      }
	    });
	  }

	  private void initializeSelectDeviceMenu() {
	    JMenuItem menuItemSelectDevice = new JMenuItem(getString("menuItemSelectDevice"));
	    menuItemSelectDevice.setMnemonic(68);
	    menuItemSelectDevice.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.selectDevice();
	      }
	    });
	    this.mPopupMenu.add(menuItemSelectDevice);
	  }

	  private void initializeOrientationMenu() {
	    JMenu menuOrientation = new JMenu(getString("menuOrientation"));
	    menuOrientation.setMnemonic(79);
	    this.mPopupMenu.add(menuOrientation);

	    ButtonGroup buttonGroup = new ButtonGroup();

	    JRadioButtonMenuItem radioButtonMenuItemPortrait = new JRadioButtonMenuItem(getString("radioButtonMenuItemPortrait"));
	    radioButtonMenuItemPortrait.setSelected(true);
	    radioButtonMenuItemPortrait.setMnemonic(80);
	    radioButtonMenuItemPortrait.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setOrientation(true);
	      }
	    });
	    buttonGroup.add(radioButtonMenuItemPortrait);
	    menuOrientation.add(radioButtonMenuItemPortrait);

	    JRadioButtonMenuItem radioButtonMenuItemLandscape = new JRadioButtonMenuItem(getString("radioButtonMenuItemLandscape"));
	    radioButtonMenuItemLandscape.setMnemonic(76);
	    radioButtonMenuItemLandscape.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setOrientation(false);
	      }
	    });
	    buttonGroup.add(radioButtonMenuItemLandscape);
	    menuOrientation.add(radioButtonMenuItemLandscape);
	  }

	  private void initializeZoomMenu() {
	    JMenu menuZoom = new JMenu(getString("menuZoom"));
	    menuZoom.setMnemonic(90);//z
	    this.mPopupMenu.add(menuZoom);

	    ButtonGroup buttonGroup = new ButtonGroup();
	    JRadioButtonMenuItem radioButtonMenuItemZoom25 = new JRadioButtonMenuItem("25%");
	    radioButtonMenuItemZoom25.setMnemonic(50);
	    radioButtonMenuItemZoom25.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(0.25D);
	      }
	    });
	    buttonGroup.add(radioButtonMenuItemZoom25);
	    menuZoom.add(radioButtonMenuItemZoom25);
	    
	    JRadioButtonMenuItem radioButtonMenuItemZoom50 = new JRadioButtonMenuItem("50%");
	    radioButtonMenuItemZoom50.setSelected(true);
	    radioButtonMenuItemZoom50.setMnemonic(53);
	    radioButtonMenuItemZoom50.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(0.5D);
	      }
	    });
	    buttonGroup.add(radioButtonMenuItemZoom50);
	    menuZoom.add(radioButtonMenuItemZoom50);

	    JRadioButtonMenuItem radioButtonMenuItemZoom75 = new JRadioButtonMenuItem("75%");
	    radioButtonMenuItemZoom75.setMnemonic(55);
	    radioButtonMenuItemZoom75.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(0.75D);
	      }
	    });
	    buttonGroup.add(radioButtonMenuItemZoom75);
	    menuZoom.add(radioButtonMenuItemZoom75);

	    JRadioButtonMenuItem radioButtonMenuItemZoom100 = new JRadioButtonMenuItem("100%");
	    radioButtonMenuItemZoom100.setMnemonic(49);
	    radioButtonMenuItemZoom100.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(1.0D);
	      }
	    });
	    buttonGroup.add(radioButtonMenuItemZoom100);
	    menuZoom.add(radioButtonMenuItemZoom100);

	    JRadioButtonMenuItem radioButtonMenuItemZoom150 = new JRadioButtonMenuItem("150%");
	    radioButtonMenuItemZoom150.setMnemonic(48);
	    radioButtonMenuItemZoom150.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(1.5D);
	      }
	    });
	    buttonGroup.add(radioButtonMenuItemZoom150);
	    menuZoom.add(radioButtonMenuItemZoom150);

	    JRadioButtonMenuItem radioButtonMenuItemZoom200 = new JRadioButtonMenuItem("200%");
	    radioButtonMenuItemZoom200.setMnemonic(50);
	    radioButtonMenuItemZoom200.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(2.0D);
	      }
	    });
	    buttonGroup.add(radioButtonMenuItemZoom200);
	    menuZoom.add(radioButtonMenuItemZoom200);
	    //radioButtonMenuItemZoomDIY 
	    JRadioButtonMenuItem radioButtonMenuItemZoomDIY = new JRadioButtonMenuItem(getString("radioButtonMenuItemZoomDIY"));
	    radioButtonMenuItemZoomDIY.setMnemonic(100);
	    radioButtonMenuItemZoomDIY.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog(mPanel, "Pls enter zoom(0.1-1):","Input",JOptionPane.INFORMATION_MESSAGE); 
				if(input!=null){
					if(!input.equals("")&&Helper.isDecimal(input)&&Double.parseDouble(input)>=0.1&&Double.parseDouble(input)<=1){
						  ScreenMirrorUI.this.setZoom(Double.parseDouble(input));
							com.Main.ThenToolsRun.logger.log(Level.INFO,"DIY set zoom ok");
					}else{
						com.Main.ThenToolsRun.logger.log(Level.INFO,"Pls input numbers(0.1-1)");
						JOptionPane.showMessageDialog(com.Main.ThenToolsRun.mainFrame, "Pls input numbers(0.1-1)!", 
								"Message", JOptionPane.ERROR_MESSAGE);
					}
				}
	      }
	    });
	    buttonGroup.add(radioButtonMenuItemZoomDIY);
	    menuZoom.add(radioButtonMenuItemZoomDIY);
	  }

	  private void initializeAdjustColor() {
	    this.mAdjustColorCheckBoxMenuItem = new JCheckBoxMenuItem(getString("mAdjustColorCheckBoxMenuItem"));
	    this.mAdjustColorCheckBoxMenuItem.setMnemonic(74);
	    this.mAdjustColorCheckBoxMenuItem.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.mAdjustColor = (!ScreenMirrorUI.this.mAdjustColor);
	        ScreenMirrorUI.this.mAdjustColorCheckBoxMenuItem.setSelected(ScreenMirrorUI.this.mAdjustColor);
	      }
	    });
	    this.mPopupMenu.add(this.mAdjustColorCheckBoxMenuItem);
	  }

	  private void initializeSaveImageMenu() {
	    JMenuItem menuItemSaveImage = new JMenuItem(getString("menuItemSaveImage"));
	    menuItemSaveImage.setMnemonic(83);
	    menuItemSaveImage.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.saveImage();
	      }
	    });
	    this.mPopupMenu.add(menuItemSaveImage);
	  }

	  private void initializeActionMap() {
	    AbstractAction actionSelectDevice = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.selectDevice();
	      }
	    };
	    AbstractAction actionPortrait = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setOrientation(true);
	      }
	    };
	    AbstractAction actionLandscape = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setOrientation(false);
	      }
	    };
	    AbstractAction actionZoom50 = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(0.5D);
	      }
	    };
	    AbstractAction actionZoom75 = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(0.75D);
	      }
	    };
	    AbstractAction actionZoom100 = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(1.0D);
	      }
	    };
	    AbstractAction actionZoom150 = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(1.5D);
	      }
	    };
	    AbstractAction actionZoom200 = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.setZoom(2.0D);
	      }
	    };
	    AbstractAction actionAdjustColor = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.mAdjustColor = (!ScreenMirrorUI.this.mAdjustColor);
	        ScreenMirrorUI.this.mAdjustColorCheckBoxMenuItem.setSelected(ScreenMirrorUI.this.mAdjustColor);
	      }
	    };
	    AbstractAction actionSaveImage = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	        ScreenMirrorUI.this.saveImage();
	      }
	    };
	    AbstractAction actionExit = new AbstractAction() {
	      public void actionPerformed(ActionEvent e) {
	      }
	    };
	    JComponent targetComponent = getRootPane();
	    InputMap inputMap = targetComponent.getInputMap();

	    inputMap.put(KeyStroke.getKeyStroke(68, 
	      128), "Select Device");
	    inputMap.put(KeyStroke.getKeyStroke(80, 
	      128), "Portrait");
	    inputMap.put(KeyStroke.getKeyStroke(76, 
	      128), "Landscape");
	    inputMap.put(KeyStroke.getKeyStroke(53, 
	      128), "50%");
	    inputMap.put(KeyStroke.getKeyStroke(55, 
	      128), "75%");
	    inputMap.put(KeyStroke.getKeyStroke(49, 
	      128), "100%");
	    inputMap.put(KeyStroke.getKeyStroke(48, 
	      128), "150%");
	    inputMap.put(KeyStroke.getKeyStroke(50, 
	      128), "200%");
	    inputMap.put(KeyStroke.getKeyStroke(74, 
	      128), "Adjust Color");
	    inputMap.put(KeyStroke.getKeyStroke(83, 
	      128), "Save Image");
//	    inputMap.put(KeyStroke.getKeyStroke(65, 
//	      128), "About ASM");

	    targetComponent.setInputMap(1, inputMap);

	    targetComponent.getActionMap().put("Select Device", actionSelectDevice);
	    targetComponent.getActionMap().put("Portrait", actionPortrait);
	    targetComponent.getActionMap().put("Landscape", actionLandscape);
	    targetComponent.getActionMap().put("Select Device", actionSelectDevice);
	    targetComponent.getActionMap().put("50%", actionZoom50);
	    targetComponent.getActionMap().put("75%", actionZoom75);
	    targetComponent.getActionMap().put("100%", actionZoom100);
	    targetComponent.getActionMap().put("150%", actionZoom150);
	    targetComponent.getActionMap().put("200%", actionZoom200);
	    targetComponent.getActionMap().put("Adjust Color", actionAdjustColor);
	    targetComponent.getActionMap().put("Save Image", actionSaveImage);
	    targetComponent.getActionMap().put("Cancle", actionExit);
	  }

	  private void initializeExit() {
	    JMenuItem menuItemAbout = new JMenuItem(getString("menuItemAbout"));
	    menuItemAbout.setMnemonic(99);
	    menuItemAbout.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	  com.Main.ThenToolsRun.logger.log(Level.INFO,"Cancle button ScreenMirror");
	      }
	    });
	    this.mPopupMenu.add(menuItemAbout);
	  }

	  public class MainPanel extends JPanel
	  {
	    private FBImage mFBImage;

	    public MainPanel()
	    {
	      setBackground(Color.BLACK);
			// 把鼠标设置成十字形
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			// setCursor 设置鼠标的形状 ，getPredefinedCursor()返回一个具有指定类型的光标的对象
			addMouseListener(new MouseA());// 添加鼠标事件
			addMouseMotionListener(new MouseB());
	    }

	    protected void paintComponent(Graphics g)
	    {
	      super.paintComponent(g);
	      if (this.mFBImage == null)
	        return;
	      int srcHeight;
	      int srcWidth;

	      if (ScreenMirrorUI.this.mPortrait) {
	         srcWidth = ScreenMirrorUI.this.mRawImageWidth;
	        srcHeight = ScreenMirrorUI.this.mRawImageHeight;
	      } else {
	        srcWidth = ScreenMirrorUI.this.mRawImageHeight;
	        srcHeight = ScreenMirrorUI.this.mRawImageWidth;
	      }
	      int dstWidth = (int)(srcWidth * ScreenMirrorUI.this.mZoom);
	      int dstHeight = (int)(srcHeight * ScreenMirrorUI.this.mZoom);
	      if (ScreenMirrorUI.this.mZoom == 1.0D) {
	        g.drawImage(this.mFBImage, 0, 0, dstWidth, dstHeight, 0, 0, srcWidth, srcHeight, null);
	      } else {
	        Image image = this.mFBImage.getScaledInstance(dstWidth, dstHeight, 4);
	        if (image != null)
	          g.drawImage(image, 0, 0, dstWidth, dstHeight, 0, 0, dstWidth, dstHeight, null);
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
	 // TODO 鼠标事件MouseA类继承了MouseAdapter 
	    //用来完成鼠标的响应事件的操作（鼠标的按下、释放、单击、移动、拖动、何时进入一个组件、何时退出、何时滚动鼠标滚轮 )
	    class MouseA extends MouseAdapter
	    {
	    	@Override
	    	public void mouseEntered(MouseEvent me) {
	    		// TODO 鼠标进入
	    			ScreenMirrorUI.this.setTitle("("+(int)(me.getX()/mZoom)+","+(int)(me.getY()/mZoom)+")  "+title);
	    	}

	    	@Override
	    	public void mouseExited(MouseEvent me) {
	    		// TODO 鼠标退出
	    		ScreenMirrorUI.this.setTitle("("+(int)(me.getX()/mZoom)+","+(int)(me.getY()/mZoom)+")  "+title);
		    	}

	    	@Override
	    	public void mousePressed(MouseEvent me) {
	    		// TODO 鼠标按下
	    				
	    	}

	    	@Override
	    	public void mouseReleased(MouseEvent me) {
	    		// TODO 鼠标松开
	    		  ScreenMirrorUI.this.mPopupMenu.show(me.getComponent(), me.getX(), me.getY());
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
	        	  ScreenMirrorUI.this.setTitle("("+(int)(me.getX()/mZoom)+","+(int)(me.getY()/mZoom)+")  "+title);
	    	  }
	    	}
	  }
	  //****************线程****************************************************************************
	  FBImage tempfbImage;
	  public class MonitorThread implements Runnable {
	    public MonitorThread() {
	    }

	    public void run() {
	    	monitorthreadrunnable=true;
	      if (mDevice == null) return;
	    	  long time;
	    //	  long imagecurrenttime;
	        	ExecutorService executor = Executors.newSingleThreadExecutor();
	        	Future<FBImage> future;
	        	//在这里可以做别的任何事情
	        	//同上面取得结果的代码
	        while (monitorthreadrunnable == true) {
        		//imagecurrenttime=System.currentTimeMillis();	
	        	try {
	        		future = executor.submit(
	     	        	   new Callable<FBImage>() {//使用Callable接口作为构造参数
	     	        	       public FBImage call() throws IOException {
	     	        	      //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型
	     								return getDeviceImage();//概率卡住
	     						}
	     	        	   });
					tempfbImage=future.get(3500, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
				} catch (java.util.concurrent.TimeoutException e) {
					// TODO Auto-generated catch block
					//timeout
					com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
					tempfbImage=null;
				}
			//	time=System.currentTimeMillis()-imagecurrenttime;
//				if(time<500){
//					try {
//						Thread.sleep(500-time);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated c atch block
//						com.Main.ThenToolsRun.logger.log(Level.WARNING,e.toString());LoggerUtil.printException(e);
//					}
//				}
				if(tempfbImage==null&&com.Main.ThenToolsRun.getdevices.getDevice()!=null){
					com.Main.ThenToolsRun.logger.log(Level.INFO,"get tempfbImage timeout, restart!");
					stopMonitor();
					startMonitor();
					break;
				}else{
		          SwingUtilities.invokeLater(new Runnable() {
				      @Override  
		            public void run() {
		              setImage(tempfbImage);
				      }
		          });
				}
	        }
	        executor.shutdown();
	        com.Main.ThenToolsRun.logger.log(Level.INFO,"end mirrorui thread.");
	      }
	    }
	  
	    private FBImage getDeviceImage() throws IOException {
	      boolean success = true;
	      boolean debug = false;
	      FBImage fbImage = null;
	      RawImage tmpRawImage = null;
	      RawImage rawImage = null;

	      if (success) {
	        try {
	          tmpRawImage = mDevice.getScreenshot();//时间长了会卡住!!!
	          if (tmpRawImage == null) {
	            success = false;
	          }
	          else if (!debug) {
	            rawImage = tmpRawImage;
	          } else {
	            rawImage = new RawImage();
	            rawImage.version = 1;
	            rawImage.bpp = 32;
	            rawImage.size = (tmpRawImage.width * tmpRawImage.height * 4);
	            rawImage.width = tmpRawImage.width;
	            rawImage.height = tmpRawImage.height;
	            rawImage.red_offset = 0;
	            rawImage.red_length = 8;
	            rawImage.blue_offset = 16;
	            rawImage.blue_length = 8;
	            rawImage.green_offset = 8;
	            rawImage.green_length = 8;
	            rawImage.alpha_offset = 0;
	            rawImage.alpha_length = 0;
	            rawImage.data = new byte[rawImage.size];

	            int index = 0;
	            int dst = 0;
	            for (int y = 0; y < rawImage.height; ++y) {
	              for (int x = 0; x < rawImage.width; ++x) {
	                int value = tmpRawImage.data[(index++)] & 0xFF;
	                value |= tmpRawImage.data[(index++)] << 8 & 0xFF00;
	                int r = (value >> 11 & 0x1F) << 3;
	                int g = (value >> 5 & 0x3F) << 2;
	                int b = (value >> 0 & 0x1F) << 3;

	                rawImage.data[(dst++)] = (byte)r;
	                rawImage.data[(dst++)] = (byte)g;
	                rawImage.data[(dst++)] = (byte)b;
	                rawImage.data[(dst++)] = -1;
	              }
	            }

	          }

	        }
	        catch (IOException | TimeoutException | AdbCommandRejectedException localIOException)
	        {
	        }
	        finally
	        {
	          if ((rawImage == null) || (
	            (rawImage.bpp != 16) && (rawImage.bpp != 32))) {
	            success = false;
	          }
	        }
	      }

	      if (success)
	      {
	        int imageHeight;
	        int imageWidth;
	        if (ScreenMirrorUI.this.mPortrait) {
	          imageWidth = rawImage.width;
	          imageHeight = rawImage.height;
	        } else {
	          imageWidth = rawImage.height;
	          imageHeight = rawImage.width;
	        }

	        fbImage = new FBImage(imageWidth, imageHeight, 
	          1, 
	          rawImage.width, rawImage.height);

	        byte[] buffer = rawImage.data;
	        int redOffset = rawImage.red_offset;
	        int greenOffset = rawImage.green_offset;
	        int blueOffset = rawImage.blue_offset;
	        int alphaOffset = rawImage.alpha_offset;
	        int redMask = getMask(rawImage.red_length);
	        int greenMask = getMask(rawImage.green_length);
	        int blueMask = getMask(rawImage.blue_length);
	        int alphaMask = getMask(rawImage.alpha_length);
	        int redShift = 8 - rawImage.red_length;
	        int greenShift = 8 - rawImage.green_length;
	        int blueShift = 8 - rawImage.blue_length;
	        int alphaShift = 8 - rawImage.alpha_length;

	        int index = 0;

	        if (rawImage.bpp == 16)
	        {
	          int offset1;
	          int offset0;
	          if (!ScreenMirrorUI.this.mAdjustColor) {
	            offset0 = 0;
	            offset1 = 1;
	          } else {
	            offset0 = 1;
	            offset1 = 0;
	          }
	          if (mPortrait) {
	            for (int y = 0; y < rawImage.height; ++y)
	              for (int x = 0; x < rawImage.width; ++x) {
	                int value = buffer[(index + offset0)] & 0xFF;
	                value |= buffer[(index + offset1)] << 8 & 0xFF00;
	                int r = (value >>> redOffset & redMask) << redShift;
	                int g = (value >>> greenOffset & greenMask) << greenShift;
	                int b = (value >>> blueOffset & blueMask) << blueShift;
	                value = 0xFF000000 | r << 16 | g << 8 | b;
	                index += 2;
	                fbImage.setRGB(x, y, value);
	              }
	          }
	          else {
	            for (int y = 0; y < rawImage.height; ++y)
	              for (int x = 0; x < rawImage.width; ++x) {
	                int value = buffer[(index + offset0)] & 0xFF;
	                value |= buffer[(index + offset1)] << 8 & 0xFF00;
	                int r = (value >>> redOffset & redMask) << redShift;
	                int g = (value >>> greenOffset & greenMask) << greenShift;
	                int b = (value >>> blueOffset & blueMask) << blueShift;
	                value = 0xFF000000 | r << 16 | g << 8 | b;
	                index += 2;
	                fbImage
	                  .setRGB(y, rawImage.width - x - 1, 
	                  value);
	              }
	          }
	        }
	        else if (rawImage.bpp == 32)
	        {
	          int offset3;
	          int offset0;
	          int offset1;
	          int offset2;
	          if (!ScreenMirrorUI.this.mAdjustColor) {
	             offset0 = 0;
	             offset1 = 1;
	             offset2 = 2;
	            offset3 = 3;
	          } else {
	            offset0 = 3;
	            offset1 = 2;
	            offset2 = 1;
	            offset3 = 0;
	          }
	          if (ScreenMirrorUI.this.mPortrait) {
	            for (int y = 0; y < rawImage.height; ++y)
	              for (int x = 0; x < rawImage.width; ++x)
	              {
	                int value = buffer[(index + offset0)] & 0xFF;
	                value |= (buffer[(index + offset1)] & 0xFF) << 8;
	                value |= (buffer[(index + offset2)] & 0xFF) << 16;
	                value |= (buffer[(index + offset3)] & 0xFF) << 24;
	                int r = (value >>> redOffset & redMask) << redShift;
	                int g = (value >>> greenOffset & greenMask) << greenShift;
	                int b = (value >>> blueOffset & blueMask) << blueShift;
	                int a;
	                if (rawImage.alpha_length == 0)
	                  a = 255;
	                else {
	                  a = (value >>> alphaOffset & alphaMask) << alphaShift;
	                }
	                value = a << 24 | r << 16 | g << 8 | b;
	                index += 4;
	                fbImage.setRGB(x, y, value);
	              }
	          }
	          else {
	            for (int y = 0; y < rawImage.height; ++y) {
	              for (int x = 0; x < rawImage.width; ++x)
	              {
	                int value = buffer[(index + offset0)] & 0xFF;
	                value |= (buffer[(index + offset1)] & 0xFF) << 8;
	                value |= (buffer[(index + offset2)] & 0xFF) << 16;
	                value |= (buffer[(index + offset3)] & 0xFF) << 24;
	                int r = (value >>> redOffset & redMask) << redShift;
	                int g = (value >>> greenOffset & greenMask) << greenShift;
	                int b = (value >>> blueOffset & blueMask) << blueShift;
	                int a;
	                if (rawImage.alpha_length == 0)
	                  a = 255;
	                else {
	                  a = (value >>> alphaOffset & alphaMask) << alphaShift;
	                }
	                value = a << 24 | r << 16 | g << 8 | b;
	                index += 4;
	                fbImage
	                  .setRGB(y, rawImage.width - x - 1, 
	                  value);
	              }
	            }
	          }
	        }
	      }

	      return fbImage;
	    }

	    public int getMask(int length) {
	      int res = 0;
	      for (int i = 0; i < length; ++i) {
	        res = (res << 1) + 1;
	      }

	      return res;
	    }
	  

		//Language 
		public String getString(String flag){
			switch(flag){
			case "title": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "屏幕镜像: ";
			}else{
				return "Screen Mirror: ";
			}
			case "menuItemSelectDevice": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "选择设备";
			}else{
				return "Select Device...";
			}
			case "menuOrientation": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "显示方向";
			}else{
				return "Orientation";
			}
			case "radioButtonMenuItemPortrait": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "竖屏";
			}else{
				return "Portrait";
			}
			case "radioButtonMenuItemLandscape": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "横屏";
			}else{
				return "Landscape";
			}
			case "menuZoom": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "放大";
			}else{
				return "Zoom";
			}
			case "mAdjustColorCheckBoxMenuItem": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "调整颜色";
			}else{
				return "Adjust Color";
			}
			case "menuItemSaveImage": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "保存图片";
			}else{
				return "Save Image...";
			}
			case "menuItemAbout": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "取消";
			}else{
				return "Cancle";
			}
			case "radioButtonMenuItemZoomDIY": 
				if(com.Main.ThenToolsRun.Language.equals("CN")){
					return "自定义";
			}else{
				return "DIY";
			}
				default: return "";
			}	
		}
}
