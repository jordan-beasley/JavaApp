
package javaapp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;
import javax.imageio.ImageIO;

/**
 *
 * @author caleb
 */
public class filter {
    
    BufferedImage img;
    int width;
    int height;
    double offsetRed = 100;
    double offsetGreen = 100; //offsets must be 0-100 values
    double offsetBlue = 100;
    
    public filter()
    {
        try{
            img = ImageIO.read(new File("C:\\Users\\caleb\\Pictures\\test.jpg"));
            width = img.getWidth();
            height = img.getHeight();
            
            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++)
                {
                    Color c = new Color(img.getRGB(i, j));
                    int r = c.getRed();
                    int b = c.getBlue();
                    int g = c.getGreen();
                    int a = c.getAlpha();
                    
                    r = (int)(r * offsetRed / 100);
                    g = (int)(g * offsetGreen / 100);
                    b = (int)(b * offsetBlue / 100);
                    Color newCol = new Color(r,g,b);
                    img.setRGB(i, j, newCol.getRGB());
                }
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public BufferedImage getImage()
    {
        return img;
    }
    

}



