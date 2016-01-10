package org.usfirst.frc.team1806.robot.commands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.States;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToAngle extends Command {

	private PIDSource ps;
	private PIDOutput po;
	private PIDController pc;
	
    public TurnToAngle() {
        requires(Robot.dtSS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.states.DrivetrainModeTracker = States.DrivetrainMode.TURNTOANGLE;
    	
    	ps = new PIDSource() {
			
			@Override
			public double pidGet() {
				// TODO Auto-generated method stub
				return Robot.dtSS.getAngle();
			}
		};
		
		po = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				// TODO Auto-generated method stub
				Robot.dtSS.arcadeDrive(0, output);
			}
		};
		
		pc = new PIDController(Constants.turningP, Constants.turningI, Constants.turningD, ps, po);
		pc.setInputRange(0, 360);
		pc.setOutputRange(-1, 1);
		pc.setContinuous(true);
		pc.enable();
		
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(Robot.oi.getRsMagnitude() > Constants.joystickDeadzoneConstant){
    		pc.setSetpoint(Robot.oi.getRsAngle());
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.oi.getRT() < .4;
    }

    // Called once after isFinished returns true
    protected void end() {
    	pc.disable();
    	Robot.dtSS.stop();
    	Robot.states.DrivetrainModeTracker = States.DrivetrainMode.ARCADE;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	pc.disable();
    	Robot.dtSS.stop();
    	Robot.states.DrivetrainModeTracker = States.DrivetrainMode.ARCADE;
    }
}
