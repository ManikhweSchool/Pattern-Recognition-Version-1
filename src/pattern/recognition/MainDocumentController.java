
package pattern.recognition;

import com.manikhweschool.patternrecognition.Music;
import com.manikhweschool.patternrecognition.Player;
import com.manikhweschool.patternrecognition.Rhythm;
import com.manikhweschool.patternrecognition.buildingblocks.CartesianPlane;
import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.DirectionBasedAnswer;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.buildingblocks.PositionBasedAnswer;
import com.manikhweschool.patternrecognition.games.AntiClockwisePointBasedGame;
import com.manikhweschool.patternrecognition.games.ClockwisePointBasedGame;
import com.manikhweschool.patternrecognition.games.DecreasingPointBasedGame;
import com.manikhweschool.patternrecognition.games.IncreasingPointBasedGame;
import com.manikhweschool.patternrecognition.buildingblocks.Plane;
import com.manikhweschool.patternrecognition.games.PointBasedGame;
import com.manikhweschool.patternrecognition.space.RegionMovingStrategy;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainDocumentController implements Initializable {
    
    @FXML
    private TextField musicTextField;
    @FXML
    private ListView<String> forwardStepsLV;
    @FXML
    private ListView<String> backwardStepsLV;
    @FXML
    private ComboBox<String> typeOfAnswers;
    @FXML
    private TextArea rhythmTextArea;
    @FXML
    private RadioButton plainRB;
    @FXML
    private RadioButton lettersRB;
    @FXML
    private RadioButton numbersRB;
    @FXML
    private RadioButton allRB;
    @FXML
    private RadioButton oneAttemptRB;
    @FXML
    private RadioButton twoAttemptsRB;
    @FXML
    private RadioButton threeAttemptsRB;
    @FXML
    private RadioButton fourAttemptsRB;
    @FXML
    private Label gameName;
    @FXML
    private Label errorLabel;
    @FXML
    private Label musicLabel;
    @FXML
    private Button beginGameButton;
    @FXML
    private Slider slider;
    @FXML
    private Label speedLabel;
    @FXML
    private Label speedLabelInfo;
    @FXML
    private ComboBox<String> r1MovingStrategy;
    @FXML
    private ComboBox<String> r2MovingStrategy;
    @FXML
    private ComboBox<String> r3MovingStrategy;
    @FXML
    private ComboBox<String> r4MovingStrategy;
    @FXML
    private ComboBox<String> r5MovingStrategy;
    @FXML
    private ComboBox<String> r6MovingStrategy;
    @FXML
    private ComboBox<String> r7MovingStrategy;
    @FXML
    private ComboBox<String> r8MovingStrategy;
    
    ArrayList<RegionMovingStrategy> movingStrategies = new ArrayList<>();
    
    private boolean hasRhythm = false;
    
    private final double INIT_SPEED_FACTOR = 50;
    
    private PointBasedGame game;
    private Music music;
    private Rhythm rhythm;
    private byte backwardSteps;
    private byte forwardSteps;
    private Plane plane;
    
    private boolean positionGame;
   
    private Stage primaryStage;
    private int width = 600;
    private int height = 600;
    
    private long currentTime = 0;
    private long startTime;
    private long endTime;  
    private long initTime = -1;
    private long timeBetweenPatterns;
    private Timeline timeline;
    
    private Player player; 
    private Map<Integer,Map<Location, LinkedList<DirectionBasedAnswer>>> directionBasedCorrectAnswers;
    private Map<Integer,Map<Location, LinkedList<PositionBasedAnswer>>> positionBasedCorrectAnswers;
    private String positionAnswer = "";
    private Map<Integer, Double> marks;
    
    public byte numberOfTries;
    
    public void readRhythm(KeyEvent event){
    
        if(initTime==-1){
            System.out.println("Error : Start By Playing Music First.");
            return;
        }
        byte numberOfMoves = 0;
        
        if(event.getCode()==KeyCode.NUMPAD1 || event.getCode()==KeyCode.DIGIT1){
            numberOfMoves = 1;
            if(!hasRhythm)
                hasRhythm = true;
        }
        else if(event.getCode()==KeyCode.NUMPAD2 || event.getCode()==KeyCode.DIGIT2){
            numberOfMoves = 2;
            if(!hasRhythm)
                hasRhythm = true;
        }
        else if(event.getCode()==KeyCode.NUMPAD3 || event.getCode()==KeyCode.DIGIT3){
            numberOfMoves = 3;
        }
        else if(event.getCode()==KeyCode.NUMPAD4 || event.getCode()==KeyCode.DIGIT4){
            numberOfMoves = 4;
            if(!hasRhythm)
                hasRhythm = true;
        }
        else if(event.getCode()==KeyCode.NUMPAD5 || event.getCode()==KeyCode.DIGIT5){
            numberOfMoves = 5;
            if(!hasRhythm)
                hasRhythm = true;
        }
        else if(event.getCode()==KeyCode.NUMPAD6 || event.getCode()==KeyCode.DIGIT6){
            numberOfMoves = 6;
            if(!hasRhythm)
                hasRhythm = true;
        }
        else if(event.getCode()==KeyCode.NUMPAD7 || event.getCode()==KeyCode.DIGIT7){
            numberOfMoves = 7;
            if(!hasRhythm)
                hasRhythm = true;
        }
        else if(event.getCode()==KeyCode.NUMPAD8 || event.getCode()==KeyCode.DIGIT8){
            numberOfMoves = 8;
            if(!hasRhythm)
                hasRhythm = true;
        }
        else if(event.getCode()==KeyCode.NUMPAD9 || event.getCode()==KeyCode.DIGIT9){
            numberOfMoves = 9;
            if(!hasRhythm)
                hasRhythm = true;
        }
        else if(event.getCode()==KeyCode.NUMPAD0 || event.getCode()==KeyCode.DIGIT0){
            currentTime =System.currentTimeMillis()-initTime;
            if(!hasRhythm){
                errorLabel.setText("Error : Make Sure You Press At Least One Clap For The Rhythm.");
                errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
                errorLabel.setVisible(true);
                ((TextArea)event.getSource()).appendText("\tSTART OVER\n\tCLOSE AND RE-OPE\n\tTHE WINDOW!");
                ((TextArea)event.getSource()).setDisable(true);
                return;
            }
            timeBetweenPatterns = currentTime-rhythm.getTimes().get(rhythm.getTimes().size()-1);
            rhythm.repeatPattern(currentTime,timeBetweenPatterns, 1000000);
            
            music.addRhythm(rhythm);
            music.stop();
            
            
                
        }
        
        if(!hasRhythm){
            errorLabel.setText("Error : Make Sure You Press At Least One Clap For The Rhythm.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            ((TextArea)event.getSource()).appendText("\tSTART OVER\n\tCLOSE AND RE-OPE\n\tTHE WINDOW!");
            ((TextArea)event.getSource()).setDisable(true);
            return;
        }
        
        int count = 0;
        byte initNumberOfMoves = numberOfMoves;
        while(numberOfMoves>0){
            currentTime =System.currentTimeMillis()-initTime;
            if(initNumberOfMoves != 1)
                currentTime += count*((1000-10*Double.valueOf(speedLabel.getText()))/initNumberOfMoves);
            ((TextArea)event.getSource()).appendText("0");
            
            if(numberOfMoves==initNumberOfMoves)
                rhythm.addTime(currentTime,true);
            else
                rhythm.addTime(currentTime,false);
            ((TextArea)event.getSource()).requestFocus();
            numberOfMoves--;
            count++;
        }
        
        if(!(event.getCode()==KeyCode.NUMPAD0 || event.getCode()==KeyCode.DIGIT0))
            ((TextArea)event.getSource()).appendText(" - " + initNumberOfMoves +"\n");
        else
            ((TextArea)event.getSource()).appendText("---Rhythm Pattern Created---\n");
    }
    
    public void pickMusic(){
    
        String path = musicTextField.getText();
        if(path == null || path.isEmpty()){
            errorLabel.setText("Error : Make Sure You Pick Music.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
        
       
        if(path.endsWith(".mp3") || 
        path.endsWith(".mp4") || path.endsWith(".wav")){
            music = new Music(path);
            
        }
        else{
            errorLabel.setText("Error : Only .mp3, .mp4 and .wav files are allowed.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            
        }
        
    }
    
    public void playMusic(ActionEvent event){
    
        pickMusic();
        if(music==null) return;
        music.play();
        initTime = System.currentTimeMillis();
        rhythm = new Rhythm();
        rhythmTextArea.requestFocus();
        System.out.println("Music playing...");
        
    }
    
    private void choosePlane(){
        
        if(plainRB.isSelected())
            plane = Plane.Plain_Plane;
        else if(lettersRB.isSelected())
            plane = Plane.Letters_Plane;
        else if(numbersRB.isSelected())
            plane = Plane.Numbers_Plane;
        else 
            plane = Plane.All_Planes;
    }
    
    private void chooseAttempts(){
        
        
        if(twoAttemptsRB.isSelected())
            numberOfTries = 2;
        else if(threeAttemptsRB.isSelected())
            numberOfTries = 3;
        else if(fourAttemptsRB.isSelected())
            numberOfTries = 4;
        else
            numberOfTries = 1;
    }
    
    public void showGame(ActionEvent event) {  
        
        choosePlane();
        chooseAttempts();
        plane = Plane.All_Planes;
        if(typeOfAnswers.getSelectionModel().isSelected(0) || typeOfAnswers.getSelectionModel().isSelected(1))
            positionGame = typeOfAnswers.getSelectionModel().isSelected(0);
        else{
            errorLabel.setText("Error : Make Sure You Pick What To Track.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
        
        fillSteps();
        
        if(forwardSteps<backwardSteps){
            errorLabel.setText("Error : Make Sure Forward Steps Greater Or Equals to Backsward Steps.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
        
        
        if(music==null){
            errorLabel.setText("Error : Make Sure You Choose Music.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
        
        
        
        //----------------------Region One-----------------------------
        if(r1MovingStrategy.getSelectionModel().isSelected(0))
            movingStrategies.add(RegionMovingStrategy.Row_By_Row);
        else if(r1MovingStrategy.getSelectionModel().isSelected(1))
            movingStrategies.add(RegionMovingStrategy.Column_By_Column);
        else{
            errorLabel.setText("Error : Make Sure You Choose Moving Strategy For Region One.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
        //----------------------Region Two-----------------------------
        if(r2MovingStrategy.getSelectionModel().isSelected(0))
            movingStrategies.add(RegionMovingStrategy.Row_By_Row);
        else if(r2MovingStrategy.getSelectionModel().isSelected(1))
            movingStrategies.add(RegionMovingStrategy.Column_By_Column);
        else{
            errorLabel.setText("Error : Make Sure You Choose Moving Strategy For Region Two.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
         //----------------------Region Three-----------------------------
        if(r3MovingStrategy.getSelectionModel().isSelected(0))
            movingStrategies.add(RegionMovingStrategy.Row_By_Row);
        else if(r3MovingStrategy.getSelectionModel().isSelected(1))
            movingStrategies.add(RegionMovingStrategy.Column_By_Column);
        else{
            errorLabel.setText("Error : Make Sure You Choose Moving Strategy For Region Three.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
         //----------------------Region Four-----------------------------
        if(r4MovingStrategy.getSelectionModel().isSelected(0))
            movingStrategies.add(RegionMovingStrategy.Row_By_Row);
        else if(r4MovingStrategy.getSelectionModel().isSelected(1))
            movingStrategies.add(RegionMovingStrategy.Column_By_Column);
        else{
            errorLabel.setText("Error : Make Sure You Choose Moving Strategy For Region Four.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
         //----------------------Region Five-----------------------------
        if(r5MovingStrategy.getSelectionModel().isSelected(0))
            movingStrategies.add(RegionMovingStrategy.Row_By_Row);
        else if(r5MovingStrategy.getSelectionModel().isSelected(1))
            movingStrategies.add(RegionMovingStrategy.Column_By_Column);
        else{
            errorLabel.setText("Error : Make Sure You Choose Moving Strategy For Region Five.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
         //----------------------Region Six-----------------------------
        if(r6MovingStrategy.getSelectionModel().isSelected(0))
            movingStrategies.add(RegionMovingStrategy.Row_By_Row);
        else if(r6MovingStrategy.getSelectionModel().isSelected(1))
            movingStrategies.add(RegionMovingStrategy.Column_By_Column);
        else{
            errorLabel.setText("Error : Make Sure You Choose Moving Strategy For Region Six.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
         //----------------------Region Seven-----------------------------
        if(r7MovingStrategy.getSelectionModel().isSelected(0))
            movingStrategies.add(RegionMovingStrategy.Row_By_Row);
        else if(r7MovingStrategy.getSelectionModel().isSelected(1))
            movingStrategies.add(RegionMovingStrategy.Column_By_Column);
        else{
            errorLabel.setText("Error : Make Sure You Choose Moving Strategy For Region Seven.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
         //----------------------Region Eight-----------------------------
        if(r8MovingStrategy.getSelectionModel().isSelected(0))
            movingStrategies.add(RegionMovingStrategy.Row_By_Row);
        else if(r8MovingStrategy.getSelectionModel().isSelected(1))
            movingStrategies.add(RegionMovingStrategy.Column_By_Column);
        else{
            errorLabel.setText("Error : Make Sure You Choose Moving Strategy For Region Eight.");
            errorLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
            errorLabel.setVisible(true);
            return;
        }
        
        primaryStage = new Stage();
        try{
            if(plane==Plane.Plain_Plane || plane==Plane.All_Planes){
                CartesianPlane.cartesianPlaneNumber = 0;
                
            }
            else if(plane==Plane.Letters_Plane){
                CartesianPlane.cartesianPlaneNumber = 1;
                
            }
            else if(plane==Plane.Numbers_Plane){
                CartesianPlane.cartesianPlaneNumber = 2;
                
            }
            
            player = new Player(numberOfTries);
            
            CartesianPlane.setNumberOfTries(numberOfTries);
            CartesianPlane.setPlaneType(plane);
            switch((byte)(Math.random()*4)){
                case 0 : game = new ClockwisePointBasedGame(positionGame,width,height,forwardSteps, 
                         backwardSteps, music, startTime, movingStrategies); break;
                case 1 : game = new AntiClockwisePointBasedGame(positionGame,width,height,forwardSteps, 
                         backwardSteps, music, startTime,movingStrategies); break;
                case 2 : game = new IncreasingPointBasedGame(positionGame,width,height,forwardSteps, 
                         backwardSteps, music, startTime,movingStrategies); break;
                case 3 : game = new DecreasingPointBasedGame(positionGame,width,height,forwardSteps, 
                         backwardSteps, music, startTime,movingStrategies); break;
            }
            
            addCellListeners();
            game.getCartesianPlane().requestFocus();
            
            //game.getCartesianPlane().getMusic().play();
            Scene scene = new Scene(game);
            
            primaryStage.setScene(scene);
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
        primaryStage.setTitle("Pattern Recognition Version I");
        primaryStage.show();
        
        initializeAnimation();
        
        
        new Thread(new MusicTask()).start();
        new Thread(new TimelineTask()).start();
        startTime = System.currentTimeMillis();
        
        game.getCartesianPlane().requestFocus();
        
    }
    
    private void initiateSomeDataFields(){
    
        timeline = new Timeline();
        directionBasedCorrectAnswers = new LinkedHashMap<>();
        positionBasedCorrectAnswers = new LinkedHashMap<>();
        
        
       
        marks = new LinkedHashMap<>();
        width = 500;
        height = 500;
    }
    
    public void fillSteps(){
    
        if(forwardStepsLV.getSelectionModel().getSelectedIndex()==0 && 
        backwardStepsLV.getSelectionModel().getSelectedIndex()==0){
            forwardSteps = 1;
            backwardSteps = 0;
        }
        else{
            switch(forwardStepsLV.getSelectionModel().getSelectedIndex()){
                case 1: forwardSteps = 2; break;
                case 2: forwardSteps = 3; break;
                case 3: forwardSteps = 4; break;
                case 4: forwardSteps = 5; break;
                case 5: forwardSteps = 6; break;
                case 6: forwardSteps = 7; break;
                case 7: forwardSteps = 8; break;
                case 8: forwardSteps = 9; 
                
            }
        
            switch(backwardStepsLV.getSelectionModel().getSelectedIndex()){
                case 1: backwardSteps = 1; break;
                case 2: backwardSteps = 2; break;
                case 3: backwardSteps = 3; break;
                case 4: backwardSteps = 4; break;
                case 5: backwardSteps = 5; break;
                case 6: backwardSteps = 6; break;
                case 7: backwardSteps = 7; break;
                case 8: backwardSteps = 8; break;
                case 9: backwardSteps = 9;
            }
        }
        
    }
    
    private void initiateMarksMap(){
    
        int key = 100;
        double value = 1.0;
        
        for(;key<=900;key+=10){
            if(value < 0) break;
            marks.put(key, value);
            value -= 1.0/90;
        }
        
    }
    
    private void addCellListeners(){
    
        game.getCartesianPlane().setOnKeyPressed(e->{
        
            byte direction = -1;
            
            
            if(!positionGame && e.getCode()==KeyCode.LEFT)
                direction = 1;          
            else if(!positionGame && e.getCode()==KeyCode.UP)
                direction = 2;
            else if(!positionGame && e.getCode()==KeyCode.DOWN)
                direction = 3;
            else if(!positionGame && e.isControlDown() && e.getCode()==KeyCode.UP)
                direction = 4;
            else if(!positionGame && e.isAltDown() && e.getCode()==KeyCode.DOWN)
                direction = 5;
            else if(!positionGame && e.isAltDown() && e.getCode()==KeyCode.UP)
                direction = 6;
            else if(!positionGame && e.isControlDown() && e.getCode()==KeyCode.DOWN)
                direction = 7;
            else if(!positionGame && e.getCode()==KeyCode.RIGHT)
                direction = 8;
            
            if(!positionGame && direction != -1 && game.getCartesianPlane().isCurrentBallInvisible()){
                //int currentRhythmIndex = music.getCurrentRhythmIndex();
                //Rhythm currentRhythm = music.getRhythm(currentRhythmIndex);
                DirectionBasedAnswer answer = new DirectionBasedAnswer(
                System.currentTimeMillis()-startTime, direction);
                
                player.addAnswer(CartesianPlane.cartesianPlaneNumber, 
                answer,game.getCartesianPlane().getCurrentPortionToTrack());
            }
            
            if(positionGame){
                
                // Deal With A
                if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT1)positionAnswer += "0";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.A)positionAnswer += "0";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT2)positionAnswer += "1";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.A)positionAnswer += "0";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT3)positionAnswer += "2";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.A)positionAnswer += "0";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT4)positionAnswer += "3";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.A)positionAnswer += "0";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT5)positionAnswer += "4";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.A)positionAnswer += "0";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT6)positionAnswer += "5";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.A)positionAnswer += "0";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT7)positionAnswer += "6";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.A)positionAnswer += "0";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT8)positionAnswer += "7";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.A)positionAnswer += "0";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT9)positionAnswer += "8";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.A)positionAnswer += "0";
                
                // Deal With B
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT1)positionAnswer += "0";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.B)positionAnswer += "1";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT2)positionAnswer += "1";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.B)positionAnswer += "1";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT3)positionAnswer += "2";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.B)positionAnswer += "1";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT4)positionAnswer += "3";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.B)positionAnswer += "1";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT5)positionAnswer += "4";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.B)positionAnswer += "1";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT6)positionAnswer += "5";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.B)positionAnswer += "1";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT7)positionAnswer += "6";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.B)positionAnswer += "1";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT8)positionAnswer += "7";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.B)positionAnswer += "1";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT9)positionAnswer += "8";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.B)positionAnswer += "1";
                
                // Deal With C
                if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT1)positionAnswer += "0";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.C)positionAnswer += "2";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT2)positionAnswer += "1";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.C)positionAnswer += "2";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT3)positionAnswer += "2";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.C)positionAnswer += "2";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT4)positionAnswer += "3";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.C)positionAnswer += "2";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT5)positionAnswer += "4";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.C)positionAnswer += "2";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT6)positionAnswer += "5";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.C)positionAnswer += "2";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT7)positionAnswer += "6";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.C)positionAnswer += "2";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT8)positionAnswer += "7";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.C)positionAnswer += "2";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT9)positionAnswer += "8";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.C)positionAnswer += "2";
                
                // Deal With D
                if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT1)positionAnswer += "0";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.D)positionAnswer += "3";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT2)positionAnswer += "1";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.D)positionAnswer += "3";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT3)positionAnswer += "2";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.D)positionAnswer += "3";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT4)positionAnswer += "3";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.D)positionAnswer += "3";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT5)positionAnswer += "4";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.D)positionAnswer += "3";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT6)positionAnswer += "5";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.D)positionAnswer += "3";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT7)positionAnswer += "6";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.D)positionAnswer += "3";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT8)positionAnswer += "7";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.D)positionAnswer += "3";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT9)positionAnswer += "8";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.D)positionAnswer += "3";
                
                // Deal With E
                if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT1)positionAnswer += "0";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.E)positionAnswer += "4";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT2)positionAnswer += "1";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.E)positionAnswer += "4";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT3)positionAnswer += "2";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.E)positionAnswer += "4";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT4)positionAnswer += "3";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.E)positionAnswer += "4";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT5)positionAnswer += "4";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.E)positionAnswer += "4";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT6)positionAnswer += "5";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.E)positionAnswer += "4";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT7)positionAnswer += "6";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.E)positionAnswer += "4";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT8)positionAnswer += "7";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.E)positionAnswer += "4";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT9)positionAnswer += "8";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.E)positionAnswer += "4";
                
                // Deal With F
                if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT1)positionAnswer += "0";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.F)positionAnswer += "5";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT2)positionAnswer += "1";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.F)positionAnswer += "5";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT3)positionAnswer += "2";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.F)positionAnswer += "5";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT4)positionAnswer += "3";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.F)positionAnswer += "5";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT5)positionAnswer += "4";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.F)positionAnswer += "5";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT6)positionAnswer += "5";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.F)positionAnswer += "5";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT7)positionAnswer += "6";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.F)positionAnswer += "5";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT8)positionAnswer += "7";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.F)positionAnswer += "5";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT9)positionAnswer += "8";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.F)positionAnswer += "5";
                
                // Deal With G
                if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT1)positionAnswer += "0";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.G)positionAnswer += "6";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT2)positionAnswer += "1";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.G)positionAnswer += "6";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT3)positionAnswer += "2";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.G)positionAnswer += "6";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT4)positionAnswer += "3";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.G)positionAnswer += "6";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT5)positionAnswer += "4";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.G)positionAnswer += "6";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT6)positionAnswer += "5";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.G)positionAnswer += "6";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT7)positionAnswer += "6";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.G)positionAnswer += "6";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT8)positionAnswer += "7";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.G)positionAnswer += "6";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT9)positionAnswer += "8";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.G)positionAnswer += "6";
                
                // Deal With H
                if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT1)positionAnswer += "0";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.H)positionAnswer += "7";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT2)positionAnswer += "1";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.H)positionAnswer += "7";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT3)positionAnswer += "2";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.H)positionAnswer += "7";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT4)positionAnswer += "3";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.H)positionAnswer += "7";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT5)positionAnswer += "4";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.H)positionAnswer += "7";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT6)positionAnswer += "5";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.H)positionAnswer += "7";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT7)positionAnswer += "6";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.H)positionAnswer += "7";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT8)positionAnswer += "7";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.H)positionAnswer += "7";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT9)positionAnswer += "8";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.H)positionAnswer += "7";
                
                // Deal With I
                if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT1)positionAnswer += "0";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.I)positionAnswer += "8";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT2)positionAnswer += "1";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.I)positionAnswer += "8";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT3)positionAnswer += "2";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.I)positionAnswer += "8";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT4)positionAnswer += "3";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.I)positionAnswer += "8";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT5)positionAnswer += "4";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.I)positionAnswer += "8";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT6)positionAnswer += "5";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.I)positionAnswer += "8";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT7)positionAnswer += "6";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.I)positionAnswer += "8";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT8)positionAnswer += "7";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.I)positionAnswer += "8";
                else if(positionAnswer.isEmpty() && e.getCode()==KeyCode.DIGIT9)positionAnswer += "8";
                else if(positionAnswer.length()==1 && e.getCode()==KeyCode.I)positionAnswer += "8";
                
                if(positionAnswer.length()==2){
                    PositionBasedAnswer answer = new PositionBasedAnswer(
                    System.currentTimeMillis()-startTime, 
                    Byte.parseByte(positionAnswer.substring(0, 1))
                    ,Byte.parseByte(positionAnswer.substring(1)));
                    player.addAnswer(CartesianPlane.cartesianPlaneNumber, 
                    answer,game.getCartesianPlane().getCurrentPortionToTrack());
                    positionAnswer = "";
                }
            }
        });
      
        if(positionGame)
            for(Cell[] arrayOfCells : game.getCartesianPlane().getCoordinates()){
                for(Cell cell : arrayOfCells){
                    cell.setOnTouchPressed(e->{
                        if(positionGame && game.getCartesianPlane().isCurrentBallInvisible()){
                            //int currentRhythmIndex = music.getCurrentRhythmIndex();
                            //Rhythm currentRhythm = music.getRhythm(currentRhythmIndex);
                            PositionBasedAnswer answer = new PositionBasedAnswer(
                            System.currentTimeMillis()-startTime, 
                            ((Cell)e.getSource()).getAtRow() 
                            ,((Cell)e.getSource()).getAtColumn());

                            player.addAnswer(CartesianPlane.cartesianPlaneNumber, answer, 
                            game.getCartesianPlane().getCurrentPortionToTrack());
                        }
                    });
                }
            }
    }
 
    private void initializeAnimation(){
    
        GameHandler handler = new GameHandler();
        Rhythm rhythm = music.getCurrentRhythm();
        
        
         int numberOfKeyFrames = rhythm.getTimes().size();
         
          for(int index = 0; index < numberOfKeyFrames; index++){
              timeline.getKeyFrames().add(
            new KeyFrame(Duration.millis(rhythm.getNextTime()), 
            handler));
          }
    }
    
    private class TimelineTask implements Runnable{
    
        @Override
        public void run(){

            Platform.runLater(new Runnable() { // Run from JavaFX GUI                
                @Override 
                public void run() { 
                    timeline.play();
                } 
            }); 

        }
    
    }
    
    private class MusicTask implements Runnable{
    
        @Override
        public void run(){
        
            Platform.runLater(new Runnable(){
                @Override
                public void run(){
                    music.play();
                }
            });
        }
    }
    
    private class GameHandler implements EventHandler<ActionEvent>{
    
        @Override
        public void handle(ActionEvent e){

            if(game.getCartesianPlane().allPortionsVisited()){
                directionBasedCorrectAnswers.put(CartesianPlane.cartesianPlaneNumber,
                game.getCartesianPlane().retrieveCorrectDirectionBasedAnswers());
                positionBasedCorrectAnswers.put(CartesianPlane.cartesianPlaneNumber,
                game.getCartesianPlane().retrieveCorrectPositionBasedAnswers());

                // Display All Expected Answers.
                //player.grantPositionBasedMarks(positionBasedCorrectAnswers);
                //player.grantDirectionBasedMarks(directionBasedCorrectAnswers);

                CartesianPlane.decreaseTries();
                CartesianPlane.incrementCartesianPlaneNumber();       
               
                if(CartesianPlane.getNumberOfTries()==0)
                    endGame();
                else
                try{
                    
                    startTime = System.currentTimeMillis();

                    
                    switch((byte)(Math.random()*4)){
                        case 0 : game = new ClockwisePointBasedGame(positionGame,width,height,
                               forwardSteps, backwardSteps, music, startTime,movingStrategies); break;
                        case 1 : game = new AntiClockwisePointBasedGame(positionGame,width,height,
                               forwardSteps, backwardSteps, music, startTime,movingStrategies); break;
                        case 2 : game = new IncreasingPointBasedGame(positionGame,width,height,
                               forwardSteps, backwardSteps, music, startTime,movingStrategies); break;
                        case 3 : game = new DecreasingPointBasedGame(positionGame,width,height,
                               forwardSteps, backwardSteps, music, startTime,movingStrategies); break;
                    }
                    addCellListeners();

                    primaryStage.setScene(new Scene(game));


                }catch(Exception ex){
                    ex.printStackTrace();
                }
                
            }

            else if((plane!=Plane.Letters_Plane && 
            player.getNumberOfCartesianPlanesToTrack()==CartesianPlane.cartesianPlaneNumber)){
                endGame();
            }
            
            game.getCartesianPlane().requestFocus();
            /* We add the following condition to make sure 
            switching from one portion to another isn't easily 
            predictable.
            */
            if(game.getCartesianPlane().canTrackOnNextPortion() /*|| 
            game.getCartesianPlane().getNumberOfAllowedMoves()==0*/){
                game.getCartesianPlane().trackOnNextPortion();

            }
            
        }   
    }
    
    private void endGame(){
    
        long minutes;
        long seconds;

        timeline.pause();
        game.pause();
        endTime = System.currentTimeMillis();
        minutes = ((endTime-startTime)/60000);
        seconds = (((endTime-startTime)%60000)/1000);
        System.out.println("Game Period : " + minutes 
        + " minutes\t" + seconds + " seconds.");


        directionBasedCorrectAnswers.put(CartesianPlane.cartesianPlaneNumber,
        game.getCartesianPlane().retrieveCorrectDirectionBasedAnswers());
        positionBasedCorrectAnswers.put(CartesianPlane.cartesianPlaneNumber,
        game.getCartesianPlane().retrieveCorrectPositionBasedAnswers());


        //player.grantPositionBasedMarks(positionBasedCorrectAnswers);
        player.grantDirectionBasedMarks(directionBasedCorrectAnswers);

        //CartesianPlane.cartesianPlaneNumber = 0;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        slider.setValue(INIT_SPEED_FACTOR);
        speedLabel.setText(String.valueOf(INIT_SPEED_FACTOR));
        
        
        speedLabel.textProperty().bind(slider.valueProperty().asString());
        
        typeOfAnswers.setItems(
        FXCollections.observableArrayList(
        "Positions",
        "Directions"));
        
        r1MovingStrategy.setItems(
        FXCollections.observableArrayList(
        "Row By Row",
        "Column By Column"));
        r2MovingStrategy.setItems(
        FXCollections.observableArrayList(
        "Row By Row",
        "Column By Column"));
        r3MovingStrategy.setItems(
        FXCollections.observableArrayList(
        "Row By Row",
        "Column By Column"));
        r4MovingStrategy.setItems(
        FXCollections.observableArrayList(
        "Row By Row",
        "Column By Column"));
        r5MovingStrategy.setItems(
        FXCollections.observableArrayList(
        "Row By Row",
        "Column By Column"));
        r6MovingStrategy.setItems(
        FXCollections.observableArrayList(
        "Row By Row",
        "Column By Column"));
        r7MovingStrategy.setItems(
        FXCollections.observableArrayList(
        "Row By Row",
        "Column By Column"));
        r8MovingStrategy.setItems(
        FXCollections.observableArrayList(
        "Row By Row",
        "Column By Column"));
        
        forwardStepsLV.setItems(
        FXCollections.observableArrayList(
        "Foward only",
        "2 Steps Then Backwards",
        "3 Steps Then Backwards",
        "4 Steps Then Backwards",
        "5 Steps Then Backwards",
        "6 Steps Then Backwards",
        "7 Steps Then Backwards",
        "8 Steps Then Backwards",
        "9 Steps Then Backwards"));
        forwardStepsLV.selectionModelProperty().getValue().select(0);
        
        backwardStepsLV.setItems(
        FXCollections.observableArrayList(
        "No Backward Steps",
        "1 Steps Then Forward",
        "2 Steps Then Forward",
        "3 Steps Then Forward",
        "4 Steps Then Forward",
        "5 Steps Then Forward",
        "6 Steps Then Forward",
        "7 Steps Then Forward",
        "8 Steps Then Forward",
        "9 Steps Then Forward"));
        backwardStepsLV.selectionModelProperty().getValue().select(0);
        
        gameName.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 30));
        musicLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        
        plainRB.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        lettersRB.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        numbersRB.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        allRB.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        
        oneAttemptRB.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        twoAttemptsRB.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        threeAttemptsRB.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        fourAttemptsRB.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        
        beginGameButton.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        speedLabelInfo.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));
        
        initiateSomeDataFields();
        initiateMarksMap();
        
    }    
    
}
