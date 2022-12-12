package com.fsci.games.model;

import com.bakar.assest.opengl.DrawableGlObject;
import com.bakar.assest.opengl.texture.Image;

import javax.media.opengl.GL;
import java.util.HashMap;
import java.util.Map;

/**
 * Class Character is model for character in game store location and size and state
 *  class use singleton design pattern
 *
 * author : mahmoud atef (bakar)
 * */
public class Character implements DrawableGlObject {
    private double x,y;
    private double height,width;
    private double xScale=1;
    private double yScale=1;
    private State currentState;
    private Map<State, Image> stateImageMap;


    private double xState,yState;
    private long internalTime;
    /* singleton pattern */
    private static final Character singleton;
    static {singleton=new Character();}
    public static Character getCharacter(Map<State, Image> stateImageMap) {
        singleton.stateImageMap=stateImageMap;
        return singleton;
    }

    private Character(){
        stateImageMap=new HashMap<>();
        currentState=State.IDLE;
    }

    /* create enum state*/
    public enum State{
        IDLE,IDLE1,IDLE2,IDLE3,WALK1,WALK2,WALK3,WALK4,JUMP,JUMP1,JUMP2,JUMP3,CHOCK ;
    }


    public void changeLocation(double deltaX,double deltaY){
        x+=deltaX;
        y+=deltaY;
        if(xState== Math.round(deltaX/Math.abs(deltaX)) && yState==Math.round(deltaY/Math.abs(deltaY)) )
            internalTime++;
        else {
            xState= Math.round(deltaX/Math.abs(deltaX));
            yState= Math.round(deltaY/Math.abs(deltaY));
            internalTime=0;
        }

        if(internalTime%10!=0) return;
        xScale=1;yScale=1;
        if(deltaX==0 && deltaY==0){
            if(currentState==State.IDLE)currentState=State.IDLE1;
            else if(currentState==State.IDLE1)currentState=State.IDLE2;
            else if(currentState==State.IDLE2)currentState=State.IDLE3;
            else currentState=State.IDLE;
        }else if(deltaX!=0 && deltaY==0){
            if(deltaX<0) xScale=-1;

            if(currentState==State.WALK1) currentState=State.WALK2;
            else if(currentState==State.WALK2) currentState=State.WALK3;
            else if(currentState==State.WALK3) currentState=State.WALK4;
            else  currentState=State.WALK1;
        }

    }

    public void setSize(double height,double width){
        this.height=height;
        this.width=width;
    }
    public void setLocation(double x,double y){
        this.x=x;
        this.y=y;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public void draw(GL gl) {
        Image image;
        if(!stateImageMap.containsKey(currentState))
            image=stateImageMap.get(State.IDLE);
        else
            image=stateImageMap.get(currentState);

        gl.glPushMatrix();
        gl.glTranslated(x,y,0);
        if(height!=0)image.setHeight(height);
        if(width!=0)image.setWidth(width);
        System.out.println(xScale);
        image.setXScale(xScale);
        image.setYScale(yScale);
        image.draw(gl);
        gl.glPopMatrix();
    }
}
