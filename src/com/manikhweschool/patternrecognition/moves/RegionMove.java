package com.manikhweschool.patternrecognition.moves;
import com.manikhweschool.patternrecognition.buildingblocks.Cell;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.space.RegionMovingStrategy;

import java.util.ArrayList;

public abstract class RegionMove extends Move{
    
    // Contains the moving strategy for all regions on this cartesian plane.
    protected RegionMovingStrategy regionMovingStrategy = 
    ((int)(Math.random()*2)==0)?RegionMovingStrategy.Row_By_Row:
    RegionMovingStrategy.Column_By_Column;
    
    protected byte startRow;
    protected byte endRow;
    
    protected byte startColumn;
    protected byte endColumn;
    
    protected byte currentRow;
    protected byte currentColumn;
    
    protected boolean hasDeltWithLetter;
    protected boolean hasDeltWithNumber;
    
    protected RegionMove(RegionMovingStrategy regionMovingStrategy, byte startRow, byte startColumn, byte endRow, byte endColumn){
        this.regionMovingStrategy = regionMovingStrategy;
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.endRow = endRow;
        this.endColumn = endColumn;
        currentDirectionIndex = 0;
        currentRow = this.startRow;
        currentColumn = this.startColumn;
        hasDeltWithLetter = false;
        hasDeltWithNumber = false;
    }
    
    public RegionMovingStrategy getRegionMovingStrategy(){
    
        return regionMovingStrategy;
    }
    
    public byte getStartRow(){ return startRow;}
    public byte getEndRow(){ return endRow;}
    
    public byte getStartColumn(){ return startColumn;}
    public byte getEndColumn(){ return endColumn;}
    
    public void setStartRow(byte startRow){ this.startRow = startRow;}
    public void setEndRow(byte endRow){ this.endRow = endRow;}
    
    public void setStartColumn(byte startColumn){ this.startColumn = startColumn;}
    public void setEndColumn(byte endColumn){ this.endColumn = endColumn;}
    
    public abstract void fillRowByRow(Cell[][] cells);
    public abstract void fillColumnByColumn(Cell[][] cells);
    
    public void addTwoOppositeDirections(byte currentRow, byte currentColumn){
    
        byte direction = -1;
        
        if((currentRow==1 && currentColumn==3) || 
        (currentRow==1 && currentColumn==5) ||
        (currentRow==7 && currentColumn==3) ||
        (currentRow==7 && currentColumn==5))
            direction = 8;
        
        else if((currentRow==3 && currentColumn==1) || 
        (currentRow==5 && currentColumn==1) ||
        (currentRow==3 && currentColumn==7) ||
        (currentRow==5 && currentColumn==7))
            direction = 3;
        
        
        if(direction==8 || direction==3){
            directions.add((int)direction);
            directions.add(getOppositeDirection(direction));
        }    
    }
    
    public void fillRowByRow(Cell[][] cells, byte forwardSteps, boolean isInfinite){
        
        fillRowByRow(cells, forwardSteps, (byte)1, isInfinite);
    }
    
    public void fillColumnByColumn(Cell[][] cells, byte forwardSteps, boolean isInfinite){
        
        fillColumnByColumn(cells, forwardSteps, (byte)1, isInfinite);
    }
    
    public void fillRowByRow(Cell[][] cells, byte forwardSteps, byte backwardSteps, boolean isInfinite){
        
        if(forwardSteps > backwardSteps && backwardSteps >= 0){
           
            if(directions.isEmpty())
               fillRowByRow(cells);
           
            if(isInfinite){
                ArrayList<Integer> anotherDirections = new ArrayList<>();
                for(int numberOfTimes = 1; numberOfTimes <=1000;numberOfTimes++)
                    anotherDirections.addAll(directions);
                directions.clear(); 
                directions.addAll(anotherDirections);
            }
            
            fillHelper(forwardSteps, backwardSteps);
        }
    }
    
