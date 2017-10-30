package edu.msud.cs.cs1;
import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdStats;

import java.awt.*;

public class GrayFrequency extends Luminance{
    private final double[] freq;   // freq[i] = # occurences of value i
    private double max;            // max frequency of any value
    private int intervalSize;
    private int numIntervals; //x1
    private int highestFrequency; //y1

    // Create a new histogram.
    private GrayFrequency(int n, double max) {
        this.numIntervals = n;
        this.max = max;
        this.freq = new double[n];
        this.intervalSize = (int)((max)/numIntervals);


    }

    // Add one occurrence of the value i.
    private void addDataPoint(double num) {


        if (num < max) {
            int interval = calculateInterval(num);
            freq[interval]++;

            if (freq[interval] > highestFrequency) {
                highestFrequency = (int)freq[interval];
            }
        }

    }

    private int calculateInterval(double n){
        int returnVal;
        returnVal = (int)((n)/this.intervalSize);
        if (returnVal < 0){
            returnVal = 0;
        }

        if (returnVal >= numIntervals) {
            returnVal = numIntervals - 1;
        }
        return returnVal;
    }



    // draw (and scale) the histogram.
    private void draw() {
//        StdDraw.setYscale(0, highestFrequency +1);//to leave border
//        StdDraw.setXscale(0, highestFrequency +1);
        StdDraw.setScale(-highestFrequency, highestFrequency);
        StdStats.plotBars(freq);

    }


    // See Program 2.2.6.
    public static void main(String[] args) {
        StdDraw.setCanvasSize(500, 500);
        Picture picture = new Picture(args[0]); // use the Picture library to open the file and access individual pixels
        double[] grayscaleCounts = new double[256];
        for (int col = 0; col < picture.width(); col++)
        {
            for (int row = 0; row < picture.height(); row++)
            {
                Color color = picture.get(col, row); // count each pixel of the image
                Color gray = Luminance.toGray(color); // convert to grayscale (does nothing if already grayscale)
                grayscaleCounts[gray.getBlue()] ++; // count the occurrence of the found value (in grayscale, R=G=B=Y, where Y is the monochrome luminance)
            }
        }
        // use grayscaleCounts to create a histogram using StdDraw
        // create the histogram
        int n = grayscaleCounts.length;
        GrayFrequency histogram = new GrayFrequency(256, n);
        double d;
        for (int t = 0; t < grayscaleCounts.length; t++) {
            d = grayscaleCounts[t];
            System.out.println(d);
            histogram.addDataPoint(d);
        }

        // display using standard draw

        histogram.draw();
        StdDraw.filledCircle(0,0, 2);
    }
}