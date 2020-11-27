package com.manikhweschool.patternrecognition.space;

import com.manikhweschool.patternrecognition.buildingblocks.Ball;
import com.manikhweschool.patternrecognition.moves.RegionMove;

import java.util.*;
import javafx.scene.paint.Color;

public class RegionBall extends Ball implements Cloneable{

    private RegionMove move;
       
    public RegionBall(
    double radius, RegionMove move, int currentRow, 
    int currentColumn) throws Exception{
        this(Color.BLACK,radius,
        move, 1, 1, currentRow, currentColumn);
    }
        
    public RegionBall(Color color, 
    double radius, RegionMove move, double deltaX, 
    double deltaY, int currentRow, int currentColumn) 
    throws Exception{
        super(color,radius, deltaX, deltaY, currentRow, currentColumn);
                
        if(move == null)
            throw new Exception("Make sure you provided a move.");
        this.move = move;
        
    }
        
       
    public RegionMove getMove() {
	return move;
    }

    public void setMove(RegionMove move) {
	this.move = move;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
    
        return super.clone();
    }
}
