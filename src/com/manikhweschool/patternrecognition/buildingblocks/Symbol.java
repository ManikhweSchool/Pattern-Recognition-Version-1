package com.manikhweschool.patternrecognition.buildingblocks;

import javafx.scene.image.ImageView;

public abstract class Symbol {
    
    protected byte row;
    protected byte column;
    protected ImageView imageView;
    
    protected Symbol(byte row, byte column, String imageLocation){
    
        this.column = column;
        this.row = row;
        this.imageView = new ImageView(imageLocation);
    }
    
    public byte getRow(){ return row;}
    public byte getColumn(){ return column;}
    public ImageView getImageView(){ return imageView;}
    
    
    
     public boolean isBelowYEqualMinusX(byte row, byte column){
    
        boolean isBelow = false;
        
        switch(column){
            case 0 :
                if(row > 0)
                    isBelow = true;
                break;
            case 1 :
                if(row > 1)
                    isBelow = true;
                break;
            case 2 :
                if(row > 2)
                    isBelow = true;
                break;
            case 3 :
                if(row > 3)
                    isBelow = true;
                break;
            case 4 :
                if(row > 4)
                    isBelow = true;
                break;
            case 5 :
                if(row > 5)
                    isBelow = true;
                break;
            case 6 :
                if(row > 6)
                    isBelow = true;
                break;
            case 7 :
                if(row > 7)
                    isBelow = true;
                break;
            
        }
        
        return isBelow;
    }
    
    public boolean isAboveYEqualMinusX(byte row, byte column){
    
        boolean isAbove = false;
        
        switch(column){
            case 1 :
                if(row<1)
                    isAbove = true;
                break;
            case 2 :
                if(row<2)
                    isAbove = true;
                break;
            case 3 :
                if(row<3)
                    isAbove = true;
                break;
            case 4 :
                if(row<4)
                    isAbove = true;
                break;
            case 5 :
                if(row<5)
                    isAbove = true;
                break;
            case 6 : 
                if(row<6)
                    isAbove = true;
                break;
            case 7 :
                if(row<7)
                    isAbove = true;
                break;
            case 8 :
                if(row<8)
                    isAbove = true;
                break;
        }
        
        return isAbove;
    }
    
    public boolean isBelowYEqualX(byte row, byte column){
    
        boolean isBelow = false;
        
        switch(column){
        
            case 1 : 
                if(row==8)
                    isBelow = true;
                break;
            case 2 : 
                if(row>6)
                    isBelow = true;
                break;
            case 3 :
                if(row>5)
                    isBelow = true;
                break;
            case 4 :
                if(row>4)
                    isBelow = true;
                break;
            case 5 :
                if(row>3)
                    isBelow = true;
                break;
            case 6 :
                if(row>2)
                    isBelow = true;
                break;
            case 7 :
                if(row>1)
                    isBelow = true;
                break;
            case 8 :
                if(row>0)
                    isBelow = true;
                break;
        }
        
        return isBelow;
    }
    
    public boolean isAboveYEqualX(byte row, byte column){
    
        boolean isAbove = false;
        
        switch(column){
            case 0 : 
                if(row < 8)
                    isAbove = true;
                break;
            case 1 :
                if(row < 7)
                    isAbove = true;
                break;
            case 2 : 
                if(row < 6)
                    isAbove = true;
                break;
            case 3 :
                if(row < 5)
                    isAbove = true;
                break;
            case 4 :
                if(row < 4)
                    isAbove = true;
                break;
            case 5 :
                if(row < 3)
                    isAbove = true;
                break;
            case 6 :
                if(row < 2)
                    isAbove = true;
                break;
            case 7 :
                if(row < 1)
                    isAbove = true;
        }
        
        return isAbove;
    }

}
