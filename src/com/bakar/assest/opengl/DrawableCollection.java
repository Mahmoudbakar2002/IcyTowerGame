package com.bakar.assest.opengl;

import javax.media.opengl.GL;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class DrawableCollection implements DrawableGlObject {
    private ArrayList<DrawableGlObject> objects;
    private Point2D.Double location;
    public DrawableCollection() {
        this.objects = new ArrayList<>();
        this.location=new Point2D.Double(0,0);
    }
    public void addObject(DrawableGlObject object){
        objects.add(object);
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public void setLocation(Point2D.Double location) {
        this.location = location;
    }

    @Override
    public void draw(GL gl) {
        gl.glPushMatrix();
        gl.glTranslated(location.getX(),location.getY(),0);
        objects.forEach((obj)->obj.draw(gl));
        gl.glPopMatrix();
    }
}
