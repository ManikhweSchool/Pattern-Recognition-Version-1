package com.manikhweschool.patternrecognition;

import com.manikhweschool.patternrecognition.result.DirectionBasedAnswer;
import com.manikhweschool.patternrecognition.result.PositionBasedAnswer;
import java.util.LinkedList;

public interface Trackable {
    
    public LinkedList<PositionBasedAnswer> getPositionBasedAnswers();
    public LinkedList<DirectionBasedAnswer> getDirectionBasedAnswers();
    public void addAnswer(PositionBasedAnswer answer);
    public void addAnswer(DirectionBasedAnswer answer);
    
}
