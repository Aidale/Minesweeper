package display;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import game.Board;

public class MouseDetector implements MouseListener
{            
    public static boolean pressing = false;
    public void mousePressed(MouseEvent e)
    {	
	pressing = true;		
	Launcher.board.pressed(e.getX(), e.getY());		
    }
    public void mouseReleased(MouseEvent e)
    {
	pressing = false;
	int modifier = e.getModifiers();	
	if (modifier == 16) Launcher.board.releasedLeft(e.getX(), e.getY());	
	if (modifier == 4) Launcher.board.releasedRight(e.getX(), e.getY());		
    }      
    public void mouseClicked(MouseEvent e) {}       
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
