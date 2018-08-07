import java.util.ArrayList;

public class CollectionTest {

	public static void main(String[] args) {
		int[] in = new int[5];
		System.out.println(in.length);
		in[0] = 1;
		System.out.println(in.length);
		in[4] = 5;
	}
	
	private static <T> ArrayList<T> setDefaultArrayListValue(T defaultValue, int targetNum) {
		ArrayList<T> list = new ArrayList<T>(targetNum);
		for(int i = 0; i < targetNum; i++) {
			list.add(defaultValue);
		}
		return list;
	}

}
