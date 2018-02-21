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
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        testFunction();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    void testFunction()
    {
        int frameNumber = 288;
        
        try
        {
            System.out.println("Grabbing frame");
            BufferedImage bufferedImage = AWTFrameGrab.getFrame(new File("%FILE PATH%"), frameNumber);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgView.setImage(image);
            System.out.println("Pringing frame");
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
