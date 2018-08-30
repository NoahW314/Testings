package org.firstinspires.ftc.teamcode.teamcalamari.Simulation.HardwareSim.DriveSim;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.teamcalamari.Angle;
import org.firstinspires.ftc.teamcode.teamcalamari.Math2;
import org.firstinspires.ftc.teamcode.teamcalamari.OpModeType;
import org.firstinspires.ftc.teamcode.teamcalamari.Simulation.HardwareSim.MotorsSim.MotorSim;


/**An abstract class for drivetrains that can drive in any direction without turning*/
public abstract class AllDirectionsDriveSim extends DriveSim {
	/**The type of OpMode, AUTO (Autonomous) or TELE (TeleOp) */
	protected OpModeType opModeType;
	/**the angle of the front of the robot relative to the first wheel motor*/
    protected Angle headAngle;

	/**The wheel motors should be listed clockwise.
    A line drawn from the center of the robot to the first wheel motor listed indicates the positive x-axis.
    headOffset is measured in degrees and is measured counterclockwise from the positive x-axis.*/
	public AllDirectionsDriveSim(MotorSim[] motors, Angle headOffset, OpModeType type) {
		super(motors);
		headAngle = headOffset;
		opModeType = type;
	}

    /**Drives the robot at a set angle*/
	public abstract void driveAtAngle(Angle angle);
	
	/**Drives the robot to a set position while turning to a set angle.  
	NO OTHER DRIVING METHODS SHOULD BE USED WHEN THIS METHOD IS CALLED.
	You do not need to call <code>drive()</code> when using this method.
	<code>turnSpeed</code> and <code>driveSpeed</code> work in a similar manner as in <code>turnTo</code> and <code>driveTo</code>,
	but there is not a simple relation. 
	@return if the robot has driven to the set position and turned to the set angle*/
	public boolean turnDriveTo(VectorF currentPose, VectorF targetPose, Angle currentAngle, Angle targetAngle) {
		Angle newTargetAngle = Angle.convertAngle(targetAngle, currentAngle);
		if(Math2.round(Math2.abs(currentPose.subtracted(targetPose)).magnitude(), driveError*2) == 0
		&& Math2.round(Angle.abs(currentAngle.subtract(newTargetAngle)), turnError.mult(2)).getDegree() == 0) {
			firstMoveToCall = true;
			maxDiffDist = null;
			return true;
		}
		else {
			if(firstMoveToCall) {
				maxDiffDist = Math.abs(targetPose.subtracted(currentPose).magnitude());
			}
			
			double prevTurnSpeed = turnSpeed;
			double prevDriveSpeed = driveSpeed;
			double setSpeed = speed;
			
			if(maxDiffDist == 0) throw new IllegalStateException("target position and starting position are equal");
			else if(maxDiffDist == null) throw new IllegalStateException("firstMoveToCall was not set to true"); 
			else {
				turnSpeed = Angle.abs(newTargetAngle.subtract(currentAngle)).div(maxDiffAngle)*setSpeed/(1-prevTurnSpeed);
				driveSpeed = Math.abs(targetPose.subtracted(currentPose).magnitude())*setSpeed/((1-prevDriveSpeed)*maxDiffDist);
				speed = turnSpeed+driveSpeed;
			}
			if(speed > setSpeed) speed = setSpeed;
			
			Angle driveAngle = new Angle(Math.atan2(targetPose.get(1)-currentPose.get(1), targetPose.get(0)-currentPose.get(0)), AngleUnit.RADIANS);
			
			driveAtAngle(driveAngle.subtract(currentAngle).subtract(headAngle));
			turn((newTargetAngle.greaterThan(currentAngle)) ? turnDirection.CCW : turnDirection.CW);
			drive();
			
			turnSpeed = prevTurnSpeed;
			driveSpeed = prevDriveSpeed;
			speed = setSpeed;
			
			firstMoveToCall = false;
			return false;
		}
	}
	
	/**Turns the robot in the given direction*/
    @Override
    public void turn(turnDirection direction){
        for(int i = 0; i < wheelMotors.length; i++) {
        	if(direction == turnDirection.CCW) wheelMotors[i].setPower(turnSpeed);
        	else wheelMotors[i].setPower(-turnSpeed);
        }
    }
	
	@Override
	public boolean driveTo(VectorF currentPose, VectorF targetPose, Angle heading) {
		if(Math2.round(Math2.abs(currentPose.subtracted(targetPose)).magnitude(), driveError*2) == 0) {
			firstMoveToCall = true;
			maxDiffDist = null;
			return true;
		}
		else {
			double setSpeed = speed;
			if(firstMoveToCall) {
				maxDiffDist = Math.abs(targetPose.subtracted(currentPose).magnitude());
			}
			if(maxDiffDist == 0) throw new IllegalStateException("target position and starting position are equal");
			else if(maxDiffDist == null) throw new IllegalStateException("firstMoveToCall was not set to true"); 
			else speed = Math.abs(targetPose.subtracted(currentPose).magnitude())*setSpeed/((1-driveSpeed)*maxDiffDist);
			
			if(speed > setSpeed) speed = setSpeed;
			
			Angle driveAngle = new Angle(Math.atan2(targetPose.get(1)-currentPose.get(1), targetPose.get(0)-currentPose.get(0)), AngleUnit.RADIANS); 
			driveAtAngle(driveAngle.subtract(heading).subtract(headAngle));
			
			drive();
			
			speed = setSpeed;
			
			firstMoveToCall = false;
			return false;
		}
	}

	@Override
    public void drive () {
		super.drive();
        //scale the motor powers down so that the absolute values don't exceed 1

		//determine the maximum wheel power
		double maxi = Math.abs(wheelMotors[0].getCurrentPower());
		for(int i = 1; i < wheelMotors.length; i++){
		    if(Math.abs(wheelMotors[i].getCurrentPower()) > maxi){
		    	maxi = Math.abs(wheelMotors[i].getCurrentPower());
		    }
		}

        /*divide all the motor powers by the maximum power to preserve
        the ratios between the wheels while keeping the powers under 1*/
        if(maxi != 0){
            for(int i = 0; i < wheelMotors.length; i++){
                wheelMotors[i].resetPower(wheelMotors[i].getCurrentPower()*speed/maxi);
            }
        }

        //set the wheel motor powers
        for(int i = 0; i < wheelMotors.length; i++) {
            wheelMotors[i].run();
        }
    }
}
