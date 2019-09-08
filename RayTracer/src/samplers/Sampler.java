package samplers;

import org.joml.Vector2d;
import org.joml.Vector3d;

public class Sampler {

	//TODO finish this
	public static Vector2d[] sampleUnitSquare(int numSamples) {
		Vector2d[] samples;
		
		int sqrt = (int)Math.sqrt(numSamples);
		double scale = 1/(double)sqrt;
		int ind = 0;
		
		samples = new Vector2d[sqrt*sqrt];
		
		for(int i = 0; i < sqrt; i++) {
			for(int j = 0; j < sqrt; j++) {
				
				double x = scale * i + scale/2;
				double y = scale * j + scale/2;
				
				x += Math.random();
				y += Math.random();
				
				samples[ind] = new Vector2d(x, y);
				
				ind++;
			}
		}
		
		return samples;
	}
	
	/*
	public static Vector3d[] sampleHemisphere(int sampleNum, float e) {
		
		Vector2d[] samples = sampleUnitSquare(sampleNum);
		
		int numSamp = samples.length;
		
		Vector3d[] outSamps = new Vector3d[numSamp];
		
		for(int i = 0; i < numSamp; i++) {
			
			double cos_phi = Math.cos(2.0 * Math.PI * samples[i].x);
			double sin_phi = Math.sin(2.0 * Math.PI * samples[i].x);
			double cos_theta = Math.pow((1.0 - samples[i].y), 1.0 / (e + 1.0));
			double sin_theta = Math.sqrt (1.0 - cos_theta * cos_theta);
			double pu = sin_theta * cos_phi;
			double pv = sin_theta * sin_phi;
			double pw = cos_theta;
			
			outSamps[i] = new Vector3d(pu, pv, pw);
		}
		return outSamps;
	}
	*/
	
	public static Vector3d sampleHemisphere(int sampleSize, int index, float e) {
		
		//Get 2D sample on square
		int sqrt = (int)Math.sqrt(sampleSize);
		double scale = 1/(double)sqrt;

		Vector2d sample;

		double x = scale * (index/sqrt) + scale/2;
		double y = scale * (index%sqrt) + scale/2;
		x += Math.random();
		y += Math.random();
		sample = new Vector2d(x, y);
		
		//Map 2d location to place on sphere
		double cos_phi = Math.cos(2.0 * Math.PI * sample.x);
		double sin_phi = Math.sin(2.0 * Math.PI * sample.x);
		double cos_theta = Math.pow((1.0 - sample.y), 1.0 / (e + 1.0));
		double sin_theta = Math.sqrt (1.0 - cos_theta * cos_theta);
		double pu = sin_theta * cos_phi;
		double pv = sin_theta * sin_phi;
		double pw = cos_theta;
		
		//return new Vector3d(pu, pv, pw);
		return new Vector3d(cos_phi, sin_phi, 0);
		
	}
	
}
