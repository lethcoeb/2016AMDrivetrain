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
		
	}
	
}
