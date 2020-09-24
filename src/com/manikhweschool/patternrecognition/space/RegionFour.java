
package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.moves.RegionFourMove;

public class RegionFour extends Region{
    
    private RegionFourMove regionFourMove;
    
    public RegionFour(RegionMovingStrategy move){
        this(move,(byte)-1, (byte)-1, (byte)-1, (byte)-1, null);
    }
    
    public RegionFour(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn, RegionBall regionBall){
        
        regionFourMove = new RegionFourMove(move,
        startRow,startColumn, endRow, endColumn);
    }
    
    public RegionFourMove getRegionFourMove(){
    
        return regionFourMove;
    }
    
    public void setRegionFourMove(RegionFourMove regionFourMove){
    
        regionBall.setMove(regionFourMove);
        this.regionFourMove = regionFourMove;
    }
    
    @Override
    public void fillInCircle(){
    
    }
    
    public void setRegionFourMove(){
    
        if(regionBall != null)
            regionBall.setMove(regionFourMove);
        else
            System.out.println("Region Ball Is Null On setRegionFourMove().");
    }
    
    @Override
    public void cartegorizeCells(){
          
        for(Cell cell : regionCells)
            cell.setCellRegion(Location.Region_Four);
    }
}
