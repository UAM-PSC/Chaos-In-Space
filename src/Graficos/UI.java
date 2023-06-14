package Graficos;

import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    //Na classe de UI sao feitas as renderizacoes da UI do player


    public static BufferedImage life = Game.sprite.getSprite(0,0,20,40);
    Font pixelFont15;
    Font pixelFont24;
    public UI(){//metodo construtor onde sao definidas os dois tipos de fonte que sao utilizados
        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/Minecraft.ttf");
            pixelFont24 = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 24);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/Minecraft.ttf");
            pixelFont15 = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 15);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g){
        //Renderiza UI dentro do jogo como score e vidas
        g.setColor(Color.white);
        g.setFont(pixelFont15);
        g.drawString("Score: "+Game.player.score,10,30);
        g.setColor(Color.white);
        g.setFont(pixelFont24);
        g.drawString("Lifes: ",10,60);
        //Define as vidas que aparecem para o jogador
        if(Game.player.getLife() == 3){
            g.drawImage(life,10,70,10,20,null);
            g.drawImage(life,25,70,10,20,null);
            g.drawImage(life,40,70,10,20,null);
        } else if (Game.player.getLife() == 2) {
            g.drawImage(life,10,70,10,20,null);
            g.drawImage(life,25,70,10,20,null);
        } else if (Game.player.getLife() == 1) {
            g.drawImage(life,10,70,10,20,null);
        }


    }
}
