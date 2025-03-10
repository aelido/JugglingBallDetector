package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class Blur implements PixelFilter {
    private double th=0.1;
    private double threshold = 0.1;

    private double[][] kernel=new double[5][5];
    private double kernelWeight=0;

    public Blur() {
        for (int y=0; y<kernel.length; y++) { for (int x=0; x<kernel[0].length; x++) kernel[y][x]=1; }
        for (int y=0; y<kernel.length; y++) { for (int x=0; x<kernel[0].length; x++) kernelWeight+=kernel[y][x]; }
        if (kernelWeight==0) kernelWeight=1;
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] rr = img.getRedChannel();
        short[][] gg = img.getGreenChannel();
        short[][] bb = img.getBlueChannel();


        short[][] rc = rr.clone();
        short[][] gc = gg.clone();
        short[][] bc = bb.clone();
        for (int r = 0; r < rr.length-kernel.length; r++) {
            for (int c = 0; c < rr[r].length-kernel[0].length; c++) {
                rc[r+1][c+1]=kernelValue(rr,kernel,r,c);
                gc[r+1][c+1]=kernelValue(gg,kernel,r,c);
                bc[r+1][c+1]=kernelValue(bb,kernel,r,c);
            }
        }

        img.setColorChannels(rc,gc,bc);
        return img;
    }

    private short kernelValue(short[][] img, double[][] kernel, int r, int c) {
        double val = 0;
        for (int y=0; y<kernel.length; y++) {
            for (int x=0; x<kernel[0].length; x++) {
                val += img[r+y][c+x] * kernel[y][x];
            }
        }
        return (short)(val/kernelWeight);
    }
}

