
package com.manikhweschool.patternrecognition;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;

public class QueenSymbol extends Pane{
    
    private double directionLength;
    private double width;
    private double height;
    
    private Polygon arrow1;
    private Polygon arrow2;
    private Polygon arrow3;
    private Polygon arrow4;
    private Polygon arrow5;
    private Polygon arrow6;
    private Polygon arrow7;
    private Polygon arrow8;
    
    public static double originRadius = 3.0;
    
    
    public static final double ARROW_THICKNESS = 5.0;
    
    public QueenSymbol(){
        this(50);
    }
    
    public QueenSymbol(double directionLength){
    
        this.directionLength = directionLength;
        width = directionLength*2.1;
        height = width;
        setPrefSize(width,height);
        
        arrow1 = new Polygon();
        arrow2 = new Polygon();
        arrow3 = new Polygon();
        arrow4 = new Polygon();
        arrow5 = new Polygon();
        arrow6 = new Polygon();
        arrow7 = new Polygon();
        arrow8 = new Polygon();
        
        createArrow1();
        createArrow2();
        createArrow3();
        createArrow8();
        
        createArrow4();
        createArrow5();
        createArrow6();
        createArrow7();
        
        arrow1.setFill(Color.RED);
        arrow2.setFill(Color.RED);
        arrow3.setFill(Color.RED);
        arrow4.setFill(Color.RED);
        arrow5.setFill(Color.RED);
        arrow6.setFill(Color.RED);
        arrow7.setFill(Color.RED);
        arrow8.setFill(Color.RED);
        
        getChildren().add(arrow1);
        getChildren().add(arrow2);
        getChildren().add(arrow3);
        getChildren().add(arrow4);
        getChildren().add(arrow5);
        getChildren().add(arrow6);
        getChildren().add(arrow7);
        getChildren().add(arrow8);
        
        Circle origin = new Circle();
        origin.setRadius(originRadius);
        origin.setCenterX(width/2);
        origin.setCenterY(height/2);
        origin.setFill(Color.BLACK);
        
        getChildren().add(origin);
        
        arrow6.setRotate(45);
        arrow5.setRotate(45);
        arrow7.setRotate(45);
        arrow4.setRotate(45);
     
      
        
    }
    
    private void createArrow1(){
    
        // Top Right Point On The Line.
        arrow1.getPoints().add(width/2);
        arrow1.getPoints().add(height/2-ARROW_THICKNESS/2);
        
        // Top Left Point On The Line.
        arrow1.getPoints().add(width/2-directionLength);
        arrow1.getPoints().add(height/2-ARROW_THICKNESS/2);
        
         // Top Point On The Arrow.
        arrow1.getPoints().add(width/2-directionLength);
        arrow1.getPoints().add(height/2-ARROW_THICKNESS*2);
        
         // Point On Arrow Edge
        arrow1.getPoints().add(width/2-directionLength-ARROW_THICKNESS);
        arrow1.getPoints().add(height/2);
        
         // Bottom Point On The Arrow.
        arrow1.getPoints().add(width/2-directionLength);
        arrow1.getPoints().add(height/2+ARROW_THICKNESS*2);
        
        // Bottom Left Point On The Line.
        arrow1.getPoints().add(width/2-directionLength);
        arrow1.getPoints().add(height/2+ARROW_THICKNESS/2);
        
        // Bottom Right Point On The Line.
        arrow1.getPoints().add(width/2);
        arrow1.getPoints().add(height/2+ARROW_THICKNESS/2);
    }
    
    private void createArrow7(){
      
        arrow7.getPoints().addAll(arrow3.getPoints());
        
    }
    
    private void createArrow6(){
    
        arrow6.getPoints().addAll(arrow2.getPoints());
    }
    
    private void createArrow5(){
    
        arrow5.getPoints().addAll(arrow8.getPoints());
    }
    
