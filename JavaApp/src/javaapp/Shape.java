package javaapp;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class Shape extends Tool
{
    Canvas canvas;
    GraphicsContext graphicsContext;
    
    EventHandler resizeEvent;
    EventHandler moveEvent;
    EventHandler enterEvent;
    EventHandler exitEvent;
    
    double height = 55;
    double width = 55;
    double x;
    double y;
    Color fillColor = Color.BLACK;
    Color lineColor = Color.BLACK;
    String shapeType;
    
    boolean resizing = false;
    
    public Shape(double x, double y, String shapeType)
    {
        this.canvas = new Canvas();
        this.x = x;
        this.y = y;
        this.shapeType = shapeType.toLowerCase();
        
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.setLayoutX(x - (canvas.getWidth() / 2));
        canvas.setLayoutY(y - (canvas.getHeight() / 2));
        
        graphicsContext = canvas.getGraphicsContext2D();
        
        UpdateShape();
        AddHandlers();
    }
    
    private void UpdateShape()
    {
        graphicsContext.setLineWidth(1);
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(lineColor);
        canvas.setWidth(width);
        canvas.setHeight(height);
        
        if("square".equals(shapeType))
        {
            //graphicsContext.fillRect(0, 0, width, height);
            graphicsContext.strokeRect(0, 0, width, height);
        }
        else if("circle".equals(shapeType))
        {
            //graphicsContext.fillOval(0, 0, width, height);
            graphicsContext.strokeOval(0, 0, width, height);
        }
        else if("triangle".equals(shapeType))
        {
            graphicsContext.beginPath();
            graphicsContext.moveTo(0, height);
            graphicsContext.lineTo((width / 2), 0);
            graphicsContext.lineTo(width, height);
            graphicsContext.closePath();
            graphicsContext.fill();
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
                EventHandler resizeWidth = new EventHandler<MouseEvent>(){
                    
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
                        UpdateShape();
                    }
                };
                
                EventHandler stopResize = new EventHandler<MouseEvent>(){
                    
                    @Override
                    public void handle(MouseEvent e)
                    {
                        resizing = false;
                    }
                };
                
                canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
                canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, stopResize);
                
                if((width - e.getX()) <= 5 || (width - e.getX()) >= (width - 10))
                {
                    canvas.getScene().setCursor(Cursor.W_RESIZE);
                    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeWidth);
                }else if((height - e.getY()) <= 5 || (height - e.getY()) >= (height - 10))
                {
                    canvas.getScene().setCursor(Cursor.N_RESIZE);
                    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeHeight);
                }else
                {
                    canvas.getScene().setCursor(Cursor.DEFAULT);
                    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, enterEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_MOVED, resizeEvent);
                    canvas.addEventHandler(MouseEvent.MOUSE_EXITED, exitEvent);
                }
            }
        };
        
        enterEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                graphicsContext.setLineWidth(1);
                graphicsContext.setStroke(lineColor);
                graphicsContext.strokeRect(0, 0, width, height);
            }
        };
        
        exitEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                graphicsContext.clearRect(0, 0, width + 1, height + 1);
                canvas.getScene().setCursor(Cursor.DEFAULT);
                UpdateShape();
            }
        };
        
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, enterEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, resizeEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_EXITED, exitEvent);
    }
    
    @Override
    public void RemoveHandlers() 
    {
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
        canvas.removeEventHandler(MouseEvent.MOUSE_ENTERED, enterEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, resizeEvent);
        canvas.removeEventHandler(MouseEvent.MOUSE_EXITED, exitEvent);
    }

    @Override
    public void SetFillColor(Color color) 
    {
        fillColor = color;
    }
    
    @Override
    public void SetOutlineColor(Color color) 
    {
        fillColor = color;
    }

    @Override
    public double GetHeight() 
    {
        return this.canvas.getHeight();
    }

    @Override
    public double GetWidth() 
    {
        return this.canvas.getWidth();
    }
    
    public Canvas GetCanvas()
    {
        return this.canvas;
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
    
}
