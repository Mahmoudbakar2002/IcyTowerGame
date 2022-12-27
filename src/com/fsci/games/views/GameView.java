package com.fsci.games.views;

import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import java.awt.event.KeyEvent;

public class GameView extends GLCanvas {
    // game panel data
    private FPSAnimator fps;
    private GLCanvas gamePanel;
    private GameEngine engine;

    private Runnable returnMenu;
    public GameView(Runnable returnMenu){
        super();

        this.returnMenu=returnMenu;
        engine=new GameEngine(returnMenu);



        addKeyListener(engine);
        addGLEventListener(engine);
        // animator
        fps =new FPSAnimator(this,70);

    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(b){
            fps.start();

            engine.resetGame();
            engine.setCurrentGame(GameEngine.GameState.PLAYING);
        }else {
            if(fps.isAnimating())fps.stop();
            engine.setCurrentGame(GameEngine.GameState.STOP);
        }
    }
}
