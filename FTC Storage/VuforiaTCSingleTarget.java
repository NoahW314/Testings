package org.firstinspires.ftc.teamcode.teamcalamari.TCHardware;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.vuforia.VuforiaLocalizerImpl;
import org.firstinspires.ftc.teamcode.teamcalamari.Location;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class VuforiaTCSingleTarget extends VuforiaLocalizerImpl {
	
	public VuforiaTrackable trackable;
	public VuforiaTrackables trackables;
	
	public VuforiaTCSingleTarget(Parameters parameters, String trackableFileName) {
		super(parameters);
		trackables = this.loadTrackablesFromAsset(trackableFileName);
		trackable = trackables.get(0);
		trackable.setName(trackableFileName);
	}
	
	public static class Parameters extends VuforiaLocalizer.Parameters{
		public Parameters() {
			this(null);
		}
		public Parameters(HardwareMap hardwareMap) {
			super((hardwareMap == null) ? 0 : 
			hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
			this.vuforiaLicenseKey = "AYqgQLz/////AAAAGRlIgk9eREkPgDEX/RlJyHIz0YsCaszxVGJICWVVqeHlMo4A7pUDj/8aGsZWdzti4GmnFY1jkXVNx0bOeDARShruEAMbQ7kggUFqwvOxOkxd4BgBlPY+PxDbNI1akD1TZAwslS6PB/hLixKfKtWzGnWpc4lpQonIV5/ZZZ/FT+nAwkS/AkLtECZHbYKqhFlL7Uq2RimV67Z+b2SjIAp0E0zlU8S8tRiDkNwqVAsnBngUUdg/nxSeFIqQJIJgy6cSuiAvgHNjlGnkMW4s8MHMeCFrms7AB+goLQANdiC52Bz4RAAM6Xym7DrXf339sYbM9ZolMSppyk0g3oLOJLZg9Px9C/EqOxI4nkB/h7OGrQDN";
		}
	}
	
	/**The target starts with the picture facing up, with the right side (looking down on the target) pointing towards 
	the positive x-axis, and with the top side pointing towards the positive y-axis.
	The positive z-axis comes straight up out of the target.
	All rotations and translations are relative to the FTC field coordinate system.
	It is best to perform the rotations first while the target is still located at the origin.
	This is accomplished by doing 
	
	<pre>OpenGLMatrix.translation(x,y,z)
	.multiplied(Orientation.getRotationMatrix(axesReference, axesOrder, angleUnit, 
	firstRot, secondRot, thirdRot)</pre>
	
	@see <a href= "https://github.com/ftctechnh/ftc_app/files/989938/FTC_FieldCoordinateSystemDefinition.pdf">FTC field coordinate system</a>*/
	public void setTargetLocation(OpenGLMatrix targetLocation) {
		trackable.setLocation(targetLocation);
	}
	/**The phone starts with the screen facing up ( in the direction of the positive z-axis), 
	with the right side (looking down at the phone) pointing towards the positive x-axis, 
	and with the top side pointing towards the positive y-axis.
	All rotations and translations are relative to the robot axes.
	The robot axes are user defined and determined by Vuforia using the matrix you give it here.
	It is best to perform the rotations first while the target is still located at the origin.
	This is accomplished by doing 
	
	<pre>OpenGLMatrix.translation(x,y,z)
	.multiplied(Orientation.getRotationMatrix(axesReference, axesOrder, angleUnit, 
	firstRot, secondRot, thirdRot)</pre>*/
	public void setPhoneLocation(OpenGLMatrix phoneLocation) {
        ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneLocation, parameters.cameraDirection);
	}
	
	public void activate() {
		trackables.activate();
	}
	
	public Position getPositionOnField() {
		return getLocationOnField().position;
	}
	public Orientation getOrientationOnField() {
		return getLocationOnField().orientation;
	}
	public Location getLocationOnField() {
		return Location.openGLMatrixToLocation(((VuforiaTrackableDefaultListener) trackable.getListener()).getRobotLocation(),
				DistanceUnit.MM);
	}
	
	public boolean isVuMark() {
		return ((VuforiaTrackableDefaultListener)trackable.getListener()).getVuMarkInstanceId() != null;
	}
}
