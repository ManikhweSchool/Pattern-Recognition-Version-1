
package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.moves.RegionOneMove;

public class RegionOne extends Region{
    
    private RegionOneMove regionOneMove;
    
    public RegionOne(RegionMovingStrategy move){
        this(move,(byte)-1, (byte)-1, (byte)-1, (byte)-1);
    }
    
    public RegionOne(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn){
        
        regionOneMove = new RegionOneMove(move,
        startRow,startColumn, endRow, endColumn);
        
    }
    
    public RegionOneMove getRegionOneMove(){
    
        return regionOneMove;
    }
    
    public void setRegionOneMove(RegionOneMove regionOneMove){
    
        regionBall.setMove(regionOneMove);
        this.regionOneMove = regionOneMove;
    }
    
    @Override
    public void fillInCircle(){
    
    }
    
    public void setRegionOneMove(){
    
        if(regionBall != null)
            regionBall.setMove(regionOneMove);
        else
            System.out.println("Region Ball Is Null On setRegionOneMove().");
    }
    
    @Override
    public void cartegorizeCells(){
          
        for(Cell cell : regionCells){
            cell.setCellRegion(Location.Region_One);
            cell.setIsOnPath(false);
        }
    }

}
