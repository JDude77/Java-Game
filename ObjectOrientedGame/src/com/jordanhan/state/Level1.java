package com.jordanhan.state;

import com.jordanhan.gameobjects.Collectable;
import com.jordanhan.gameobjects.Enemy;
import com.jordanhan.gameobjects.Player;
import com.jordanhan.gameobjects.Sprite;
import com.jordanhan.main.LevelPanel;
import com.jordanhan.sprite.SpriteLoader;
import com.jordanhan.tilemap.TileMapManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 *
 * @author Jordan Han
 */
public class Level1 extends LevelState
{
    //Attributes
    private Player p;
    private boolean win;
    private Enemy[] enemies;
    private Collectable[] collectables;
    private TileMapManager tmm;
    private SpriteLoader sp;
    
    //Constructor
    public Level1(LevelManager lm)
    {
        super(lm);
        tmm = new TileMapManager();
        p = new Player(tmm);
        win = false;
        initPlayer();
        init();
        initEnemies();
        initCollectables();
    }//End constructor
    
    //Behaviours
    //Initialise the level
    private void init()
    {
        
    }//End init
    
    //Initialise the enemies
    private void initEnemies()
    {
        enemies = new Enemy[5];
        
        for (int i = 0; i < enemies.length; i++)
        {
            enemies[i] = new Enemy(tmm);
        }//End for
    }//End initEnemies
    
    //Initialise the collectables
    private void initCollectables()
    {
        collectables = new Collectable[5];
        
        for (int i = 0; i < collectables.length; i++)
        {
            collectables[i] = new Collectable(tmm);
        }//End for
    }//End initCollectables
    
    //Initialise the player
    private void initPlayer()
    {
        Sprite s;
        SpriteLoader sm = new SpriteLoader("/images/level1/character");
        s = sm.loadFileSequence("right", 4, "png");
        p.setAnimations("RIGHT", s);
        s = sm.loadFileSequence("left", 4, "png");
        p.setAnimations("LEFT", s);
        s = sm.loadFileSequence("idle", 3, "png");
        p.setAnimations("STATIC", s);
        s = sm.loadFileSequence("jump", 3, "png");
        p.setAnimations("JUMP", s);
        s = sm.loadFileSequence("fall", 3, "png");
        p.setAnimations("FALL", s);
    }//End initPlayer
    
    //Get key presses from the user
    @Override
    public void keyPressed(int keyCode)
    {
        switch(keyCode)
        {
            case KeyEvent.VK_A: case KeyEvent.VK_LEFT:
                p.moveLeft(true);
                break;
            case KeyEvent.VK_D: case KeyEvent.VK_RIGHT:
                p.moveRight(true);
                break;
            case KeyEvent.VK_W: case KeyEvent.VK_UP:
                p.jump(true);
                break;
        }//End switch
    }//End keyPressed
    
    //Get key releases from the user
    @Override
    public void keyReleased(int keyCode)
    {
        switch(keyCode)
        {
            case KeyEvent.VK_A: case KeyEvent.VK_LEFT:
                p.moveLeft(false);
                break;
            case KeyEvent.VK_D: case KeyEvent.VK_RIGHT:
                p.moveRight(false);
                break;
            case KeyEvent.VK_W: case KeyEvent.VK_UP:
                p.jump(false);
                break;
        }//End switch
    }//End keyReleased
    
    //Update the level
    @Override
    public void update()
    {
        //Update player
        p.update();
        //Check for collisions against enemies
        p.checkEnemyCollision(enemies);
        //Check for collisions against collectables
        p.checkCollectableCollision(collectables);
        
        tmm.setCameraPosition((int) LevelPanel.PANEL_WIDTH / 2 - p.getX(), (int) LevelPanel.PANEL_WIDTH / 2 - p.getY());
    }//End update
    
    //Update the level visuals
    @Override
    public void draw(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, LevelPanel.PANEL_WIDTH, LevelPanel.PANEL_HEIGHT);
        tmm.draw(g);
        p.draw(g);
    }//End draw

    @Override
    public void mouseClicked(Point mouseClick)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}