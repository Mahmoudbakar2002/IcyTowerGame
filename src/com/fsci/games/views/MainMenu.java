package com.fsci.games.views;

import com.fsci.games.controller.Music;
import com.fsci.games.controller.MusicEngine;
import com.fsci.games.utills.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class MainMenu extends AbstractGameView{
    private JLabel paper;
    private Image paperImage;

    private JLabel finger;
    private JLabel logo;
    private String menu[]={"Play","Setting","How to play","Info","Score","exit"};
    private JLabel menuButtons[];
    private int currentSelection=0;
    private Music sound;
    private Consumer<Integer> function;


    public MainMenu(Consumer<Integer> function){
        this.function=function;
        sound = MusicEngine.getMenuSound();
        sound.play();
        paperImage=new ImageIcon(getClass().getResource("/assets/menu/paper.png")).getImage();


        paper=new JLabel(new ImageIcon(paperImage.getScaledInstance(380,360,Image.SCALE_SMOOTH)));
        paper.setBounds(300,300,380,360);
        initialize();
        add(paper);
        init();
    }

    public void changeSelector(int idx){
        if(idx>menuButtons.length)return;

        int x=menuButtons[idx].getX() - finger.getWidth();
        int y=menuButtons[idx].getY()-10;
        finger.setLocation(x,y);
    }

    private void initialize(){
        finger=new JLabel(new ImageIcon(getClass().getResource("/assets/menu/finger-selector.png")));
        finger.setBounds(380-59,325,59,48);

        add(finger);

        logo=new JLabel(new ImageIcon(getClass().getResource("/assets/menu/logo.png")));
        logo.setBounds(0,0,374,322);
        add(logo);

        menuButtons=new JLabel[menu.length];
        for (int i=0,y=335;i<menu.length;i++,y+=50){
            menuButtons[i]=new JLabel(menu[i]);
            menuButtons[i].setBounds(380,y,220,25);
            menuButtons[i].setForeground(Color.BLACK);
            menuButtons[i].setFont(FontLoader.Broom.deriveFont(25.0f));
            add(menuButtons[i]);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP) currentSelection=(currentSelection==0)?0:currentSelection-1;
        if(e.getKeyCode()==KeyEvent.VK_DOWN) currentSelection+=(currentSelection==menuButtons.length-1)?0:1;

        changeSelector(currentSelection);

        if(e.getKeyCode()==KeyEvent.VK_SPACE||e.getKeyCode()==KeyEvent.VK_ENTER)
            function.accept(currentSelection+1);
    }
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(b){
            sound.restart();
        }else{
            sound.stop();
        }
    }
}
