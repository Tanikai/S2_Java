package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Keks {

    // Variablen
    private int FX, FY;
    private int FZucker;
    private Color FColor;

    // Methoden
    public Keks(int i_x, int i_y, Color i_Color, int i_Zucker) {
        this.FX = i_x;
        this.FY = i_y;
        FColor = i_Color;
        FZucker = i_Zucker;
    }

    public int getFX() {
        return FX;
    }

    public int getFY() {
        return FY;
    }
    
    public int getZucker() {
        return FZucker;
    }

    public void draw(Graphics g) {
        g.setColor(FColor);
        g.fillRect(FX * 10, FY * 10, 10, 10);
    }
}
