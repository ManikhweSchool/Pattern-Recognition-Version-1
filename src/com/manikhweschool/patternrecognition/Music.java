package com.manikhweschool.patternrecognition;

import java.util.ArrayList;
import java.io.File;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.util.Duration;

public class Music{

    private long periodInMilliseconds;
    private String fileLocation;
    private ArrayList<Rhythm> rhythms;
    private int currentRhythmIndex = 0;
    private MediaPlayer mediaPlayer;
	
    
    public Music(String fileLocation) {
        
	this.fileLocation = new File(fileLocation).toURI().toString();
        mediaPlayer = new MediaPlayer(new Media(this.fileLocation));
        rhythms = new ArrayList<>();
        //mediaPlayer.setVolume(0);
    }
	
    public Music(Rhythm rhythm, String fileLocation, long periodInMilliseconds) {
	rhythms.add(rhythm);
	this.fileLocation = fileLocation;
        this.periodInMilliseconds = periodInMilliseconds;
        mediaPlayer = new MediaPlayer(new Media(fileLocation));
    }
    
    public void setStartTime(Duration startTime){
        mediaPlayer.setStartTime(startTime);
    }
    
    public void play(){ 
        mediaPlayer.play();
        
    }
    
    public void startOver(){
        mediaPlayer.seek(mediaPlayer.getStartTime());
        play();
    }
    
    public void stop(){
        mediaPlayer.stop();
    }
    
    
    public void addRhythm(Rhythm rhythm){
        rhythms.add(rhythm);
    }

    public long getPeriodInMilliseconds() {
	return periodInMilliseconds;
    }

    public void setPeriodInMilliseconds(long periodInMilliseconds) {
	this.periodInMilliseconds = periodInMilliseconds;
    }

    public String getFileLocation() {
	return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
	this.fileLocation = fileLocation;
    }
    
    public ArrayList<Rhythm> getRhythms(){
        return rhythms;
    }

    public Rhythm getRhythm(int index) {
	return rhythms.get(index);
    }

    public void setRhythm(Rhythm rhythm, int index) {
        rhythms.set(index, rhythm);
    }
    
    public Rhythm getCurrentRhythm(){
        return rhythms.get(currentRhythmIndex);
    }
    
    public void increaseTimesBy(long time){
    
        getCurrentRhythm().increaseTimesBy(time);
    }
    
    public void decreaseTimeBy(long time){
    
        getCurrentRhythm().decreaseTimesBy(time);
    }
    
    public int getCurrentRhythmIndex(){ return currentRhythmIndex;}
    
    public Rhythm getNextRhythm(){ 
        return rhythms.get((++currentRhythmIndex)%rhythms.size());}
}
