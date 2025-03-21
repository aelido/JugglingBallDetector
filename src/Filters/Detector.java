package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class Detector implements PixelFilter {
    PixelFilter[] filterList = {
            //run these outside the processImage method because DImage runs multiple times breaking the blur code.
            new Blur(),
            new ColorMask()
    };
    @Override
    public DImage processImage(DImage img) {
        for (PixelFilter filter:  filterList) {
            //apply the blur effect
          img = filter.processImage(img);
        }
        return img;
    }
}

