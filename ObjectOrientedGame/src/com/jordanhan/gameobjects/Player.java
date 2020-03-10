package com.jordanhan.gameobjects;

import com.jordanhan.tilemap.TileMapManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

/**
 *
 * @author Jordan Han
 */
public class Player extends GameObject
{
    //Attributes
    private int health;
    private double xSpeed = 0.2;
    private double xSpeedMax = 2.5;
    private double gravity = 9.81 / 100;
    private double maxGravity = 8.0;
    private double jumpStrength = 6.0;
    private double dx;
    private double dy;
    private boolean FALLING;
    private boolean MOVE_LEFT;
    private boolean MOVE_RIGHT;
    private boolean STANDING;
    private boolean JUMPING;
    private HashMap<String, Sprite> animations = new HashMap();
    
    //Constructor
    public Player(TileMapManager tmm)
    {
        super(tmm);
        
        x = 100;
        y = 100;
        dx = 0;
        dy = 0;
        
        FALLING = true;
    }//End Constructor
    
    //Getters & Setters
    //X Getter
    public double getX()
    {
        return x;
    }//End x getter
    //Y Getter
    public double getY()
    {
        return y;
    }//End y getter
    public void setAnimations(String direction, Sprite s)
    {
        animations.put(direction, s);
        
        //Static is default sprite
        if(direction.equals("STATIC"))
        {
            sprite = s;
            cHeight = sprite.getHeight();
            cWidth = sprite.getWidth();
        }//End if
    }//End Animations Setter
    
    //Behaviours
    //Update the player
    @Override
    public void update()
    {
        //Init variables for checking changes in movement
        double checkX;
        double checkY;
        
        //Make the new variables be the new x and y positions
        checkX = x + dx;
        checkY = y + dy;
        
        //Check to see if the position we're moving into has a solid tile
        checkTileMapCollision(checkX, checkY);
        
        if(cTopLeft && cBottomLeft)
        {
            System.out.println("Collision Left");
            checkX = x;
        }//End if
        
        if(cBottomRight && cTopRight)
        {
            System.out.println("Collision Right");
            checkX = x;
        }//End if
        
        if(cTopRight || cTopLeft)
        {
            System.out.println("Collision Top");
            checkY = y;
        }//End if
        
        if(cBottomLeft || cBottomRight)
        {
            if(!JUMPING)
            {
                System.out.println("Collision Bottom");
                checkY = y;
                FALLING = false;
                STANDING = true;
                dy = 0;
            }//End if
        }//End if
        else
        {
            FALLING = true;
        }//End else
        
        //If the player is falling, apply gravity
        if(FALLING)
        {
            if(JUMPING && gravity >= jumpStrength)
            {
                gravity = 0;
                dy = 0;
                JUMPING = false;
            }//End if
            if(gravity <= maxGravity)
            {
                gravity += 9.81 / 100;
            }//End if
        }//End if
        else
        {
            gravity = 0;
        }//End else
        
        //Apply the movement to the player
        x = checkX;
        y = checkY;
        
        //Apply the gravity to the player
        y += gravity;
    }//End update
    
    //Update the player's graphics
    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        
        if(STANDING)
        {
            sprite = animations.get("STATIC");
        }//End else if
        if(MOVE_RIGHT)
        {
            sprite = animations.get("RIGHT");
        }//End if
        if(MOVE_LEFT)
        {
            sprite = animations.get("LEFT");
        }//End else if
        if(FALLING)
        {
            sprite = animations.get("FALL");
        }//End else if
        if(JUMPING)
        {
            sprite = animations.get("JUMP");
        }//End else if
    }//End draw
    
    //Move the player left
    public void moveLeft(boolean move)
    {
        if(move == true)
        {
            MOVE_LEFT = true;
            if(dx >= -xSpeedMax)
            {
                dx -= xSpeed;
            }//End if
        }//End if
        else
        {
            MOVE_LEFT = false;
            STANDING = true;
            dx = 0;
        }//End else
    }//End moveLeft
    
    //Move the player right
    public void moveRight(boolean move)
    {
        if(move == true)
        {
            MOVE_RIGHT = true;
            if(dx <= xSpeedMax)
            {
                dx += xSpeed;
            }//End if
        }//End if
        else
        {
            MOVE_RIGHT = false;
            STANDING = true;
            dx = 0;
        }//End else
    }//End moveRight
    
    //Make the player jump
    public void jump(boolean move)
    {
        if(move == true && FALLING == false && JUMPING == false)
        {
            JUMPING = true;
            FALLING = true;
            dy -= jumpStrength;
        }//End if
    }//End jump
    
    //Check for a collision against all enemies
    public void checkEnemyCollision(Enemy[] enemies)
    {
        for(Enemy current : enemies)
        {
            //If there is an intersection with an enemy
            if(intersects(current))
            {
                
            }//End if
        }//End for
    }//End checkEnemyCollision
    
    //Check for a collision against all collectables
    public void checkCollectableCollision(Collectable[] collectables)
    {
        for(Collectable current : collectables)
        {
            //If there is an intersection with a collectable
            if(intersects(current))
            {
                
            }//End if
        }//End for
    }//End checkCollectableCollision
}