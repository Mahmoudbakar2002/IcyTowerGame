package com.fsci.games.views;

import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    // game panel data
    private FPSAnimator fps;
    private GLCanvas gamePanel;
    private GameEngine engine;


    public GameFrame(){
        initialize();
    }
    public  void initialize(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        //center the JFrame on the screen
        this.setLocationRelativeTo(null);


        engine=new GameEngine();

        GLCanvas gamePanel=new GLCanvas();
        gamePanel.addKeyListener(engine);
        gamePanel.addGLEventListener(engine);
        this.add(gamePanel, BorderLayout.CENTER);
        // animator
        fps =new FPSAnimator(gamePanel,60);
        fps.start();

    }
}
