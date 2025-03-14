package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;
import java.util.Objects;

public class Blur implements PixelFilter {

    private int size;
    private double[][] kernel;
    private double kernelWeight=0;


    public Blur() {
        String j = JOptionPane.showInputDialog("Would you like to change Kernel? ");
        if(Objects.equals(j, "yes")){
            String r = JOptionPane.showInputDialog("Enter Size of Kernel: ");
            String l = JOptionPane.showInputDialog("Enter Kernel number: ");
            size = Integer.parseInt(r);
            int val = Integer.parseInt(l);
            kernelSettings(val);
        }else{
            kernel = new double[5][5];
        }

    }
    public void kernelSettings(int val){
        kernel = new double[size][size];
        kernelWeight=0;
        for (int y=0; y<kernel.length; y++) { for (int x=0; x<kernel[0].length; x++) kernel[y][x]=val;}
        for (int y=0; y<kernel.length; y++) { for (int x=0; x<kernel[0].length; x++) kernelWeight+=kernel[y][x]; }
        if (kernelWeight==0) kernelWeight=1;
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
        for (int r = 0; r < rr.length-kernel.length; r++) {
            for (int c = 0; c < rr[r].length-kernel[0].length; c++) {
                rc[r+(size/2)][c+(size/2)]=kernelValue(rr,kernel,r,c);
                gc[r+(size/2)][c+(size/2)]=kernelValue(gg,kernel,r,c);
                bc[r+(size/2)][c+(size/2)]=kernelValue(bb,kernel,r,c);
            }
        }

        img.setColorChannels(rc,gc,bc);
        return img;
    }

    private short kernelValue(short[][] img, double[][] kernel, int r, int c) {
        double val = 0;
        for (int y=0; y<kernel.length; y++) {
            for (int x=0; x<kernel[0].length; x++) {
                int row = Math.min(r+y,img.length-1);//Keeps in bounds!
                int col = Math.min(c+x,img.length-1);

                val += img[row][col] * kernel[y][x];
            }
        }
        return (short)(val/kernelWeight);
    }


}

