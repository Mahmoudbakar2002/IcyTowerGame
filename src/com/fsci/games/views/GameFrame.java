package com.fsci.games.views;

import com.fsci.games.utills.ScoreReader;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private int cardsLength=0;




    private MainMenu mainMenu;
    private GameView gameView;
    private HowView howView;
    private SettingView settingView;
    private InfoView infoView;
    private ScoreBoard scoreBoard;

    private void changeView(int idx){
        if(idx==6) System.exit(0);

        if(idx>cardsLength||idx<0)return;

        String name=String.valueOf(idx);
        cardLayout.show(cardPanel,name);

        for(Component comp : cardPanel.getComponents()){
            if(comp.getName().equalsIgnoreCase(name))
                comp.requestFocusInWindow();
        }

    }
    private void addView(Component panel){
        panel.setName(String.valueOf(cardsLength));
        cardPanel.add(panel,String.valueOf(cardsLength++));
    }

    private void scoreChecker(int score){
        if(!ScoreReader.isHighScore(score))return;
        String name=JOptionPane.showInputDialog(this,"You get High Score\nEnter Your Name","High Score",JOptionPane.INFORMATION_MESSAGE);
        if(name==null ||name.trim().isEmpty())
            name="-no-name-";

        ScoreReader.addNewScore(new ScoreReader.Score(name,score));
    }

    public GameFrame(){
        initialize();
    }
    public  void initialize(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setResizable(false);
        //center the JFrame on the screen
        this.setLocationRelativeTo(null);

        cardLayout=new CardLayout();
        cardPanel=new JPanel(cardLayout);
        this.add(cardPanel,BorderLayout.CENTER);




        mainMenu=new MainMenu(this::changeView);
        mainMenu.setSize(getWidth(),getHeight());

        gameView=new GameView(()->changeView(0),(e)->scoreChecker(e));
        gameView.setSize(getWidth(),getHeight());

        howView=new HowView(()->changeView(0));
        howView.setSize(getWidth(),getHeight());

        settingView=new SettingView(()->changeView(0));
        settingView.setSize(getWidth(),getHeight());

        infoView=new InfoView(()->changeView(0));
        infoView.setSize(getWidth(),getHeight());

        scoreBoard=new ScoreBoard(()->changeView(0));
        scoreBoard.setSize(getWidth(),getHeight());

        addView(mainMenu);
        addView(gameView);
        addView(settingView);
        addView(howView);
        addView(infoView);
        addView(scoreBoard);

        changeView(0);

    }
}
