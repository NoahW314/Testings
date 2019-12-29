import java.util.ArrayList;
import java.util.List;

public class ErrorTest {
	public static void main(String[] args) {
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);
		l.add(1);
		
		boolean errorOccurred = false;
		int i = 0;
		int li = -1;
		do {
			errorOccurred = false;
			try {
				li = l.get(i);
			} catch(IndexOutOfBoundsException e) {
				errorOccurred = true;
			} finally {
				i++;
				System.out.println("Increased i");
			}
		} while(!errorOccurred);
	}
}
