package com.manikhweschool.patternrecognition.numbers;

public class Number2 extends Number{
    
    public Number2(byte row, byte column){
        super(row,column,"com/manikhweschool/patternrecognition/numbers/two.jpg");
    }
    
    @Override
    public byte getNumber(){ return 2;}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
    }
}
