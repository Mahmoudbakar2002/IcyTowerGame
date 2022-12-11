package com.bakar.assest.opengl.shapes;

import com.bakar.assest.opengl.DrawableVertex;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Circle extends Shape {
    private final double visibleSlicePart;
    private final double radius ;


    private  static final CircleBuilder builder;
    static { builder=new CircleBuilder();}

    public static CircleBuilder builder(double radius){
        builder.radius=radius;
        builder.setVisibleSlicePart(360);
        return builder;
    }

    /**
     * Private constructor to prevent create object from it and use Builder to create objects only
     * */
    private Circle(double radius,double visibleSlicePart ,Shape shape){
        super(shape);
        this.visibleSlicePart=visibleSlicePart;
        this.radius=radius;
    }

    /**
     * Builder Class to Build circle
     * */
    public static class CircleBuilder extends ShapeBuilder{
        private double visibleSlicePart=360;
        private double radius ;

        @Override
        public CircleBuilder setRotateAngel(double rotateAngel){super.setRotateAngel(rotateAngel);return this;}
        @Override
        public CircleBuilder setLocation(Point2D.Double location){super.setLocation(location);return this;}
        @Override
        public CircleBuilder setLocation(double x, double y){return setLocation(new Point2D.Double(x,y));}
        @Override
        public CircleBuilder setColor(Color color){super.setColor(color);return this;}
        @Override
        public CircleBuilder setFilled(boolean filled){super.setFilled(filled);return this;}

        public CircleBuilder setVisibleSlicePart(double visibleSlicePart){this.visibleSlicePart=visibleSlicePart; return this;}

        @Override
        public Circle build(){
            return new Circle(radius,visibleSlicePart,super.build());
        }
    }



    @Override
    public List<DrawableVertex> getVertices() {
        ArrayList<DrawableVertex> list = new ArrayList<>();

        double angel = getRotateAngel();
        double plussingAngel = 0.5;

        while (angel <= getRotateAngel()+ visibleSlicePart){
            double x=radius* Math.cos(Math.toRadians(angel))+ getLocation().x;
            double y=radius* Math.sin(Math.toRadians(angel))+ getLocation().y;
            list.add(new DrawableVertex(x,y,getColor()));
            angel+=plussingAngel;
        }
        return list;
    }
}
