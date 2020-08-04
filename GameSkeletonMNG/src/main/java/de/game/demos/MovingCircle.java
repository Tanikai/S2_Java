/*
 * MovingCircle.java
 * Erstellt 19.9.2008
 */
package de.game.demos;

import de.game.core.AbstractGame;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demo für die Gameengine.
 *
 * @author caschoff
 * @version 1.0
 */
public class MovingCircle extends AbstractGame {

    //
    // Variablen, die alle haben sollten.
    //
    /**
     * Der Logger. Siehe log4j.properties.
     */
    private static final Logger logger = LogManager.getLogger(MovingCircle.class);
    /**
     * Die Versionsnummer.
     */
    protected static final long serialVersionUID = 1l;
    //
    // Eigene Variablen
    //
    private int x, y;

    /**
     * Der Konstruktor. Wir rufen nur den Elternkonstruktor auf.
     *
     * @param core Der Fensterrahmen.
     */
    public MovingCircle(Frame core) {
        super(core, 800, 600);
    }

    /**
     * Initialisierung.
     */
    @Override
    public void init() {
        x = 100;
        y = 100;
    }

    @Override
    public void done() {
    }

    /**
     * Berechnungen.
     *
     * @param tickCount In welchem Tick sind wir?
     */
    @Override
    public void calc(int tickCount) {
        x++;
        if (x > 500) {
            x = 100;
        }
    }

    /**
     * Spiel zeichnen.
     *
     * @param graphics Der grafische Kontext.
     */
    @Override
    public void draw(Graphics graphics) {
        Color dunkelblau = new Color(0, 0, 64);

        graphics.setColor(dunkelblau);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        graphics.setColor(Color.magenta);
        graphics.drawOval(x, y, 20, 20);
    }
}
