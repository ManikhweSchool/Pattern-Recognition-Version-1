package com.manikhweschool.patternrecognition.games;

import com.manikhweschool.patternrecognition.Music;
import com.manikhweschool.patternrecognition.moves.ClockwiseMove;
import com.manikhweschool.patternrecognition.buildingblocks.CartesianPlane;
import com.manikhweschool.patternrecognition.space.RegionMovingStrategy;
import java.util.ArrayList;

public class ClockwisePointBasedGame extends PointBasedGame{
    
    public ClockwisePointBasedGame(boolean positionGame,int width, int height,
    byte forwardSteps, byte backwardSteps,Music music, 
    long startTime,ArrayList<RegionMovingStrategy> moves) throws Exception{
        super(positionGame,new CartesianPlane(width, height,
        forwardSteps, backwardSteps,music,moves), startTime);

        ClockwiseMove move = new ClockwiseMove();
        move.fillDirections(cartesianPlane.getBall().getCurrentRow(), 
        cartesianPlane.getBall().getCurrentColumn());
        cartesianPlane.getBall().setMove(move);   
    }
}
