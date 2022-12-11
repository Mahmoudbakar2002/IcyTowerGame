package com.fsci.games.views;

import com.bakar.assest.opengl.ListenerPanel;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class GameEngine extends ListenerPanel {


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, 600, 0, 600, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glBegin(GL.GL_LINES);
            gl.glVertex2i(0,20);
            gl.glVertex2i(600,20);
        gl.glEnd();

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {
    }
}
