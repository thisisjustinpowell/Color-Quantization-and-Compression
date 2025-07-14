package cs1501_p5;

import java.lang.Math;

public class Pixel {
    private int red;
    private int green;
    private int blue;

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public String toString() {
        return "(" + this.red + "," + this.green + "," + this.blue + ")";
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    /**
     * Returns the hue of this pixel as a an integer from 0 (inclusive) to 360
     * (exclusive). Note that hue is circular, so 359 is most similar to 358 and
     * 0.
     *
     * @return the hue of this pixel
     */
    public int getHue() {
        // use floating types to force conversion for ratios later
        double min = Math.min(Math.min(red, green), blue);
        double max = Math.max(Math.max(red, green), blue);
        if (min == max) {
            return 0;
        }

        double hue = 0;
        if (max == red) hue = (green - blue) / (max - min);
        else if (max == green) hue = 2 + (blue - red) / (max - min);
        else hue = 4 + (red - green) / (max - min);

        hue *= 60;
        if (hue < 0) hue += 360;

        // Math.round gives a long, but we know we're <360 so int is fine
        return (int)Math.round(hue);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!(other instanceof Pixel))  return false;
        Pixel pOther = (Pixel) other;
        return this.red == pOther.red &&
                this.green == pOther.green &&
                this.blue == pOther.blue;
    }

    @Override
    public int hashCode() {
        return (100 * this.red) + (10 * this.green) + (this.blue);
    }
}
