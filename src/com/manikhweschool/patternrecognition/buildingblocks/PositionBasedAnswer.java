package com.manikhweschool.patternrecognition.buildingblocks;

import com.manikhweschool.patternrecognition.Answer;

public class PositionBasedAnswer extends Answer{
    
    private byte row;
    private byte column;
    
    public PositionBasedAnswer(long time, byte row, byte column){
        super(time);
        this.row = row;
        this.column = column;
    }
    
    public byte getRowAnswer(){ return row;}
    public byte getColumnAnswer(){ return column;}
    
    @Override
    public String toString(){
        return super.toString()+"\tRow : " + row + "\tColumn : " + column;
    }
}
