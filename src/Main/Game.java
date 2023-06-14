package Main;


import Entities.*;
import Graficos.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.swing.*;


//Classe Game é uma subclasse de Canvas para facilitar opcoes graficas no jogo
// e implementa Runnable para trabalhar com Threads e KeyListener para deteccao do teclado
//na classe Game sao executados toda a logica e renderizacao do jogo de fato
//todos os arquivos como save do scoreboard,sprites, fontes e fundo sao exportados da pasta /res que e definida como Resourses Root
//dentro do proprio projeto
public class Game extends Canvas implements Runnable,KeyListener {

    public static JFrame frame;
    private boolean isRunning=true;
    private Thread thread;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int scale = 1;
    public static String gameState="MENU";
    private BufferedImage image;
    public static Player player;
    private EnemySpawn enemySpawn;
    public static SpriteSheet sprite= new SpriteSheet("/player.png");
    private int updateScoreboard=0,scoreChanger=1;

    private UI ui;
    private Menu menu;
    private boolean showMessageGameOver;
    public static ArrayList<PlayerShoot> shoots;
    public static ArrayList<Entity> entities;
    private int framesGameOver;
    public static boolean restartGame=false;
    private ScoreManager scoreManager;
    public static int[] scoreboard = new int[10];


    public Game(){
        //inicializa Keylistener e define a dimensao padrao da janela
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH*scale, HEIGHT*scale));
        initFrame();
        //Inicio OBJETOS
        ui = new UI();
        sprite = new SpriteSheet("/player.png");
        entities = new ArrayList<Entity>();
        player = new Player(WIDTH/2,HEIGHT/2,20,40,sprite.getSprite(0,0,20,40));
        entities.add(player);
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        shoots = new ArrayList<PlayerShoot>();
        enemySpawn = new EnemySpawn();
        scoreManager = new ScoreManager();
        try {
            menu = new Menu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    //initFrame possui as opcoes de janela do jogo
    public void initFrame(){
        frame = new JFrame("Asteroids");
        frame.add(this);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(this);
    }



    public synchronized void start(){ //metodo utilizado para estanciar, iniciar uma nova thread e definir que o jogo esta rodando
        thread = new Thread(this);
        isRunning=true;
        thread.start();
    }


    public synchronized void stop(){ //metodo utilizado caso haja algum erro durante a execucao do jogo
        isRunning=false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) { //metodo main que instancia novo jogo e chama a classe start para iniciar a Thread
        Game game = new Game();
        game.start();
    }

    public void restartGame(){ // metodo utilizado para reiniciar todos os dentro do proprio jogo
        ui = new UI();
        sprite = new SpriteSheet("/player.png");
        entities = new ArrayList<Entity>();
        player = new Player(WIDTH/2,HEIGHT/2,20,40,sprite.getSprite(0,0,20,40));
        entities.add(player);
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        shoots = new ArrayList<PlayerShoot>();
        enemySpawn = new EnemySpawn();
        updateScoreboard=0;

    }
    public void tick(){ // metodo onde serao executados toda a logica do jogo
        if(gameState == "NORMAL") {
            this.restartGame=false;
            enemySpawn.tick();

            for (int i = 0; i < shoots.size(); i++) {
                PlayerShoot e = shoots.get(i);
                e.tick();
            }
            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);
                e.tick();
            }


        } else if (gameState == "MENU" || gameState == "PAUSE" || gameState == "SCOREBOARD") {
            scoreManager.readScoreboard();
            menu.tick();

        } else if (gameState == "GAMEOVER") {

            if(updateScoreboard == 0){
                updateScoreboard++;
                if(scoreboard[0] == 0){
                    scoreboard[0]= player.score;
                } else if (scoreboard[0] < player.score) {
                    scoreboard[scoreChanger] = scoreboard[0];
                    scoreboard[0]= player.score;
                    scoreChanger++;
                }else{
                    scoreboard[scoreChanger]= player.score;
                    scoreChanger++;
                }

                if(scoreChanger> scoreboard.length){
                    scoreChanger=1;
                }
                Arrays.sort(scoreboard);
            }
            scoreManager.saveScoreboard();

            this.framesGameOver++;
            if(this.framesGameOver == 60){
                this.framesGameOver=0;
                if(this.showMessageGameOver){
                    this.showMessageGameOver=false;
                }else{
                    this.showMessageGameOver=true;
                }
            }
        }
        if(restartGame){
            this.restartGame=false;
            this.restartGame();
        }


    }
    public void render(){ //metodo onde sera feito toda a parte grafica do jogo
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
        for(int i=0;i < shoots.size();i++ ){
            PlayerShoot e= shoots.get(i);
            e.render(g);
        }
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }
        ui.render(g);

        if(showMessageGameOver) {

            g2.drawString(">PRESSIONE ENTER PARA CONTINUAR<", WIDTH/2-150, HEIGHT/2);
        }else if (gameState == "MENU" || gameState == "PAUSE" || gameState=="SCOREBOARD") {
            menu.render(g);
        }


        //Game render

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image,0,0,WIDTH*scale,HEIGHT*scale,null);
        bs.show();
    }




    @Override
    public void run() { // metodo padrao de uma Thread que realmente executa o jogo
        long lasTime = System.nanoTime(); // usado para calcular o tempo decorrido entre cada iteracao do loop principal
        double amountOfTicks = 120.0; // define a quantidade de atualizacoes do jogo que devem ocorrer por segundo
        double ns = 1000000000 / amountOfTicks; //calcula a quantidade de nanossegundos que deve passar entre cada atualizacao do jogo
        double delta = 0;//representa o tempo acumulado desde a ultima atualizacao do jogo
        int frames =0;//usada para contar o numero de quadros
        double timer = System.currentTimeMillis(); //usado para controlar quando exibir as informações de FPS
        requestFocus(); //usada para solicitar o foco
        while(isRunning){
            long now = System.nanoTime(); //obtem o valor atual do tempo em nanossegundos
            delta+= (now- lasTime)/ns; //calcula o tempo decorrido desde a ultima iteracao e adiciona a delta
            lasTime=now; // atualiza o valor de lasTime
            if(delta >= 1){ //verifica se e hora de executar uma atualizacao do jogo
                tick(); //chama o metodo tick
                render(); // chama metodo render
                frames++; //incrementa frames para numero de quadros
                delta--; // remove 1 de delta para indicar que ja foi feita uma atualizacao
            }
            if(System.currentTimeMillis() - timer >= 1000){ //verifica o tempo de 1 seg para a contagem de quadros
                System.out.println("FPS: "+frames);
                frames=0;
                timer+=1000;
            }

        }
        stop(); // chama o metodo stop d Thread caso algo de errado no loop
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) { // metodo que verifica as teclas pressionadas
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){

                if(gameState =="NORMAL"){
                    gameState="PAUSE";
                } else if (gameState =="PAUSE") {
                    gameState= "NORMAL";
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                player.setToRight(true);

            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                player.setToLeft(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                if (gameState == "MENU") {
                    menu.up=true;
                } else if (gameState == "PAUSE") {
                    menu.up=true;
                } else if (gameState=="NORMAL") {
                    player.setToFront(true);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {

                if (gameState == "MENU") {
                    menu.down=true;
                } else if (gameState == "PAUSE") {
                    menu.down=true;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                player.setShooting(true);
            }


            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (gameState == "GAMEOVER") {
                    this.restartGame = true;
                    this.showMessageGameOver=false;
                    gameState="MENU";
                } else if (gameState == "MENU") {
                    menu.enter = true;
                } else if (gameState == "PAUSE") {
                    menu.enter = true;
                } else if (gameState == "SCOREBOARD") {
                    menu.enter = true;
                }
            }



    }

    @Override
    public void keyReleased(KeyEvent e) { // metodo que verifica teclas que nao estao mais sendo pressionadas
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


