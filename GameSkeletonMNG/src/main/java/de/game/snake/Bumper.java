package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Bumper {
    int FX, FY;
    Color FColor;
    
    public Bumper(int i_x, int i_y, Color i_Color)
    {
        FX = i_x;
        FY = i_y;
        FColor = i_Color;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(FColor);
        g.fillOval(FX*10, FY*10, 10, 10);
    }

    public int getX() {
        return FX;
    }

    public int getY() {
        return FY;
    }  
    
}
