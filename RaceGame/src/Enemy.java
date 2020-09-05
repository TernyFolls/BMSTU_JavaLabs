import java.awt.Image;
import javax.swing.ImageIcon;

public class Enemy {
	double x;
	double y;
	double v;
	Image img = new ImageIcon("res/enemy.png").getImage();
	Road road;
	
	public Enemy(int x, int y, int v, Road road) {
		this.x = x;
		this.y = y;
		this.v = v;
		this.road = road;
				
	}
	
	public void move() {
		x = x - road.p.v + v;
	}
	
	
}
