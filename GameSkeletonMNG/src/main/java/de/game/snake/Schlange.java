package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Schlange {

    // Variablen
    private int FKopfX, FKopfY;
    private Color FColor;

    // Implementierung
    public Schlange(int i_x, int i_y, char i_dir, Color i_color) {
        FKopfX = i_x;
        FKopfY = i_y;
        FColor = i_color;
    }

    public void init() {

    }

    public void calc() {
    }

    public void draw(Graphics g) {
        g.setColor(FColor);
        g.fillRect(FKopfX*10, FKopfX*10, 10, 10);
    }
}
