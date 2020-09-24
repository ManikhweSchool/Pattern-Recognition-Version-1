package com.manikhweschool.patternrecognition.buildingblocks;

import javafx.geometry.Point2D;

public class Point extends Point2D{

	private boolean isReserved;
	
	public Point(double x, double y) {
            super(x, y);
	}
	
	public Point(int x, int y, boolean isReserved) {
            super((double)x, (double)y);
            this.isReserved = isReserved;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	
	
}
