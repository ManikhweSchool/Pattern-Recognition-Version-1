package com.manikhweschool.patternrecognition.letters;

public class LetterL extends Letter{
    
    public LetterL(byte row, byte column){
    
        super(row, column,"com/manikhweschool/patternrecognition/letters/L.jpg");
    }
    
    @Override
    public char getLetter(){ return 'L';}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
        if(correspondingCellIndeces.length != 2){
            System.out.println("Error : Corresponding Cell Indeces Array Has To Have Only Two Element.");
            System.exit(0);
        }
            
        correspondingCellIndeces[1] = column;
        if(row > 0) correspondingCellIndeces[0] = (byte)(row-1);
        else correspondingCellIndeces[0] = row;
    }
}
