
public class CompilerTest {
	
	public boolean pprs = privateStatic("Public");
	public boolean ppr = privateMethod("Public");
	public boolean pps = publicStatic("Public");
	public boolean pp = publicMethod("Public");
	
	public static boolean psprs = privateStatic("Public Static");
	public static boolean psps = publicStatic("Public Static");
	
	private boolean prprs = privateStatic("Private");
	private boolean prpr = privateMethod("Private");
	private boolean prps = publicStatic("Private");
	private boolean prp = publicMethod("Private");
	
	private static boolean prsprs = privateStatic("Private Static");
	private static boolean prsps = publicStatic("Private Static");
	
	public static void main(String[] args) {
		System.out.println("Running Main");
		
		CompilerTest ct = new CompilerTest();
	}
	
	private static boolean privateStatic(String s) {
		System.out.println("Private Static "+s);
		return false;
	}
	private boolean privateMethod(String s) {
		System.out.println("Private Method "+s);
		return false;
	}
	public static boolean publicStatic(String s) {
		System.out.println("Public Static "+s);
		return false;
	}
	public boolean publicMethod(String s) {
		System.out.println("Public Method "+s);
		return false;
	}
	
}
