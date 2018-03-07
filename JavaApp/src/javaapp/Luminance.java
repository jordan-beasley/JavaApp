package javaapp;

import java.awt.Color;

/******************************************************************************
 *  Compilation:  javac Luminance.java
 *  Execution:    java Luminance r1 g1 b1 r2 g2 b2
 *  https://introcs.cs.princeton.edu/java/31datatype/Luminance.java.html
 *
 *  Library for dealing with monochrome luminance. 
 *  Uses the NTSC formula Y = 0.299*r + 0.587*g + 0.114*b.
 *
 *  % java Luminance 0 0 0 0 0 255
 * 
 *
 ******************************************************************************/
public class Luminance 
{
    // return the monochrome luminance of given color
    @Deprecated
    public static double lum(Color color) {
        return intensity(color);
    }

    // return the monochrome luminance of given color
    public static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return 0.299*r + 0.587*g + 0.114*b;
    }

    // return a gray version of this Color
    public static Color toGray(Color color) {
        int y = (int) (Math.round(lum(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    // are the two colors compatible?
    public static boolean areCompatible(Color a, Color b) {
        return Math.abs(intensity(a) - intensity(b)) >= 128.0;
    }
}
