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

    private final int START_LENGTH = 9;

    // Variablen
    private int FZustand;

    Spielfeld FFeld;
    Schlange FSchlange1;
    Schlange FSchlange2;
    Keksdose FKeksdose;
    BumperCollection FBumperListe;
    Warpfeld FWarpfeld;

    private int AnzahlKekse = 4;
    private int AnzahlBumper = 4;
    private boolean FLebt1, FLebt2;
    private String FUrsache1, FUrsache2;
    private String FWinnerText;

    // Implementierung
    public SnakeGame(Frame core) {
        super(core, 800, 600);
        FFeld = new Spielfeld();
        FSchlange1 = new Schlange(10, 10, 1, 0, new Color(124, 158, 178));
        FSchlange2 = new Schlange(10, 40, 1, 0, new Color(204, 113, 120));
        FKeksdose = new Keksdose();
        FBumperListe = new BumperCollection();
        FWarpfeld = new Warpfeld(0, 0, 0, 0);
        FZustand = ZUSTAND_SPLASH_SCREEN;
    }

    @Override
    public void init() {
        FFeld.init();
        FSchlange1.init(10, 10, 1, 0, START_LENGTH);
        FLebt1 = true;
        FUrsache1 = "";
        FSchlange2.init(10, 40, 1, 0, START_LENGTH);
        FLebt2 = true;
        FUrsache2 = "";

        FWarpfeld.init(20, 20, 50, 50);

        FBumperListe.init();
        for (int i = 0; i < AnzahlBumper; i++) {
            neuerBumper();
        }

        // Kekse als letztes generieren! -> Position der Kekse
        FKeksdose.init();
        for (int i = 0; i < AnzahlKekse; i++) {
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
                if (FLebt1 && FSchlange1.calc(tickCount)) {
                    if (FFeld.istWand(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FUrsache1 = "(Gegen Wand gelaufen)";
                        FLebt1 = false;
                    }
                    if (FSchlange1.istKoerper(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FUrsache1 = "(Gegen sich selbst gelaufen)";
                        FLebt1 = false;
                    }
                    if (FSchlange2.istKoerper(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FUrsache1 = "(Gegen Schlange 2 gelaufen)";
                        FLebt1 = false;
                    }

                    // Kollision mit Keks checken
                    Keks k = FKeksdose.getKeksAtPosition(FSchlange1.getKopfX(), FSchlange1.getKopfY());
                    if (k != null) {
                        FSchlange1.wachsen(k.getZucker());
                        FKeksdose.entferneKeks(k);
                        neuerKeks();
                    }

                    if (FBumperListe.istBumper(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FSchlange1.linksDrehen();
                    }

                    if (FWarpfeld.istWarpfeld1(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FSchlange1.teleportieren(FWarpfeld.getFPortalX2(), FWarpfeld.getFPortalY2());
                    } else if (FWarpfeld.istWarpfeld2(FSchlange1.getKopfX(), FSchlange1.getKopfY())) {
                        FSchlange1.teleportieren(FWarpfeld.getFPortalX1(), FWarpfeld.getFPortalY1());
                    }
                }

                if (FLebt2 && FSchlange2.calc(tickCount)) {
                    if (FFeld.istWand(FSchlange2.getKopfX(), FSchlange2.getKopfY())) {
                        FUrsache2 = "(Gegen Wand gelaufen)";
                        FLebt2 = false;
                    }
                    if (FSchlange1.istKoerper(FSchlange2.getKopfX(), FSchlange2.getKopfY())) {
                        FUrsache2 = "(Gegen Schlange 1 gelaufen)";
                        FLebt2 = false;
                    }
                    if (FSchlange2.istKoerper(FSchlange2.getKopfX(), FSchlange2.getKopfY())) {
                        FUrsache2 = "(Gegen sich selbst gelaufen)";
                        FLebt2 = false;
                    }

                    // Kollision mit Keks checken
                    Keks k = FKeksdose.getKeksAtPosition(FSchlange2.getKopfX(), FSchlange2.getKopfY());
                    if (k != null) {
                        FSchlange2.wachsen(k.getZucker());
                        FKeksdose.entferneKeks(k);
                        neuerKeks();
                    }

                    if (FBumperListe.istBumper(FSchlange2.getKopfX(), FSchlange2.getKopfY())) {
                        FSchlange2.linksDrehen();
                    }

                    if (FWarpfeld.istWarpfeld1(FSchlange2.getKopfX(), FSchlange2.getKopfY())) {
                        FSchlange2.teleportieren(FWarpfeld.getFPortalX2(), FWarpfeld.getFPortalY2());
                    } else if (FWarpfeld.istWarpfeld2(FSchlange2.getKopfX(), FSchlange2.getKopfY())) {
                        FSchlange2.teleportieren(FWarpfeld.getFPortalX1(), FWarpfeld.getFPortalY1());
                    }
                }

                // Erst Game Over, wenn beide Schlangen gestorben sind
                // -> Comeback-Potential wenn die verlierende Schlange genug
                //    Punkte sammelt
                if (!(FLebt1 || FLebt2)) {
                    if (FSchlange1.getScore() == FSchlange2.getScore()) {
                        FWinnerText = "Unentschieden!";
                    } else if (FSchlange1.getScore() > FSchlange2.getScore()) {
                        FWinnerText = "Schlange 1 hat gewonnen!";
                    } else {
                        FWinnerText = "Schlange 2 hat gewonnen!";
                    }
                    FZustand = ZUSTAND_GAME_OVER;
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
        /**
         * Notiz vom Autor: Ich habe mal die Funktion, dass der Score während
         * des Spiels angezeigt wird, nicht implementiert, da es dadurch
         * spannender wird. Der Score wird am Ende angezeigt, wenn beide
         * Schlangen gestorben sind.
         */
        switch (FZustand) {
            case ZUSTAND_GAME_RUNNING: {
                FFeld.draw(graphics);
                FSchlange1.draw(graphics);
                FSchlange2.draw(graphics);
                FKeksdose.draw(graphics);
                FBumperListe.draw(graphics);
                FWarpfeld.draw(graphics);
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
                        case KeyEvent.VK_D: {
                            FSchlange2.queueCommand(1, 0);
                        }
                        break;
                        case KeyEvent.VK_A: {
                            FSchlange2.queueCommand(-1, 0);
                        }
                        break;
                        case KeyEvent.VK_W: {
                            FSchlange2.queueCommand(0, -1);
                        }
                        break;
                        case KeyEvent.VK_S: {
                            FSchlange2.queueCommand(0, 1);
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

    private void neuerBumper() {
        int LX, LY;
        do {
            LX = (int) (Math.random() * Spielfeld.WIDTH);
            LY = (int) (Math.random() * Spielfeld.HEIGHT);
        } while (FFeld.istWand(LX, LY)
                || FWarpfeld.istWarpfeld1(LX, LY)
                || FWarpfeld.istWarpfeld2(LX, LY));
        FBumperListe.addBumper(LX, LY, Color.magenta);
    }

    private void neuerKeks() {
        int LX, LY;
        do {
            LX = (int) (Math.random() * Spielfeld.WIDTH);
            LY = (int) (Math.random() * Spielfeld.HEIGHT);
        } while (FSchlange1.istSchlange(LX, LY)
                || FSchlange2.istSchlange(LX, LY)
                || FFeld.istWand(LX, LY)
                || FBumperListe.istBumper(LX, LY)
                || FWarpfeld.istWarpfeld1(LX, LY)
                || FWarpfeld.istWarpfeld2(LX, LY)
                || (null != FKeksdose.getKeksAtPosition(LX, LY)));

        int LZucker = (int) (1 + Math.random() * 5);
        FKeksdose.addKeks(new Keks(LX, LY, new Color(241 - LZucker * 25, 196, 15 + LZucker * 10), LZucker));
    }

    public void drawSplash(Graphics g) {
        long LTime = System.currentTimeMillis();
        int LOffsetX = (int) (LTime % 17 + LTime % 61 / 7) / 3;
        int LOffsetY = (int) (LTime % 19 + LTime % 151 / 11) / 3;

        g.drawImage(IMG_SPLASH, 0, 0, null);
        g.drawImage(IMG_SNAKE, 100 + LOffsetX, 300 + LOffsetY, 250, 250, null);

        g.setColor(C_DARK);

        g.setFont(new Font("Papyrus", Font.PLAIN, 20));
        g.drawString("hissssss", 250 + LOffsetX, 325 + LOffsetY);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Press [SPACE] to start.", 300, 400);
    }

    public void drawGameOver(Graphics g) {
        FFeld.draw(g);
        FSchlange1.draw(g);
        FSchlange2.draw(g);
        FKeksdose.draw(g);
        FBumperListe.draw(g);
        FWarpfeld.draw(g);
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(0, 0, Spielfeld.WIDTH * 10, Spielfeld.HEIGHT * 10);
        g.setColor(C_LIGHT);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Game over!", 100, 100);
        g.drawString("Press [SPACE] to return to the title screen.", 100, 150);
        g.drawString(FWinnerText, 100, 200);
        g.drawString("Score S1: " + FSchlange1.getScore() + " " + FUrsache1, 100, 250);
        g.drawString("Score S2: " + FSchlange2.getScore() + " " + FUrsache2, 100, 300);
    }
}
