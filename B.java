
public class B extends A {

	public B(Program program) {
		super(program);
	}
	public enum BProgram implements Program{A, B, C, D, E};
	/*public B() {
		programs = new Bprograms() {};
	}
	interface Bprograms extends Programs {
	    
	}*/
}