    public void fillColumnByColumn(Cell[][] cells, byte forwardSteps, byte backwardSteps, boolean isInfinite){
    
        if(forwardSteps > backwardSteps && backwardSteps >= 0){
           
            if(directions.isEmpty())
               fillColumnByColumn(cells);
           
            if(isInfinite){
                ArrayList<Integer> anotherDirections = new ArrayList<>();
                for(int numberOfTimes = 1; numberOfTimes <=1000;numberOfTimes++)
                    anotherDirections.addAll(directions);
                directions.clear();
                directions.addAll(anotherDirections);
            }
            
            fillHelper(forwardSteps, backwardSteps);
            
        }
    }
    
    public boolean exist(){ 
        return !(startRow==-1 || 
            startColumn==-1 || 
            startRow==-1 ||  
            startColumn==-1);
    }
    
    
    @Override
    public int getNextDirection(){
     
        if(directions.isEmpty() || currentDirectionIndex==directions.size()) return 0;
        return directions.get((currentDirectionIndex++)%directions.size());
    }
    
    protected void keepGoingRight(Cell[][] cells, Location region){
    
        while(currentColumn+1<=8 && !cells[currentRow][currentColumn+1].getIsOnPath()
        && cells[currentRow][currentColumn+1].getCellRegion() == region){
            
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(8);
            currentColumn++;
        }
    }
    
    protected void keepGoingRight(Cell[][] cells, byte initialRow, byte initialColumn){
    
        while(currentColumn+1<=8){
            if(initialRow==currentRow && initialColumn==currentColumn)
                break;
            directions.add(8);
            currentColumn++;
        }
    }
    
    protected void keepGoingRight(Cell[][] cells){
    
        while(currentColumn+1<=8){
            directions.add(8);
            currentColumn++;
        }
    }
    
    protected void keepGoingLeft(Cell[][] cells, Location region){
    
        while(currentColumn-1>=0 && !cells[currentRow][currentColumn-1].getIsOnPath()
        && cells[currentRow][currentColumn-1].getCellRegion() == region){
            
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(1);
            currentColumn--;
        }
    }
    
    protected void keepGoingLeft(Cell[][] cells, byte initialRow, byte initialColumn){
    
        while(currentColumn-1>=0){
            if(initialRow==currentRow && initialColumn==currentColumn)
                break;
            directions.add(1);
            currentColumn--;
        }
    }
    
    protected void keepGoingLeft(Cell[][] cells){
    
        while(currentColumn-1>=0){
            directions.add(1);
            currentColumn--;
        }
    }
    
    protected void keepGoingUp(Cell[][] cells, Location region){

        while(currentRow-1>=0 && !cells[currentRow-1][currentColumn].getIsOnPath()
        && cells[currentRow-1][currentColumn].getCellRegion() == region){
            
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(2);
            currentRow--;
        }
    }
    
    protected void keepGoingUp(Cell[][] cells, byte initialRow, byte initialColumn){
    
        while(currentRow-1>=0){
            if(initialRow==currentRow && initialColumn==currentColumn)
                break;
            directions.add(2);
            currentRow--;
        }
    }
    
    protected void keepGoingUp(Cell[][] cells){
    
        while(currentRow-1>=0){
            directions.add(2);
            currentRow--;
        }
    }
    
    protected void keepGoingDown(Cell[][] cells, Location region){
    
        while(currentRow+1<=endRow && !cells[currentRow+1][currentColumn].getIsOnPath()
        && cells[currentRow+1][currentColumn].getCellRegion() == region){
            
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(3);
            currentRow++;
        }
    }
    
    protected void keepGoingDown(Cell[][] cells, byte initialRow, byte initialColumn){
    
        while(currentRow+1<=endRow){
            if(initialRow==currentRow && initialColumn==currentColumn)
                break;
            directions.add(3);
            currentRow++;
        }
    }
    
    protected void keepGoingDown(Cell[][] cells){
    
        while(currentRow+1<=endRow){
            directions.add(3);
            currentRow++;
        }
    }
    
