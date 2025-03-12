package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class Detector implements PixelFilter {
    private double hueDist = 0.1;
    private double[] targetHues = {0.9, 0.15, 0.3, 0.6};
    private double[] targetSats = {0.7, 0.7, 0.4, 0.4};
    private short[][] targetHSVtoRGB= {
            {255,0,0}, {255,100,0}, {0,255,0}, {0,0,255}
    };

    @Override
    public DImage processImage(DImage img) {
        PixelFilter[] filterList = {
                new Blur(),
                new ColorMask().setHueDist(hueDist).setTargetHues(targetHues).setTargetSats(targetSats).setTargetHSVtoRGB(targetHSVtoRGB),
                new BallPath().setTargetHSVtoRGB(targetHSVtoRGB)
        };
        for (PixelFilter filter : filterList) {
            img = filter.processImage(img);
        }
        return img;
    }
}

