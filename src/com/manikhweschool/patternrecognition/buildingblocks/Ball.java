package com.manikhweschool.patternrecognition.buildingblocks;

import javafx.scene.paint.Color;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.*;
import javafx.scene.shape.Ellipse;

public class Ball extends Ellipse implements Cloneable{

    protected int currentRow;
    protected int currentColumn;
    protected boolean hasFaded = false;
    protected boolean isGoingForward = true;
    protected Color ballColor;
	
    private DoubleProperty deltaX = new SimpleDoubleProperty(20);
    private DoubleProperty deltaY = new SimpleDoubleProperty(20);
	
        protected Ball(
        double radius, int currentRow, 
        int currentColumn) throws Exception{
            this(Color.BLACK,radius, 1, 1, currentRow, currentColumn);
        }
        
        protected Ball(Color color, 
        double radius, double deltaX, 
        double deltaY, int currentRow, int currentColumn) 
        throws Exception{
	
                setRadiusX(radius);
                setRadiusY(radius);
                ballColor = color;
                setFill(null);
                setStroke(Color.BLACK);
                this.deltaX = new SimpleDoubleProperty(deltaX);
                this.deltaY = new SimpleDoubleProperty(deltaY);
                radiusXProperty().bind((this.deltaX.get() < this.deltaY.get())? 
                deltaXProperty().subtract(2):deltaYProperty().subtract(2));
                radiusYProperty().bind((this.deltaX.get() < this.deltaY.get())? 
                deltaXProperty().subtract(2):deltaYProperty().subtract(2));
                 
            if(!(currentRow <= 8 && currentRow >= 0))
                throw new Exception("Make sure current row is between 0-8.");
            this.currentRow = currentRow;
            
            if(!(currentColumn <= 8 && currentColumn >= 0))
                throw new Exception("Make sure current column is between 0-8.");
            this.currentColumn = currentColumn;
            /*
             if(move == null)
                throw new Exception("Make sure you provided a move.");
            this.move = move;
            move.fillDirections(currentRow, currentColumn);*/
	}
    
    public void fillBallColor(){setFill(ballColor);}
        
    public void setIsGoingForward(boolean isGoingForward){
        
        this.isGoingForward = isGoingForward;
        if(!isGoingForward){
            setRadiusY(getRadiusY()/2);
        }
        else{
            setRadiusY(getRadiusY()*2);
        }
        radiusYProperty().bind((this.deltaX.get() < this.deltaY.get())? 
        deltaXProperty().subtract(2):deltaYProperty().subtract(2));  
    }
        
    public String getCurrentPointString(){
            
        return "(" + getCenterX() + "," + getCenterY() + ")";
    }
        
    public void setHasFaded(boolean hasFaded){
        this.hasFaded = hasFaded;
    }
    
    public boolean getHasFaded(){ return hasFaded;}

    public void setCurrentRow(int newRow){ currentRow = newRow;}
    public int getCurrentRow(){ return currentRow;}
    public void incrementRow(){ currentRow++;}
    public void decrementRow(){ currentRow--;}
    
    public void setCurrentColumn(int newColumn){ currentColumn = newColumn;}
    public int getCurrentColumn(){ return currentColumn;}
     public void incrementColumn(){ currentColumn++;}
    public void decrementColumn(){ currentColumn--;}
    
    @Override
    public Object clone() throws CloneNotSupportedException{
    
        return super.clone();
    }
	
	/** Value getter method */
        public double getDeltaX() { return deltaX.get(); }
        /** Value getter method */
        public double getDeltaY() { return deltaY.get(); }
        /** Value setter method */ 
        public void setDeltaX(double deltaX) {  this.deltaX = new SimpleDoubleProperty(deltaX);}
        /** Value setter method */ 
        public void setDeltaY(double deltaY) {  this.deltaY = new SimpleDoubleProperty(deltaY);}
        /** Property getter method */ 
        public DoubleProperty deltaXProperty() { return deltaX; }
        /** Property getter method */ 
        public DoubleProperty deltaYProperty() { return deltaY; }
}
