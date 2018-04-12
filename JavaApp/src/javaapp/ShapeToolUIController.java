package javaapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;


public class ShapeToolUIController implements Initializable 
{
    /*
        -Color (fill, outline)
        -Height
        -Width
        Rotate
        Scale
        Position
    */

    @FXML
    private ComboBox<?> fillColorDropDown;
    
    @FXML
    private ComboBox<?> outlineColorDropDown;
    
    @FXML
    private CheckBox cbSetFill;
    
    Tool shape = null;
    Map<String, String> colors = new HashMap<String, String>();
    @FXML
    private Spinner<?> spHeight;
    @FXML
    private Spinner<?> spWidth;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        colors.put("Black", "000000");
        colors.put("Red", "ff0000");
        colors.put("Green", "03ff00");
        colors.put("Blue", "0021ff");
        
        List<String> list = new ArrayList<String>(colors.keySet());
        ObservableList options = FXCollections.observableArrayList(list);
        fillColorDropDown.setItems(options);
        outlineColorDropDown.setItems(options);
        
        fillColorDropDown.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                String key = (String)fillColorDropDown.getValue();
                String colorHex = (String)colors.get(key);
                
                if(shape != null)
                {
                    shape.SetFillColor(colorHex);
                }
            }
        });
        
        outlineColorDropDown.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                String key = (String)outlineColorDropDown.getValue();
                String colorHex = (String)colors.get(key);
                
                if(shape != null)
                {
                    shape.SetOutlineColor(colorHex);
                }
            }
        });
        
        cbSetFill.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, 
                    Boolean oldValue, Boolean newValue)
            {
                if(newValue == true)
                {
                    shape.Fill();
                }else
                {
                    shape.NoFill();
                }
                
                fillColorDropDown.setDisable(!newValue);
            }
        });
        
        SpinnerValueFactory heightValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 10000);
        SpinnerValueFactory widthValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 10000);
        spHeight.setValueFactory(heightValueFactory);
        spWidth.setValueFactory(widthValueFactory);
        
        spHeight.getValueFactory().valueProperty().addListener((obs, oldValue, newValue) -> 
        {
            if(this.shape != null)
            {
                DoubleSpinnerValueFactory factory = (DoubleSpinnerValueFactory)spHeight.getValueFactory();
                double value = factory.getValue();
                this.shape.SetHeight(value);
            }
        });
        
        spWidth.getValueFactory().valueProperty().addListener((obs, oldValue, newValue) -> 
        {
            if(this.shape != null)
            {
                DoubleSpinnerValueFactory factory = (DoubleSpinnerValueFactory)spWidth.getValueFactory();
                double value = factory.getValue();
                this.shape.SetWidth(value);
            }
        });
    }
    
    public void SetShape(Tool shape)
    {
        this.shape = shape;
        
        if(this.shape != null)
        {
            cbSetFill.setSelected(this.shape.HasFill());
            SpinnerValueFactory heightFactory = spHeight.getValueFactory();
            SpinnerValueFactory widthFactory = spWidth.getValueFactory();
            
            heightFactory.setValue(this.shape.GetHeight());
            widthFactory.setValue(this.shape.GetWidth());
            
            
            String lineHex = this.shape.GetOutlineColor().toString().substring(2, 8);
            String lineColor = null;
            
            
            for(String key : colors.keySet())
            {
                if(lineHex.equals(colors.get(key)))
                {
                    lineColor = key;
                    break;
                }
            }
            
            String prmp = (lineColor != null) ? lineColor : "Colors";
            outlineColorDropDown.setPromptText(prmp);
            
            String fillHex = this.shape.GetFillColor().toString().substring(2, 8);
            String fillColor = null;
            
            for(String key : colors.keySet())
            {
                if(fillHex.equals(colors.get(key)))
                {
                    fillColor = key;
                    break;
                }
            }
            
            prmp = (fillColor != null) ? fillColor : "Colors";
            fillColorDropDown.setPromptText(prmp);
            
            fillColorDropDown.setDisable(!this.shape.HasFill());
            
        }
    }
}
