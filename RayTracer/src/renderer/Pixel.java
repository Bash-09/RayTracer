package renderer;

import java.awt.Color;
import java.awt.Point;

public class Pixel {

	public Pixel(int x, int y) {
		pos = new Point(x, y);
	}
	
	private Point pos;
	private Color col;
	
	public void setColour(Color col) {
		this.col = col;
	}
	public Color getCol() {
		return col;
	}
	
	public Point getPos() {
		return pos;
	}
	
}
