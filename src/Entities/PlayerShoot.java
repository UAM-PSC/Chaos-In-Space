package Entities;

import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerShoot extends Entity{
        private Player player;
        private int playerX,playerY;
        private int x=playerY,y=playerY;
        public  int    width=1,height=20;
        private double playerAngle;
        private double speed=6;
        private double dx,dy;



    public PlayerShoot(int x, int y, int width, int height, int playerX, int playerY, double playerAngle, BufferedImage sprite){
        super(x, y, width, height,sprite);
        this.playerX=playerX;
        this.playerY=playerY;
        this.playerAngle=playerAngle;
    }
    public void tick() { // metodo que executa a logica do disparo do player



        double dx = Math.round(2 * Math.sin(Math.toRadians(playerAngle))) * speed;
        double dy = Math.round(-2 * Math.cos(Math.toRadians(playerAngle))) * speed;
        playerX += dx;
        playerY += dy;
        x=playerX;
        y=playerY;


    }








    public void render(Graphics g){ //metodo que renderiza o disparo
        Graphics2D g2 =(Graphics2D) g.create();
        g2.setColor(Color.white);
        g2.rotate(Math.toRadians(playerAngle),playerX,playerY);
        g2.fillRect(playerX, playerY-25,width,height);
        if(playerX > Game.WIDTH){ //metodo para otimizacao do jogo //caso o tiro saia da tela ele e apagado
            Game.shoots.remove(this);
        }else if(playerX < 0){
            Game.shoots.remove(this);
        }
        if(playerY > Game.HEIGHT){
            Game.shoots.remove(this);
        }else if ( playerY < 0 ){
            Game.shoots.remove(this);
        }
    }

    @Override
    public double getX() {
        return playerX;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return playerY;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }



    public void setWidth(int width) {
        this.width = width;
    }




    public void setHeight(int height) {
        this.height = height;
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
