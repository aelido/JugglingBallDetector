package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class ColorMask implements PixelFilter {
    private short rt=0, gt=200, bt=0;
    private double threshold = 150;

    @Override
    public DImage processImage(DImage img) {
        short[][] rr = img.getRedChannel();
        short[][] gg = img.getGreenChannel();
        short[][] bb = img.getBlueChannel();


        for (int r = 0; r < rr.length; r++) {
            for (int c = 0; c < rr[r].length; c++) {
                short val = (short)((dist(rr[r][c],gg[r][c],bb[r][c])>threshold)?0:255);
                rr[r][c] = val;
                gg[r][c] = val;
                bb[r][c] = val;
            }
        }

        img.setColorChannels(rr,gg,bb);
        return img;
    }

    private double dist(short ro, short go, short bo) {
        return Math.sqrt((ro-rt)*(ro-rt)+(go-gt)*(go-gt)+(bo-bt)*(bo-bt));
    }
}

