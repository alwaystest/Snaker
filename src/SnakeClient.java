import java.awt.*;
import java.awt.event.*;

public class SnakeClient extends Frame {
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(50, 50, 30, 30);
		g.setColor(c);
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
	}

	public static void main(String[] args) {
		SnakeClient sc = new SnakeClient();
		sc.lauchFrame();
	}

}
