package com.manikhweschool.patternrecognition.letters;

import com.manikhweschool.patternrecognition.buildingblocks.Symbol;

public abstract class Letter extends Symbol{
    
    protected Letter(byte row, byte column, String imageLocation){
        super(row,column,imageLocation);
    }
    
    public abstract char getLetter();
    public abstract void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column);
   
}
