
package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.moves.RegionFiveMove;

public class RegionFive extends Region{
    
    protected RegionFiveMove regionFiveMove;
    
    public RegionFive(RegionMovingStrategy move){
        this(move,(byte)-1, (byte)-1, (byte)-1, (byte)-1, null);
    }
    
    public RegionFive(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn, RegionBall regionBall){
        
        regionFiveMove = new RegionFiveMove(move,
        startRow,startColumn, endRow, endColumn);
    }
    
    public RegionFiveMove getRegionFiveMove(){
    
        return regionFiveMove;
    }
    
    @Override
    public void fillInCircle(){
    
    }
    
    public void setRegionFiveMove(RegionFiveMove regionFiveMove){
    
        regionBall.setMove(regionFiveMove);
        this.regionFiveMove = regionFiveMove;
    }
    
    public void setRegionFiveMove(){
    
        if(regionBall != null)
            regionBall.setMove(regionFiveMove);
        else
            System.out.println("Region Ball Is Null On setRegionFiveMove().");
    }
    
    @Override
    public void cartegorizeCells(){
          
        for(Cell cell : regionCells)
            cell.setCellRegion(Location.Region_Five);
    }
    
}
