package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class BallPath implements PixelFilter {
    private short[][] targetHSVtoRGB;

    public BallPath setTargetHSVtoRGB(short[][] targetHSVtoRGB) {
        this.targetHSVtoRGB = targetHSVtoRGB;
        return this;
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] rr = img.getRedChannel();
        short[][] gg = img.getGreenChannel();
        short[][] bb = img.getBlueChannel();



        img.setColorChannels(rr,gg,bb);
        return img;
    }
}

