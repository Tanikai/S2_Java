package de.game.snake;

import de.game.core.AbstractGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

public class SnakeGame extends AbstractGame {

    // Konstanten
    public static final int ZUSTAND_SPLASH_SCREEN = 0;
    public static final int ZUSTAND_GAME_RUNNING = 1;
    public static final int ZUSTAND_GAME_OVER = 2;
    
    public static final Color C_LIGHT = new Color(248, 244, 227);
    public static final Color C_DARK = new Color(26, 20, 35);
    
    private final Image IMG_SPLASH = Toolkit.getDefaultToolkit().getImage(SnakeGame.class.getResource("/splashscreen.png"));
    private final Image IMG_SNAKE = Toolkit.getDefaultToolkit().getImage(SnakeGame.class.getResource("/catsnake.png"));

    // Variablen
    private int FZustand;
    
    Spielfeld FFeld;
    Schlange FSchlange1;
    Keksdose FKeksdose;

    // Implementierung
    public SnakeGame(Frame core) {
        super(core, 800, 600);
        FFeld = new Spielfeld();
        FSchlange1 = new Schlange(10, 10, 1, 0, new Color(124, 158, 178));
        FKeksdose = new Keksdose(3);
        FZustand = ZUSTAND_SPLASH_SCREEN;
    }
    
    @Override
    public void init() {
        FFeld.init();
        FSchlange1.init(10, 10, 1, 0);
        FSchlange1.wachsen(0);
        FKeksdose.init();
    }
    
    @Override
    public void done() {
        
    }
    
    @Override
    public void calc(int tickCount) {
        switch (FZustand) {
            case ZUSTAND_GAME_RUNNING: {
                if (FSchlange1.calc(tickCount)) {
                    if (FFeld.istWand(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FZustand = ZUSTAND_GAME_OVER;
                        System.out.println("Collided with wall");
                    }
                    if (FSchlange1.istKoerper(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FZustand = ZUSTAND_GAME_OVER;
                        
                    }
                    if (FKeksdose.istKeks(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FSchlange1.wachsen(1);
                    }
                }
            }
            break;
            case ZUSTAND_SPLASH_SCREEN: {
                // Splash Screen calc
            }
            break;
            case ZUSTAND_GAME_OVER: {
                // Game Over-Screen calc
            }
            break;
        }
    }
    
    @Override
    public void draw(Graphics graphics) {
        switch (FZustand) {
            case ZUSTAND_GAME_RUNNING: {
                FFeld.draw(graphics);
                FSchlange1.draw(graphics);
                FKeksdose.draw(graphics);
            }
            break;
            case ZUSTAND_SPLASH_SCREEN: {
                long LTime = System.currentTimeMillis();
                int LOffsetX = (int) (LTime % 17 + LTime % 61 / 7) / 3;
                int LOffsetY = (int) (LTime % 19 + LTime % 151 / 11) / 3;
                
                graphics.drawImage(IMG_SPLASH, 0, 0, null);
                graphics.drawImage(IMG_SNAKE, 100 + LOffsetX, 300 + LOffsetY, 250, 250, null);
                
                graphics.setColor(C_DARK);
                
                graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                graphics.drawString("hissssss", 250 + LOffsetX, 325 + LOffsetY);
                
                graphics.setFont(new Font("Arial", Font.PLAIN, 30));
                graphics.drawString("Press [SPACE] to start.", 300, 400);
            }
            break;
            case ZUSTAND_GAME_OVER: {
                FFeld.draw(graphics);
                FSchlange1.draw(graphics);
                graphics.setColor(new Color(0, 0, 0, 128));
                graphics.fillRect(0, 0, Spielfeld.WIDTH * 10, Spielfeld.HEIGHT * 10);
                graphics.setColor(C_LIGHT);
                graphics.setFont(new Font("Arial", Font.PLAIN, 30));
                graphics.drawString("Game over!", 100, 100);
                graphics.drawString("Press [SPACE] to return to the title screen.", 100, 150);
            }
            break;
        }
    }
    
    @Override
    public void processKeyEvent(KeyEvent e) {
        switch (FZustand) {
            case ZUSTAND_GAME_RUNNING: {
                if (e.getID() == KeyEvent.KEY_RELEASED) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT: {
                            FSchlange1.neueRichtung(1, 0);
                        }
                        break;
                        case KeyEvent.VK_LEFT: {
                            FSchlange1.neueRichtung(-1, 0);
                        }
                        break;
                        case KeyEvent.VK_UP: {
                            FSchlange1.neueRichtung(0, -1);
                        }
                        break;
                        case KeyEvent.VK_DOWN: {
                            FSchlange1.neueRichtung(0, 1);
                        }
                        break;
                    }
                }
            }
            break;
            
            case ZUSTAND_SPLASH_SCREEN: {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        this.init();
                        FZustand = ZUSTAND_GAME_RUNNING;
                    }
                }
            }
            break;
            
            case ZUSTAND_GAME_OVER: {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        FZustand = ZUSTAND_SPLASH_SCREEN;
                    }
                }
            }
            break;
        }
    }
}
