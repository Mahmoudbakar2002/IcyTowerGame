package com.fsci.games.views;

import com.fsci.games.utills.Config;
import com.fsci.games.utills.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class InfoView extends AbstractGameView{
    private JLabel paper;
    private Image paperImage;

    private Runnable  returnMenu;

    public InfoView(Runnable returnMenu){
        this.returnMenu=returnMenu;

        paperImage=new ImageIcon(getClass().getResource("/assets/menu/paper.png")).getImage();


        paper=new JLabel();
        paper.setBounds(50,50,600,600);
        paper.setIcon(new ImageIcon(paperImage.getScaledInstance(paper.getWidth(),paper.getHeight(),Image.SCALE_SMOOTH)));

        JLabel textView=new JLabel("<html><body><center>"+Config.INFO_TEXT.replaceAll("\n","<br/>")+"</center></body></html>");
        textView.setBounds(100,100,500,500);
        textView.setFont(FontLoader.Broom.deriveFont(25.0f));
        add(textView);

        JLabel dataView=new JLabel();
        dataView.setBounds(200,150,500,400);
        dataView.setFont(FontLoader.Broom.deriveFont(25f));
        add(dataView);



        add(paper);

        init();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        returnMenu.run();
    }
}
