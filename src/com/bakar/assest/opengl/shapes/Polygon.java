package com.bakar.assest.opengl.shapes;

import com.bakar.assest.opengl.DrawableVertex;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class using to build simple Polygon and use Builder-Pattern to make build objects easy and more clear
 * */
public class Polygon extends Shape {
    private final int countOfSide;
    private final double radius ;


    private  static final PolygonBuilder builder;
    static { builder=new PolygonBuilder();}

    public static PolygonBuilder builder(int countOfSide,double radius){
        builder.countOfSide=countOfSide;
        builder.radius=radius;
        return builder;
    }

    /**
     * Private constructor to prevent create object from it and use Builder to create objects only
     * */
    private Polygon( int countOfSide,double radius ,Shape shape){
        super(shape);
        this.countOfSide=countOfSide;
        this.radius=radius;
    }

    /**
     * Builder Class to Build Polygon
     * */
    public static class PolygonBuilder extends ShapeBuilder{
        private int countOfSide;
        private double radius ;

        @Override
        public PolygonBuilder setRotateAngel(double rotateAngel){super.setRotateAngel(rotateAngel);return this;}
        @Override
        public PolygonBuilder setLocation(Point2D.Double location){super.setLocation(location);return this;}
        @Override
        public PolygonBuilder setLocation(double x, double y){return setLocation(new Point2D.Double(x,y));}
        @Override
        public PolygonBuilder setColor(Color color){super.setColor(color);return this;}
        @Override
        public PolygonBuilder setFilled(boolean filled){super.setFilled(filled);return this;}


        @Override
        public Polygon build(){
            return new Polygon(countOfSide,radius,super.build());
        }
    }



    @Override
    public List<DrawableVertex> getVertices() {
        ArrayList<DrawableVertex> list = new ArrayList<>();

        double angel = getRotateAngel();
        double plussingAngel = 360.0/ countOfSide;

        while (angel <= getRotateAngel()+ 360.0){
            double x=radius* Math.cos(Math.toRadians(angel))+ getLocation().x;
            double y=radius* Math.sin(Math.toRadians(angel))+ getLocation().y;
            list.add(new DrawableVertex(x,y,getColor()));
            angel+=plussingAngel;
        }
        return list;
    }
}
