package com.fsci.games.views;

import com.fsci.games.utills.Config;
import com.fsci.games.utills.FontLoader;
import com.fsci.games.utills.ScoreReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ScoreBoard extends  AbstractGameView{
    private JLabel paper;
    private Image paperImage;
    private JLabel dataView;
    private Runnable  returnMenu;
//    private String[][] scores={{"bakar","100"},{"ahmed","15"},{"bakar","100"},{"bakar","100"},{"bakar","100"},{"bakar","100"},{"bakar","100"},{"bakar","100"},{"bakar","100"},{"bakar","100"} };

    private ScoreReader.Score scores[]=new ScoreReader.Score[10];

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        scores= ScoreReader.scoreData();
        refreshScoreData();
    }

    public ScoreBoard(Runnable returnMenu){
        this.returnMenu=returnMenu;

        paperImage=new ImageIcon(getClass().getResource("/assets/menu/paper.png")).getImage();


        paper=new JLabel();
        paper.setBounds(50,50,600,600);
        paper.setIcon(new ImageIcon(paperImage.getScaledInstance(paper.getWidth(),paper.getHeight(),Image.SCALE_SMOOTH)));

        JLabel title=new JLabel("TOP 10");
        title.setBounds(100,100,500,35);
        title.setFont(FontLoader.Broom.deriveFont(30.0f).deriveFont(Font.BOLD));
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);

        dataView=new JLabel();
        dataView.setBounds(200,150,500,400);
        dataView.setFont(FontLoader.Broom.deriveFont(25f));
        add(dataView);


        add(paper);
        init();
    }

    private void refreshScoreData(){
        String data="";int idx=1;
        for(ScoreReader.Score score:scores){
            data+=(idx++)+". "+(idx==11?"":" ")+score.toString()+"\n";
        }
        dataView.setText("<html><body>"+data.replaceAll("\n","<br/>").replaceAll(" ","&nbsp;")+"</body><html>");
    }
    @Override
    public void keyPressed(KeyEvent e) {
        returnMenu.run();
    }
}