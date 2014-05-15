
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Snake {
	
	public LinkedList<Node> body;
	
	public int dir=1;//dir 1=up 2=right 3=down 4=left
	public int size;//链表获取size需要遍历，提取出来方便操作

	public Snake(){
		body=new LinkedList<Node>();
		addHead(new Node(100,100));
		addHead(new Node(100,110));
		//addHead(new Node(100,120));
		//addHead(new Node(100,130));
		//addHead(new Node(100,140));
		//addHead(new Node(100,150));
		//addHead(new Node(100,160));
		//addHead(new Node(100,170));
		//addHead(new Node(100,180));
		//addHead(new Node(100,190));
		//addHead(new Node(100,200));
		//addHead(new Node(100,210));
		size=body.size();
	}

	public void addHead(Node n){
		body.add(n);
		SnakeClient.check[n.x][n.y]=1;
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

