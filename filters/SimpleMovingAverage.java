package filters;

import java.util.ArrayList;

public class SimpleMovingAverage implements Filter<Double> {
	
	int sampleSize = 10;
	double[] avAccel = {0,0,0};
	double[] pAvAccel = {0,0,0};
	
	public SimpleMovingAverage(int ss){
		sampleSize = ss;
	}
	public SimpleMovingAverage() {}

	@Override
	public Double run(ArrayList<Double[]> accelerations, int measNum, int txyz) {
		double currAccel = accelerations.get(measNum)[txyz]+100*(txyz-1);
		
		if(measNum == 0) {
			pAvAccel[txyz-1] = currAccel;
		}
		
		if(measNum < sampleSize) {
			avAccel[txyz-1] = (currAccel+(measNum)*pAvAccel[txyz-1])/(measNum+1);
		}
		else {
			avAccel[txyz-1] = pAvAccel[txyz-1]+(currAccel-accelerations.get(measNum-sampleSize)[txyz]-100*(txyz-1))/sampleSize;
		}
		pAvAccel[txyz-1] = avAccel[txyz-1];
		
		return avAccel[txyz-1];
	}
	
}
