package org.usfirst.frc.team1806.robot;

public class Constants {
	
	
	//deadzones for inputs (out of 1)
	public static final double joystickDeadzoneConstant = .25;
	public static final double triggerDeadzoneConstant = .1;
	
	
	//threshold to report if the robot is tilted in degrees
	public static final double pitchThreshold = 10;
	public static final double rollThreshold = 10;
	
	//entry limit for navx filtering
	public static final int filterEntryLimit = 15;
	
	//drive straight pid
	public static final double driveP = .1;
	public static final double driveI = 0;
	public static final double driveD = 0;
	
		//should be in inches
	public static final double drivePIDTolerance = 1;
	
	//turning pid
	public static final double turningP = .01;
	public static final double turningI = 0;
	public static final double turningD = 0;
	
		//should be in degrees
	public static final double turnPIDTolerance = 2;
	
	//distance per pulse for encoders
	//FIXME: make this a real value
	public static final double distancePerPulse = .1;
	
}
