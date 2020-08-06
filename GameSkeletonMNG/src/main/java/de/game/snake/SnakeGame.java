package de.game.snake;

import de.game.core.AbstractGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

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
        FKeksdose = new Keksdose();
        FZustand = ZUSTAND_SPLASH_SCREEN;
    }

    @Override
    public void init() {
        FFeld.init();
        FSchlange1.init(10, 10, 1, 0);
        FSchlange1.wachsen(9);
        FKeksdose.init();
        for (int i = 0; i < 100; i++) {
            neuerKeks();
        }
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
                        System.out.println("Game Over: Collided with wall at " + FSchlange1.getKopfX() + "/" + FSchlange1.getKopfY());
                        FZustand = ZUSTAND_GAME_OVER;

                    }
                    if (FSchlange1.istKoerper(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FZustand = ZUSTAND_GAME_OVER;

                    }

                    // Kollision mit Keks checken
                    Keks k = FKeksdose.getKeksAtPosition(FSchlange1.getKopfX(), FSchlange1.getKopfY());
                    if (k != null) {
                        FSchlange1.wachsen(1);
                        FKeksdose.entferneKeks(k);
                        neuerKeks();
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
                drawSplash(graphics);
            }
            break;
            case ZUSTAND_GAME_OVER: {
                drawGameOver(graphics);
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
                            FSchlange1.queueCommand(1, 0);
                        }
                        break;
                        case KeyEvent.VK_LEFT: {
                            FSchlange1.queueCommand(-1, 0);
                        }
                        break;
                        case KeyEvent.VK_UP: {
                            FSchlange1.queueCommand(0, -1);
                        }
                        break;
                        case KeyEvent.VK_DOWN: {
                            FSchlange1.queueCommand(0, 1);
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

    private void neuerKeks() {
        int LX, LY;
        do {
            LX = (int) (Math.random() * Spielfeld.WIDTH);
            LY = (int) (Math.random() * Spielfeld.HEIGHT);
        } while (FSchlange1.istSchlange(LX, LY)
                || FFeld.istWand(LX, LY)
                || (null != FKeksdose.getKeksAtPosition(LX, LY)));
        FKeksdose.addKeks(new Keks(LX, LY, new Color(241, 196, 15)));
    }

    public void drawSplash(Graphics g) {
        long LTime = System.currentTimeMillis();
        int LOffsetX = (int) (LTime % 17 + LTime % 61 / 7) / 3;
        int LOffsetY = (int) (LTime % 19 + LTime % 151 / 11) / 3;

        g.drawImage(IMG_SPLASH, 0, 0, null);
        g.drawImage(IMG_SNAKE, 100 + LOffsetX, 300 + LOffsetY, 250, 250, null);

        g.setColor(C_DARK);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("hissssss", 250 + LOffsetX, 325 + LOffsetY);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Press [SPACE] to start.", 300, 400);
    }

    public void drawGameOver(Graphics g) {
        FFeld.draw(g);
        FSchlange1.draw(g);
        FKeksdose.draw(g);
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(0, 0, Spielfeld.WIDTH * 10, Spielfeld.HEIGHT * 10);
        g.setColor(C_LIGHT);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Game over!", 100, 100);
        g.drawString("Press [SPACE] to return to the title screen.", 100, 150);
    }
}
