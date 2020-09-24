package com.manikhweschool.patternrecognition.numbers;

public class Number3 extends Number{
    
    public Number3(byte row, byte column){
        super(row,column,"com/manikhweschool/patternrecognition/numbers/three.png");
    }
    
    @Override
    public byte getNumber(){ return 3;}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
    }
}
