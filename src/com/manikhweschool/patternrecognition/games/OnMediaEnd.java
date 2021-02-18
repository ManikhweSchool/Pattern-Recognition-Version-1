package com.manikhweschool.patternrecognition.games;

import java.util.ArrayList;

public class OnMediaEnd   implements Runnable{

    private QueenBallMovement queenBallMovement;
    private ArrayList<RegionBallMovement> regionBallsMovement;
    
    public OnMediaEnd(QueenBallMovement queenBallMovement,
    ArrayList<RegionBallMovement> regionBallsMovement){
        this.queenBallMovement = queenBallMovement;
        this.regionBallsMovement = regionBallsMovement;
    }
    
    @Override
    public void run() {
        
        queenBallMovement.pause();
        
        for(RegionBallMovement movement : regionBallsMovement)
            movement.pause();
    }
    
}
