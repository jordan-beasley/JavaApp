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
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


public class FileSaver extends Tool
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
    
    public FileSaver(Pane parent, AnchorPane controlPane, Stage stage)
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
        
        SaveFile();
    }
    
    
    @Override
    public void Update()
    {
        graphicsContext.clearRect(0, 0, width, height);
        graphicsContext.fillRect(0,  0, width, height);
    }
    
    private void SaveFile()
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            File file = fileChooser.showSaveDialog(stage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);

            if (file != null) 
            {
                ImageIO.write(renderedImage, "png", file);
            }
            
            
            parent.getChildren().remove(canvas);
        }catch(IOException ex)
        {
            System.out.println("Image not saved");
        }
    }
}
