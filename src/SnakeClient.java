import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;
import javax.imageio.*;

public class SnakeClient extends Frame {//主窗口
	JFrame frame;
	//Frame gframe;//不可以声明，否则不能画出蛇
	public static final int GAME_WIDTH = 800;//窗体的大小包括为边框指定的所有区域
	public static final int GAME_HEIGHT = 600;
	public static final int IMG_WIDTH=GAME_WIDTH-8;
	public static final int IMG_HEIGHT=GAME_HEIGHT-30;
	
	public static int NodeWidth=10;//步进 node宽度
	public static int[][] check=new int[GAME_WIDTH][GAME_HEIGHT];
	public static boolean runstate=true;
	public Snake mySnake = new Snake();
	public sqlite sql=new sqlite();//未加入connection.close()
	//public food f= new food(GAME_WIDTH,GAME_HEIGHT);
	public food f= new food();  //测试用例
	Object ob=new Object();//添加监视器 线程的wait notify需要在同一个监视器才能执行,synchronized run无法直接在changestate中找出之前的监视器,把监视器提取出来未免就可以获取到了

	Image offScreenImage = null;
	
	public static void main(String[] args) {
		SnakeClient sc = new SnakeClient();
		sc.startFace();
	}

	public void update(Graphics g) {//画图，双缓冲，防止闪烁
		if(offScreenImage == null) {
			offScreenImage = this.createImage(IMG_WIDTH, IMG_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.gray);
		gOffScreen.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
		gOffScreen.setColor(c);
		drawSnake(gOffScreen);
		drawFood(gOffScreen);
		gOffScreen.drawString(Integer.toString(mySnake.size),10,40);
		g.drawImage(offScreenImage, 4, 27, null);//设置偏移，防止边框遮盖
	}

	private class PaintThread implements Runnable {

		public  void run() {
			while(true) {
				synchronized(this){

				if (!runstate){
					try{
						ob=this;//将监视器传出去 等待notify
						//System.out.println("wait");
						//System.out.println(this.getClass());
						this.wait();
					}
					catch(InterruptedException ex){
						ex.printStackTrace();
					}
				}
				}

				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	/*	public synchronized void wakeup(){
			notify();	
		}//尝试通过调用相同线程下的synchronized获取同一监视器  失败*/
	}
	
	boolean restart(){
		sql.in(mySnake.size);
		check=new int[GAME_WIDTH][GAME_HEIGHT];
		mySnake = new Snake();
		f= new food();
		
		sql.out();
		System.gc();//显式调用垃圾回收器(实际上只是提醒JVM该回收了,具体回收不回收由JVM定),防止重开太多次之前的对象没有回收,造成内存溢出什么的
		System.out.println("Restart");
		return true;
	}
	
	public void drawNode(Graphics gg,Node n){
		gg.setColor(Color.RED);
		gg.fillRect(n.x,n.y,NodeWidth,NodeWidth);
}
	
	public void drawSnake(Graphics g) {//包含蛇行进方向的判断，向前进方向加一个Node表示蛇头到此处，同时移除最后一个Node表示蛇尾离开此处
		LinkedList<Node> body=mySnake.body;//将蛇身传递出来操作
		Node head=(Node) body.getFirst();//以目前位置为基准，判断下一步Node的位置
		Node end=(Node) body.getLast();
		int x = head.x,
			y = head.y,
			x1=end.x,
			y1=end.y;
		switch(mySnake.dir){//已提取步进值   
			case 1:
				y-=NodeWidth;
				/*if(z==0){
					x-=1;
					z=1;
				}
				else{
					x+=1;
					z=0;
				}*/
				break;
			case 2:
				x+=NodeWidth;
				/*if(z==0){
					y-=1;
					z=1;
				}
				else{
					y+=1;
					z=0;
				}*/
				break;
			case 3:
				y+=NodeWidth;
				/*if(z==0){
					x-=1;
					z=1;
				}
				else{
					x+=1;
					z=0;
				}*/
				break;
			case 4:
				x-=NodeWidth;
				/*if(z==0){
					y-=1;
					z=1;
				}
				else{
					y+=1;
					z=0;
				}*/
				break;
		}
		if (x<0||x>=IMG_WIDTH-NodeWidth||y<0||y>=IMG_HEIGHT){
			//System.out.println(x+"  "+y);
			Thread.yield();
		}
		else{
			if(check[x][y]==1)
				System.out.println("Failed");
			else if(check[x][y]==2){
				body.addFirst(new Node(f.x,f.y));
				check[f.x][f.y]=1;
				mySnake.size++;
				while(check[f.x][f.y]==1){
				f=new food(IMG_WIDTH,IMG_HEIGHT);
				//f=new food(GAME_WIDTH,40);
				//System.out.println(f.x+" aaa "+f.y);
				}
			}

			Node nnode=new Node(x,y);
			body.addFirst(nnode);
			check[x][y]=1;
			body.removeLast();
			check[x1][y1]=0;
		}

		ListIterator<Node> list=body.listIterator(0);//ListIterator是对ListLink的迭代器，详见api文档的Iterator，类似数组的指针，这个可以后移next()，也可以前移previous()，直接用Iterator也可以，但不能前移
		while(list.hasNext()){//将蛇身画完为止 不进行强制转换会报错
			Node paint=(Node) list.next();
			drawNode(g,paint);
		}
	}

	public void drawFood(Graphics gg)
	{
		gg.setColor(Color.blue);
		gg.fillRect(f.x,f.y,NodeWidth,NodeWidth);
	}

	public void startFace() {

		frame=new JFrame();		
		//frame.setLayout(new BorderLayout());
		ImgPanel ip = new ImgPanel("iphone.jpg");
		//JLabel startlabel=new JLabel();
		ip.setLayout(null);
		ip.setBounds(0, 0, 400, 300);
		JButton startbutton=new JButton("开始游戏");
		startbutton.setBounds(250, 140, 100, 50);
		//ImageIcon a = new ImageIcon("iphone.jpg");
		//startbutton.isOpaque();//不知道代智这里要干什么
		//frame.add(startbutton,BorderLayout.CENTER);
		ip.add(startbutton);
		//startlabel.add(startbutton);
		//startlabel.setIcon(a);
		startbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameFace();//进入另一个界面
				
				frame.dispose();  //主界面关闭
			}
		
		});
		
		JButton quitbutton=new JButton("退出游戏");
		quitbutton.setBounds(250, 200, 100, 50);
		ip.add(quitbutton);
		//startlabel.add(quitbutton);
		//frame.add(quitbutton,BorderLayout.SOUTH);
		quitbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);  //主界面关闭
			}
		
		});
		frame.getContentPane().add(ip);
		//frame.getContentPane().add(startlabel);
		frame.setTitle("Snaker");
		frame.setSize(400,300);
		frame.setLocation(500,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void gameFace() {//对窗口的一系列设置
		
		this.setLocation(300, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);//加大窗体，减小边框对界面的遮盖
		this.setTitle("Snaker");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		KeyMonitor km=new KeyMonitor();
		this.addKeyListener(km);


		this.setVisible(true);

		Thread p=new Thread(new PaintThread());
		p.start();
	}

	public void stopFace() {
		final JFrame sframe=new JFrame();
		//sframe.setLayout(new BorderLayout());
		ImgPanel ip = new ImgPanel("iphone1.png");
		ip.setLayout(null);
		ip.setBounds(0, 0, 400, 300);
		
		JButton backbutton=new JButton("返回游戏");
		//sframe.add(backbutton,BorderLayout.NORTH);
		backbutton.setBounds(10, 10, 100, 50);
		ip.add(backbutton);
		backbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeState();
				sframe.dispose();      //另一个界面关闭
			}
			
		});
		
		
		JButton restartbutton=new JButton("重新开始");
		//sframe.add(restartbutton,BorderLayout.CENTER);
		restartbutton.setBounds(10, 80, 100, 50);
		ip.add(restartbutton);
		restartbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeState();
				restart();
				sframe.dispose();      //另一个界面关闭
			}
			
		});
		JButton menutbutton=new JButton("菜单");
		//sframe.add(menutbutton,BorderLayout.SOUTH);
		menutbutton.setBounds(10,150, 100, 50);
		ip.add(menutbutton);
		menutbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				onlystopFace();
				//frame.setVisible(true);    //主界面显示
				sframe.dispose();
			}
			
		});
		sframe.getContentPane().add(ip);
		sframe.setTitle("Snaker");
		sframe.setSize(400,300);
		sframe.setLocation(500,300);
		sframe.setVisible(true);
	}

	public void onlystopFace(){
		final JFrame osframe=new JFrame();
		//osframe.setLayout(new FlowLayout());
		ImgPanel ip = new ImgPanel("iphone2.jpg");
		ip.setLayout(null);
		ip.setBounds(0, 0, 400, 300);
		
		JButton onlybackbutton=new JButton("退出");	
		onlybackbutton.setBounds(170, 135, 60, 30);
		//osframe.getContentPane().add(onlybackbutton);
		ip.add(onlybackbutton);
		onlybackbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);  //主界面关闭
			}
		
		});
		osframe.getContentPane().add(ip);
		osframe.setTitle("Snaker");
		osframe.setSize(400,300);
		osframe.setLocation(500,300);
		osframe.setVisible(true);
		osframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	boolean ChangeState(){
		runstate=!runstate;
		synchronized(ob){
		if(runstate){

			//System.out.println(this.getClass());
			System.out.println(runstate);
			ob.notify();
		}
		}
		return runstate;
		
	}

	class ImgPanel extends JPanel {
		Image img;
		String add;
		ImgPanel(String add){
			this.add=add;
		}
		public void paintComponent(Graphics g) {
			try {
				// 装载图像
				img = ImageIO.read(new File(add));//eclipse的jvm启动路径是当前项目的根目录
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 绘制图像
			g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), null);
		}
	}
	


	private class KeyMonitor extends KeyAdapter {//将对键盘的操作传到Snake的监听器中
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key==KeyEvent.VK_ENTER){
				ChangeState();
				stopFace();
				//runstate=!runstate;
			}
			if(key==KeyEvent.VK_S&&(e.isControlDown())){
				restart();
				//runstate=!runstate;
			}
			mySnake.keyPressed(e);
		}

	}
}


