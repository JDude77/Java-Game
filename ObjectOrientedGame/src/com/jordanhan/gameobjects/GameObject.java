/**
 * Game Object
 * 
 * Serves as a base class for characters and other game objects
 */
package com.jordanhan.gameobjects;

import com.jordanhan.tilemap.Tile;
import com.jordanhan.tilemap.TileMapManager;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;

/**
 * Superclass for all objects that can take part in the game
 * 
 * @author James
 */
public class GameObject
{    
    //Attributes
    //Object X and Y Coordinates
    protected double x;
    protected double y;
    protected Sprite sprite;
    protected TileMapManager tmm;
    //Collision box width and height
    protected int cWidth;
    protected int cHeight;
    //Collision directions
    //These values are set to true if a collision is detected in any of the directions
    protected boolean cTopLeft;
    protected boolean cBottomRight;
    protected boolean cTopRight;
    protected boolean cBottomLeft;
    //private BufferedImage image1 = null;
    //private BufferedImage image2 = null;
    
    //Constructor
    public GameObject(TileMapManager tmm)
    {
        this.tmm = tmm;
    }//End Constructor

    //Getters & Setters
    //X Getter
    public double getX()
    {
        return x;
    }//End X Getter
    
    //Y Getter
    public double getY()
    {
        return y;        
    }//End Y Getter
    
    //Sprite Getter
    public Sprite getSprite()
    {
        return sprite;
    }//End Sprite Getter
    
    //Sprite Setter
    public void setSprite(Sprite s)
    {
        sprite = s;
        
        cWidth = sprite.getWidth();
        cHeight = sprite.getHeight();
    }//End Sprite Setter
    
    //Behaviours
    //Update the game object
    public void update()
    {
        
    }//End update

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
        
    //Check to see if there is an intersection between two objects
    public boolean intersects(GameObject obj)
    {
        //Get bounds of current object
        Rectangle r1 = getBounds();
        //Get bounds of other object
        Rectangle r2 = obj.getBounds();
        
        return r1.intersects(r2);
    }//End intersects
    
    //Get collision bounds
    public Rectangle getBounds()
    {
        return new Rectangle((int) x, (int) y, cWidth, cHeight);
    }//End getBounds
    
    //Draw the game object to the screen
    public void draw(Graphics2D g)
    {
        g.drawImage(sprite.getSprite(), (int) x, (int) y, null);
    }//End draw
        
    //Checking for a collision between two game objects
    public boolean collidesWith(GameObject g)
    {
        //Value set-up
        boolean collision = false;
        int x1, y1;
        int x2, y2;
        
        // Get the centre of each rectangle - measure the distance between the centrepoints
        //Get the x-coordinate of the first rectangle's centre
        x1 = (int) getBounds().getX() + (int)(getBounds().getWidth()/2);
        //Get the y-coordinate of the first rectangle's centre
        y1 = (int) getBounds().getY() + (int)(getBounds().getHeight()/2);
        //Get the x-coordinate of the second rectangle's centre
        x2 = (int) g.getBounds().getX() + (int) (getBounds().getWidth()/2);
        //Get the y-coordinate of the second rectangle's centre
        y2 = (int) g.getBounds().getY() + (int) (getBounds().getHeight()/2);
        
        //Calculate the distance between the different x-coordinates
        double x1x2 = Math.abs(x2 - x1);
        //Calculate the distance between the different x-coordinates
        double y1y2 = Math.abs(y2 - y1);
        //Distance between the two points in the form of a single distance value
        int distance = (int)Math.hypot(x1x2, y1y2);
        
        //Due to pixel collision being expensive, only check in relatively small range
        if(distance < 80) // TODO Change this value to something sprite related parameterise this value
        {
            collision = pixelCollisionCheck(g, g.getBounds(), getBounds());
        }//End if
        
        System.out.println("Collision: " + collision);
        
        return collision;
    }//End collidesWith
    
    //Getting more pinpoint accurate collisions
    public boolean pixelCollisionCheck(GameObject other, Rectangle r1, Rectangle r2)
    {
        //Setting up corners to check for collisions
        int topCornerX;
        int bottomCornerX;
        int topCornerY;
        int bottomCornerY;
        
        //Checking the Y-coordinates for both top and bottom corners
        topCornerY = (r1.y > r2.y) ? r1.y : r2.y;
        bottomCornerY = ((r1.y + r1.height) > (r2.y + r2.height)) ? r1.y + r1.height : r2.y + r2.height;
        
        //Checking the X-coordinates for both top and bottom corners
        topCornerX = (r1.x > r2.x)? r1.x : r2.x;
        bottomCornerX = ((r1.width + r1.x) > (r2.width + r2.x)) ? r1.x + r1.height: r2.x + r2.height;
        
        //Get the distance between the y-coordinates
        int height = bottomCornerY - topCornerY;
        //Get the distance between the x-coordinates
        int width = bottomCornerX - topCornerX;
        //Set up arrays of pixels for the different objects
        int[] pixels1 = new int[width * height];
        int[] pixels2 = new int[width * height];
        //Pass in the data to the pixel grabber objects..?
        PixelGrabber pg1 = new PixelGrabber(other.getSprite().getSprite(), topCornerX - (int)other.getX(), (int) topCornerY - (int) other.getY(), width, height, pixels1, 0, width);
        PixelGrabber pg2 = new PixelGrabber(getSprite().getSprite(), topCornerX - (int)getX(), (int) topCornerY - (int) getY(), width, height, pixels2, 0, width);
        //Try to do the pixel grabbing
        try
        {
            pg1.grabPixels();
            pg2.grabPixels();            
        }//End try
        catch(InterruptedException ex)
        {
            System.err.println("Error in Pixel Collision Check");
            System.err.println("Cannot Grab Pixels");
            System.err.println(ex.getMessage());
        }//End catch
        /*image1 = new BufferedImage(pg1.getWidth(), pg1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        image1.setRGB(0, 0, pg1.getWidth(), pg1.getHeight(), pixels1, 0, pg1.getWidth());
        image2 = new BufferedImage(pg1.getWidth(), pg1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        image2.setRGB(0, 0, pg2.getWidth(), pg2.getHeight(), pixels2, 0, pg1.getWidth());*/
        
        //Loop through pixels to check for collisions between them
        for(int i = 0; i < pixels1.length; i++)
        {
            int t1 = (pixels1[i] >>> 24) & 0xFF;
            int t2 = (pixels2[i] >>> 24) & 0xFF;
            
            if(t1 > 0 && t2 > 0)
                return true;
        }//End for
        
        return false;
    }//End pixelCollisionsCheck
}
