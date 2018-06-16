package filters;

import java.util.ArrayList;

public class NoFilter implements Filter<Double> {

	@Override
	public Double run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		return accelerations.get(measNum)[txyz]+100*(txyz-1);
	}

}
