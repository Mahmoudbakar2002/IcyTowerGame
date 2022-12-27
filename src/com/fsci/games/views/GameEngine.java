package com.fsci.games.views;

import com.bakar.assest.opengl.ListenerPanel;
import com.bakar.assest.opengl.texture.Image;
import com.fsci.games.controller.FloorFactory;
import com.fsci.games.controller.ImageEngine;
import com.fsci.games.controller.Music;
import com.fsci.games.controller.PhysicsScene;
import com.fsci.games.model.Character;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
    private static final int maxX=700,
            minX=0,
            maxY=700,
            minY=0;

    /** Character Data */
    private final static String characterChosen="haroldv4";
    private Character player;
    private Map<Character.State, Image> collection;


    /** Images draws in scene*/
    private Image bgImage;
    private Image gameOverWord;


    /** Game Scene Controller */
    private PhysicsScene physics;
    private FloorFactory floorFactory;
    private GameState currentGame=GameState.PLAYING;

    /** data for background and Camera Scene */
    private int bgLocation,repeatBG,scrollDy;


    private Music m,menusound,amazing,dieandtryagine;


    /***  Runnable to return to Main menu*/
    private Runnable returnMenu;


    /** Inner enum represent Game state */
    public enum GameState{
        PLAYING,PAUSE,GAME_OVER,STOP;

    }

    public GameEngine(Runnable runnable){
        this.returnMenu=runnable;

        /****** read Images ********/
        collection = ImageEngine.loadCharacterImagesState(characterChosen);
        bgImage=ImageEngine.getBackGroundImage();
        gameOverWord=ImageEngine.getGameOverImage();


        /* initialize player and passing character images collection*/
        player= Character.getCharacter(collection);
        physics=new PhysicsScene(player,minX,maxX,minY,maxY);
        floorFactory=new FloorFactory(maxX,maxY,100);
    }

    /* reset function to return game state to initial state */
    public void resetGame(){
        player.setLocation(100,40);
        physics.resetVariables();
        floorFactory.reset();
        bgLocation=0;
        scrollDy=0;
        repeatBG = (maxX-minX+ ((int) bgImage.getHeight())-1)/ (int)bgImage.getHeight();
        resetBitset();
    }

    /* initial function to initialize gl canvas settings*/
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        GLU glu= new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize matrix for paint
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(minX,maxX,minY,maxY, -1.0, 1.0);


        // enabling texture mapping
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        try {
            m = new Music("src/assets/Sounds/icy_tower_ingame.wav");
            menusound = new Music("src/assets/Sounds/icy_tower.wav");
            amazing = new Music("src/assets/Sounds/amazing.wav");
            dieandtryagine = new Music("src/assets/Sounds/dieandtryagine.wav");
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }

        /* ---  load character images ----*/
        for(Map.Entry entry: collection.entrySet()){
            ((Image)entry.getValue()).loadInGl(gl,glu);
        }

        floorFactory.load(gl,glu);

        // load background image
        bgImage.loadInGl(gl,glu);
        gameOverWord.loadInGl(gl,glu);


        gameOverWord.setHeight(gameOverWord.getHeight()*1.2);
        gameOverWord.setX((maxX-gameOverWord.getWidth() )/2);

        resetGame();
        //play music
        m.play();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        if(currentGame==GameState.STOP) return;

        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer

        if(player.getY()>maxY/2.0){
            if(Math.abs(maxY/2.0 - player.getY())>=50)
                scrollDy=5;
            else if(Math.abs(maxY/2.0 - player.getY())>=100)
                scrollDy=10;
            else
                scrollDy=floorFactory.getFloorIndex()/50+1;
        }
        // draw background
        drawBg(gl);

        // draw and scroll
        floorFactory.scrollDown(scrollDy);
        floorFactory.drawFloors();
        player.setLocation(player.getX(), player.getY()-scrollDy);
        physics.updateNearestFloor(floorFactory.getNearestFloor(player));
        floorFactory.isOnEdge(player);



        //gameover
        if(player.getY()<=0){
            if(currentGame!=GameState.GAME_OVER) {
                scrollDy = 0;
                dieandtryagine.once();
                m.stop();
                currentGame = GameState.GAME_OVER;
            }

            if(gameOverWord.getY()<maxY/2+50) {
                gameOverWord.setY(gameOverWord.getY()+10);
            }
            gameOverWord.draw(gl);
            for(int i=0;i<0xFF;i++)
                if (isKeyPressed(i)&&gameOverWord.getY()>=maxY/2.0) {
                    returnMenu.run();
                    gameOverWord.setY(0);
                    currentGame=GameState.STOP;
                }

            return;
        }

        physics.updateKeyState(
                isKeyPressed(KeyEvent.VK_SPACE),
                isKeyPressed(KeyEvent.VK_RIGHT),
                isKeyPressed(KeyEvent.VK_LEFT)
        );

        physics.updatePhysicsScene();

        /* update player location and draw it then */
        gl.glColor3f(1,1,1);
        player.changeLocation(physics.getDeltaX(), physics.getDeltaY());

        if(player.getY()<= physics.getNearst_floor())
            player.setLocation(player.getX(), physics.getNearst_floor());

        player.draw(gl);
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

    public void setCurrentGame(GameState currentGame) {
        this.currentGame = currentGame;
    }


}
