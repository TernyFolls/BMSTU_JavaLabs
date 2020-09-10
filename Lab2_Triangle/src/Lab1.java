import java.io.*;

public class Lab1 {
	public static void main(String[] args) throws Exception {

//		Point3d a1 = new Point3d(getDouble(), getDouble(), getDouble());
//
//		Point3d a2 = new Point3d(getDouble(), getDouble(), getDouble());
//
//		Point3d a3 = new Point3d(getDouble(), getDouble(), getDouble());

		Point3d a1 = new Point3d(1, 1, 0);

		Point3d a2 = new Point3d(-2, 4, 0);

		Point3d a3 = new Point3d(-2, -2, 0);

		if (a1.checkEquality(a2) || a2.checkEquality(a3) || a1.checkEquality(a3)) {
			throw new Exception("Одинаковые точки");
		}

		System.out.println("Площадь: \t" + (float)computeArea(a1, a2, a3));

	}

	public static double getDouble() {

		// There's potential for the input operation to "fail"; hard with a
		// keyboard, though!
		try {
			// Set up a reader tied to standard input.
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			// Read in a whole line of text.
			String s = br.readLine();

			// Conversion is more likely to fail, of course, if there's a typo.
			try {
				double d = Double.parseDouble(s);

				// Return the inputed double.
				return d;
			} catch (NumberFormatException e) {
				// Bail with a 0. (Simple solution for now.)
				return 0.0;
			}
		} catch (IOException e) {
			// This should never happen with the keyboard, but we'll
			// conform to the other exception case and return 0 here,
			// too.
			return 0.0;
		}
	}

	public static double computeArea(Point3d a1, Point3d a2, Point3d a3) {
		double p = ((a1.distanceTo(a2) + a2.distanceTo(a3) + a1.distanceTo(a3)));
		System.out.println("Периметр: \t" + p);
		p = p / 2;
		System.out.println("Полупериметр: \t" + p);
		return Math.sqrt(p * (p - a1.distanceTo(a2)) * (p - a2.distanceTo(a3)) * (p - a1.distanceTo(a3)));
	}
}

//private static float round(float number, int scale) {
//    int pow = 10;
//    for (int i = 1; i < scale; i++)
//        pow *= 10;
//    float tmp = number * pow;
//    return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
//}
