package com.fsci.games.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public abstract class AbstractGameView extends JPanel implements KeyListener {
    private Image bgImage;

    private JLabel background;
    public void init(){
        bgImage=new ImageIcon(getClass().getResource("/assets/menu/menubg.png")).getImage();

        setLayout(null);
        addKeyListener(this);
        setFocusable(true);

        background=new JLabel();
        background.setIcon(new ImageIcon(bgImage));
        background.setBounds(0,0,getWidth(),getHeight());
        setLayout(null);
        add(background);
    }

    public void setBgImage(Image image){
        bgImage=image;
    }

    @Override
    public void setSize(int width,int height){
        super.setSize(width,height);
        background.setSize(width,height);
        if (width>0&&height>0)
            background.setIcon(new ImageIcon(bgImage.getScaledInstance(getWidth(),getHeight(), Image.SCALE_DEFAULT)));
    }

}
