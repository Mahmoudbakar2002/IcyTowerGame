package com.fsci.games.views;

import com.bakar.assest.opengl.ListenerPanel;
import com.bakar.assest.opengl.texture.Image;
import com.fsci.games.controller.FloorFactory;
import com.fsci.games.controller.ImageEngine;
import com.fsci.games.model.Character;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Map;

/**
 * Game Engine class it's main class use :
 *              - as container for game objects
 *              - draw game objects
 *              - apply physics rules
 *              - update when key pressed
 */
public class GameEngine extends ListenerPanel {

    /**  Properties for scene*/
    private static final int maxX=600,
            minX=0,
            maxY=600,
            minY=0;

    private final static String characterChosen="haroldv4";
    private Character player;
    private double nearst_floor,Wallpadding,deltaX,deltaY,fraction_factor,gravity,accelration_factor, projectile_theta,Max_speed;
    private int uptime ;

    // data for background
    private Image bgImage;
    private int bgLocation,repeatBG,scrollDy;

    // ----
    private FloorFactory floorFactory;


    /* reset function to return game state to initial state */
    public void resetGame(){
        player.setLocation(100,40);
        deltaX=0;
        deltaY=0;
        uptime =0;
        fraction_factor = 0.05;
        gravity = 0.02;
        projectile_theta = 70;
        accelration_factor = 0.2;
        Max_speed = 20;
        Wallpadding = 20;
        nearst_floor = 40;
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
        gl.glOrtho(minX,maxX,minY,maxY, -1.0, 1.0);


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


        floorFactory=new FloorFactory(gl,glu,maxX,maxY);
        floorFactory.setyGap(100);


        // load background image
        try {
            bgImage=new Image("assets/bg2.png");
            bgImage.loadInGl(gl,glu);
            repeatBG = (maxX-minX+ ((int) bgImage.getHeight())-1)/ (int)bgImage.getHeight();
        }catch (IOException ex){
            System.out.println("Error in load Background :" + ex.getMessage() );
        }

        resetGame();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer

        // draw background
        drawBg(gl);

        // draw and scroll
        floorFactory.scrollDown(scrollDy);
        floorFactory.drawFloors();

        /* physics for moving, gravity and velocity */
            //deaccelerate
            fraction();
            // if y <= one for floor's y
            if(player.getY()>nearst_floor)
                gravity();
            else{
                deltaY = 0;
                //@todo lazy fix till figure out collision formulas
               player.setLocation(player.getX(), nearst_floor);
            }
            //600 <=> Max width
            if(player.getX()>=600-Wallpadding){
                h_collision(true);
            }
            else if(player.getX()<=Wallpadding){
                h_collision(false);
            }

            /* handling key pressed to do moving */
            // accelerate
            if (isKeyPressed(KeyEvent.VK_UP)&&player.getY()==nearst_floor)
                jump();
            if(isKeyPressed(KeyEvent.VK_LEFT))
                accelerate(0);
            if(isKeyPressed(KeyEvent.VK_RIGHT))
                accelerate(1);
        /* update player location and draw it then */
        gl.glColor3f(1,1,1);
        player.changeLocation(deltaX,deltaY);
        player.draw(gl);




        //jump time tracker
        uptime++;
    }
    private void h_collision(boolean b){
        //v = v/sum of masses
        if(b)
            player.setLocation(579, player.getY());
        else
            player.setLocation(21, player.getY());
        deltaX = -1.0*deltaX/2;
    }
    private void gravity(){
        deltaY-=gravity*uptime;
    }
    private void fraction(){
        if(deltaY==0) {
            if (deltaX > 0)
                deltaX -= fraction_factor;
            else if (deltaX < 0)
                deltaX += fraction_factor;
            //@todo lazy fix till figure out collision formulas
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
        if (deltaX != 0) {
            deltaY += (Math.sqrt(36 + Math.abs(deltaX) * Math.abs(deltaX))) * Math.sin(Math.toRadians(projectile_theta));
            deltaY += (Math.sqrt(36 + Math.abs(deltaX) * Math.abs(deltaX))) * Math.sin(Math.toRadians(90 - projectile_theta));
        } else
            deltaY += 6;
        uptime = 0;
    }


    private void drawBg(GL gl){
        bgLocation+=scrollDy;
        bgLocation%=bgImage.getHeight();
        for (int i=0,bgStart=-bgLocation;i<=repeatBG;i++) {
            bgImage.setY(bgStart);
            bgImage.draw(gl);
            bgStart+=bgImage.getHeight();
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {
    }

}
