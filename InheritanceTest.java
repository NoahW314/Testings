

public class InheritanceTest {

	public static void main(String[] args) {
		A b = new B();
		
		System.out.println(b instanceof B);
	}
	
	public static void get(B b) {
		printPrivate((C)b);
	}
	
	private static void printPrivate(B b) {System.out.println("Be a Squirrel B!");}
	private static void printPrivate(C c) {System.out.println("Now See Here C!");}

}
