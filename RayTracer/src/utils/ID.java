package utils;

public class ID {

	private static int id = 0;
	
	public static int getID() {
		id++;
		return id;
	}
	
}
