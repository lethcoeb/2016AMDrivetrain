package org.usfirst.frc.team1806.robot.commands;

import org.usfirst.frc.team1806.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrossRoughDefense extends Command {

	//Command to cross a 'rough' defense autonomously. Uses an aggressive turning PID loop to compensate for misdirection
	
	double m_speed;
	
	PIDSource ps;
	PIDOutput po;
	PIDController pc;
	
    public CrossRoughDefense(double speed) {
        requires(Robot.dtSS);
        m_speed = speed;
        
        ps = new PIDSource() {
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				setPIDSourceType(PIDSourceType.kDisplacement);
			}
			
			@Override
			public double pidGet() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return null;
			}
		};
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
