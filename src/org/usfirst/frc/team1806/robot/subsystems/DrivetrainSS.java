package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DrivetrainSS extends Subsystem {
    
    Talon leftTalon;
    Talon rightTalon;
    AHRS navx;
    
    public DrivetrainSS(){
    	leftTalon = new Talon(RobotMap.leftMotor);
        rightTalon = new Talon(RobotMap.rightMotor);
        rightTalon.setInverted(true);
        navx = new AHRS(SerialPort.Port.kMXP);
        
    }
    
    public void arcadeDrive(double power, double turn){
    	leftTalon.set(power + turn);
    	rightTalon.set(power - turn);
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
    
    public double getAngle(){
    	return navx.getAngle();
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

