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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Blend;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
<<<<<<< HEAD
import javafx.stage.FileChooser;
=======
>>>>>>> origin/master
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    
    Image image;
    private Canvas drawCanvas;
    
    @FXML
    private AnchorPane anchorPane;
    
    BlurTool blur;
    Tool currentTool;
    private AnchorPane controlPane;

    EventHandler dragEvent;
    EventHandler clickEvent;
    @FXML
    private Button btnAny;
    @FXML
    private AnchorPane colorPickerPane;
    @FXML
    private AnchorPane toolControlPane;
    @FXML
    private SplitPane MainSplitPane;
    
    
    private void handleButtonAction(ActionEvent event) 
    {
        // remove the current tool and set a new tool
        ClearTool();
        currentTool = new Pen(drawCanvas, controlPane);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //filter f = new filter();
        //image = SwingFXUtils.toFXImage(f.getImage(), null);
        //imgView.setImage(image);
        //GraphicsContext gc = drawCanvas.getGraphicsContext2D();
        // add a black box around the border of the main canvas
        /*GraphicsContext gc = drawCanvas.getGraphicsContext2D();
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
        */
        
        anchorPane.widthProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, 
                    Number old_val, Number new_val)
            {
                // make the canvas width twice as large as the parent
                // to perserve some elements 'off screen'
                drawCanvas.setWidth((double)new_val * 2);
            }
        });
        
        anchorPane.heightProperty().addListener(new ChangeListener<Number>()
        {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ColorPickerUI.fxml"));
            Pane controls = loader.load();
            //PenToolUIController pCon = loader.getController();

            //ColorPicker colorPicker = new ColorPicker();
            colorPickerPane.getChildren().setAll(controls);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
    }
<<<<<<< HEAD

=======
            @Override
            public void changed(ObservableValue<? extends Number> ov, 
                    Number old_val, Number new_val) 
            {
                // make the canvas height twice as large as the parent
                // to perserve some elements 'off screen'
                drawCanvas.setHeight((double)new_val * 2);
            }
        });
        
    }
    
    
    @FXML
>>>>>>> origin/master
    private void RemovePen(ActionEvent event) 
    {
        ClearTool();
        PlaceShape();
    }

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
    }
    
    private void PlaceShape()
    {
        Stage stage = (Stage)anchorPane.getScene().getWindow();
        Tool newTool = new FilteredImage(anchorPane, stage, controlPane);
        currentTool = newTool;
        
        //anchorPane.getChildren().add(currentTool.GetCanvas());
        
        // a click event for placing objects
        clickEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                // check if the object that was clicked wasn't 
                // the main canvas itself
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
<<<<<<< HEAD
                        Tool newTool = new FilteredImage(anchorPane.getWidth() / 2, anchorPane.getHeight() / 2, anchorPane, controlPane); //new Shape(event.getX(), event.getY(), shape, controlPane);
                        currentTool = newTool;
                        anchorPane.getChildren().add(currentTool.GetCanvas());
=======
>>>>>>> origin/master
                        System.out.println(event.getTarget());
                        anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                    }
                    
                }
                
            }
        };
        
        //this.anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
    }

    private void CustomShape(ActionEvent event) 
    {
        ClearTool();
        //currentTool = new CustomShape(drawCanvas, controlPane);
        //anchorPane.getChildren().add(currentTool.GetCanvas());
    }
    
    private void GetPixelColor()
    {
        // take a snap shot of the entire canvas
        // use scene/event (x,y) to get row and height
        // get pixel value from writable image of canvas
        Stage stage = (Stage)anchorPane.getScene().getWindow();
        currentTool =  new Dropper(anchorPane, controlPane, stage);
    }

    @FXML
    private void AddText(ActionEvent event) 
    {
        clickEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {   
                if(currentTool != null)
                {
                    ClearTool();
                }
                
                Tool newTool = new TextBox(event.getX(), event.getY(), controlPane);
                currentTool = newTool;
                anchorPane.getChildren().add(currentTool.GetCanvas());
                anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                
                // remove this handler after the object has been placed
                // anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
        
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
    }

    @FXML
    private void TestButton(ActionEvent event) 
    {
        clickEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(currentTool != null)
                {
                    ClearTool();
                }
                
                //Tool newTool = new SelectionTool(event.getSceneX(), event.getSceneY(), anchorPane, controlPane);
                //currentTool = newTool;
                //anchorPane.getChildren().add(currentTool.GetCanvas());
                anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                
                // remove this handler after the object has been placed
                // anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage stage = (Stage)anchorPane.getScene().getWindow();
        fileChooser.showOpenDialog(stage);
        
        Tool newTool = new FilteredImage(anchorPane.getWidth() / 2, anchorPane.getHeight() / 2, anchorPane, controlPane); //new Shape(event.getX(), event.getY(), shape, controlPane);
        //currentTool = newTool;
        
        //this.anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        //this.anchorPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
    }

    @FXML
    private void tempButtonClick(ActionEvent event) 
    {
        EventHandler clickEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                // check if the object that was clicked wasn't 
                // the main canvas itself
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
                        Tool newTool = new Shape(event.getX(), event.getY(), shape, toolControlPane);
                        currentTool = newTool;
                        anchorPane.getChildren().add(currentTool.GetCanvas());
                        anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                    }
                    
                }
                
                // remove this handler after the object has been placed
                // anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
        // a click event for dragging to clear click event
        EventHandler dragEvent = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                //currentTool = null;
                anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
<<<<<<< HEAD
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        //this.anchorPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
=======
        //this.anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
>>>>>>> origin/master
    }

}
