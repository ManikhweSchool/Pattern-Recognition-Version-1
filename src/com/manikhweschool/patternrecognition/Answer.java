package com.manikhweschool.patternrecognition;

public class Answer {
   
    protected long time;
    
    protected Answer(long time){
        setTime(time);
    }
    
    private void setTime(long time){
        this.time = time;
    }
    
    public long getTime(){ return time;}
    
    @Override
    public String toString(){
        return "Time : " + time;
    }
    
}
