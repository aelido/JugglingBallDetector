package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class ColorMask implements PixelFilter {
    private double hueDist;
    private double[] targetHues, targetSats;
    private short[][] targetHSVtoRGB;

    public ColorMask() {
    }

    public ColorMask setHueDist(double hueDist) {
        this.hueDist = hueDist;
        return this;
    }

    public ColorMask setTargetHSVtoRGB(short[][] targetHSVtoRGB) {
        this.targetHSVtoRGB = targetHSVtoRGB;
        return this;
    }

    public ColorMask setTargetHues(double[] targetHues) {
        this.targetHues = targetHues;
        return this;
    }

    public ColorMask setTargetSats(double[] targetSats) {
        this.targetSats = targetSats;
        return this;
    }

    @Override
    public DImage processImage(DImage img) {
        if (targetSats==null) return img;
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
                            rgb = targetHSVtoRGB[i];
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
        return diff < hueDist;
    }
}

