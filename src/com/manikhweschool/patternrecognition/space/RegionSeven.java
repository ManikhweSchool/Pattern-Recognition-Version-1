
package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.moves.RegionSevenMove;

public class RegionSeven extends Region{
    
    private RegionSevenMove regionSevenMove;
    
    public RegionSeven(RegionMovingStrategy move){
        this(move,(byte)-1, (byte)-1, (byte)-1, (byte)-1, null);
    }
    
    public RegionSeven(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn, RegionBall regionBall){
        
        regionSevenMove = new RegionSevenMove(move,
        startRow,startColumn, endRow, endColumn);
    }
    
    public RegionSevenMove getRegionSevenMove(){
    
        return regionSevenMove;
    }
    
    @Override
    public void fillInCircle(){
    
    }
    
    public void setRegionSevenMove(RegionSevenMove regionSevenMove){
    
        regionBall.setMove(regionSevenMove);
    }
    
    public void setRegionSevenMove(){
    
        if(regionBall != null)
            regionBall.setMove(regionSevenMove);
        else
            System.out.println("Region Ball Is Null On setRegionSevenMove().");
    }
    
    @Override
    public void cartegorizeCells(){
          
        for(Cell cell : regionCells)
            cell.setCellRegion(Location.Region_Seven);
    }
}
