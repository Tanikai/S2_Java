package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Schlange {

    // Variablen
    private int FKopfX, FKopfY;
    private int FVX, FVY;
    private Color FColor;

    // Implementierung
    public Schlange(int i_x, int i_y, int i_vx, int i_vy, Color i_color) {
        FKopfX = i_x;
        FKopfY = i_y;
        FVX = i_vx;
        FVY = i_vy;
        FColor = i_color;
        System.out.println("VX/VY: " + FVX + "/" + FVY);
    }

    public void init() {

    }

    public void calc() {
        FKopfX += FVX;
        FKopfY += FVY;
    }

    public void draw(Graphics g) {
        g.setColor(FColor);
        g.fillRect(FKopfX*10, FKopfY*10, 10, 10);
    }
}
