
package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.PositionBasedAnswer;
import com.manikhweschool.patternrecognition.buildingblocks.DirectionBasedAnswer;
import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.Trackable;

import java.util.ArrayList;
import java.util.LinkedList;
import javafx.scene.paint.Color;

public abstract class Region implements Trackable{
    protected ArrayList<Cell> regionCells = new ArrayList<>();
    protected String regionColor = "-fx-background-color:white";
    protected boolean isCurrentPortionToTrack = false;
    protected Cell[][] coordinates;
    protected int numberOfAllowedMoves = 0;
    protected LinkedList<PositionBasedAnswer> positionBasedAnswers;
    protected LinkedList<DirectionBasedAnswer> directionBasedAnswers;
    
    private boolean isVisited = false;
    protected boolean goingForward = true;
    
    protected RegionBall regionBall;
    
    protected byte forwardSteps;
    protected byte backwardSteps;
    
    protected byte initForwardSteps = -1;
    protected byte initBackwardSteps = -1;
    
    
    protected Region(){
    
        setRegionColor(RegionColor.None);
        positionBasedAnswers = new LinkedList<>();
        directionBasedAnswers = new LinkedList<>();
    }
    
    public byte getInitForwardSteps(){return initForwardSteps;}
    public byte getInitBackwardSteps(){ return initBackwardSteps;}
    
    public abstract void fillInCircle();
    
    public boolean hasAtLeastOneCells(){ return !regionCells.isEmpty();}
    
    public void setSteps(byte forwardSteps, byte backwardSteps){
        if(initForwardSteps==-1 && initBackwardSteps==-1){
            this.forwardSteps = forwardSteps;
            initForwardSteps = forwardSteps;
            this.backwardSteps = backwardSteps;
            initBackwardSteps = backwardSteps;
        }
        
    }
    
    public void displaySwitchingInfo(){
    
        
        if(initBackwardSteps >= 1)
            System.out.println("Can Switch? : " + 
            ((backwardSteps==0 && forwardSteps==0)?"Yes\t":"No\t") + 
            "Forward Steps : " + forwardSteps + "\tBackward Steps : " + backwardSteps);
        else
            System.out.println("Can Switch? : Yes\tForward Steps : 1" + 
            "\tBackward Steps : None");
        
    }
    
    public void updateRegion(){
    
        // Portion Switching Condition 3
        /* We only switch when the backward steps for the next potion is zero,
           given that init backward steps isn't zero. If it is by any means, then
           this condition isn't considered.*/
        if((getForwardSteps()==getInitForwardSteps()
        && getBackwardSteps()==getInitBackwardSteps())){
            regionBall.fillBallColor();
           
        }

        if(getForwardSteps()==0
        && getBackwardSteps()>0)
            regionBall.radiusYProperty().bind(
            coordinates[0][0].prefWidthProperty().add(
            coordinates[0][0].prefHeightProperty()).divide(16));
        
        else{
            regionBall.radiusYProperty().bind(
            coordinates[0][0].prefWidthProperty().add(
            coordinates[0][0].prefHeightProperty()).divide(8));
           
        }
        
        if(goingForward){
            if(!decreaseForwardSteps()){
                goingForward = false; 
                decreaseBackwardSteps();
                    
            }
            
        }
        else{
            if(!decreaseBackwardSteps()){
                setForwardSteps((byte)(getInitForwardSteps()));
                setBackwardSteps((byte)(getInitBackwardSteps()));
                goingForward = true;
                decreaseForwardSteps();
            }
            
        }
        
        
        
    }
    
    public boolean isBallStartingOver(){ 
        return (backwardSteps==0 && forwardSteps==0) || 
                (initBackwardSteps==-1);
    }
    
    public void setBackwardSteps(byte backwardSteps){
        if(backwardSteps > 0){
            this.backwardSteps = backwardSteps;
            initBackwardSteps = backwardSteps;
        }
    }
    
    public void setForwardSteps(byte forwardSteps){
        if(forwardSteps>=0){
            this.forwardSteps = forwardSteps;
            initForwardSteps = forwardSteps;
        }
    }
    
    public byte getForwardSteps(){ return forwardSteps;}
    public byte getBackwardSteps(){ return backwardSteps;}
    
    public boolean increaseForwardSteps(){ 
        if(forwardSteps+1<=initForwardSteps){
            forwardSteps++;
            return true;
        }
        return false;
    }
    public boolean increaseBackwardSteps(){ 
        if(backwardSteps+1<=initBackwardSteps){
            backwardSteps++;
            return true;
        }
        return false;
    }
    
