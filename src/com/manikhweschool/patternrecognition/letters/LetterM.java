package com.manikhweschool.patternrecognition.letters;

public class LetterM extends Letter{
    
    public LetterM(byte row, byte column){
        super(row,column,"com/manikhweschool/patternrecognition/letters/M.jpg");
    }
    
    @Override
    public char getLetter(){ return 'M';}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
        if(correspondingCellIndeces.length != 2){
            System.out.println("Error : Corresponding Cell Indeces Array Has To Have Only Two Element.");
            System.exit(0);
        }
            
        correspondingCellIndeces[1] = column;
        if(row < 8) correspondingCellIndeces[0] = (byte)(row+1);
        else correspondingCellIndeces[0] = row;
    }
}
