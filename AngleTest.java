
public class AngleTest {
	public static void main(String[] args) {
		double[] a = new double[] {0, 30, 45, 90, 120, 135, 180, 270};
				
		for(int i = 0; i < a.length; i++) {
			System.out.println(autoToTeleDriveAngle(a[i]));
		}
	}
	
	//work in degrees
	public static double autoToTeleDriveAngle(double autoDriveAngle) {
		//extract the x and y components of the auto drive angle
		double x = Math.cos(autoDriveAngle*Math.PI/180);
		double y = Math.sin(autoDriveAngle*Math.PI/180);
		
		double teleDriveAngle = Math.atan2(-y, x)*180/Math.PI;
		teleDriveAngle-=90;
		return teleDriveAngle;
	}
}
