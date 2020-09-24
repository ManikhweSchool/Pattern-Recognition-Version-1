package com.manikhweschool.patternrecognition.moves;

import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.space.RegionMovingStrategy;

public class RegionOneMove extends RegionMove{
    
    public RegionOneMove(RegionMovingStrategy move,byte startRow, byte startColumn, 
    byte endRow, byte endColumn){ 
        super(move,startRow, startColumn, endRow,endColumn);
    }
    
    @Override
    public void fillRowByRow(Cell[][] cells){
        
        boolean toRight = true;
        
        
        for(currentRow = startRow; currentRow<=endRow;currentRow++){
            
            currentColumn = startColumn;
            
            while(currentColumn + 1 < cells[currentRow].length && 
            !cells[currentRow][currentColumn+1].getIsOnPath()){
                addLetterPictureDirections(cells);
                if(toRight){
                    directions.add(8);
                }
                else{
                    directions.add(1);
                }
                currentColumn++;
            }
            
            if(toRight){
                addLetterPictureDirections(cells);
                if(currentRow + 1 <= endRow && 
                cells[currentRow+1][currentColumn].getCellRegion() 
                == Location.Region_One){
                    directions.add(3);
                }
                else if(currentRow + 1 <= endRow){
                    directions.add(7);
                }
            }
            else{
                addLetterPictureDirections(cells);
                if(currentRow + 1 <= endRow){
                    directions.add(3);
                }
            }
            
            toRight = !toRight;     
        }
        
        addOppositeDirections();
    }
    
    @Override
    public void fillColumnByColumn(Cell[][] cells){
    
        currentRow = startRow;
        currentColumn = startColumn;
        
        while(currentColumn <= 8 && !cells[currentRow][currentColumn].getIsOnPath()){
            
            while(currentRow + 1 <= endRow && 
            !cells[currentRow+1][currentColumn].getIsOnPath() &&
            cells[currentRow+1][currentColumn].getCellRegion()==Location.Region_One){
                addLetterPictureDirections(cells);
                directions.add(3);
                currentRow++;
                
            }
            
            if(currentColumn + 1 <= 8 && currentRow - 1 >= 0 &&
            cells[currentRow-1][currentColumn+1].getCellRegion() 
            == Location.Region_One){
                addLetterPictureDirections(cells);
                directions.add(6);
                currentColumn++;
                currentRow--;
                
            }
            else{
                break;
            }
           
            while(currentRow - 1 >= 0){
                addLetterPictureDirections(cells);
                directions.add(2);
                currentRow--;
                
            }
            
            if(currentColumn + 1 <= 8 && cells[currentRow][currentColumn+1].getCellRegion() 
            == Location.Region_One){
                addLetterPictureDirections(cells);
                directions.add(8);
                currentColumn++;
            }
            else{
                break;
            }
        }
        
        addOppositeDirections();
    }
}
