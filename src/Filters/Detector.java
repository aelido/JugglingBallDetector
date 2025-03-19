package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class Detector implements PixelFilter {
    PixelFilter[] filterList = {
            new Blur(),
            new ColorMask()
    };
    @Override
    public DImage processImage(DImage img) {

        for (PixelFilter filter:  filterList) {
          img = filter.processImage(img);
        }
        return img;
    }
}

