import java.util.ArrayList;

public class RunnableTest {
	public static void main(String[] args) {
		ArrayList<Runnable2> runnables = new ArrayList<Runnable2>();
		runnables.add(new Runnable2 (){
			@Override
			public boolean act() {
				System.out.println("1");
				return false;
			}
		});
		for(int i = 0; i < 1; i++) {
			if(!runnables.get(i).act()) {
				System.out.println("Working");
			}
		}
	}
}
