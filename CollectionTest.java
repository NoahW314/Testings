import java.util.ArrayList;
import java.util.Collections;

public class CollectionTest {

	public static void main(String[] args) {
		ArrayList<Boolean> list = new ArrayList<Boolean>(5);
		list = setDefaultArrayListValue(null, 5);
		System.out.println(list);
		list.set(0, true);
		System.out.println(list);
		list.set(0, null);
		System.out.println(list);
	}
	
	private static <T> ArrayList<T> setDefaultArrayListValue(T defaultValue, int targetNum) {
		ArrayList<T> list = new ArrayList<T>(targetNum);
		for(int i = 0; i < targetNum; i++) {
			list.add(defaultValue);
		}
		return list;
	}

}
