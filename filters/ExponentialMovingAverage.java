package filters;

import java.util.ArrayList;

public class ExponentialMovingAverage implements Filter<Double> {

	double smoothingFactor = 0.5;
	double[] pAvAccel = {0,0,0};
	double[] avAccel = {0,0,0};
	
	public ExponentialMovingAverage(double smoothing) {
		smoothingFactor = smoothing;
	}
	public ExponentialMovingAverage() {}

	@Override
	public Double run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		double currAccel = accelerations.get(measNum)[txyz]+100*(txyz-1);
		if(measNum == 0) {
			pAvAccel[txyz-1] = currAccel;
		}
		
		avAccel[txyz-1] = smoothingFactor*currAccel+(1-smoothingFactor)*pAvAccel[txyz-1];
		
		pAvAccel[txyz-1] = avAccel[txyz-1];
		
		return avAccel[txyz-1];
	}

}
