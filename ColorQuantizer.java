package cs1501_p5;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.lang.Math;

public class ColorQuantizer implements ColorQuantizer_Inter{
    Pixel[][] image;
    ColorMapGenerator_Inter generator;
    final int totalColorSpace=intPower(2,24);

    ColorQuantizer(Pixel[][] image, ColorMapGenerator_Inter generator){
        this.image=image;
        this.generator=generator;
    }

    ColorQuantizer(String fileLine, ColorMapGenerator_Inter generator){
        try{
            File file= new File(fileLine);
            BufferedImage rawFile= ImageIO.read(file);
            Util util= new Util();
            this.image=util.convertBitmapToPixelMatrix(rawFile);
            this.generator=generator;
        }
        catch(IOException e){
            this.image=null;
            this.generator=generator;
        }
        
    }

    public Pixel[][] quantizeTo2DArray(int numColors){
        Pixel[] colorPalette=generator.generateColorPalette(image,numColors);
        Map<Pixel,Pixel> colorMap= generator.generateColorMap(image,colorPalette);
        Pixel[][] newImage= new Pixel[image.length][image[0].length];
        for(int i=0;i<image.length;i++){
            for(int j=0;j<image[0].length;j++){
                Pixel currentPixel= image[i][j];
                Pixel newPixel=colorMap.get(currentPixel);
                newImage[i][j]=newPixel;
            }
        }
        return newImage;
    }

    //Tested
    public void quantizeToBMP(String fileName, int numColors){
        Pixel[] colorPalette=generator.generateColorPalette(image,numColors);
        Map<Pixel,Pixel> colorMap= generator.generateColorMap(image,colorPalette);
        Pixel[][] newImage= new Pixel[image.length][image[0].length];
        for(int i=0;i<image.length;i++){
            for(int j=0;j<image[0].length;j++){
                Pixel currentPixel= image[i][j];
                Pixel newPixel=colorMap.get(currentPixel);
                newImage[i][j]=newPixel;
            }
        }
        Util.savePixelMatrixToBitmap(fileName, newImage);      
    }

    //Helper Methods
    public int rgb(int red, int green, int blue){
        return (red << 16 | green << 8 | blue);
    }

    public int findBucket(int sizeOfBucket, int color){
        return color/sizeOfBucket;
    }

     public int intPower(int base, int exponet){
        int result=1;
        for(int i=1;i<=exponet;i++){
            result*=base;
        }
        return result;
    }

   

    public boolean constructorIsEmpty(){
        return image==null;
    }
}
