package org.usfirst.frc.team1806.robot;

import edu.wpi.first.wpilibj.buttons.Button;

public class OI {
	
	public double lsY = 0;
	public double rsX = 0;
	
	XboxController dc = new XboxController(0);
	
	public void update(){
		//put operator interface things here
		
		lsY = dc.getLeftJoyY();
		rsX = dc.getRightJoyX();
		
		Robot.dtSS.execute(lsY, rsX);
		
	}
	
}

