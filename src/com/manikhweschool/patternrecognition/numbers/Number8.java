package com.manikhweschool.patternrecognition.numbers;

public class Number8 extends Number{
    
    public Number8(byte row, byte column){
        super(row,column,"com/manikhweschool/patternrecognition/numbers/eight.jpg");
    }
    
    @Override
    public byte getNumber(){ return 8;}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
    }
}
