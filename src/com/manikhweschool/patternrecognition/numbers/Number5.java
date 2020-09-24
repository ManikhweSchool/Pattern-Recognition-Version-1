package com.manikhweschool.patternrecognition.numbers;

public class Number5 extends Number{
    
    public Number5(byte row, byte column){
        super(row,column,"com/manikhweschool/patternrecognition/numbers/five.jpg");
    }
    
    @Override
    public byte getNumber(){ return 5;}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
    }
}
