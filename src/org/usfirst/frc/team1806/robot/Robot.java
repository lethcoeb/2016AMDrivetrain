
package org.usfirst.frc.team1806.robot;

import java.awt.Frame;

import org.usfirst.frc.team1806.robot.subsystems.DrivetrainSS;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.vision.AxisCamera;

public class Robot extends IterativeRobot {

	//Subsystems
	public static DrivetrainSS dtSS;
	
	//States
	public static States states;
	
	public static SmartDashboardInterface sdi;
	
	public static OI oi;

	public static VisionThread vt;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	//Instantiate objects
    	dtSS = new DrivetrainSS();
    	
    	states = new States();
    	states.reset();
    	
    	sdi = new SmartDashboardInterface();
    	
		oi = new OI();
		
		vt = new VisionThread();
        // instantiate the command used for the autonomous period
		
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		oi.updateInputs();
		sdi.update();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        //if (autonomousCommand != null) autonomousCommand.cancel();
    	
    	vt.start();
    	
    }

    public void disabledInit(){

    }
    
    public void teleopPeriodic() {
    	
    	Scheduler.getInstance().run();
    	
    	oi.run();
    	
        sdi.update();
        
    }

    public void testPeriodic() {
        LiveWindow.run();
    }
}
