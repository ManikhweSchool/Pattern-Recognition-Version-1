package com.manikhweschool.patternrecognition;

import com.manikhweschool.patternrecognition.result.DirectionBasedAnswer;
import com.manikhweschool.patternrecognition.buildingblocks.Location;
import com.manikhweschool.patternrecognition.result.DirectionMarks;
import com.manikhweschool.patternrecognition.result.PositionBasedAnswer;
import com.manikhweschool.patternrecognition.result.PositionMarks;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Player {

    private ArrayList<Map<Location,LinkedList<DirectionBasedAnswer>>> directionBasedAnswers;
    private ArrayList<Map<Location,LinkedList<PositionBasedAnswer>>> positionBasedAnswers;;
    private int currentQueueIndex = 0;
    
    private int numberOfCartesianPlanesToTrack;
    
    private DirectionMarks directionMarks;
    private PositionMarks positionMarks;
    
    public Player(){
    
        this(2);
    }
    
    public Player(int numberOfCartesianPlanes){
    
        if(positionBasedAnswers==null) positionBasedAnswers = new ArrayList<>();
        if(directionBasedAnswers==null) directionBasedAnswers = new ArrayList<>();
        
        if(numberOfCartesianPlanes>0)
            this.numberOfCartesianPlanesToTrack = numberOfCartesianPlanes;
        else
            numberOfCartesianPlanes = 5;
        
        for(int numberOfTimes = 1; numberOfTimes <= numberOfCartesianPlanes;numberOfTimes++){
            positionBasedAnswers.add(new LinkedHashMap<>());
            directionBasedAnswers.add(new LinkedHashMap<>());
        }
        
        directionMarks = new DirectionMarks();
        positionMarks = new PositionMarks();
    }
    
    public void addAnswer(int queueIndex, PositionBasedAnswer answer, Location location){ 
        
        if(queueIndex>=0 && queueIndex<numberOfCartesianPlanesToTrack) {  
            
            Map<Location, LinkedList<PositionBasedAnswer>> map = positionBasedAnswers.get(queueIndex);
            
            if(!map.containsKey(location))
                map.put(location, new LinkedList<>());
            
            map.get(location).add(answer);
        }
        else
            System.out.println("Number Of Allowed Cartesian Planes Exceeded.");
    }
    
    public void addAnswer(int queueIndex, DirectionBasedAnswer answer, Location location){ 
        
        if(queueIndex>=0 && queueIndex<numberOfCartesianPlanesToTrack) {  
            
            Map<Location, LinkedList<DirectionBasedAnswer>> map = directionBasedAnswers.get(queueIndex);
            
            if(!map.containsKey(location))
                map.put(location, new LinkedList<>());
            
            map.get(location).add(answer);
            
            
        }
            
        else
            System.out.println("Number Of Allowed Cartesian Planes Exceeded.");
    }
    
    public ArrayList<Map<Location,LinkedList<PositionBasedAnswer>>> getPositionBasedAnswers(){ return positionBasedAnswers;}
    public ArrayList<Map<Location,LinkedList<DirectionBasedAnswer>>> getDirectionBasedAnswers(){ return directionBasedAnswers;}
    
    public void setCurrentQueueIndex(int currentQueueIndex){
        this.currentQueueIndex = currentQueueIndex;
    }
    
    public int getCurrentQueueIndex(){ return currentQueueIndex;}
    
    public int getNumberOfCartesianPlanesToTrack(){
    
        return numberOfCartesianPlanesToTrack;
    }
    
    public DirectionMarks getDirectionMarks(){
        return directionMarks;
    }
    
    public PositionMarks getPositionMarks(){
        return positionMarks;
    }
    
    public void displayPlayerPositionBasedAnswers(){
        
        
    }
    
    public void grantPositionBasedMarks(Map<Integer,Map<Location, LinkedList<PositionBasedAnswer>>> positionBasedCorrectAnswers){
    
        for(Integer key : positionBasedCorrectAnswers.keySet()){
            System.out.println("Cartesian Plane No : " + key);
            for(Entry<Location, LinkedList<PositionBasedAnswer>> portionMap : positionBasedCorrectAnswers.get(key).entrySet()){
                System.out.println("\t\t" + portionMap.getKey());
                for(PositionBasedAnswer answer : portionMap.getValue())
                    System.out.println("\t\t\t" + answer);
                
                
            }
        }
        
        System.out.println("\n=================Player Position Answers====================\n");
        for(int i = 1; i <= positionBasedAnswers.size();i++){
            System.out.println("Cartesian Plane No : " + i);
            
            for(Entry<Location, LinkedList<PositionBasedAnswer>> portionMap : positionBasedAnswers.get(i-1).entrySet()){
                System.out.println("\t\t" + portionMap.getKey());
                
                for(PositionBasedAnswer answer : portionMap.getValue()){
                    System.out.println("\t\t\t" + answer);
                    
                }
                System.out.println();
                
            }
        }
        
        positionMarks.setPositionBasedCorrectAnswers(positionBasedCorrectAnswers);
        positionMarks.setPositionBasedAnswers(positionBasedAnswers);
    }
    
    public void grantDirectionBasedMarks(Map<Integer,Map<Location, LinkedList<DirectionBasedAnswer>>> directionBasedCorrectAnswers){
        
        for(Integer key : directionBasedCorrectAnswers.keySet()){
            System.out.println("Cartesian Plane No : " + key);
            
            for(Entry<Location, LinkedList<DirectionBasedAnswer>> portionMap : directionBasedCorrectAnswers.get(key).entrySet()){
                System.out.println("\t\t" + portionMap.getKey());
                
                for(DirectionBasedAnswer answer : portionMap.getValue()){
                    System.out.println("\t\t\t" + answer);
                    
                }
                
            }
        }
        
        System.out.println("\n=================Player Directions Answers====================\n");
        for(int i = 1; i <= directionBasedAnswers.size();i++){
            System.out.println("Cartesian Plane No : " + i);
            
            for(Entry<Location, LinkedList<DirectionBasedAnswer>> portionMap : directionBasedAnswers.get(i-1).entrySet()){
                System.out.println("\t\t" + portionMap.getKey());
                
                for(DirectionBasedAnswer answer : portionMap.getValue()){
                    System.out.println("\t\t\t" + answer);
                    
                }
                System.out.println();
                
            }
        }
        
        directionMarks.setDirectionBasedCorrectAnswers(directionBasedCorrectAnswers);
        directionMarks.setDirectionBasedAnswers(directionBasedAnswers);
    }
}
