
public class LoggerTest {

	public static void main(String[] args) throws InterruptedException {
		DataLoggerTime logger = new DataLoggerTime("LogTest");
		
		logger.addField("Value");
		logger.newLine();
		Thread.sleep(4000);
		logger.addField(0);
		logger.newLine();
		logger.closeDataLogger();
	}

}
