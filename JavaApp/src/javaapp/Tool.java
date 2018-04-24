package javaapp;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Tool
{
    Canvas canvas = null;
    GraphicsContext graphicsContext;
    
    EventHandler resizeEvent;
    EventHandler moveEvent;
    EventHandler enterEvent;
    EventHandler exitEvent;
    EventHandler clickEvent;
    
    double height = 1;
    double width = 1;
    double x = 0;
    double y = 0;
    double rotation = 0;
    Color fillColor = Color.web("000000");
    Color lineColor = Color.web("000000");
    Color boxBorderColor = Color.web("000000"); // border drawn around shapes, always black
    String shapeType;
    
    String text = "Text";
    Font font = new Font(Font.getDefault().getFamily(), 50);
    
    boolean resizing = false;
    boolean fillShape = true;
    
    public void RemoveHandlers(){}
    public void Update(){}
    public void SetFillColor(String hex){ this.fillColor = Color.web(hex); }
    public void SetOutlineColor(String hex){ this.lineColor = Color.web(hex); }
    public Color GetFillColor(){ return fillColor; }
    public Color GetOutlineColor(){ return lineColor; }
    
    public Canvas GetCanvas(){ return canvas; }
    public void Rotate(double angle){}
    public double GetRotation(){ return rotation; }
    public void SetHeight(double height){ this.height = height; }
    public void SetWidth(double width){ this.width = width; }
    public double GetHeight(){ return height; }
    public double GetWidth(){ return width; }
    
    public void NoFill(){}
    public void Fill(){}
    public boolean HasFill(){ return fillShape; }
    public void SetText(String text){}
    public void SetFontFamily(String family){}
    public void SetFontSize(double fontSize){}
    public String GetText(){ return text; }
    public double GetFontSize(){ return 0.0; }
    public String GetFont(){ return this.canvas.getGraphicsContext2D().getFont().getFamily(); }
    
}
