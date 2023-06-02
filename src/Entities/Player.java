package Entities;

import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
        private boolean toRight=false,toLeft=false,toFront=false,isShooting=false;
        public double angle=0;
        private double spinSpeed=1,flightSpeed=0.8,slidingSpeed=0.3;
        private double lastXDirection,lastYDirection,dx,dy;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }


    public void tick(){


        if(angle == 361 || angle == -361){
            angle=0;
        }


        x+=lastXDirection*slidingSpeed;
        y+=lastYDirection*slidingSpeed;

        if (toRight) {
            angle += 1 * spinSpeed;
        } else if (toLeft) {
            angle -= 1 * spinSpeed;
        } else if (toFront) {
            double dx = Math.round(2 * Math.sin(Math.toRadians(angle))) * flightSpeed;
            double dy = Math.round(-2 * Math.cos(Math.toRadians(angle))) * flightSpeed;
            x += dx;
            y += dy;
            lastXDirection = dx;
            lastYDirection = dy;
        }

        if(isShooting){
            isShooting=false;
            PlayerShoot shoot = new PlayerShoot(0,0,1,20,(int)x,(int)y,angle);
            Game.shoots.add(shoot);

        }
        this.isColiddingEnemy();

    }


    public void isColiddingEnemy(){
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if (e instanceof AsteroidL) {
                if (isColidding(this, e)) {
                    Game.entities.remove(e);
                    System.out.println("ESTOU COLIDINDO");
                }

            }
        }

    }



    public void render(Graphics g){

        Graphics2D g2 =(Graphics2D) g.create();
        g2.setColor(Color.green);
        g2.translate(x, y);
        g2.rotate(Math.toRadians(angle));
        g2.fillPolygon(new int[] {-10, 10, 0}, new int[] {20, 20, -20}, 3);

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


    public boolean isToFront() {
        return toFront;
    }

    public void setToFront(boolean toFront) {
        this.toFront = toFront;
    }




    public boolean isToRight() {
        return toRight;
    }

    public void setToRight(boolean toRight) {
        this.toRight = toRight;
    }

    public boolean isToLeft() {
        return toLeft;
    }

    public void setToLeft(boolean toLeft) {
        this.toLeft = toLeft;
    }


    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public double getSpinSpeed() {
        return spinSpeed;
    }

    public void setSpinSpeed(double spinSpeed) {
        this.spinSpeed = spinSpeed;
    }

    public double getFlightSpeed() {
        return flightSpeed;
    }

    public void setFlightSpeed(double flightSpeed) {
        this.flightSpeed = flightSpeed;
    }

    public double getSlidingSpeed() {
        return slidingSpeed;
    }

    public void setSlidingSpeed(double slidingSpeed) {
        this.slidingSpeed = slidingSpeed;
    }

    public double getLastXDirection() {
        return lastXDirection;
    }

    public void setLastXDirection(double lastXDirection) {
        this.lastXDirection = lastXDirection;
    }

    public double getLastYDirection() {
        return lastYDirection;
    }

    public void setLastYDirection(double lastYDirection) {
        this.lastYDirection = lastYDirection;
    }
}
