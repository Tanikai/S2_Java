package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Spielfeld {

    // Variablen
    private boolean[][] FWalls;
    private int FWidth, FHeight;
    private Color cWall;
    private Color cField;

    // Implementierung    
    public Spielfeld(int i_width, int i_height) {
        FWidth = i_width / 10;
        FHeight = i_height / 10;
        FWalls = new boolean[FWidth][FHeight];
        cWall = new Color(248, 244, 227);
        cField = new Color(26, 20, 35);
    }

    public void init() {
        int i;
        for (i = 0; i < FWidth; i++) {
            FWalls[i][0] = true; // Wand oben
            FWalls[i][FHeight - 1] = true; // Wand unten
        }

        for (i = 0; i < FHeight; i++) {
            FWalls[0][i] = true; // Wand links
            FWalls[FWidth - 1][i] = true; // Wand rechts
        }

        // Lücken erstellen
        int LTenth = FWidth / 10;
        for (i = LTenth * 4; i < FWidth - 4 * LTenth; i++) {
            FWalls[i][0] = false; // oben
            FWalls[i][FHeight - 1] = false; // unten
        }

        LTenth = FHeight / 10;
        for (i = LTenth * 3; i < FHeight - 3 * LTenth; i++) {
            FWalls[0][i] = false; // links
            FWalls[FWidth - 1][i] = false; // rechts
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < FWidth; i++) {
            for (int k = 0; k < FHeight; k++) {
                if (FWalls[i][k]) {
                    g.setColor(cWall);
                    g.fillRect(i * 10, k * 10, 10, 10);
                } else {
                    g.setColor(cField);
                    g.fillRect(i * 10, k * 10, 10, 10);
                }
            }
        }

    }
}
