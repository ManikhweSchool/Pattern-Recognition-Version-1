package com.manikhweschool.patternrecognition.numbers;

import com.manikhweschool.patternrecognition.buildingblocks.Symbol;

public abstract class Number extends Symbol{
    
    protected Number(byte row, byte column, String imageLocation){
        super(row,column,imageLocation);
         
        
    }
    
    public abstract byte getNumber();
    public abstract void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column);
}
