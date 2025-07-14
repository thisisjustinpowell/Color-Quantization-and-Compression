package cs1501_p5;
import java.util.Map;
import java.util.HashMap;
import java.lang.Math;


public class BucketingMapGenerator implements ColorMapGenerator_Inter{
    HashMap<Pixel, Pixel> colorMap;
    final int totalColors=intPower(2,24); //total color space
    //Tested
    public Pixel[] generateColorPalette(Pixel[][] pixelArray, int numColors){
        Pixel[] returnArray= new Pixel[numColors]; //inital pallette array
        double sizeOfBuckets=(double) totalColors/ numColors; // the size of a bucket in the pallette
        for(int i=0;i<numColors;i++){
            double bucketStart= i* sizeOfBuckets;
            double bucketMiddle= bucketStart+(sizeOfBuckets/2.0);
            int middleOfBucketColor= (int) bucketMiddle;

            int red= middleOfBucketColor >> 16 & 0xFF; //The red part of the color
            int green= middleOfBucketColor >> 8 & 0xFF; //The part componet of the color
            int blue= middleOfBucketColor & 0xFF; //The blue part of the color

            Pixel pixel= new Pixel(red, green, blue); // The pixel that represents the color of a particular bucket
            returnArray[i]=pixel;
        }
        return returnArray;
    }

    //Tested
    public Map<Pixel, Pixel> generateColorMap(Pixel[][] pixelArray, Pixel[] initialColorPalette){
        HashMap<Pixel,Pixel> colorMap=new HashMap<Pixel,Pixel>(); // Returning colorMap
        int sizeOfBuckets=totalColors/initialColorPalette.length; //size of each bucket
        for(int i=0;i<pixelArray.length;i++){
            for(int j=0;j<pixelArray[0].length;j++){
                Pixel currentPixel= pixelArray[i][j];
                int red=currentPixel.getRed();
                int green=currentPixel.getGreen();
                int blue=currentPixel.getBlue();

                int color=rgb(red,green,blue);
                int index= findBucket(sizeOfBuckets,color);
                if(color>=totalColors || index>=initialColorPalette.length){ //Checking if color is equal to or greater than the total color space
                    index=initialColorPalette.length-1;
                }
                Pixel intialPalettePixel=initialColorPalette[index];
                colorMap.put(currentPixel,intialPalettePixel);
            }
        }
        return colorMap;
    }

    //Helper Methods
    //Finding rgb value of a combo of red, green and blue
    public int rgb(int red, int green, int blue){
        return (red << 16 | green << 8 | blue);
    }

    // Find the correct buclet a particular color should go in
    public int findBucket(int sizeOfBucket, int color){
        return color/sizeOfBucket;
    }
    
    public int getTotalColors(){
        return totalColors;
    }

    //finds the result of a certain base raised to a exponet
    public int intPower(int base, int exponet){
        int result=1;
        for(int i=1;i<=exponet;i++){
            result*=base;
        }
        return result;
    }

}
