package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Menu {

    public String[] options = {"new game", "scoreboard", "exit"};
    public int currentOption = 0, maxOption = options.length - 1;
    public String[] optionsPause = {"continue", "exit"};
    public String optionScoreboard = "sair";
    public int currentOptionPause = 0, maxOptionPause = optionsPause.length - 1;
    public boolean up, down, enter;
    private BufferedImage menuBackground;


    private Font pixelFont110;
    private Font pixelFont40;


    public Menu() throws IOException, FontFormatException {
        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/Minecraft.ttf");
            pixelFont110 = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 110);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/Minecraft.ttf");
            pixelFont40 = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 40);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


    }

    public void tick() {
        if (Game.gameState == "MENU") {
            if (up) {
                up = false;
                currentOption--;
                if (currentOption < 0) {
                    up = false;
                    currentOption = maxOption;
                }
            }
            if (down) {
                down = false;
                currentOption++;
                if (currentOption > maxOption) {
                    currentOption = 0;
                }
            }

            if (enter) {
                enter = false;
                if (options[currentOption] == "new game") {
                    Game.gameState = "NORMAL";
                    Game.restartGame=true;
                } else if (options[currentOption] == "scoreboard") {
                    Game.gameState = "SCOREBOARD";
                } else if (options[currentOption] == "exit") {
                    System.exit(1);
                }
            }
        } else if (Game.gameState == "PAUSE") {
            if (up) {
                up = false;
                currentOptionPause--;
                if (currentOptionPause < 0) {
                    up = false;
                    currentOptionPause = maxOptionPause;
                }
            }
            if (down) {
                down = false;
                currentOptionPause++;
                if (currentOptionPause > maxOptionPause) {
                    currentOptionPause = 0;
                }
            }

            if (enter) {
                enter = false;
                if (optionsPause[currentOptionPause] == "continue") {
                    Game.gameState = "NORMAL";
                } else if (optionsPause[currentOptionPause] == "exit") {
                    Game.gameState = "MENU";
                }
            }


        } else if (Game.gameState == "SCOREBOARD") {
            if(enter){
                enter = false;
                    if(optionScoreboard.equalsIgnoreCase("sair")){
                        Game.gameState="MENU";
                    }
            }
        }


    }

    public void render(Graphics g) {
        //RENDERIZA GRAFICOS DO MENU
        if (Game.gameState == "MENU") {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, Game.WIDTH * Game.scale, Game.HEIGHT * Game.scale);
            try {
                menuBackground = ImageIO.read(getClass().getResource("/menu.png"));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.drawImage(menuBackground, 0, 0, Game.WIDTH, Game.HEIGHT, null);


            g.setColor(Color.WHITE);
            g.setFont(pixelFont110);
            g.drawString("Asteroids", (Game.WIDTH * Game.scale) / 2 - 250, (Game.HEIGHT * Game.scale) / 2 - 200);

            //RENDERIZA Opcoes de menu

            g.setColor(Color.WHITE);
            g.setFont(pixelFont40);
            g.drawString("Start", (Game.WIDTH * Game.scale) / 2 - 50, 250);
            g.drawString("ScoreBoard", (Game.WIDTH * Game.scale) / 2 - 120, 350);
            g.drawString("Exit", (Game.WIDTH * Game.scale) / 2 - 50, 450);

            if (options[currentOption] == "new game") {
                g.drawString(">", (Game.WIDTH * Game.scale) / 2 - 90, 250);
            } else if (options[currentOption] == "scoreboard") {
                g.drawString(">", (Game.WIDTH * Game.scale) / 2 - 160, 350);
            } else if (options[currentOption] == "exit") {
                g.drawString(">", (Game.WIDTH * Game.scale) / 2 - 90, 450);
            }

        } else if (Game.gameState == "PAUSE") {
            //RENDERIZA A TELA DE PAUSE
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, Game.WIDTH * Game.scale, Game.HEIGHT * Game.scale);

            g.setColor(Color.RED);
            g.setFont(pixelFont110);
            g.drawString("PAUSED", (Game.WIDTH * Game.scale) / 2 - 230, (Game.HEIGHT * Game.scale) / 2 - 200);

            //RENDERIZA AS OPCOES PAUSE
            g.setColor(Color.WHITE);
            g.setFont(pixelFont40);
            g.drawString("Continue", (Game.WIDTH * Game.scale) / 2 - 90, 250);
            g.drawString("Exit", (Game.WIDTH * Game.scale) / 2 - 50, 350);
            g.drawString("If you quit in middle of the game score won't be saved.",((Game.WIDTH * Game.scale)/2)-550,650);
            g.setColor(Color.red);
            g.drawString("WARNING:",((Game.WIDTH * Game.scale)/2)-550,610);


            if (optionsPause[currentOptionPause] == "continue") {
                g.drawString(">", (Game.WIDTH * Game.scale) / 2 - 130, 250);
            } else if (optionsPause[currentOptionPause] == "exit") {
                g.drawString(">", (Game.WIDTH * Game.scale) / 2 - 90, 350);
            }
        } else if (Game.gameState=="SCOREBOARD") {
            //RENDERIZA A TELA DE SCOREBOARD
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, Game.WIDTH * Game.scale, Game.HEIGHT * Game.scale);

            g.setColor(Color.white);
            g.drawRect(Game.WIDTH/3+Game.WIDTH/20,Game.HEIGHT/12,Game.WIDTH/4,Game.HEIGHT-Game.HEIGHT/3-50);
            g.setFont(pixelFont40);

            //RENDERIZA OS SCORES
            g.drawString("1 - "+Game.scoreboard[9],514,110);
            g.drawString("2 - "+Game.scoreboard[8],510,150);
            g.drawString("3 - "+Game.scoreboard[7],510,190);
            g.drawString("4 - "+Game.scoreboard[6],510,230);
            g.drawString("5 - "+Game.scoreboard[5],508,270);
            g.drawString("6 - "+Game.scoreboard[4],510,310);
            g.drawString("7 - "+Game.scoreboard[3],510,350);
            g.drawString("8 - "+Game.scoreboard[2],510,390);
            g.drawString("9 - "+Game.scoreboard[1],510,430);
            g.drawString("10 - "+Game.scoreboard[0],510,470);

            //RENDERIZA AS OPCOES DE SCOREBOARD
            g.drawString("back to menu",Game.WIDTH/3+ 100,600);
            g.drawString(">",Game.WIDTH/3+ 80,600);
            g.setColor(Color.red);
            g.drawString("ScoreBoard", Game.WIDTH/3+Game.WIDTH/20,50);

        }


    }
}
