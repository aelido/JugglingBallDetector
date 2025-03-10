package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class Detector implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        PixelFilter[] filterList = {
                new Blur(),
                new ColorMask()
        };
        for (PixelFilter filter : filterList) {
            img = filter.processImage(img);
        }
        return img;
    }
}

