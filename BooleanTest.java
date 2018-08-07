import org.ejml.simple.SimpleMatrix;

public class BooleanTest {
	public static void main(String[] args) {
		SimpleMatrix s = new SimpleMatrix(new double[][] {{1,2,3}, {4,5,6}, {7,8,9}});
		s.print();
		s = s.extractMatrix(0, 2, 0, 2);
		s.print();
	}
}
