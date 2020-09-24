package com.manikhweschool.patternrecognition.moves;

import java.util.ArrayList;

public abstract class Move{

    protected int currentDirectionIndex = 0;
    protected ArrayList<Integer> directions = new ArrayList<>();
    //protected boolean isActive;
        
    public abstract int getNextDirection();
        
    public int getCurrentDirectionIndex(){
        return currentDirectionIndex;
    }
        
    //public void setIsActive(boolean isActive){ this.isActive = isActive;}
    
    @Override
    public String toString(){
        return directions.toString();
    }
    
        
    protected static int getOppositeDirection(int direction){
        
        int oppositeDirection;
        
        switch(direction){
            case 1 : oppositeDirection = 8; break;
            case 2 : oppositeDirection = 3; break;
            case 3 : oppositeDirection = 2; break;
            case 4 : oppositeDirection = 5; break;
            case 5 : oppositeDirection = 4; break;
            case 6 : oppositeDirection = 7; break;
            case 7 : oppositeDirection = 6; break;
            default : oppositeDirection = 1;
        }
        
        return oppositeDirection;
    }
    
        
        protected void fillHelper(byte forwardSteps, byte backwardSteps){
    
        if(forwardSteps==1 && backwardSteps==0)
            return;
            
        ArrayList<Integer> newDirections = new ArrayList<>();
        int currentIndex = 0;
        
        while(currentIndex < directions.size()){
               
            for(int numberOfTimes = 1; numberOfTimes<=forwardSteps;numberOfTimes++){
                if(currentIndex<directions.size())
                    newDirections.add(directions.get(currentIndex));
                else
                    break;
                if(currentIndex+1<=directions.size())
                    currentIndex++;
                else
                    break;
            }
               
            for(int numberOfTimes = 1; numberOfTimes <= backwardSteps;numberOfTimes++){ 
                if(currentIndex==directions.size())
                    break; 
                newDirections.add(getOppositeDirection(directions.get(currentIndex-1)));
                    
                if(currentIndex-1>=0)
                    currentIndex--;
                else
                    break;
           }
        }

        directions.clear();
        directions.addAll(newDirections);
    }
}
