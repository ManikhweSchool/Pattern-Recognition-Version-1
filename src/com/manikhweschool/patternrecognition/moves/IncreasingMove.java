package com.manikhweschool.patternrecognition.moves;

public class IncreasingMove extends QueenMove{
    
    
    
     @Override
    public String toString(){
        return "Increasing Move";
    }
    
    @Override
    public void fillDirections(int startingRow, int startingColumn){
    
        
        int row = startingRow;
        int column = startingColumn;
        
        while(column>0){
            directions.add(1);
            column--;
            isOppositeDirection.add(false);
        }
        while(column<startingColumn){
            directions.add(8);
            column++;
            isOppositeDirection.add(true);
        }
        
        while(row>0){
            directions.add(2);
            row--;
            isOppositeDirection.add(false);
        }
        while(row<startingRow){
            directions.add(3);
            row++;
            isOppositeDirection.add(true);
        }
        
        while(row<8){
            directions.add(3);
            row++;
            isOppositeDirection.add(false);
        }
        while(row>startingRow){
            directions.add(2);
            row--;
            isOppositeDirection.add(true);
        }
        
        while(column>0 && row>0){
            directions.add(4);
            row--;
            column--;
            isOppositeDirection.add(false);
        }
        while(row<startingRow && column<startingColumn){
            directions.add(5);
            row++;
            column++;
            isOppositeDirection.add(true);
        }  
        
        while(row<8 && column<8){
            directions.add(5);
            row++;
            column++;
            isOppositeDirection.add(false);
        }
        while(column>startingColumn 
        && row>startingRow){
            directions.add(4);
            column--;
            row--;
            isOppositeDirection.add(true);
        }
        
        while(column<8 && row>0){
            directions.add(6);
            row--;
            column++;
            isOppositeDirection.add(false);
        }
        while(row<startingRow && column>startingColumn){
            directions.add(7);
            row++;
            column--;
            isOppositeDirection.add(true);
        }
        
        while(row<8 && column>0){
            directions.add(7);
            row++;
            column--;
            isOppositeDirection.add(false);
        }
        while(row>startingRow && column<startingColumn){
            directions.add(6);
            row--;
            column++;
            isOppositeDirection.add(true);
        }
        
        while(column<8){
            directions.add(8);
            column++;
            isOppositeDirection.add(false);
        }
        while(column>startingColumn){
            directions.add(1);
            column--;
            isOppositeDirection.add(true);
        }
        
    }
}
