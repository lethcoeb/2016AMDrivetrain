package org.usfirst.frc.team1806.robot.commands;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoTurnToAngle extends Command {

	//in degrees
	private double m_targetAngle;
	
	private PIDSource ts;
	private PIDOutput to;
	private PIDController tc;
	
    public AutoTurnToAngle(double angle) {
        requires(Robot.dtSS);
        m_targetAngle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.dtSS.enableTurn();
    	Robot.dtSS.setTurnControllerSetpoint(m_targetAngle);
		
		
		
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.dtSS.isTurnControllerOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.dtSS.disableControllers();
    	Robot.dtSS.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.dtSS.disableControllers();
    	Robot.dtSS.stop();
    }
}
