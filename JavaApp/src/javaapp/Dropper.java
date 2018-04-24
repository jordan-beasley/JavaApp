package javaapp;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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

public class Dropper extends Tool
{
    double padding = 20; // padding to add around shape for hover outline
    AnchorPane controlPane;
    ContextMenu contextMenu;
    Pane parent;
    PixelReader pr;
    
    boolean placed = false;
    double startingX = 0;
    double startingY = 0;
    WritableImage writableImage;
    Stage stage;
    
    public Dropper(Pane parent, AnchorPane controlPane, Stage stage)
    {
        this.controlPane = controlPane;
        this.parent = parent;
        this.stage = stage;
        
        this.canvas = new Canvas();
        this.canvas.setWidth(parent.getWidth());
        this.canvas.setHeight(parent.getWidth());
        
        parent.getChildren().add(this.canvas);
        
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        this.writableImage = new WritableImage((int)this.parent.getWidth(),
                (int)this.parent.getHeight());
        this.parent.snapshot(sp, writableImage);
        
        pr = writableImage.getPixelReader();
        
        AddHandlers();
    }
    
    @Override
    public void Update()
    {
        graphicsContext.clearRect(0, 0, width, height);
        graphicsContext.fillRect(0,  0, width, height);
    }
    
    private void AddHandlers()
    {
        moveEvent = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) 
            {
                try
                {
                    Color scene = pr.getColor((int)event.getSceneX(),  (int)event.getSceneY());
                    String p = scene.toString().substring(2, 8);
                    System.out.println("scene: " + p);
                }catch(Exception e)
                {
                    System.out.println("Not in parent bounds");
                }
                
               
            }
        };
        
        clickEvent = new EventHandler<MouseEvent>()
        {
                    
            @Override
            public void handle(MouseEvent e)
            {
                parent.getChildren().remove(canvas);
                parent.removeEventHandler(MouseEvent.MOUSE_MOVED, moveEvent);
                parent.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                
            }
        };
        
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        parent.addEventHandler(MouseEvent.MOUSE_MOVED, moveEvent);
    }
    
    @Override
    public void RemoveHandlers() 
    {
        parent.removeEventHandler(MouseEvent.MOUSE_MOVED, this.moveEvent);
        parent.removeEventHandler(MouseEvent.MOUSE_CLICKED, this.clickEvent);
    }
}
