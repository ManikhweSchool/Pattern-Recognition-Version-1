package com.manikhweschool.patternrecognition.games;

import com.manikhweschool.patternrecognition.Music;
import com.manikhweschool.patternrecognition.moves.DecreasingMove;
import com.manikhweschool.patternrecognition.buildingblocks.CartesianPlane;
import com.manikhweschool.patternrecognition.buildingblocks.Plane;
import com.manikhweschool.patternrecognition.space.RegionMovingStrategy;
import java.util.ArrayList;

public class DecreasingPointBasedGame extends PointBasedGame{
    

    public DecreasingPointBasedGame() throws Exception{
        
        DecreasingMove move = new DecreasingMove();
        move.fillDirections(cartesianPlane.getBall().getCurrentRow(), 
        cartesianPlane.getBall().getCurrentColumn());
        
        cartesianPlane.getBall().setMove(move);  
    }
    
    public DecreasingPointBasedGame(boolean positionGame,int width, int height,
    byte forwardSteps, byte backwardSteps,Music music, 
    long startTime,ArrayList<RegionMovingStrategy> moves) throws Exception{
        super(positionGame,new CartesianPlane(width, height,
        forwardSteps, backwardSteps,music,moves), startTime);

        DecreasingMove move = new DecreasingMove();
        move.fillDirections(cartesianPlane.getBall().getCurrentRow(), 
        cartesianPlane.getBall().getCurrentColumn());
        cartesianPlane.getBall().setMove(move);   
    }
}
