package pattern.recognition;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class PatternRecognition extends Application {
    
    @Override
    public void start(Stage primaryStage) { 
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource("MainDocument.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("maindocument.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
   

    public static void main(String[] args) {
        launch(args);
    }
    
}
