package org.usfirst.frc.team1806.robot.subsystems;

import org.usfirst.frc.team1806.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DrivetrainSS extends Subsystem {
    
    Talon leftTalon = new Talon(RobotMap.leftMotor);
    Talon rightTalon = new Talon(RobotMap.rightMotor);
    
    public void execute(double power, double turn){
    	leftTalon.set(power + turn);
    	rightTalon.set(power - turn);
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
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

