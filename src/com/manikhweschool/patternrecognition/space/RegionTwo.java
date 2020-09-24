
package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.moves.RegionTwoMove;

public class RegionTwo extends Region{
    
    private RegionTwoMove regionTwoMove;
    
    public RegionTwo(RegionMovingStrategy move){
        this(move,(byte)-1, (byte)-1, (byte)-1, (byte)-1, null);
    }
    
    public RegionTwo(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn, RegionBall regionBall){
        
        regionTwoMove = new RegionTwoMove(move,
        startRow,startColumn, endRow, endColumn);
    }
    
    public void setRegionTwoMove(){
    
        if(regionBall != null)
            regionBall.setMove(regionTwoMove);
        else
            System.out.println("Region Ball Is Null On setRegionTwoMove().");
    }
    
    public RegionTwoMove getRegionTwoMove(){
    
        return regionTwoMove;
    }
    
    @Override
    public void fillInCircle(){
    
    }
    
    public void setRegionTwoMove(RegionTwoMove regionTwoMove){
    
        regionBall.setMove(regionTwoMove);
    }
    
    @Override
    public void cartegorizeCells(){
          
        for(Cell cell : regionCells)
            cell.setCellRegion(Location.Region_Two);
    }
}
