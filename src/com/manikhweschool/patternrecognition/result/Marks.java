package com.manikhweschool.patternrecognition.result;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public abstract class Marks extends StackPane{
    
    protected byte unansweredNoOfSteps;
    protected boolean incorrectInput = false;
    
    private Label gameEndMessage1;
    protected Label gameEndMessage2;
    protected Label gameEndMessage3;
    
    protected VBox gameOverMessagesBox = new VBox();
    
    protected Marks(){
        unansweredNoOfSteps = -1;
        gameEndMessage1 = new Label("Game Over");
        gameEndMessage2 = new Label();
        gameEndMessage3 = new Label();
        setStyle("-fx-background-color: brown; -fx-border-color: white");
        gameEndMessage1.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 36));
        gameOverMessagesBox.setAlignment(Pos.CENTER);
        getChildren().add(gameEndMessage1);
        gameEndMessage2.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 36));
        gameEndMessage3.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 36));
        
        gameOverMessagesBox.getChildren().add(gameEndMessage1);
    }
    
    public boolean isIncorrectInput(){ return incorrectInput;}
    public abstract void setIncorrectInput(boolean incorrectInput);
    
    public byte getUnansweredNoOfSteps(){ return unansweredNoOfSteps;}
    
    public void increaseNoOfStepsExceeded(){
        
        unansweredNoOfSteps++;
        if(unansweredNoOfSteps==1){
           gameEndMessage2.setText("A STEP WAS MISSED");
           if(!gameOverMessagesBox.getChildren().contains(gameEndMessage2))
                gameOverMessagesBox.getChildren().add(gameEndMessage2);
           
           if(!getChildren().contains(gameOverMessagesBox))
            getChildren().add(gameOverMessagesBox);
        }
    }
    
    public void resetUnansweredNoOfSteps(){ unansweredNoOfSteps = -1;}
    
    public void win(){
    
        gameEndMessage3.setText("YOU WON");
        
        if(!gameOverMessagesBox.getChildren().contains(gameEndMessage3))
            gameOverMessagesBox.getChildren().add(gameEndMessage3);
        
        if(!getChildren().contains(gameOverMessagesBox))
            getChildren().add(gameOverMessagesBox);
    }
    
}
