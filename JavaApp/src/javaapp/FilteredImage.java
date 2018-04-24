/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp;

import java.io.File;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author caleb
 */
public class FilteredImage extends Tool 
{
    double padding = 20; // padding to add around shape for hover outline
    AnchorPane controlPane;
    BufferedImage bufferedImg;
    Image img;
    
    public FilteredImage(double x, double y, AnchorPane parent, AnchorPane controlPane)
    {
        this.controlPane = controlPane;
        
        this.height = 55;
        this.width = 55;
        this.canvas = new Canvas();
        this.x = x;
        this.y = y;
        this.shapeType = shapeType.toLowerCase();
        
        canvas.setWidth(width + padding);
        canvas.setHeight(height + padding);
        canvas.setLayoutX(x - (canvas.getWidth() / 2));
        canvas.setLayoutY(y - (canvas.getHeight() / 2));
        
        graphicsContext = canvas.getGraphicsContext2D();
        
        //LoadControls();
        //Update();
        //AddHandlers();
    }
    
    private void LoadControls()
    {
        Tool _this = this;
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try
                {
                    controlPane.getChildren().removeAll(controlPane.getChildren());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ShapeToolUI.fxml"));
                    Pane controls = loader.load();
                    ShapeToolUIController sCon = loader.getController();
                    sCon.SetShape(_this);
                    controls.localToParent(controlPane.getLayoutBounds());
                    AnchorPane.setBottomAnchor(controls, 0.0);
                    AnchorPane.setTopAnchor(controls, 0.0);
                    AnchorPane.setLeftAnchor(controls, 0.0);
                    AnchorPane.setRightAnchor(controls, 0.0);
                    controlPane.getChildren().setAll(controls);

                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        thread.run();
            
    }
    
    @Override
    public void Update()
    {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        try
        {
            bufferedImg = ImageIO.read(new File("C:\\Users\\caleb\\Pictures\\test.jpg"));
            img = SwingFXUtils.toFXImage(bufferedImg, null);
            canvas.setHeight(padding + img.getHeight());
            canvas.setWidth(padding + img.getWidth());
            //graphicsContext.setStroke(lineColor);
            //graphicsContext.strokeRect(padding / 2,  padding / 2, width, height);
            graphicsContext.drawImage(img, padding/2, padding/2, img.getWidth(), img.getHeight());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
    }
    
    private void AddHandlers()
    {
        
        moveEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                if(resizing)
                    return;
                
                canvas.setLayoutX(e.getSceneX() - (canvas.getWidth() / 2));
                canvas.setLayoutY(e.getSceneY() - (canvas.getHeight() / 2));
            }
        };
        
        resizeEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                /*EventHandler resizeWidth = new EventHandler<MouseEvent>(){
                    
                    @Override
                    public void handle(MouseEvent e)
                    {
                        resizing = true;
                    }
                };
                
                EventHandler resizeHeight = new EventHandler<MouseEvent>(){
                    
                    @Override
                    public void handle(MouseEvent e)
                    {
                        resizing = true;
                        System.out.println("new height " + (height + (canvas.getLayoutY() - e.getSceneY())));
                        graphicsContext.clearRect(0, 0, width, height);
                        height = (height + (canvas.getLayoutY() - e.getSceneY()));
                        Update();
                    }
                };
                
                EventHandler stopResize = new EventHandler<MouseEvent>(){
                    
                    @Override
                    public void handle(MouseEvent e)
                    {
                        resizing = false;
                    }
                };
                
                //canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
                //canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, stopResize);
                
                if((width - e.getX()) <= 5 || (width - e.getX()) >= (width - 10))
                {
                    canvas.getScene().setCursor(Cursor.W_RESIZE);
                    //canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeWidth);
                }else if((height - e.getY()) <= 5 || (height - e.getY()) >= (height - 10))
                {
                    canvas.getScene().setCursor(Cursor.N_RESIZE);
                    //canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeHeight);
                }else
                {
                    canvas.getScene().setCursor(Cursor.DEFAULT);
                    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, enterEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_MOVED, resizeEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_EXITED, exitEvent);
                }*/
            }
        };
        
        enterEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                graphicsContext.setLineWidth(1);
                graphicsContext.setStroke(boxBorderColor);
                graphicsContext.setLineDashes(new double[]{8});
                graphicsContext.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
                graphicsContext.setLineDashes(new double[]{0});
            }
        };
        
        exitEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                canvas.getScene().setCursor(Cursor.DEFAULT);
                Update();
            }
        };
        
        
        clickEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                //LoadControls();
                graphicsContext.setLineWidth(1);
                graphicsContext.setStroke(boxBorderColor);
                graphicsContext.setLineDashes(new double[]{8});
                graphicsContext.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
                graphicsContext.setLineDashes(new double[]{0});
            }
        };
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, enterEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, resizeEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_EXITED, exitEvent);
    }
    
    @Override
    public void RemoveHandlers() 
    {
        //canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
        //canvas.removeEventHandler(MouseEvent.MOUSE_ENTERED, enterEvent);
        //canvas.addEventHandler(MouseEvent.MOUSE_MOVED, resizeEvent);
        //canvas.removeEventHandler(MouseEvent.MOUSE_EXITED, exitEvent);
        //canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
    }

    @Override
    public void SetFillColor(String hex) 
    {
        fillColor = Color.web(hex);
        Update();
    }
    
    @Override
    public void SetOutlineColor(String hex) 
    {
        lineColor = Color.web(hex);
        Update();
    }
    
    @Override
    public void SetHeight(double height)
    {
        this.height = height;
        canvas.setHeight(height + padding);
        Update();
    }
    
    @Override
    public void SetWidth(double width)
    {
        this.width = width;
        canvas.setWidth(width + padding);
        Update();
    }
    
    @Override
    public void NoFill()
    {
        if(!fillShape)
            return;
        
        fillShape = false;
        Update();
    }
    
    @Override
    public void Fill()
    {
        if(fillShape)
            return;
        
        fillShape = true;
        Update();
    }
}
