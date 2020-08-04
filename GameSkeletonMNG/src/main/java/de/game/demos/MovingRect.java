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
        
    public MovingRect(Frame core, int width, int height){
        super(core, width, height); // Konstruktor der Parent-Klasse
    }
        
    @Override
    public void init() {
        x = 100;
        y = 100;
    }

    @Override
    public void done() {
        // Nichts machen
    }

    @Override
    public void calc(int tickCount) {
        x += 1;
        
        if (x > 500)
        {
            x = 100;
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.pink);
        graphics.drawRect(x, y, 100, 100);
    }
}
