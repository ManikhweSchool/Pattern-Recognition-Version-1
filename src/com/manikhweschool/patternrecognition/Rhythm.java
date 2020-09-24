package com.manikhweschool.patternrecognition;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Rhythm {

	private ArrayList<Long> times;
        private ArrayList<Boolean> isQuestionable;
	private int nextTimeIndex;
	
	public Rhythm() {
            times = new ArrayList<>();
            isQuestionable = new ArrayList<>();
            nextTimeIndex  = -1;
            
	}
        
        public Rhythm(RhythmType rhythmType) {
            times = new ArrayList<>();
            isQuestionable = new ArrayList<>();
            nextTimeIndex  = -1;
            createRhythm(rhythmType);
	}
	
	public Rhythm(ArrayList<Long> times) {
            this.times = times;
            isQuestionable = new ArrayList<>();
            nextTimeIndex  = -1;
	}
        
        private void createRhythm(RhythmType rhythmType){
            
            switch(rhythmType){
                case Rhythm_One : createRhythmOne(); break;
                case Rhythm_Two : createRhythmTwo(); break;
                default : createRhythmThree();
                
            }
        }
        
        public void repeatPattern(long initialTime, long timeBetweenPatterns,long maxPeriod){
        
            
            ArrayList<Long> originalTimes = (ArrayList<Long>) times.clone();
            ArrayList<Boolean> originalIsQuestionable = (ArrayList<Boolean>) isQuestionable.clone();
            
            if(originalTimes.isEmpty())
                System.out.println("Error : Tap at least twice.");
            
            long constantTime = initialTime-times.get(0);
            long currentTime = maxPeriod;
            int iterationIndex = 1;
            
            do{  
                for(int i = 0; i < originalTimes.size();i++){
                   currentTime = constantTime*iterationIndex+originalTimes.get(i);
                   if(currentTime<=maxPeriod)
                       addTime(currentTime, originalIsQuestionable.get(i));
                   else
                       break;
                }
                iterationIndex++;
            }while(currentTime<=maxPeriod);
            
        }
        
        public boolean getIsQuestionable(int index){
        
            return isQuestionable.get(index);
        }
	
	public void addTime(long time, boolean isQuestionable) {
		times.add(time);
                this.isQuestionable.add(isQuestionable);
	}
        
        public void setIsQuestionable(boolean isQuestionable, int index){
            this.isQuestionable.set(index, isQuestionable);
        }
	
	public void clearTimes() {
		times.clear();
                isQuestionable.clear();
	}
        
        public List<Long> getTimes(){
            
            return Collections.unmodifiableList(times);
            
        }
        
        public List<Boolean> getIsQuestionable(){
            
            return Collections.unmodifiableList(isQuestionable);
            
        }
        
        public long getCurrentTime(){ return times.get(nextTimeIndex);}
        
        public long getNextTime() {
		
            return times.get(((++nextTimeIndex)%times.size()));                                                                                                                                                                                                                                                                                                                                                                                                            
	}
        
        public void increaseTimesBy(long time){
            
            for(int i = 0; i < times.size();i++)
                times.set(i, times.get(i)+time);
        }
        
        public void decreaseTimesBy(long time){
            
            ArrayList<Long> newTimes = (ArrayList<Long>)times.clone();
            boolean areAllTimesValid = true;
            
            for(int i = 0; i < newTimes.size();i++){
                long newTime = newTimes.get(i)-time;
                if(newTime<0){
                    areAllTimesValid = false;
                    break;
                }
                newTimes.set(i, newTime);
                
            }
            
            if(areAllTimesValid)
                times = newTimes;
        }
        
        public void createRhythmOne(){
        
            if(!times.isEmpty())
                times.clear();
            
            long sum = 0;
            
            for(int index = 1; index <= 3000;index++){
                sum += 1000;
                times.add(sum);
            }
        }
        
        public void createRhythmTwo(){
        
            if(!times.isEmpty())
                times.clear();
            
            long sum = 0;
            
            for(int index = 1; index <= 3000;index++){
                sum += (index%2!=0)?1000:500;
                times.add(sum);
            }
        }
        
        public void createRhythmThree(){
        
            if(!times.isEmpty())
                times.clear();

            long sum = 0;

            for(int index = 1; index <= 3000;index++){

                if(index%3==1) sum += 3000;
                else if(index%3==2) sum += 2000;
                else sum += 100;

                times.add(sum);
            }
        }
}

