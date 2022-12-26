package com.fsci.games.views;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HowView extends JPanel implements KeyListener{
    private JLabel bg;
    private Image bgImage;

    Runnable  returnMenu;
    public HowView(Runnable returnMenu){
        this.returnMenu=returnMenu;

        this.setLayout(null);
        setFocusable(true);
        addKeyListener(this);

        bgImage =new ImageIcon(getClass().getResource("/assets/howbg.jpg")).getImage();

        bg=new JLabel(new ImageIcon(bgImage));
        bg.setBounds(0,0,700,700);
        add(bg);
    }

    @Override
    public void setSize(int w,int h ) {
        super.setSize(w,h);
        bg.setSize(w,h);
        if (w>0&&h>0)
            bg.setIcon(new ImageIcon(bgImage.getScaledInstance(getWidth(),getHeight(), Image.SCALE_DEFAULT)));

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
