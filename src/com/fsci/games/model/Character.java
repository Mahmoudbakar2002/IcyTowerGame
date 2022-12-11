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
    private State currentState;
    private Map<State, Image> stateImageMap;

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
        IDLE(0),IDLE1(1),IDLE2(2);
        int id;
        State(int id){
            this.id=id;
        }
    }

    public void changeLocation(double deltaX,double deltaY){
        x+=deltaX;
        y+=deltaY;

        if(deltaX==0 && deltaY==0){
            if(currentState==State.IDLE)currentState=State.IDLE1;
            else if(currentState==State.IDLE1)currentState=State.IDLE2;
            else currentState=State.IDLE;
        }
    }

    public void setSize(double height,double width){
        this.height=height;
        this.width=width;
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
        image.draw(gl);
        gl.glPopMatrix();
    }
}
