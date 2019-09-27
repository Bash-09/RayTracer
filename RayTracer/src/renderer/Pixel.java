package renderer;

import java.awt.Color;
import java.awt.Point;

public class Pixel {

	public Pixel(int x, int y) {
		pos = new int[2];
		pos[0] = x;
		pos[1] = y;
	}
	
	public int[] pos;
	public Color col;
}
