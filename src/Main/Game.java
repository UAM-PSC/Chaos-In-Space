package Main;


import Entities.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener {

    public static JFrame frame;
    private boolean isRunning=true;
    private Thread thread;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int scale = 1;
    private String gameState="MENU";
    private BufferedImage image;
    public int rodaa=0,rodab=0;
    private Player player;
    private EnemySpawn enemySpawn;
    public static ArrayList<PlayerShoot> shoots;
    public static ArrayList<Entity> entities;
    AsteroidL asteroid;




    public Game(){

        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH*scale, HEIGHT*scale));
        initFrame();
        //Inicio OBJETOS
        entities = new ArrayList<Entity>();
        player = new Player(WIDTH/2,HEIGHT/2,12,12);
        entities.add(player);
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        shoots = new ArrayList<PlayerShoot>();
        enemySpawn = new EnemySpawn();
        asteroid = new AsteroidL(Game.WIDTH/2,Game.HEIGHT/2,160,160);
        entities.add(asteroid);

    }

    public void initFrame(){
        frame = new JFrame("Jogo #1");
        frame.add(this);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public synchronized void start(){
        thread = new Thread(this);
        isRunning=true;
        thread.start();
    }

    public synchronized void stop(){
        isRunning=false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void tick(){
        enemySpawn.tick();
        asteroid.tick();


        for(int i=0;i < shoots.size();i++ ){
            PlayerShoot e= shoots.get(i);
            e.tick();
        }
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
        }


    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,WIDTH,HEIGHT); //g.fill pode ser usado para formas alem de retangulos
        //Game render
        Graphics2D g2 = (Graphics2D) g;
        //Game render

        for(int i=0;i < shoots.size();i++ ){
            PlayerShoot e= shoots.get(i);
            e.render(g);
        }
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }





















        //Game render

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image,0,0,WIDTH*scale,HEIGHT*scale,null);
        bs.show();
    }




    @Override
    public void run() {
        long lasTime = System.nanoTime();
        double amountOfTicks = 120.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames =0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while(isRunning){
            long now = System.nanoTime();
            delta+= (now- lasTime)/ns;
            lasTime=now;
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: "+frames);
                frames=0;
                timer+=1000;
            }

        }
        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D){
            player.setToRight(true);
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT|| e.getKeyCode()==KeyEvent.VK_A ){
            player.setToLeft(true);
        }
        if(e.getKeyCode()==KeyEvent.VK_UP|| e.getKeyCode()==KeyEvent.VK_W){
            player.setToFront(true);
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            player.setShooting(true);
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT|| e.getKeyCode()==KeyEvent.VK_D){
            player.setToRight(false);

        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT|| e.getKeyCode()==KeyEvent.VK_A){
            player.setToLeft(false);
        }
        if(e.getKeyCode()==KeyEvent.VK_UP|| e.getKeyCode()==KeyEvent.VK_W){
            player.setToFront(false);
        }

    }
}


