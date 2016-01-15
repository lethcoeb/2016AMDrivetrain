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
    	
    	Robot.dtSS.resetAngle();
    	
    	ts = new PIDSource() {
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				setPIDSourceType(PIDSourceType.kDisplacement);
			}
			
			@Override
			public double pidGet() {
				// TODO Auto-generated method stub
				return Robot.dtSS.navxYaw();
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		to = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				// TODO Auto-generated method stub
				Robot.dtSS.arcadeDrive(0, output);
			}
		};
		
		tc = new PIDController(Constants.turningP, Constants.turningI, Constants.turningD, ts, to);
		tc.setAbsoluteTolerance(Constants.turnPIDTolerance);
		tc.setOutputRange(-1, 1);
		tc.enable();
		tc.setSetpoint(m_targetAngle);
		
		
		
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return tc.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	tc.disable();
    	Robot.dtSS.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	tc.disable();
    	Robot.dtSS.stop();
    }
}
