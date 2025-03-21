package Filters;

public class Point {
    private int x, y;
    public Point(int y, int x) { set(y,x); }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int[] get() {
        return new int[]{y,x};
    }
    public void set(Point p) {
        set(p.getY(), p.getX());
    }
    public void set(int y, int x) {
        this.y=y; this.x=x;
    }

    public int dist(Point p) {
        int px = p.getX(), py = p.getY();
        return dist(px,py);
    }
    public int dist(int py, int px) {
        return (px-x)*(px-x)+(py-y)*(py-y);
    }

    @Override
    public String toString() {
        return "Point{" +  "x=" + x + ", y=" + y + '}';
    }
}
