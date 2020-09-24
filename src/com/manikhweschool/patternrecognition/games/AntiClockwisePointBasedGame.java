package com.manikhweschool.patternrecognition.games;

import com.manikhweschool.patternrecognition.Music;
import com.manikhweschool.patternrecognition.moves.AntiClockwiseMove;
import com.manikhweschool.patternrecognition.buildingblocks.CartesianPlane;
import com.manikhweschool.patternrecognition.space.RegionMovingStrategy;
import java.util.ArrayList;

public class AntiClockwisePointBasedGame extends PointBasedGame{
    

    public AntiClockwisePointBasedGame() throws Exception{
       
        AntiClockwiseMove move = new AntiClockwiseMove();
        move.fillDirections(cartesianPlane.getBall().getCurrentRow(), 
        cartesianPlane.getBall().getCurrentColumn());
        
        cartesianPlane.getBall().setMove(move);  
    }
    
    public AntiClockwisePointBasedGame(boolean positionGame, int width, int height,
    byte forwardSteps, byte backwardSteps, Music music,
    long startTime,ArrayList<RegionMovingStrategy> moves) throws Exception{
        super(positionGame,new CartesianPlane(width, height,
        forwardSteps, backwardSteps,music, moves), startTime);

        AntiClockwiseMove move = new AntiClockwiseMove();
        move.fillDirections(cartesianPlane.getBall().getCurrentRow(), 
        cartesianPlane.getBall().getCurrentColumn());
        cartesianPlane.getBall().setMove(move);   
    }
    
}
