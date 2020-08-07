package de.game.snake;

import java.awt.Color;
import java.awt.Graphics;


public class Warpfeld {
    // Variablen
    private int FPortalX1, FPortalY1;
    private int FPortalX2, FPortalY2;
    private Color FColor = Color.blue;
    
    // Methoden
    public Warpfeld(int i_x1, int i_y1, int i_x2, int i_y2) {
        FPortalX1 = i_x1;
        FPortalY1 = i_y1;
        FPortalX2 = i_x2;
        FPortalY2 = i_y2;
    }
    
    public void init(int i_x1, int i_y1, int i_x2, int i_y2) {
        FPortalX1 = i_x1;
        FPortalY1 = i_y1;
        FPortalX2 = i_x2;
        FPortalY2 = i_y2;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(FColor.brighter());
        g.fillOval(FPortalX1*10, FPortalY1*10, 10, 10);
        g.setColor(FColor.darker());
        g.fillOval(FPortalX2*10, FPortalY2*10, 10, 10);
    }
    
    public boolean istWarpfeld1(int i_x, int i_y)
    {
        return ((i_x == FPortalX1) && (i_y == FPortalY1));
    }
    
    public boolean istWarpfeld2(int i_x, int i_y)
    {
        return ((i_x == FPortalX2) && (i_y == FPortalY2));
    }

    public int getFPortalX1() {
        return FPortalX1;
    }

    public int getFPortalY1() {
        return FPortalY1;
    }

    public int getFPortalX2() {
        return FPortalX2;
    }

    public int getFPortalY2() {
        return FPortalY2;
    }
    
    
}
