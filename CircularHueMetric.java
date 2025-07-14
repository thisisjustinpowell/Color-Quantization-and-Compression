
package cs1501_p5;
import java.lang.Math;
public class CircularHueMetric implements DistanceMetric_Inter{

    //Tested
    public double colorDistance(Pixel p1, Pixel p2){
        double color1=p1.getHue();
        double color2=p2.getHue();
        double difference= Math.abs(color1-color2);
        return Math.min(difference, 360-difference);
    }
}
