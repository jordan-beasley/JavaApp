package javaapp;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CustomShape extends Tool
{
    double padding = 20; // padding to add around shape for hover outline
    AnchorPane controlPane;
    AnchorPane parentPane;
    
    EventHandler initDraw;
    EventHandler addPoint;
    
    //Point2D startPoint;
    ArrayList<Point2D> points = new ArrayList<Point2D>();
    
    int count = 0;
    
    double maxWidth = 0;
    double maxHeight = 0;
    
    public CustomShape(Canvas parentPane, AnchorPane controlPane)
    {
        this.controlPane = controlPane;
        
        //this.height = parentPane.getHeight();
        //this.width = parentPane.getWidth();
        this.canvas = new Canvas();
        
        canvas.setWidth(parentPane.getWidth());
        canvas.setHeight(parentPane.getHeight());
        canvas.setLayoutX(x); // place in center of parent pane
        canvas.setLayoutY(y);
        
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(5);
        graphicsContext.setStroke(lineColor);
        graphicsContext.strokeRect(0, 0, parentPane.getWidth(), parentPane.getHeight());
        
        //LoadControls();
        //Update();
        AddHandlers();
    }
    
    /*private void LoadControls()
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
            
    }*/
    
    @Override
    public void Update()
    {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setLineWidth(1);
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(lineColor);
        
        if("square".equals(shapeType))
        {
            
            graphicsContext.strokeRect(padding / 2,  padding / 2, width, height);
            
            if(fillShape)
            {
                graphicsContext.fillRect(padding / 2,  padding / 2, width, height);
            }
        }
        else if("circle".equals(shapeType))
        {
            graphicsContext.strokeOval(padding / 2,  padding / 2, width, height);
            
            if(fillShape)
            {
                graphicsContext.fillOval(padding / 2,  padding / 2, width, height);
            }
        }
        else if("triangle".equals(shapeType))
        {
            // draw inside of triangle
            graphicsContext.beginPath();
            graphicsContext.moveTo(padding / 2, height + (padding / 2));
            graphicsContext.lineTo((width / 2) + (padding / 2), padding / 2);
            graphicsContext.lineTo(width + (padding / 2), height + (padding / 2));
            graphicsContext.closePath();
            
            if(fillShape)
            {
                graphicsContext.fill();
            }
            
            // draw outine of shape
            graphicsContext.stroke();
        }
        
    }
    
    private void AddHandlers()
    {
        
        initDraw = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                graphicsContext.setFill(GetFillColor());
                graphicsContext.setStroke(GetOutlineColor());
                graphicsContext.setLineWidth(width);
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.stroke();
                
                System.out.println("first");
                points.add(new Point2D(event.getX(), event.getY()));
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, addPoint);
                canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        
        addPoint = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                System.out.println("scene, event" + " (" + event.getSceneX() + ", " + event.getX() + ")");
                System.out.println("scene, event" + " (" + event.getSceneY() + ", " + event.getY() + ")");
                
                graphicsContext.setFill(GetFillColor());
                graphicsContext.setStroke(GetOutlineColor());
                graphicsContext.setLineWidth(width);
                
                maxHeight = ((points.get(0).getY() - event.getY()) > maxHeight) ? 
                        (points.get(0).getY() - event.getY()) : maxHeight;
                maxWidth = ((event.getX() - points.get(0).getX()) > maxWidth) ? 
                        (event.getX() - points.get(0).getX()) : maxWidth;
                
                if(maxHeight == 0)
                {
                    maxHeight = event.getY() - points.get(0).getY();
                }
                
                
                if(maxWidth == 0)
                {
                    maxWidth = event.getX() - points.get(0).getX();
                }
                
                System.out.println("maxH = " + maxHeight);
                System.out.println("maxW = " + maxWidth);
                
                if(count < 3)
                {
                    points.add(new Point2D(event.getX(), event.getY()));
                    graphicsContext.lineTo(event.getX(), event.getY());
                    graphicsContext.stroke();
                }else
                {
                    // clear canvas
                    //graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    // move canvas(x y) to startpoint
                    //graphicsContext.lineTo(points.get(0).getX(), points.get(0).getY());
                    //graphicsContext.stroke();
                    canvas.setLayoutX(points.get(0).getX());
                    canvas.setLayoutY(points.get(0).getY());
                    // resize for width and height
                    canvas.setHeight(maxHeight);
                    canvas.setWidth(maxWidth);
                    
                    /*
                    graphicsContext.setLineWidth(5);
                    graphicsContext.setStroke(lineColor);
                    graphicsContext.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    
                    graphicsContext.beginPath();
                    graphicsContext.moveTo(points.get(0).getX() - maxWidth, points.get(0).getY() - maxHeight);
                    System.out.println("converting (" + points.get(0).getX() + ", " + points.get(0).getX()
                            + ") to (" + (points.get(0).getX() - maxWidth) + "," + (points.get(0).getY() - maxHeight) + ")");
                    */
                    graphicsContext.beginPath();
                    graphicsContext.moveTo(maxWidth - points.get(0).getX(), maxHeight - points.get(0).getY());
                    graphicsContext.lineTo(maxWidth - points.get(0).getX(), maxHeight - points.get(0).getY());
                    // draw points - width and height
                    for(int i = 1; i < points.size(); i++)
                    {
                        graphicsContext.lineTo(maxWidth - points.get(i).getX(), maxHeight - points.get(i).getY());
                        graphicsContext.stroke();
                    }
                     
                }
                
                count++;
                System.out.println("count: " + count);
            }
        };
        
        /*
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
        };*/
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, initDraw);
        //canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
        //canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, enterEvent);
        //canvas.addEventHandler(MouseEvent.MOUSE_MOVED, resizeEvent);
        //canvas.addEventHandler(MouseEvent.MOUSE_EXITED, exitEvent);
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
    
    public void Rotate(double angle)
    {
        this.rotation = angle;
        canvas.setRotate(angle);
    }
}
