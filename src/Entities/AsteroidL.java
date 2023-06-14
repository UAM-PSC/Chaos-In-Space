package Entities;
import Graficos.SpriteSheet;
import Main.EnemySpawn;
import Main.Game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class AsteroidL extends Enemy {


    private double x, y, speed = 3;
    private int width=160, height=160;
    private Player player;

    public AsteroidL(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height,sprite);
        this.setMheight(this.height);
        this.setMwidth(this.width);
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
        colisionShoot();


    }



    public void colisionShoot() { // valida colisao do asteroide com o tiro
        for (int i = 0; i < Game.shoots.size(); i++) {
            Entity e = Game.shoots.get(i);
            if(e instanceof PlayerShoot) {
                if (isColidding(this, e)) {
                    Random rand = new Random();
                    int spawn = rand.nextInt(101);
                        if(spawn <= 70){
                           Game.shoots.remove(e);
                           Game.entities.remove(this);
                            EnemySpawn spawnS = new EnemySpawn();
                            spawnS.spawnSmall();
                            spawnS.spawnSmall();
                            Game.player.setScore(Game.player.getScore()+20);
                        }else{
                            Game.shoots.remove(e);
                            Game.entities.remove(this);
                            Game.player.setScore(Game.player.getScore()+20);
                        }
                }
            }
        }
    }


    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(x, y);
        g.drawImage(Entity.asteroidL, (int) this.getX(),(int)this.getY(),null);



        if (x > Game.WIDTH) {
            x = 0;
        } else if (x < 0) {
            x = Game.WIDTH;
        }
        if (y > Game.HEIGHT) {
            y = 0;
        } else if (y < 0) {
            y = Game.HEIGHT;
        }
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}


