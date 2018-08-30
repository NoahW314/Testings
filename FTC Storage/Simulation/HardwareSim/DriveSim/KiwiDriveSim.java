package org.firstinspires.ftc.teamcode.teamcalamari.Simulation.HardwareSim.DriveSim;

import org.firstinspires.ftc.teamcode.teamcalamari.Angle;
import org.firstinspires.ftc.teamcode.teamcalamari.OpModeType;
import org.firstinspires.ftc.teamcode.teamcalamari.Simulation.HardwareSim.MotorsSim.MotorSim;

public class KiwiDriveSim extends OmniWheelDriveSim {

	/**The wheel motors should be listed clockwise.
    A line drawn from the center of the robot to the first wheel motor listed indicates the positive x-axis.
    headOffset is measured in degrees and is measured counterclockwise from the positive x-axis.*/
	public KiwiDriveSim(MotorSim[] motors, Angle headOffset, OpModeType type) {
		super(motors, headOffset, type);
	}
	
	@Override
	public void driveAtAngle(Angle driveAngle) {
		//TODO: finish method
	}

}
