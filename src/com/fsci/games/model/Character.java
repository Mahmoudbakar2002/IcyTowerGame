package com.fsci.games.model;

import com.bakar.assest.opengl.DrawableGlObject;
import com.bakar.assest.opengl.texture.Image;
import com.fsci.games.controller.Music;

import javax.media.opengl.GL;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class Character is model for character in game store location and size and state
 *  class use singleton design pattern
 *
 * author : mahmoud atef (bakar),Mohamed atef (k3br) :"
 * */
public class Character implements DrawableGlObject {

    /** character properties */
    private double x,y;
    private double height,width;
    private double xScale;
    private double yScale;
    private State currentState;
    private Map<State, Image> stateImageMap;
    private double rotateAngle;
    /*
    *  state and internal time use for update character state
    *  if state equal 0 is still stand
    *  if state equal 1 is moving positive moving
    *  if state equal -1 is moving negative moving
    */
    private double xState,yState,rotateState;
    private long internalTime;
    private boolean onleftEdge =false,onrightEdge=false;
    /* singleton pattern */
    private static final Character singleton;

    private  Music rotatejump,jumps;
    static {singleton=new Character();}
    public static Character getCharacter(Map<State, Image> stateImageMap) {
        singleton.stateImageMap=stateImageMap;
        return singleton;
    }



    private Character(){
        stateImageMap=new HashMap<>();
        currentState=State.IDLE;
        xScale=1;
        yScale=1;
        try {
            rotatejump = new Music("src/assets/Sounds/rotatejump.wav");
            jumps = new Music("src/assets/Sounds/jumps.wav");
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
        rotateAngle=0;
    }


    /**
     *  enum state use for determine character state and updating image for view
     */
    public enum State{
        IDLE,IDLE1,IDLE2,IDLE3,WALK1,WALK2,WALK3,WALK4,JUMP,JUMP1,JUMP2,JUMP3,CHOCK,EDGE1,EDGE2,ROTATE;
    }

    /**
     *  change location : use for update current location by deltaX and deltaY
     *  new_x will be current_x+deltaX
     *  new_y will be current_y+deltaY
     *
     * @param deltaX changing in x-axes
     * @param deltaY changing in y-axes
     * */
    public void changeLocation(double deltaX,double deltaY){
        x+=deltaX;
        y+=deltaY;
        // state for know if character change moving or still in same moving direction
        double newXState=Math.round(deltaX/Math.abs(deltaX));
        double newYState=Math.round(deltaY/Math.abs(deltaY));
        rotateState=deltaY;
        if(xState==  newXState&& yState== newYState) {
            internalTime++;
        }else {
            xState= newXState;
            yState= newYState;
            internalTime=0;
        }

        // update state every 1/6 seconds (frame rate is 60) : general  (FPS/10) change every second
        if(internalTime%10==0) updateCurrentState();
        if(internalTime%2==0&& currentState==State.ROTATE)
            updateCurrentState();
        if((rotateState>=12|| (yState!=0 && (currentState==State.ROTATE)))){
            rotatejump.once();
        }
    }


    /**
     *  private method implement logic for changing character state
     **/
    private void updateCurrentState(){
        // reset scale (flipping about axes) to default
        xScale=1;
        yScale=1;
        //high jump
        // auth : mohamed atef
        if(rotateState>=12 || (yState!=0&&currentState==State.ROTATE)){
            currentState=State.ROTATE;
            rotateAngle -= 30;
        }
        else {
            rotateAngle=0;
            // if xState 0 and yState 0 that mean is still stand
             if (xState == 0 && yState == 0 && (onleftEdge || onrightEdge)) {
                if (onrightEdge) xScale = -1;
                if (currentState == State.EDGE1) currentState = State.EDGE2;
                else currentState = State.EDGE1;
                rotateAngle = 0;
            } else if (xState == 0 && yState == 0) {
                if (currentState == State.IDLE) currentState = State.IDLE1;
                else if (currentState == State.IDLE1) currentState = State.IDLE2;
                else if (currentState == State.IDLE2) currentState = State.IDLE3;
                else currentState = State.IDLE;
            } else if (xState != 0 && yState == 0) {  // if moving horizontal only

                // if moving left flip images
                if (xState < 0) xScale = -1;

                if (currentState == State.WALK1) currentState = State.WALK2;
                else if (currentState == State.WALK2) currentState = State.WALK3;
                else if (currentState == State.WALK3) currentState = State.WALK4;
                else currentState = State.WALK1;
            } else if (xState == 0 && yState != 0) { // if moving vertical only (up and down)
                currentState = State.JUMP;
            } else if (xState != 0 && yState != 0) { // moving in vertical and horizontal

                // if moving left flip images
                if (xState < 0) xScale = -1;

                if (yState <= -1) {
                    currentState = State.JUMP3;
                } else {
                    currentState = State.JUMP1;
                }
            }
        }
    }


    /* draw implements from GLDrawableObject */
    @Override
    public void draw(GL gl) {
        Image image; // image for current state
        if(!stateImageMap.containsKey(currentState))
            image=stateImageMap.get(State.IDLE);
        else
            image=stateImageMap.get(currentState);

        gl.glPushMatrix();
        gl.glTranslated(x,y,0); // update location for character

        // update size for character if it set
        if(currentState!=State.ROTATE){
            if(height!=0)image.setHeight(height);
            if(width!=0)image.setWidth(width);
        }

        // update scale for handling flipping about axis
        image.setXScale(xScale);
        image.setYScale(yScale);
        image.setRotationAngel(rotateAngle);

        image.draw(gl);
        gl.glPopMatrix();
    }


    /*****************************************
     *  Getter and setter methods
     *****************************************/
    public void setSize(double height,double width){
        this.height=height;
        this.width=width;
    }
    public void setLocation(double x,double y){
        this.x=x;
        this.y=y;
    }
    public void setOnEdge(boolean onrightEdge,boolean onleftEdge) {
        this.onrightEdge = onrightEdge;
        this.onleftEdge = onleftEdge;
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

}
