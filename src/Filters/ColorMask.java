package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class ColorMask implements PixelFilter, Interactive {
    private double thresholdHue = 0.1;
    private double[] targetHues = {0.9, 0.15, 0.3, 0.6};
    private double[] targetSats = {0.7, 0.7, 0.4, 0.4};
    private short[][] targetHueSets= {
            {255,0,0}, {255,100,0}, {0,255,0}, {0,0,255}
    };

    @Override
    public DImage processImage(DImage img) {
        short[][] rr = img.getRedChannel();
        short[][] gg = img.getGreenChannel();
        short[][] bb = img.getBlueChannel();

        float[] hsv = new float[3];
        for (int r = 0; r < rr.length; r++) {
            for (int c = 0; c < rr[r].length; c++) {

                Color.RGBtoHSB(rr[r][c],gg[r][c],bb[r][c],hsv);
                short[] rgb = new short[3];
                if (hsv[1]>=0.5) {
                    for (int i=0; i<targetHues.length; i++) {
                        double hue = targetHues[i];
                        double sat = targetSats[i];
                        if (checkDist(hsv[0],hue)) {
                            if (hsv[1] < sat) continue;
                            rgb = targetHueSets[i];
                            break;
                        }
                    }
                }
                rr[r][c] = rgb[0];
                gg[r][c] = rgb[1];
                bb[r][c] = rgb[2];
            }
        }

        img.setColorChannels(rr,gg,bb);
        return img;
    }



    private boolean checkDist(double curHue, double tarHue) {
        double dif1 = Math.abs(curHue-tarHue);
        double dif2 = Math.abs(curHue-tarHue+1);
        double dif3 = Math.abs(curHue-tarHue-1);
        double diff = Math.min(Math.min(dif1,dif2),dif3);
        return diff < thresholdHue;
    }

    @Override
    public void keyPressed(char key) {
        if(key == '='){thresholdHue += 5;}
        if(key == '-'){thresholdHue -= 5;}
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {
    }
}

