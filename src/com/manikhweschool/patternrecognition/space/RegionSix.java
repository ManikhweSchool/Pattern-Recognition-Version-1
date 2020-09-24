
package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.moves.RegionSixMove;

public class RegionSix extends Region{
    
    private RegionSixMove regionSixMove;
    
    public RegionSix(RegionMovingStrategy move){
        this(move,(byte)-1, (byte)-1, (byte)-1, (byte)-1, null);
    }
    
    public RegionSix(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn, RegionBall regionBall){
        
        regionSixMove = new RegionSixMove(move,
        startRow,startColumn, endRow, endColumn);
    }
    
    public RegionSixMove getRegionSixMove(){
    
        return regionSixMove;
    }
    
    public void setRegionSixMove(RegionSixMove regionSixMove){
    
        regionBall.setMove(regionSixMove);
        this.regionSixMove = regionSixMove;
    }
    
    @Override
    public void fillInCircle(){
    
    }
    
    public void setRegionSixMove(){
    
        if(regionBall != null)
            regionBall.setMove(regionSixMove);
        else
            System.out.println("Region Ball Is Null On setRegionSixMove().");
    }
    
    @Override
    public void cartegorizeCells(){
          
        for(Cell cell : regionCells)
            cell.setCellRegion(Location.Region_Six);
    }
}
