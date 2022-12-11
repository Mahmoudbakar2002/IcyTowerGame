package com.bakar.assest.opengl.shapes;

import com.bakar.assest.opengl.DrawableVertex;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class StarPolygon extends Shape {
    private final double minRadius,maxRadius;
    private final int numberOfLegs;

    private static final StarPolygonBuilder builder;
    static {builder=new StarPolygonBuilder();}

    public static StarPolygonBuilder builder(int numberOfLegs,double minRadius,double maxRadius){
        builder.minRadius=minRadius;
        builder.maxRadius=maxRadius;
        builder.numberOfLegs=numberOfLegs;
        return builder;
    }

    private StarPolygon(double minRadius,double maxRadius,int numberOfLegs,Shape shape){
        super(shape);
        this.minRadius=minRadius;
        this.maxRadius=maxRadius;
        this.numberOfLegs=numberOfLegs;
    }
    public static class StarPolygonBuilder extends Shape.ShapeBuilder{
        private double minRadius,maxRadius;
        private int numberOfLegs;

        @Override
        public StarPolygonBuilder setRotateAngel(double rotateAngel){super.setRotateAngel(rotateAngel);return this;}
        @Override
        public StarPolygonBuilder setLocation(Point2D.Double location){super.setLocation(location);return this;}
        @Override
        public StarPolygonBuilder setLocation(double x, double y){return setLocation(new Point2D.Double(x,y));}
        @Override
        public StarPolygonBuilder setColor(Color color){super.setColor(color);return this;}
        @Override
        public StarPolygonBuilder setFilled(boolean filled){super.setFilled(filled);return this;}

        @Override
        public StarPolygon build() {
            return new StarPolygon(minRadius,maxRadius,numberOfLegs,super.build());
        }
    }


    @Override
    public List<DrawableVertex> getVertices() {
        ArrayList<DrawableVertex> list=new ArrayList<>();
        double plussing=360.0/(2*numberOfLegs);

        double angel=getRotateAngel();
        boolean flag=false;
        while (angel < getRotateAngel()+ 360.0){
            double xp=(flag?maxRadius:minRadius)* Math.cos(Math.toRadians(angel))+ getLocation().getX();
            double yp=(flag?maxRadius:minRadius)* Math.sin(Math.toRadians(angel))+ getLocation().getY();
            list.add(new DrawableVertex(xp,yp,getColor()));
            flag=!flag;
            angel+=plussing;
        }

        return list;
    }


}
