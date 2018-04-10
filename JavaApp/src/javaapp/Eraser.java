package javaapp;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Eraser extends Tool 
{
    GraphicsContext graphicsContext;
    Canvas canvas;
    
    EventHandler eraseDrag;
    EventHandler initErase;
    
    double width = 5;
    double height = 5;
    
    public Eraser(Canvas canvas)
    {
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(width);
        
        AddHandlers();
    }
    
    private void AddHandlers()
    {
        
        initErase = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.clearRect(event.getX(), event.getY(), width, height);
            }
        };
        
        eraseDrag = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                graphicsContext.clearRect(event.getX(), event.getY(), width, height);
            }
        };
        
        this.canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, initErase);
        this.canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, eraseDrag);
        
    }
    
    public void RemoveHandlers()
    {
        this.canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, initErase);
        this.canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eraseDrag);
    }
    
    public void SetFillColor(Color color)
    {
        return;
    }
    
    public void SetOutlineColor(Color color)
    {
        return;
    }
    
    public double GetWidth()
    {
        return this.width;
    }
    
    public double GetHeight()
    {
        return this.height;
    }
    
    public Color GetFillColor()
    {
        return null;
    }
    
    public Color GetOutlineColor()
    {
        return null;
    }
    
    public void SetHeight(double height)
    {
        this.height = height;
    }
    
    public void SetWidth(double width)
    {
        this.width = width;
    }
    
    public Canvas GetCanvas()
    {
        return this.graphicsContext.getCanvas();
    }
}
