package cs1501_p5;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.HashMap;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class ClusteringMapGenerator implements ColorMapGenerator_Inter{
    DistanceMetric_Inter metric;
    final int totalColorSpace=intPower(2,24);
    public ClusteringMapGenerator(DistanceMetric_Inter metric){
        this.metric= metric;
    }

    //Tested
    public Pixel[] generateColorPalette(Pixel[][] pixelArray, int numColors){
        Pixel[] returnArray=new Pixel[numColors];
        returnArray[0]=pixelArray[0][0];
        for(int i=1;i<numColors;i++){
            Pixel newPixel=new Pixel(0,0,0);
            double maxDistance=0.0;
            for(int j=0;j<pixelArray.length;j++){
                for(int k=0;k<pixelArray[0].length;k++){
                    Pixel currentPixel=pixelArray[j][k];
                    Pixel closetCentroid=findClosetCentroid(returnArray,i,currentPixel);
                    double distance=metric.colorDistance(currentPixel,closetCentroid);
                    if(distance>maxDistance){
                        maxDistance=distance;
                        newPixel=currentPixel;
                        continue;
                    }
                    int currentPixelRed=currentPixel.getRed();
                    int currentPixelGreen=currentPixel.getGreen();
                    int currentPixelBlue=currentPixel.getBlue();
                    int currentPixelRGB=rgb(currentPixelRed,currentPixelGreen,currentPixelBlue);

                    int previousPixelRed=newPixel.getRed();
                    int previousPixelGreen=newPixel.getGreen();
                    int previousPixelBlue=newPixel.getBlue();
                    int previousPixelRGB=rgb(previousPixelRed,previousPixelGreen,previousPixelBlue);
                    
                    if(distance==maxDistance && currentPixelRGB>previousPixelRGB){
                        newPixel=currentPixel;
                    }
                }
            }
            returnArray[i]=newPixel;
        }
        return returnArray;
    }

    public Map<Pixel, Pixel> generateColorMap(Pixel[][] pixelArray, Pixel[] initialColorPalette){
        initialColorPalette=kMeans(initialColorPalette,pixelArray);
        HashMap<Pixel,Pixel> colorMap=new HashMap<Pixel,Pixel>();
        int sizeOfBuckets=totalColorSpace/initialColorPalette.length;
        for(int i=0;i<pixelArray.length;i++){
            for(int j=0;j<pixelArray[0].length;j++){
                Pixel currentPixel= pixelArray[i][j];

                int index=findClosetCentroidIndex(initialColorPalette,currentPixel);
                if(index>initialColorPalette.length-1){
                    index=initialColorPalette.length-1;
                }
                Pixel intialPalettePixel=initialColorPalette[index];
                colorMap.put(currentPixel,intialPalettePixel);
            }
        }
        return colorMap;
    }

    //Helper Methods
    public Pixel findClosetCentroid(Pixel[] pixelArray, int i, Pixel pixel){
        double minDistance=Double.MAX_VALUE;
        Pixel closetCentroid=pixelArray[0];
        for(int j=0;j<i;j++){
            double distance=metric.colorDistance(pixel,pixelArray[j]);
            if(distance<minDistance){
                minDistance=distance;
                closetCentroid=pixelArray[j];
            }
        }
        return closetCentroid;
    }
    public int findClosetCentroidIndex(Pixel[] pixelArray, Pixel pixel){
        double minDistance=Double.MAX_VALUE;
        int closetCentroidIndex=0;
        for(int j=0;j<pixelArray.length;j++){
            double distance=metric.colorDistance(pixel,pixelArray[j]);
            if(distance<minDistance){
                minDistance=distance;
                closetCentroidIndex=j;
            }
        }
        return closetCentroidIndex;
    }
    public int rgb(int red, int green, int blue){
        return (red << 16 | green << 8 | blue);
    }

    public int intPower(int base, int exponet){
        int result=1;
        for(int i=1;i<=exponet;i++){
            result*=base;
        }
        return result;
    }

    public int findBucket(int sizeOfBucket, int color){
        return color/sizeOfBucket;
    }

    public Pixel findMean(Pixel[] pixels){
        int redSum=0;
        int greenSum=0;
        int blueSum=0;
        for(Pixel p:pixels){
            redSum+=p.getRed();
            greenSum+=p.getGreen();
            blueSum+=p.getBlue();
        }
        int count=pixels.length;
        int avgRed=redSum/count;
        int avgGreen=greenSum/count;
        int avgBlue=blueSum/count;
        return new Pixel(avgRed,avgGreen,avgBlue);
    }

    //Naive k means implementation
    public Pixel[] kMeans(Pixel[] initialColorPalette, Pixel[][] image){
       Pixel[] centroids= initialColorPalette; //Setting the intial centriods the to the initalColorPalette
       int length=initialColorPalette.length;
       boolean covergence=false;
       while(!covergence){
        ArrayList<Pixel>[] clusters= new ArrayList[length];
            //Clearing clusters
            for(int l=0;l<length;l++){
                clusters[l]= new ArrayList<Pixel>();
            }
            //Adding pixels to centroids
            for(int i=0;i<image.length;i++){
                for(int j=0;j<image[0].length;j++){
                    Pixel currentPixel=image[i][j];
                    int index=findClosetCentroidIndex(centroids,currentPixel);
                    clusters[index].add(currentPixel);
                }
            }
            Pixel[] newCentroids=new Pixel[length]; //Creating array for new centroids
            covergence=true;
            for(int k=0;k<length;k++){
                if(!clusters[k].isEmpty()){
                    Pixel[] clusterArray=clusters[k].toArray(new Pixel[0]);
                    newCentroids[k]=findMean(clusterArray); //Setting new centroid equal to the mean all points in cluster
                }
                else{
                    newCentroids[k]=centroids[k];
                }
            }
            if(!equalArrays(newCentroids,centroids)){
                covergence=false;
            }
            centroids=newCentroids;
       }
       return centroids;
    }

    //Method checks if two arrays are equal
    public boolean equalArrays(Pixel[] array1, Pixel[] array2){
        if(array1.length!=array2.length){
            return false;
        }
        for(int i=0; i<array1.length;i++){
            if(!array1[i].equals(array2[i])){
                return false;
            }
        }
        return true;
    }
}
