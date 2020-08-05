package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Schlange {

    // Variablen
    private int FKopfX, FKopfY;
    private int FVX, FVY;
    private int FWidth, FHeight;
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

    public void init(int i_FeldWidth, int i_FeldHeight) {
        FWidth = i_FeldWidth / 10;
        FHeight = i_FeldHeight / 10;
    }

    public void calc(int tickCount) {
        // Geschwindigkeit dynamisch anhand der Länge setzen        
        if (0 == tickCount % 5) {
            FKopfX += FVX;
            FKopfY += FVY;
        }

        checkRand();
    }

    public void draw(Graphics g) {
        g.setColor(FColor);
        g.fillRect(FKopfX * 10, FKopfY * 10, 10, 10);
    }

    public void neueRichtung(int i_vX, int i_vY) {
        FVX = i_vX;
        FVY = i_vY;
    }

    public void checkRand() {
        if (FKopfX >= FWidth) {
            FKopfX = 0;
        } else if (FKopfX < 0) {
            FKopfX = FWidth - 1;
        }

        if (FKopfY >= FHeight) {
            FKopfY = 0;
        } else if (FKopfY < 0) {
            FKopfY = FHeight - 1;
        }

    }
}
