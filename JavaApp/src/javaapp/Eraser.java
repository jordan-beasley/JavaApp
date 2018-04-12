package javaapp;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Eraser extends Tool
{
    EventHandler eraseDrag;
    EventHandler initErase;
    AnchorPane controlPane;
    
    public Eraser(Canvas canvas, AnchorPane controlPane)
    {
        this.controlPane = controlPane;
        this.canvas = canvas;
        this.width = 5;
        this.height = 5;
        
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(width);
        
        LoadControls();
        AddHandlers();
    }
    
    private void LoadControls()
    {
        try
        {
            controlPane.getChildren().removeAll(controlPane.getChildren());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PenToolUI.fxml"));
            Pane controls = loader.load();
            PenToolUIController pCon = loader.getController();
            pCon.SetPen((Tool)this);
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
    
    private void AddHandlers()
    {
        
        initErase = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.clearRect(event.getX() - (width / 2), event.getY() - (height / 2), width, height);
            }
        };
        
        eraseDrag = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.clearRect(event.getX() - (width / 2), event.getY() - (height / 2), width, height);
            }
        };
        
        this.canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, initErase);
        this.canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, eraseDrag);
        
    }
    
    @Override
    public void RemoveHandlers()
    {
        this.canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, initErase);
        this.canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eraseDrag);
    }
    
    @Override
    public void SetFillColor(String hex)
    {
        return; // ignore color changes
    }
    
    @Override
    public void SetOutlineColor(String hex)
    {
        return; // ignore color changes
    }
    
    @Override
    public Color GetFillColor()
    {
        return null; // No color should be returned
    }
    
    @Override
    public void SetWidth(double width)
    {
        this.width = width;
        this.height = width;
    }
    
    @Override
    public void SetHeight(double height)
    {
        SetWidth(height);
    }
}
