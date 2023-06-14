package Entities;

import Graficos.UI;
import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{
        private boolean toRight=false,toLeft=false,toFront=false,isShooting=false;
        public double angle=0;
        private double spinSpeed=2,flightSpeed=0.8,slidingSpeed=0.3;
        private double lastXDirection,lastYDirection,dx,dy;
        private int life=3;
        private boolean isDamaged=false;
        private int damageFrames=0;
        public int score=0;


    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height,sprite);
    }


    public void tick(){ //metodo onde e executado a logica do player/jogador




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

        if(isShooting){ // verifica se jogador esta atirando
            isShooting=false;
            PlayerShoot shoot = new PlayerShoot(0,0,1,20,(int)x,(int)y,angle,Game.sprite.getSprite(0,0,1,20));
            Game.shoots.add(shoot);

        }

        this.isColiddingEnemy();
        if(isDamaged){ //define quantidade de frames de invunerabilidade do player ao ser atigido
            if(damageFrames == 0){
                life--;
                damageFrames=30;
            }
            damageFrames--;
            isDamaged=false;
        }

        if(life == 0){ //verifica se jogador ainda tem vidas
            Game.gameState="GAMEOVER";
        }
    }


    public void isColiddingEnemy(){ //verifica colisao de jogador com inimigo
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if (e instanceof Enemy) {
                if (isColidding(this, e)) {
                    isDamaged=true;
                }
            }
        }

    }



    public void render(Graphics g){ // renderiza o jogador

        Graphics2D g2 =(Graphics2D) g.create();
        g2.setColor(Color.green);
        g2.translate(x, y);
        g2.rotate(Math.toRadians(angle));
        if(toFront) {
            g2.drawImage(Entity.playerAcl, -Entity.player.getWidth() / 2, -Entity.player.getHeight() / 2, null);
        }else {
            g2.drawImage(Entity.player, -Entity.player.getWidth() / 2, -Entity.player.getHeight() / 2, null);
        }
        if(x > Game.WIDTH){ // feito para jogador atravessar a tela de um lado para outro
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
