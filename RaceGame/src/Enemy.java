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
	
	public void move() {
		x = x - road.p.v + v;
		if (diff == 1) {
			if (y < Player.MAX_TOP) {
				direction = false;
			}
			if (y > Player.MAX_BOT) {
				direction = true;
			}

			y += (direction ? -1 : 1) * 6;
		}
	}

}
