/***
 * @desc
 * @autho Mahmoud atef
 */

package com.fsci.games.model;

import com.bakar.assest.opengl.DrawableGlObject;
import com.bakar.assest.opengl.texture.Image;

import javax.media.opengl.GL;

public class Floor implements DrawableGlObject {
    private double x, y, width;
    private Image start, middle, end;


    public Floor(double x, double y, double width, Image start, Image middle, Image end) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.start = start;
        this.middle = middle;
        this.end = end;
    }

    public Floor(Image start, Image middle, Image end) {
        this(0, 0, 0, start, middle, end);
    }


    @Override
    public void draw(GL gl) {

        gl.glPushMatrix();
        gl.glTranslated(x, y, 0);
        gl.glScaled(1, -1, 1);


        start.setX(x);
        start.draw(gl);
        double x = start.getWidth();

        for (; x <width- end.getWidth();) {
            middle.setX(x);
            middle.draw(gl);
            x += middle.getWidth();
        }
        end.setX(width- end.getWidth());
        end.draw(gl);
        gl.glPopMatrix();
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setImage(Image start, Image middle, Image end) {
        this.start = start;
        this.middle = middle;
        this.end = end;
    }
}
