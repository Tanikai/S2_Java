package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Keks {

    // Variablen
    private int FX, FY;
    private Color FColor;

    // Methoden
    public Keks(int i_x, int i_y, Color i_Color) {
        this.FX = i_x;
        this.FY = i_y;
        FColor = i_Color;
    }
    
    public int getFX() {
        return FX;
    }

    public int getFY() {
        return FY;
    }
    
    public void draw(Graphics g) {
        g.setColor(FColor);
        g.fillRect(FX*10, FY*10, 10, 10);
    }
}
