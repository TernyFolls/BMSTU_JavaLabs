import javax.swing.JFrame;

public class Game {
	public static void main(String[] args) {
		JFrame f = new JFrame("Speed2D");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1490, 900);
		f.add(new Road());
		f.setVisible(true);
	}
}
