package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class BasicColorFilter implements PixelFilter {


    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        red[0][0]=0;
        green[0][0]=0;
        blue[0][0]=0;

        img.setColorChannels(red, green, blue);
        return img;
    }
}

