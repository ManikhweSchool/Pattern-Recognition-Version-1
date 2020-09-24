
package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.moves.RegionEightMove;

public class RegionEight extends Region{
    
    private RegionEightMove regionEightMove;
    
    public RegionEight(RegionMovingStrategy move){
        this(move,(byte)-1, (byte)-1, (byte)-1, (byte)-1, null);
    }
    
    public RegionEight(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn, RegionBall regionBall){
        
        regionEightMove = new RegionEightMove(move,
        startRow,startColumn, endRow, endColumn);
    }
    
    @Override
    public void fillInCircle(){
    
    }
    
    public RegionEightMove getRegionEightMove(){
    
        return regionEightMove;
    }
    
    public void setRegionEightMove(RegionEightMove regionEightMove){
    
        regionBall.setMove(regionEightMove);
        this.regionEightMove = regionEightMove;
    }
    
    public void setRegionEightMove(){
    
        if(regionBall != null)
            regionBall.setMove(regionEightMove);
        else
            System.out.println("Region Ball Is Null On setRegionEightMove().");
    }
    
    @Override
    public void cartegorizeCells(){
          
        for(Cell cell : regionCells)
            cell.setCellRegion(Location.Region_Eight);
    }
    
}
