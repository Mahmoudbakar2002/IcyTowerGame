package com.fsci.games.views;

import com.fsci.games.utills.Config;
import com.fsci.games.utills.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class SettingView extends  AbstractGameView {
    private JLabel background;
    private JLabel paper,finger;
    private Image bgImage,paperImage;
    private String menu[]={"Sound","Back"};
    private JLabel menuButtons[];
    private int currentSelection=0;

    private Runnable backToMenu;
    private JLabel soundLevel;


    public SettingView(Runnable backToMenu){
        this.backToMenu=backToMenu;
        paperImage=new ImageIcon(getClass().getResource("/assets/menu/paper.png")).getImage();

        paper=new JLabel();
        paper.setBounds(50,50,600,600);
        paper.setIcon(new ImageIcon(paperImage.getScaledInstance(paper.getWidth(),paper.getHeight(),Image.SCALE_SMOOTH)));

        JLabel title=new JLabel("Setting");
        title.setBounds(100,100,500,35);
        title.setFont(FontLoader.Broom.deriveFont(30.0f).deriveFont(Font.BOLD));
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);


        menuButtons=new JLabel[menu.length];
        for (int i=0,y=300;i<menu.length;i++,y+=50){
            menuButtons[i]=new JLabel(menu[i]);
            menuButtons[i].setBounds(200,y,220,25);
            menuButtons[i].setForeground(Color.BLACK);
            menuButtons[i].setFont(FontLoader.Broom.deriveFont(25.0f));
            add(menuButtons[i]);
        }
        finger=new JLabel(new ImageIcon(getClass().getResource("/assets/menu/finger-selector.png")));
        finger.setBounds(200-finger.getWidth(),220-10,59,48);
        int x=menuButtons[currentSelection].getX() - finger.getWidth();
        int y=menuButtons[currentSelection].getY()-10;
        finger.setLocation(x,y);
        add(finger);

        soundLevel=new JLabel(Config.SOUND_LEVEL+"");
        soundLevel.setBounds(420,menuButtons[0].getY(),100,25);
        soundLevel.setForeground(Color.BLACK);
        soundLevel.setFont(FontLoader.Broom.deriveFont(25.0f));
        add(soundLevel);

        add(paper);
        init();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP) currentSelection=(currentSelection==0)?0:currentSelection-1;
        if(e.getKeyCode()==KeyEvent.VK_DOWN) currentSelection+=(currentSelection==menuButtons.length-1)?0:1;

        if(currentSelection>menuButtons.length)return;


        if(currentSelection==0){
            if(e.getKeyCode()==KeyEvent.VK_RIGHT && Config.SOUND_LEVEL<100) Config.SOUND_LEVEL++;
            if(e.getKeyCode()==KeyEvent.VK_LEFT && Config.SOUND_LEVEL>0) Config.SOUND_LEVEL--;

            soundLevel.setText(Config.SOUND_LEVEL+"");
        }

        int x=menuButtons[currentSelection].getX() - finger.getWidth();
        int y=menuButtons[currentSelection].getY()-10;
        finger.setLocation(x,y);

        if(currentSelection==1&&(e.getKeyCode()==KeyEvent.VK_SPACE||e.getKeyCode()==KeyEvent.VK_ENTER))
            backToMenu.run();

    }
}
