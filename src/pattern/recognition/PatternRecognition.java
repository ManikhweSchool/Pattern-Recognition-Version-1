package pattern.recognition;

import com.manikhweschool.patternrecognition.Rhythm;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Duration;


public class PatternRecognition extends Application {
    
    private Stage stage;
    private Timeline timeline;
    private Thread thread;
    
    @Override
    public void start(Stage primaryStage) { 
        
        stage = primaryStage;
        timeline = new Timeline();
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource("MainDocument.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("maindocument.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            
            initializeAnimation();
            thread = new Thread(new StageTask());
            thread.start();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void initializeAnimation(){
    
        JustHandler handler = new JustHandler();
         ArrayList<Long> checkingTimes = new ArrayList<>();
        
         for(long i = 1; i <= 1000;i++)
             checkingTimes.add(i*1000);
        
         int numberOfKeyFrames = checkingTimes.size();
         
          for(int index = 0; index < numberOfKeyFrames; index++){
              timeline.getKeyFrames().add(
            new KeyFrame(Duration.millis(checkingTimes.get(index)), 
            handler));
          }
    }
    
     private class JustHandler implements EventHandler<ActionEvent>{
    
        @Override
        public void handle(ActionEvent e){
        
            if(MainDocumentController.gameHasStarted){
                stage.close();
                
            }
        }
        
     }
    
    private class StageTask implements Runnable{
    
        @Override
        public void run(){
        
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    timeline.play();
                        
                }
            });
        }
    }
   

    public static void main(String[] args) {
        launch(args);
    }
    
}
