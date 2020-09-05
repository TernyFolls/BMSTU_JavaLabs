import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Road extends JPanel implements ActionListener, Runnable {

	Timer mainTimer = new Timer(20, this);

	Image img = new ImageIcon("res/bg_road.png").getImage();

	Player p = new Player();


	Thread enemiesFactory = new Thread(this);

	List<Enemy> enemies = new ArrayList<Enemy>();

	public Road() {
		mainTimer.start();
		enemiesFactory.start();
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);

	}

	private class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			p.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			p.keyReleased(e);
		}
	}

	public void paint(Graphics g) {



		g = (Graphics2D) g;

		g.drawImage(img, (int) p.layer1, 0, null);
		g.drawImage(img, (int) p.layer2, 0, null);
		Iterator<Enemy> i = enemies.iterator();
		while (i.hasNext()) {
			Enemy e = i.next();
			g.drawImage(e.img, (int) e.x, (int) e.y, null);
		}
		g.drawImage(p.img, (int) p.x, (int) p.y, null);


	}

	public void actionPerformed(ActionEvent e) {
		p.move();
		Iterator<Enemy> i = enemies.iterator();
		while (i.hasNext()) {
			i.next().move();			
		}
		repaint();
	}

	@Override
	public void run() {
		while (true) {
			Random rand = new Random();
			try {
				// Создание врагов
				Thread.sleep(rand.nextInt(2000) + 1500);
				enemies.add(new Enemy(1400, rand.nextInt(400) + 200, rand.nextInt(46) + 30, this));
				//enemies.add(new Enemy(1200, 200, rand.nextInt(80) + 20, this));
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
