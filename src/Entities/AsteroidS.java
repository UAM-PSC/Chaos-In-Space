package Entities;

import Main.Game;

import java.awt.*;
import java.util.Random;

public class AsteroidS extends Enemy {
    private double x= Game.WIDTH/2,y=Game.HEIGHT/2,speed=3;
    private int width=40,height=40;

    public AsteroidS(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void tick() {


        Random random = new Random();
        double angle = random.nextDouble() * 360;

        if (this.dx == 0) {
            this.angle = random.nextDouble() * 360;
            this.dx = Math.sin(Math.toRadians(angle));
            this.dy = Math.cos(Math.toRadians(angle));
        } else {
            this.x += this.dx * this.speed;
            this.y += this.dy * this.speed;
        }
        if (x > Game.WIDTH) {
            this.angle = random.nextDouble() * 360;
            this.dx = Math.sin(Math.toRadians(this.angle));
            this.y = Math.cos(Math.toRadians(this.angle));
        } else if (x < 0) {
            this.angle = random.nextDouble() * 360;
            this.dx = Math.sin(Math.toRadians(this.angle));
            this.dy = Math.cos(Math.toRadians(this.angle));
        }
        if (y > Game.HEIGHT) {
            this.angle = random.nextDouble() * 360;
            this.dx = Math.sin(Math.toRadians(angle));
            this.dy = Math.cos(Math.toRadians(angle));
        } else if (y < 0) {
            this.angle = random.nextDouble() * 360;
            this.dx = Math.sin(Math.toRadians(this.angle));
            this.dy = Math.cos(Math.toRadians(this.angle));
        }


    }



    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.red);
        g2.translate(x, y);
        g2.fillRect(0,0,width,height);


        if(x > Game.WIDTH){
            x=0;
        }else if(x < 0){
            x = Game.WIDTH;
        }
        if(y > Game.HEIGHT){
            y=0;
        }else if ( y < 0 ){
            y=Game.HEIGHT;
        }
    }
}
