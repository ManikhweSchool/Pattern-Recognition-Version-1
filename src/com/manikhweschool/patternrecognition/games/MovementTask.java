package com.manikhweschool.patternrecognition.games;

import com.manikhweschool.patternrecognition.Rhythm;
import javafx.animation.Timeline;
import javafx.application.Platform;

public abstract class MovementTask  implements Runnable{
   
    protected Rhythm rhythm;
    protected Timeline timeline = new Timeline();
    protected int direction;
    protected long startTime;
    
    protected boolean hasReachZero = false;
    protected boolean hasReachOneFromZero = false;
    protected byte stepsAfterFading = (byte)(3+(Math.random()*3));
    
    protected MovementTask(Rhythm rhythm, long startTime){
        this.rhythm = rhythm;
        this.startTime = startTime;
    }
    
    protected abstract void initializeAnimation();
    
    public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract void moveTopRight();
    public abstract void moveTopLeft();
    public abstract void moveBottomRight();
    public abstract void moveBottomLeft();

    @Override
    public void run(){
    
        Platform.runLater(new Runnable() { // Run from JavaFX GUI                
            @Override 
            public void run() { 
                timeline.play();
            } 
        }); 
       
    }
    
    public void setAutoReverse(boolean autoReverse){ timeline.setAutoReverse(autoReverse);}
    public boolean getAutoReverse(){ return timeline.autoReverseProperty().get();}
    
    public void setCycleCount(int cycleCount){ timeline.setCycleCount(cycleCount);}
    public int getCycleCount(){ return timeline.getCycleCount();}
    
    public void pause(){ timeline.pause();}
}
