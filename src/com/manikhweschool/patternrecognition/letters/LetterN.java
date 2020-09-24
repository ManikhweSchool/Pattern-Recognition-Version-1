package com.manikhweschool.patternrecognition.letters;

public class LetterN extends Letter{
    
    public LetterN(byte row, byte column){
        super(row, column,"com/manikhweschool/patternrecognition/letters/N.jpg");
    }
    
    @Override
    public char getLetter(){ return 'N';}
    
    @Override
    public void findCorrespondingCell(byte[] correspondingCellIndeces, byte row, byte column){
    
        if(correspondingCellIndeces.length != 2){
            System.out.println("Error : Corresponding Cell Indeces Array Has To Have Only Two Element.");
            System.exit(0);
        }
            
        
        if(row > 0 && column > 0){ 
            correspondingCellIndeces[0] = (byte)(row-1);
            correspondingCellIndeces[1] = (byte)(column-1);
        }
        else{
            correspondingCellIndeces[0] = row;
            correspondingCellIndeces[1] = column;
        }
    }
}
