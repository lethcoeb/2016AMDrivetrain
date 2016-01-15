package org.usfirst.frc.team1806.robot.subsystems;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.usfirst.frc.team1806.robot.Constants;
import org.usfirst.frc.team1806.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
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
    
    double chassisAngle;
    
    public DrivetrainSS(){
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
        
    }
    
    public void arcadeDrive(double power, double turn){
    	leftTalon.set(.5 * power + .5 *turn);
    	rightTalon.set(.5 * power -  .5 * turn);
    }
    
    public void tankDrive(double left, double right){
    	leftTalon.set(left);
    	rightTalon.set(right);
    }
    
    public void stop(){
    	leftTalon.set(0);
    	rightTalon.set(0);
    }
    
    //Getters here!
    public double getLeftPower(){
    	return leftTalon.get();
    }
    
    public double getRightPower(){
    	return rightTalon.get();
    }
    
    public double getRightEncoderDistance(){
    	return rightDriveEncoder.getDistance();
    }
    
    public double getLeftEncoderDistance(){
    	return leftDriveEncoder.getDistance();
    }
    
    public double getRightEncoderRate(){
    	return rightDriveEncoder.getRate();
    }
    
    public double getLeftEncoderRate(){
    	return leftDriveEncoder.getRate();
    }
    
    public void resetRightEncoder(){
    	rightDriveEncoder.reset();
    }
    
    public void resetLeftEncoder(){
    	leftDriveEncoder.reset();
    }
    
    public double getAngle(){
    	
    	//returns an angle from -180 to 180 with 0 being the forward face of the robot
    	
    	chassisAngle = navx.getAngle();
    	if(chassisAngle >= 0 && chassisAngle <= 180){
    		return chassisAngle;
    	}else{
    		return chassisAngle - 360;
    	}
    }
    
    public void resetAngle(){
    	navx.reset();
    }
    
    public double navxPitch(){
    	return navx.getPitch();
    }
    
    public double navxRoll(){
    	return navx.getRoll();
    }
    
    public double navxYaw(){
    	return navx.getYaw();
    }
    
    public boolean isCalibrating(){
    	return navx.isCalibrating();
    }
    
    public boolean isFlat(){
    	if(Math.abs(navx.getRoll()) < Constants.rollThreshold && Math.abs(navx.getPitch()) < Constants.pitchThreshold){
    		return true;
    	}else return false;
    }
    
    public void filterRoll(){
    	
    	rollSum = 0;
    	
    	filterRollArray.add(navxRoll());
    	
    	if (filterRollArray.size() >= Constants.filterEntryLimit){
    		//you've reached the max number of entries in the array so delete the first one
    		filterRollArray.remove(0);
    	}
    	
    	for(int i = 0; i< filterRollArray.size(); i++){
    		rollSum = rollSum + filterRollArray.get(i);
    	}
    }
    
    public double getRollAverage(){
    	
    	filterRoll();
    	
    	if(filterRollArray.size() != 0){
    		return rollSum/filterRollArray.size();
    	}else return navxRoll();
    }
    
    public void filterPitch(){
    	
    	pitchSum = 0;
    	
    	filterPitchArray.add(navxPitch());
    	
    	if (filterPitchArray.size() >= Constants.filterEntryLimit){
    		//you've reached the max number of entries in the array so delete the first one
    		filterPitchArray.remove(0);
    	}
    	
    	for(int i = 0; i< filterPitchArray.size(); i++){
    		pitchSum = pitchSum + filterPitchArray.get(i);
    	}
    }
    
    public double getPitchAverage(){
    	filterPitch();
    	
    	if(filterPitchArray.size() != 0){
    		return pitchSum/filterPitchArray.size();
    	}else return navxPitch();
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

