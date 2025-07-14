
package cs1501_p5;
import java.lang.Math;
public class SquaredEuclideanMetric implements DistanceMetric_Inter{
    public double colorDistance(Pixel p1, Pixel p2){
        double p1Red=p1.getRed();
        double p1Green=p1.getGreen();
        double p1Blue=p1.getBlue();

        double p2Red=p2.getRed();
        double p2Green=p2.getGreen();
        double p2Blue=p2.getBlue();

        double redDiffernce= Math.pow(p1Red-p2Red,2);
        double greenDiffrence= Math.pow(p1Green-p2Green,2);
        double blueDifference= Math.pow(p1Blue-p2Blue,2);

        return redDiffernce+greenDiffrence+blueDifference;
    }
}
