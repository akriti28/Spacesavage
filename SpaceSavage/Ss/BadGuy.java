import java.awt.*;
import java.util.Vector;

public class BadGuy{
	public static final int WIDTH = 15;
	public static final int HEIGHT = 15;
	public static final double INITIAL_SPEED_X = 40; //pxls/s
	public static final double INITIAL_SPEED_Y = 2; //pxls/s
	public static final double SPEED_INCREASE = 1.2;
	public static final double INITIAL_BULLET_FREQUENCY = .0025; //bullets / update
	public static final double FREQUENCY_INCREASE = 1.001;

	private double x, y;
	private double dx, dy;
	private double maxX;
	private double bulletFrequency;

	public BadGuy(double x_, double y_, double maxX_, boolean left){
		x = x_;
		y = y_;
		maxX = maxX_;
		if(left)
			dx = -INITIAL_SPEED_X;
		else
			dx = INITIAL_SPEED_X;
		dy = INITIAL_SPEED_Y;
		bulletFrequency = INITIAL_BULLET_FREQUENCY;

	}

	public double getY(){
		return y;
	}

	public double getX(){
		return x;
	}

	public boolean hitBy(Bullet b){
		Rectangle bounds = new Rectangle((int)x-WIDTH/2, (int)y-HEIGHT/2, WIDTH, HEIGHT);
		if(bounds.contains(new Point((int)b.getX(), (int)b.getY())))
			return true;
		//else
		return false;
	}

	public void update(double dt, Vector<Bullet> bullets){
		x+=dx*dt;
		y+=dy*dt;
		if(x>maxX || x<0)
			bounce();
		if(Math.random()<bulletFrequency){
			bullets.add(new Bullet((int)x, (int)y, false));
		}
		bulletFrequency*=FREQUENCY_INCREASE;
	}

	public void bounce(){
		if(x>maxX)
			x = maxX;
		if(x<0)
			x = 0;
		dx*=-SPEED_INCREASE;
		dy*=SPEED_INCREASE;
	}

	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillRect((int)x-WIDTH/2, (int)y-HEIGHT/2, WIDTH, HEIGHT);
	}
}