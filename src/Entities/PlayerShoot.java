package Entities;

import Main.Game;

import java.awt.*;

public class PlayerShoot extends Entity{
        private Player player;
        private int playerX,playerY,width,height;
        private double playerAngle;
        private double x,y,speed=6;
        private double dx,dy;



    public PlayerShoot(int x, int y, int width, int height,int playerX, int playerY,double playerAngle){
        super(x, y, width, height);
        this.playerX=playerX;
        this.playerY=playerY;
        this.playerAngle=playerAngle;
    }
    public void tick() {
        double dirtiroX;
        double dirtiroY;
        dirtiroX=playerX+Math.round(1*Math.sin(playerAngle));
        dirtiroY=playerY+Math.round(-1*Math.cos(playerAngle));
        dx+=dirtiroX;
        dy+=dirtiroY;
        y -=1*speed;
        System.out.println(dx);
        System.out.println(dy);
        System.out.println(playerX);
        System.out.println(playerY);

    }

    public void render(Graphics g){
        Graphics2D g2 =(Graphics2D) g.create();
        g2.setColor(Color.white);
        g2.translate(playerX, playerY);
        g2.rotate(Math.toRadians(playerAngle));
        g2.fillRect((int)x, (int)y-45,1,20);
        if(this.y < -1400){
            Game.shoots.remove(this);
        }



    }
}
