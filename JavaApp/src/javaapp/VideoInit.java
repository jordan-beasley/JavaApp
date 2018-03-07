
package javaapp;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.jcodec.common.model.Picture;

/**
 *
 * @author caleb
 */
public class VideoInit {
    //I am not storing the information, just manipulating and returning data
    
    //file is loaded, 
    public void greyScale(Picture frame){
        int width = frame.getWidth();
        int height = frame.getHeight();

        // convert to grayscale
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Color color = frame.get(col, row);
                Color gray = Luminance.toGray(color);
                frame.set(col, row, gray);
            }
        }
        frame.show();
        
    }
}
