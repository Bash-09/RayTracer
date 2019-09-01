package renderer;

import java.awt.Color;

public class Chunk {

	public Color[] chunk;
	private int i;
	
	public Chunk(int i, Color[] chunk) {
		this.i = i;
		this.chunk = chunk;
	}
	
	public int getInd() {
		return i;
	}
	
}
