package javaapp;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class Pen extends Tool
{
    EventHandler drawDrag;
    EventHandler initDraw;
    AnchorPane controlPane;
    
    public Pen(Canvas canvas, AnchorPane controlPane)
    {
        this.controlPane = controlPane;
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        
        LoadControls();
        AddHandlers();
    }
    
    private void LoadControls()
    {
        Tool _this = this;
        
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run() 
            {
                try
                {
                    controlPane.getChildren().removeAll(controlPane.getChildren());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("filterUI.fxml"));
                    Pane controls = loader.load();
                    //PenToolUIController pCon = loader.getController();
                    FilterUIController pCon = loader.getController();
                    //pCon.SetPen(_this);
                    controls.localToParent(controlPane.getLayoutBounds());
                    AnchorPane.setBottomAnchor(controls, 0.0);
                    AnchorPane.setTopAnchor(controls, 0.0);
                    AnchorPane.setLeftAnchor(controls, 0.0);
                    AnchorPane.setRightAnchor(controls, 0.0);
                    ColorPicker colorPicker = new ColorPicker();
                    controlPane.getChildren().setAll(controls);

                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                
            }
        });
        
        thread.run();
        
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
            }
        };
        
        drawDrag = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                graphicsContext.setFill(GetFillColor());
                graphicsContext.setStroke(GetOutlineColor());
                graphicsContext.setLineWidth(width);
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
            }
        };
        
        this.canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, initDraw);
        this.canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, drawDrag);
        
    }
    
    @Override
    public void RemoveHandlers()
    {
        this.canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, initDraw);
        this.canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, drawDrag);
    }
}
