package javaapp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Random;
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
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.effect.Blend;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ImageView imgView;
    
    Image image;
    @FXML
    private Canvas drawCanvas;
    @FXML
    private Slider slider;
    
    @FXML
    private AnchorPane anchorPane;
    
    BlurTool blur;
    Tool currentTool;
    @FXML
    private Button btnBlack;
    @FXML
    private Button btnRed;
    @FXML
    private Button btnNo;
    @FXML
    private Button btnEraser;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        ClearTool();
        currentTool = new Pen(drawCanvas);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        GraphicsContext gc = drawCanvas.getGraphicsContext2D();
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
    }
    
    int frameNumber = 340;
    boolean isRunning = false;
    
    void testFunction()
    {
        
        try
        {
            System.out.println("Loading Image");
            File file = new File("C:\\Users\\Beasley\\Documents\\cscade saves\\spring 18\\java application development\\project\\project\\JavaApp\\TestResources\\howl.jpg");
            //image = new Image(file.toURI().toString());
            //imgView.setImage(image);
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void something(ActionEvent event) 
    {
        ClearTool();
        currentTool = new Pen(drawCanvas);
        currentTool.SetOutlineColor(Color.RED);
    }

    @FXML
    private void ChangeRadious(MouseEvent event) 
    {
        
    }

    @FXML
    private void RemovePen(ActionEvent event) 
    {
        ClearTool();
        TestFunction();
    }

    @FXML
    private void UseEraser(ActionEvent event) 
    {
        ClearTool();
        currentTool = new Eraser(drawCanvas);
    }
    
    private void ClearTool()
    {
        if(currentTool != null)
        {
            currentTool.RemoveHandlers();
            currentTool = null;
        }
    }
    
    private void TestFunction()
    {
        EventHandler clickEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(event.getTarget().toString().toLowerCase().contains("canvas@"))
                {
                    System.out.println("clicked shape");
                    System.out.println();
                    currentTool = null;
                    anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                    
                }
                else
                {
                    System.out.println("Adding shape");
                    Random random = new Random();
                    double rand = random.nextFloat();
                    String shape = (rand > 0.5) ? "square" : "circle";
                    currentTool = new Shape(event.getX(), event.getY(), "triangle");
                    
                    if(currentTool != null)
                        anchorPane.getChildren().add(currentTool.GetCanvas());
                }
            }
        };
        
        EventHandler dragEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                currentTool = null;
                anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
    }
}