    protected void keepGoingTopRight(Cell[][] cells, Location region){
    
        while(currentColumn+1<=endColumn &&currentRow-1>=0 && 
        !cells[currentRow-1][currentColumn+1].getIsOnPath() &&
        cells[currentRow-1][currentColumn+1].getCellRegion() == region){
            
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(6);
            currentRow--;
            currentColumn++;
        }
    }
    
    protected void keepGoingTopRight(Cell[][] cells, byte initialRow, byte initialColumn){
    
        while(currentColumn+1<=endColumn &&currentRow-1>=0){
            if(initialRow==currentRow && initialColumn==currentColumn)
                break;
            directions.add(6);
            currentRow--;
            currentColumn++;
        }
    }
    
    protected void keepGoingTopRight(Cell[][] cells){
    
        while(currentColumn+1<=endColumn &&currentRow-1>=0){
            directions.add(6);
            currentRow--;
            currentColumn++;
        }
    }
    
    protected void keepGoingTopLeft(Cell[][] cells, Location region){
    
        while(currentColumn-1>=0 &&currentRow-1>=0 && 
        !cells[currentRow-1][currentColumn-1].getIsOnPath() &&
        cells[currentRow-1][currentColumn-1].getCellRegion() == region){
            
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(4);
            currentRow--;
            currentColumn--;
        }
    }
    
    protected void keepGoingTopLeft(Cell[][] cells, byte initialRow, byte initialColumn){
    
        while(currentColumn-1>=0 &&currentRow-1>=0){
            if(initialRow==currentRow && initialColumn==currentColumn)
                break;
            directions.add(4);
            currentRow--;
            currentColumn--;
        }
    }
    
    protected void keepGoingTopLeft(Cell[][] cells){
    
        while(currentColumn-1>=0 &&currentRow-1>=0){
            directions.add(4);
            currentRow--;
            currentColumn--;
        }
    }
    
    protected void keepGoingBottomRight(Cell[][] cells, Location region){

        while(currentColumn+1<=endColumn &&currentRow+1<=endRow && 
        !cells[currentRow+1][currentColumn+1].getIsOnPath() &&
        cells[currentRow+1][currentColumn+1].getCellRegion() == region){
            
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(5);
            currentRow++;
            currentColumn++;
        }
    }
    
    protected void keepGoingBottomRight(Cell[][] cells, byte initialRow, byte initialColumn){
    
        while(currentColumn+1<=endColumn &&currentRow+1<=endRow){
            if(initialRow==currentRow && initialColumn==currentColumn)
                break;
            directions.add(5);
            currentRow++;
            currentColumn++;
        }
    }
    
    protected void keepGoingBottomRight(Cell[][] cells){
    
        while(currentColumn+1<=endColumn &&currentRow+1<=endRow){
            directions.add(5);
            currentRow++;
            currentColumn++;
        }
    }
    
