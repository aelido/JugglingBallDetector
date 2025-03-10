package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class ColorMask implements PixelFilter {
    private double th=0.05;
    private double threshold = 0.1;

    @Override
    public DImage processImage(DImage img) {
        short[][] rr = img.getRedChannel();
        short[][] gg = img.getGreenChannel();
        short[][] bb = img.getBlueChannel();

        float[] hsv = new float[3];

        for (int r = 0; r < rr.length; r++) {
            for (int c = 0; c < rr[r].length; c++) {

                Color.RGBtoHSB(rr[r][c],gg[r][c],bb[r][c],hsv);
//                boolean hCheck = Math.abs(th-hsv[0])<=threshold || Math.abs(th-hsv[0]-360)<=threshold || Math.abs(th-hsv[0]+360) <= threshold;
                boolean sCheck = hsv[1]>=0.5;
//                boolean vCheck = 1-Math.abs(hsv[2]-10)<=0.7;
                short val = (short)((sCheck)?255:0);
                if (r==0 && c==0) System.out.println(hsv[2]);
                rr[r][c] = val;
                gg[r][c] = val;
                bb[r][c] = val;
            }
        }

        img.setColorChannels(rr,gg,bb);
        return img;
    }

    private double dist(short ro, short go, short bo, short rt, short gt, short bt) {
        return Math.sqrt((ro-rt)*(ro-rt)+(go-gt)*(go-gt)+(bo-bt)*(bo-bt));
    }
}

