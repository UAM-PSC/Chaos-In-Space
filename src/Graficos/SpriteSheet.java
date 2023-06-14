package Graficos;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    //Classe SpriteSheet e usada para facilitar as importacoes das sprites

   public BufferedImage spritesheet;

    public SpriteSheet(String path){
        try {
            spritesheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getSprite(int x,int y, int width, int height){
        return spritesheet.getSubimage(x,y,width,height);
    }
}
