package com.jordanhan.main;

import javax.swing.JFrame;

/**
 *
 * @author Jordan Han
 */
public class Game
{
    //Attributes
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    private static final String TITLE = "New & Improved Game";
    private JFrame mainWindow;
    private LevelPanel lp;
    
    //Constructor
    public Game()
    {
        initGame();
        initComponents();
        initWindow();
        
        lp.startGame();
    }//End Constructor
    
    //Behaviours
    //Initialise the game-related objects
    private void initGame()
    {
        lp = new LevelPanel();
        mainWindow = new JFrame();
    }//End initGame
    
    //Initialise GUI components
    private void initComponents()
    {
        mainWindow.add(lp);
    }//End initComponents
    
    //Initialise the game window
    public void initWindow()
    {
        mainWindow.setTitle(Game.TITLE);
        mainWindow.setSize(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }//End initWindow
    
    //Main method
    public static void main(String[] args)
    {
        Game g = new Game();
    }//End main
}