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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FractalExplorer {

	private int side;

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
	
	class ActiveLisner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Сброс")) {
				jpgImg.clearImage();
				geni.getInitialRange(vsblRange);
				drawFractal();
			}
			else if (cmd.equals("comboBoxChanged")) {
				JComboBox<FractalGenerator> combo = (JComboBox<FractalGenerator>)e.getSource();
				geni = (FractalGenerator)combo.getSelectedItem();
				geni.getInitialRange(vsblRange);
				drawFractal();
			}
			else if (cmd.equals("Скрин")) {
				JFileChooser choozer = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
				choozer.setFileFilter(filter);
				choozer.setAcceptAllFileFilterUsed(false);
				int a = choozer.showSaveDialog(choozer.getParent());
				System.out.println(e);
				if (a == JFileChooser.APPROVE_OPTION) {
					String png = "png";
					try {
						ImageIO.write((RenderedImage)jpgImg.getImg(), png, choozer.getSelectedFile());
					}
					catch (Exception ex) {
						JOptionPane.showMessageDialog(choozer.getParent(), ex.getMessage(), "Ошибка записи файла", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				
			}
		}
	}
	class MouseClass extends MouseAdapter{
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
		JFrame f = new JFrame("Лабораторная работа 5");
		JComboBox<FractalGenerator> combo = new JComboBox<FractalGenerator>();
		
		f.setLayout(new BorderLayout());
		f.add(jpgImg, BorderLayout.CENTER);
		JButton b = new JButton("Сброс");
		JPanel p2 = new JPanel();
		JButton b2 = new JButton("Скрин");
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

	public static void main(String[] args) {
		FractalExplorer frac = new FractalExplorer(800);
		frac.createAndShowGUI();
		frac.drawFractal();
	}
}
