package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Spielfeld {

    // Variablen
    private boolean[][] FWalls;
    public static int WIDTH = 80;
    public static int HEIGHT = 60;

    // Implementierung    
    public Spielfeld() {
        FWalls = new boolean[WIDTH][HEIGHT];
    }

    public void init() {
        int i;
        for (i = 0; i < WIDTH; i++) {
            FWalls[i][0] = true; // Wand oben
            FWalls[i][HEIGHT - 1] = true; // Wand unten
        }

        for (i = 0; i < HEIGHT; i++) {
            FWalls[0][i] = true; // Wand links
            FWalls[WIDTH - 1][i] = true; // Wand rechts
        }

        // Lücken erstellen
        int LTenth = WIDTH / 10;
        for (i = LTenth * 4; i < WIDTH - 4 * LTenth; i++) {
            FWalls[i][0] = false; // oben
            FWalls[i][HEIGHT - 1] = false; // unten
        }

        LTenth = HEIGHT / 10;
        for (i = LTenth * 4; i < HEIGHT - 4 * LTenth; i++) {
            FWalls[0][i] = false; // links
            FWalls[WIDTH - 1][i] = false; // rechts
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < WIDTH; i++) {
            for (int k = 0; k < HEIGHT; k++) {
                if (FWalls[i][k]) {
                    g.setColor(SnakeGame.C_LIGHT);
                    g.fillRect(i * 10, k * 10, 10, 10);
                } else {
                    g.setColor(SnakeGame.C_DARK);
                    g.fillRect(i * 10, k * 10, 10, 10);
                }
            }
        }
    }

    public boolean istWand(int i_x, int i_y) {
        return (FWalls[i_x][i_y]);
    }
}
