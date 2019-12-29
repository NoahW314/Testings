import java.util.ArrayList;

public class CollectionTest {

	public static void main(String[] args) {
		/*int[] a = new int[] {1, 2, 3};
		setInt(a, 1, 0);
		a[0] = -1;
		System.out.println(a[0]+" "+a[1]+" "+a[2]);*/
		
		/*ArrayList<Integer> arrL1 = new ArrayList<>();
		arrL1.add(0);
		arrL1.add(1);
		arrL1.add(2);
		
		System.out.println("Start "+arrL1.toString());
		
		ArrayList<ArrayList<Integer>> arrL = new ArrayList<>();
		arrL.add(new ArrayList<Integer>());
		arrL.add(arrL1);
		
		ArrayList<ArrayList<Integer>> arrL2 = new ArrayList<>();
		arrL2.add(arrL1);
		
		arrL.get(1).set(0, -1);
		
		System.out.println("Changed "+arrL1.toString());
		System.out.println("2 "+arrL2.toString());
		System.out.println("1 "+arrL.toString());*/
	}
	
	public static void setInt(int[] arr, int index, int value) {
		arr[index] = value;
	}
	
	private int[] a = new int[] {1,2,3};
	public int[] getArr() {
		return a;
	}
	public void printArr() {
		System.out.println(a[0]+" "+a[1]+" "+a[2]);
	}
}
