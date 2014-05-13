import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SnakeClient extends Frame {//主窗口
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	public Snake mySnake = new Snake();
	
	public Image offScreenImage = null;
	
	public void drawSnake(Graphics g) {//包含蛇行进方向的判断，向前进方向加一个Node表示蛇头到此处，同时移除最后一个Node表示蛇尾离开此处
		LinkedList<Node> body=mySnake.body;//将蛇身传递出来操作

		Node head=(Node) body.getFirst();//以目前位置为基准，判断下一步Node的位置
		
		int x = head.x,
			y = head.y;

		switch(mySnake.dir){//等待提取步进值
			case 1:
				y-=5;
				break;
			case 2:
				x+=5;
				break;
			case 3:
				y+=5;
				break;
			case 4:
				x-=5;
				break;
		}
		Node nnode=new Node(x,y);
		body.addFirst(nnode);
		body.removeLast();

		ListIterator list=body.listIterator(0);//ListIterator是对ListLink的迭代器，详见api文档的Iterator，类似数组的指针，这个可以后移next()，也可以前移previous()，直接用Iterator也可以，但不能前移
		while(list.hasNext()){//将蛇身画完为止
			
			Node paint=(Node) list.next();
			drawNode(g,paint);
		}
	}

	public void drawNode(Graphics gg,Node n){
		
		gg.setColor(Color.RED);
		gg.fillRect(n.x,n.y,10,10);//等待提取宽度
	}
	
	public void update(Graphics g) {//画图，双缓冲，防止闪烁
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		drawSnake(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void lauchFrame() {//对窗口的一系列设置
		this.setLocation(40, 30);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("Snaker");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		
		this.addKeyListener(new KeyMonitor());
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		SnakeClient sc = new SnakeClient();
		sc.lauchFrame();
	}
	
	private class PaintThread implements Runnable {

		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter {//将对键盘的操作传到Snake的监听器中

		public void keyPressed(KeyEvent e) {
			mySnake.keyPressed(e);
		}
		
	}
}













