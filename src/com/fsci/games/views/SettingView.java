package com.fsci.games.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class SettingView extends  AbstractGameView {
    private JLabel background;
    private JLabel paper;
    private Image bgImage,paperImage;

    private Runnable backToMenu;

    public SettingView(Runnable backToMenu){
        this.backToMenu=backToMenu;

        init();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        backToMenu.run();
    }
}
