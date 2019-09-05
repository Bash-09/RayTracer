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
	
	
	public static Vector3d[] sampleHemisphere(Vector2d[] samples) {
		
		int numSamp = samples.length;
		
		Vector3d[] outSamps = new Vector3d[numSamp];
		
		for(int i = 0; i < numSamp; i++) {
			
			
			
		}
		return outSamps;
	}
	
	/*
	void
	Sampler::map_samples_to_hemisphere(const float e) {
		int size = samples.size();
		hemisphere_samples.reserve(num_samples * num_sets);
		for (int j= 0; j < size; j++) {
			float cos_phi = cos(2.0 * PI * samples[j].x);
			float sin_phi = sin(2.0 * PI * samples[j].x);
			float cos_theta = pow((1.0 - samples[j].y), 1.0 / (e + 1.0));
			float sin_theta = sqrt (1.0 - cos_theta * cos_theta);
			float pu = sin_theta * cos_phi;
			float pv = sin_theta * sin_phi;
			float pw = cos_theta;
		}
	}
	hemisphere_samples.push_back(Point3D(pu, pv, pw));
*/
	
	
}
