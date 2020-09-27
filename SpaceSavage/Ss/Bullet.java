import java.awt.*;

public class Bullet{

	public static final int SPEED = 150; //pxls/s

	private double x, y;
	private boolean up;

	public Bullet(double x_, double y_, boolean up_){
		x = x_;
		y = y_;
		up = up_;
	}

	public void update(double dt){
		if(up)
			y-=dt*SPEED;
		else
			y+=dt*SPEED;
	}

	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.drawLine((int)x, (int)y-1, (int)x, (int)y+1);
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public boolean goingUp(){
		return up;
	}
}