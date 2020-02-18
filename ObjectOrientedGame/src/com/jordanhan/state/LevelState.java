package com.jordanhan.state;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Jordan Han
 */
public abstract class LevelState
{
    //Attributes
    protected LevelManager lm;
    
    //Constructor
    public LevelState(LevelManager lm)
    {
        this.lm = lm;
    }//End constructor
    
    //Behaviours
    public abstract void keyPressed(int keyCode);
    public abstract void keyReleased(int keyCode);
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void mouseClicked(Point mouseClick);
}