/**
 * “очка в двумерном пространстве.
 **/
public class Point2d {

    /**  оордината X точки **/
    private double xCoord;

    /** координата Y точки **/
    private double yCoord;

    /**  онструктор инициализирующий начальное значение координат точки (x, y). **/
    public Point2d(double x, double y) {
        xCoord = x;
        yCoord = y;
    }
 
    /** конструктор без аргументов:  по умолчанию точка ставитс€ в начало координат. **/
    public Point2d() {
       // ¬ызываем конструктор с двум€ аргументами дл€ инициализации позиции.
        this(0, 0);
    }

    /** ¬озвращает координату X точки. **/
    public double getX() {
        return xCoord;
    }

    /** ¬озвращает координату Y точки. **/
    public double getY() {
        return yCoord;
    }

    /** »змен€ет координату X точки. **/
    public void setX(double val) {
        xCoord = val;
    }

    /** »змен€ет координату Y точки. **/
    public void setY(double val) {
        yCoord = val;
    }
}