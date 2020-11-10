package com.manikhweschool.patternrecognition.buildingblocks;

import com.manikhweschool.patternrecognition.result.PositionBasedAnswer;
import com.manikhweschool.patternrecognition.result.DirectionBasedAnswer;
import com.manikhweschool.patternrecognition.Music;
import com.manikhweschool.patternrecognition.Player;
import com.manikhweschool.patternrecognition.Trackable;
import com.manikhweschool.patternrecognition.space.*;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class CartesianPlane extends GridPane implements Trackable{
    
    private Music music;
    private Player player;
    
    private RegionColor regionOneColor;
    private RegionColor regionTwoColor;
    private RegionColor regionThreeColor;
    private RegionColor regionFourColor;
    private RegionColor regionFiveColor;
    private RegionColor regionSixColor;
    private RegionColor regionSevenColor;
    private RegionColor regionEightColor;
    
    private Color regionOneBallColor;
    private Color regionTwoBallColor;
    private Color regionThreeBallColor;
    private Color regionFourBallColor;
    private Color regionFiveBallColor;
    private Color regionSixBallColor;
    private Color regionSevenBallColor;
    private Color regionEightBallColor;
    
    private final ArrayList<RegionColor> allColors = new ArrayList<>(Arrays.asList(
                  RegionColor.Blue,RegionColor.Burlywood, 
                  RegionColor.Chartreuse, RegionColor.Deep_Pink, 
                  RegionColor.Green, RegionColor.Light_Skyblue,RegionColor.Magenta,
                  RegionColor.Maroon, RegionColor.Medium_Violetred,RegionColor.Red, 
                  RegionColor.Salmon,RegionColor.Violet,RegionColor.Yellow));
    private ArrayList<Color> colors;
  
    
    private Map<RegionColor, Boolean> ballColors;
    
    
    private byte reflectionAxis;
    
    private String displayableMove;
    
    public static int cartesianPlaneNumber = -1;
    private static byte numberOfTries = 1;
    private static Plane planeType = Plane.Plain_Plane;
    
    private final int minimumX = -4;
    private final int maximumX = 4;
    private final int minimumY = -4;
    private final int maximumY = 4;

    private final double deltaXY = 1.0;
    
    private QueenBall ball;
    
    public static boolean isInfinite = true;
    
    public byte REFERENCE_ROW;
    public byte REFERENCE_COLUMN;
    private byte currentRow;
    private byte currentColumn;
    
    // The order in which regions and path are visited.
    private ArrayList<Byte> order;
    private Map<Byte, Trackable> orderMap;
    
    private Location currentPortionToTrack;
    private byte currentRegionIndex;
    // Useful for detecting whether a player's answer is correct.
    // Sometimes a region to track might not have moving strategy directions.
    // So, in that case we don't want our program to throw an exception.
    private byte currentRegionIndexBackup;
    private int numberOfAllowedMoves;
    
    private Point[][] points;
    private Cell[][] coordinates;
    
    private RegionOne regionOne;
    private RegionTwo regionTwo;
    private RegionThree regionThree;
    private RegionFour regionFour;
    private RegionFive regionFive;
    private RegionSix regionSix;
    private RegionSeven regionSeven;
    private RegionEight regionEight;
    
    private ArrayList<Integer> regionOneAnswers;
    private ArrayList<Integer> regionTwoAnswers;
    private ArrayList<Integer> regionThreeAnswers;
    private ArrayList<Integer> regionFourAnswers;
    private ArrayList<Integer> regionFiveAnswers;
    private ArrayList<Integer> regionSixAnswers;
    private ArrayList<Integer> regionSevenAnswers;
    private ArrayList<Integer> regionEightAnswers;
    private ArrayList<Integer> queenBallAnswers;
    
    // Number of moves a ball does before reversing.
    private byte forwardSteps;
    // Number of reverse steps a ball does.
    private byte backwardSteps;
    
    private LinkedList<PositionBasedAnswer> positionBasedAnswers;
    private LinkedList<DirectionBasedAnswer> directionBasedAnswers;
    private byte currentAnswerIndex;
    
    // Contains numbers corresponding to letters(e.g. letter C corresponds to number 1).
    private ArrayList<Byte> letterNumbers;
    // Contains numbers that are allowed as pictures on a cartesian plane.
    private ArrayList<Byte> numbers;
    
    private ArrayList<RegionMovingStrategy> movingStrategies;
    private int width; 
    private int height;
    
    public CartesianPlane(int width, int height,
    byte forwardSteps, byte backwardSteps, Music music,
    ArrayList<RegionMovingStrategy> movingStrategies) throws Exception{
        
        refresh(width,height,forwardSteps,backwardSteps,music,movingStrategies);
    }
    
    private void refresh(int width, int height,
    byte forwardSteps, byte backwardSteps, Music music,
    ArrayList<RegionMovingStrategy> movingStrategies) throws Exception{
    
        if(!(width==height && width > 0))
            throw new Exception("Make sure you have a "
            + "positive width which is equals to your "
            + "height.");
        this.width = width;
        this.height = height;
        
        if(music != null)
            this.music = music;
        else
            throw new Exception("Music Can't Be Null.");
        
        if(movingStrategies==null || movingStrategies.isEmpty())
            throw new Exception("Error : Make Sure Moving Strategies Are Specifies.");
        this.movingStrategies = movingStrategies;
            
             
        setPrefSize(width,height);
        setMinSize(width, height);
        
        if(forwardSteps==backwardSteps && backwardSteps==0)
            throw new Exception("Both Forward Steps And Backward Steps Can't Be Negetive.");
        else if(forwardSteps>=backwardSteps && backwardSteps>=0){
            this.forwardSteps = forwardSteps;
            this.backwardSteps = backwardSteps;
        }
        else
            throw new Exception("Forward Steps And Backward Steps Values Aren't Allowed");
        
        setStyle("-fx-border-color: red");
        
        initiateSomeDataFields(movingStrategies);
        
        fillMatrix();
        addCells();
        shadePath();
        
        classifyPoints();
        
        orderRegions();
        trackOnNextPortion();
        
        locateBall(new QueenBall(Color.BLACK,20, currentRow, currentColumn));
        
        queenBallAnswers = new ArrayList<>();
        
        // THERE ARE FEW DIRECTIONS THAN THERE SUPPOSE TO BE ON THE LIST "queenBallAnswers".
        for(Integer direction : ball.getMove().getDirections())
            queenBallAnswers.add(direction);
    }
    
    public void refresh(){
    
        
        try {
            refresh(width,height,forwardSteps,backwardSteps,music,movingStrategies);
        } catch (Exception ex) {
            Logger.getLogger(CartesianPlane.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error : The refresh Method Is Throwing An Exception.");
        }
    }
    
    @Override
    public void addAnswer(PositionBasedAnswer answer){
        positionBasedAnswers.add(answer);
    }
    
    @Override
    public void addAnswer(DirectionBasedAnswer answer){
        directionBasedAnswers.add(answer);
    }
    
    @Override
    public LinkedList<PositionBasedAnswer> getPositionBasedAnswers(){ return positionBasedAnswers;}
    @Override
    public LinkedList<DirectionBasedAnswer> getDirectionBasedAnswers(){ return directionBasedAnswers;}
    
    public Music getMusic(){ return music;}
    
    public void setMusic(Music music){ 
        this.music = music;
        
        if(regionOne.exist()) regionOne.setMusic(music);
        if(regionTwo.exist()) regionTwo.setMusic(music);
        if(regionThree.exist()) regionThree.setMusic(music);
        if(regionFour.exist()) regionFour.setMusic(music);
        if(regionFive.exist()) regionFive.setMusic(music);
        if(regionSix.exist()) regionSix.setMusic(music);
        if(regionSeven.exist()) regionSeven.setMusic(music);
        if(regionEight.exist()) regionEight.setMusic(music);
    }
    
    public Player getPlayer(){ return player;}
    
    public void setPlayer(Player player){ 
        this.player = player;
        
        if(regionOne.exist()) regionOne.setPlayer(player);
        if(regionTwo.exist()) regionTwo.setPlayer(player);
        if(regionThree.exist()) regionThree.setPlayer(player);
        if(regionFour.exist()) regionFour.setPlayer(player);
        if(regionFive.exist()) regionFive.setPlayer(player);
        if(regionSix.exist()) regionSix.setPlayer(player);
        if(regionSeven.exist()) regionSeven.setPlayer(player);
        if(regionEight.exist()) regionEight.setPlayer(player);
    }
    
    public String getDisplayableMove(){ return displayableMove;}
    private void setDisplayableMove(){
        
        displayableMove = "";
        
        switch(getCurrentPortionToTrack()){
            case Region_One :
                if(regionOne.getRegionOneMove().getRegionMovingStrategy()
                ==RegionMovingStrategy.Column_By_Column)
                displayableMove += "Column By Column";
                else
                    displayableMove += "Row By Row";
                
                break;
            case Region_Two :
                if(regionTwo.getRegionTwoMove().getRegionMovingStrategy()
                ==RegionMovingStrategy.Column_By_Column)
                displayableMove += "Column By Column";
                else
                    displayableMove += "Row By Row";
                
                break;
            case Region_Three :
                if(regionThree.getRegionThreeMove().getRegionMovingStrategy()
                ==RegionMovingStrategy.Column_By_Column)
                displayableMove += "Column By Column";
                else
                    displayableMove += "Row By Row";
                
                break;
            case Region_Four :
                if(regionFour.getRegionFourMove().getRegionMovingStrategy()
                ==RegionMovingStrategy.Column_By_Column)
                displayableMove += "Column By Column";
                else
                    displayableMove += "Row By Row";
                
                break;
            case Region_Five :
                if(regionFive.getRegionFiveMove().getRegionMovingStrategy()
                ==RegionMovingStrategy.Column_By_Column)
                displayableMove += "Column By Column";
                else
                    displayableMove += "Row By Row";
                
                break;
            case Region_Six :
                if(regionSix.getRegionSixMove().getRegionMovingStrategy()
                ==RegionMovingStrategy.Column_By_Column)
                displayableMove += "Column By Column";
                else
                    displayableMove += "Row By Row";
                
                break;
            case Region_Seven :
                if(regionSeven.getRegionSevenMove().getRegionMovingStrategy()
                ==RegionMovingStrategy.Column_By_Column)
                displayableMove += "Column By Column";
                else
                    displayableMove += "Row By Row";
                
                break;
            case Region_Eight :
                if(regionEight.getRegionEightMove().getRegionMovingStrategy()
                ==RegionMovingStrategy.Column_By_Column)
                displayableMove += "Column By Column";
                else
                    displayableMove += "Row By Row";
                
                break;
            default :
                
                if(getBall() != null){
                    displayableMove += getBall().getMove().toString();
                    
                }
                
        }
            
        if(!(forwardSteps==1 && backwardSteps==0))
            displayableMove += " Forward Steps = " + forwardSteps + " Backward Steps = " + backwardSteps;
        else
             displayableMove += " Forward Steps Only ";
    }
    
    public boolean isCurrentBallInvisible(){
    
        Ball ball;
        
        if(regionOne.getIsCurrentPortionToTrack()) ball = regionOne.getRegionBall(); 
        else if(regionTwo.getIsCurrentPortionToTrack()) ball = regionTwo.getRegionBall(); 
        else if(regionThree.getIsCurrentPortionToTrack()) ball = regionThree.getRegionBall(); 
        else if(regionFour.getIsCurrentPortionToTrack()) ball = regionFour.getRegionBall(); 
        else if(regionFive.getIsCurrentPortionToTrack()) ball = regionFive.getRegionBall(); 
        else if(regionSix.getIsCurrentPortionToTrack()) ball = regionSix.getRegionBall(); 
        else if(regionSeven.getIsCurrentPortionToTrack()) ball = regionSeven.getRegionBall(); 
        else if(regionEight.getIsCurrentPortionToTrack()) ball = regionEight.getRegionBall(); 
        else ball = getBall();
        
        
        return ball.getOpacity()<0.2;
    }
    
    public static void setPlaneType(Plane planeType){
        CartesianPlane.planeType = planeType;
    }
    
    public static void setNumberOfTries(byte numberOfTries){ 
    
        CartesianPlane.numberOfTries = numberOfTries;
    }
    
    public static byte getNumberOfTries(){ return numberOfTries;}
    
    public static void decreaseTries(){ if(numberOfTries>0)numberOfTries--;}
    
    public Map<Location, LinkedList<PositionBasedAnswer>> retrieveCorrectPositionBasedAnswers(){
    
        Map<Location, LinkedList<PositionBasedAnswer>> correctAnswers = new LinkedHashMap<>();
        
        for(Byte orderIndex : order)
           correctAnswers.put(toCellLocation(orderIndex), orderMap.get(orderIndex).getPositionBasedAnswers());
        
        return correctAnswers;
    }
    
    public PositionBasedAnswer findNextPositionAnswer(){
    
        Map<Location, LinkedList<PositionBasedAnswer>> correctAnswers = retrieveCorrectPositionBasedAnswers();
        
        LinkedList<PositionBasedAnswer> allAnswers = correctAnswers.get(currentPortionToTrack);
        
        System.out.println();
        
        for(PositionBasedAnswer answer : allAnswers)
            System.out.print("{Row : " + answer.getRowAnswer() + ", Column : " + answer.getColumnAnswer() + "} ");
        System.out.println();
        

        return allAnswers.getLast();
    }
    
    public Map<Location, LinkedList<DirectionBasedAnswer>> retrieveCorrectDirectionBasedAnswers(){
    
        Map<Location, LinkedList<DirectionBasedAnswer>> correctAnswers = new LinkedHashMap<>();
        
        for(Byte orderIndex : order)
           correctAnswers.put(toCellLocation(orderIndex), orderMap.get(orderIndex).getDirectionBasedAnswers());
        
        return correctAnswers;
    }
    
    public DirectionBasedAnswer findNextDirectionAnswer(){
    
        Map<Location, LinkedList<DirectionBasedAnswer>> correctAnswers = retrieveCorrectDirectionBasedAnswers();
        
        LinkedList<DirectionBasedAnswer> allAnswers = correctAnswers.get(currentPortionToTrack);
        
        System.out.println();
        
        for(DirectionBasedAnswer answer : allAnswers)
            System.out.print(answer.getDirectionAnswer() + " ");
        System.out.println();
        

        return allAnswers.getLast();
        
    }
    
    public void increaseCurrentAnswerIndex(){ currentAnswerIndex++;}
    public int findCurrentDirectionAnswer(){
        
        switch(currentPortionToTrack){
            case Region_One : return regionOneAnswers.get(currentAnswerIndex);
            case Region_Two : return regionTwoAnswers.get(currentAnswerIndex);
            case Region_Three : return regionThreeAnswers.get(currentAnswerIndex);
            case Region_Four : return regionFourAnswers.get(currentAnswerIndex);
            case Region_Five : return regionFiveAnswers.get(currentAnswerIndex);
            case Region_Six : return regionSixAnswers.get(currentAnswerIndex);
            case Region_Seven : return regionSevenAnswers.get(currentAnswerIndex);
            case Region_Eight : return regionEightAnswers.get(currentAnswerIndex);
            default : return queenBallAnswers.get(currentAnswerIndex);
        }
    }
    
    /* Either User findNextDirection() or both findCurrentDirectionAnswer() 
    and increaseCurrentAnswerIndex() to find the corrent direction answer.
    All the three methods are not useful if findNextDirectionAnswer() is
    on the client program.*/
    public int findNextDirection(){
    
        switch(currentPortionToTrack){
            case Region_One : return regionOneAnswers.get(currentAnswerIndex++);
            case Region_Two : return regionTwoAnswers.get(currentAnswerIndex++);
            case Region_Three : return regionThreeAnswers.get(currentAnswerIndex++);
            case Region_Four : return regionFourAnswers.get(currentAnswerIndex++);
            case Region_Five : return regionFiveAnswers.get(currentAnswerIndex++);
            case Region_Six : return regionSixAnswers.get(currentAnswerIndex++);
            case Region_Seven : return regionSevenAnswers.get(currentAnswerIndex++);
            case Region_Eight : return regionEightAnswers.get(currentAnswerIndex++);
            default : return queenBallAnswers.get(currentAnswerIndex++);
        }
        
        
    }
    
    private Location toCellLocation(byte orderIndex){
        
        Location cellLocation;
        
        switch(orderIndex){
            
            case 1 : cellLocation = Location.Region_One; break;
            case 2 : cellLocation = Location.Region_Two; break;
            case 3 : cellLocation = Location.Region_Three; break;
            case 4 : cellLocation = Location.Region_Four; break;
            case 5 : cellLocation = Location.Region_Five; break;
            case 6 : cellLocation = Location.Region_Six; break;
            case 7 : cellLocation = Location.Region_Seven; break;
            case 8 : cellLocation = Location.Region_Eight; break;
            default : cellLocation = Location.On_Path; 
        }
        
        return cellLocation;
    }
    
    private void initiateSomeDataFields(ArrayList<RegionMovingStrategy> movingStrategies){
    
        REFERENCE_ROW = 1/*(byte)(1+Math.random()*7)*/;
        REFERENCE_COLUMN = 1/*(byte)(1+Math.random()*7)*/;
        currentRow = REFERENCE_ROW;
        currentColumn = REFERENCE_COLUMN;
        
        currentRegionIndex = 0;
        currentRegionIndexBackup = currentRegionIndex;
        numberOfAllowedMoves = 0;
        
        positionBasedAnswers = new LinkedList<>();
        directionBasedAnswers = new LinkedList<>();
        
        currentAnswerIndex = 0;
        
        order = new ArrayList<>();
        orderMap = new HashMap<>();
        ballColors = new HashMap<>();
        
        points = new Point[9][9];
        coordinates = new Cell[9][9];
    
        regionOne = new RegionOne(movingStrategies.get(0));
        regionTwo = new RegionTwo(movingStrategies.get(1));
        regionThree = new RegionThree(movingStrategies.get(2));
        regionFour = new RegionFour(movingStrategies.get(3));
        regionFive = new RegionFive(movingStrategies.get(4));
        regionSix = new RegionSix(movingStrategies.get(5));
        regionSeven = new RegionSeven(movingStrategies.get(6));
        regionEight = new RegionEight(movingStrategies.get(7));
        
        orderMap.put((byte)0, this);
        orderMap.put((byte)1, regionOne);
        orderMap.put((byte)2, regionTwo);
        orderMap.put((byte)3, regionThree);
        orderMap.put((byte)4, regionFour);
        orderMap.put((byte)5, regionFive);
        orderMap.put((byte)6, regionSix);
        orderMap.put((byte)7, regionSeven);
        orderMap.put((byte)8, regionEight);
        
        colors= new ArrayList<>(Arrays.asList(
        Color.BLUE,Color.BURLYWOOD, Color.TEAL, Color.SLATEGREY,
        Color.CHARTREUSE, Color.DEEPPINK, 
        Color.GREEN, Color.LIGHTSKYBLUE,Color.MAGENTA,
        Color.MAROON,Color.RED, Color.AQUA,Color.DARKORANGE,
        Color.SALMON,Color.YELLOW));
        
        reflectionAxis = (byte)(Math.random()*4);
        ArrayList<RegionColor> regionColors = new ArrayList<>();
        regionColors.add(RegionColor.Violet);
        regionColors.add(RegionColor.Chartreuse);
        regionColors.add(RegionColor.Light_Skyblue);
        regionColors.add(RegionColor.Salmon);
        Collections.shuffle(regionColors);
        
        
        if(cartesianPlaneNumber%3==1){
            letterNumbers = new ArrayList<>();
            letterNumbers.add((byte)1);
            letterNumbers.add((byte)2);
            letterNumbers.add((byte)3);
            letterNumbers.add((byte)4);
            letterNumbers.add((byte)5);
            letterNumbers.add((byte)6);
            letterNumbers.add((byte)7);
            letterNumbers.add((byte)8);
            Collections.shuffle(letterNumbers);
        }
        
        else if(cartesianPlaneNumber%3==2){
            numbers = new ArrayList<>();
            numbers.add((byte)1);
            numbers.add((byte)2);
            numbers.add((byte)3);
            numbers.add((byte)4);
            numbers.add((byte)5);
            numbers.add((byte)6);
            numbers.add((byte)7);
            numbers.add((byte)8);
            Collections.shuffle(numbers);
        }
        
        
        switch(reflectionAxis){
            case 0 : 
                regionOneColor = regionColors.get(0);
                regionEightColor = regionOneColor;
                regionTwoColor = regionColors.get(1);
                regionSevenColor = regionTwoColor;
                regionThreeColor = regionColors.get(2);
                regionSixColor = regionThreeColor;
                regionFourColor = regionColors.get(3);
                regionFiveColor = regionFourColor;
                break;
            case 1 :
                regionOneColor = regionColors.get(0);
                regionFourColor = regionOneColor;
                regionTwoColor = regionColors.get(1);
                regionThreeColor = regionTwoColor;
                regionSixColor = regionColors.get(2);
                regionSevenColor = regionSixColor;
                regionFiveColor= regionColors.get(3);
                regionEightColor = regionFiveColor;
                break;
            case 2 : 
                regionOneColor = regionColors.get(0);
                regionTwoColor = regionOneColor;
                regionThreeColor = regionColors.get(1);
                regionEightColor = regionThreeColor;
                regionFourColor = regionColors.get(2);
                regionSevenColor = regionFourColor;
                regionFiveColor = regionColors.get(3);
                regionSixColor = regionFiveColor;
                break;
            default :
                regionOneColor = regionColors.get(0);
                regionSixColor = regionOneColor;
                regionTwoColor = regionColors.get(1);
                regionFiveColor = regionTwoColor;
                regionThreeColor = regionColors.get(2);
                regionFourColor = regionThreeColor;
                regionSevenColor = regionColors.get(3);
                regionEightColor = regionSevenColor;
        }
        
        
        Collections.shuffle(allColors);
        
        for(RegionColor color : allColors)
            ballColors.put(color, false);
    }
    
    public static void incrementCartesianPlaneNumber(){ 
        
        if(planeType == Plane.All_Planes)
            cartesianPlaneNumber++;
    
    }
    
    private void setNumberOfAllowedMoves(){
    
        
        switch(currentPortionToTrack){
            case Region_One : 
                regionOne.setNumberOfAllowedMoves();
                numberOfAllowedMoves = regionOne.getNumberOfAllowedMoves();
                break;
            case Region_Two : 
                regionTwo.setNumberOfAllowedMoves();
                numberOfAllowedMoves = regionTwo.getNumberOfAllowedMoves();
                break;
            case Region_Three : 
                regionThree.setNumberOfAllowedMoves();
                numberOfAllowedMoves = regionThree.getNumberOfAllowedMoves();
                break;
            case Region_Four : 
                regionFour.setNumberOfAllowedMoves();
                numberOfAllowedMoves = regionFour.getNumberOfAllowedMoves();
                break;
            case Region_Five : 
                regionFive.setNumberOfAllowedMoves();
                numberOfAllowedMoves = regionFive.getNumberOfAllowedMoves();
                break;
            case Region_Six: 
                regionSix.setNumberOfAllowedMoves();
                numberOfAllowedMoves = regionSix.getNumberOfAllowedMoves();
                break;
            case Region_Seven : 
                regionSeven.setNumberOfAllowedMoves();
                numberOfAllowedMoves = regionSeven.getNumberOfAllowedMoves();
                break;
            case Region_Eight : 
                regionEight.setNumberOfAllowedMoves();
                numberOfAllowedMoves = regionEight.getNumberOfAllowedMoves();
                break;
            default :
                for(Cell[] cells : coordinates)
                    for(Cell cell : cells)
                        if(cell.getIsOnPath())
                            numberOfAllowedMoves++;
                
                numberOfAllowedMoves *= 2;
                numberOfAllowedMoves += 5+(byte)(Math.random()*6);
        }
        
    }
    
    public void decreaseNumberOfAllowedMoves(){
        if(numberOfAllowedMoves>0)
            numberOfAllowedMoves--;
        
        switch(currentPortionToTrack){
            case Region_One : regionOne.decreaseNumberOfAllowedMoves();
            case Region_Two : regionTwo.decreaseNumberOfAllowedMoves();
            case Region_Three : regionThree.decreaseNumberOfAllowedMoves();
            case Region_Four : regionFour.decreaseNumberOfAllowedMoves();
            case Region_Five : regionFive.decreaseNumberOfAllowedMoves();
            case Region_Six : regionSix.decreaseNumberOfAllowedMoves();
            case Region_Seven : regionSeven.decreaseNumberOfAllowedMoves();
            case Region_Eight : regionEight.decreaseNumberOfAllowedMoves();
        }
    }
    
    public boolean canTrackOnNextPortion(){
    
        boolean goToNextPortion;
        
        switch(currentPortionToTrack){
            case Region_One : 
                goToNextPortion = regionOne.getRegionBall().getHasFaded() 
                                  && regionOne.isBallStartingOver(); 
                break;
            case Region_Two : 
                goToNextPortion = regionTwo.getRegionBall().getHasFaded()
                                  && regionTwo.isBallStartingOver(); 
                break;
            case Region_Three : 
                goToNextPortion = regionThree.getRegionBall().getHasFaded()
                                  && regionThree.isBallStartingOver(); 
                break;
            case Region_Four : 
                goToNextPortion = regionFour.getRegionBall().getHasFaded()
                                  && regionFour.isBallStartingOver(); 
                break;
            case Region_Five : 
                goToNextPortion = regionFive.getRegionBall().getHasFaded()
                                  && regionFive.isBallStartingOver(); 
                break;
            case Region_Six : 
                goToNextPortion = regionSix.getRegionBall().getHasFaded()
                                  && regionSix.isBallStartingOver(); 
                break;
            case Region_Seven : 
                goToNextPortion = regionSeven.getRegionBall().getHasFaded()
                                  && regionSeven.isBallStartingOver(); 
                break;
            case Region_Eight : 
                goToNextPortion = regionEight.getRegionBall().getHasFaded()
                                  && regionEight.isBallStartingOver(); 
                break;
            default : goToNextPortion = getBall().getHasFaded() 
                                        && isBallStartingOver();
        }
        
        return goToNextPortion;
    }
    
    private boolean isBallStartingOver(){
    
        boolean yesOrNo = false;
        if(regionOne.exist() && regionOne.getRegionCells().size()>1)
            yesOrNo = regionOne.isBallStartingOver();
        else if(regionTwo.exist() && regionTwo.getRegionCells().size()>1)
            yesOrNo = regionTwo.isBallStartingOver();
        else if(regionThree.exist() && regionThree.getRegionCells().size()>1)
            yesOrNo = regionThree.isBallStartingOver();
        else if(regionFour.exist() && regionFour.getRegionCells().size()>1)
            yesOrNo = regionFour.isBallStartingOver();
        else if(regionFive.exist() && regionFive.getRegionCells().size()>1)
            yesOrNo = regionFive.isBallStartingOver();
        else if(regionSix.exist() && regionSix.getRegionCells().size()>1)
            yesOrNo = regionSix.isBallStartingOver();
        else if(regionSeven.exist() && regionSeven.getRegionCells().size()>1)
            yesOrNo = regionSeven.isBallStartingOver();
        else if(regionEight.exist() && regionEight.getRegionCells().size()>1)
            yesOrNo = regionEight.isBallStartingOver();
        
        return yesOrNo;
    }
    
    public int getNumberOfAllowedMoves(){
        return numberOfAllowedMoves;
    }
    
    public boolean isPathOnTrack(){
    
        boolean pathToTrack = true;
        
        if(regionOne.exist() && regionOne.getIsCurrentPortionToTrack())
            pathToTrack = false;
        else if(regionTwo.exist() && regionTwo.getIsCurrentPortionToTrack())
            pathToTrack = false;
        else if(regionThree.exist() && regionThree.getIsCurrentPortionToTrack())
            pathToTrack = false;
        else if(regionFour.exist() && regionFour.getIsCurrentPortionToTrack())
            pathToTrack = false;
        else if(regionFive.exist() && regionFive.getIsCurrentPortionToTrack())
            pathToTrack = false;
        else if(regionSix.exist() && regionSix.getIsCurrentPortionToTrack())
            pathToTrack = false;
        else if(regionSeven.exist() && regionSeven.getIsCurrentPortionToTrack())
            pathToTrack = false;
        else if(regionEight.exist() && regionEight.getIsCurrentPortionToTrack())
            pathToTrack = false;
        
        return pathToTrack;
    }
    
    private void setCurrentPortionToTrack(){
        
        switch(order.get(currentRegionIndex)){
                case 1 : 
                    currentPortionToTrack = Location.Region_One; 
                    regionOne.setIsCurrentPortionToTrack(true);
                    if(regionTwo.getIsCurrentPortionToTrack())
                        regionTwo.setIsCurrentPortionToTrack(false);
                    if(regionThree.getIsCurrentPortionToTrack())
                        regionThree.setIsCurrentPortionToTrack(false);
                    if(regionFour.getIsCurrentPortionToTrack())
                        regionFour.setIsCurrentPortionToTrack(false);
                    if(regionFive.getIsCurrentPortionToTrack())
                        regionFive.setIsCurrentPortionToTrack(false);
                    if(regionSix.getIsCurrentPortionToTrack())
                        regionSix.setIsCurrentPortionToTrack(false);
                    if(regionSeven.getIsCurrentPortionToTrack())
                        regionSeven.setIsCurrentPortionToTrack(false);
                    if(regionEight.getIsCurrentPortionToTrack())
                        regionEight.setIsCurrentPortionToTrack(false);

                    break;
                case 2 : 
                    currentPortionToTrack = Location.Region_Two;
                    regionTwo.setIsCurrentPortionToTrack(true);
                    if(regionOne.getIsCurrentPortionToTrack())
                        regionOne.setIsCurrentPortionToTrack(false);
                    if(regionThree.getIsCurrentPortionToTrack())
                        regionThree.setIsCurrentPortionToTrack(false);
                    if(regionFour.getIsCurrentPortionToTrack())
                        regionFour.setIsCurrentPortionToTrack(false);
                    if(regionFive.getIsCurrentPortionToTrack())
                        regionFive.setIsCurrentPortionToTrack(false);
                    if(regionSix.getIsCurrentPortionToTrack())
                        regionSix.setIsCurrentPortionToTrack(false);
                    if(regionSeven.getIsCurrentPortionToTrack())
                        regionSeven.setIsCurrentPortionToTrack(false);
                    if(regionEight.getIsCurrentPortionToTrack())
                        regionEight.setIsCurrentPortionToTrack(false);

                    break;
                case 3 : 
                    currentPortionToTrack = Location.Region_Three;
                    regionThree.setIsCurrentPortionToTrack(true);
                    if(regionOne.getIsCurrentPortionToTrack())
                        regionOne.setIsCurrentPortionToTrack(false);
                    if(regionTwo.getIsCurrentPortionToTrack())
                        regionTwo.setIsCurrentPortionToTrack(false);
                    if(regionFour.getIsCurrentPortionToTrack())
                        regionFour.setIsCurrentPortionToTrack(false);
                    if(regionFive.getIsCurrentPortionToTrack())
                        regionFive.setIsCurrentPortionToTrack(false);
                    if(regionSix.getIsCurrentPortionToTrack())
                        regionSix.setIsCurrentPortionToTrack(false);
                    if(regionSeven.getIsCurrentPortionToTrack())
                        regionSeven.setIsCurrentPortionToTrack(false);
                    if(regionEight.getIsCurrentPortionToTrack())
                        regionEight.setIsCurrentPortionToTrack(false);

                    break;
                case 4 : 
                    currentPortionToTrack = Location.Region_Four;
                    regionFour.setIsCurrentPortionToTrack(true);
                    if(regionTwo.getIsCurrentPortionToTrack())
                        regionTwo.setIsCurrentPortionToTrack(false);
                    if(regionThree.getIsCurrentPortionToTrack())
                        regionThree.setIsCurrentPortionToTrack(false);
                    if(regionOne.getIsCurrentPortionToTrack())
                        regionOne.setIsCurrentPortionToTrack(false);
                    if(regionFive.getIsCurrentPortionToTrack())
                        regionFive.setIsCurrentPortionToTrack(false);
                    if(regionSix.getIsCurrentPortionToTrack())
                        regionSix.setIsCurrentPortionToTrack(false);
                    if(regionSeven.getIsCurrentPortionToTrack())
                        regionSeven.setIsCurrentPortionToTrack(false);
                    if(regionEight.getIsCurrentPortionToTrack())
                        regionEight.setIsCurrentPortionToTrack(false);

                    break;
                case 5 : 
                    currentPortionToTrack = Location.Region_Five;
                    regionFive.setIsCurrentPortionToTrack(true);
                    if(regionTwo.getIsCurrentPortionToTrack())
                        regionTwo.setIsCurrentPortionToTrack(false);
                    if(regionThree.getIsCurrentPortionToTrack())
                        regionThree.setIsCurrentPortionToTrack(false);
                    if(regionFour.getIsCurrentPortionToTrack())
                        regionFour.setIsCurrentPortionToTrack(false);
                    if(regionOne.getIsCurrentPortionToTrack())
                        regionOne.setIsCurrentPortionToTrack(false);
                    if(regionSix.getIsCurrentPortionToTrack())
                        regionSix.setIsCurrentPortionToTrack(false);
                    if(regionSeven.getIsCurrentPortionToTrack())
                        regionSeven.setIsCurrentPortionToTrack(false);
                    if(regionEight.getIsCurrentPortionToTrack())
                        regionEight.setIsCurrentPortionToTrack(false);

                    break;
                case 6 : 
                    currentPortionToTrack = Location.Region_Six;
                    regionSix.setIsCurrentPortionToTrack(true);
                    if(regionTwo.getIsCurrentPortionToTrack())
                        regionTwo.setIsCurrentPortionToTrack(false);
                    if(regionThree.getIsCurrentPortionToTrack())
                        regionThree.setIsCurrentPortionToTrack(false);
                    if(regionFour.getIsCurrentPortionToTrack())
                        regionFour.setIsCurrentPortionToTrack(false);
                    if(regionFive.getIsCurrentPortionToTrack())
                        regionFive.setIsCurrentPortionToTrack(false);
                    if(regionOne.getIsCurrentPortionToTrack())
                        regionOne.setIsCurrentPortionToTrack(false);
                    if(regionSeven.getIsCurrentPortionToTrack())
                        regionSeven.setIsCurrentPortionToTrack(false);
                    if(regionEight.getIsCurrentPortionToTrack())
                        regionEight.setIsCurrentPortionToTrack(false);

                    break;
                case 7 : 
                    currentPortionToTrack = Location.Region_Seven;
                    regionSeven.setIsCurrentPortionToTrack(true);
                    if(regionTwo.getIsCurrentPortionToTrack())
                        regionTwo.setIsCurrentPortionToTrack(false);
                    if(regionThree.getIsCurrentPortionToTrack())
                        regionThree.setIsCurrentPortionToTrack(false);
                    if(regionFour.getIsCurrentPortionToTrack())
                        regionFour.setIsCurrentPortionToTrack(false);
                    if(regionFive.getIsCurrentPortionToTrack())
                        regionFive.setIsCurrentPortionToTrack(false);
                    if(regionSix.getIsCurrentPortionToTrack())
                        regionSix.setIsCurrentPortionToTrack(false);
                    if(regionOne.getIsCurrentPortionToTrack())
                        regionOne.setIsCurrentPortionToTrack(false);
                    if(regionEight.getIsCurrentPortionToTrack())
                        regionEight.setIsCurrentPortionToTrack(false);

                    break;
                case 8 : 
                    currentPortionToTrack = Location.Region_Eight;
                    regionEight.setIsCurrentPortionToTrack(true);
                    if(regionTwo.getIsCurrentPortionToTrack())
                        regionTwo.setIsCurrentPortionToTrack(false);
                    if(regionThree.getIsCurrentPortionToTrack())
                        regionThree.setIsCurrentPortionToTrack(false);
                    if(regionFour.getIsCurrentPortionToTrack())
                        regionFour.setIsCurrentPortionToTrack(false);
                    if(regionFive.getIsCurrentPortionToTrack())
                        regionFive.setIsCurrentPortionToTrack(false);
                    if(regionSix.getIsCurrentPortionToTrack())
                        regionSix.setIsCurrentPortionToTrack(false);
                    if(regionSeven.getIsCurrentPortionToTrack())
                        regionSeven.setIsCurrentPortionToTrack(false);
                    if(regionOne.getIsCurrentPortionToTrack())
                        regionOne.setIsCurrentPortionToTrack(false);

                    break;
                default : 
                    currentPortionToTrack = Location.On_Path;
                    
                    if(regionOne.getIsCurrentPortionToTrack())
                        regionOne.setIsCurrentPortionToTrack(false);
                    if(regionTwo.getIsCurrentPortionToTrack())
                        regionTwo.setIsCurrentPortionToTrack(false);
                    if(regionThree.getIsCurrentPortionToTrack())
                        regionThree.setIsCurrentPortionToTrack(false);
                    if(regionFour.getIsCurrentPortionToTrack())
                        regionFour.setIsCurrentPortionToTrack(false);
                    if(regionFive.getIsCurrentPortionToTrack())
                        regionFive.setIsCurrentPortionToTrack(false);
                    if(regionSix.getIsCurrentPortionToTrack())
                        regionSix.setIsCurrentPortionToTrack(false);
                    if(regionSeven.getIsCurrentPortionToTrack())
                        regionSeven.setIsCurrentPortionToTrack(false);
                    if(regionEight.getIsCurrentPortionToTrack())
                        regionEight.setIsCurrentPortionToTrack(false);


            }
        
        setDisplayableMove();
    }
    
    public boolean allPortionsVisited(){
        
        if(regionOne.exist() && !regionOne.getRegionBall().getHasFaded())
            return false;
        if(regionTwo.exist() && !regionTwo.getRegionBall().getHasFaded())
            return false;
        if(regionThree.exist() && !regionThree.getRegionBall().getHasFaded())
            return false;
        if(regionFour.exist() && !regionFour.getRegionBall().getHasFaded())
            return false;
        if(regionFive.exist() && !regionFive.getRegionBall().getHasFaded())
            return false;
        if(regionSix.exist() && !regionSix.getRegionBall().getHasFaded())
            return false;
        if(regionSeven.exist() && !regionSeven.getRegionBall().getHasFaded())
            return false;
        if(regionEight.exist() && !regionEight.getRegionBall().getHasFaded())
            return false;
        if(!getBall().getHasFaded())
            return false;
        return true;
    }
    
    public Location getCurrentPortionToTrack(){ return currentPortionToTrack;}
    
    public boolean trackOnNextPortion(){
    
        if(!(currentRegionIndex<order.size()))
            return false;
        trackOnNextPortion(order.get(currentRegionIndex));
        return true;
    }
    
    private void trackOnNextPortion(byte portionIndex){
    
        if(currentRegionIndex != 0){
            switch(order.get(currentRegionIndex-1)){
            case 1 : regionOne.setRegionColor(regionOneColor);break;
            case 2 : regionTwo.setRegionColor(regionTwoColor);break;
            case 3 : regionThree.setRegionColor(regionThreeColor);break;
            case 4 : regionFour.setRegionColor(regionFourColor);break;
            case 5 : regionFive.setRegionColor(regionFiveColor);break;
            case 6 : regionSix.setRegionColor(regionSixColor);break;
            case 7 : regionSeven.setRegionColor(regionSevenColor);break;
            case 8 : regionEight.setRegionColor(regionEightColor);break;
            default :
                
                for(int i = 0; i < coordinates.length;i++)
                    for(int j = 0; j < coordinates[i].length;j++)
                        if(coordinates[i][j].getIsOnPath() && !(i == REFERENCE_ROW && j == REFERENCE_COLUMN))
                            coordinates[i][j].setStyle("-fx-background-color:#DEB887");
                        else if(coordinates[i][j].getIsOnPath() && i == REFERENCE_ROW && j == REFERENCE_COLUMN)
                            coordinates[i][j].setStyle("-fx-background-color:white");
            }
        }
        
        switch(portionIndex){
            case 1 : regionOne.setRegionColor(RegionColor.None);break;
            case 2 : regionTwo.setRegionColor(RegionColor.None);break;
            case 3 : regionThree.setRegionColor(RegionColor.None);break;
            case 4 : regionFour.setRegionColor(RegionColor.None);break;
            case 5 : regionFive.setRegionColor(RegionColor.None);break;
            case 6 : regionSix.setRegionColor(RegionColor.None);break;
            case 7 : regionSeven.setRegionColor(RegionColor.None);break;
            case 8 : regionEight.setRegionColor(RegionColor.None);break;
            default :
                for(int i = 0; i < coordinates.length;i++)
                    for(int j = 0; j < coordinates[i].length;j++)
                        if(coordinates[i][j].getIsOnPath() && !(i == REFERENCE_ROW && j == REFERENCE_COLUMN))
                            coordinates[i][j].setStyle("-fx-background-color:white");
                        else if(coordinates[i][j].getIsOnPath() && i == REFERENCE_ROW && j == REFERENCE_COLUMN)
                            coordinates[i][j].setStyle("-fx-background-color:#DEB887");
        }
        
        setCurrentPortionToTrack();
        setNumberOfAllowedMoves();
        currentRegionIndex++;
    }
    
    public static String getRegionColor(RegionColor regionColor){
    
        String color = "-fx-background-color:";
        
        switch(regionColor){
            case Red : color += "red"; break;
            case Green : color += "green"; break;
            case Blue : color += "blue"; break;
            case Light_Skyblue : color += "#87CEFA"; break;   
            case Magenta : color += "#FF00FF"; break;
            case Salmon : color += "#FA8072"; break;
            case Medium_Violetred : color += "#C71585"; break;
            case Burlywood : color += "#DEB887"; break;
            case Deep_Pink : color += "#ff1493"; break;
            case Chartreuse : color += "#7FFF00"; break;
            case Maroon : color += "#800000"; break;  
            case Violet : color += "#EE82EE"; break;
            case None : color += "white"; break;
        } 
        
        return color;
    }
    
    public void setCurrentPortionToTrack(Location portion){
        currentPortionToTrack = portion;
        setCurrentPortionToTrack();
    }
    
    private void orderRegions(){
        
        order.add((byte)0);
        if(regionOne.exist()) order.add((byte)1);
        if(regionTwo.exist()) order.add((byte)2);
        if(regionThree.exist()) order.add((byte)3);
        if(regionFour.exist()) order.add((byte)4);
        if(regionFive.exist()) order.add((byte)5);
        if(regionSix.exist()) order.add((byte)6);
        if(regionSeven.exist()) order.add((byte)7);
        if(regionEight.exist()) order.add((byte)8);
        
        Collections.shuffle(order);
        
    }
        
    private void locateBall(QueenBall ball){
    
        this.ball = ball;
        
        ball.radiusXProperty().bind(
        coordinates[0][0].prefWidthProperty().add(
        coordinates[0][0].prefHeightProperty()).divide(8));
        
        ball.radiusYProperty().bind(
        coordinates[0][0].prefWidthProperty().add(
        coordinates[0][0].prefHeightProperty()).divide(8));
         
        coordinates[currentRow][currentColumn].getChildren().add(ball);
    }
    
    private Color findBallColor(RegionColor regionColor){
    
        Color color = null;
        int randomIndex = (int)(Math.random()*(colors.size()-1));
        
        for(RegionColor key : ballColors.keySet()){
        
            if(key!=regionColor && !ballColors.get(key)){
                switch(regionColor){
                    case Red : 
                        color = (colors.get(randomIndex)==Color.RED)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Green : color = (colors.get(randomIndex)==Color.GREEN)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Blue : color = (colors.get(randomIndex)==Color.BLUE)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Yellow : color = (colors.get(randomIndex)==Color.YELLOW)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Burlywood : color = (colors.get(randomIndex)==Color.BURLYWOOD)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Violet : color = (colors.get(randomIndex)==Color.VIOLET)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Chartreuse :color = (colors.get(randomIndex)==Color.CHARTREUSE)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Salmon : color = (colors.get(randomIndex)==Color.SALMON)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Light_Skyblue :color = (colors.get(randomIndex)==Color.LIGHTSKYBLUE)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Maroon : color = (colors.get(randomIndex)==Color.MAROON)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Magenta : color = (colors.get(randomIndex)==Color.MAGENTA)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Medium_Violetred : color = (colors.get(randomIndex)==Color.MEDIUMVIOLETRED)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    case Deep_Pink : color = (colors.get(randomIndex)==Color.DEEPPINK)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        break;
                    default : color = (colors.get(randomIndex)==Color.WHITE)?
                        colors.get((++randomIndex)%colors.size()):colors.get(randomIndex); 
                        
                }
                colors.remove(randomIndex);
                ballColors.put(key, true);
                break;
            }
                
        }
        
        return color;
    }
    
    public RegionOne getRegionOne(){ return regionOne;}
    public RegionTwo getRegionTwo(){ return regionTwo;}
    public RegionThree getRegionThree(){ return regionThree;}
    public RegionFour getRegionFour(){ return regionFour;}
    public RegionFive getRegionFive(){ return regionFive;}
    public RegionSix getRegionSix(){ return regionSix;}
    public RegionSeven getRegionSeven(){ return regionSeven;}
    public RegionEight getRegionEight(){ return regionEight;}
    
    private void chooseMove(RegionNumber regionNumber){
    
        if(regionNumber == RegionNumber.Region_One && 
        regionOne.getRegionOneMove().getRegionMovingStrategy() 
        == RegionMovingStrategy.Column_By_Column){
            if(regionOne.getRegionCells().size()==2)
                regionOne.getRegionOneMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionOne.getRegionOneMove().fillColumnByColumn(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_One && 
        regionOne.getRegionOneMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Row_By_Row){
            if(regionOne.getRegionCells().size()==2)
                regionOne.getRegionOneMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionOne.getRegionOneMove().fillRowByRow(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Two && 
        regionTwo.getRegionTwoMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Column_By_Column){
            if(regionTwo.getRegionCells().size()==2)
                regionTwo.getRegionTwoMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionTwo.getRegionTwoMove().fillColumnByColumn(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Two && 
        regionTwo.getRegionTwoMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Row_By_Row){
            if(regionTwo.getRegionCells().size()==2)
                regionTwo.getRegionTwoMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionTwo.getRegionTwoMove().fillRowByRow(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Three && 
        regionThree.getRegionThreeMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Column_By_Column){
            if(regionThree.getRegionCells().size()==2)
                regionThree.getRegionThreeMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionThree.getRegionThreeMove().fillColumnByColumn(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Three && 
        regionThree.getRegionThreeMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Row_By_Row){
            if(regionThree.getRegionCells().size()==2)
                regionThree.getRegionThreeMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionThree.getRegionThreeMove().fillRowByRow(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Four && 
        regionFour.getRegionFourMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Column_By_Column){
            if(regionFour.getRegionCells().size()==2)
                regionFour.getRegionFourMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionFour.getRegionFourMove().fillColumnByColumn(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Four && 
        regionFour.getRegionFourMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Row_By_Row){
            if(regionFour.getRegionCells().size()==2)
                regionFour.getRegionFourMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionFour.getRegionFourMove().fillRowByRow(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Five && 
        regionFive.getRegionFiveMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Column_By_Column){
            if(regionFive.getRegionCells().size()==2)
                regionFive.getRegionFiveMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionFive.getRegionFiveMove().fillColumnByColumn(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Five && 
        regionFive.getRegionFiveMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Row_By_Row){
            if(regionFive.getRegionCells().size()==2)
                regionFive.getRegionFiveMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionFive.getRegionFiveMove().fillRowByRow(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Six && 
        regionSix.getRegionSixMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Column_By_Column){
            if(regionSix.getRegionCells().size()==2)
                regionSix.getRegionSixMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionSix.getRegionSixMove().fillColumnByColumn(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Six && 
        regionSix.getRegionSixMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Row_By_Row){
            if(regionSix.getRegionCells().size()==2)
                regionSix.getRegionSixMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionSix.getRegionSixMove().fillRowByRow(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Seven && 
        regionSeven.getRegionSevenMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Column_By_Column){
            if(regionSeven.getRegionCells().size()==2)
                regionSeven.getRegionSevenMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionSeven.getRegionSevenMove().fillColumnByColumn(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Seven && 
        regionSeven.getRegionSevenMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Row_By_Row){
            if(regionSeven.getRegionCells().size()==2)
                regionSeven.getRegionSevenMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionSeven.getRegionSevenMove().fillRowByRow(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else if(regionNumber == RegionNumber.Region_Eight && 
        regionEight.getRegionEightMove().getRegionMovingStrategy()
        == RegionMovingStrategy.Column_By_Column){
            if(regionEight.getRegionCells().size()==2)
                regionEight.getRegionEightMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionEight.getRegionEightMove().fillColumnByColumn(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
        else{
            if(regionEight.getRegionCells().size()==2)
                regionEight.getRegionEightMove().addTwoOppositeDirections(currentRow, currentColumn);
            regionEight.getRegionEightMove().fillRowByRow(coordinates, forwardSteps, backwardSteps, isInfinite);
        }
    }
    
    private void classifyPoints(){
    
        int startRow = 0;
        int startColumn;      
        int nextPortionEndRow = -1;
        
        // Deal With Portion One.
        while(startRow < currentRow){
            startColumn = currentColumn+1;
            while(startColumn <= 8 && !coordinates[startRow][startColumn].getIsOnPath()){
                regionOne.addCell(coordinates[startRow][startColumn]);
                
                if(startRow==0 && startColumn == currentColumn+1){
                    regionOne.getRegionOneMove().setStartRow((byte)0);
                    regionOne.getRegionOneMove().setStartColumn((byte)(currentColumn+1));
                }
                       
                regionOne.getRegionOneMove().setEndColumn((byte)startColumn);
                startColumn++;
            }
            
            regionOne.getRegionOneMove().setEndRow((byte)(startRow-1));
            
            
            if(startColumn <= 8 && coordinates[startRow][startColumn].getIsOnPath() 
            && nextPortionEndRow == -1){
                
                if(startColumn+1<=8)
                    nextPortionEndRow = startRow;                
                else{
                    if(startRow+1 < currentRow)
                        nextPortionEndRow = startRow + 1;
                }
            }
          
            startRow++;
        }
        regionOne.setRegionColor(regionOneColor);
        regionOneBallColor = findBallColor(regionOneColor);
        
        if(regionOne.hasAtLeastOneCells() && regionOne.getRegionOneMove().exist()){
            regionOne.setCoordinates(coordinates);
            regionOne.cartegorizeCells();
            
            try{               
                RegionBall regionBall = new RegionBall(regionOneBallColor,20, regionOne.getRegionOneMove(), 
                deltaXY, deltaXY, regionOne.getRegionOneMove().getStartRow(), 
                regionOne.getRegionOneMove().getStartColumn());

                regionOne.setRegionBall(regionBall);
                addLetterPictures(regionOne, (byte)0);
                addNumberPictures(regionOne, (byte)0);
                
                regionOne.setRegionOneMove();
                chooseMove(RegionNumber.Region_One);
                regionOne.setSteps(forwardSteps, backwardSteps);
                regionOneAnswers = new ArrayList<>();
                
                for(Integer direction : regionOne.getRegionOneMove().getDirections())
                    regionOneAnswers.add(direction);
                
                regionBall.radiusXProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                regionBall.radiusYProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                
                coordinates[regionOne.getRegionOneMove().getStartRow()]
                [regionOne.getRegionOneMove().getStartColumn()].getChildren().add(regionBall);
                
            }catch(Exception ex){
                System.out.println("Cannot Create Region One Ball.");
            }
        
        }
            
        startRow = nextPortionEndRow;
        
        // Deal With Portion Two.
        if(nextPortionEndRow != -1){
            while(startRow < currentRow){
                startColumn = 8;
                while(startColumn > currentColumn && !coordinates[startRow][startColumn].getIsOnPath()){
                    regionTwo.addCell(coordinates[startRow][startColumn]);
                
                    if(startRow==nextPortionEndRow){
                        regionTwo.getRegionTwoMove().setStartRow((byte)nextPortionEndRow);
                        regionTwo.getRegionTwoMove().setStartColumn((byte)(startColumn ));
                    }

                    startColumn--;
                }

                regionTwo.getRegionTwoMove().setEndRow((byte)startRow);
                
                startRow++;
            }
            regionTwo.getRegionTwoMove().setEndColumn((byte)8);
        }
        regionTwo.setRegionColor(regionTwoColor);
        regionTwoBallColor = findBallColor(regionTwoColor);
        
        if(regionTwo.hasAtLeastOneCells() && regionTwo.getRegionTwoMove().exist()){
            regionTwo.setCoordinates(coordinates);
            regionTwo.cartegorizeCells();
            try{                
                RegionBall regionBall = new RegionBall(regionTwoBallColor,20, regionTwo.getRegionTwoMove(), 
                deltaXY, deltaXY, regionTwo.getRegionTwoMove().getStartRow(), 
                regionTwo.getRegionTwoMove().getStartColumn());
                
                regionTwo.setRegionBall(regionBall);
                
                addNumberPictures(regionTwo, (byte)1);
                addLetterPictures(regionTwo, (byte)1);
                
                regionTwo.setRegionTwoMove();
                
                chooseMove(RegionNumber.Region_Two);
                regionTwo.setSteps(forwardSteps, backwardSteps);
                regionTwoAnswers = new ArrayList<>();
                
                for(Integer direction : regionTwo.getRegionTwoMove().getDirections())
                    regionTwoAnswers.add(direction);
                
                regionBall.radiusXProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                regionBall.radiusYProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                
                coordinates[regionTwo.getRegionTwoMove().getStartRow()]
                [regionTwo.getRegionTwoMove().getStartColumn()].getChildren().add(regionBall);
            }catch(Exception ex){
                System.out.println("Cannot Create Region Two Ball.");
            }
        }
        
        
        startRow = 0;
        nextPortionEndRow = -1;
        
        // Deal With Portion Eight.
        while(startRow < currentRow){
            startColumn = currentColumn-1;
            while(startColumn >= 0 && !coordinates[startRow][startColumn].getIsOnPath()){
                regionEight.addCell(coordinates[startRow][startColumn]);
                
                    if(startRow==0)                       
                        regionEight.getRegionEightMove().setStartColumn((byte)(startColumn));                   
                    else if(startRow==currentRow-2)
                        regionEight.getRegionEightMove().setEndColumn((byte)startColumn);
                startColumn--;
            }
            
            if(startColumn >= 0 && coordinates[startRow][startColumn].getIsOnPath() 
            && nextPortionEndRow == -1){

                if(startColumn-1>=0)
                    nextPortionEndRow = startRow;                
                else{
                    if(startRow+1 < currentRow)
                        nextPortionEndRow = startRow + 1;
                }
            }
            
            startRow++;
        }
        regionEight.getRegionEightMove().setStartRow((byte)(0));
        regionEight.getRegionEightMove().setEndRow((byte)(currentRow - 2));
        regionEight.setRegionColor(regionEightColor);
        regionEightBallColor = findBallColor(regionEightColor);
        
        if(regionEight.hasAtLeastOneCells() && regionEight.getRegionEightMove().exist()){
            regionEight.setCoordinates(coordinates);
            regionEight.cartegorizeCells();
            try{
                RegionBall regionBall = new RegionBall(regionEightBallColor,20, regionEight.getRegionEightMove(), 
                deltaXY, deltaXY, regionEight.getRegionEightMove().getStartRow(), 
                regionEight.getRegionEightMove().getStartColumn());

                regionEight.setRegionBall(regionBall);
                
                addNumberPictures(regionEight, (byte)7);
                addLetterPictures(regionEight, (byte)7);
                
                regionEight.setRegionEightMove();
                chooseMove(RegionNumber.Region_Eight);
                regionEight.setSteps(forwardSteps, backwardSteps);
                regionEightAnswers = new ArrayList<>();
                
                for(Integer direction : regionEight.getRegionEightMove().getDirections())
                    regionEightAnswers.add(direction);
                
                regionBall.radiusXProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                regionBall.radiusYProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                
                
                coordinates[regionEight.getRegionEightMove().getStartRow()]
                [regionEight.getRegionEightMove().getStartColumn()].getChildren().add(regionBall);
            }catch(Exception ex){
                System.out.println("Cannot Create Region Eight Ball.");
            }
        }
        
        startRow = currentRow + 1;
        startColumn = 8;
        
        // Deal With Portion Three.
        if(!coordinates[startRow][startColumn].getIsOnPath()){
            while(startRow <= 8 && !coordinates[startRow][startColumn].getIsOnPath()){
               
                while(startColumn > currentColumn && !coordinates[startRow][startColumn].getIsOnPath()){
                    regionThree.addCell(coordinates[startRow][startColumn]);
                
                    if(startRow==currentRow + 1 ){
                        regionThree.getRegionThreeMove().setStartRow((byte)(currentRow + 1));
                        regionThree.getRegionThreeMove().setStartColumn((byte)(startColumn));
                    }
                    startColumn--;
                }
                
                regionThree.getRegionThreeMove().setEndRow((byte)startRow);
                startColumn = 8;
                startRow++;
            }
            regionThree.getRegionThreeMove().setEndColumn((byte)8);
        } 
        
        regionThree.setRegionColor(regionThreeColor);
        regionThreeBallColor = findBallColor(regionThreeColor);
        
        if(regionThree.hasAtLeastOneCells() && regionThree.getRegionThreeMove().exist()){
            regionThree.setCoordinates(coordinates);
            regionThree.cartegorizeCells();
            try{
                RegionBall regionBall = new RegionBall(regionThreeBallColor,20, regionThree.getRegionThreeMove(), 
                deltaXY, deltaXY, regionThree.getRegionThreeMove().getStartRow(), 
                regionThree.getRegionThreeMove().getStartColumn());

                regionThree.setRegionBall(regionBall);
                
                addNumberPictures(regionThree, (byte)2);
                addLetterPictures(regionThree, (byte)2);
                
                regionThree.setRegionThreeMove();
                chooseMove(RegionNumber.Region_Three);
                regionThree.setSteps(forwardSteps, backwardSteps);
                regionThreeAnswers = new ArrayList<>();
                
                for(Integer direction : regionThree.getRegionThreeMove().getDirections())
                    regionThreeAnswers.add(direction);
                
                regionBall.radiusXProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                regionBall.radiusYProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                
                coordinates[regionThree.getRegionThreeMove().getStartRow()]
                [regionThree.getRegionThreeMove().getStartColumn()].getChildren().add(regionBall);
            }catch(Exception ex){
                System.out.println("Cannot Create Region Three Ball.");
            }
        }
        
        startRow = currentRow + 2;
        startColumn = currentColumn+1;
        
        // Deal With Portion Four.
        if(startRow <= 8 && startColumn <= 8 && !coordinates[startRow][startColumn].getIsOnPath()){
            while(startRow <= 8 && !coordinates[startRow][startColumn].getIsOnPath()){
               
                while(startColumn <= 8 && !coordinates[startRow][startColumn].getIsOnPath()){
                    regionFour.addCell(coordinates[startRow][startColumn]);
                
                    if(startRow==currentRow + 2 && startColumn == currentColumn+1){
                        regionFour.getRegionFourMove().setStartRow((byte)(currentRow + 2));
                        regionFour.getRegionFourMove().setStartColumn((byte)(currentColumn+1));
                    }
                    regionFour.getRegionFourMove().setEndColumn((byte)startColumn);
                    startColumn++;
                }
                startColumn = currentColumn+1;
                regionFour.getRegionFourMove().setEndRow((byte)startRow);
                startRow++;
            }
        }
        regionFour.setRegionColor(regionFourColor);
        regionFourBallColor = findBallColor(regionFourColor);
        
        if(regionFour.hasAtLeastOneCells() && regionFour.getRegionFourMove().exist()){
            regionFour.setCoordinates(coordinates);
            regionFour.cartegorizeCells();
            try{
                RegionBall regionBall = new RegionBall(regionFourBallColor,20, regionFour.getRegionFourMove(), 
                deltaXY, deltaXY, regionFour.getRegionFourMove().getStartRow(), 
                regionFour.getRegionFourMove().getStartColumn());

                regionFour.setRegionBall(regionBall);
                
                addNumberPictures(regionFour, (byte)3);
                addLetterPictures(regionFour, (byte)3);
                
                regionFour.setRegionFourMove();
                chooseMove(RegionNumber.Region_Four);
                regionFour.setSteps(forwardSteps, backwardSteps);
                regionFourAnswers = new ArrayList<>();
                
                for(Integer direction : regionFour.getRegionFourMove().getDirections())
                    regionFourAnswers.add(direction);
                
                regionBall.radiusXProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                regionBall.radiusYProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                
                coordinates[regionFour.getRegionFourMove().getStartRow()]
                [regionFour.getRegionFourMove().getStartColumn()].getChildren().add(regionBall);
            }catch(Exception ex){
                System.out.println("Cannot Create Region Four Ball.");
            }
        }
        
        startRow = currentRow + 2;
        startColumn = currentColumn-1;
        
        // Deal With Portion Five.
        if(startRow <= 8 && startColumn >= 0 && !coordinates[startRow][startColumn].getIsOnPath()){
            while(startRow <= 8 && !coordinates[startRow][startColumn].getIsOnPath()){
               
                while(startColumn >= 0 && !coordinates[startRow][startColumn].getIsOnPath()){
                    regionFive.addCell(coordinates[startRow][startColumn]);
                
                    if(startRow==currentRow + 2){
                        regionFive.getRegionFiveMove().setStartRow((byte)(currentRow + 2));
                        regionFive.getRegionFiveMove().setStartColumn((byte)(startColumn));
                    }
                    
                    startColumn--;
                }
                
                startColumn = currentColumn-1;
                regionFive.getRegionFiveMove().setEndRow((byte)(startRow));
                startRow++;
            }
            regionFive.getRegionFiveMove().setEndColumn((byte)(currentColumn-1));
        } 
        
        regionFive.setRegionColor(regionFiveColor);
        regionFiveBallColor = findBallColor(regionFiveColor);
        
        if(regionFive.hasAtLeastOneCells() && regionFive.getRegionFiveMove().exist()){
            regionFive.setCoordinates(coordinates);
            regionFive.cartegorizeCells();
            try{
                RegionBall regionBall = new RegionBall(regionFiveBallColor,20, regionFive.getRegionFiveMove(), 
                deltaXY, deltaXY, regionFive.getRegionFiveMove().getStartRow(), 
                regionFive.getRegionFiveMove().getStartColumn());

                regionFive.setRegionBall(regionBall);
                
                addNumberPictures(regionFive, (byte)4);
                addLetterPictures(regionFive, (byte)4);
                
                regionFive.setRegionFiveMove();
                chooseMove(RegionNumber.Region_Five);
                regionFive.setSteps(forwardSteps, backwardSteps);
                regionFiveAnswers = new ArrayList<>();
                
                for(Integer direction : regionFive.getRegionFiveMove().getDirections())
                    regionFiveAnswers.add(direction);
                
                regionBall.radiusXProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                regionBall.radiusYProperty().bind(
                coordinates[0][0].prefWidthProperty().add(
                coordinates[0][0].prefHeightProperty()).divide(8));
                
                coordinates[regionFive.getRegionFiveMove().getStartRow()]
                [regionFive.getRegionFiveMove().getStartColumn()].getChildren().add(regionBall);
            }catch(Exception ex){
                System.out.println("Cannot Create Region Five Ball.");
            }
        }
        
        startRow = currentRow + 1;
        startColumn = 0;
        
        // Deal With Portion Six.
        if(!coordinates[startRow][startColumn].getIsOnPath()){
            while(startRow <= 8 && !coordinates[startRow][startColumn].getIsOnPath()){
               
                while(startColumn < currentColumn && !coordinates[startRow][startColumn].getIsOnPath()){
                    regionSix.addCell(coordinates[startRow][startColumn]);
                
                    regionSix.getRegionSixMove().setEndColumn((byte)startColumn);
                    
                    startColumn++;
                }
                startColumn = 0;
                startRow++;
            }

            regionSix.getRegionSixMove().setStartRow((byte)(currentRow + 1));
            regionSix.getRegionSixMove().setStartColumn((byte)(0));
            regionSix.getRegionSixMove().setEndRow((byte)(startRow-1));
        } 
        
        regionSix.setRegionColor(regionSixColor);
        regionSixBallColor = findBallColor(regionSixColor);
        
        if(regionSix.hasAtLeastOneCells() && regionSix.getRegionSixMove().exist()){
            regionSix.setCoordinates(coordinates);
            regionSix.cartegorizeCells();   
            try{
                    RegionBall regionBall = new RegionBall(regionSixBallColor,20, regionSix.getRegionSixMove(), 
                    deltaXY, deltaXY, regionSix.getRegionSixMove().getStartRow(), 
                    regionSix.getRegionSixMove().getStartColumn());

                    regionSix.setRegionBall(regionBall);
                    
                    addNumberPictures(regionSix, (byte)5);
                    addLetterPictures(regionSix, (byte)5);
                    
                    regionSix.setRegionSixMove();
                    chooseMove(RegionNumber.Region_Six);
                    regionSix.setSteps(forwardSteps, backwardSteps);
                    regionSixAnswers = new ArrayList<>();
                
                for(Integer direction : regionSix.getRegionSixMove().getDirections())
                    regionSixAnswers.add(direction);

                    regionBall.radiusXProperty().bind(
                    coordinates[0][0].prefWidthProperty().add(
                    coordinates[0][0].prefHeightProperty()).divide(8));
                    regionBall.radiusYProperty().bind(
                    coordinates[0][0].prefWidthProperty().add(
                    coordinates[0][0].prefHeightProperty()).divide(8));
                    
                    coordinates[regionSix.getRegionSixMove().getStartRow()]
                    [regionSix.getRegionSixMove().getStartColumn()].getChildren().add(regionBall);
                }catch(Exception ex){
                    System.out.println("Cannot Create Region Six Ball.");
                }
        }
        
        startRow = nextPortionEndRow;
        
        // Deal With Portion Seven.
        if(nextPortionEndRow != -1){
            while(startRow < currentRow){
                startColumn = 0;
                while(startColumn < currentColumn && !coordinates[startRow][startColumn].getIsOnPath()){
                    regionSeven.addCell(coordinates[startRow][startColumn]);
                
                    if(startRow==nextPortionEndRow){
                       
                        regionSeven.getRegionSevenMove().setStartColumn((byte)(0));
                    }
                    else if(startRow==currentRow-2)
                        regionSeven.getRegionSevenMove().setEndColumn((byte)(startColumn));
                    startColumn++;
                }

                startRow++;
            }
            
            regionSeven.getRegionSevenMove().setStartRow((byte)(nextPortionEndRow));
            regionSeven.getRegionSevenMove().setEndRow((byte)(currentRow-1));
            regionSeven.getRegionSevenMove().setStartColumn((byte)(0));
            regionSeven.getRegionSevenMove().setEndColumn((byte)(currentColumn-2));
            regionSeven.setRegionColor(regionSevenColor);
            regionSevenBallColor = findBallColor(regionSevenColor);
                       
            if(regionSeven.hasAtLeastOneCells() && regionSeven.getRegionSevenMove().exist()){
                regionSeven.setCoordinates(coordinates);
                regionSeven.cartegorizeCells();
                try{
                    RegionBall regionBall = new RegionBall(regionSevenBallColor,20, regionSeven.getRegionSevenMove(), 
                    deltaXY, deltaXY, regionSeven.getRegionSevenMove().getStartRow(), 
                    regionSeven.getRegionSevenMove().getStartColumn());
                    
                    regionSeven.setRegionBall(regionBall);
                    
                    addNumberPictures(regionSeven, (byte)6);
                    addLetterPictures(regionSeven, (byte)6);
                    
                    regionSeven.setRegionSevenMove();
                    chooseMove(RegionNumber.Region_Seven);
                    regionSeven.setSteps(forwardSteps, backwardSteps);
                    regionSevenAnswers = new ArrayList<>();
                
                for(Integer direction : regionSeven.getRegionSevenMove().getDirections())
                    regionSevenAnswers.add(direction);
                    
                    regionBall.radiusXProperty().bind(
                    coordinates[0][0].prefWidthProperty().add(
                    coordinates[0][0].prefHeightProperty()).divide(8));
                    regionBall.radiusYProperty().bind(
                    coordinates[0][0].prefWidthProperty().add(
                    coordinates[0][0].prefHeightProperty()).divide(8));
                    
                    
                    coordinates[regionSeven.getRegionSevenMove().getStartRow()]
                    [regionSeven.getRegionSevenMove().getStartColumn()].getChildren().add(regionBall);
                }catch(Exception ex){
                    System.out.println("Cannot Create Region Seven Ball.");
                }
            }
        }
    }
    
    public RegionColor getRegionOneColor(){ return regionOneColor;}
    public RegionColor getRegionTwoColor(){ return regionTwoColor;}
    public RegionColor getRegionThreeColor(){ return regionThreeColor;}
    public RegionColor getRegionFourColor(){ return regionFourColor;}
    public RegionColor getRegionFiveColor(){ return regionFiveColor;}
    public RegionColor getRegionSixColor(){ return regionSixColor;}
    public RegionColor getRegionSevenColor(){ return regionSevenColor;}
    public RegionColor getRegionEightColor(){ return regionEightColor;}
    
    public void increaseCurrentRow(){
    
        if(currentRow+1<=8)
            currentRow++;
    }
    
    public void decreaseCurrentRow(){
    
        if(currentRow-1>=0)
            currentRow--;
    }
    
    public void increaseCurrentColumn(){
    
        if(currentColumn+1<=8)
            currentColumn++;
    }
    
    public void decreaseCurrentColumn(){
    
        if(currentColumn-1>=0)
            currentColumn--;
    }
    
    private void fillMatrix() {
        
        for(int y = maximumY, row = 0; y >= minimumY && row < 2*(maximumY/deltaXY)+1; y -= deltaXY,row++)
            for(int x = minimumX, column = 0; x <=  maximumX && column < 2*(maximumX/deltaXY)+1;x += deltaXY, column++)
                points[row][column] = new Point(x,y);   

    }
    
    private void addLetterPictures(Region region, byte letterIndex){
        
        if(cartesianPlaneNumber%3==1 && region.exist()){
            Cell cell = region.getRandomCell();
            cell.chooseLetter(letterNumbers.get(letterIndex));
            ImageView imageView = cell.getLetter().getImageView();
            imageView.fitHeightProperty().bind(cell.prefHeightProperty().divide(1.5));
            imageView.fitWidthProperty().bind(cell.prefWidthProperty().divide(1.5));
            cell.getChildren().add(imageView);
            
        }
    }
    
    private void addNumberPictures(Region region, byte numberIndex){
    
        if(cartesianPlaneNumber%3==2 && region.exist()){
            Cell cell = region.getRandomCell();
            cell.chooseNumber(numbers.get(numberIndex));
            ImageView imageView = cell.getNumber().getImageView();
            imageView.fitHeightProperty().bind(cell.prefHeightProperty().divide(1.5));
            imageView.fitWidthProperty().bind(cell.prefWidthProperty().divide(1.5));
            cell.getChildren().add(imageView);
            
        }
    }
    
    private void addCells(){
        
        for(byte row = 0; row < 11; row++){
            for(byte column = 0; column < 11; column++){
                if(!(row==0 || row==10 || column==0 || column==10)){
                    /*Text coordinate = new Text("(" + (int)points[row-1][column-1].getX() + 
                    "," + (int)points[row-1][column-1].getY() + ")");*/

                    Text coordinate = new Text("" + row + (char)('A' + column-1));
                    coordinate.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 
                    FontPosture.REGULAR, 14));
                    
                    Cell cell = new Cell((byte)(row-1), (byte)(column-1)); 

                    cell.prefHeightProperty().bind(heightProperty().divide(9.0));
                    cell.prefWidthProperty().bind(widthProperty().divide(9.0)); 

                    cell.getChildren().add(coordinate);     

                    add(cell,column,row);            
                    coordinates[row-1][column-1] = cell;
                }
                else{
                    Cell cell = new Cell(row, column); 

                    cell.prefHeightProperty().bind(heightProperty().divide(9.0));
                    cell.prefWidthProperty().bind(widthProperty().divide(9.0));                 

                    Text letterOrNumber = new Text();
                    
                    letterOrNumber.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 
                    FontPosture.REGULAR, 20));
                    
                    cell.setStyle("-fx-background-color: brown; -fx-border-color: white");
                    
                    if((row == 0 || row == 10) && column==1) letterOrNumber.setText("A");
                    else if((column == 0 || column == 10) && row == 1) letterOrNumber.setText("1");
                    else if((row == 0 || row == 10) && column==2) letterOrNumber.setText("B");
                    else if((column == 0 || column == 10) && row == 2) letterOrNumber.setText("2");
                    else if((row == 0 || row == 10) && column==3) letterOrNumber.setText("C");
                    else if((column == 0 || column == 10) && row == 3) letterOrNumber.setText("3");
                    else if((row == 0 || row == 10) && column==4) letterOrNumber.setText("D");
                    else if((column == 0 || column == 10) && row == 4) letterOrNumber.setText("4");
                    else if((row == 0 || row == 10) && column==5) letterOrNumber.setText("E");
                    else if((column == 0 || column == 10) && row == 5) letterOrNumber.setText("5");
                    else if((row == 0 || row == 10) && column==6) letterOrNumber.setText("F");
                    else if((column == 0 || column == 10) && row == 6) letterOrNumber.setText("6");
                    else if((row == 0 || row == 10) && column==7) letterOrNumber.setText("G");
                    else if((column == 0 || column == 10) && row == 7) letterOrNumber.setText("7");
                    else if((row == 0 || row == 10) && column==8) letterOrNumber.setText("H");
                    else if((column == 0 || column == 10) && row == 8) letterOrNumber.setText("8");
                    else if((row == 0 || row == 10) && column==9) letterOrNumber.setText("I");
                    else if((column == 0 || column == 10) && row == 9) letterOrNumber.setText("9");
                    
                    cell.getChildren().add(letterOrNumber);     

                    add(cell,column,row);
                }
            }
           
        }
    }
    
    
    
    private void shadePath(){
    
        int i = currentRow;
        int j = currentColumn;
        
        // Upwards
        for(; i >= 0; i--){
            coordinates[i][currentColumn].setStyle("-fx-background-color: #DEB887;");
            coordinates[i][currentColumn].setIsOnPath(true);
            coordinates[i][currentColumn].setCellRegion(Location.Region_None);
        }
            
        i = currentRow;
        
        // Downwards
        for(; i <= 8; i++){
            coordinates[i][currentColumn].setStyle("-fx-background-color: #DEB887;");
            coordinates[i][currentColumn].setIsOnPath(true);
            coordinates[i][currentColumn].setCellRegion(Location.Region_None);
            
        }
        
        i = currentRow;
        
        // To The Left
        for(;  j>= 0; j--){
            coordinates[currentRow][j].setStyle("-fx-background-color: #DEB887;");
            coordinates[currentRow][j].setIsOnPath(true);
            coordinates[currentRow][j].setCellRegion(Location.Region_None);
        }
        
        j = currentColumn;
        
        // To The Right
         for(;  j<= 8; j++){
            coordinates[currentRow][j].setStyle("-fx-background-color: #DEB887;");
            coordinates[currentRow][j].setIsOnPath(true);
            coordinates[currentRow][j].setCellRegion(Location.Region_None);
         }
         
         j = currentColumn;
         
         // Top Left
         for(; i >= 0 && j >= 0;i--,j--){
             coordinates[i][j].setStyle("-fx-background-color: #DEB887;");
             coordinates[i][j].setIsOnPath(true);
             coordinates[i][j].setCellRegion(Location.Region_None);
             
         }
         
          i = currentRow;
          j = currentColumn;
          
          // Top Right
         for(; i >= 0 && j <= 8;i--,j++){
             coordinates[i][j].setStyle("-fx-background-color: #DEB887;");
             coordinates[i][j].setIsOnPath(true);
             coordinates[i][j].setCellRegion(Location.Region_None);
         }
             
          i = currentRow;
          j = currentColumn;
          
          // Bottom Right
          for(; i <= 8 && j <= 8;i++,j++){
              coordinates[i][j].setStyle("-fx-background-color: #DEB887;");
              coordinates[i][j].setIsOnPath(true);
              coordinates[i][j].setCellRegion(Location.Region_None);
          }
          
          i = currentRow;
          j = currentColumn;
          
          // Bottom Left
          for(; i <= 8 && j >= 0;i++,j--){
              coordinates[i][j].setStyle("-fx-background-color: #DEB887;");
              coordinates[i][j].setIsOnPath(true);
              coordinates[i][j].setCellRegion(Location.Region_None);
          
          }
          
          // Point Of Reference.
          coordinates[currentRow][currentColumn].setStyle("-fx-background-color: white;");
          coordinates[currentRow][currentColumn].setIsOnPath(true);
          coordinates[currentRow][currentColumn].setCellRegion(Location.Region_None);
    }
    
    public double getDeltaXY(){ return deltaXY;}
    
    public void setCurrentRow(byte currentRow){
        
        if(currentRow <= 8 && currentRow >=0)
            this.currentRow = currentRow;  
    }
    
    public byte getCurrentRow(){
        return currentRow;
    }
    
    public void setCurrentColumn(byte currentColumn){
        
        if(currentColumn <= 8 && currentColumn >=0)
            this.currentColumn = currentColumn;
    }
    
    public byte getCurrentColumn(){
        return currentColumn;
    }
    
    public void setBall(QueenBall ball){ this.ball = ball;}
    public QueenBall getBall(){ return ball;}
    
    public Point getCurrentPoint(){
        return points[currentRow][currentColumn];
    }
    
    public Cell[][] getCoordinates(){ return coordinates;}
    public Point[][] getPoints(){ return points;}
}
