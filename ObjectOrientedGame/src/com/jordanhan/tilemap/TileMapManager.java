package com.jordanhan.tilemap;

import com.jordanhan.main.LevelPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.imageio.ImageIO;

/**
 *
 * @author Jordan Han
 */
public class TileMapManager
{
    //Attributes
    //File-Reading Attributes
    //Tile-Related Attributes
    private final int TILE_SIZE = 64;
    private Tile[] tiles;
    
    private int[][] map;
    private int numberOfColumns;
    private int numberOfRows;
    
    //Camera Attributes
    private double cameraX;
    private double cameraY;
    private int xMax;
    private int xMin;
    private int yMax;
    private int yMin;
    private int rowOffset;
    private int colOffset;
    private int numColumnsToDraw;
    private int numRowsToDraw;
    
    //Constructor
    public TileMapManager()
    {
        try
        {
            File file = new File("TileMap.txt");
            if(!file.exists())
            {
                file.createNewFile();
            }//End if
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.mark(5000);
            int row = 0, col = 0, rows = 0, cols = 0;
            for(String line; (line = bufferedReader.readLine()) != null;)
            {
                rows++;
                char[] chars = line.toCharArray();
                cols = chars.length + 1;
            }//End for
            map = new int[rows][cols];
            bufferedReader.reset();
            for(String line; (line = bufferedReader.readLine()) != null;)
            {
                char[] chars = line.toCharArray();
                for(int i = 0; i < chars.length; i++)
                {
                    switch(chars[i])
                    {
                        case '0': map[row][i] = 0; break;
                        case '1': map[row][i] = 1; break;
                        case '2': map[row][i] = 2; break;
                        case '3': map[row][i] = 3; break;
                    }//End switch
                }//End for
                for(int n : map[row])
                {
                    System.out.print(n);
                }//End for
                System.out.println("");
                row++;
            }//End for
            fileReader.close();
        }//End try
        catch(Exception e)
        {
            e.printStackTrace();
        }//End catch
        int width;
        int height;
        
        numberOfRows = map.length;
        numberOfColumns = map[0].length;
        
        numColumnsToDraw = LevelPanel.PANEL_WIDTH / TILE_SIZE + 2;
        numRowsToDraw = LevelPanel.PANEL_HEIGHT / TILE_SIZE + 2;
        
        width = numberOfColumns * TILE_SIZE;
        height = numberOfRows * TILE_SIZE;
        
        xMin = LevelPanel.PANEL_WIDTH - width;
        xMax = 0;
        yMin = LevelPanel.PANEL_HEIGHT - height;
        yMax = 0;
        
        cameraX = 0;
        cameraY = 0;
        
        //Unused?
        //loadMap();
        loadTiles();
    }//End constructor
    
    //Getters & Setters
    //Camera X Getter
    public double getCameraX()
    {
        return cameraX;
    }//End cameraX getter
    
    //Camera Y Getter
    public double getCameraY()
    {
        return cameraY;
    }//End cameraY getter
    
    //Behaviours
    private void loadTiles()
    {
        tiles = new Tile[3];
        
        try
        {
            tiles[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/Images/col1.png")), Tile.TYPE_NORMAL);
            tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/Images/col2.png")), Tile.TYPE_BLOCKED);
            tiles[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/Images/col3.png")), Tile.TYPE_BLOCKED);
        }//End try
        catch(java.io.IOException e)
        {
            System.err.println(e);
        }//End catch
    }//End loadTiles
    
    public Tile getTileAt(double x, double y)
    {
        //Convert from local (camera) coordinates to global coordinates
        x = x - cameraX;
        y = y - cameraY;
        
        int row = (int) y / TILE_SIZE;
        int col = (int) x / TILE_SIZE;
        
        int tileID = map[row][col];
        
        tileID = matchTile(tileID);
        System.out.println("X: " + x + "\tY: " + y);
        System.out.println("In Tile: " + row + ", " + col);
        return tiles[tileID];
    }//End getTileAt
    
    public void setCameraPosition(double x, double y)
    {
        cameraX += (x - cameraX);
        cameraY += (y - cameraY);
        
        fixCameraBounds();
        
        colOffset = (int) - cameraX / TILE_SIZE;
        rowOffset = (int) - cameraY / TILE_SIZE;
    }//End setCameraPosition
    
    private void fixCameraBounds()
    {
        if(cameraX < xMin)
        {
            cameraX = xMin;
        }//End if
        if(cameraX > xMax)
        {
            cameraX = xMax;
        }//End if
        if(cameraY < yMin)
        {
            cameraY = yMin;
        }//ENd if
        if(cameraY > yMax)
        {
            cameraY = yMax;
        }//End if
    }//End fixCameraBounds
    
    private int matchTile(int tileMapID)
    {
        int mappedTile = 0;
        
        switch(tileMapID)
        {
            case 1:
                mappedTile = 0;
                break;
            case 2:
                mappedTile = 1;
                break;
            case 3:
                mappedTile = 2;
                break;
        }//End switch
        
        return mappedTile;
    }//End matchTile
    
    public void draw(Graphics2D g)
    {
        int tileImage;
        //Offsets from camera start position
        double tempX;
        double tempY;
        
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.white);
        g.drawString("Camera X: " + cameraX + "\tCamera Y: " + cameraY, 20, 20);
        
        g.setColor(Color.black);
        for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++)
        {
            if(row >= numberOfRows) break;
            
            tempY = cameraY + row * TILE_SIZE;
            for(int col = colOffset; col < colOffset + numColumnsToDraw; col++)
            {
                if(col >= numberOfColumns) break;
                
                tempX = cameraX + col * TILE_SIZE;
                if(map[row][col] != 0)
                {
                    tileImage = matchTile(map[row][col]);
                    g.drawImage(tiles[tileImage].getImage(), (int) tempX, (int) tempY, null);
                    g.drawRect((int) tempX, (int) tempY, TILE_SIZE, TILE_SIZE);
                }//End if
            }//End for
        }//End for
    }//End draw
}