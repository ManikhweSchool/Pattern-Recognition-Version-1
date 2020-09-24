package com.manikhweschool.patternrecognition.numbers;

public class Number1 extends Number{
    
    public Number1(byte row, byte column){
        super(row,column,"com/manikhweschool/patternrecognition/numbers/one.jpg");
        
    }
    
    @Override
    public byte getNumber(){ return 1;}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
    }
}
