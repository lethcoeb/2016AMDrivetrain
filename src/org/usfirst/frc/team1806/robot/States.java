package org.usfirst.frc.team1806.robot;

public class States {
	
	public enum DrivetrainMode{
		ARCADE, TANK, TURNTOANGLE
	}
	
	public static DrivetrainMode DrivetrainModeTracker;
	
	public void reset(){
		DrivetrainModeTracker = DrivetrainMode.ARCADE;
	}
	
}
