package com.manikhweschool.patternrecognition.buildingblocks;

import com.manikhweschool.patternrecognition.letters.*;
import com.manikhweschool.patternrecognition.numbers.Number;
import com.manikhweschool.patternrecognition.numbers.Number1;
import com.manikhweschool.patternrecognition.numbers.Number2;
import com.manikhweschool.patternrecognition.numbers.Number3;
import com.manikhweschool.patternrecognition.numbers.Number4;
import com.manikhweschool.patternrecognition.numbers.Number5;
import com.manikhweschool.patternrecognition.numbers.Number6;
import com.manikhweschool.patternrecognition.numbers.Number7;
import com.manikhweschool.patternrecognition.numbers.Number8;



import javafx.scene.layout.StackPane;

public class Cell extends StackPane{
    
    private Location cellRegion;
    private boolean isOnPath;
    private byte atRow;
    private byte atColumn;
    
    private Letter letter = null;
    private Number number = null;
            
    public Cell(){
        cellRegion = Location.Region_None;
        isOnPath = false;
    }
    
    public Cell(byte atRow, byte atColumn){
        cellRegion = Location.Region_None;
        isOnPath = false;
        this.atColumn = atColumn;
        this.atRow = atRow;
    }

    public Cell(Location cellRegion){
        this.cellRegion = cellRegion;
        
        if(cellRegion==Location.Region_None)
            this.isOnPath = true;
        else
           this.isOnPath = false; 
    }
    
    public boolean getIsOnPath(){
        return isOnPath;
    }
    
    public boolean containsLetter(){
    
        return letter != null;
    }
    
    public boolean containsNumber(){
    
        return number != null;
    }
    
    public Letter getLetter(){ return letter;}
    public Number getNumber(){ return number;}
    
    public void chooseLetter(byte letterNumber){
    
        switch(letterNumber){
            case 1 : 
                letter = new LetterC(atRow,atColumn);
                break;
            case 2 :
                letter = new LetterL(atRow,atColumn);
                break;
            case 3 :
                letter = new LetterM(atRow,atColumn);
                break;
            case 4 :
                letter = new LetterN(atRow,atColumn);
                break;
            case 5 :
                letter = new LetterO(atRow,atColumn);
                break;
            case 6 :
                letter = new LetterR(atRow,atColumn);
                break;
            case 7 :
                letter = new LetterS(atRow,atColumn);
                break;
            default :
                letter = new LetterW(atRow,atColumn);
                
        }
    }
    
    public void chooseNumber(byte numberNumber){
    
        switch((numberNumber)){
            case 1 : 
                number = new Number1(atRow,atColumn);
                break;
            case 2 :
                number = new Number2(atRow,atColumn);
                break;
            case 3 :
                number = new Number3(atRow,atColumn);
                break;
            case 4 :
                number = new Number4(atRow,atColumn);
                break;
            case 5 :
                number = new Number5(atRow,atColumn);
                break;
            case 6 :
                number = new Number6(atRow,atColumn);
                break;
            case 7 :
                number = new Number7(atRow,atColumn);
                break;
            default :
                number = new Number8(atRow,atColumn);
                break;
        }
        
    }
    
    public void setIsOnPath(boolean isOnPath){
        this.isOnPath = isOnPath;
    }
    
    public Location getCellRegion(){
    
        return cellRegion;
    }
    
    public void setCellRegion(Location cellRegion){
    
        this.cellRegion = cellRegion;
    }
    
    @Override
    public String toString(){
    
        return "Row : " + atRow + " Column : " + atColumn + " On Path : " + ((isOnPath)?"Yes ":"No ") + 
        "Has Letter : " + ((letter==null)?"No ":"Yes ");
    }
    
    public byte getAtRow(){ return atRow;}
    public byte getAtColumn(){ return atColumn;}
    
    public void setAtRow(byte atRow){ this.atRow = atRow;}
    public void setAtColumn(byte atColumn){ this.atColumn = atColumn;}
}
