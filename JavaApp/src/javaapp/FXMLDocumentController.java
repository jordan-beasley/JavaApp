package javaapp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.awt.AWTFrameGrab;
import org.jcodec.common.model.Picture;


import javafx.embed.swing.SwingFXUtils;
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button bigbutton;
    @FXML
    private ImageView imgView;
    @FXML
    private Button nextFrame;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        testFunction();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    int frameNumber = 340;
    boolean isRunning = false;
    
    void testFunction()
    {
        
        try
        {
            //System.out.println("Grabbing frame");
            //BufferedImage bufferedImage = AWTFrameGrab.getFrame(new File("C:\\Users\\caleb\\Documents\\! Schoolwork\\Java\\project\\JavaApp\\TestResources\\Taylor Swift Goat Parody.mp4"), frameNumber);
            //Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            //imgView.setImage(image);
            //System.out.println("Pringing frame");
            
            Thread mt = new Thread(){
                public void run()
                {
                    try{
                        //System.out.println("Grabbing frame " + frameNumber);
                        File f = new File("C:\\Users\\caleb\\Documents\\! Schoolwork\\Java\\project\\JavaApp\\TestResources\\Taylor Swift Goat Parody.mp4");
                        BufferedImage bufferedImage = AWTFrameGrab.getFrame(f, frameNumber);
                        int currentFrame = 0;
                        
                        while(isRunning)
                        {
                            if(currentFrame != frameNumber)
                            {
                                System.out.println("Grabbing frame " + frameNumber);
                                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                                imgView.setImage(image);
                                System.out.println("Printing frame");
                                currentFrame = frameNumber;
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            
            if(!isRunning)
            {
                isRunning = true;
                mt.start();
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void something(ActionEvent event) {
        frameNumber++;
        while (frameNumber < 500){
            testFunction();
            frameNumber++;
        }
    }
    
}
