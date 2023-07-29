package game;

public class Tile
{   
    private int value;
    private boolean mine;    
    private boolean flagged;
    private boolean known;
    private boolean pressed;
    public Tile()
    {		
	mine = false;
	known = false;
	flagged = false;
    }
        
    public void toggleFlagged() 
    {
	if (!known)
	{
	    flagged = !flagged;
	    pressed = false;
	}
    }   
    
    public void setKnown() 
    {
	if (!flagged) 
	{
	    known = true;
	    pressed = false;
	}
    }          
    
    public void setPressed(boolean isPressed)
    {
	if (isPressed && !flagged && !known)
	{
	    pressed = true;
	}
	else pressed = false;
    }
        
    public void setValue(int value) {this.value = value;}
    public int getValue() {return value;}
    public void setMine(boolean isMine) {mine = isMine;}
    public boolean isMine() {return mine;}
    public void toggleMine() {mine = !mine;}
    public void setKnown(boolean isKnown) {known = isKnown;}
    public boolean isKnown() {return known;}
    public void toggleKnown() {known = !known;}
    public boolean isFlagged() {return flagged;} 
    public boolean isPressed() {return pressed;}
}
