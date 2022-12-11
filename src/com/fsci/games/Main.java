package com.fsci.games;


import com.fsci.games.views.GameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final GameFrame app=new GameFrame();
        SwingUtilities.invokeLater (() -> app.setVisible(true));
    }
}