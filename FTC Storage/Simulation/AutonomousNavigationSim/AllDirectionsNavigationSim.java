package org.firstinspires.ftc.teamcode.teamcalamari.Simulation.AutonomousNavigationSim;

import java.util.ArrayList;

import org.firstinspires.ftc.teamcode.teamcalamari.Angle;
import org.firstinspires.ftc.teamcode.teamcalamari.Simulation.HardwareSim.DriveSim.AllDirectionsDriveSim;
import org.firstinspires.ftc.teamcode.teamcalamari.Simulation.OpModeSim.OpModeSim;

public class AllDirectionsNavigationSim extends AutonomousNavigationSim {
	
	/**The speeds at which the robot turns throughout the routine.
	Corresponds to turnSpeed in the drivetrain.
	Element <code>n</code> of <code>turnSpeeds</code> is the speed when the robot
	 is moving from element <code>n</code> to element <code>n+1</code> in <code>targets</code>*/
	public ArrayList<Double> turnSpeeds;
	
	/**The angles at which the robot should be trying to turn to while moving between targets.
	Values of <code>null</code> indicate that the robot should maintain the last orientation that it was set to.
	Element <code>n</code> of <code>drivingTurns</code> is the angle when the robot
	 is moving from element <code>n</code> to element <code>n+1</code> in <code>targets</code>*/
	public ArrayList<Angle> drivingTurns;

	public AllDirectionsNavigationSim(int targetNumber, double dSpeed, double dDriveSpeed, double dStatTurnSpeed, double dStatTurnTurnSpeed, double dMoveTurnSpeed, OpModeSim opMode) {
		super(targetNumber, dSpeed, dDriveSpeed, dStatTurnSpeed, dStatTurnTurnSpeed, opMode);
		setDefaultArrayListValues(dMoveTurnSpeed);
	}
	
	protected void setDefaultArrayListValues(double dMoveTurnSpeed) {
		turnSpeeds = setDefaultArrayListValue(dMoveTurnSpeed, targetNumber-1);
		drivingTurns  = setDefaultArrayListValue(null, targetNumber-1);
	}
	
	@Override
	public void runDriving() {
		if(i >= targets.size()) {
			robotAction = RobotActions.FINISHED;
		}
		else {
			if(drivingActions.get(i-1) != null) {
				if(firstDrive) {
					if(!drivingActions.get(i-1).start()) {
						drivingActions.get(i-1).act();
					}
					firstDrive = false;
				}
				else {
					drivingActions.get(i-1).act();
				}
			}
			
			if(turnSpeeds.get(i-1) != null) drive.turnSpeed = turnSpeeds.get(i-1);
			else throw new NullPointerException("Autonomous Navigation turn speed cannot be null");
			if(driveSpeeds.get(i-1) != null) drive.driveSpeed = driveSpeeds.get(i-1);
			else throw new NullPointerException("Autonomous Navigation drive speed cannot be null");
			if(speeds.get(i-1) != null) drive.speed = speeds.get(i-1);
			else throw new NullPointerException("Autonomous Navigation speed cannot be null");
			
			if(heading == null) throw new IllegalStateException("The heading must be updated and AutonomousNavigation.initialize must be called before values of heading can be accessed");
			if(prevSetHeading == null) throw new IllegalStateException("AutonomousNavigation.initialize must be called before calling AutonomousNavigation.run");
			if(position == null) throw new NullPointerException("AutonomousNavigation.updatePosition must be called before calling AutonomousNavigation.run");
			if(targets.get(i) == null) throw new NullPointerException("Autonomous Navigation target cannot be null");
			
			if(drivingTurns.get(i-1) == null) {
				if(drive.driveTo(position, targets.get(i), heading)) {
					firstDrive = true;
					if(actionFirst.get(i)) {robotAction = RobotActions.ACTION;}
					else {robotAction = RobotActions.STATIONARY_TURN;}
				}
			}
			else{
				prevSetHeading = drivingTurns.get(i-1);
			
				if(((AllDirectionsDriveSim)drive).turnDriveTo(position, targets.get(i), heading, prevSetHeading)) {
					if(actionFirst.get(i)) {robotAction = RobotActions.ACTION;}
					else {robotAction = RobotActions.STATIONARY_TURN;}
				}
			}
		}
	}
	
}
