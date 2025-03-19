package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class BallPath implements PixelFilter {
    private short[][] targetHSVtoRGB;

//    private int[][] coords;


    public BallPath setTargetHSVtoRGB(short[][] targetHSVtoRGB) {
        this.targetHSVtoRGB = targetHSVtoRGB;
//        coords = new int[targetHSVtoRGB.length][2];
        return this;
    }

    @Override
    public DImage processImage(DImage img) {
        reset = true;
        short[][] rr = img.getRedChannel();
        short[][] gg = img.getGreenChannel();
        short[][] bb = img.getBlueChannel();

        short[][] rc = rr.clone();
        short[][] gc = gg.clone();
        short[][] bc = bb.clone();

        ArrayList<Point> coordinates;
        for (short[] rgb : targetHSVtoRGB) {
            coordinates = circleLocations(rr,gg,bb,rgb[0],rgb[1],rgb[2]);
            for (Point coords : coordinates) {
                int y = coords.getY(), x = coords.getX();
                if (x<0 || y<0 || y>= rr.length || x>=rr[0].length) continue;
//                System.out.println(y + ", " + x);
                drawDot(rc,gc,bc,y,x,3);
//            drawLine(rc,gc,bc,y,x,0,0);
            }
        }

        img.setColorChannels(rr,gg,bb);
        return img;
    }

    boolean reset=false;
//    int[] circleLocation(short[][] ra, short[][] ga, short[][] ba, short r, short g, short b) {
//        long xAv = -1, yAv = -1;
//        long count = 0;
//        for (int y=0; y< ra.length; y+=2) {
//            for (int x=0; x< ra[0].length; x+=2) {
//                if (sameColor(ra[y][x],ga[y][x],ba[y][x],r,g,b)) {
//                    count++;
//                    xAv+=x; yAv+=y;
//                }
//            }
//        }
//        if (count==0) return new int[]{-1,-1};
//        return new int[]{(int)(yAv/count), (int)(xAv/count)};
//    }
    ArrayList<Point> circleLocations(short[][] ra, short[][] ga, short[][] ba, short r, short g, short b) {
        ArrayList<Point> out = new ArrayList<>();
        ArrayList<Point> points = getPoints(ra,ga,ba,r,g,b);
        long sumY=0, sumX=0;
        int thresholdArea = 20;
        int count=0;
        while (!points.isEmpty()) {
            Point curP = null;
            if (count==0) {
                curP = points.removeFirst();
                sumY=curP.getY();
                sumX=curP.getX();
                count++;
                continue;
            }

            int threshDist = (int)(count*1.75); // a little over half-PI
            int avY = (int)(sumY/count), avX =(int)(sumX/count);
            int shortest = 10000;
            for (Point p : points) {
                int dist = p.dist(avY,avX);
                if (dist<shortest) {
                    shortest=dist;
                }
                if (dist <= threshDist) {
                    curP=p; break;
                }
            }
//            System.out.println(threshDist);

            if (curP!=null) {
                sumY+=curP.getY();
                sumX+=curP.getX();
                points.remove(curP);
                count++;
                if (!points.isEmpty()) continue;
            }
            if (count>thresholdArea) {
                out.add(new Point(avY,avX));
            }
            count=0;
            sumY=0;
            sumX=0;
        }

        return out;
    }
    ArrayList<Point> getPoints(short[][] ra, short[][] ga, short[][] ba, short r, short g, short b) {
        ArrayList<Point> out = new ArrayList<>();
        for (int y=0; y<ra.length; y++) {
            for (int x=0; x<ra[0].length; x++) {
                if (ra[y][x]!=r) continue;
                if (ga[y][x]!=g) continue;
                if (ba[y][x]!=b) continue;
                out.add(new Point(y,x));
            }
        }
        return out;
    }


    void drawDot(short[][]ra, short[][]ga, short[][]ba, int y, int x, int dotSize) {
        for (int r=Math.max(0,y-dotSize); r<=y+dotSize && r< ra.length; r++) {
            for (int c=Math.max(0,x-dotSize); c<=x+dotSize && c< ra[0].length; c++) {
                ra[r][c] = 255;
                ga[r][c] = 255;
                ba[r][c] = 255;
            }
        }
    }
    void drawLine(short[][]ra, short[][]ga, short[][]ba, int y1, int x1, int y2, int x2) {
        double xDif = x2-x1, yDif=y2-y1;
        if (xDif > yDif) {
            xDif/=yDif; yDif=1;
        } else {
            yDif/=yDif; xDif=1;
        }
        double initXSign = Math.signum(xDif), initYSign = Math.signum(yDif);
        for (double r=y2; Math.signum(y2-y1)!=initYSign; r+=yDif) {
            for (double c=x2; Math.signum(x2-x1)!=initXSign; c+=xDif) {
                ra[(int)r][(int)c] = 255;
                ga[(int)r][(int)c] = 255;
                ba[(int)r][(int)c] = 255;
            }
        }
    }
    boolean sameColor(short r1, short g1, short b1, short r2, short g2, short b2) {
        return r1==r2 && g1==g2 && b1==b2;
    }
}

