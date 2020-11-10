package com.manikhweschool.patternrecognition.moves;

import java.util.ArrayList;

public abstract class QueenMove extends Move{

        protected ArrayList<Boolean> isOppositeDirection = new ArrayList<>();
        protected boolean isCurrentIndexInitiated = false;
        
        public abstract void fillDirections(int startingRow, int startingColumn);
        
        // ...NOT WORKING
        public void fillDirections(int startRow, int startColumn, byte forwardSteps, byte backwardSteps){
        
            if(directions.isEmpty())
                fillDirections(startRow, startColumn);
            
        }
        
        @Override
        public int getNextDirection(){

        if(isCurrentIndexInitiated==false){
            int randomDirection = 1+ (int)(Math.random()*8);

            while(!isCurrentIndexInitiated){
                
                if(directions.get(currentDirectionIndex%directions.size()) != randomDirection)
                    currentDirectionIndex++;
                
                else{
                    if(isOppositeDirection.get(currentDirectionIndex))
                        currentDirectionIndex++;
                    else
                        isCurrentIndexInitiated = true;
                }
            }
            
        }
        
        return directions.get((currentDirectionIndex++)%directions.size());
    }
}
