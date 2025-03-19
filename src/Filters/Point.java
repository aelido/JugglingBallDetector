package Filters;

public class Point {
    private int x, y;
    public Point(int y, int x) { this.y=y; this.x=x;}
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int[] get() {
        return new int[]{y,x};
    }
    public float dist(Point p) {
        int px = p.getX(), py = p.getY();
        return (float)Math.sqrt((px-x)*(px-x)+(py-y)*(py-y));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
