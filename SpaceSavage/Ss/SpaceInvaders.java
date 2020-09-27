import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class SpaceInvaders extends JApplet implements KeyListener, Runnable{

	public static final int PLAYING = 0;
	public static final int WON = 1;
	public static final int LOST = 2;

	private Player player;
	private boolean left, right;
	private Vector<BadGuy> badGuys;
	private Vector<Bullet> bullets;
	private int state = PLAYING;

	@Override
	public void init(){
		addKeyListener(this);

		player = new Player(getWidth()/2, getHeight()-Player.HEIGHT/2, getWidth());

		badGuys = new Vector<BadGuy>();
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 4; j++){
				BadGuy newBG = new BadGuy(BadGuy.WIDTH*(2*i+2), BadGuy.HEIGHT*(2*j+2), getWidth(), false);
				badGuys.add(newBG);
			}
		}

		bullets = new Vector<Bullet>();

		new Thread(this).start();
	}

	@Override
	public void paint(Graphics graphics){
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		player.draw(g);
		for(int i = 0; i < badGuys.size(); i++)
			badGuys.get(i).draw(g);
		for(int i = 0; i < bullets.size(); i++)
			bullets.get(i).draw(g);
		if(state==WON)
			g.drawString("YOU WIN!", getWidth()/2-30, getHeight()/2-6);
		else if(state==LOST)
			g.drawString("You Lose.", getWidth()/2-30, getHeight()/2-6);
		graphics.drawImage(image, 0, 0, null);
	}


	@Override
	public void keyPressed(KeyEvent ke){
		switch(ke.getKeyCode()){
		case KeyEvent.VK_LEFT: left = true; break;
		case KeyEvent.VK_RIGHT: right = true; break;
		}
	}

	@Override
	public void keyReleased(KeyEvent ke){
		switch(ke.getKeyCode()){
		case KeyEvent.VK_LEFT: left = false; break;
		case KeyEvent.VK_RIGHT: right = false; break;
		}
	}

	@Override
	public void keyTyped(KeyEvent ke){
		if(ke.getKeyChar()==' ')
			player.shoot(bullets);
	}

	//dt in seconds.
	public void update(double dt){
		if(state == PLAYING){
			if(left)
				player.moveLeft(dt);
			if(right)
				player.moveRight(dt);
			for(int i = 0; i < badGuys.size(); i++)
				badGuys.get(i).update(dt, bullets);
			for(int i = 0; i < bullets.size(); i++)
				bullets.get(i).update(dt);
			testForBulletCollisions();
			if(hasWon())
				state = WON;
			else if(hasLost())
				state = LOST;
		}
	}

	public void testForBulletCollisions(){
		top:for(int i = 0; i < bullets.size(); i++){
			for(int j = 0; j < badGuys.size(); j++){
				if(badGuys.get(j).hitBy(bullets.get(i)) && bullets.get(i).goingUp()){
					bullets.remove(i);
					badGuys.remove(j);
					i--;
					continue top;
				}
			}
			if(player.hitBy(bullets.get(i)) && !bullets.get(i).goingUp()){
				player.hit();
				bullets.remove(i);
				i--;
			}
		}
	}

	public boolean hasWon(){
		return badGuys.size()==0;
	}

	public boolean hasLost(){
		for(int i = 0; i < badGuys.size(); i++){
			if(badGuys.get(i).getY()>getHeight())
				return true;
		}
		if(player.getHealth()<=0)
			return true;
		return false;
	}


	@Override
	public void run(){
		long time = System.nanoTime();
		while(true){
			try{Thread.sleep(50);}catch(Throwable t){}
			double dt = (System.nanoTime()-time)*1E-9;
			time = System.nanoTime();
			update(dt);

			repaint();
		}
	}
}