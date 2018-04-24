/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

/**
 * FXML Controller class
 *
 * @author kristianjumper
 */
public class FilterUIController implements Initializable {

    @FXML
    private Slider RedSlider;
    @FXML
    private Slider GreenSlider;
    @FXML
    private Slider BlueSlider;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        RedSlider.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, 
                    Number old_val, Number new_val)
            {
                double val = Math.floor((double)new_val);
                
                System.out.println(val);
            }
        });
        
        GreenSlider.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, 
                    Number old_val, Number new_val)
            {
                double val = Math.floor((double)new_val);
                
                System.out.println(val);
            }
        });
        
        BlueSlider.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, 
                    Number old_val, Number new_val)
            {
                double val = Math.floor((double)new_val);
                
                System.out.println(val);
            }
        });
        
        
        
    }    
    
}
