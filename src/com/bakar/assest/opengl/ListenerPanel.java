package com.bakar.assest.opengl;

import javax.media.opengl.GLEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

/**
 * this class is template for panel listen to all key pressing and handle errors in pressing and releasing key
 *
 * author : mahmoud atef (bakar)
* */
public abstract class  ListenerPanel implements GLEventListener, KeyListener {
    private BitSet bitSet=new BitSet(0xFFFF);


    public boolean isKeyPressed(int keyCode){
        return bitSet.get(keyCode);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()>= bitSet.length()) return;
        bitSet.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()>= bitSet.length()) return;
        bitSet.set(e.getKeyCode(),false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // don't do any thing here
    }
}
