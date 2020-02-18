package com.jordanhan.gameobjects;

import com.jordanhan.tilemap.Tile;
import com.jordanhan.tilemap.TileMapManager;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Jordan Han
 */
public class GameObject
{
    //Attributes
    protected double x;
    protected double y;
    protected BufferedImage sprite;
    protected int cWidth;
    protected int cHeight;
    protected boolean cTopLeft;
    protected boolean cBottomRight;
    protected boolean cTopRight;
    protected boolean cBottomLeft;
    protected TileMapManager tmm;
    
    //Constructor
    public GameObject(String fileName, TileMapManager tmm)
    {
        this.tmm = tmm;
        
        loadSprite(fileName);
    }//End constructor
    
    //Behaviours
    private void loadSprite(String fileName)
    {
        try
        {
            sprite = ImageIO.read(getClass().getResourceAsStream(fileName));
        }//End try
        catch(IOException ex)
        {
            System.err.println("Error: Unable to load Game Object Sprite.\n"+ex);
        }//End catch
        
        cWidth = sprite.getWidth();
        cHeight = sprite.getHeight();
    }//End loadSprite
    
    //Update the game object
    public void update()
    {
        
    }//End update
    
    //Get collision bounds
    public Rectangle getBounds()
    {
        return new Rectangle((int) x, (int) y, cWidth, cHeight);
    }//End getBounds
    
    //Draw the game object to the screen
    public void draw(Graphics2D g)
    {
        g.drawImage(sprite, (int) x, (int) y, null);
    }//End draw
    
    //Check to see if there is an intersection between two objects
    public boolean intersects(GameObject obj)
    {
        //Get bounds of current object
        Rectangle r1 = getBounds();
        //Get bounds of other object
        Rectangle r2 = obj.getBounds();
        
        return r1.intersects(r2);
    }//End intersects
    
    //Check to see if there is an collision with the tile map
    public void checkTileMapCollision(double x, double y)
    {
        double currXPos;
        double currYPos;
        Tile tile;
        
        //Top Left collision check
        currXPos = x;
        currYPos = y;
        tile = tmm.getTileAt(currXPos, currYPos);
        cTopLeft = tile.getType() == Tile.TYPE_BLOCKED;
        
        //Top Right collision check
        currXPos = x + cWidth;
        currYPos = y;
        tile = tmm.getTileAt(currXPos, currYPos);
        cTopRight = tile.getType() == Tile.TYPE_BLOCKED;
        
        //Bottom Right collision check
        currXPos = x + cWidth;
        currYPos = y + cHeight;
        tile = tmm.getTileAt(currXPos, currYPos);
        cBottomRight = tile.getType() == Tile.TYPE_BLOCKED;
        
        //Bottom Left collision check
        currXPos = x;
        currYPos = y + cHeight;
        tile = tmm.getTileAt(currXPos, currYPos);
        cBottomLeft = tile.getType() == Tile.TYPE_BLOCKED;
    }//End checkTileMapCollision
}