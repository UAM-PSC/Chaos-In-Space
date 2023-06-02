package Entities;

import java.awt.*;

public class Entity {
    protected double x,y,width,height;
    private int maskx,masky,mheight,mwidth;


    public void tick(){}

    public void render(Graphics g){}

    public Entity(int x, int y,int width, int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;


        this.maskx =0;
        this.masky=0;
        this.mwidth=width;
        this.mheight=height;
    }



    public void setMasK(int maskx, int masky, int mwidth, int mheight){
        this.maskx =maskx;
        this.masky=masky;
        this.mwidth=mwidth;
        this.mheight=mheight;

    }

    public static boolean isColidding(Entity e1,Entity e2){

        Rectangle e1Mask = new Rectangle((int)e1.getX()+ e1.maskx,(int)e1.getY()+e1.masky,(int)e1.width,e1.mheight);
        Rectangle e2Mask = new Rectangle((int)e2.getX()+ e2.maskx,(int)e2.getY()+e2.masky,(int)e2.width,e2.mheight);
        return e1Mask.intersects(e2Mask);

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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }


    public int getMaskx() {
        return maskx;
    }

    public void setMaskx(int maskx) {
        this.maskx = maskx;
    }

    public int getMasky() {
        return masky;
    }

    public void setMasky(int masky) {
        this.masky = masky;
    }

    public int getMheight() {
        return mheight;
    }

    public void setMheight(int mheight) {
        this.mheight = mheight;
    }

    public int getMwidth() {
        return mwidth;
    }

    public void setMwidth(int mwidth) {
        this.mwidth = mwidth;
    }
}
