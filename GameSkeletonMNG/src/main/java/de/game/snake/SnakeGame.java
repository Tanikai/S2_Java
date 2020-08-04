package de.game.snake;

import de.game.core.AbstractGame;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;

public class SnakeGame extends AbstractGame {

    // Variablen
    Spielfeld FFeld;
    Schlange FSchlange1;

    // Implementierung
    public SnakeGame(Frame core) {
        super(core, 800, 600);
        FFeld = new Spielfeld(800, 600);
        FSchlange1 = new Schlange(10, 10, 'u', new Color(124, 158, 178));
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
        FSchlange1.calc();
    }

    @Override
    public void draw(Graphics graphics) {
        FFeld.draw(graphics);
        FSchlange1.draw(graphics);
    }

}
