package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class BumperCollection {

    //Variablen
    ArrayList<Bumper> BumperListe;

    public BumperCollection() {
        BumperListe = new ArrayList<>();
    }

    public void init() {
        BumperListe.clear();
    }

    public void addBumper(int i_x, int i_y, Color i_Color) {
        BumperListe.add(new Bumper(i_x, i_y, i_Color));
    }

    public void draw(Graphics g) {
        for (Bumper b : BumperListe) {
            b.draw(g);
        }
    }

    //Methoden
    public boolean istBumper(int i_x, int i_y) {
        for (Bumper b : BumperListe) {
            if ((b.getX() == i_x) && (b.getY() == i_y)) {
                return true;
            }
        }
        return false;
    }
}
