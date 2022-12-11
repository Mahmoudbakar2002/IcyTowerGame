package com.fsci.games.views;

import com.bakar.assest.opengl.ListenerPanel;
import com.bakar.assest.opengl.texture.Image;
import com.fsci.games.model.Character;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.io.IOException;

public class GameEngine extends ListenerPanel {

    Image image ;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        GLU glu=new GLU();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        try {
            image =new Image("assets/characters/harold/idle.png");
            image.loadInGl(gl,glu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.setX(100);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glClearColor(0,0,0,1);
        gl.glLoadIdentity();
        gl.glOrtho(0, 600, 0, 600, -1.0, 1.0);

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer



        gl.glColor3f(1,1,1);
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
