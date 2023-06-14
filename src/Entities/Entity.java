package Entities;

import Main.EnemySpawn;
import Main.Game;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Entity {
    public static BufferedImage asteroidL = EnemySpawn.spriteL.getSprite(0,0,160,160);
    public static BufferedImage asteroidM = EnemySpawn.spriteM.getSprite(0,0,80,80);
    public static BufferedImage asteroidS = EnemySpawn.spriteS.getSprite(0,0,40,40);
    public static BufferedImage player = Game.sprite.getSprite(0,0,20,40);
    public static BufferedImage playerAcl = Game.sprite.getSprite(20,0,20,40);
    protected double x, y, dx, dy;
    private BufferedImage sprite;
    private int maskx, masky, mheight, mwidth, width, height;

    public void tick() {}

    public void render(Graphics g) {
        g.drawImage(sprite, (int) (this.x), (int) (this.y),null);
    }

    public Entity(int x, int y, int width, int height, BufferedImage sprite) { //define posicao da sprite ao instanciar uma entidade
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;

        this.maskx = 0;
        this.masky = 0;
        this.mwidth = width;
        this.mheight = height;
    }

    public void setMasK(int maskx, int masky, int mwidth, int mheight) { //define a mascara de colisao caso necessario muda-la
        this.maskx = maskx;
        this.masky = masky;
        this.mwidth = mwidth;
        this.mheight = mheight;
    }

    public static boolean isColidding(Entity e1, Entity e2) { // metodo de colisao padrao para todas as subclasses de Entity
        Ellipse2D e1Mask = new Ellipse2D.Double(e1.getX() + e1.getMaskx(), e1.getY() + e1.getMasky(),
                e1.getMwidth(), e1.getMheight());
        Rectangle2D e2Mask = new Rectangle2D.Double(e2.getX() + e2.getMaskx(), e2.getY() + e2.getMasky(),
                e2.getMwidth(), e2.getMheight());
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
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

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
