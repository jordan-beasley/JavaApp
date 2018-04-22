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
import javafx.scene.control.TextField;
import javafx.scene.text.Font;


public class TextBoxToolUIController implements Initializable {

    @FXML
    private ComboBox<?> fillColorDropDown;
    @FXML
    private ComboBox<?> outlineColorDropDown;
    @FXML
    private CheckBox cbSetFill;
    @FXML
    private Spinner<?> spRotation;
    
    Tool textBox = null;
    Map<String, String> colors = new HashMap<String, String>();
    @FXML
    private TextField textField;
    @FXML
    private Spinner<?> spFontSize;
    @FXML
    private ComboBox<?> fontDropDown;

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
        
        
        List<String> fonts = new ArrayList<String>(Font.getFamilies());
        ObservableList fontOptions = FXCollections.observableArrayList(fonts);
        fontDropDown.setItems(fontOptions);
        
        
        fillColorDropDown.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                String key = (String)fillColorDropDown.getValue();
                String colorHex = (String)colors.get(key);
                
                if(textBox != null)
                {
                    textBox.SetFillColor(colorHex);
                }
            }
        });
        
        fontDropDown.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                String font = (String)fontDropDown.getValue();
                
                if(textBox != null)
                {
                    textBox.SetFontFamily(font);
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
                
                if(textBox != null)
                {
                    textBox.SetOutlineColor(colorHex);
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
                    textBox.Fill();
                }else
                {
                    textBox.NoFill();
                }
                
                fillColorDropDown.setDisable(!newValue);
            }
        });
        
        SpinnerValueFactory widthValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 10000);
        SpinnerValueFactory rotationValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-360, 360);
        spFontSize.setValueFactory(widthValueFactory);
        spRotation.setValueFactory(rotationValueFactory);
        
        
        spFontSize.getValueFactory().valueProperty().addListener((obs, oldValue, newValue) -> 
        {
            if(this.textBox != null)
            {
                SpinnerValueFactory.DoubleSpinnerValueFactory factory = (SpinnerValueFactory.DoubleSpinnerValueFactory)spFontSize.getValueFactory();
                double value = factory.getValue();
                this.textBox.SetFontSize(value);
            }
        });
        
        spRotation.getValueFactory().valueProperty().addListener((obs, oldValue, newValue) -> 
        {
            if(this.textBox != null)
            {
                SpinnerValueFactory.DoubleSpinnerValueFactory factory = (SpinnerValueFactory.DoubleSpinnerValueFactory)spRotation.getValueFactory();
                double angle = factory.getValue();
                this.textBox.Rotate(angle);
            }
        });
        
        textField.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) 
            {
                if(textBox != null)
                {
                    String text = textField.textProperty().getValue();
                    textBox.SetText(text);
                    UpdateFields();
                }
            }
        });
        
    }    
    
    public void SetTextBox(Tool textBox)
    {
        this.textBox = textBox;
        
        if(this.textBox != null)
        {
            textField.setText(this.textBox.GetText());
            cbSetFill.setSelected(this.textBox.HasFill());
            SpinnerValueFactory sizeFactory = spFontSize.getValueFactory();
            SpinnerValueFactory rotationFactory = spRotation.getValueFactory();
            
            sizeFactory.setValue(this.textBox.GetFontSize());
            rotationFactory.setValue(this.textBox.GetRotation());
            
            String lineHex = this.textBox.GetOutlineColor().toString().substring(2, 8);
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
            
            String fillHex = this.textBox.GetFillColor().toString().substring(2, 8);
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
            fillColorDropDown.setDisable(!this.textBox.HasFill());
            
            fontDropDown.setPromptText(this.textBox.GetFont());
        }
    }
    
    
    private void UpdateFields()
    {
        textField.setText(this.textBox.GetText());
        cbSetFill.setSelected(this.textBox.HasFill());
        SpinnerValueFactory sizeFactory = spFontSize.getValueFactory();
        SpinnerValueFactory rotationFactory = spRotation.getValueFactory();

        
        sizeFactory.setValue(this.textBox.GetFontSize());
        rotationFactory.setValue(this.textBox.GetRotation());

        String lineHex = this.textBox.GetOutlineColor().toString().substring(2, 8);
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

        String fillHex = this.textBox.GetFillColor().toString().substring(2, 8);
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
        fillColorDropDown.setDisable(!this.textBox.HasFill());
        fontDropDown.setPromptText(this.textBox.GetFont());
    }
}
