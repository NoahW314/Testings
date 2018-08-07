
public class FileTest {

	public static void main(String[] args) {
		
		DataLogger logger = new DataLogger("2A2P");

		logger.addField("Time");
		logger.addField("accel1x");
		logger.addField("accel1y");
		logger.addField("accel1z");
		logger.addField("accel2x");
		logger.addField("accel2y");
		logger.addField("accel2z");
		logger.addField("Encoder");
		logger.addField("Vuforia");
		logger.addField("Ultrasonic");
		logger.newLine();
		
		double velo = 0;
		double eP = 0;
		double vP = 0;
		double uP = 0;
		for(int i = 0; i < 80; i++) {
			logger.addField(1);
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			eP = velo+Math.random()*2500-1250;
			vP+=velo+Math.random()*750-375;
			if(i < 40) {uP+=velo+Math.random()*1250-625;}
			else {uP+=Math.random()*1250-625;}
			velo+=75-Math.pow((i*5-200), 2)/300;
			logger.addField(eP);
			logger.addField(vP);
			logger.addField(uP);
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
