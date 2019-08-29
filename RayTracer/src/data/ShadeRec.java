package data;

import java.awt.Color;
import java.util.ArrayList;

public class ShadeRec {

	ArrayList<Collision> collisions = new ArrayList<>();
	
	public Color getColour() {
		if(collisions.size() == 0) {
			return Color.BLACK;
		}
		
		
		
		
		return Color.WHITE;
	}
	
}
