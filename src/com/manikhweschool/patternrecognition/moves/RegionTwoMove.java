package com.manikhweschool.patternrecognition.moves;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.space.RegionMovingStrategy;
import java.util.ArrayList;


public class RegionTwoMove extends RegionMove{
    
    public RegionTwoMove(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn){ 
        super(move,startRow, startColumn, endRow,endColumn);
    }
   
    @Override
    public void fillRowByRow(Cell[][] cells){
        
        Location region = Location.Region_Two;
        currentRow = startRow;
        currentColumn = startColumn;
        
        while(currentRow <= endRow){
            keepGoingRight(cells, region);
            if(!addThree(cells, region))
                break;
            keepGoingLeft(cells, region);
            if(!addSeven(cells, region))
                break;
        }
        addOppositeDirections();
        
    }
    
    @Override
    public void fillColumnByColumn(Cell[][] cells){
    
        boolean rightFirst = (int)(Math.random()*2)==0;
        
        Location region = Location.Region_Two;
        currentRow = startRow;
        currentColumn = startColumn;
        
        if(rightFirst && currentColumn+1<=8){
            while(currentColumn <= endColumn){
                keepGoingDown(cells, region);
                if(!addEight(cells, region))
                    break;
                keepGoingUp(cells, region);
                if(!addEight(cells, region))
                    break;
            }
            addOppositeDirections();
            
            currentColumn = startColumn;
            currentRow = startRow;
            
            ArrayList<Integer> initialDirections = (ArrayList<Integer>)directions.clone();
            directions.clear();
            while(currentColumn>=0){
                keepGoingDown(cells, region);
                if(!addOne(cells, region))
                    break;
                keepGoingUp(cells, region);
                if(!addSeven(cells, region))
                    break;
            }
            addOppositeDirections();
            currentColumn = startColumn;
            currentRow = startRow;
            
            initialDirections.addAll(directions);
            directions.clear();
            
            directions.addAll(initialDirections);
        }
        else{
            while(currentColumn>=0){
                keepGoingDown(cells, region);
                if(!addOne(cells, region))
                    break;
                keepGoingUp(cells, region);
                if(!addSeven(cells, region))
                    break;
            }
            addOppositeDirections();
            currentColumn = startColumn;
            currentRow = startRow;
            
            if(currentColumn+1<=8){
                ArrayList<Integer> initialDirections = (ArrayList<Integer>)directions.clone();
                directions.clear();


                while(currentColumn <= endColumn){
                    keepGoingDown(cells, region);
                    if(!addEight(cells, region))
                        break;
                    keepGoingUp(cells, region);
                    if(!addEight(cells, region))
                        break;
                }
                addOppositeDirections();

                initialDirections.addAll(directions);
                directions.clear();
                directions.addAll(initialDirections);

                currentRow = startRow;
                currentColumn = startColumn;
            }
        }
    }
}
