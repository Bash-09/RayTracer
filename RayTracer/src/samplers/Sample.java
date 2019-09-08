package samplers;

public class Sample {

	private int samples;
	private int index = 0;
	
	public Sample(int samples) {
		this.samples = samples;
	}
	
	public int getSamples() {
		return samples;
	}
	
	public int getInd() {
		return index;
	}
	
	public void increment() {
		index++;
	}
	
}
