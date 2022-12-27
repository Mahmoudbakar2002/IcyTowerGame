package com.fsci.games.controller;

import com.fsci.games.model.Character;

import java.awt.event.KeyEvent;

public class PhysicsScene {
    private Character player;
    private int maxX, minX, maxY,minY;

    private double nearst_floor,Wallpadding,deltaX,deltaY,fraction_factor,gravity,accelration_factor, projectile_theta,Max_speed;
    private int uptime ;
    private boolean spaceKeyState=false,rightKeyState=true,leftKeyState=false;


    public PhysicsScene(Character player , int minX,int maxX,int minY,int maxY){
        this.player=player;
        this.minX=minX;
        this.maxX=maxX;
        this.minY=minY;
        this.maxY=maxY;
        this.resetVariables();
    }
    public void resetVariables(){
        deltaX=0;
        deltaY=0;
        uptime =0;
        fraction_factor = 0.09;
        gravity = 0.018;
        projectile_theta = 70;
        accelration_factor = 0.2;
        Max_speed = 8;
        Wallpadding = 20;
        nearst_floor = 40;
    }



    public void updateKeyState(boolean space,boolean right,boolean left){
        this.spaceKeyState=space;
        this.rightKeyState=right;
        this.leftKeyState=left;
    }
    public void updateNearestFloor(double nearst_floor){
        this.nearst_floor=nearst_floor;
    }
    public void updatePhysicsScene(){
        /* physics for moving, gravity and velocity */
        //deaccelerate
        if(uptime!=0)
        fraction();
        // if y <= one for floor's y
        if(player.getY()>nearst_floor)
            gravity();
        else
            deltaY = 0;
        if(player.getY()==nearst_floor)
            uptime=0;
        //600 <=> Max width
        if(player.getX()>maxX- player.getWidth()-Wallpadding-29){//maxX-2*player.getWidth()-Wallpadding){
            h_collision(true);
        }
        else if(player.getX()<=Wallpadding){
            h_collision(false);
        }

        /* handling key pressed to do moving */
        // accelerate
        if (spaceKeyState&&player.getY()==nearst_floor)
            jump();
        if(leftKeyState)
            accelerate(0);
        if(rightKeyState)
            accelerate(1);
        //jump time tracker
        uptime++;
    }

    public double getNearst_floor() {
        return nearst_floor;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    private void h_collision(boolean b){
        //v = v/(sum of masses)
        if(b)
            player.setLocation(maxX- player.getWidth()-Wallpadding-30 , player.getY());
        else
            player.setLocation(Wallpadding+1, player.getY());
        deltaX = -1.0*deltaX;
    }
    private void gravity(){
        deltaY-= gravity*uptime;
    }
    private void fraction(){
        if(deltaY==0) {
            if (deltaX > 0)
                deltaX -= fraction_factor;
            else if (deltaX < 0)
                deltaX += fraction_factor;
            if (Math.abs(deltaX) < fraction_factor)
                deltaX = 0;
        }
    }
    private void accelerate(int i){
        if (i==1&&deltaX<Max_speed)
            deltaX+= accelration_factor;
        if (i==0&& deltaX>-Max_speed)
            deltaX-= accelration_factor;
    }
    private void jump(){
        if (deltaX >2) {
            deltaY += (Math.sqrt(49 + Math.abs(deltaX) * Math.abs(deltaX))) * Math.sin(Math.toRadians(projectile_theta));
            deltaY += (Math.sqrt(49 + Math.abs(deltaX) * Math.abs(deltaX))) * Math.sin(Math.toRadians(90 - projectile_theta));
        } else
            deltaY += 7;
        uptime = 0;
    }
}
