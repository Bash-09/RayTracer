package io;

public class TextParser {

	public static float[] parseVector(String source) {
		String[] nums = source.split(":");
		float[] out = new float[nums.length];
		
		for(int i = 0; i < out.length; i++) {
			out[i] = Float.parseFloat(nums[i]);
		}
		return out;
	}
	
	
	
}
