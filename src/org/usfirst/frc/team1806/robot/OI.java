package org.usfirst.frc.team1806.robot;

import java.lang.Thread.State;

import org.usfirst.frc.team1806.robot.States.DrivetrainMode;
import org.usfirst.frc.team1806.robot.commands.TurnToAngle;

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
	
	public double lsY = 0;
	public double rsX = 0;
	public double rsY = 0;
	public double RT = 0;
	public boolean A = false;

	private Latch arcadeTankLatch = new Latch();

	XboxController dc = new XboxController(0, Constants.joystickDeadzoneConstant, Constants.triggerDeadzoneConstant);

	public void run() {
		// put operator interface things here

		// read inputs
		updateInputs();

		if(RT > .5 && Robot.states.DrivetrainModeTracker != States.DrivetrainMode.TURNTOANGLE){
			Robot.states.DrivetrainModeTracker = States.DrivetrainMode.TURNTOANGLE;
			new TurnToAngle().start();
		}
		
		// Latch to flip between arcade drive mode and tank drive mode
		if (arcadeTankLatch.update(A) && Robot.states.DrivetrainModeTracker != States.DrivetrainMode.TURNTOANGLE) {
			if (Robot.states.DrivetrainModeTracker == States.DrivetrainMode.ARCADE) {
				Robot.states.DrivetrainModeTracker = States.DrivetrainMode.TANK;
			} else {
				Robot.states.DrivetrainModeTracker = States.DrivetrainMode.ARCADE;
			}
		}

		if (Robot.states.DrivetrainModeTracker == States.DrivetrainMode.ARCADE) {
			//DEADZONES ARE HANDLED IN XBOX CONTROLLER CLASS
			Robot.dtSS.arcadeDrive(lsY, rsX);
		}else if(Robot.states.DrivetrainModeTracker == States.DrivetrainMode.TANK){
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
		RT = dc.getRightTrigger();
	}
	
	public double getRsAngle(){
		//TODO check that this returns 0-360 deg like a unit circle
		return Math.atan(rsY/rsX);
	}
	
	public double getRsMagnitude(){
		return SWATLib.convertTo2DVector(rsX, rsY);
	}
	
	public double getRT(){
		return RT;
	}

}
