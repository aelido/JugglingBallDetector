package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class ObjectTracker implements PixelFilter {
    private int Threshold;
    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        Threshold = 140;
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[r].length; c++) {
                if(blue[r][c] <= Threshold){
                    red[r][c]=0;
                    green[r][c]=0;
                    blue[r][c]=0;
                } else {
                    red[r][c]=0;
                    green[r][c]=0;
                    blue[r][c]=0;
                }
            }
        }
        img.setColorChannels(red,green,blue);
        return img;
    }

    public static double calcDist(int r, int g, int b){
        return Math.sqrt((r*r)+(g*g)+(b*b));
    }

}
