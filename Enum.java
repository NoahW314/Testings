
public enum Enum {
	A(0),
	B(1),
	C(2),
	D(3),
	E(-1);
	public int num;
	private Enum(int i) {
		num = i;
	}
	public static void setInt(int i, int j) {
		switch(i) {
		case 0:Enum.A.num = j;
		case 1:Enum.B.num = j;
		case 2:Enum.C.num = j;
		case 3:Enum.D.num = j;
		case -1:Enum.E.num = j;
		}
	}
}
