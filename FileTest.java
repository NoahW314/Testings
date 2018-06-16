
public class FileTest {

	public static void main(String[] args) {
		
		DataLogger logger = new DataLogger("AccelCompare");

		logger.addField("Time");
		logger.addField("accel1x");
		logger.addField("accel1y");
		logger.addField("accel1z");
		logger.addField("accel2x");
		logger.addField("accel2y");
		logger.addField("accel2z");
		logger.newLine();
		
		for(int i = 0; i < 80; i++) {
			logger.addField(1);
			/*logger.addField(Math.round(Math.random()*25-Math.pow(i-40, 2)/30));
			logger.addField(Math.round(Math.random()*25-Math.pow(i-40, 2)/30));
			logger.addField(Math.round(Math.random()*25-Math.pow(i-40, 2)/30));
			logger.addField(Math.round(Math.random()*25-Math.pow(i-40, 2)/30));
			logger.addField(Math.round(Math.random()*25-Math.pow(i-40, 2)/30));
			logger.addField(Math.round(Math.random()*25-Math.pow(i-40, 2)/30));*/
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.addField(Math.round(Math.random()*50+50-Math.pow((i*5-200), 2)/300));
			logger.newLine();
		}
		logger.closeDataLogger();
	}

}
