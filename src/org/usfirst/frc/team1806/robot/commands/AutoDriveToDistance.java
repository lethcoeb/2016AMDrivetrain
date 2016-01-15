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
public class AutoDriveToDistance extends Command {

	private double m_targetDistance;
	
	private double m_turn;
	
	private PIDSource ps;
	private PIDOutput po;
	private PIDController pc;
	
	private PIDSource ps_turn;
	private PIDOutput po_turn;
	private PIDController pc_turn;
	
    public AutoDriveToDistance(double distance) {
        requires(Robot.dtSS);
        m_targetDistance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.dtSS.zeroYaw();
    	Robot.dtSS.resetLeftEncoder();
    	Robot.dtSS.resetRightEncoder();
    	Robot.dtSS.enableDriveStraight();
    	Robot.dtSS.setDriveControllerSetpoint(m_targetDistance);
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.dtSS.isDriveControllerOnTarget();
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
