package game;

import java.awt.event.KeyEvent;

import display.Launcher;

public class Board
{
    private int xTiles, yTiles;
    private Tile[][] tiles;
    private boolean starting;
    public Board(int width, int height, double mineRate)
    {
	xTiles = width;
	yTiles = height;	
	tiles = new Tile[xTiles][yTiles];
	starting = true;
	int mineCount = (int)(xTiles * yTiles * mineRate);
	generateTiles();
	generateMines(mineCount);
	assignValues();
    }
    
    private void generateTiles()
    {
	for (int x = 0; x < xTiles; x++)
	{
	    for (int y = 0; y < yTiles; y++)
	    {
		tiles[x][y] = new Tile();
	    }
	}
    }
    
    private void generateMines(int mineCount)
    {	
	int counter = 0;
	while (counter < mineCount)
	{
	    int randX = (int)(Math.random() * xTiles);
	    int randY = (int)(Math.random() * yTiles);
	    if (!tiles[randX][randY].isMine())
	    {
		tiles[randX][randY].setMine(true);
		counter++;
	    }
	}
    }
    
    private void assignValues()
    {
	for (int x = 0; x < xTiles; x++)
	{
	    for (int y = 0; y < yTiles; y++)
	    {
		int value = 0;
		for (int dx = -1; dx <= 1; dx++)
		{
		    for (int dy = -1; dy <= 1; dy++)
		    {			
			try
			{
			    if (tiles[x + dx][y + dy].isMine()) value++;
			}
			catch (Exception ex) {}			
		    }
		}
		tiles[x][y].setValue(value);
		if (value == 0) tiles[x][y].setMine(false);
	    }
	}
    }
                     
    public Tile getTile(int x, int y) 
    {
	try 
	{
	    return tiles[x][y];
	}
	catch (Exception ex)
	{
	    return null;
	}
    }
    
    public void pressed(int xLoc, int yLoc)
    {	
	xLoc = convertX(xLoc);
	yLoc = convertY(yLoc);
	for (int x = 0; x < xTiles; x++)
	{
	    for (int y = 0; y < yTiles; y++)
	    {
		tiles[x][y].setPressed(false);
	    }
	}
	if (0 <= xLoc && xLoc < xTiles && 0 <= yLoc && yLoc < yTiles) 
	{
	    tiles[xLoc][yLoc].setPressed(true);
	}
    }
    
    public void releasedLeft(int xLoc, int yLoc)
    {
	xLoc = convertX(xLoc);
	yLoc = convertY(yLoc);
//	if (starting)
//	{
//	    int minesRemoved = 0;
//	    for (int dx = -1; dx <= 1; dx++)
//	    {
//		for (int dy = -1; dy <= 1; dy++)
//		{
//		    if (tiles[xLoc + dx][yLoc + dy].isMine()) minesRemoved++;
//		    tiles[xLoc + dx][yLoc + dy].setMine(false);
//		}
//	    }	    
//	    //generateMines(minesRemoved);
//	    assignValues();
//	    starting = false;
//	}
	if (0 <= xLoc && xLoc < xTiles && 0 <= yLoc && yLoc < yTiles && !tiles[xLoc][yLoc].isKnown()) 
	{	    
	    tiles[xLoc][yLoc].setKnown();
	    if (tiles[xLoc][yLoc].getValue() == 0) chainReveal(xLoc, yLoc);
	}
	for (int x = 0; x < xTiles; x++)
	{
	    for (int y = 0; y < yTiles; y++)
	    {
		tiles[x][y].setPressed(false);
	    }
	}
    }
    
    public void releasedRight(int xLoc, int yLoc)
    {
	xLoc = convertX(xLoc);
	yLoc = convertY(yLoc);
	if (0 <= xLoc && xLoc < xTiles && 0 <= yLoc && yLoc < yTiles && !tiles[xLoc][yLoc].isKnown()) 
	{	    
	    tiles[xLoc][yLoc].toggleFlagged();	    
	}
	for (int x = 0; x < xTiles; x++)
	{
	    for (int y = 0; y < yTiles; y++)
	    {
		tiles[x][y].setPressed(false);
	    }
	}
    }
    
    public void typed(int xLoc, int yLoc, int keyID)
    {
	xLoc = convertX(xLoc);
	yLoc = convertY(yLoc);
	if (!(0 <= xLoc && xLoc < xTiles && 0 <= yLoc && yLoc < yTiles)) return;
	if (keyID == KeyEvent.VK_M)
	{
	    tiles[xLoc][yLoc].setMine(true);	
	    assignValues();
	}
	if (keyID == KeyEvent.VK_S)
	{
	    tiles[xLoc][yLoc].setMine(false);
	    assignValues();
	}
	if (keyID == KeyEvent.VK_K)
	{
	    tiles[xLoc][yLoc].toggleKnown();
	}
	if (keyID == KeyEvent.VK_C)
	{
	    revealAll();
	}
	if (keyID == KeyEvent.VK_A) 
	{
	    for (int x = 0; x < xTiles; x++)
            {
                for (int y = 0; y < yTiles; y++)
                {
                    tiles[x][y].toggleKnown();
                }
            }	
	}
    }
    
    private int convertX(int xLoc)
    {
	return (xLoc - Launcher.leftBuffer - 7) / Launcher.tileSize;	
    }
    
    private int convertY(int yLoc)
    {	
	return (yLoc - Launcher.topBuffer - 30) / Launcher.tileSize;
    }       
    
    private void chainReveal(int xLoc, int yLoc)
    {
	for (int dx = -1; dx <= 1; dx++)
	{
	    for (int dy = -1; dy <= 1; dy++)
	    {		
		try
		{
		    Tile tile = tiles[xLoc + dx][yLoc + dy];
		    if (!tile.isFlagged() && !tile.isMine() && !tile.isKnown())
		    {
			tile.setKnown();
			if (tile.getValue() == 0)
			{
			    chainReveal(xLoc + dx, yLoc + dy);
			}
		    }
		}
		catch (Exception ex) {}				    		
	    }
	}
    }        
    
    public void revealAll()
    {
	for (int x = 0; x < xTiles; x++)
	{
	    for (int y = 0; y < yTiles; y++)
	    {
		if (!tiles[x][y].isMine()) tiles[x][y].setKnown();
		else tiles[x][y].toggleFlagged();
	    }
	}
    } 
}
