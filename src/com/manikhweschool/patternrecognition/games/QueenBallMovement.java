package com.manikhweschool.patternrecognition.games;

import com.manikhweschool.patternrecognition.buildingblocks.CartesianPlane;
import com.manikhweschool.patternrecognition.result.DirectionBasedAnswer;
import com.manikhweschool.patternrecognition.result.PositionBasedAnswer;
import com.manikhweschool.patternrecognition.Rhythm;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class QueenBallMovement extends MovementTask{
    
    protected CartesianPlane cartesianPlane;
    
     public QueenBallMovement(CartesianPlane cartesianPlane, Rhythm rhythm, long startTime){
         super(rhythm, startTime);
         this.cartesianPlane = cartesianPlane;
         initializeAnimation();
     }
     
     @Override
     protected void initializeAnimation(){
        
        int numberOfKeyFrames = rhythm.getTimes().size();
        EventHandler handler;
        
        int visibleNumberOfSteps = 10 + (int)(Math.random()*10);
        int invisibleNumberOfSteps = 10 + (int)(Math.random()*10);
        
        int numberOfFadeInSteps = 5;
        int numberOfFadeOutSteps = 5;
        
        for(int index = 0; index < numberOfKeyFrames; index++){
            
            if(visibleNumberOfSteps==0 && 
            invisibleNumberOfSteps==0 && 
            numberOfFadeInSteps==0 &&
            numberOfFadeOutSteps==0){
                visibleNumberOfSteps = 10 + (int)(Math.random()*10);
                invisibleNumberOfSteps = 10 + (int)(Math.random()*10);

                numberOfFadeInSteps = 5;
                numberOfFadeOutSteps = 5;
            }
            
            handler = new PointBasedMovementHandler();
            
            if(visibleNumberOfSteps > 0){ 
                visibleNumberOfSteps--;
            }
            else if(numberOfFadeInSteps > 0){
                handler = new FadeInHandler();
                numberOfFadeInSteps--;
            }
            else if(invisibleNumberOfSteps > 0){
                invisibleNumberOfSteps--;
            }
            else if(numberOfFadeOutSteps > 0){
                handler = new FadeOutHandler();
                numberOfFadeOutSteps--;
            }
            
            timeline.getKeyFrames().add(
            new KeyFrame(Duration.millis(rhythm.getNextTime()), 
            handler));
            
        }
        
        
    }
     
     private class FadeInHandler implements EventHandler<ActionEvent> {
    
        @Override
        public void handle(ActionEvent e){
            
            if(cartesianPlane.isPathOnTrack() && cartesianPlane.getBall().getOpacity()>0){
                cartesianPlane.getBall().setOpacity(cartesianPlane.getBall().getOpacity()-0.2);
                if(cartesianPlane.getBall().getOpacity()<0.2)
                    hasReachZero = true;
            }
            
            movePoint();
           
        }
    }
    
     private class FadeOutHandler implements EventHandler<ActionEvent> {
    
        @Override
        public void handle(ActionEvent e){
            
            if(cartesianPlane.isPathOnTrack() && cartesianPlane.getBall().getOpacity()<1){
                cartesianPlane.getBall().setOpacity(cartesianPlane.getBall().getOpacity()+0.2);
                if(hasReachZero && cartesianPlane.getBall().getOpacity()>0.8)
                    hasReachOneFromZero = true;
                    
            }
            else if(cartesianPlane.getBall().getOpacity()!=1)
                cartesianPlane.getBall().setOpacity(1);
            movePoint();
        }
    }
    
    private class PointBasedMovementHandler implements EventHandler<ActionEvent>{
    
        @Override
        public void handle(ActionEvent e){

            movePoint(); 
            
            if(cartesianPlane.isCurrentBallInvisible() && 
            cartesianPlane.getMusic().getCurrentRhythm().getRhythmFit())
                cartesianPlane.getPlayer().getDirectionMarks().increaseNoOfStepsExceeded();
                
            
        } 
        
    }
     
     private void movePoint(){

        direction  = cartesianPlane.getBall().getMove().getNextDirection();
        
        switch(direction) {
            case 1 :            
                    moveLeft();
                break;
            case 2 :          
                    moveUp();
                break;
            case 3 :            
                    moveDown();
                break;
            case 4 :             
                    moveTopLeft();
                break;
            case 5 :             
                    moveBottomRight();
                break;
            case 6 :             
                    moveTopRight();
                break;
            case 7 :             
                   moveBottomLeft();
                break;
            default :            
                   moveRight();
            }
        cartesianPlane.decreaseNumberOfAllowedMoves();
        
        if(hasReachZero && hasReachOneFromZero){
            stepsAfterFading--;
            if(stepsAfterFading==0){
                cartesianPlane.getBall().setHasFaded(true);
                
            }
        }
        
        if(cartesianPlane.isPathOnTrack() && cartesianPlane.getBall().getOpacity()<0.2){
            cartesianPlane.addAnswer(new DirectionBasedAnswer(System.currentTimeMillis()
            -startTime, (byte)direction));
            cartesianPlane.addAnswer(new PositionBasedAnswer(System.currentTimeMillis()
            -startTime, (byte)cartesianPlane.getCurrentRow(),
            (byte)cartesianPlane.getCurrentColumn()));
        }
    }
     
     @Override
     public void moveUp() {
        
        if(cartesianPlane.getCurrentRow()>0)
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().remove(
            cartesianPlane.getBall());
        
        if(cartesianPlane.getCurrentRow()-1 >= 0){
            cartesianPlane.decreaseCurrentRow();
            cartesianPlane.getBall().decrementRow();
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().add(cartesianPlane.getBall());
        }
        
    }
	
    @Override
    public void moveTopRight() {
	
        if(cartesianPlane.getCurrentColumn()<8 && cartesianPlane.getCurrentRow()>0)
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().remove(
            cartesianPlane.getBall());
        
        if(cartesianPlane.getCurrentColumn()+1 <= 8 && cartesianPlane.getCurrentRow()-1 >= 0){
            cartesianPlane.increaseCurrentColumn();
            cartesianPlane.decreaseCurrentRow();
            cartesianPlane.getBall().incrementColumn();
            cartesianPlane.getBall().decrementRow();
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().add(cartesianPlane.getBall());
        }
    }
	
    @Override
    public void moveTopLeft() {
		
        if(cartesianPlane.getCurrentColumn()>0 && cartesianPlane.getCurrentRow()>0)
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().remove(
            cartesianPlane.getBall());
        
        if(cartesianPlane.getCurrentColumn()-1 >= 0 && cartesianPlane.getCurrentRow()-1 >= 0){
            
            cartesianPlane.decreaseCurrentRow();
            cartesianPlane.decreaseCurrentColumn();
                 
            cartesianPlane.getBall().decrementRow();
            cartesianPlane.getBall().decrementColumn();
   
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().add(
            cartesianPlane.getBall());
        }
    }
	
    @Override
    public void moveDown() {
            
        if(cartesianPlane.getCurrentRow()<8)
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().remove(
            cartesianPlane.getBall());
        
        if(cartesianPlane.getCurrentRow()+1 <= 8){
            cartesianPlane.increaseCurrentRow();
            cartesianPlane.getBall().incrementRow();
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().add(
            cartesianPlane.getBall());            
        }  
    }
	
    @Override
    public void moveBottomRight() {
		
        if(cartesianPlane.getCurrentRow()<8 && cartesianPlane.getCurrentColumn()<8)
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().remove(
            cartesianPlane.getBall());
        
        if(cartesianPlane.getCurrentRow()+1 <= 8 && cartesianPlane.getCurrentColumn()+1 <= 8){
            cartesianPlane.increaseCurrentRow();
            cartesianPlane.increaseCurrentColumn();
            cartesianPlane.getBall().incrementRow();
            cartesianPlane.getBall().incrementColumn();
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().add(
            cartesianPlane.getBall());
        }
    }
	
    @Override
    public void moveBottomLeft() {
		
        if(cartesianPlane.getCurrentColumn()>0 && cartesianPlane.getCurrentRow()<8)
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().remove(
            cartesianPlane.getBall());
        
        if(cartesianPlane.getCurrentColumn()-1 >= 0 && cartesianPlane.getCurrentRow()+1 <= 8){
            cartesianPlane.increaseCurrentRow();
            cartesianPlane.decreaseCurrentColumn();
            cartesianPlane.getBall().incrementRow();
            cartesianPlane.getBall().decrementColumn();
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().add(
            cartesianPlane.getBall());
        }
    }
	
    @Override
    public void moveLeft() {
        
        if(cartesianPlane.getCurrentColumn()>0)
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().remove(
            cartesianPlane.getBall());
        
         if(cartesianPlane.getCurrentColumn()-1 >= 0){
             cartesianPlane.decreaseCurrentColumn();
             cartesianPlane.getBall().decrementColumn();
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().add(
            cartesianPlane.getBall());
         }
    }
	
    @Override
    public void moveRight() {
        
        if(cartesianPlane.getCurrentColumn()<8)
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().remove(
            cartesianPlane.getBall());
        
        if(cartesianPlane.getCurrentColumn()+1 <= 8){
            cartesianPlane.increaseCurrentColumn();
            cartesianPlane.getBall().incrementColumn();
            cartesianPlane.getCoordinates()[cartesianPlane.getCurrentRow()]
            [cartesianPlane.getCurrentColumn()].getChildren().add(
            cartesianPlane.getBall());
        }
    }
}
