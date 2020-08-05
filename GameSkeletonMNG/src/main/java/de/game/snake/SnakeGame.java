package de.game.snake;

import de.game.core.AbstractGame;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class SnakeGame extends AbstractGame {

    // Variablen
    Spielfeld FFeld;
    Schlange FSchlange1;

    // Implementierung
    public SnakeGame(Frame core) {
        super(core, 800, 600);
        FFeld = new Spielfeld();
        FSchlange1 = new Schlange(10, 10, 1, 0, new Color(124, 158, 178));
    }

    @Override
    public void init() {
        FFeld.init();
        FSchlange1.init();
    }

    @Override
    public void done() {

    }

    @Override
    public void calc(int tickCount) {
        FSchlange1.calc(tickCount);
    }

    @Override
    public void draw(Graphics graphics) {
        FFeld.draw(graphics);
        FSchlange1.draw(graphics);
    }

    @Override
    public void processKeyEvent(KeyEvent e) {
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
}
