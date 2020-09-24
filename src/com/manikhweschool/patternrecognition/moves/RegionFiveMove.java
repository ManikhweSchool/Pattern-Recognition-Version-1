package com.manikhweschool.patternrecognition.moves;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.space.RegionMovingStrategy;

public class RegionFiveMove extends RegionMove{
    
    public RegionFiveMove(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn){ 
        super(move,startRow, startColumn, endRow,endColumn);
    }
    
    @Override
    public void fillRowByRow(Cell[][] cells){
        
        Location region = Location.Region_Five;
        currentRow = startRow;
        currentColumn = startColumn;
        
        while(currentRow <= endRow){
            keepGoingLeft(cells, region);
            if(!addSeven(cells, region))
                if(!addThree(cells, region))
                    break;
            keepGoingRight(cells, region);
            if(!addThree(cells, region))
                break;
            
        }
        addOppositeDirections();
    }
    
    @Override
    public void fillColumnByColumn(Cell[][] cells){
    
        Location region = Location.Region_Five;
        currentRow = startRow;
        currentColumn = startColumn;
        
        while(currentColumn>=0){
            keepGoingDown(cells, region);
            if(!addOne(cells, region))
                break;
            keepGoingUp(cells, region);
            if(!addSeven(cells, region))
                break;
        }
        
        addOppositeDirections();
    }
}
