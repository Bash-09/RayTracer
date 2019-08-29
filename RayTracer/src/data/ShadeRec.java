package data;

import java.awt.Color;
import java.util.ArrayList;

public class ShadeRec {

	private ArrayList<Collision> collisions = new ArrayList<>();
	
	public Color getColour() {
		if(collisions.size() == 0) {
			return Color.BLACK;
		}
		
		
		double closestDist;
		int closestInd = 0;
		
		closestDist = collisions.get(0).getDistance();

		for(int i = 1; i < collisions.size(); i++) {
			double temp = collisions.get(i).getDistance();
			
			if(temp < closestDist) {
				closestDist = temp;
				closestInd = i;
			}
		}
		
		return collisions.get(closestInd).getObject().getCol();
	}
	
	public void addCollision(Collision col) {
		collisions.add(col);
	}
	
}
