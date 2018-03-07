
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
    public void greyScale(BufferedImage frame){
        int width  = frame.getWidth();
        int height = frame.getHeight();
        
        try
        {
            System.out.println("Applying Gray Scale");
            for (int col = 0; col < width; col++) 
            {
                for (int row = 0; row < height; row++) 
                {
                    Color color = new Color(frame.getRGB(col, row));
                    Color gray = Luminance.toGray(color);
                    frame.setRGB(col, row, gray.getRGB());
                }
            }
            System.out.println("Gray Scale Complete");
        }
        catch(Exception e)
        {
            e.printStackTrace();
}
        
    }
}
