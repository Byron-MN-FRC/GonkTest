/**
 * Simple class containing constants used throughout project
 */

package frc.robot;

import java.util.HashMap;
import java.util.Map;

//import org.usfirst.frc4859.Rover.Gains;

public class Constants {
	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/**
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int kTimeoutMs = 30;

	/**
	 * Gains used in Motion Magic, to be adjusted accordingly
     * Gains(kp, ki, kd, kf, izone, peak output);
     */
	public static final Gains kGains = new Gains(0.2, 0.001, 0.0, 0.2, 0, 1.0);
	
	// Speeds for catapult & aquisition
	public static final double kUpSpeed = .7;
	public static final double kUpTime = .45;
	public static final double kDownTime = .5;
	public static final double kDownSpeed = .15; 
	public static final double kBallAquireSpeed = -.6 ;
	public static final double kBallAquireRetractSpeed = -.25;

	public static boolean currentDriveCamMode = true;

	
	  // Catapult Speed and Time
	  public static final Map<String, double[]> catapultVariables = new HashMap<String, double[]> () {

		private static final long serialVersionUID = 1L;
	
		{	   //name                              USpeed  UTime  DSpeed  DTime  EnCoder
			put("CargoBall",        new double[]  { .7   , .65  , .15   , .5  , 8500 } );
			put("NoLift",  	       	new double[]  { .7   , .45  , .15   , .5  , 0 } );
			put("MidRocket",    	new double[]  { .8   , .90  , .15   , .5  , 25000 } );
		//  put("MidRocketHatch",   new double[]  { .7   , .90  , .15   , .5  , 27000 } );
		}};
}