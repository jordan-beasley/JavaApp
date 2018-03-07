package javaapp;

//import org.jcodec.audio...

import java.awt.Color;
import java.awt.image.BufferedImage;
import static javafx.scene.paint.Color.color;
import org.jcodec.common.model.Picture;


public class AudioTrack
{
    // use AudioSource to get audio source from file
    // use Audio class to manipulate audio track further
    
    // possibly use sourceDataLine to open as clip and read audio as bytes
    // ****FIND FFT LIBRARY FOR FURTHER MANIPULATION****
    
    public void GrayScaleTest(BufferedImage img)
    {
        int width  = img.getWidth();
        int height = img.getHeight();
        
        try
        {
            System.out.println("Applying Gray Scale");
            for (int col = 0; col < width; col++) 
            {
                for (int row = 0; row < height; row++) 
                {
                    Color color = new Color(img.getRGB(col, row));
                    Color gray = Luminance.toGray(color);
                    img.setRGB(col, row, gray.getRGB());
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

