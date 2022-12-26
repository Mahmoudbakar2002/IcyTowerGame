package com.fsci.games.views;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
