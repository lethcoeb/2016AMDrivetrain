package org.usfirst.frc.team1806.robot;

import java.lang.Thread.State;

import org.usfirst.frc.team1806.robot.States.DrivetrainMode;
import org.usfirst.frc.team1806.robot.commands.TurnToAngleDriverInput;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Scheduler;
import util.utilityClasses.Latch;
import util.utilityClasses.SWATLib;

public class OI {

	/*
	 * 
	 * RT - enter turn to angle mode
	 * A - toggle between arcade and tank
	 * 
	 */
	
	public double lsY;
	public double rsX;
	public double rsY;
	public double RT;
	public double LT;
	public boolean A;
	public boolean X;

	private Latch arcadeTankLatch;

	public XboxController dc;
	
	public OI(){
		
		lsY = 0;
		rsX = 0;
		rsY = 0;
		RT = 0;
		RT = 0;
		A = false;
		X = false;
		
		arcadeTankLatch = new Latch();
		dc = new XboxController(0, Constants.joystickDeadzoneConstant, Constants.triggerDeadzoneConstant);
		
	}

	public void run() {
		// put operator interface things here

		// read inputs
		updateInputs();

		if(RT > .5 && Robot.states.DrivetrainModeTracker != States.DrivetrainMode.TURNTOANGLE){
			Robot.states.DrivetrainModeTracker = States.DrivetrainMode.TURNTOANGLE;
			new TurnToAngleDriverInput().start();
		}
		
		// Latch to flip between arcade drive mode and tank drive mode
		if (arcadeTankLatch.update(A) && Robot.states.DrivetrainModeTracker != States.DrivetrainMode.TURNTOANGLE) {
			if (Robot.states.DrivetrainModeTracker == States.DrivetrainMode.ARCADE) {
				Robot.states.DrivetrainModeTracker = States.DrivetrainMode.TANK;
			} else {
				Robot.states.DrivetrainModeTracker = States.DrivetrainMode.ARCADE;
			}
		}
		
		if(X){
			Robot.dtSS.resetAngle();
		}

		if (Robot.states.DrivetrainModeTracker == States.DrivetrainMode.ARCADE) {
			//DEADZONES ARE HANDLED IN XBOX CONTROLLER CLASS
			if(Math.abs(lsY) < Constants.joystickDeadzoneConstant){
				lsY = 0;
			}
			
			if(Math.abs(rsX) < Constants.joystickDeadzoneConstant){
				rsX = 0;
			}
			
			Robot.dtSS.arcadeDrive(lsY, rsX);
		}else if(Robot.states.DrivetrainModeTracker == States.DrivetrainMode.TANK){
			
			if(Math.abs(lsY) < Constants.joystickDeadzoneConstant){
				lsY = 0;
			}
			
			if(Math.abs(rsY) < Constants.joystickDeadzoneConstant){
				rsY = 0;
			}
			
			Robot.dtSS.tankDrive(lsY, rsY);
		}else if(Robot.states.DrivetrainModeTracker == States.DrivetrainMode.TURNTOANGLE){
			//do nuttin bc its handled by the TurnToAngle command
		}
	}
	
	public void updateInputs(){
		lsY = dc.getLeftJoyY();
		rsX = dc.getRightJoyX();
		rsY = dc.getRightJoyY();
		A = dc.getButtonA();
		X = dc.getButtonX();
		RT = dc.getRightTrigger();
		LT = dc.getLeftTrigger();
	}
	
	public double getRsAngle(){
		//TODO needs to return -180 to 180 with 0 being up, 90 being 1 x 0 y
		double angle = Math.atan(rsX/rsY) * 180/Math.PI;
		
		if(rsX == 0){
			if(rsY > 0){
				return 0;
			}else{
				return 180;
			}
		}else if(rsY == 0){
			if(rsX > 0){
				return 90;
			}else{
				return -90;
			}
		}else if(rsY > 0) {
			//quadrants 1 and 2
			return angle;
		}else if(rsX > 0){
			//quadrant 4
			return angle + 180;
		}else{
			//quadrant 3
			return angle - 180;
		}
	}
	
	public double getRsMagnitude(){
		return SWATLib.convertTo2DVector(rsX, rsY);
	}

}
