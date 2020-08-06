package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Keks {

    // Variablen
    private int FX, FY;
    private final Color C_KEKS  = new Color(241, 196, 15);

    // Methoden
    public Keks(int FX, int FY) {
        this.FX = FX;
        this.FY = FY;
    }

    public int getFX() {
        return FX;
    }

    public int getFY() {
        return FY;
    }

    public void draw(Graphics g) {
        g.setColor(C_KEKS);
        g.fillRect(FX*10, FY*10, 10, 10);
    }
}
