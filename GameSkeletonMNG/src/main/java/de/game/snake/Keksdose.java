package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Keksdose {

    // Variablen
    private ArrayList<Keks> FKekse;

    // Methoden
    public Keksdose() {
        FKekse = new ArrayList<>();
    }

    public void init() {
        FKekse.clear();
    }

    public void addKeks(Keks i_Keks) {
        FKekse.add(i_Keks);
    }

    public void entferneKeks(Keks i_k) {
        FKekse.remove(i_k);
    }

    public Keks getKeksAtPosition(int x, int y) {
        for (Keks kek : FKekse) {
            if ((kek.getFX() == x) && (kek.getFY() == y)) {
                return kek;
            }
        }
        return null;
    }

    public void draw(Graphics g) {
        for (Keks kek : FKekse) {
            kek.draw(g);
        }
    }

}
