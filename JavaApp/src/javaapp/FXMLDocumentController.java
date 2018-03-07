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
    
    
    File videoFile;
    
    @FXML
    private Button bigbutton;
    @FXML
    private ImageView imgView;
    @FXML
    private Button nextFrame;
    @FXML
    private ImageView imgViewGray;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        testFunction();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        videoFile = new File("C:\\Users\\Beasley\\Documents\\cscade saves\\spring 18\\java application development\\project\\project\\JavaApp\\TestResources\\TaylorSwiftGoatParody.mp4");
    }
    
    void testFunction()
    {
        int frameNumber = 340;
        
        try
        {
            System.out.println("Grabbing frame");
            BufferedImage bufferedImage = AWTFrameGrab.getFrame(videoFile, frameNumber);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgView.setImage(image);
            System.out.println("Pringing frame");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    void testGrayScale()
    {
        int frameNumber = 340;
        
        try
        {
            System.out.println("Grabbing frame");
            BufferedImage bufferedImage = AWTFrameGrab.getFrame(videoFile, frameNumber);
            AudioTrack at = new AudioTrack();
            at.GrayScaleTest(bufferedImage);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgViewGray.setImage(image);
            System.out.println("Pringing frame");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void something(ActionEvent event) {
        testGrayScale();
    }
    
}
