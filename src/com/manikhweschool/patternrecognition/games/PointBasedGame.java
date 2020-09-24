package com.manikhweschool.patternrecognition.games;

import com.manikhweschool.patternrecognition.buildingblocks.CartesianPlane;

public class PointBasedGame  extends Game{
   
    protected Thread regionOneThread;
    protected RegionBallMovement regionOneTask;
    
    protected Thread regionTwoThread;
    protected RegionBallMovement regionTwoTask;
    
    protected Thread regionThreeThread;
    protected RegionBallMovement regionThreeTask;
    
    protected Thread regionFourThread;
    protected RegionBallMovement regionFourTask;
    
    protected Thread regionFiveThread;
    protected RegionBallMovement regionFiveTask;
    
    protected Thread regionSixThread;
    protected RegionBallMovement regionSixTask;
    
    protected Thread regionSevenThread;
    protected RegionBallMovement regionSevenTask;
    
    protected Thread regionEightThread;
    protected RegionBallMovement regionEightTask;
    
    protected Thread onPathPortionThread;
    protected QueenBallMovement onPathPortionTask;
    
    protected PointBasedGame() throws Exception{
        this((byte)(Math.random()*2)==0,new CartesianPlane(), System.currentTimeMillis());
        
    }
    
    protected PointBasedGame(boolean positionGame, CartesianPlane cartesianPlane,long startTime){
        super(positionGame,cartesianPlane, startTime);
        initializeAnimation();
        handleRhythmShiftButtons();
    }
    
    private void initializeAnimation(){
        
        if(cartesianPlane.getRegionOne().exist()){
            regionOneTask = new RegionBallMovement(
            cartesianPlane.getRegionOne(), cartesianPlane.getMusic().getRhythm(0),
            startTime);
            regionOneThread = new Thread(regionOneTask);
            regionOneThread.start();
        }
        if(cartesianPlane.getRegionTwo().exist()){
            regionTwoTask = new RegionBallMovement(
            cartesianPlane.getRegionTwo(), cartesianPlane.getMusic().getRhythm(0),
            startTime);
            regionTwoThread = new Thread(regionTwoTask);
            regionTwoThread.start();
            
        }
        if(cartesianPlane.getRegionThree().exist()){
            regionThreeTask = new RegionBallMovement(
            cartesianPlane.getRegionThree(), cartesianPlane.getMusic().getRhythm(0),
            startTime);
            regionThreeThread = new Thread(regionThreeTask);
            regionThreeThread.start();
        }
        if(cartesianPlane.getRegionFour().exist()){
            regionFourTask = new RegionBallMovement(
            cartesianPlane.getRegionFour(), cartesianPlane.getMusic().getRhythm(0),
            startTime);
            regionFourThread = new Thread(regionFourTask);
            regionFourThread.start();
        }
        if(cartesianPlane.getRegionFive().exist()){
            regionFiveTask = new RegionBallMovement(
            cartesianPlane.getRegionFive(), cartesianPlane.getMusic().getRhythm(0),
            startTime);
            regionFiveThread = new Thread(regionFiveTask);
            regionFiveThread.start();
        }
        if(cartesianPlane.getRegionSix().exist()){
            regionSixTask = new RegionBallMovement(
            cartesianPlane.getRegionSix(), cartesianPlane.getMusic().getRhythm(0),
            startTime);
            regionSixThread = new Thread(regionSixTask);
            regionSixThread.start();
            
        }
        if(cartesianPlane.getRegionSeven().exist()){
            regionSevenTask = new RegionBallMovement(
            cartesianPlane.getRegionSeven(), cartesianPlane.getMusic().getRhythm(0),
            startTime);
            regionSevenThread = new Thread(regionSevenTask);
            regionSevenThread.start();
            
        }
        if(cartesianPlane.getRegionEight().exist()){
            regionEightTask = new RegionBallMovement(
            cartesianPlane.getRegionEight(), cartesianPlane.getMusic().getRhythm(0),
            startTime);
            regionEightThread = new Thread(regionEightTask);
            regionEightThread.start();
            
        }
        
        onPathPortionTask = 
        new QueenBallMovement(cartesianPlane, 
        cartesianPlane.getMusic().getRhythm(0),startTime);
        onPathPortionThread = new Thread(onPathPortionTask);
        onPathPortionThread.start();
    }
    
    private void handleRhythmShiftButtons(){
    
    
        if(rightShiftButton.getOnAction()==null)
            rightShiftButton.setOnAction(e->{
            pause();
            cartesianPlane.getMusic().stop();
            rhythmShiftAmount += 100;
            rhythmShiftAmountInfo.setText("Current Rhythm Shift Amount Is " + rhythmShiftAmount + " Milliseconds.");
        });
        
        if(leftShiftButton.getOnAction()==null)
            leftShiftButton.setOnAction(e->{
            pause();
            cartesianPlane.getMusic().stop();
            rhythmShiftAmount -= 100;
            rhythmShiftAmountInfo.setText("Current Rhythm Shift Amount Is " + rhythmShiftAmount + " Milliseconds.");
            
        });
    
        if(applyRhythmShiftButton.getOnAction()==null)
            applyRhythmShiftButton.setOnAction(e->{
            if(rhythmShiftAmount < 0) {
                cartesianPlane.getMusic().getCurrentRhythm().decreaseTimesBy(Math.abs(rhythmShiftAmount));
                cartesianPlane.refresh();
                initializeAnimation();
                cartesianPlane.getMusic().startOver();
            }
            else if(rhythmShiftAmount>0){
                cartesianPlane.getMusic().getCurrentRhythm().increaseTimesBy(rhythmShiftAmount);
                cartesianPlane.refresh();
                initializeAnimation();
                cartesianPlane.getMusic().startOver();
            }
        });
        
    }
    
    public void pause(){
        
        if(cartesianPlane.getRegionOne().exist())
            regionOneTask.pause();
        if(cartesianPlane.getRegionTwo().exist())
            regionTwoTask.pause();
        if(cartesianPlane.getRegionThree().exist())
            regionThreeTask.pause();
        if(cartesianPlane.getRegionFour().exist())
            regionFourTask.pause();
        if(cartesianPlane.getRegionFive().exist())
            regionFiveTask.pause();
        if(cartesianPlane.getRegionSix().exist())
            regionSixTask.pause();
        if(cartesianPlane.getRegionSeven().exist())
            regionSevenTask.pause();
        if(cartesianPlane.getRegionEight().exist())
            regionEightTask.pause();
        onPathPortionTask.pause();
    }
    
}
