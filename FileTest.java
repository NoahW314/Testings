
public class FileTest {

	public static void main(String[] args) {
		
		DataLogger logger = new DataLogger("TwoAccelVelo");

		logger.addField("Time");
		logger.addField("accel1x");
		logger.addField("accel1y");
		logger.addField("accel1z");
		logger.addField("accel2x");
		logger.addField("accel2y");
		logger.addField("accel2z");
		logger.addField("Velocity");
		logger.newLine();
		
		double velo = 0;
		for(int i = 0; i < 80; i++) {
			logger.addField(1);
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			velo+=75-Math.pow((i*5-200), 2)/300;
			logger.addField(velo);
			/*logger.addField(Math.round(Math.random()*50));
			logger.addField(Math.round(Math.random()*50));
			logger.addField(Math.round(Math.random()*50));
			logger.addField(Math.round(Math.random()*50));
			logger.addField(Math.round(Math.random()*50));
			logger.addField(Math.round(Math.random()*50));
			velo+=25;
			logger.addField(velo);*/
			logger.newLine();
		}
		logger.closeDataLogger();
	}

}
