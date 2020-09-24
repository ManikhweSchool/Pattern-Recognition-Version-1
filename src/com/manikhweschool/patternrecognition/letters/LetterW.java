package com.manikhweschool.patternrecognition.letters;

public class LetterW extends Letter{
    
    public LetterW(byte row, byte column){
        super(row, column,"com/manikhweschool/patternrecognition/letters/W.jpg");
    }
    
    @Override
    public char getLetter(){ return 'W';}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
        if(correspondingCellIndeces.length != 2){
            System.out.println("Error : Corresponding Cell Indeces Array Has To Have Only Two Element.");
            System.exit(0);
        }
            
        correspondingCellIndeces[0] = row;
        if(column < 8) correspondingCellIndeces[1] = (byte)(column+1);
        else correspondingCellIndeces[1] = column;
    }
    
}
