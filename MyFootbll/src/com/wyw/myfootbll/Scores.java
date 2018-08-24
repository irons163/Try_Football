package com.wyw.myfootbll;

public class Scores {

	public static final int WIN_POINT = 5;
	
	private int left = 0;
	private int top = 0;
	
	private int width = 0;
	private int height = 0;
	
	int Scores = 0;
	int type = 0;
	
	Scores(int type){
		this.type = type; 
	}
	
	public void setLeft(int value){
		left = value;
	}
	
	public void setTop(int value){
		top = value;
	}
	
	public void setWidth(int value){
		width = value;
	}
	
	public void setHeight(int value){
		height = value;
	}
	
	public int getLeft(){
		return left;
	}
	
	public int getTop(){
		return top;
	}
	
	public int getRight(){
		return left + width;
	}
	
	public int getBottom(){
		return top + height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
