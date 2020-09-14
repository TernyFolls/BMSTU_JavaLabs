import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Player {

	
	public static final double MAX_V = 80;
	public static final double MAX_TOP = 200;
	public static final double MAX_BOT = 600;
	public static final double SHAKE = 5;
	public static final double COFF = 3;
	
	//compile
	//Image img = new ImageIcon(getClass().getClassLoader().getResource("res/player.png")).getImage();
	Image img = new ImageIcon("res/player.png").getImage();
		
	boolean isCrashPlay = false;
	boolean sw = false;
	double v = 0;
	double dv = 0;
	double dy = 0;
	double s = 0;

	
	double x = 30;
	double y = 300;

	double layer1 = 0;
	double layer2 = 1514;

	public Rectangle hitbox() {
		return new Rectangle((int) x + img.getWidth(null) / 4, (int) y + img.getHeight(null) / 4,
				img.getWidth(null) / 2, img.getHeight(null) / 2);
		
	}

	public void move() {
		s += v;
		v += dv;
		if (v <= 7)
			v = 7;
		if (v >= MAX_V) {
			v -= 1;
		}

		if (v >= MAX_V - 40) {
			y -= 0.1 * Math.pow(2, 0.1 * v);
			if (sw) {
				y += 0.2 * Math.pow(2, 0.1 * v);
			}
			sw = !sw;
		}

		y -= dy;
		if (layer2 - v <= 0) {
			layer1 = 0;
			layer2 = 1514;
		}
		if (y <= MAX_TOP) {
			y += 10;
			dy = 0;
		}
		if (y >= MAX_BOT) {
			y -= 10;
			dy = 0;
		}

		layer1 -= v;
		layer2 -= v;
	}
	
	public void crash() {
		//compile
		img = new ImageIcon(getClass().getClassLoader().getResource("res/kaboomP.png")).getImage();
		isCrashPlay = true;
	}


	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			dv = 0.5;
		}
		if (key == KeyEvent.VK_S) {
			dv = -1.5;
		}
		if (key == KeyEvent.VK_LEFT) {
			dy = 11;
			//compile
			//img = new ImageIcon(getClass().getClassLoader().getResource("res/player_left.png")).getImage();
			img = new ImageIcon("res/player_left.png").getImage();
			
		}
		if (key == KeyEvent.VK_RIGHT) {
			dy = -11;
			//compile
			//img = new ImageIcon(getClass().getClassLoader().getResource("res/player_right.png")).getImage();
			img = new ImageIcon("res/player_right.png").getImage();
			
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_S) {
			dv = 0;
		}
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
			dy = 0;
			//compile
			//img = new ImageIcon(getClass().getClassLoader().getResource("res/player.png")).getImage();
			img = new ImageIcon("res/player.png").getImage();
		}
	}
}
