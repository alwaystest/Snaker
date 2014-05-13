
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Snake {
	
	public LinkedList<Node> body;
	
	public int dir=1;//dir 1=up 2=right 3=down 4=left

	public Snake(){
		body=new LinkedList<Node>();
		body.add(new Node(100,100));
		body.add(new Node(100,110));
		body.add(new Node(100,120));
		body.add(new Node(100,130));
		body.add(new Node(100,140));
		body.add(new Node(100,150));
		
	}
	
	public void keyPressed(KeyEvent e) {
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
class Node{
	public int x,y;
	public Node(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	
		}
