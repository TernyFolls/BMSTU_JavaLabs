import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JFrame;

public class FractalExplorer {

	private int side;

	private JImageDisplay jpgImg;

	private FractalGenerator geni = new Mandelbrot();

	private Rectangle2D.Double vsblRange;

	public FractalExplorer(int x) {
		side = x;
		vsblRange = new Rectangle2D.Double();
		geni.getInitialRange(vsblRange);
		jpgImg = new JImageDisplay(x, x);

	}

	class ActiveLisner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			if (e.getActionCommand().equals("—брос")) {
				System.out.println("—брасывание");
				jpgImg.clearImage();
				geni.getInitialRange(vsblRange);
				drawFractal();
			}
		}
	}

	class MouseClass extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			System.out.println(e);
			double xCoord = FractalGenerator.getCoord(vsblRange.x, vsblRange.x + vsblRange.width, side, e.getX());
			double yCoord = FractalGenerator.getCoord(vsblRange.y, vsblRange.y + vsblRange.height, side, e.getY());
			geni.recenterAndZoomRange(vsblRange, xCoord, yCoord, 0.3);
			drawFractal();
		}
	}

	private void drawFractal() {
		int a;
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				double xCoord = FractalGenerator.getCoord(vsblRange.x, vsblRange.x + vsblRange.width, side, i);
				double yCoord = FractalGenerator.getCoord(vsblRange.y, vsblRange.y + vsblRange.height, side, j);
				a = geni.numIterations(xCoord, yCoord);
				float hue = 0.7f + (float) a / 200f;
				int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
				if (a == -1) {
					jpgImg.drawPixel(i, j, 0);
				} else {
					jpgImg.drawPixel(i, j, rgbColor);
				}
			}
		}
		jpgImg.repaint();
	}

	private void createAndShowGUI() {
		JFrame f = new JFrame("Ћабораторна€ работа 4");

		f.setLayout(new BorderLayout());
		f.add(jpgImg, BorderLayout.CENTER);
		JButton b = new JButton("—брос");
		f.add(b, BorderLayout.SOUTH);
		b.addActionListener(new ActiveLisner());
		jpgImg.addMouseListener(new MouseClass());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		f.setResizable(false);
	}

	public static void main(String[] args) {
		FractalExplorer frac = new FractalExplorer(800);
		frac.createAndShowGUI();
		frac.drawFractal();
	}
}
