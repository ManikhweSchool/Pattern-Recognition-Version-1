package com.manikhweschool.patternrecognition.games;

import com.manikhweschool.patternrecognition.result.PositionBasedAnswer;
import com.manikhweschool.patternrecognition.result.DirectionBasedAnswer;
import com.manikhweschool.patternrecognition.Rhythm;
import com.manikhweschool.patternrecognition.buildingblocks.CartesianPlane;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.space.Region;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class RegionBallMovement  extends MovementTask{
    
    private Region region;
    private Paint paint;
    private boolean isPaintInitialized = false;
    
    public RegionBallMovement(Region region, Rhythm rhythm, long startTime){
        super(rhythm, startTime);
        this.region = region;
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
            
            if(region.getIsCurrentPortionToTrack() && region.getRegionBall().getOpacity()>0){
                region.getRegionBall().setOpacity(region.getRegionBall().getOpacity()-0.2);
                if(region.getRegionBall().getOpacity()<0.2){
                    hasReachZero = true;
                }
                    
            }

            movePoint();
            
        }
    }
    
     private class FadeOutHandler implements EventHandler<ActionEvent> {
    
        @Override
        public void handle(ActionEvent e){
            
            if(region.getIsCurrentPortionToTrack() && region.getRegionBall().getOpacity()<1){
                region.getRegionBall().setOpacity(region.getRegionBall().getOpacity()+0.2);
                if(hasReachZero && region.getRegionBall().getOpacity()>0.8)
                    hasReachOneFromZero = true;
            }
                
            movePoint();
        }
    }
     
    private class PointBasedMovementHandler implements EventHandler<ActionEvent>{
        
        
        @Override
        public void handle(ActionEvent e){
            
            if(!isPaintInitialized){
                paint = region.getRegionBall().getFill();
                isPaintInitialized = true;
            }
            
            if(CartesianPlane.cartesianPlaneNumber==2){
                if(region.getIsCurrentPortionToTrack()){
                    paint = region.getRegionBall().getFill();
                    region.getRegionBall().setFill(null);   
                }
                else{
                    region.getRegionBall().fillBallColor();
                }
            }
            
            movePoint();    
            
            
            if(!region.getRegionBall().isVisible() && 
            region.getMusic().getCurrentRhythm().getRhythmFit())
                region.getPlayer().getDirectionMarks().increaseNoOfStepsExceeded();
            
        } 
        
    }
     
     private void movePoint(){
        
         
         
        direction  = region.getRegionBall().getMove().getNextDirection();
        
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
            case 8 :            
                   moveRight();
        }
        
        // Portion Switching Condition 1.
        // The method updates the minimum no of steps required.
        region.decreaseNumberOfAllowedMoves();
        
        
        // Portion Switching Condition 2.
        if(hasReachZero && hasReachOneFromZero){
            stepsAfterFading--;
            if(stepsAfterFading==0){
                region.getRegionBall().setHasFaded(true);
            }
        }
        
        // Portion Switching Condition 3.
        region.updateRegion();
        
        if(region.getIsCurrentPortionToTrack() && region.getRegionBall().getOpacity()<0.2){
            region.addAnswer(new DirectionBasedAnswer(System.currentTimeMillis()
            -startTime, (byte)direction));
            region.addAnswer(new PositionBasedAnswer(System.currentTimeMillis()
            -startTime, (byte)region.getRegionBall().getCurrentRow(),
            (byte)region.getRegionBall().getCurrentColumn()));
        }
    }
     
    @Override
     public void moveUp() {
         
         if(region.getRegionBall().getCurrentRow()>0)
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().remove(
            region.getRegionBall());
        
        if(region.getRegionBall().getCurrentRow()-1 >= 0){
            region.decreaseCurrentRow();
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().add(region.getRegionBall());
        }
     }
     
    @Override
     public void moveDown() {
         
         if(region.getRegionBall().getCurrentRow()<8)
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().remove(
            region.getRegionBall());
        
        if(region.getRegionBall().getCurrentRow()+1 <= 8){
            region.increaseCurrentRow();
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().add(
            region.getRegionBall());            
        } 
     }
     
     @Override
     public void moveRight() {
         
         if(region.getRegionBall().getCurrentColumn()<8)
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().remove(
            region.getRegionBall());
        
        if(region.getRegionBall().getCurrentColumn()+1 <= 8){
            region.increaseCurrentColumn();
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().add(
            region.getRegionBall());
        }
     }
     
    @Override
     public void moveLeft() {

         if(region.getRegionBall().getCurrentColumn()>0)
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().remove(
            region.getRegionBall());
        
         if(region.getRegionBall().getCurrentColumn()-1 >= 0){
            region.decreaseCurrentColumn();
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().add(
            region.getRegionBall());
         }
     }
     
    @Override
     public void moveBottomRight() {
         
         if(region.getRegionBall().getCurrentRow()<8 && region.getRegionBall().getCurrentColumn()<8)
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().remove(
            region.getRegionBall());
        
        if(region.getRegionBall().getCurrentRow()+1 <= 8 && region.getRegionBall().getCurrentColumn()+1 <= 8){
            region.increaseCurrentRow();
            region.increaseCurrentColumn();
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().add(
            region.getRegionBall());
        }
     }
     
    @Override
     public void moveBottomLeft() {
         
         if(region.getRegionBall().getCurrentColumn()>0 && region.getRegionBall().getCurrentRow()<8)
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().remove(
            region.getRegionBall());
        
        if(region.getRegionBall().getCurrentColumn()-1 >= 0 && region.getRegionBall().getCurrentRow()+1 <= 8){
            region.increaseCurrentRow();
            region.decreaseCurrentColumn();
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().add(
            region.getRegionBall());
        }
     }
     
     @Override
     public void moveTopRight() {
         
         if(region.getRegionBall().getCurrentColumn()<8 && region.getRegionBall().getCurrentRow()>0)
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().remove(
            region.getRegionBall());
        
        if(region.getRegionBall().getCurrentColumn()+1 <= 8 && region.getRegionBall().getCurrentRow()-1 >= 0){
            region.increaseCurrentColumn();
            region.decreaseCurrentRow();
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().add(region.getRegionBall());
        }
     }
     
    @Override
     public void moveTopLeft() {

         if(region.getRegionBall().getCurrentColumn()>0 && region.getRegionBall().getCurrentRow()>0)
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().remove(
            region.getRegionBall());
        
        if(region.getRegionBall().getCurrentColumn()-1 >= 0 && 
        region.getRegionBall().getCurrentRow()-1 >= 0){
            
            region.decreaseCurrentRow();
            region.decreaseCurrentColumn();
   
            region.getCoordinates()[region.getRegionBall().getCurrentRow()]
            [region.getRegionBall().getCurrentColumn()].getChildren().add(
            region.getRegionBall());
        }
     }
}
