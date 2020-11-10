package com.manikhweschool.patternrecognition.result;

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
