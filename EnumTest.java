
public class EnumTest {
	public static void main(String[] args) {
		Enum.setInt(0, -1);
		Enum e = Enum.A;
		System.out.println(Enum.A.num);
		System.out.println(e.num);
		Enum.setInt(0, 2);
		System.out.println(Enum.A.num);
		System.out.println(e.num);
		Enum.setInt(0, 3);
		Enum e2 = Enum.A;
		System.out.println(Enum.A.num);
		System.out.println(e.num);
		System.out.println(e2.num);
	}
}
