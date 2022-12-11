package com.bakar.assest.opengl;

import javax.media.opengl.GL;
import java.util.List;

public abstract  class DrawableShape implements DrawableGlObject {

    private boolean filled=false;


    public abstract List<DrawableVertex> getVertices();

    @Override
    public void draw(GL gl) {
        if(filled) gl.glBegin(GL.GL_POLYGON);
        else gl.glBegin(GL.GL_LINE_LOOP);

        getVertices().forEach((vertex)->{
            if(vertex.hasColor()) gl.glColor3fv(vertex.getColor().getColorComponents(null),0);
            gl.glVertex2d(vertex.getX(),vertex.getY());
        });

        gl.glEnd();
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
