package com.fsci.games.views;


import javax.swing.*;
import java.awt.event.KeyEvent;

public class HowView extends AbstractGameView{

    private Runnable  returnMenu;
    public HowView(Runnable returnMenu){
        this.returnMenu=returnMenu;

        this.setLayout(null);
        setFocusable(true);
        addKeyListener(this);

        init();
        setBgImage(new ImageIcon(getClass().getResource("/assets/howbg.jpg")).getImage());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        returnMenu.run();
    }

}
