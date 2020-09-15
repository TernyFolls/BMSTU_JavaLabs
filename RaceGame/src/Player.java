import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Player {

	public static final float MAX_V = 80f;
	public static final float MAX_TOP = 200f;
	public static final float MAX_BOT = 600f;
	public static final float SHAKE = 5f;
	public static final float COFF = 3f;

	// compile
	// Image img = new
	// ImageIcon(getClass().getClassLoader().getResource("res/player.png")).getImage();
	Image img = new ImageIcon("res/player.png").getImage();

	boolean isCrashPlay = false;
	boolean sw = false;
	float v = 0f;
	float dv = 0f;
	float dy = 0f;
	float s = 0f;

	float x = 30f;
	float y = 300f;

	float layer1 = 0f;
	float layer2 = 1514f;

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
			y -= 0.1 * v;
			if (sw) {
				y += 0.2 * v;
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
		// compile
		// img = new
		// ImageIcon(getClass().getClassLoader().getResource("res/kaboomP.png")).getImage();
		img = new ImageIcon("res/kaboomP.png").getImage();
		isCrashPlay = true;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			dv = 0.5f;
		}
		if (key == KeyEvent.VK_S) {
			dv = -1.5f;
		}
		if (key == KeyEvent.VK_LEFT) {
			dy = 11;
			// compile
			// img = new
			// ImageIcon(getClass().getClassLoader().getResource("res/player_left.png")).getImage();
			img = new ImageIcon("res/player_left.png").getImage();

		}
		if (key == KeyEvent.VK_RIGHT) {
			dy = -11;
			// compile
			// img = new
			// ImageIcon(getClass().getClassLoader().getResource("res/player_right.png")).getImage();
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
			// compile
			// img = new
			// ImageIcon(getClass().getClassLoader().getResource("res/player.png")).getImage();
			img = new ImageIcon("res/player.png").getImage();
		}
	}
}
