/*
 * Main.java
 *
 * Created on 24. März 2007, 14:20
 */
package de.game.core;

// Spiele
import de.game.demos.MovingCircle;
import de.game.demos.MovingRect;
import de.game.snake.SnakeGame;

// Sonstiges
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die ausführbare Komponente der Spiels.
 *
 * @author caschoff
 * @version 1.0
 */
public class Main extends Frame implements Runnable {

    //
    // Variablen, die alle haben sollten.
    //
    /**
     * Der Logger. Siehe log4j.properties.
     */
    private static final Logger logger = LogManager.getLogger(Main.class);
    /**
     * Die Versionsnummer.
     */
    protected static final long serialVersionUID = 1l;
    //
    // Eigene Variablen.
    //
    /**
     * Thread soll laufen, hört auf, wenn <code>running</code> auf
     * <code>false</code> gesetzt wird.
     */
    private boolean running = true;
    /**
     * Spiel pausieren?
     */
    private boolean pause = false;
    /**
     * Antialiasig gwünscht?
     */
    private boolean antialiasing = true;
    /**
     * Anzahl der Ticks.
     */
    private int tickCount = 0;
    /**
     * Die Zeit, der der Thread pro Durchlauf benötigen soll (in Millisekunden).
     */
    private long sleepTime = 1000 / 24; // 24 fps
    /**
     * Das Bild für den Double-Buffer.
     */
    private Image dbImage = null;
    /**
     * Der grafische Kontext für den Double-Buffer.
     */
    private Graphics dbGraphics = null;
    //
    // Das Spiel
    //
    private AbstractGame game;

    /**
     * Ein neues Hauptprogramm erzeugen.
     */
    public Main() {
        super("Game");

        //
        // Events einschalten
        //
        // wir wollen Tastatur-Events...
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        // wir wollen Maus-Events...
        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        // wir wollen Events über das Fenster (z. B. Close)...
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        // wir wollen Events über Größenänderungen...
        enableEvents(AWTEvent.COMPONENT_EVENT_MASK);

        //
        // Ein paar Systeminfos ausgeben
        //
        logger.debug("*********************");
        logger.debug("Systeminfos folgen...");
        logger.debug("*********************");
        Properties props = System.getProperties();
        Enumeration e = props.propertyNames();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            logger.info(name + " = " + props.getProperty(name));
        }

