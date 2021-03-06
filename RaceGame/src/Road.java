import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Road extends JPanel implements ActionListener, Runnable {

	int score = 0;
	int tutorX = -300;
	int tutorY = 600;

	Timer mainTimer = new Timer(13, this);

	// compile
	// Image img = new
	// ImageIcon(getClass().getClassLoader().getResource("res/bg_road.png")).getImage();
	Image img = new ImageIcon("res/bg_road.png").getImage();

	Player p = new Player();

	Thread enemiesFactory = new Thread(this);
	Thread scoreCounter = new Thread(new Score());

	List<Enemy> enemies = new ArrayList<Enemy>();

	public Road() {
		mainTimer.start();
		enemiesFactory.start();
		scoreCounter.start();
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
		g.setFont(new Font("Arial", Font.ITALIC, 25));
		g.setColor(new Color(255, 100, 100));
		g = (Graphics2D) g;

		g.drawImage(img, (int) p.layer1, 0, null);
		g.drawImage(img, (int) p.layer2, 0, null);
		Iterator<Enemy> i = enemies.iterator();
		while (i.hasNext()) {
			Enemy e = i.next();
			g.drawImage(e.img, (int) e.x, (int) e.y, null);
			// g.drawRect((int) e.x + e.img.getWidth(null) / 4, (int) e.y +
			// e.img.getHeight(null) / 3,
			// e.img.getWidth(null) / 2, e.img.getHeight(null) / 3);
		}
		g.drawImage(p.img, (int) p.x, (int) p.y, null);
		// g.drawRect((int) p.x + p.img.getWidth(null) / 4, (int) p.y +
		// p.img.getHeight(null) / 3,
		// p.img.getWidth(null) / 2, p.img.getHeight(null) / 3);

		g.drawString("Score: " + score, 1150, 100);
		g.setColor(new Color(0, 0, 255));
		g.drawString("Speed: " + (int) (p.v * 3.5), 100, 100);
		g.drawString("km\\h", 235, 100);
		if (tutorX < 2000) {
			g.setColor(new Color(0, 255, 0));
			g.drawString("A to Acceleration", tutorX++, tutorY);
			g.setColor(new Color(255, 0, 0));
			g.drawString("S to Stop", tutorX++, tutorY+20);
			g.setColor(new Color(0, 0, 255));
			g.drawString("Left to Left", tutorX++, tutorY+40);
			g.setColor(new Color(255, 255, 0));
			g.drawString("Right to Right", tutorX++, tutorY+60);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (!p.isCrashPlay) {
			p.move();
			Iterator<Enemy> i = enemies.iterator();
			while (i.hasNext()) {
				Enemy carItem = i.next();
				carItem.move();
				Iterator<Enemy> j = enemies.iterator();
				while (j.hasNext()) {
					Enemy car2 = j.next();
					if (car2 != carItem) {
						if (car2.hitbox().intersects(carItem.hitbox())) {
							car2.x = -3500;
							// carItem.x = -3500;
							System.err.println("Столкновение");
							// carItem.changeIco();
							carItem.isKaboomed = true;
							carItem.crash(true);
							break;
						}
					}
				}
				if (p.hitbox().intersects(carItem.hitbox())) {
					p.crash();
					carItem.crash(false);
					//

					//
				}
				// || p.hitbox().intersects(carItem.hitbox())
				if (carItem.getX() <= -3000) {
					i.remove();
				}

			}
			repaint();
		} else {
			enemiesFactory.interrupt();
			scoreCounter.interrupt();
			JOptionPane.showMessageDialog(null, "      Game over\nYour score: " + score);
			//System.exit(1);
			
		}
	}

	public class Score extends Thread implements Runnable {
		@Override
		public void run() {

			while (true) {
				try {

					if (!isInterrupted()) {
						Thread.sleep(100);
						score += p.v;
					} else {
						throw new InterruptedException();

					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			Random rand = new Random();
			try {
				// Создание врагов
				Thread.sleep(rand.nextInt(500) + 3000);
				enemies.add(new Enemy(1400, rand.nextInt(100) + 400, rand.nextInt(46) + 30, rand.nextInt(2), this));
				// enemies.add(new Enemy(1200, 200, rand.nextInt(80) + 20, this));

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
