package com.manikhweschool.patternrecognition.numbers;

public class Number7 extends Number{
    
    public Number7(byte row, byte column){
        super(row,column,"com/manikhweschool/patternrecognition/numbers/seven.jpg");
    }
    
    @Override
    public byte getNumber(){ return 7;}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
    }
}
