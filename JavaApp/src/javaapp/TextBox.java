package javaapp;

import java.util.List;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextBox extends Tool
{
    double padding = 20; // padding to add around shape for hover outline
    AnchorPane controlPane;
    ContextMenu contextMenu;
    double fontSize = 50;
    
    public TextBox(double x, double y, AnchorPane controlPane)
    {
        this.controlPane = controlPane;
        
        this.height = 55;
        this.width = 55;
        this.canvas = new Canvas();
        this.x = x;
        this.y = y;
        
        canvas.setWidth(width + padding);
        canvas.setHeight(height + padding);
        canvas.setLayoutX(x - (canvas.getWidth() / 2));
        canvas.setLayoutY(y - (canvas.getHeight() / 2));
        
        graphicsContext = canvas.getGraphicsContext2D();
        
        LoadControls();
        Update();
        AddHandlers();
        AddContextMenu();
    }
    
    private void AddContextMenu()
    {
        contextMenu = new ContextMenu();
        MenuItem itemToFront = new MenuItem("Move to Front");
        MenuItem itemDeleteShape = new MenuItem("Delete");
        
        itemToFront.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                canvas.toFront();
            }
        });
        
        itemDeleteShape.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Pane parent = (Pane) canvas.getParent();
                parent.getChildren().remove(canvas);
                controlPane.getChildren().removeAll(controlPane.getChildren());
                
            }
        });
        
        contextMenu.getItems().add(itemToFront);
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("TextBoxToolUI.fxml"));
                    Pane controls = loader.load();
                    TextBoxToolUIController controller = loader.getController();
                    controller.SetTextBox(_this);
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
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setLineWidth(1);
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(lineColor);
        graphicsContext.setFont(font);
        
        
        Text textString = new Text(text);
        font = new Font(font.getFamily(), fontSize);
        textString.setFont(font);
        double fontWidth = textString.layoutBoundsProperty().get().getWidth();
        double fontHeight = textString.layoutBoundsProperty().get().getHeight();
        
        canvas.setWidth(fontWidth + padding);
        canvas.setHeight(fontHeight + padding);
        
        graphicsContext.strokeText(text, padding / 2,  fontHeight - (padding / 2));
        
        if(fillShape)
        {
            graphicsContext.fillText(text, padding / 2,  fontHeight - (padding / 2));
        }
        
    }
    
    private void AddHandlers()
    {
        
        moveEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                canvas.setLayoutX(e.getSceneX() - (canvas.getWidth() / 2));
                canvas.setLayoutY(e.getSceneY() - (canvas.getHeight() / 2));
            }
        };
        
        enterEvent = new EventHandler<MouseEvent>(){
                    
            @Override
            public void handle(MouseEvent e)
            {
                // draw a border around tool area
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
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, enterEvent);
        canvas.addEventHandler(MouseEvent.MOUSE_EXITED, exitEvent);
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
    public void SetFontSize(double fontSize)
    {
        try
        {
            this.fontSize = fontSize;
            font = new Font(font.getFamily(), this.fontSize);
            Update();
        }catch(Exception e)
        {
            System.out.println("Font too large");
        }
        
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
    
    @Override
    public void Rotate(double angle)
    {
        this.rotation = angle;
        canvas.setRotate(angle);
    }
    
    @Override
    public void SetText(String text)
    {
        this.text = text.trim();
        Update();
    }
    
    @Override
    public double GetWidth()
    {
        return this.fontSize * text.length();
    }
    
    @Override
    public double GetHeight()
    {
        return this.fontSize;
    }
    
    @Override
    public double GetFontSize()
    { 
        return this.fontSize;
    }
    
    @Override
    public void SetFontFamily(String family)
    {
        this.font = new Font(family, this.fontSize);
        Update();
    }
}
