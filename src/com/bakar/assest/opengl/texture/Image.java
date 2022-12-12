package com.bakar.assest.opengl.texture;

import com.bakar.assest.opengl.DrawableGlObject;
import com.bakar.assest.opengl.outsource.texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import java.io.IOException;

public class Image implements DrawableGlObject {
    private String sourceFile;
    private int glIndex=-1;

    private TextureReader.Texture texture;
    private double x,y;
    private double height,width;
    private double rotationAngel;
    private double sizeRatio; // width/height
    private boolean useSizeRatio;
    private double xScale=1,yScale=1;

    public Image(String source)throws IOException{
        this.sourceFile=source;
        inti();
    }
    public void inti() throws IOException {
        texture=TextureReader.readTexture(sourceFile,true);
        glIndex=-1;
        x=0;y=0;
        height=texture.getHeight();
        width= texture.getWidth();
        rotationAngel=0;
        sizeRatio= width/height;
        useSizeRatio=false;
    }
    public void loadInGl(GL gl, GLU glu){
        if(glIndex==-1) {
            int idx[] = new int[1];
            gl.glGenTextures(1, idx, 0);
            this.glIndex = idx[0];
        }

        gl.glBindTexture(GL.GL_TEXTURE_2D, glIndex);
        glu.gluBuild2DMipmaps(
                GL.GL_TEXTURE_2D,
                GL.GL_RGBA, // Internal Texel Format,
                texture.getWidth(), texture.getHeight(),
                GL.GL_RGBA, // External format from image,
                GL.GL_UNSIGNED_BYTE,
                texture.getPixels() // Imagedata
        );
    }




    @Override
    public void draw(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, glIndex);	// Turn Blending On

        gl.glPushMatrix();

        gl.glTranslated((width/2.0)+x,(height/2.0)+y,0);
        gl.glRotated(rotationAngel,0,0,1);
        gl.glScaled(xScale,yScale,1);
//        gl.glTranslated((w/2.0),(h/2.0),0);

        gl.glBegin(GL.GL_QUADS);
//         Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3d(-(width/2.0), -(height/2.0), -1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3d((width/2.0), -(height/2.0), -1.0f);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3d((width/2.0), (height/2.0), -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3d(-(width/2.0), (height/2.0), -1.0f);
        gl.glEnd();

        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }


    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        if(useSizeRatio)
            width= height*sizeRatio;
    }

    public double getWidth() {
        return width;

    }

    public void setWidth(double width) {
        this.width = width;

        if(useSizeRatio)
            this.height= width*(1.0/sizeRatio);
    }

    public double getRotationAngel() {
        return rotationAngel;
    }

    public void setRotationAngel(double rotationAngel) {
        this.rotationAngel = rotationAngel;
    }

    public boolean isUseSizeRatio() {
        return useSizeRatio;
    }

    public void useSizeRatio(boolean useSizeRatio) {
        this.useSizeRatio = useSizeRatio;
    }

    public void flipInX(){xScale*=-1;}
    public void flipInY(){yScale*=-1;}

    public void setXScale(double xScale) {
        this.xScale = xScale;
    }

    public void setYScale(double yScale) {
        this.yScale = yScale;
    }

    public double getxScale() {
        return xScale;
    }

    public double getyScale() {
        return yScale;
    }
}
