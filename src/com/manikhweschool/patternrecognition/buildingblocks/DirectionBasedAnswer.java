package com.manikhweschool.patternrecognition.buildingblocks;

import com.manikhweschool.patternrecognition.Answer;

public class DirectionBasedAnswer extends Answer{
    
    private byte directionAsAnswer;
    
    public DirectionBasedAnswer(long time, byte directionAsAnswer){
        super(time);
        this.directionAsAnswer = directionAsAnswer;
    }
    
    public byte getDirectionAnswer(){ return directionAsAnswer;}
    
    @Override
    public String toString(){
        return super.toString()+"\tDirection : " + directionAsAnswer;
    }
}
