package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Schlange {

    // Variablen
    private int FKopfX, FKopfY;
    private int FVX, FVY;
    private Color FColor;
    private ArrayList<SchlangenWirbel> FKoerper;
    private ArrayList<int[]> FCommandQueue;

    // Methoden
    public Schlange(int i_x, int i_y, int i_vx, int i_vy, Color i_color) {
        FKopfX = i_x;
        FKopfY = i_y;
        FVX = i_vx;
        FVY = i_vy;
        FColor = i_color;
        FKoerper = new ArrayList<>();
        FCommandQueue = new ArrayList<>();
    }

    public void init(int i_x, int i_y, int i_vx, int i_vy) {
        FKopfX = i_x;
        FKopfY = i_y;
        FVX = i_vx;
        FVY = i_vy;
        FKoerper.clear();
    }

    public boolean calc(int tickCount) {
        // ToDo: Geschwindigkeit dynamisch anhand der Länge setzen        
        if (0 == tickCount % 5) {
            if (!FCommandQueue.isEmpty()) {
                int[] command = FCommandQueue.remove(0);
                neueRichtung(command[0], command[1]);
            }

            // Körper der Schlange nachziehen
            if (!FKoerper.isEmpty()) {
                int LLastIndex = FKoerper.size() - 1;

                FKoerper.get(LLastIndex).setFX(FKopfX);
                FKoerper.get(LLastIndex).setFY(FKopfY);
                // Wirbel an den Anfang setzen
                SchlangenWirbel LLastElement = FKoerper.remove(LLastIndex);
                FKoerper.add(0, LLastElement);
            }

            // Kopf bewegen
            FKopfX += FVX;
            FKopfY += FVY;

            // Kollision mit Portal prüfen
            istPortal();

            return true;
        } else {
            return false;
        }
    }

    public void draw(Graphics g) {
        // Schlangenkörper malen
        for (SchlangenWirbel sw : FKoerper) {
            sw.draw(g);
        }

        // Schlangenkopf malen
        g.setColor(FColor);
        g.fillRect(FKopfX * 10, FKopfY * 10, 10, 10);
    }

    public void queueCommand(int i_vX, int i_vY) {
        if (FCommandQueue.size() < 6) {
            int[] c = new int[2];
            c[0] = i_vX;
            c[1] = i_vY;
            FCommandQueue.add(c);
        }
    }

    public void neueRichtung(int i_vX, int i_vY) {

        if (!((i_vX == -FVX) || (i_vY == -FVY))) {
            FVX = i_vX;
            FVY = i_vY;
        }

    }

    public void istPortal() {
        if (FKopfX >= Spielfeld.WIDTH) {
            FKopfX = 0;
        } else if (FKopfX < 0) {
            FKopfX = Spielfeld.WIDTH - 1;
        }

        if (FKopfY >= Spielfeld.HEIGHT) {
            FKopfY = 0;
        } else if (FKopfY < 0) {
            FKopfY = Spielfeld.HEIGHT - 1;
        }
    }

    public boolean istSchlange(int x, int y) {
        return ((FKopfX == x) && (FKopfY == y)) || istKoerper(x, y);
    }

    public boolean istKoerper(int x, int y) {
        for (SchlangenWirbel wirbel : FKoerper) {
            if ((wirbel.getFX() == x) && (wirbel.getFY() == y)) {
                return true;
            }
        }
        return false;
    }

    public void wachsen(int i_Anzahl) {
        for (int i = 0; i < i_Anzahl; i++) {
            FKoerper.add(new SchlangenWirbel(FKopfX, FKopfY, FColor.darker()));
        }
    }

    public void clearWirbel() {
        FKoerper.clear();
    }

    public int getKopfX() {
        return FKopfX;
    }

    public int getKopfY() {
        return FKopfY;
    }
}
