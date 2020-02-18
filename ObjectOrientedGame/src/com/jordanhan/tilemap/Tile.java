package com.jordanhan.tilemap;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jordan Han
 */
public class Tile
{
    //Attributes
    private int type;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_BLOCKED = 1;
    public static final int TYPE_SLOW = 3;
    private BufferedImage tileImage;
    
    //Default Constructor
    public Tile()
    {
        type = Tile.TYPE_NORMAL;
        tileImage = null;
    }//End default constructor
    
    //Constructor With Parameters
    public Tile(BufferedImage image, int type)
    {
        this.tileImage = image;
        this.type = type;
    }//End constructor with parameters
    
    //Getters & Setters
    //Type Setter
    public void setType(int type)
    {
        this.type = type;
    }//End type setter
    
    //Type Getter
    public int getType()
    {
        return this.type;
    }//End type getter
    
    //Tile Image Setter
    public void setImage(BufferedImage tileImage)
    {
        this.tileImage = tileImage;
    }//End tile image setter
    
    //Tile Image Getter
    public BufferedImage getImage()
    {
        return this.tileImage;
    }//End tile image getter
    
    //Behaviours
}