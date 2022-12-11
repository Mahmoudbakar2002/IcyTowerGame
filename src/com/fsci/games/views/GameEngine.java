package com.fsci.games.views;

import com.bakar.assest.opengl.ListenerPanel;
import com.bakar.assest.opengl.texture.Image;
import com.fsci.games.controller.ImageEngine;
import com.fsci.games.model.Character;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.io.IOException;
import java.util.Map;

public class GameEngine extends ListenerPanel {

    Image image ;
    Character player;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        GLU glu= new GLU();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, 600, 0, 600, -1.0, 1.0);

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);


        Map<Character.State, Image> collection = ImageEngine.loadCharacterImagesState("harold");
        for(Map.Entry entry: collection.entrySet()){
            ((Image)entry.getValue()).loadInGl(gl,glu);
        }
        player= Character.getCharacter(collection);
        player.changeLocation(100,20);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer


        player.draw(gl);
        player.changeLocation(0,0);
        gl.glColor3f(0,0,0);
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
