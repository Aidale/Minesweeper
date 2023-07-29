package display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyDetector implements KeyListener
{
    public static boolean typing;
    public static int keyID;
    public void keyTyped(KeyEvent e)
    {	
	
    }

    public void keyPressed(KeyEvent e)
    {	
	typing = true;
	keyID = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e)
    {	
	typing = false;		
    }

}
