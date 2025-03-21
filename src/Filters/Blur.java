package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;
import java.util.Objects;

public class Blur implements PixelFilter {

    private int size;
    private double[][] kernel;
    private double kernelWeight = 0;

    public Blur() {
        ask();
    }

    public void ask() {
        String l = JOptionPane.showInputDialog("Would you like to change Kernel Settings? (Do not input size larger than 6)");
        if (l.equals("yes")) {
            kernelSettings();
        } else {
            AutoKernel();
        }
        //ask if kernel size wants to be changed for the blur effect.
    }

    public void kernelSettings() {
        // System.out.println("This has ran!");
        String r = JOptionPane.showInputDialog("Enter Size of Kernel: ");
        size = Integer.parseInt(r);
        kernel = new double[size][size];
        //Get size and make kernel according to the size.
        String l = JOptionPane.showInputDialog("Enter Kernel number: ");
        int val = Integer.parseInt(l);
        //Get the number inside the kernel
        kernelWeight = 0;
        for (int y = 0; y < kernel.length; y++) {
            for (int x = 0; x < kernel[0].length; x++) kernel[y][x] = val;
            //Each Kernel value is set to val given above
        }
        for (double[] doubles : kernel) {
            for (int x = 0; x < kernel[0].length; x++) kernelWeight += doubles[x];
            //kernelWeight adjusted
        }
        if (kernelWeight == 0) kernelWeight = 1;
        //clipper
    }

    public void AutoKernel() {
        kernel = new double[5][5];
        kernelWeight = 0;
        for (int y = 0; y < kernel.length; y++) {
            for (int x = 0; x < kernel[0].length; x++) kernel[y][x] = 1;
        }
        for (double[] doubles : kernel) {
            for (int x = 0; x < kernel[0].length; x++) kernelWeight += doubles[x];
        }
        if (kernelWeight == 0) kernelWeight = 1;
        //if ask method hits else case, run this method for a default kernel and value.(preferable size: 3 - 5)
    }

    @Override
    public DImage processImage(DImage img) {

        short[][] rr = img.getRedChannel();
        short[][] gg = img.getGreenChannel();
        short[][] bb = img.getBlueChannel();


        short[][] rc = rr.clone();
        short[][] gc = gg.clone();
        short[][] bc = bb.clone();
        //
        for (int r = 0; r < rr.length - kernel.length; r++) {
            for (int c = 0; c < rr[r].length - kernel[0].length; c++) {
                rc[r + (size / 2)][c + (size / 2)] = kernelValue(rr, kernel, r, c);
                gc[r + (size / 2)][c + (size / 2)] = kernelValue(gg, kernel, r, c);
                bc[r + (size / 2)][c + (size / 2)] = kernelValue(bb, kernel, r, c);
            }
        }
        //runs our preset or custom kernel above
        img.setColorChannels(rc, gc, bc);
        return img;
    }

    private short kernelValue(short[][] img, double[][] kernel, int r, int c) {
        double val = 0;
        for (int y = 0; y < kernel.length; y++) {
            for (int x = 0; x < kernel[0].length; x++) {
                val += img[r + y][c + x] * kernel[y][x];
            }
        }
        return (short) (val / kernelWeight);
    }
}

