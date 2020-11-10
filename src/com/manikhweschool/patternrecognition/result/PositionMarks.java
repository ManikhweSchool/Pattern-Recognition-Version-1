package com.manikhweschool.patternrecognition.result;

import com.manikhweschool.patternrecognition.buildingblocks.Location;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class PositionMarks extends Marks{
    
    protected Map<Integer,Map<Location, LinkedList<PositionBasedAnswer>>> 
    positionBasedCorrectAnswers;
    protected ArrayList<Map<Location,LinkedList<PositionBasedAnswer>>> 
    positionBasedAnswers;
    
    public PositionMarks(){
    
        setWidthAndHeight(600,600);
    }
    
    protected PositionMarks(Map<Integer,Map<Location, LinkedList<PositionBasedAnswer>>> 
    positionBasedCorrectAnswers,ArrayList<Map<Location,LinkedList<PositionBasedAnswer>>> 
    positionBasedAnswers, double width, double height){
        this.positionBasedAnswers = positionBasedAnswers;
        this.positionBasedCorrectAnswers = positionBasedCorrectAnswers;
        
        setPrefSize(width,height);
        setMinSize(width, height);
    }
    
    @Override
    public void setIncorrectInput(boolean incorrectInput){
        this.incorrectInput = incorrectInput;
        if(incorrectInput){
           gameEndMessage2.setText("WRONG POSITION INPUT");
           gameOverMessagesBox.getChildren().add(gameEndMessage2);
           getChildren().add(gameOverMessagesBox);
        }
    }
    
    public void setWidthAndHeight(double width, double height){
    
        setPrefSize(width,height);
        setMinSize(width, height);
    }
    
    public void setPositionBasedCorrectAnswers(Map<Integer,Map<Location, LinkedList<PositionBasedAnswer>>> 
    positionBasedCorrectAnswers){
        this.positionBasedCorrectAnswers = positionBasedCorrectAnswers;
    }
    
    public void setPositionBasedAnswers(ArrayList<Map<Location,LinkedList<PositionBasedAnswer>>> 
    positionBasedAnswers){
        this.positionBasedAnswers = positionBasedAnswers;
    }
}
