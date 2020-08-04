/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.game.demos;

import de.game.core.AbstractGame;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;

/**
 *
 * @author Kai
 */
public class MovingRect extends AbstractGame {

    // Variablen
    private int x, y;
    private int vX, vY;
    private int pacWidth, pacHeight;

    public MovingRect(Frame core, int width, int height) {
        super(core, width, height); // Konstruktor der Parent-Klasse
    }

    @Override
    public void init() {
        x = 100;
        y = 100;
        vX = 8;
        vY = 4;
        pacWidth = 100;
        pacHeight = 100;
    }

    @Override
    public void done() {
        // Nichts machen
    }

    @Override
    public void calc(int tickCount) {
        x += vX;
        y += vY;
        System.out.println("X/Y: " + x + "/" + y);

        // Rechts oder links abprallen
        if ((x >= getWidth()-pacWidth) || (x <= 0)) {
            vX *= -1;
            System.out.println("Abprallen");
        }

        // Oben oder unten abprallen
        if ((y <= 0) || (y >= getHeight()-pacHeight)) {
            vY *= -1;
            System.out.println("Abprallen");
        }
    }

    @Override
    public void draw(Graphics graphics) {
        Color farbe_pacman = new Color(255, 238, 0);

        graphics.setColor(farbe_pacman);
        graphics.fillOval(x, y, pacWidth, pacHeight);

        graphics.setColor(Color.black);
        graphics.fillOval(x + 25, y + 25, 10, 10);
        graphics.fillOval(x + 65, y + 25, 10, 10);
        graphics.drawLine(x + 25, y + 70, x + 75, y + 70);
    }
}
