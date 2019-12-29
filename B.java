
public class B implements A{
	private float[] data = new float[2];
	
	public B(float x, float y) {
		data[0] = x;
		data[1] = y;
	}
	
	public float get(int index) {
		return data[index];
	}
	public void put(int index, float value) {
		data[index] = value;
	}
	
	public String toString() {
		return "["+data[0]+", "+data[1]+"]";
	}
}
