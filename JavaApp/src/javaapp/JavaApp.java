package javaapp;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


public class JavaApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        BufferedImage bufferedImg = ImageIO.read(new File("C:\\Users\\caleb\\Pictures\\JavaApp.png"));;
        Image i = SwingFXUtils.toFXImage(bufferedImg, null);

        stage.setTitle("OpenEdit");
        stage.getIcons().add(i);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
