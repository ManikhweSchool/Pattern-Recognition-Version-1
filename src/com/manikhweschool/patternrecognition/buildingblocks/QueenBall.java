package com.manikhweschool.patternrecognition.buildingblocks;

import com.manikhweschool.patternrecognition.buildingblocks.Ball;
import com.manikhweschool.patternrecognition.moves.QueenMove;
import com.manikhweschool.patternrecognition.moves.ClockwiseMove;

import java.util.*;
import javafx.scene.paint.Color;

public class QueenBall extends Ball implements Cloneable{

    private QueenMove move;
    
	
    public QueenBall( Color color, double radius, int currentRow, 
    int currentColumn) throws Exception{

        this(color,radius,new ClockwiseMove(), currentRow, currentColumn);
    }
        
    public QueenBall(Color color,
    double radius, QueenMove move, int currentRow, 
    int currentColumn) throws Exception{
        this(color, radius,
        move, 1, 1, currentRow, currentColumn);
    }
        
    public QueenBall(Color color,
    double radius, QueenMove move, double deltaX, 
    double deltaY, int currentRow, int currentColumn) 
    throws Exception{
        super(color, radius, deltaX, deltaY, currentRow, currentColumn);
        fillBallColor();
        if(move == null)
            throw new Exception("Make sure you provided a move.");
        this.move = move;
        move.fillDirections(currentRow, currentColumn);
    }
        
       
    public QueenMove getMove() {
	return move;
    }

    public void setMove(QueenMove move) {
	this.move = move;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
    
        return super.clone();
    }
}
