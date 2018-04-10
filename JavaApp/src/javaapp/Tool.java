package javaapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;


public abstract class Tool 
{
    public abstract void RemoveHandlers();
    public abstract void SetFillColor(Color color);
    public abstract void SetOutlineColor(Color color);
    public abstract Color GetFillColor();
    public abstract Color GetOutlineColor();
    
    public abstract void SetHeight(double height);
    public abstract void SetWidth(double width);
    public abstract double GetHeight();
    public abstract double GetWidth();
    
    public abstract Canvas GetCanvas();
}
