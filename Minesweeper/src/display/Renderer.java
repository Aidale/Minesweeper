package display;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Board;
import game.Tile;

public class Renderer extends JPanel
{   
    private int topBuffer, botBuffer, leftBuffer, rightBuffer, tileSize, yTiles, xTiles;      
    private BufferedImage[] sprites; //unknown, mine, flag, pressed
    private String spritePath;
    private int spriteSize, spriteNum; 
        
    public Renderer()
    {
	initializeMembers();
	createSprites();
    }
    
    private void initializeMembers()
    {	
	topBuffer = Launcher.topBuffer;
	botBuffer = Launcher.botBuffer;
	leftBuffer = Launcher.leftBuffer;
	rightBuffer = Launcher.rightBuffer;				
	tileSize = Launcher.tileSize;
	yTiles = Launcher.yTiles;
	xTiles = Launcher.xTiles;
	
	spritePath = "C:\\Users\\Aidan\\eclipse-workspace\\Minesweeper\\res\\spriteSheet.png";
	spriteSize = 16;
	spriteNum = 13;
    }
    
    private void createSprites()
    {
	BufferedImage spriteSheet;
	sprites = new BufferedImage[spriteNum];
	try
	{
	    spriteSheet = ImageIO.read(new File(spritePath));
	} 
	catch (IOException e)
	{	    
	    e.printStackTrace();
	    return;
	}
	
	Graphics g2d = null;
	for (int i = 0; i < sprites.length; i++)
	{
	    sprites[i] = spriteSheet.getSubimage(i * spriteSize, 0, spriteSize, spriteSize);	    
	    BufferedImage resized = new BufferedImage(tileSize, tileSize, sprites[i].getType());
	    g2d = resized.createGraphics();
	    g2d.drawImage(sprites[i], 0, 0, tileSize, tileSize, null);	    
	    sprites[i] = resized;    
	}
	g2d.dispose();
    }
     
    public void paintComponent(Graphics g)
    {
	Graphics2D g2d = (Graphics2D)g;
	for (int x = 0; x < xTiles; x++)
	{
	    for (int y = 0; y < yTiles; y++)		
	    {
		BufferedImage tileImage = null;
		Tile tile = Launcher.board.getTile(x, y);
		if (tile.isKnown() && !tile.isMine()) 
		{
		    tileImage = sprites[tile.getValue()];
		}
		if (!tile.isKnown())
		{
		    tileImage = sprites[9];
		}
		if (tile.isKnown() && tile.isMine())
		{
		    tileImage = sprites[10];
		}		
		if (tile.isFlagged())
		{
		    tileImage = sprites[11];
		}
		if (tile.isPressed())
		{
		    tileImage = sprites[12];
		}
		g2d.drawImage(tileImage, leftBuffer + x * tileSize, topBuffer + y * tileSize, null);	
	    }
	}	
    }    
}
