package javaapp;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Pen extends Tool
{
    GraphicsContext graphicsContext;
    Canvas canvas;
    
    EventHandler drawDrag;
    EventHandler initDraw;
    double height = 1;
    double width = 1;
    Color fillColor = Color.BLACK;
    Color lineColor = Color.BLACK;
    
    public Pen(Canvas canvas)
    {
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        
        AddHandlers();
    }
    
    private void AddHandlers()
    {
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(lineColor);
        graphicsContext.setLineWidth(width);
        
        initDraw = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.stroke();
            }
        };
        
        drawDrag = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
            }
        };
        
        this.canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, initDraw);
        this.canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, drawDrag);
        
    }
    
    public void RemoveHandlers()
    {
        this.canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, initDraw);
        this.canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, drawDrag);
    }
    
    public void SetFillColor(Color color)
    {
        fillColor = color;
        graphicsContext.setFill(color);
    }
    
    public void SetOutlineColor(Color color)
    {
        lineColor = color;
        graphicsContext.setStroke(color);
    }
    
    public double GetHeight()
    {
        return this.height;
    }
    
    public double GetWidth()
    {
        return this.width;
    }
    
    public Color GetFillColor()
    {
        return this.fillColor;
    }
    
    public Color GetOutlineColor()
    {
        return this.lineColor;
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
