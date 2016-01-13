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
    	Robot.dtSS.resetLeftEncoder();
    	Robot.dtSS.resetRightEncoder();
    	Robot.dtSS.resetAngle();
    	
    	ps = new PIDSource() {
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				setPIDSourceType(PIDSourceType.kDisplacement);
				
			}
			
			@Override
			public double pidGet() {
				// TODO Auto-generated method stub
				return (Robot.dtSS.getLeftEncoderDistance() + Robot.dtSS.getRightEncoderDistance())/2 ;
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		po = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				// TODO implement turn controller
				Robot.dtSS.arcadeDrive(output, m_turn);
			}
		};
		
		pc = new PIDController(Constants.driveP, Constants.driveI, Constants.driveD, ps, po);
		
		ps_turn = new PIDSource() {
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				setPIDSourceType(PIDSourceType.kDisplacement);
			}
			
			@Override
			public double pidGet() {
				// TODO Auto-generated method stub
				return Robot.dtSS.getAngle();
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		po_turn = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				// TODO Auto-generated method stub
				m_turn = output;
			}
		};
		
		pc_turn = new PIDController(Constants.turningP, Constants.turningI, Constants.turningD, ps_turn, po_turn);
		pc_turn.setOutputRange(-1, 1);
		pc.enable();
		pc.setSetpoint(0);
		
		pc.setOutputRange(-1, 1);
		pc.setAbsoluteTolerance(Constants.drivePIDTolerance);
		pc.enable();
		pc.setSetpoint(m_targetDistance);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pc.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	pc_turn.disable();
    	pc.disable();
    	Robot.dtSS.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	pc_turn.disable();
    	pc.disable();
    	Robot.dtSS.stop();
    }
}
