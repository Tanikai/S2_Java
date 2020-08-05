package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class SchlangenWirbel {

    // Variablen
    private int FX, FY;
    private Color FColor;

    // Methoden
    public SchlangenWirbel(int FX, int FY, Color FColor) {
        this.FX = FX;
        this.FY = FY;
        this.FColor = FColor;
    }

    public void draw(Graphics g) {
        g.setColor(FColor);
        g.fillRect(FX * 10, FY * 10, 10, 10);
    }

    public int getFX() {
        return FX;
    }

    public int getFY() {
        return FY;
    }

    public void setFX(int FX) {
        this.FX = FX;
    }

    public void setFY(int FY) {
        this.FY = FY;
    }

}
