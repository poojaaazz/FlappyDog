package com.pooja.flappydog;

public class Dog {
    private int dogX, dogY, currentFrame, velocity;
    public static int maxFrame;
public Dog(){
    dogX = AppConstants.SCREEN_WIDTH/2 - AppConstants.getBitmapBank().getDogWidth()/2;
    dogY = AppConstants.SCREEN_HEIGHT/2 - AppConstants.getBitmapBank().getDogHeight()/2;
    currentFrame = 0;
    maxFrame = 3;
    velocity = 0;
}
//Getter method for velocity
    public int getVelocity(){
    return velocity;
    }
    //setter method for velocity
    public void setVelocity(int velocity){
    this.velocity = velocity;
    }
//Getter method for current frame
    public int getCurrentFrame(){
    return currentFrame;
    }
    //setter method for current frame
    public void setCurrentFrame(int currentFrame){
    this.currentFrame = currentFrame;
    }
    //Getter method for getting X-coordinate of the Dog
    public int getX(){
    return dogX;
    }

    //Getter method for getting the Y-coordinate of the Dog
    public int getY(){
    return dogY;
    }
    //setter method for setting the X-coordinate
    public void setX(int dogX){
    this.dogX = dogX;
    }
    public void setY(int dogY){
    this.dogY = dogY;
    }
}
