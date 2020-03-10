package com.jordanhan.sprite;

import com.jordanhan.gameobjects.Sprite;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author James
 */
public class SpriteLoader
{
    //Attributes
    private String spritePath = "/images";
    
    //Constructor
    public SpriteLoader(String spritePath)
    {
        this.spritePath = spritePath;
    }//End constructor
    
    //Behaviours
    //Load a sprite sheet in as a sprite
    public Sprite loadSpriteSheet(String uri, int tileSize)
    {
        Sprite s = new Sprite(10);        
        return s;
    }//End loadSpriteSheet

    //Load in a sequence of images by file name
    public Sprite loadFileSequence(String fileName, int numberOfFrames, String type)
    {
        //???
        Sprite s = new Sprite(7);
        //Buffered image says bi rights
        BufferedImage bi;
        //No need to swear
        String tempFName;
  
        //Try to read in the individual frames from the file path given
        try
        {
            for(int i = 1; i <= numberOfFrames; i++)
            {
                tempFName = spritePath + "/" +fileName + "_" + i + "." +type;
                bi = ImageIO.read(getClass().getResourceAsStream(tempFName));
                s.addFrame(bi);
            }//End for
        }catch(IOException ex)
        {
            System.err.println("loadFileSequence");
            System.err.println("Error loading sprite image");
            ex.getMessage();
        }
        
        return s;
    }//End loadFileSequence
}
