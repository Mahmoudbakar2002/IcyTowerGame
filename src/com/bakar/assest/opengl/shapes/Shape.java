package com.bakar.assest.opengl.shapes;

import com.bakar.assest.opengl.DrawableShape;
import com.bakar.assest.opengl.DrawableVertex;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


/**
*  this class use as parent of drawable shapes
 *  It's contain a main properties for draw shapes
 *
 *  this class Use Builder Pattern
 *  Builder class simplify a way to set properties
**/
public class Shape extends DrawableShape {
    private double rotateAngel;
    private Point2D.Double location;
    private Color color;

    public Shape(double rotateAngel,Point2D.Double location,Color color,boolean filled){
        this.rotateAngel=rotateAngel;
        this.location=location;
        this.color=color;
        this.setFilled(filled);
    }
    public Shape(Shape shape){
        this.rotateAngel=shape.rotateAngel;
        this.location=shape.location;
        this.color=shape.color;
        this.setFilled(shape.isFilled());
    }

    /**
     * Builder Class to Build Shape
     * */
    public static abstract  class ShapeBuilder{
        private double rotateAngel=0;
        private Point2D.Double location=new Point2D.Double(0,0);
        private Color color=null;
        private boolean filled=false;

        public ShapeBuilder setRotateAngel(double rotateAngel){this.rotateAngel=rotateAngel;return this;}
        public ShapeBuilder setLocation(Point2D.Double location){this.location=location;return this;}
        public ShapeBuilder setLocation(double x, double y){this.location=new Point2D.Double(x,y);return this;}
        public ShapeBuilder setColor(Color color){this.color=color;return this;}
        public ShapeBuilder setFilled(boolean filled){this.filled=filled;return this;}

        public Shape build(){
            return new Shape(rotateAngel,location,color,filled);
        }

    }

    public double getRotateAngel() {
        return rotateAngel;
    }
    public void setRotateAngel(double rotateAngel) {
        this.rotateAngel = rotateAngel;
    }

    public Point2D.Double getLocation() {
        return location;
    }
    public void setLocation(Point2D.Double location) {
        this.location = location;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public List<DrawableVertex> getVertices() {
        return new ArrayList<>();
    }
}
