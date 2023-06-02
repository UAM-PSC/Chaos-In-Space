package Entities;
import Main.Game;
import java.awt.*;
import java.util.Random;


public class AsteroidL extends Enemy {


    private double x, y, speed = 3;
    private int width, height;
    private Player player;

    public AsteroidL(int x, int y, int width, int height) {
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

    public void isColiddingShoot() {
        for (int i = 0; i < Game.shoots.size(); i++) {
            PlayerShoot e = new PlayerShoot((int) this.x, (int) this.y, this.height, this.width, (int) player.getX(), (int) player.getY(), player.getAngle());
            if (e instanceof PlayerShoot) {
                if (isColidding(this, e)) {
                    Game.shoots.remove(e);


                }

            }


        }


    }


    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.red);
        g2.translate(x, y);
        g2.fillRect(0, 0, 160, 160);


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


