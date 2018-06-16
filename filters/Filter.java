package filters;

import java.util.ArrayList;

public interface Filter<D> {
	public D run(ArrayList<Double[]> accelerations, int measNum, int txyz);
}
