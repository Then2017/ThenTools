package com.GetScreen;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class SelectDeviceDialog extends JDialog
{
  private JList mList;
  private JScrollPane mScrollPane;
  private JButton mOK;
  private JButton mCancel;
  private DefaultListModel mModel;
  private boolean mIsOK = false;
  private int mSelectedIndex = -1;

  public SelectDeviceDialog(Frame owner, boolean modal, ArrayList<String> initialList)
  {
    super(owner, modal);

    setTitle(getString("title"));
    setBounds(0, 0, 240, 164);
    setResizable(false);

    mModel = new DefaultListModel();
    for (int i = 0; i < initialList.size(); ++i) {
      mModel.addElement(initialList.get(i));
    }

    mList = new JList(mModel);
    if (mModel.getSize() > 0) {
      mSelectedIndex = 0;
      mList.setSelectedIndex(mSelectedIndex);
    }
    mList.addMouseListener(new MouseListener() {
      public void mouseReleased(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 1)
          SelectDeviceDialog.this.onOK();
      }
    });
    mScrollPane = new JScrollPane(mList);
    mScrollPane.setVerticalScrollBarPolicy(20);

    mOK = new JButton(getString("mOK"));
    mOK.setEnabled(mModel.getSize() > 0);
    mOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SelectDeviceDialog.this.onOK();
      }
    });
    mCancel = new JButton(getString("mCancel"));
    mCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    });
    Container container1 = new Container();
    GridLayout gridLayout = new GridLayout(1, 2, 0, 0);
    container1.setLayout(gridLayout);
    container1.add(mOK);
    container1.add(mCancel);

    Container containger = getContentPane();
    containger.add(mScrollPane, "Center");
    containger.add(container1, "South");

    AbstractAction actionOK = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    };
    AbstractAction actionCancel = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    };
    JComponent targetComponent = getRootPane();
    InputMap inputMap = targetComponent.getInputMap();
    inputMap.put(KeyStroke.getKeyStroke(10, 0), "OK");
    inputMap.put(KeyStroke.getKeyStroke(27, 0), "Cancel");
    targetComponent.setInputMap(1, inputMap);
    targetComponent.getActionMap().put("OK", actionOK);
    targetComponent.getActionMap().put("Cancel", actionCancel);
  }

  public int getSelectedIndex()
  {
    return mSelectedIndex;
  }

  public boolean isOK() {
    return mIsOK;
  }

  private void onOK() {
    mSelectedIndex = mList.getSelectedIndex();
    mIsOK = true;
    dispose();
  }

  private void onCancel() {
    dispose();
  }
	//Language 
	public String getString(String flag){
		switch(flag){
		case "title": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "选择设备";
		}else{
			return "Select a Device";
		}
		case "mOK": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "确定";
		}else{
			return "OK";
		}
		case "mCancel": 
			if(com.Main.ThenToolsRun.Language.equals("CN")){
			return "取消";
		}else{
			return "Cancel";
		}
			default: return "";
		}	
	}
}