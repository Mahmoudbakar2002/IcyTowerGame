package com.fsci.games.controller;

import com.bakar.assest.opengl.texture.Image;
import com.fsci.games.model.Character;
import com.fsci.games.model.Floor;
import com.sun.opengl.util.j2d.TextRenderer;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class FloorFactory {
    private String floors[]={"stone","ice","wood"};
    private Map<String, Floor> map;
    private Image numBoard;
    private int yGap=60,maxHigh,maxWidth ,initialY=40,floorIndex=1;
    private TextRenderer textRenderer;

    private LinkedList<FloorData> floorData;
    private  GL gl;


    public FloorFactory(int maxWidth,int maxHigh,int yGap){
        this.maxHigh=maxHigh;
        this.maxWidth=maxWidth;
        this.gl=gl;
        this.yGap=yGap;

//        textRenderer=new TextRenderer(new Font("Courier New", Font.BOLD, 120));
//        textRenderer.beginRendering(maxWidth, maxHigh);
        map=new HashMap<>();
        reset();
    }
    public void load(GL gl, GLU glu){
        this.gl=gl;
        try {
            for (String floorStr:floors) {
                Image start = new Image("assets/floors/"+floorStr+"/start.png");
                Image middle = new Image("assets/floors/"+floorStr+"/middle.png");
                Image end = new Image("assets/floors/"+floorStr+"/end.png");
                start.loadInGl(gl,glu);
                middle.loadInGl(gl,glu);
                end.loadInGl(gl,glu);

                /** solving flip image by rotate and change direction*/
                start.setRotationAngel(180);
                middle.setRotationAngel(180);
                end.setRotationAngel(180);
                Floor floor =new Floor(end,middle,start);
                map.put(floorStr,floor);
            }
        }catch (IOException exception){
            System.out.println("Error in load Images : "+exception.getMessage());
        }
    }


    public void reset(){
        initialY=40;floorIndex=1;
        floorData=new LinkedList<FloorData>();
        floorData.add(new FloorData(0,initialY,maxWidth,floors[getFloorIndex(floorIndex++)]));

        while (floorData.getLast().y<maxHigh)
            createFloor();

    }

    public void drawFloors(){
        floorData.forEach((e)->{
            Floor floor = map.get(e.type);
            floor.setLocation(e.x,e.y);
            floor.setWidth(e.width);
            floor.draw(gl);
        });
    }

    public void scrollDown(int dy){
        for (Iterator<FloorData> iterator = floorData.iterator(); iterator.hasNext();) {
            FloorData fd = iterator.next();
            fd.y-=dy;
            if (fd.y<0)
                iterator.remove();

        }
        if(floorData.getLast().y<maxHigh)
            createFloor();
    }

    private void createFloor(){
        Random ran=new Random();
        int w=ran.nextInt(200)+200;
        int x=ran.nextInt(maxWidth-w);
        if(floorIndex%50==0) {
            w = maxWidth;
            x=0;
        }
        int y=floorData.getLast().y+yGap;
        floorData.add( new FloorData(x,y,w,floors[getFloorIndex(floorIndex++)]));
    }

    private int getFloorIndex(int index){
        return (index/100)% floors.length;
    }

    public Image getNumBoard() {
        return numBoard;
    }

    public void setNumBoard(Image numBoard) {
        this.numBoard = numBoard;
    }


    public int getNearestFloor(Character player){

        int nearest= -1;
        for (FloorData e:floorData){
            if(e.y<= (player).getY() && player.getX()>=e.x-20&&player.getX()<=e.x+e.width-20) {
                nearest = e.y;
            }
        };
        return nearest;
    }
    public void isOnEdge(Character player){
        for (FloorData e:floorData){
            if(e.y<= (player).getY() && player.getX()>=e.x-20&&player.getX()<=e.x+e.width-20) {
                if(Math.abs(player.getX()-(e.x-20))<=10)
                    player.setOnEdge(true,false);
                else if(Math.abs(player.getX()-(e.x+e.width-20))<=10)
                    player.setOnEdge(false,true);
                else
                    player.setOnEdge(false,false);
            }
        }
    }
    public int getFloorIndex() {
        return floorIndex;
    }
    public int getyGap() {
        return yGap;
    }

    private class FloorData{
        public FloorData(int x, int y, int width,String type) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.type=type;
        }

        int x,y,width;
        String type;
    }

}
