import java.awt.*;
import java.util.*;

public class Player{
	public static final double SHOT_FREQUENCY = 2; // bullets/s
	public static final double SPEED = 60;// pxls/s
	public static final int WIDTH = 40;
	public static final int HEIGHT = 30;
	public static final int MAX_HEALTH=4;

	private double x, y, maxX;
	private int health;
	private long lastShot = 0;

	public Player(double x_, double y_, double maxX_){
		x = x_;
		y = y_;
		maxX = maxX_;
		health = MAX_HEALTH;
	}

	public void moveRight(double dt){
		x+=dt*SPEED;
		if(x > maxX)
			x = maxX;
	}

	public void moveLeft(double dt){
		x-=dt*SPEED;
		if(x < 0)
			x = 0;
	}

	public void hit(){
		health-=1;
	}

	public int getHealth(){
		return health;
	}

	public void shoot(Vector<Bullet> bullets){
		double dt = (System.nanoTime()-lastShot)*1E-9;
		if(dt>1.0/SHOT_FREQUENCY){
			bullets.add(new Bullet(x, y-HEIGHT/2-2, true));
			lastShot = System.nanoTime();
		}
	}

	public boolean hitBy(Bullet b){
		Rectangle bounds = new Rectangle((int)x-WIDTH/2, (int)y-HEIGHT/2, WIDTH, HEIGHT);
		if(bounds.contains(new Point((int)b.getX(), (int)b.getY())))
			return true;
		//else
		return false;
	}

	public void draw(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect((int)x-WIDTH/2, (int)y-HEIGHT/2, WIDTH, HEIGHT);
		g.setColor(Color.RED);
		g.fillRect(10, 10, (health*100)/MAX_HEALTH, 10);
	}
}