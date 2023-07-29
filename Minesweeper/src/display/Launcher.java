package display;

import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Board;

public class Launcher 
{
    public static int topBuffer = 10, botBuffer = 10, leftBuffer = 10, rightBuffer = 10,
	    WIDTH = 1200, HEIGHT = 700, xTiles = 40, yTiles = 20, boardWidth, boardHeight, tileSize;
    public static double mineRate = 0.3;  
    public static Board board = new Board(xTiles, yTiles, mineRate);    
    public static void main(String[] args)
    {	
	initializeMembers();
	Renderer r = new Renderer();	
	JFrame frame = new JFrame();
	frame.setVisible(true);
	frame.setResizable(false);
	//frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        frame.setSize(WIDTH, HEIGHT + 37);        
        frame.setTitle("Minesweeper V2");        
        frame.add(r);
        frame.addMouseListener(new MouseDetector());    
        frame.addKeyListener(new KeyDetector());          
        while (true)
        {
            r.repaint();
            if (MouseDetector.pressing)
            {        	        	    
    		Point f = frame.getLocation();
    		Point m = MouseInfo.getPointerInfo().getLocation();
    		double x = m.getX() - f.getX();
    		double y = m.getY() - f.getY();        		
        	board.pressed((int)x, (int)y);    
            }
            if (KeyDetector.typing)
            {
        	Point f = frame.getLocation();
    		Point m = MouseInfo.getPointerInfo().getLocation();
    		double x = m.getX() - f.getX();
    		double y = m.getY() - f.getY();        		
        	board.typed((int)x, (int)y, KeyDetector.keyID);
            }
            try
            {
        	Thread.sleep(10);
            }
            catch (InterruptedException e) {}
        }
    }
    
    private static void initializeMembers()
    {		
	boardWidth = WIDTH - leftBuffer - rightBuffer;
	boardHeight = HEIGHT - topBuffer - botBuffer;
	int tileWidth = boardWidth / xTiles;
	int tileHeight = boardHeight / yTiles;
	
	tileSize = Math.min(tileWidth, tileHeight);
	
	rightBuffer += boardWidth - tileSize * xTiles;
	botBuffer += boardHeight - tileSize * yTiles;
	
	boardWidth = WIDTH - leftBuffer - rightBuffer;
	boardHeight = HEIGHT - topBuffer - botBuffer;
    }
}
