package com.fsci.games.views;

import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private int cardsLength=0;

    // game panel data
    private FPSAnimator fps;
    private GLCanvas gamePanel;
    private GameEngine engine;


    private MainMenu mainMenu;
    private HowView howView;
    private SettingView settingView;
    private InfoView infoView;

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


        engine=new GameEngine();

        gamePanel=new GLCanvas();
        gamePanel.addKeyListener(engine);
        gamePanel.addGLEventListener(engine);
        this.addKeyListener(engine);

        // animator
        fps =new FPSAnimator(gamePanel,70);
        fps.start();

        mainMenu=new MainMenu(this::changeView);
        mainMenu.setSize(getWidth(),getHeight());

        howView=new HowView(()->changeView(0));
        howView.setSize(getWidth(),getHeight());

        settingView=new SettingView(()->changeView(0));
        settingView.setSize(getWidth(),getHeight());

        infoView=new InfoView(()->changeView(0));
        infoView.setSize(getWidth(),getHeight());

        addView(mainMenu);
        addView(gamePanel);
        addView(settingView);
        addView(howView);
        addView(infoView);

        changeView(0);

    }
}
