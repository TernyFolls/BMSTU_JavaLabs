
public class Point3d {

	private double PointX, PointY, PointZ;

	public Point3d(double x, double y, double z) {
		PointX = x;
		PointY = y;
		PointZ = z;
	}

	public Point3d() {
		this(0, 0, 0);
	}

	public double getPointX() {
		return PointX;
	}

	public void setPointX(double pointX) {
		PointX = pointX;
	}

	public double getPointY() {
		return PointY;
	}

	public void setPointY(double pointY) {
		PointY = pointY;
	}

	public double getPointZ() {
		return PointZ;
	}

	public void setPointZ(double pointZ) {
		PointZ = pointZ;
	}

	public boolean checkEquality(Point3d a) {
		return (a.getPointX() == PointX && a.getPointY() == PointY && a.getPointZ() == PointZ);
	}

	public double distanceTo(Point3d a) {
		double x = a.getPointX() - PointX, y = a.getPointY() - PointY, z = a.getPointZ() - PointZ;
		return (Math.sqrt(x * x + y * y + z * z));
	}

}
