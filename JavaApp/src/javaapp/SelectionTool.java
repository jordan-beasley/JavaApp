package javaapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class SelectionTool extends Tool
{
    double padding = 20; // padding to add around shape for hover outline
    AnchorPane controlPane;
    ContextMenu contextMenu;
    Pane parent;
    
    boolean placed = false;
    double startingX = 0;
    double startingY = 0;
    
    public SelectionTool(double x, double y, Pane parent, AnchorPane controlPane)
    {
        this.controlPane = controlPane;
        this.parent = parent;
        
        this.height = 1;
        this.width = 1;
        this.canvas = new Canvas();
        this.x = x;
        this.y = y;
        
        //this.lineColor = Color.web("4488ff");
        this.lineColor = Color.web("005dff");
        this.fillColor = Color.web("619aff", 0.5);
        
        
        
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(1);
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(lineColor);
        
        
        //LoadControls();
        //Update();
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
        graphicsContext.fillRect(0,  0, width, height);
    }
    
    private void AddHandlers()
    {
        enterEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                graphicsContext.setLineWidth(1);
                graphicsContext.setStroke(boxBorderColor);
                graphicsContext.setLineDashes(new double[]{8});
                graphicsContext.strokeRect(0, 0, width, height);
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
        
        
        moveEvent = new EventHandler<MouseEvent>(){
                    
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
        
        clickEvent = new EventHandler<MouseEvent>()
        {
                    
            @Override
            public void handle(MouseEvent e)
            {
                
                if(placed == false)
                {
                    canvas.setLayoutX(x);
                    canvas.setLayoutY(y);
                    canvas.setWidth(width);
                    canvas.setHeight(height);
                    parent.addEventHandler(MouseEvent.MOUSE_MOVED, moveEvent);
                    placed = true;
                }else
                {
                    parent.removeEventHandler(MouseEvent.MOUSE_MOVED, moveEvent);
                    parent.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                }
                
            }
        };
        
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
    }
    
    @Override
    public void RemoveHandlers() 
    {
        parent.removeEventHandler(MouseEvent.MOUSE_CLICKED, this.clickEvent);
        parent.removeEventHandler(MouseEvent.MOUSE_MOVED, this.moveEvent);
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
