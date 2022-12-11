package com.bakar.assest.opengl;

import java.awt.*;

public class DrawableVertex {
    private double x,y;
    private Color color=null;

    public DrawableVertex(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public DrawableVertex(double x, double y) {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean hasColor(){return color!=null;}
}
