package de.game.snake;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Keksdose {

    // Variablen
    private ArrayList<Keks> FKekse;
    private int FMaxKekse;

    // Methoden
    public Keksdose(int i_max) {
        FMaxKekse = i_max;
        FKekse = new ArrayList<>();
    }

    public void init() {
        FKekse.clear();
        for (int i = 0; i < FMaxKekse; i++) {
            newKeks();
        }
    }

    public void newKeks() {
        int LX = ThreadLocalRandom.current().nextInt(1, Spielfeld.WIDTH);
        int LY = ThreadLocalRandom.current().nextInt(2, Spielfeld.HEIGHT);
        FKekse.add(new Keks(LX, LY));
    }

    public void calc() {
        if (FKekse.size() < FMaxKekse) {
            newKeks();
        }
    }

    public void draw(Graphics g) {
        for (Keks kek : FKekse) {
            kek.draw(g);
        }
    }

    public boolean istKeks(int i_x, int i_y) {
        for (Keks kek : FKekse) {
            if ((kek.getFX() == i_x) && (kek.getFY() == i_y)) {
                FKekse.remove(kek);
                newKeks();
                return true;
            }
        }
        return false;
    }
}
