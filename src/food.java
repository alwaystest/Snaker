import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class food {
	public int x,y;	
	public food(){
		Random a = new Random();
		x=a.nextInt(800);
		y=a.nextInt(600);
		SnakeClient.check[x][y]=2;
		//System.out.println(x+"  "+y);
		
	}
	public food(int w,int h){
		Random a = new Random();
		x=10*a.nextInt(w/10);//使食物落到规定点上
		y=10*a.nextInt(h/10);
		SnakeClient.check[x][y]=2;
		//System.out.println(x+"  "+y);
		
	}

}
