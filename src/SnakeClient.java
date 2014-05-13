import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SnakeClient extends Frame {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Snake mySnake = new Snake();
	
	Image offScreenImage = null;
	
	public void drawSnake(Graphics g) {
		LinkedList<Node> body=mySnake.body;

		Node head=(Node) body.getFirst();
		
		int x = head.x,
			y = head.y;

		switch(mySnake.dir){
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

		ListIterator list=body.listIterator(0);
		while(list.hasNext()){
			
			Node paint=(Node) list.next();
			drawNode(g,paint);
		}
	}

	public void drawNode(Graphics gg,Node n){
		
		gg.setColor(Color.RED);
		gg.fillRect(n.x,n.y,10,10);//waiting for bring number out
	}
	
	public void update(Graphics g) {
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

	public void lauchFrame() {
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
	
	private class KeyMonitor extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			mySnake.keyPressed(e);
		}
		
	}
}













