package com.manikhweschool.patternrecognition.moves;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.space.RegionMovingStrategy;

public class RegionSixMove extends RegionMove{
    
    public RegionSixMove(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn){ 
        super(move,startRow, startColumn, endRow,endColumn);
    }
    
    @Override
    public void fillRowByRow(Cell[][] cells){
        
        Location region = Location.Region_Six;
        currentRow = startRow;
        currentColumn = startColumn;
        
        while(currentRow<=endRow){
            keepGoingRight(cells, region);
            if(!addSeven(cells, region))
                break;
            keepGoingLeft(cells, region);
            if(!addThree(cells, region))
                break;
        }
        
        addOppositeDirections();
    }
    
    @Override
    public void fillColumnByColumn(Cell[][] cells){
    
        Location region = Location.Region_Six;
        currentRow = startRow;
        currentColumn = startColumn;
        
        while(currentColumn <= 8){
            keepGoingDown(cells, region);
            if(!addEight(cells, region))
                if(!addSix(cells, region))
                    break;
            
            keepGoingUp(cells, region);
            if(!addEight(cells, region))
                break;
        }
        
        addOppositeDirections();
    }
}
