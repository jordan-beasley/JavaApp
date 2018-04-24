package javaapp;

import com.sun.scenario.effect.ImageData;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class CropTool extends Tool
{
    double padding = 20; // padding to add around shape for hover outline
    AnchorPane controlPane;
    ContextMenu contextMenu;
    Pane parent;
    Canvas imgCanvas;
    EventHandler dragEvent;
    EventHandler initEvent;
    Image img;
    
    boolean placed = false;
    PixelReader pr;
    WritableImage writableImage;
    
    public CropTool(double x, double y, Pane parent, AnchorPane controlPane, Canvas imgCanvas)
    {
        this.controlPane = controlPane;
        this.parent = parent;
        this.imgCanvas = imgCanvas;
        
        this.height = 1;
        this.width = 1;
        this.canvas = new Canvas();
        this.x = x;
        this.y = y;
        
        
        graphicsContext = canvas.getGraphicsContext2D();
        
        AddHandlers();
        AddContextMenu();
    }
    
    private void AddContextMenu()
    {
        contextMenu = new ContextMenu();
        MenuItem itemDeleteShape = new MenuItem("Delete");
        
        itemDeleteShape.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Pane parent = (Pane) canvas.getParent();
                parent.getChildren().remove(canvas);
                controlPane.getChildren().removeAll(controlPane.getChildren());
                
            }
        });
        
        contextMenu.getItems().add(itemDeleteShape);
        
        canvas.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
            }
        });
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
        graphicsContext.clearRect(0, 0, width, height);
        graphicsContext.drawImage(img, padding/2, padding/2, img.getWidth(), img.getHeight());
    }
    
    private void AddHandlers()
    {
        dragEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                width = e.getSceneX() - canvas.getLayoutX();
                height = e.getSceneY() - canvas.getLayoutY();
                
                canvas.setWidth(width);
                canvas.setHeight(height);
                graphicsContext.clearRect(0, 0, width, height);
                graphicsContext.strokeRect(0,  0, width, height);
                graphicsContext.fillRect(0,  0, width, height);
            }
        };
        
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
                LoadControls();
            }
        };
        
        initEvent = new EventHandler<MouseEvent>()
        {
                    
            @Override
            public void handle(MouseEvent e)
            {
                
                if(placed == false)
                {
                    graphicsContext.setLineWidth(1);
                    lineColor = Color.web("005dff");
                    fillColor = Color.web("619aff", 0.5);
                    graphicsContext.setFill(fillColor);
                    graphicsContext.setStroke(lineColor);
                    canvas.setLayoutX(e.getSceneX());
                    canvas.setLayoutY(e.getSceneY());
                    canvas.setWidth(width);
                    canvas.setHeight(height);
                    parent.getChildren().add(canvas);
                    parent.addEventHandler(MouseEvent.MOUSE_MOVED, dragEvent);
                    placed = true;
                }else
                {
                    graphicsContext.clearRect(0, 0, width, height);
                    
                    
                    SnapshotParameters sp = new SnapshotParameters();
                    
                    Rectangle2D viewPort = new Rectangle2D((int)canvas.getLayoutX(), (int)canvas.getLayoutY(),
                            (int)canvas.getWidth(),
                            (int)canvas.getHeight());
                    sp.setViewport(viewPort);
                    sp.setFill(Color.TRANSPARENT);
                    writableImage = new WritableImage(
                            (int)imgCanvas.getWidth(),
                            (int)imgCanvas.getHeight());
                    imgCanvas.snapshot(sp, writableImage);
                    pr = writableImage.getPixelReader();
                    
                    img = (Image)writableImage;
                    graphicsContext.drawImage(img, padding/2, padding/2, img.getWidth(), img.getHeight());
                    
                    parent.removeEventHandler(MouseEvent.MOUSE_MOVED, dragEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, enterEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_EXITED, exitEvent);
                    parent.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                }
                
            }
        };
        
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED, initEvent);
    }
    
    @Override
    public void RemoveHandlers() 
    {
        parent.removeEventHandler(MouseEvent.MOUSE_CLICKED, this.clickEvent);
        parent.removeEventHandler(MouseEvent.MOUSE_MOVED, this.moveEvent);
    }
}
