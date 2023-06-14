package Entities;

import Graficos.SpriteSheet;
import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Enemy extends Entity{
    protected double dx,dy;
    protected int life;
    protected double speed,angle;
    protected String type;


    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height,sprite);
    }

    public void tick() { // metodo onde a logica do inimigo e feita


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


    public void render(Graphics g) { // metodo onde o inimigo e renderizado
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.red);
        g2.translate(x, y);
        g2.fillRect(0, 0, 160, 160);


        if (x > Game.WIDTH) { //feito para inimigo nao sair da tela para o inifinito e alem
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
}
