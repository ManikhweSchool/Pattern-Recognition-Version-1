package com.manikhweschool.patternrecognition.result;

import com.manikhweschool.patternrecognition.buildingblocks.Location;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;


public class DirectionMarks extends Marks{
    
    protected Map<Integer,Map<Location, LinkedList<DirectionBasedAnswer>>> 
    directionBasedCorrectAnswers;
    protected ArrayList<Map<Location,LinkedList<DirectionBasedAnswer>>> 
    directionBasedAnswers;
    
    public DirectionMarks(){
    
        setWidthAndHeight(600,600);
    }
    
    public DirectionMarks(Map<Integer,Map<Location, LinkedList<DirectionBasedAnswer>>> 
    directionBasedCorrectAnswers,ArrayList<Map<Location,LinkedList<DirectionBasedAnswer>>> 
    directionBasedAnswers, double width, double height){
        this.directionBasedAnswers = directionBasedAnswers;
        this.directionBasedCorrectAnswers = directionBasedCorrectAnswers;
        
        setPrefSize(width,height);
        setMinSize(width, height);
    }
    
    public void setWidthAndHeight(double width, double height){
    
        setPrefSize(width,height);
        setMinSize(width, height);
    }
    
    @Override
    public void setIncorrectInput(boolean incorrectInput){
        this.incorrectInput = incorrectInput;
        if(incorrectInput){
           gameEndMessage2.setText("WRONG DIRECTION INPUT");
           gameOverMessagesBox.getChildren().add(gameEndMessage2);
           getChildren().add(gameOverMessagesBox);
        }
    }
    
    public void setDirectionBasedCorrectAnswers(Map<Integer,Map<Location, LinkedList<DirectionBasedAnswer>>> 
    directionBasedCorrectAnswers){
        this.directionBasedCorrectAnswers = directionBasedCorrectAnswers;
    }
    
    public void setDirectionBasedAnswers(ArrayList<Map<Location,LinkedList<DirectionBasedAnswer>>> 
    directionBasedAnswers){
        this.directionBasedAnswers = directionBasedAnswers;
    }
}
