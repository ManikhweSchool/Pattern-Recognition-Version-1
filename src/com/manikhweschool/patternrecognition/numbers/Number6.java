package com.manikhweschool.patternrecognition.numbers;

public class Number6 extends Number{
    
    public Number6(byte row, byte column){
        super(row,column,"com/manikhweschool/patternrecognition/numbers/six.jpg");
    }
    
    @Override
    public byte getNumber(){ return 6;}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
    }
}
