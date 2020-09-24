package com.manikhweschool.patternrecognition.numbers;

public class Number4 extends Number{
    
    public Number4(byte row, byte column){
        super(row,column,"com/manikhweschool/patternrecognition/numbers/four.jpg");
    }
    
    @Override
    public byte getNumber(){ return 4;} 
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
    }
}
