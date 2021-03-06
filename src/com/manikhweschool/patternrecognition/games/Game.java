package com.manikhweschool.patternrecognition.games;

import com.manikhweschool.patternrecognition.buildingblocks.CartesianPlane;
import com.manikhweschool.patternrecognition.QueenSymbol;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Font;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Game extends BorderPane{

    protected CartesianPlane cartesianPlane;
    
    protected BorderPane gameInfo = new BorderPane();
    protected Text shiftRhythmLeftText;
    protected Button leftShiftButton;
    protected Text shiftRhythmRightText;
    protected Button rightShiftButton;
    protected int rhythmShiftAmount;
    protected Text rhythmShiftAmountInfo;
    protected Button applyRhythmShiftButton;
    
    protected CheckBox rhythmFitWell;
    
    protected int startingRow;
    protected int startingColumn;
    protected long startTime;
    
    // Tells us whether or not the expected player answer is a cell position.
    protected boolean positionGame;
    
    public static double fontWidth = 20;
    protected QueenSymbol symbol;
    private final Label one = new Label("C\n1");
    private final Label two = new Label("L\n2");
    private final Label three = new Label("M\n3");
    private final Label four = new Label("N\n4");
    private final Label five = new Label("O\n5");
    private final Label six = new Label("R\n6");
    private final Label seven = new Label("S\n7");
    private final Label eight = new Label("W\n8");
    
    
         
    protected Game(boolean positionGame, CartesianPlane cartesianPlane, long startTime){
        
        this.positionGame = positionGame;
        this.cartesianPlane = cartesianPlane;
        this.startTime = startTime;
        
        startingRow = this.cartesianPlane.getBall().getCurrentRow();
        startingColumn = this.cartesianPlane.getBall().getCurrentColumn();
        
        initialize();
    } 
    
    private void initialize(){
        
        rhythmShiftAmount = 0;
        rhythmShiftAmountInfo = new Text(20,20,"Current Rhythm Shift Amount Is " + rhythmShiftAmount + " Milliseconds.");
        
        shiftRhythmLeftText = new Text(20, 20, "\t\tShift Rhythm Left : ");
        leftShiftButton = new Button("<<<<<");
        
        shiftRhythmRightText = new Text(20, 20, "Shift Rhythm Right : ");
        rightShiftButton = new Button(">>>>>");
        Text applyRhythmShiftText = new Text("Apply Rhythm Shift");
        applyRhythmShiftButton = new Button("",applyRhythmShiftText);
        
        rhythmFitWell = new CheckBox("Does Rhythm Fit Well?");
        rhythmFitWell.setTextFill(Color.NAVY);
        
        
        applyRhythmShiftText.setFill(Color.NAVY);
        shiftRhythmLeftText.setFill(Color.BROWN);
        shiftRhythmRightText.setFill(Color.BROWN);
        rhythmShiftAmountInfo.setFill(Color.BROWN);
        
        shiftRhythmLeftText.setFont(Font.font("Times New Roman", 
        FontWeight.BOLD, FontPosture.ITALIC, 16));
        shiftRhythmRightText.setFont(Font.font("Times New Roman", 
        FontWeight.BOLD, FontPosture.ITALIC, 16));
        rhythmShiftAmountInfo.setFont(Font.font("Times New Roman", 
        FontWeight.BOLD, FontPosture.ITALIC, 16));
        applyRhythmShiftText.setFont(Font.font("Times New Roman", 
        FontWeight.BOLD, FontPosture.ITALIC, 14));
        rhythmFitWell.setFont(Font.font("Times New Roman", 
        FontWeight.BOLD, FontPosture.ITALIC, 14));
        
        symbol = new QueenSymbol();
        one.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.ITALIC, fontWidth));
        two.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, fontWidth));
        three.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, fontWidth));
        four.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, fontWidth));
        five.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, fontWidth));
        six.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, fontWidth));
        seven.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, fontWidth));
        eight.setFont(Font.font(null, FontWeight.EXTRA_BOLD, FontPosture.REGULAR, fontWidth));
        
        one.setStyle(CartesianPlane.getRegionColor(cartesianPlane.getRegionOneColor()));
        two.setStyle(CartesianPlane.getRegionColor(cartesianPlane.getRegionTwoColor()));
        three.setStyle(CartesianPlane.getRegionColor(cartesianPlane.getRegionThreeColor()));
        four.setStyle(CartesianPlane.getRegionColor(cartesianPlane.getRegionFourColor()));
        five.setStyle(CartesianPlane.getRegionColor(cartesianPlane.getRegionFiveColor()));
        six.setStyle(CartesianPlane.getRegionColor(cartesianPlane.getRegionSixColor()));
        seven.setStyle(CartesianPlane.getRegionColor(cartesianPlane.getRegionSevenColor()));
        eight.setStyle(CartesianPlane.getRegionColor(cartesianPlane.getRegionEightColor()));
       
        
        HBox leftShiftPane = new HBox();
        leftShiftPane.getChildren().add(shiftRhythmLeftText);
        leftShiftPane.getChildren().add(leftShiftButton);
        
        HBox rightShiftPane = new HBox();
        rightShiftPane.getChildren().add(shiftRhythmRightText);
        rightShiftPane.getChildren().add(rightShiftButton);
        rightShiftPane.getChildren().add(new Label("\t\t\t"));
        
        BorderPane rhythmFitPane = new BorderPane();
        rhythmFitPane.setLeft(rhythmFitWell);
        rhythmFitPane.setRight(applyRhythmShiftButton);
        
        gameInfo.setLeft(leftShiftPane);
        gameInfo.setRight(rightShiftPane);
        
        VBox info = new VBox();
        info.setAlignment(Pos.CENTER);
        info.getChildren().add(rhythmShiftAmountInfo);
        info.getChildren().add(gameInfo);
        info.getChildren().add(rhythmFitPane);    
        
        setCenter(cartesianPlane);
        setBottom(info);
            
        HBox topPane = new HBox(9);
        topPane.getChildren().add(one);
        topPane.getChildren().add(two);
        topPane.getChildren().add(three);
        topPane.getChildren().add(four);
        topPane.getChildren().add(symbol);
        topPane.getChildren().add(five);
        topPane.getChildren().add(six);
        topPane.getChildren().add(seven);
        topPane.getChildren().add(eight);
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(10,0,10,0));
        
        setTop(topPane);
        setLeft(new Label("\t\t\t"));
        setRight(new Label("\t\t\t"));
    }
    
    public void relocateBall(){
        cartesianPlane.getBall().setCurrentRow(cartesianPlane.getCurrentRow());
        cartesianPlane.getBall().setCurrentColumn(cartesianPlane.getCurrentColumn());
        
    }
    
    public CartesianPlane getCartesianPlane(){
    
        return cartesianPlane;
    }
    
}
