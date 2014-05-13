
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Snake {
	
	public LinkedList<Node> body;//linkedlist相当于数据结构的链表，容易增加和删除。用这个存储蛇的身体位置
	
	public int dir=1;//dir 1=up 2=right 3=down 4=left

	public Snake(){//初始化为蛇增加身体，目前由6个Node组成。
		body=new LinkedList<Node>();
		body.add(new Node(100,100));
		body.add(new Node(100,110));
		body.add(new Node(100,120));
		body.add(new Node(100,130));
		body.add(new Node(100,140));
		body.add(new Node(100,150));
		
	}
	
	public void keyPressed(KeyEvent e) {//键盘监听，控制蛇的行进方向
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT :
			dir=4;
			break;
		case KeyEvent.VK_UP :
			dir=1;
			break;
		case KeyEvent.VK_RIGHT :
			dir=2;
			break;
		case KeyEvent.VK_DOWN :
			dir=3;
			break;
		}
	}
}
class Node{//组成蛇的基础部分
	public int x,y;
	public Node(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	
		}
