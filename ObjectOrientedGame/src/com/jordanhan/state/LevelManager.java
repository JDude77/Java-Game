package com.jordanhan.state;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Jordan Han
 */
public class LevelManager
{
    //Attributes
    private LevelState[] gameStateList;
    private int currentState;
    public static final int MENU_SCREEN = 0;
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int VICTORY_SCREEN = 3;
    public static final int DEATH_SCREEN = 4;
    
    //Constructor
    public LevelManager()
    {
        //Set the start of the game to be level 1 for now
        currentState = 1;
        //Create the list of levels
        gameStateList = new LevelState[5];
        
        //Add first couple levels to the list
        //gameStateList[0] = new Menu(this);
        gameStateList[1] = new Level1(this);
    }//End Constructor
    
    //Getters & Setters
    //currentState setter
    public void setCurrentState(int state)
    {
        this.currentState = state;
    }//End currentState setter
    //currentState getter
    public int getCurrentState()
    {
        return this.currentState;
    }//End currentState getter
    
    //Behaviours
    //Update the current level
    public void update()
    {
        gameStateList[currentState].update();
    }//End update
    
    //Go to a specified level
    public void goToState(int state)
    {
        this.currentState = state;
    }//End goToState
    
    //Go to the next level
    public void nextState()
    {
        this.currentState++;
    }//End nextState
    
    //Go to previous level
    public void previousState()
    {
        this.currentState--;
    }//End previousState
    
    //Update the screen's graphics
    public void updateScreenBuffer(Graphics2D g)
    {
        gameStateList[currentState].draw(g);
    }//End updateScreenBuffer
    
    //Register key presses from the user
    public void keyPressed(int keyCode)
    {
        gameStateList[currentState].keyPressed(keyCode);
    }//End keyPressed
    
    //Register key releases from the user
    public void keyReleased(int keyCode)
    {
        gameStateList[currentState].keyReleased(keyCode);
    }//End keyPressed

    public void clicked(Point p)
    {
        gameStateList[currentState].mouseClicked(p);
    }//End clicked
}