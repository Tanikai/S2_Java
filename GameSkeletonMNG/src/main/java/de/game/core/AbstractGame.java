/*
 * AbstractGame.java
 *
 * Created on 24. März 2007, 14:20
 */
package de.game.core;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Eine abstrakte Vorlage für neue Spiele. Diese muss überschrieben werden.
 *
 * @author caschoff
 * @version 1.0
 */
public abstract class AbstractGame implements Serializable {
    //
    // Variablen, die alle haben sollten.
    //

    /**
     * Der Logger. Siehe log4j.properties.
     */
    private static final Logger logger = LogManager.getLogger(AbstractGame.class);
    /**
     * Die Versionsnummer.
     */
    protected static final long serialVersionUID = 1l;
    //
    // Eigene Variablen.
    //
    /**
     * Das Hauptprogramm, unter dem ich laufe.
     */
    private Frame core;
    /**
     * Screengröße.
     */
    private int width, height;

    /**
     * Der Konstruktor.
     *
     * @param core Das Hauptprogramm.
     * @param width Die Breite des Schirms in Pixeln.
     * @param height Die Höhe des Schirms in Pixeln.
     */
    public AbstractGame(Frame core, int width, int height) {
        this.core = core;
        this.width = width;
        this.height = height;
    }

    /**
     * Das Spiel initialisieren.
     */
    public abstract void init();

    /**
     * Das Spiel wurde beendet.
     */
    public abstract void done();

    /**
     * Berechnungen durchführen.
     *
     * @param tickCount Die Anzahl der Durchläufe.
     */
    public abstract void calc(int tickCount);

    /**
     * Das Spiel neu zeichnen.
     *
     * @param graphics Der grafische Kontext.
     */
    public abstract void draw(Graphics graphics);

    /*
     * Ereignisse. Möchte man Ereignisse im Spiel haben, muss
     * man diese Methoden überschreiben.
     */
    public void processKeyEvent(KeyEvent e) {
    }

    public void processMouseEvent(MouseEvent e, Insets insets) {
    }

    public void processMouseMotionEvent(MouseEvent e, Insets insets) {
    }

    public void processWindowEvent(WindowEvent e) {
    }

    public void processComponentEvent(ComponentEvent e) {
    }

    /*
     * Getter/Setter
     */
    public int getX() {
        return core.getX();
    }

    public int getY() {
        return core.getY();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public Frame getCore() {
        return core;
    }
    
}
