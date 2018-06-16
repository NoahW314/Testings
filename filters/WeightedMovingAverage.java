package filters;

import java.util.ArrayList;

public class WeightedMovingAverage implements Filter<Double> {
	
	int sampleSize = 10;
	double[] avAccel = {0,0,0};
	
	public WeightedMovingAverage(int ss) {
		sampleSize = ss;
	}
	public WeightedMovingAverage() {}

	@Override
	public Double run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		
		avAccel[txyz-1] = 0;
		
		for(int i = Math.max(0, measNum-sampleSize+1); i < measNum+1; i++){
			avAccel[txyz-1]+=(i+1-Math.max(0, measNum-sampleSize+1))*(accelerations.get(i)[txyz]+100*(txyz-1));
		}
		avAccel[txyz-1]/=((Math.pow(Math.min(sampleSize, measNum+1), 2)+Math.min(sampleSize, measNum+1))/2);
		
		return avAccel[txyz-1];
	}

}