    protected void keepGoingBottomLeft(Cell[][] cells, Location region){
    
        while(currentColumn-1>=0 &&currentRow+1<=endRow && 
        !cells[currentRow+1][currentColumn-1].getIsOnPath() &&
        cells[currentRow+1][currentColumn-1].getCellRegion() == region){
            
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);
            
            directions.add(7);
            currentRow++;
            currentColumn--;
        }
    }
    
    protected void keepGoingBottomLeft(Cell[][] cells, byte initialRow, byte initialColumn){
    
        while(currentColumn-1>=0 &&currentRow+1<=endRow){
            if(initialRow==currentRow && initialColumn==currentColumn)
                break;
            directions.add(7);
            currentRow++;
            currentColumn--;
        }
    }
    
    protected void keepGoingBottomLeft(Cell[][] cells){
    
        while(currentColumn-1>=0 &&currentRow+1<=endRow){
            directions.add(7);
            currentRow++;
            currentColumn--;
        }
    }
    
    protected void addOppositeDirections(){
    
        addOppositeDirections(directions);
    }
    
    protected void addOppositeDirections(ArrayList<Integer> directions){
    
        ArrayList<Integer> oppositeDirections = new ArrayList<>();
        
        for(int index = directions.size()-1; index >= 0;index--){
            int oppositeDirection = getOppositeDirection(directions.get(index));
            if((index == directions.size()- 1 && oppositeDirection==directions.get(index-1))
            || (index == directions.size()- 2 && oppositeDirection==directions.get(index+1)))
                continue;
            
            oppositeDirections.add(getOppositeDirection(directions.get(index)));
            
        }
        
        directions.addAll(oppositeDirections);
    }
    
    protected void addLetterPictureDirections(Cell[][] cells){
    
        
        if(!hasDeltWithLetter && cells[currentRow][currentColumn].containsLetter()){
            
            switch(cells[currentRow][currentColumn].getLetter().getLetter()){
                case 'C' : 
                    if(addOne()) 
                        addEight();
                    break;
                case 'L' : 
                    if(addTwo())
                        addThree();
                    break;
                case 'M' : 
                    if(addThree()) 
                        addTwo();
                    break;
                case 'N' : 
                    if(addFour())
                        addFive();
                    break;
                case 'O' : 
                    if(addFive())
                        addFour();
                    break;
                case 'R' :
                    if(addSix())
                        addSeven();
                    break;
                case 'S' :
                    if(addSeven())
                        addSix();
                    break;
                default : 
                    if(addEight())
                        addOne();
            }
            
            hasDeltWithLetter = true;
            
        }
            
    }
    
    protected void addNumberPictureDirections(Cell[][] cells){
    
        if(!hasDeltWithNumber && cells[currentRow][currentColumn].containsNumber()){
            byte initialRow = currentRow;
            byte initialColumn = currentColumn;
            switch(cells[currentRow][currentColumn].getNumber().getNumber()){
                case 1 : 
                    keepGoingLeft(cells);
                    keepGoingRight(cells, initialRow, initialColumn);
                    break;
                case 2 :
                    keepGoingUp(cells);
                    keepGoingDown(cells, initialRow, initialColumn);
                    break;
                case 3 :
                    keepGoingDown(cells);
                    keepGoingUp(cells, initialRow, initialColumn);
                    break;
                case 4 : 
                    keepGoingTopLeft(cells);
                    keepGoingBottomRight(cells,initialRow, initialColumn);
                    break;
                case 5 :
                    keepGoingBottomRight(cells);
                    keepGoingTopLeft(cells, initialRow, initialColumn);
                    break;
                case 6 :
                    keepGoingTopRight(cells);
                    keepGoingBottomLeft(cells, initialRow, initialColumn);
                    break;
                case 7 :
                    keepGoingBottomLeft(cells);
                    keepGoingTopRight(cells, initialRow, initialColumn);
                    break;
                default :

            }
            hasDeltWithNumber = true;
        }
    }
    
    
    
    protected boolean addOne(Cell[][] cells, Location region){
    
        if(currentColumn-1>=0 && !cells[currentRow][currentColumn-1].getIsOnPath()
        && cells[currentRow][currentColumn-1].getCellRegion() == region){
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);
            directions.add(1);
            currentColumn--;
            return true;
        }
        addLetterPictureDirections(cells);
        addNumberPictureDirections(cells);
        return false;
    }
    
    protected boolean addOne(){
    
        if(currentColumn-1>=0){
            directions.add(1);
            currentColumn--;
            return true;
        }
        
        return false;
    }
    
    protected boolean addTwo(Cell[][] cells, Location region){
    
        if(currentRow-1>=0 && !cells[currentRow-1][currentColumn].getIsOnPath()
        && cells[currentRow-1][currentColumn].getCellRegion() == region){
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);
            directions.add(2);
            currentRow--;
            return true;
        }
        addLetterPictureDirections(cells);
        addNumberPictureDirections(cells);

        return false;
    }
    
    protected boolean addTwo(){
    
        if(currentRow-1>=0){
            directions.add(2);
            currentRow--;
            return true;
        }
        return false;
    }
    
    protected boolean addThree(Cell[][] cells, Location region){

        if(currentRow+1<=endRow && !cells[currentRow+1][currentColumn].getIsOnPath()
        && cells[currentRow+1][currentColumn].getCellRegion() == region){
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(3);
            currentRow++;
            return true;
        }
        addLetterPictureDirections(cells);
        addNumberPictureDirections(cells);

        return false;
    }
    
    protected boolean addThree(){

        if(currentRow+1<=8){
            directions.add(3);
            currentRow++;
            return true;
        }
        return false;
    }
    
    protected boolean addFour(Cell[][] cells, Location region){
    
        if(currentColumn-1>=0 &&currentRow-1>=0 && 
        !cells[currentRow-1][currentColumn-1].getIsOnPath() &&
        cells[currentRow-1][currentColumn-1].getCellRegion() == region){
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(4);
            currentRow--;
            currentColumn--;
            return true;
        }
        addLetterPictureDirections(cells);
        addNumberPictureDirections(cells);

        return false;
    }
    
    protected boolean addFour(){
    
        if(currentColumn-1>=0 && currentRow-1>=0){
            directions.add(4);
            currentRow--;
            currentColumn--;
            return true;
        }
        return false;
    }
    
    protected boolean addFive(Cell[][] cells, Location region){
    
        if(currentColumn+1<=endColumn &&currentRow+1<=endRow && 
        !cells[currentRow+1][currentColumn+1].getIsOnPath() &&
        cells[currentRow+1][currentColumn+1].getCellRegion() == region){
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(5);
            currentRow++;
            currentColumn++;
            return true;
        }
        addLetterPictureDirections(cells);
        addNumberPictureDirections(cells);

        return false;
    }
    
    protected boolean addFive(){
    
        if(currentColumn+1<=8 &&currentRow+1<=8){
            directions.add(5);
            currentRow++;
            currentColumn++;
            return true;
        }
        return false;
    }
    
    protected boolean addSix(Cell[][] cells, Location region){
    
        if(currentColumn+1<=8 &&currentRow-1>=0 && 
        !cells[currentRow-1][currentColumn+1].getIsOnPath() &&
        cells[currentRow-1][currentColumn+1].getCellRegion() == region){
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(6);
            currentRow--;
            currentColumn++;
            return true;
        }
        addLetterPictureDirections(cells);
        addNumberPictureDirections(cells);

        return false;
    }
    
    protected boolean addSix(){
    
        if(currentColumn+1<=8 &&currentRow-1>=0){
            directions.add(6);
            currentRow--;
            currentColumn++;
            return true;
        }
        return false;
    }
    
    protected boolean addSeven(Cell[][] cells, Location region){
    
        if(currentColumn-1>=0 &&currentRow+1<=endRow && 
        !cells[currentRow+1][currentColumn-1].getIsOnPath() &&
        cells[currentRow+1][currentColumn-1].getCellRegion() == region){
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);

            directions.add(7);
            currentRow++;
            currentColumn--;
            return true;
        }
        addLetterPictureDirections(cells);
        addNumberPictureDirections(cells);

        return false;
    }
    
    protected boolean addSeven(){
    
        if(currentColumn-1>=0 &&currentRow+1<=8){
            directions.add(7);
            currentRow++;
            currentColumn--;
            return true;
        }
        return false;
    }
    
    protected boolean addEight(Cell[][] cells, Location region){
    
        if(currentColumn+1<=8 && !cells[currentRow][currentColumn+1].getIsOnPath()
        && cells[currentRow][currentColumn+1].getCellRegion() == region){
            addLetterPictureDirections(cells);
            addNumberPictureDirections(cells);
            directions.add(8);
            currentColumn++;
            return true;
        }
        addLetterPictureDirections(cells);
        addNumberPictureDirections(cells);

        return false;
    }
    
    protected boolean addEight(){
    
        if(currentColumn+1<=8){
            directions.add(8);
            currentColumn++;
            return true;
        }
        return false;
    }
}

