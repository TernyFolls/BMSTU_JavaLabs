import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.RenderedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FractalExplorer {

	private int side;
	private int strCount;

	private JButton b;
	private JButton b2;
	private JComboBox<FractalGenerator> combo;

	private JImageDisplay jpgImg;

//	private FractalGenerator geni = new Mandelbrot();
//	private FractalGenerator geni = new Tricorn();
	private FractalGenerator geni = new BurningShip();

	private Rectangle2D.Double vsblRange;

	public FractalExplorer(int x) {
		side = x;
		vsblRange = new Rectangle2D.Double();
		geni.getInitialRange(vsblRange);
		jpgImg = new JImageDisplay(x, x);

	}

	void enableUI(boolean val) {
		b.setEnabled(val);
		b2.setEnabled(val);
		combo.setEnabled(val);
	}

	class ActiveLisner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Ресет")) {
				jpgImg.clearImage();
				geni.getInitialRange(vsblRange);
				drawFractal();
			} else if (cmd.equals("comboBoxChanged")) {
				JComboBox<FractalGenerator> combo = (JComboBox<FractalGenerator>) e.getSource();
				geni = (FractalGenerator) combo.getSelectedItem();
				geni.getInitialRange(vsblRange);
				drawFractal();
			} else if (cmd.equals("Скрин")) {
				JFileChooser choozer = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
				choozer.setFileFilter(filter);
				choozer.setAcceptAllFileFilterUsed(false);
				int a = choozer.showSaveDialog(choozer.getParent());
				System.out.println(e);
				if (a == JFileChooser.APPROVE_OPTION) {
					String png = "png";
					try {
						ImageIO.write((RenderedImage) jpgImg.getImg(), png, choozer.getSelectedFile());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(choozer.getParent(), ex.getMessage(), "Ошибка сохранения",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	class MouseClass extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (strCount == 0) {
				System.out.println(e);
				double xCoord = FractalGenerator.getCoord(vsblRange.x, vsblRange.x + vsblRange.width, side, e.getX());
				double yCoord = FractalGenerator.getCoord(vsblRange.y, vsblRange.y + vsblRange.height, side, e.getY());
				geni.recenterAndZoomRange(vsblRange, xCoord, yCoord, 0.3);
				drawFractal();
			}
		}
	}

////
	private void drawFractal() {
		enableUI(false);
		strCount = side;
		for (int i = 0; i < side; i++) {
			new FractalWorker(i).execute();
		}

	}
////

	private void createAndShowGUI() {
		JFrame f = new JFrame("Лабораторная работа 6");
		combo = new JComboBox<FractalGenerator>();

		f.setLayout(new BorderLayout());
		f.add(jpgImg, BorderLayout.CENTER);
		b = new JButton("Ресет");
		JPanel p2 = new JPanel();
		b2 = new JButton("Скрин");
		f.add(b, BorderLayout.SOUTH);
		p2.add(b2);
		p2.add(b);
		f.add(p2, BorderLayout.SOUTH);

		b.addActionListener(new ActiveLisner());
		jpgImg.addMouseListener(new MouseClass());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		combo.addItem(new BurningShip());
		combo.addItem(new Tricorn());
		combo.addItem(new Mandelbrot());
		JPanel p = new JPanel();
		p.add(new JLabel("Фрактал "));
		p.add(combo);
		f.add(p, BorderLayout.NORTH);

		combo.addActionListener(new ActiveLisner());
		b2.addActionListener(new ActiveLisner());

		f.pack();
		f.setVisible(true);
		f.setResizable(false);
	}

	// Потоки
	private class FractalWorker extends SwingWorker<Object, Object> {

		int y;
		int[] yarray;

		public FractalWorker(int y) {
			this.y = y;
		}

		@Override
		protected Object doInBackground() throws Exception {
			yarray = new int[side];
			int a;
			double yCoord = FractalGenerator.getCoord(vsblRange.y, vsblRange.y + vsblRange.height, side, y);
			for (int i = 0; i < side; i++) {
//				for (int j = 0; j < side; j++) {
				double xCoord = FractalGenerator.getCoord(vsblRange.x, vsblRange.x + vsblRange.width, side, i);

				a = geni.numIterations(xCoord, yCoord);
				float hue = 0.7f + (float) a / 200f;
				int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
				if (a == -1) {
					yarray[i] = 0;
				} else {
					yarray[i] = rgbColor;
				}
//				}
			}
			return null;
		}

		@Override
		protected void done() {
			for (int i = 0; i < side; i++) {
				jpgImg.drawPixel(i, y, yarray[i]);
				jpgImg.repaint(0, 0, i, side, 1);
			}
			strCount--;
			if (strCount == 0)
				enableUI(true);
		}
	}

	public static void main(String[] args) {
		FractalExplorer frac = new FractalExplorer(800);
		frac.createAndShowGUI();
		frac.drawFractal();
	}
}
