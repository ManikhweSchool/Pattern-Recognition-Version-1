
package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.moves.RegionThreeMove;

public class RegionThree extends Region{
    
    private RegionThreeMove regionThreeMove;
    
    public RegionThree(RegionMovingStrategy move){
        this(move,(byte)-1, (byte)-1, (byte)-1, (byte)-1, null);
    }
    
    public RegionThree(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn, RegionBall regionBall){
        
        regionThreeMove = new RegionThreeMove(move,
        startRow,startColumn, endRow, endColumn);
    }
    
    public RegionThreeMove getRegionThreeMove(){
    
        return regionThreeMove;
    }
    
    public void setRegionThreeMove(){
    
        if(regionBall != null)
            regionBall.setMove(regionThreeMove);
        else
            System.out.println("Region Ball Is Null On setRegionThreeMove().");
    }
    
    @Override
    public void fillInCircle(){
    
    }
    
    public void setRegionThreeMove(RegionThreeMove regionThreeMove){
    
        regionBall.setMove(regionThreeMove);
        this.regionThreeMove = regionThreeMove;
    }
    
    @Override
    public void cartegorizeCells(){
          
        for(Cell cell : regionCells)
            cell.setCellRegion(Location.Region_Three);
    }
}
