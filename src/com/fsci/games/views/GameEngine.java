package com.fsci.games.views;

import com.bakar.assest.opengl.ListenerPanel;
import com.bakar.assest.opengl.texture.Image;
import com.fsci.games.controller.ImageEngine;
import com.fsci.games.model.Character;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.util.Map;

/**
 * Game Engine class it's main class use :
 *              - as container for game objects
 *              - draw game objects
 *              - apply physics rules
 *              - update when key pressed
 */
public class GameEngine extends ListenerPanel {

    private String characterChosen="haroldv4";
    private Character player;
    private double deltaX,deltaY,fraction_factor,gravity,accelration_factor, projectile_theta;
    private int uptime ;
    public GameEngine() {
    }

    /* reset function to return game state to initial state */
    public void resetGame(){
        player.setLocation(100,20);
        deltaX=0;
        deltaY=0;
        uptime =0;
        fraction_factor = 0.05;
        gravity = 0.02;
        projectile_theta = 60;
        accelration_factor = 0.1;
    }

    /* initial function to initialize gl canvas settings*/
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        GLU glu= new GLU();

        // set background color
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize matrix for paint
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, 500, 0, 500, -1.0, 1.0);


        // enabling texture mapping
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);


        /* ---  load character images ----*/
        Map<Character.State, Image> collection = ImageEngine.loadCharacterImagesState(characterChosen);
        for(Map.Entry entry: collection.entrySet()){
            ((Image)entry.getValue()).loadInGl(gl,glu);
        }

        /* initialize player and passing character images collection*/
        player= Character.getCharacter(collection);
        resetGame();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer

        /* physics for moving, gravity and velocity and handling click pressed to do moving*/
        //deaccelrate
        fraction();
        gravity(player.getY());
        // accelrate
        accelerate();
        jump();


        /* update player location and draw it then */
        gl.glColor3f(1,1,1);
        player.changeLocation(deltaX,deltaY);
        player.draw(gl);


        /* vertical line for test */
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2i(0,20);
        gl.glVertex2i(600,20);
        gl.glEnd();

        //jump time tracker
        uptime++;
    }
    private void gravity(double y){
        if(y>20)
            deltaY-=gravity*uptime;
        // if y <= one for floor's y
        if(y<=20) {
            deltaY = 0;
            //lazy fix till figure out collision formulas
            player.setLocation(player.getX(), 20);
        }
    }
    private void fraction(){
        if(deltaX>0)
            deltaX-= fraction_factor;
        else if(deltaX<0)
            deltaX+= fraction_factor;
        //lazy fix till figure out collision formulas
        if(Math.abs(deltaX)< fraction_factor)
            deltaX=0;
    }
    private void accelerate(){
        if (isKeyPressed(KeyEvent.VK_RIGHT)&&deltaX<5)
            deltaX+= accelration_factor;
        if (isKeyPressed(KeyEvent.VK_LEFT)&& deltaX>-5)
            deltaX-= accelration_factor;
    }
    private void jump(){
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if(uptime >50) {
                if(deltaX!=0) {
                    deltaY+=(Math.sqrt(16+Math.abs(deltaX)*Math.abs(deltaX)))*Math.sin(Math.toRadians(projectile_theta));
                    deltaY+=(Math.sqrt(16+Math.abs(deltaX)*Math.abs(deltaX)))*Math.sin(Math.toRadians(90- projectile_theta));
                }
                else
                    deltaY+=4;
                uptime =0;
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {
    }

}
