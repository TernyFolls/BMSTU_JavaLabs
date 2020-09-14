import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Enemy {
	double x;
	double y;
	double v;
	int diff;
	boolean direction;
	boolean isKaboomed = false;
	boolean fuse = false;

	Image img = new ImageIcon("res/enemy.png").getImage();
	Road road;

	public Enemy(int x, int y, int v, int diff, Road road) {
		this.x = x;
		this.y = y;
		this.v = v;
		this.road = road;
		this.diff = diff;
		direction = new Random().nextBoolean();
	}

	public Rectangle hitbox() {
		return new Rectangle((int) x + img.getWidth(null) / 4, (int) y + img.getHeight(null) / 4,
				img.getWidth(null) / 2, img.getHeight(null) / 2);

	}

	public double getX() {
		return x;
	}

//	public void crash2() {
//		img = new ImageIcon("res/kaboom2.png").getImage();
//	}

	public void move() {

		x = x - road.p.v + v;
		if (diff == 1) {
			if (y < Player.MAX_TOP) {
				direction = false;
			}
			if (y > Player.MAX_BOT) {
				direction = true;
			}
			if (!isKaboomed) {
				y += (direction ? -1 : 1) * 6;
			}
		}
	}

	public void crash(boolean x) {
		if (!fuse) {
			img = x ? new ImageIcon("res/kaboom2.png").getImage() : new ImageIcon("res/kaboomE.png").getImage();
			fuse = true;
		}
		if (v < 10) {
			v = 0;
		} else {
			v = v * (-0.01);
		}
	}

}
