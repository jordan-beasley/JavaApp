package javaapp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.effect.Blend;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FXMLDocumentController implements Initializable {
    
    
    Image image;
    @FXML
    private Canvas drawCanvas;
    
    @FXML
    private AnchorPane anchorPane;
    
    BlurTool blur;
    Tool currentTool;
    @FXML
    private Button btnBlack;
    @FXML
    private Button btnNo;
    @FXML
    private Button btnEraser;
    @FXML
    private AnchorPane controlPane;
    @FXML
    private Button btnText;
    
    EventHandler dragEvent;
    EventHandler clickEvent;
    
    //Dictionary elements = new Dictionary<>();
    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        // remove the current tool and set a new tool
        ClearTool();
        currentTool = new Pen(drawCanvas, controlPane);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // add a black box around the border of the main canvas
        /*GraphicsContext gc = drawCanvas.getGraphicsContext2D();
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
        */
    }
    
    
    @FXML
    private void RemovePen(ActionEvent event) 
    {
        ClearTool();
        PlaceShape();
    }

    @FXML
    private void UseEraser(ActionEvent event) 
    {
        ClearTool();
        currentTool = new Eraser(drawCanvas, controlPane);
    }
    
    private void ClearTool()
    {
        // remove the handlers for the current tool
        // and set it to null
        if(currentTool != null)
        {
            currentTool.RemoveHandlers();
            currentTool = null;
        }
        
        if(dragEvent != null)
        {
            anchorPane.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
        }
        
        if(clickEvent != null)
        {
            anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        }
    }
    
    private void PlaceShape()
    {
        // a click event for placing objects
        clickEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                // check if the object that was clicked wasn't 
                // the main canvas itself
                System.out.println(event.getTarget());
                if(event.getTarget().toString().toLowerCase().contains("canvas@"))
                {
                    //((Tool)event.getTarget()).SetWidth(((Tool)event.getTarget()).GetWidth() * 2);
                }
                else
                {
                    if(currentTool == null)
                    {
                        Random random = new Random();
                        double rand = random.nextFloat();
                        String shape = "square"; //(rand <= 0.33) ? "square" : (rand <= 0.66) ? "circle" : "triangle";
                        Tool newTool = new Shape(event.getX(), event.getY(), shape, controlPane);
                        currentTool = newTool;
                        anchorPane.getChildren().add(currentTool.GetCanvas());
                        System.out.println(event.getTarget());
                        anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                    }
                    
                }
                
                // remove this handler after the object has been placed
                // anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
        // a click event for dragging to clear click event
        dragEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                //currentTool = null;
                anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        //this.anchorPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
    }

    private void CustomShape(ActionEvent event) 
    {
        ClearTool();
        //currentTool = new CustomShape(drawCanvas, controlPane);
        //anchorPane.getChildren().add(currentTool.GetCanvas());
    }
    
    private void GetPixelColor()
    {
        // a click event for dragging to clear click event
        /*EventHandler moveEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            { 
                if(event.getTarget().toString().toLowerCase().contains("canvas@") ||
                        event.getTarget().toString().toLowerCase().contains("canvas["))
                {
                    // place a canvas over the current objects on the drawCanvas
                    // take a snapshot of that canvas to get a writable image, then remove it from the view
                    // use event x and y to get pixel values from the writable image of that canvas
                    Canvas temp = ((Canvas)event.getTarget());
                    WritableImage writableImage = new WritableImage((int)temp.getWidth(), (int)temp.getHeight());
                    ((Canvas)event.getTarget()).snapshot(null, writableImage);
                    PixelReader pr = writableImage.getPixelReader();
                    System.out.println(pr.getColor(0, 0));
                }
                
                System.out.println(event.getTarget().toString());
            }
        };
        
        EventHandler clickEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                
                // remove this handler after the object has been placed
                anchorPane.removeEventHandler(MouseEvent.MOUSE_MOVED, moveEvent);
                anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_MOVED, moveEvent);*/
    }

    @FXML
    private void AddText(ActionEvent event) 
    {
        clickEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                // check if the object that was clicked wasn't 
                // the main canvas itself
                /*if(event.getTarget().toString().toLowerCase().contains("canvas@"))
                {
                    //((Tool)event.getTarget()).SetWidth(((Tool)event.getTarget()).GetWidth() * 2);
                }
                else
                {
                    
                }*/
                
                if(currentTool != null)
                {
                    currentTool = null;
                }
                
                Tool newTool = new TextBox(event.getX(), event.getY(), controlPane);
                currentTool = newTool;
                anchorPane.getChildren().add(currentTool.GetCanvas());
                anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                
                // remove this handler after the object has been placed
                // anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
        // a click event for dragging to clear click event
        dragEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                currentTool = null;
                anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        //this.anchorPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
    }
}
