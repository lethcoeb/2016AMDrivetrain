package org.usfirst.frc.team1806.robot.subsystems;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.Robot;
import org.usfirst.frc.team1806.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DrivetrainSS extends Subsystem {

	Talon leftTalon;
	Talon rightTalon;

	Encoder leftDriveEncoder;
	Encoder rightDriveEncoder;

	AHRS navx;

	private ArrayList<Double> filterRollArray;
	double rollSum;

	private ArrayList<Double> filterPitchArray;
	double pitchSum;

	private PIDSource driveSource;
	private PIDOutput driveOutput;
	private PIDController driveController;

	private PIDSource turnSource;
	private PIDOutput turnOutput;
	private double m_turn;
	private PIDController turnController;
	private boolean turnOnly;

	public DrivetrainSS() {
		leftTalon = new Talon(RobotMap.leftMotor);
		rightTalon = new Talon(RobotMap.rightMotor);
		rightTalon.setInverted(true);

		leftDriveEncoder = new Encoder(RobotMap.leftEncA, RobotMap.leftEncB);
		rightDriveEncoder = new Encoder(RobotMap.rightEncA, RobotMap.rightEncB);
		leftDriveEncoder.setDistancePerPulse(Constants.distancePerPulse);
		rightDriveEncoder.setDistancePerPulse(Constants.distancePerPulse);

		navx = new AHRS(SerialPort.Port.kMXP);

		filterRollArray = new ArrayList<Double>();
		filterPitchArray = new ArrayList<Double>();

		driveSource = new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				setPIDSourceType(PIDSourceType.kDisplacement);

			}

			@Override
			public double pidGet() {
				// TODO Auto-generated method stub
				return (Robot.dtSS.getLeftEncoderDistance() + Robot.dtSS.getRightEncoderDistance()) / 2;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		driveOutput = new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				// TODO implement turn controller
				Robot.dtSS.arcadeDrive(output, m_turn);
			}
		};

		driveController = new PIDController(Constants.driveP, Constants.driveI, Constants.driveD, driveSource,
				driveOutput);

		turnSource = new PIDSource() {

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

		turnOutput = new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				//this if else statement enables this pid controller to be uses as both a correction tool for
				//the driving straight controller AND as a controller for just a turn
				if(turnOnly){
					arcadeDrive(0, output);
				}else{
					m_turn = output;
				}
			}
		};

		turnController = new PIDController(Constants.turningP, Constants.turningI, Constants.turningD, turnSource, turnOutput);

		
		driveController.setAbsoluteTolerance(Constants.drivePIDTolerance);
		turnController.setAbsoluteTolerance(Constants.turnPIDTolerance);
		driveController.setOutputRange(-1, 1);
		turnController.setInputRange(-180, 180);
		turnController.setOutputRange(-1, 1);
		
		m_turn = 0;
		turnOnly = false;
	}

	public void arcadeDrive(double power, double turn) {
		leftTalon.set(.5 * power + .5 * turn);
		rightTalon.set(.5 * power - .5 * turn);
	}

	public void tankDrive(double left, double right) {
		leftTalon.set(left);
		rightTalon.set(right);
	}

	public void stop() {
		leftTalon.set(0);
		rightTalon.set(0);
	}

	// Getters here!
	public double getLeftPower() {
		return leftTalon.get();
	}

	public double getRightPower() {
		return rightTalon.get();
	}

	public double getRightEncoderDistance() {
		return rightDriveEncoder.getDistance();
	}

	public double getLeftEncoderDistance() {
		return leftDriveEncoder.getDistance();
	}

	public double getRightEncoderRate() {
		return rightDriveEncoder.getRate();
	}

	public double getLeftEncoderRate() {
		return leftDriveEncoder.getRate();
	}

	public void resetRightEncoder() {
		rightDriveEncoder.reset();
	}

	public void resetLeftEncoder() {
		leftDriveEncoder.reset();
	}

	public double getAngle() {
		return navx.getAngle();
	}

	public void resetAngle() {
		navx.reset();
	}

	public double navxPitch() {
		return navx.getPitch();
	}

	public double navxRoll() {
		return navx.getRoll();
	}

	public double navxYaw() {
		return navx.getYaw();
	}

	public double accelZAxis() {
		// TODO: is this up and down?
		return navx.getWorldLinearAccelZ();
	}

	public boolean isCalibrating() {
		return navx.isCalibrating();
	}

	public boolean isFlat() {
		if (Math.abs(navx.getRoll()) < Constants.rollThreshold
				&& Math.abs(navx.getPitch()) < Constants.pitchThreshold) {
			return true;
		} else
			return false;
	}

	public void zeroYaw(){
		navx.zeroYaw();
	}
	
	public void filterRoll() {

		rollSum = 0;

		filterRollArray.add(navxRoll());

		if (filterRollArray.size() >= Constants.filterEntryLimit) {
			// you've reached the max number of entries in the array so delete
			// the first one
			filterRollArray.remove(0);
		}

		for (int i = 0; i < filterRollArray.size(); i++) {
			rollSum = rollSum + filterRollArray.get(i);
		}
	}

	public double getRollAverage() {

		filterRoll();

		if (filterRollArray.size() != 0) {
			return rollSum / filterRollArray.size();
		} else
			return navxRoll();
	}

	public void filterPitch() {

		pitchSum = 0;

		filterPitchArray.add(navxPitch());

		if (filterPitchArray.size() >= Constants.filterEntryLimit) {
			// you've reached the max number of entries in the array so delete
			// the first one
			filterPitchArray.remove(0);
		}

		for (int i = 0; i < filterPitchArray.size(); i++) {
			pitchSum = pitchSum + filterPitchArray.get(i);
		}
	}

	public double getPitchAverage() {
		filterPitch();

		if (filterPitchArray.size() != 0) {
			return pitchSum / filterPitchArray.size();
		} else
			return navxPitch();
	}
	
	public void setTurnOnly(boolean onOff){
		turnOnly = onOff;
	}
	
	public void enableDriveStraight(){
		if(turnOnly){
			turnOnly = false;
		}
		
		driveController.enable();
		turnController.enable();
		zeroYaw();
		turnController.setSetpoint(0);
	}
	
	public void enableTurn(){
		if(driveController.isEnabled()){
			driveController.disable();
		}
		
		turnOnly = true;
		turnController.enable();
	}
	
	public void setDriveControllerSetpoint(double setpoint){
		driveController.setSetpoint(setpoint);
	}
	
	public void setTurnControllerSetpoint(double setpoint){
		turnController.setSetpoint(setpoint);
	}
	
	public boolean isDriveControllerOnTarget(){
		return driveController.onTarget();
	}
	
	public boolean isTurnControllerOnTarget(){
		return turnController.onTarget();
	}
	
	public void disableControllers(){
		if(driveController.isEnabled()){
			driveController.disable();
		}
		
		if(turnController.isEnabled()){
			turnController.disable();
		}

	}
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
