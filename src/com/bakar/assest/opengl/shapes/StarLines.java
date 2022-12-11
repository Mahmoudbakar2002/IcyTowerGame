package com.bakar.assest.opengl.shapes;

import com.bakar.assest.opengl.DrawableGlObject;

import javax.media.opengl.GL;
import java.awt.geom.Point2D;

public class StarLines implements DrawableGlObject {
    private final int numberOfLegs,step;
    private final double radius,rotateAngel;
    private final Point2D.Double location;

    public StarLines(double radius,int numberOfLegs,int step, double rotateAngel, Point2D.Double location) {
        this.radius = radius;
        this.numberOfLegs=numberOfLegs;
        this.step=step;
        this.rotateAngel = rotateAngel;
        this.location = location;
    }
    public StarLines(double radius,int numberOfLegs,int step){
        this(radius,numberOfLegs,step,0,new Point2D.Double(0,0));
    }

    @Override
    public void draw(GL gl) {
        double plussing=360.0/numberOfLegs;


        int p=0;
        gl.glBegin(GL.GL_LINE_LOOP);
        do{
          gl.glVertex2d(
                    radius*Math.cos(Math.toRadians((p*plussing)+rotateAngel))+location.getX() ,
                    radius*Math.sin(Math.toRadians((p*plussing)+rotateAngel))+location.getY());
            p=(p+step)%numberOfLegs;
        }while(p!=0);
        gl.glEnd();
    }
}
