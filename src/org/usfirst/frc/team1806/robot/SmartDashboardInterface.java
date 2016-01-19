package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardInterface {

	public SmartDashboardInterface(){
		
	}
	
	public void update(){
		
		SmartDashboard.putNumber("rs magnitude", Robot.oi.getRsMagnitude());
		SmartDashboard.putNumber("rs angle", Robot.oi.getRsAngle());
		SmartDashboard.putNumber("navx angle", Robot.dtSS.getAngle());
		SmartDashboard.putString("drivetrain mode", String.valueOf(Robot.states.DrivetrainModeTracker));
		SmartDashboard.putNumber("pitch", Robot.dtSS.navxPitch());
		SmartDashboard.putNumber("roll", Robot.dtSS.navxRoll());
		SmartDashboard.putNumber("yaw", Robot.dtSS.navxYaw());
		SmartDashboard.putBoolean("Is flat?", Robot.dtSS.isFlat());
		SmartDashboard.putBoolean("is calibrating?", Robot.dtSS.isCalibrating());
		SmartDashboard.putNumber("filtered roll", Robot.dtSS.getRollAverage());
		SmartDashboard.putNumber("filtered pitch", Robot.dtSS.getPitchAverage());
		
		
		SmartDashboard.putString("Vision?", String.valueOf(Robot.states.VisionTrackingTracker));
	}
	
}
