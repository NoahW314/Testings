

public class BooleanTest {
	
	public static void main(String[] args){
		CollectionTest test = new CollectionTest();
		int[] arr = test.getArr();
		arr[1] = 0;
		test.printArr();
		int[] a = test.getArr();
		System.out.println(a[0]+" "+a[1]+" "+a[2]);
	}
}
