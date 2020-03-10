package com.jordanhan.gameobjects;
/**
 *
 * @author Jordan Han
 */
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Sprite
{
    //Attributes
    private int stepCount;
    private int animationSpeed;
    private int currentFrame;
    private int numberOfFrames;
    private boolean isAnimated;
    private ArrayList<BufferedImage> animation;
    private int width;
    private int height;
    
    //Constructor
    public Sprite(int animationSpeed)
    {
        stepCount = 0;
        this.animationSpeed = animationSpeed;
        currentFrame = 0;
        numberOfFrames = 0;
        isAnimated = false;
        animation = new ArrayList();
    }//End Constructor
    
    //Getters & Setters
    public BufferedImage getSprite()
    {
        return isAnimated ? getNextFrame() : animation.get(0);
    }//End Sprite Getter
    
    public int getWidth()
    {
        return width;
    }//End Width Getter
    
    public int getHeight()
    {
        return height;
    }//End Height Getter
    
    //Behaviours
    public void addFrame(BufferedImage frame)
    {
        if(frame != null)
        {
            animation.add(frame);
            numberOfFrames = animation.size();
            
            if(numberOfFrames > 1)
            {
                isAnimated = true;
            }//End if
        }//End if
        
        update();
    }//End addFrame
    
    public void addFrame(String frameID)
    {
        BufferedImage tempImage = null;
        
        try
        {
            tempImage = ImageIO.read(getClass().getResourceAsStream(frameID));
        }//End try
        catch(IOException ex)
        {
            System.err.println("Error adding frame to animation.");
        }//End catch
    }//End addFrame
    
    private void update()
    {
        width = animation.get(0).getWidth();
        height = animation.get(0).getHeight();
    }//End update
    
    private BufferedImage getNextFrame()
    {
        BufferedImage frame = animation.get(currentFrame);
        stepCount++;
        if(stepCount % animationSpeed == 0)
        {
            currentFrame++;
        }//End if
        if(currentFrame == numberOfFrames)
        {
            currentFrame = 0;
        }//End if
        
        return frame;
    }//End getNextFrame
}