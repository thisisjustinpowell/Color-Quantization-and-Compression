/**
 * Distance Metric interface for CS1501 Project 5
 * @author  Dr. Farnan
 * @author  Brian T. Nixon
 * @author  Marcelo d'Almeida
 */
package cs1501_p5;

interface DistanceMetric_Inter {
    /**
     * Computes the distance between the RGB values of two pixels. Different
     * implementations may use different formulas for calculating distance.
     *
     * @param p1 the first pixel
     * @param p2 the second pixel
     * @return The distance between the RGB values of p1 and p2
     */
    public double colorDistance(Pixel p1, Pixel p2);
}
