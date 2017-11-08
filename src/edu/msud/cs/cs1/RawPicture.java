package edu.msud.cs.cs1;
import edu.princeton.cs.introcs.Picture;

import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;


public class RawPicture{

    //instance variables
    int width;    //width of the image
    int height;   //height of the image
    Color[] pixels;
    Picture image;
    String fromFile, toFile;


    //constructors
    public RawPicture(){
        width = 0;
        height = 0;   //height of the image
        pixels = null;
        toFile = "picture.RAW";
        fromFile = null;

    }
    public RawPicture(Picture picture){
        this.image = picture;
        height = image.height();
        width = image.width();
        ArrayList<Color> tempPix = new ArrayList<>(); //to construct array
        for(int i =0; i < image.width(); i ++)
        {
            for(int j = 0; j < image.height(); j++)
            {
                tempPix.add(image.get(i,j));

            }
        }
        pixels = new Color[tempPix.size()];
        int i = 0;
        for (Color pix: tempPix) pixels[i ++] = pix;
        fromFile = null;


    }
    public RawPicture(String file)
    {
        fromFile = file;
        image = new Picture(file);
        if(fromFile.endsWith(".GIF")||fromFile.endsWith(".JPG")||
                fromFile.endsWith(".PNG"))
        {
            BufferedImage pic = null;
            try {
                pic = ImageIO.read(new File(fromFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<Color> tempPix = new ArrayList<>(); //to construct array
            for(int i =0; i < pic.getWidth(); i ++)
            {
                for(int j = 0; j < pic.getHeight(); j++)
                {
                    tempPix.add(new Color(pic.getRGB(i,j)));

                }
            }
            pixels = new Color[tempPix.size()];
            int i = 0;
            for (Color pix: tempPix) pixels[i ++] = pix;
        }

        else if (fromFile.endsWith(".RAW")){
            read(file);


        }
    }
    //methods

    //read raw image
    public void read( String r) {
        if (r.endsWith(".RAW")){
            try {
                Scanner input = new Scanner(new File(r));
                this.height = input.nextInt();
                this.width = input.nextInt();
                System.out.println(height);
                System.out.println(width);
                for(int i = 0; i < this.pixels.length; i++){
                    System.out.printf("%d %d %d \n",input.nextInt(), input.nextInt(),input.nextInt());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }



    }

    //write raw image, no spaces (i.e. "pic.RAW")
//
    public void write( String w) {

        PrintWriter output = null;
        try {
            output = new PrintWriter("pic.RAW");
            output.println(this.height);
            output.println(this.width);
            for (Color c: this.pixels) {
                output.printf("%d %d %d \n", c.getRed(), c.getGreen(), c.getBlue());
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (output != null)
                output.close();
        }


        }

        public static void main (String[]args){
            RawPicture test = new RawPicture(new Picture(args[0]));
            try {
                test.write(args[0]);
                test.read("pic.RAW");
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }



        }

    }