    public boolean decreaseForwardSteps(){ 
        if(forwardSteps-1>=0){
            forwardSteps--;
            return true;
        }
        return false;
    }
    public boolean decreaseBackwardSteps(){ 
        if(backwardSteps-1>=0){
            backwardSteps--;
            return true;
        }
        return false;
    }
    
    public void decreaseNumberOfAllowedMoves(){
        if(numberOfAllowedMoves>0)
            numberOfAllowedMoves--;
    }
    
    public int getNumberOfAllowedMoves(){
        return numberOfAllowedMoves;
    }
    
    public void setNumberOfAllowedMoves(){
        numberOfAllowedMoves = regionCells.size();
        numberOfAllowedMoves *= 2;
        
        numberOfAllowedMoves += 5+(byte)(Math.random()*6);
    }
    
    public abstract void cartegorizeCells();
    
    public boolean getIsCurrentPortionToTrack(){
    
        return isCurrentPortionToTrack;
    }
    
    public void setIsVisited(boolean isVisited){
        this.isVisited = isVisited;
    }
    
    public boolean getIsVisited(){ return isVisited;}
    
    public void setIsCurrentPortionToTrack(boolean yesOrNo){
        
        isCurrentPortionToTrack = yesOrNo;
    }
    
    @Override
    public void addAnswer(PositionBasedAnswer answer){
        positionBasedAnswers.add(answer);
    }
    
    @Override
    public void addAnswer(DirectionBasedAnswer answer){
        directionBasedAnswers.add(answer);
    }
    
    @Override
    public LinkedList<PositionBasedAnswer> getPositionBasedAnswers(){ return positionBasedAnswers;}
    @Override
    public LinkedList<DirectionBasedAnswer> getDirectionBasedAnswers(){ return directionBasedAnswers;}
    
    public void setRegionColor(RegionColor regionColor){
        
        switch(regionColor){
            case Red : this.regionColor = "-fx-background-color:red"; break;
            case Green : this.regionColor = "-fx-background-color:green"; break;
            case Blue : this.regionColor = "-fx-background-color:blue"; break;
            case Light_Skyblue : this.regionColor = "-fx-background-color:#87CEFA"; break;   
            case Magenta : this.regionColor = "-fx-background-color:#FF00FF"; break;
            case Salmon : this.regionColor = "-fx-background-color:#FA8072"; break;
            case Medium_Violetred : this.regionColor = "-fx-background-color:#C71585"; break;
            case Burlywood : this.regionColor = "-fx-background-color:#DEB887"; break;
            case Deep_Pink : this.regionColor = "-fx-background-color:#ff1493"; break;
            case Chartreuse : this.regionColor = "-fx-background-color:#7FFF00"; break;
            case Maroon : this.regionColor = "-fx-background-color:#800000"; break;  
            case Violet : this.regionColor = "-fx-background-color:#EE82EE"; break;
            case None : this.regionColor = "-fx-background-color:white"; break;
        } 
        
        for(Cell cell : regionCells)
            cell.setStyle(this.regionColor);
    }
    
    public void setRegionBall(RegionBall regionBall){ this.regionBall = regionBall;}
    public RegionBall getRegionBall(){ return regionBall;}
    
    public ArrayList<Cell> getRegionCells(){ return regionCells;}
    
    public void setCoordinates(Cell[][] coordinates){ this.coordinates = coordinates;}
    public Cell[][] getCoordinates(){ return coordinates;}
    
    public boolean exist(){ return regionCells.size()>1;}
    public void addCell(Cell cell){ regionCells.add(cell);}
    
    
    public void increaseCurrentRow(){
    
        if(regionBall.getCurrentRow()+1<=8)
            regionBall.setCurrentRow(regionBall.getCurrentRow()+1);
    }
    
    public void decreaseCurrentRow(){
    
        if(regionBall.getCurrentRow()-1>=0)
            regionBall.setCurrentRow(regionBall.getCurrentRow()-1);
    }
    
    public void increaseCurrentColumn(){
    
        if(regionBall.getCurrentColumn()+1<=8)
            regionBall.setCurrentColumn(regionBall.getCurrentColumn()+1);
    }
    
    public void decreaseCurrentColumn(){
    
        if(regionBall.getCurrentColumn()-1>=0)
            regionBall.setCurrentColumn(regionBall.getCurrentColumn()-1);
    }
    
    public Cell getRandomCell(){
    
        if(exist())
            return regionCells.get((int)(Math.random()*regionCells.size()));
        return null;
    }
}
