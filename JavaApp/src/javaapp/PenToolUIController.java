package javaapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class PenToolUIController implements Initializable 
{

    @FXML
    private Slider slider;
    Tool pen = null;
    @FXML
    private ComboBox<?> colorDropDown;
    
    Map<String, String> colors = new HashMap<String, String>();

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        colors.put("Black", "000000");
        colors.put("Red", "ff0000");
        colors.put("Green", "03ff00");
        colors.put("Blue", "0021ff");
        colors.put("White", "ffffff");
        
        List<String> list = new ArrayList<String>(colors.keySet());
        ObservableList options = FXCollections.observableArrayList(list);
        colorDropDown.setItems(options);
        
        slider.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, 
                    Number old_val, Number new_val)
            {
                double val = Math.floor((double)new_val);
                
                if(pen != null)
                    pen.SetWidth(val);
            }
        });
        
        colorDropDown.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                String key = (String)colorDropDown.getValue();
                String colorHex = (String)colors.get(key);
                
                if(pen != null)
                {
                    pen.SetFillColor(colorHex);
                    pen.SetOutlineColor(colorHex);
                }
            }
        });
        
    }    
    
    public void SetPen(Tool pen)
    {
        this.pen = pen;
        
        if(pen != null)
        {
            slider.setValue(this.pen.GetWidth());
            
            /*
            String hex = pen.GetOutlineColor().toString().substring(2, 8);
            String color = null;
            
            for(String key : colors.keySet())
            {
                if(hex.equals(colors.get(key)))
                {
                    color = key;
                    break;
                }
            }
            
            String prmp = (color != null) ? color : "Colors";
            colorDropDown.setPromptText(prmp);*/
        }
    }
}