        //
        // Ein paar Environment-Infos ausgeben
        //
        logger.debug("***************************");
        logger.debug("Environment-Infos folgen...");
        logger.debug("***************************");
        Map<String, String> env = System.getenv();
        Iterator<String> i = env.keySet().iterator();
        while (i.hasNext()) {
            String key = i.next();
            logger.debug(key + " = " + env.get(key));
        }
    }

    /**
     * Initialisierung des Spiels. Hier schreibt man das Spiel rein, das
     * gestartet werden soll. Im Demo <br />
     * <code>
     * game = new MovingCircle(this);
     * </code>
     */
    private void init() {
        // hier kommt rein, was gestartet wird
//        game = new MovingCircle(this);
//        game = new MovingRect(this, 800, 600);
        game = new SnakeGame(this);
        // die folgende Zeile bleibt immer...
        game.init();
    }

    /**
     * Die Einsprungroutine für das Hauptprogramm.
     *
     * @param args Die Kommandozeilenargumente.
     */
    public static void main(String args[]) {
        Main main = new Main();

        // initialisieren...
        main.init();
        // die Grafik initialisieren...
        main.initGrafik();
        // und los!
        main.start();
    }

    /**
     * Initialisierung der Grafik.
     */
    private void initGrafik() {
        setVisible(true);
        // Grafik vorbereiten
        setResizable(true);
        // setUndecorated(true); // Falls kein Rahmen gewünscht wird.
        setLayout(null);
        // Falls maximiert gestartet werde soll.
        // setExtendedState(MAXIMIZED_BOTH); 
        // ... oder...
        setSize(new Dimension(game.getWidth() + getInsets().left + getInsets().right, game.getHeight() + getInsets().top + getInsets().bottom));
        logger.debug("Grafik initialisiert (getInsets().left=" + getInsets().left + "; getInsets().right=" + getInsets().right + "; getInsets().top=" + getInsets().top + "; getInsets().bottom=" + getInsets().bottom + ").");
    }

    /**
     * Starten des Threads.
     */
    private void start() {
        Thread thread = new Thread(this);
        thread.start();
        logger.debug("Thread gestartet.");
    }

    /**
     * Der eigentliche Thread.
     */
    public void run() {
        while (running) {
            long start = System.currentTimeMillis();
            try {
                if (!pause) {
                    tickCount++;
                    game.calc(tickCount);
                }
                // neu malen...
                repaint();
                // schlafen bis die sleepTime erreicht ist...
                long sleep = sleepTime - (System.currentTimeMillis() - start);
                if (sleep > 0) {
                    Thread.sleep(sleep);
                } else {
                    logger.debug("Schlafstörung: sleep=" + sleep);
                }
            } catch (Exception ex) {
                logger.error("ERROR!", ex);
                running = false;
            }
        }

        logger.debug("Thread wurde beendet, beende Applikation...");
        game.done();
        System.exit(0);
    }

    /**
     * Realisierung der Doppelpufferung zur Reduzierung des Bildschirmflackerns.
     *
     * @param graphics Der grafische Kontext.
     */
    @Override
    public void update(Graphics graphics) {
        if (dbImage == null) {
            // Initialisierung des DoubleBuffers...
            dbImage = createImage(getWidth(), getHeight());
            dbGraphics = dbImage.getGraphics();
            logger.debug("Buffer mit " + getWidth() + "x" + getHeight() + " für DoubleBuffering erzeugt.");
        }

        // in Puffer malen
        paint(dbGraphics);
        // Nun fertig gezeichnetes Offscreen-Bild auf dem richtigen Bildschirm anzeigen
        graphics.drawImage(dbImage, getInsets().left, getInsets().top, this);
    }

    /**
     * Diese Methode wird zyklisch vom Tread (indirekt) aufgerufen.
     *
     * @param g Der grafische Kontext.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        //
        // Antialiasing einschalten?
        //
        if (antialiasing) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }

        //
        // Hintergrund löschen...
        //
        graphics.setColor(new Color(132, 132, 132));
        graphics.fillRect(0, 0, getWidth(), getHeight());

        //
        // Infostring aufbauen...
        //
        StringBuffer infoString = new StringBuffer();
        infoString.append("@" + tickCount + " Ticks. ");
        infoString.append("[q] zum Beenden. ");
        if (pause) {
            infoString.append("Pause. [p] zum Weitermachen.");
        } else {
            infoString.append("[p] für Pause.");
        }

        // und malen...
        Color infoColor = new Color(64, 64, 64, 128);
        graphics.setColor(infoColor);
        graphics.drawString(infoString.toString(), 10, getHeight() - 5);

        game.draw(graphics);
    }

    /**
     * Die tatsächliche Position der Zeichenfläche bekommen.
     *
     * @return Die tatsächliche Position der Zeichenfläche.
     */
    @Override
    public int getX() {
        return getInsets().left;
    }

    /**
     * Die tatsächliche Position der Zeichenfläche bekommen.
     *
     * @return Die tatsächliche Position der Zeichenfläche.
     */
    @Override
    public int getY() {
        return getInsets().top;
    }

    /**
     * Die tatsächliche Breite der Zeichenfläche bekommen.
     *
     * @return Die tatsächliche Breite.
     */
    @Override
    public int getWidth() {
        return super.getWidth() - getExtraWidth();
    }

    /**
     * Die Breite des Verlustes rechts und links durch den Rahmen.
     *
     * @return Die Verlustbreite.
     */
    public int getExtraWidth() {
        return getInsets().left + getInsets().right;
    }

    /**
     * Die tatsächliche Höhe der Zeichenfläche bekommen.
     *
     * @return Die tatsächliche Höhe.
     */
    @Override
    public int getHeight() {
        return super.getHeight() - getExtraHeight();
    }

    /**
     * Die Höhe des Verlustes oben und unten durch den Rahmen.
     *
     * @return Die Verlusthöhe.
     */
    public int getExtraHeight() {
        return getInsets().top + getInsets().bottom;
    }

    /*
     * Eventverarbeitung. Events werden an das Spiel weitergereicht.
     */
    /**
     * Eine Taste wurde gedrückt.
     *
     * @param e Der Event.
     */
    @Override
    protected void processKeyEvent(KeyEvent e) {
        // nur als Demo-Code...
        if (e.getID() == KeyEvent.KEY_PRESSED) {
        }

        // Tasten, die das Hauptprogramm braucht...
        if (e.getID() == KeyEvent.KEY_RELEASED) {
            switch (e.getKeyCode()) {
                case 65:
                    // a-Teste = antialiasing
                    // antialiasing = !antialiasing;
                    break;
                case 80:
                    // p-Taste = Pause...
                    pause = !pause;
                    break;
                case 81:
                    // q-Taste = Beenden...
                    running = false;
                    break;
            }
        }

        // Tasten an das Spiel weiterleiten...
        if (game != null) {
            game.processKeyEvent(e);
        }

        // und unser Elternobjekt nicht vergessen...
        super.processKeyEvent(e);
    }

    /**
     * Eine Maustaste wurde gedrückt.
     *
     * @param e Der Event.
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
        }

        if (e.getID() == MouseEvent.MOUSE_RELEASED) {
        }

        if (game != null) {
            game.processMouseEvent(e, getInsets());
        }

        super.processMouseEvent(e);
    }

    /**
     * Die Maus wurde bewegt.
     *
     * @param e Der Event.
     */
    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_MOVED) {
        }

        if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
        }

        if (game != null) {
            game.processMouseMotionEvent(e, getInsets());
        }

        super.processMouseMotionEvent(e);
    }

    /**
     * Einen Fenster-Event bearbeiten.
     *
     * @param e Der Event.
     */
    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            running = false;
        }

        if (game != null) {
            game.processWindowEvent(e);
        }

        super.processWindowEvent(e);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        if (e.getID() == ComponentEvent.COMPONENT_RESIZED) {
            dbImage = null;
        }

        if (game != null) {
            game.processComponentEvent(e);
        }

        super.processComponentEvent(e);
    }

}
