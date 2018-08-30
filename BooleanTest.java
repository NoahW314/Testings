public class BooleanTest {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		C c;
		c = new C(BooleanTest.get(B.class, "Name"));
		
	}
	public static <T> T get(Class<T> clazz, String name) throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}
}
