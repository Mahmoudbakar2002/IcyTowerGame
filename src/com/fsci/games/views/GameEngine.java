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
    private double deltaX,deltaY;
    /* reset function to return game state to initial state */
    public void resetGame(){
        player.setLocation(100,20);
        deltaX=0;
        deltaY=0;
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

        /*     physics for moving, gravity and velocity */
        deltaX=0;
        /* handle click pressed to do moving*/

        /* update player location and draw it then */
        gl.glColor3f(1,1,1);
        player.changeLocation(deltaX,deltaY);
        player.draw(gl);


        /* vertical line for test */
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2i(0,20);
        gl.glVertex2i(600,20);
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {
    }

}
