import java.awt.*;
import java.awt.event.*;

public class SnakeClient extends Frame {
	
	int x = 50, y = 50;
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, 30, 30);
		g.setColor(c);
		
		y += 5;
	}

	public void lauchFrame() {
		this.setLocation(400, 300);
		this.setSize(800, 600);
		this.setTitle("Snaker");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
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

}
