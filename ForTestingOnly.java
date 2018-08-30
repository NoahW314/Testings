
public class ForTestingOnly {
	
	public static void main(String[] args) {
		for(int i = 0, k = 0; i < 6; i++) {
			if(i%2 == 0) {
				k++;
			}
			System.out.println(i+" "+k);
			if(i == 5) {
				System.out.println(k.getClass());
			}
		}
		System.out.println("Done");
	}
}
