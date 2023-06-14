package Main;

import Entities.AsteroidL;
import Entities.AsteroidM;
import Entities.AsteroidS;
import Graficos.SpriteSheet;

import java.util.Random;

public class EnemySpawn {
    public static SpriteSheet spriteL= new SpriteSheet("/asteroidL.png");
    public static SpriteSheet spriteM= new SpriteSheet("/asteroidM.png");;
    public static SpriteSheet spriteS = new SpriteSheet("/asteroidS.png");;
    private double timer;
    private double timerSpawn=500;

    private AsteroidL asteroidLarge;
    private AsteroidM asteroidMedium;
    private AsteroidS asteroidSmall;


    public EnemySpawn() { // instancia as sprites dos respectivos tipos de asteroides
        spriteL = new SpriteSheet("/asteroidL.png");
        spriteM = new SpriteSheet("/asteroidM.png");
        spriteS = new SpriteSheet("/asteroidS.png");
    }


    public void spawnRandom(){ // gera asteroides entre os 3 tipos com chances estabelecidas abaixo
        Random rand = new Random();
        int spawner= rand.nextInt(100);
        timer++;
        if(timer == timerSpawn) {
            if (spawner <= 60) {
                asteroidSmall = new AsteroidS(0, 0, 40, 40,spriteS.getSprite(0,0,40,40));
                Game.entities.add(asteroidSmall);
            } else if (spawner <= 80) {
                asteroidMedium = new AsteroidM(0, 0, 80, 80,spriteM.getSprite(0,0,80,80));
                Game.entities.add(asteroidMedium);
            } else if (spawner <= 100) {
                asteroidLarge = new AsteroidL(0, 0, 160, 160,spriteL.getSprite(0,0,160,160));
                Game.entities.add(asteroidLarge);
            }
            timer=0;
            if(timerSpawn > 120){
                timerSpawn-=5;
            }
        }

    }

    public void spawnLarge(){ // gera um asteroide grande
        asteroidLarge = new AsteroidL(0, 0, 160, 160,spriteL.getSprite(0,0,160,160));
        Game.entities.add(asteroidLarge);
    }
    public void spawnMedium() { // gera um asteroide medio
        asteroidMedium = new AsteroidM(0, 0, 80, 80,spriteM.getSprite(0,0,80,80));
        Game.entities.add(asteroidMedium);
    }

    public void spawnSmall(){ // gera um asteroide pequeno
        asteroidSmall = new AsteroidS(0, 0, 40, 40,spriteS.getSprite(0,0,40,40));
        Game.entities.add(asteroidSmall);
    }

    public void tick(){
        spawnRandom();
    }


}
