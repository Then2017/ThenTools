package com.CheckTP;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.Serializable;
//ͼ�λ����� ���ڻ��Ƹ���ͼ��
//���࣬����ͼ�ε�Ԫ���õ����еĽӿڣ�����ʹ�õ�
//���������Էŵ������У�������Ա����ظ�����

/*��ͨ��ʵ�� java.io.Serializable �ӿ������������л����ܡ�
δʵ�ִ˽ӿڵ��ཫ�޷�ʹ���κ�״̬���л������л���
�����л�������������ͱ����ǿ����л��ġ����л��ӿ�û�з������ֶΣ�
�����ڱ�ʶ�����л������塣*/


public class TPDrawing implements Serializable {

int x1,x2,y1,y2;   	    //������������
int  R,G,B;				//����ɫ������
float stroke ;			//����������ϸ������

void draw(Graphics2D g2d ){}//�����ͼ����
}

class Drag extends TPDrawing//ֱ����
{
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));// Ϊ Graphics2D ���������� Paint ���ԡ�
		// ʹ��Ϊ null �� Paint ������ô˷����Դ� Graphics2D �ĵ�ǰ Paint ����û���κ�Ӱ�졣

		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		// setStroke(Stroke s)Ϊ Graphics2D ���������� Stroke
		// BasicStroke �ඨ�����ͼ��ͼԪ�����������Ե�һ����������
		// BasicStroke.CAP_ROUNDʹ�ð뾶���ڻ��ʿ��һ���Բ��װ�ν���δ��յ���·���������߶�
		// BasicStroke.JOIN_BEVELͨ��ֱ�����ӿ�����������ǣ���·���߶�������һ��
		g2d.drawLine(x1, y1, x2, y2);// ��ֱ��
		//g2d.draw(new Line2D.Double(x1, y1, x2, y2));
	}
}

class Tap extends TPDrawing{//ʵ��Բ��
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.fillOval(x2-5,y2-5,10,10);
	}
}
class Longtap extends TPDrawing{//ʵ��Բ��
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.fillOval(x2-5,y2-5,10,10);
	}
}