    private void createArrow4(){
    
        arrow4.getPoints().addAll(arrow1.getPoints());
    }
    
    private void createArrow3(){
    
        // Top Left Point On The Line.
        arrow3.getPoints().add(width/2-ARROW_THICKNESS/2);
        arrow3.getPoints().add(height/2);
        
        // Bottom Left Point On The Line.
        arrow3.getPoints().add(width/2-ARROW_THICKNESS/2);
        arrow3.getPoints().add(height/2+directionLength);
        
         // Left Point On The Arrow.
        arrow3.getPoints().add(width/2-ARROW_THICKNESS*2);
        arrow3.getPoints().add(height/2+directionLength);
        
         // Point On Arrow Edge
        arrow3.getPoints().add(width/2);
        arrow3.getPoints().add(height/2+directionLength+ARROW_THICKNESS);
        
        // Right Point On The Arrow.
        arrow3.getPoints().add(width/2+ARROW_THICKNESS*2);
        arrow3.getPoints().add(height/2+directionLength);
        
        // Bottom Right Point On The Line.
        arrow3.getPoints().add(width/2+ARROW_THICKNESS/2);
        arrow3.getPoints().add(height/2+directionLength);
        
        // Top Right Point On The Line.
        arrow3.getPoints().add(width/2+ARROW_THICKNESS/2);
        arrow3.getPoints().add(height/2);
        
        
    }
    
    private void createArrow2(){
    
        // Bottom Left Point On The Line.
        arrow2.getPoints().add(width/2-ARROW_THICKNESS/2);
        arrow2.getPoints().add(height/2);
        
        // Top Left Point On The Line.
        arrow2.getPoints().add(width/2-ARROW_THICKNESS/2);
        arrow2.getPoints().add(height/2-directionLength);
        
         // Left Point On The Arrow.
        arrow2.getPoints().add(width/2-ARROW_THICKNESS*2);
        arrow2.getPoints().add(height/2-directionLength);
        
         // Point On Arrow Edge
        arrow2.getPoints().add(width/2);
        arrow2.getPoints().add(height/2-directionLength-ARROW_THICKNESS);
        
        // Right Point On The Arrow.
        arrow2.getPoints().add(width/2+ARROW_THICKNESS*2);
        arrow2.getPoints().add(height/2-directionLength);
        
        // Top Right Point On The Line.
        arrow2.getPoints().add(width/2+ARROW_THICKNESS/2);
        arrow2.getPoints().add(height/2-directionLength);
        
        // Bottom Right Point On The Line.
        arrow2.getPoints().add(width/2+ARROW_THICKNESS/2);
        arrow2.getPoints().add(height/2);
    }
    
    private void createArrow8(){
    
        // Top Left Point On The Line.
        arrow8.getPoints().add(width/2);
        arrow8.getPoints().add(height/2-ARROW_THICKNESS/2);
        
        // Top Right Point On The Line.
        arrow8.getPoints().add(directionLength + width/2);
        arrow8.getPoints().add(height/2-ARROW_THICKNESS/2);
        
         // Top Point On The Arrow.
        arrow8.getPoints().add(directionLength + width/2);
        arrow8.getPoints().add(height/2-ARROW_THICKNESS*2);
        
         // Point On Arrow Edge
        arrow8.getPoints().add(directionLength + width/2+ARROW_THICKNESS);
        arrow8.getPoints().add(height/2);
        
         // Bottom Point On The Arrow.
        arrow8.getPoints().add(directionLength + width/2);
        arrow8.getPoints().add(height/2+ARROW_THICKNESS*2);
        
        // Bottom Right Point On The Line.
        arrow8.getPoints().add(directionLength + width/2);
        arrow8.getPoints().add(height/2+ARROW_THICKNESS/2);
        
        // Bottom Left Point On The Line.
        arrow8.getPoints().add(width/2);
        arrow8.getPoints().add(height/2+ARROW_THICKNESS/2);
        
    }
}
