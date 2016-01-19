package org.usfirst.frc.team1806.robot;

public class States {
	
	public enum DrivetrainMode{
		ARCADE, TANK, TURNTOANGLE, POINTTOANGLE
	}
	
	public enum VisionTracking{
		ON, OFF
	}
	
	public enum VisionTrackingPointTo{
		ON, OFF
	}
	
	public static DrivetrainMode DrivetrainModeTracker;
	public static VisionTracking VisionTrackingTracker;
	public static VisionTrackingPointTo VisionTrackingPointToTracker;
	
	public void reset(){
		DrivetrainModeTracker = DrivetrainMode.ARCADE;
		VisionTrackingTracker = VisionTracking.OFF;
		VisionTrackingPointToTracker = VisionTrackingPointTo.OFF;
	}
	
}
