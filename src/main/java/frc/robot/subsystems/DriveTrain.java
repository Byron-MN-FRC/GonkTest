// RobotBuilder Version: 2.0
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.DriveWithJoystick;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_TalonSRX pigeonTalon;
private WPI_TalonFX rightMaster;
private WPI_TalonFX rightFollower;
private SpeedControllerGroup rightSpeedControllerGroup;
private WPI_TalonFX leftMaster;
private WPI_TalonFX leftFollower;
private SpeedControllerGroup leftSpeedControllerGroup;
private DifferentialDrive tankDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private PigeonIMU pigeon; 

    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
pigeonTalon = new WPI_TalonSRX(15);


        
rightMaster = new WPI_TalonFX(1);


        
rightFollower = new WPI_TalonFX(0);


        
rightSpeedControllerGroup = new SpeedControllerGroup(rightMaster, rightFollower  );
addChild("RightSpeedControllerGroup",rightSpeedControllerGroup);

        
leftMaster = new WPI_TalonFX(3);


        
leftFollower = new WPI_TalonFX(2);


        
leftSpeedControllerGroup = new SpeedControllerGroup(leftMaster, leftFollower  );
addChild("LeftSpeedControllerGroup",leftSpeedControllerGroup);

        
tankDrive = new DifferentialDrive(leftSpeedControllerGroup, rightSpeedControllerGroup);
addChild("TankDrive",tankDrive);
tankDrive.setSafetyEnabled(false);
tankDrive.setExpiration(0.1);
tankDrive.setMaxOutput(1.0);

        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        pigeon = new PigeonIMU(pigeonTalon); 
    //    config();
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new DriveWithJoystick());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        SmartDashboard.putNumber("Left encoder", leftMaster.getSelectedSensorPosition());
        SmartDashboard.putNumber("Left velocity", leftMaster.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Right encoder", rightMaster.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right velocity", rightMaster.getSelectedSensorVelocity());
        PigeonIMU.GeneralStatus genStatus = new PigeonIMU.GeneralStatus();
		PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
		double [] xyz_dps = new double [3];
		/* grab some input data from Pigeon and gamepad*/
		pigeon.getGeneralStatus(genStatus);
		pigeon.getRawGyro(xyz_dps);
		pigeon.getFusedHeading(fusionStatus);
//		double currentAngularRate = xyz_dps[2];
        SmartDashboard.putBoolean("reading?", (pigeon.getState() == PigeonIMU.PigeonState.Ready) ? true : false);
        SmartDashboard.putNumber("angle", fusionStatus.heading);
 
        double [] ypr = new double[3];
        pigeon.getYawPitchRoll(ypr);
        SmartDashboard.putNumber("Yaw", ypr[0]);
     }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    double limitedJoystick = 0;
    private double y = 0;
	private double twist = 0;


    public void driveforward(Joystick joystickP0) {
		y = -joystickP0.getY();
        twist = joystickP0.getTwist();
       // double change = y - limitedJoystick; 
       // if (change > 0.01) {change = 0.01;}
        //else if (change < -0.01) {change = -0.01;}
        //limitedJoystick += change;
        twist = 0;
        tankDrive.arcadeDrive(y, twist);    
     }
   

    

    public void config() {

        /* Disable all motor contollers */
        leftMaster.set(ControlMode.PercentOutput, 0);
        leftFollower.set(ControlMode.PercentOutput, 0);
        rightMaster.set(ControlMode.PercentOutput, 0);
        rightFollower.set(ControlMode.PercentOutput, 0);

        /* Factory default hardware to prevent unexpected behavior */
        leftMaster.configFactoryDefault();
        leftFollower.configFactoryDefault();
        rightMaster.configFactoryDefault();
        rightFollower.configFactoryDefault();
        pigeon.configFactoryDefault();

        /* Set Neutral Mode */
		leftMaster.setNeutralMode(NeutralMode.Brake);
        rightMaster.setNeutralMode(NeutralMode.Brake);
        leftFollower.setNeutralMode(NeutralMode.Brake);
        rightFollower.setNeutralMode(NeutralMode.Brake);
        
         /** Feedback Sensor Configuration */
		
		/* Configure the left Talon's selected sensor as local IntegratedSensor */
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 
                                                Constants.PID_PRIMARY, 
                                                Constants.kTimeoutMs); // Select Sensor (Encoder)
  
        /* Configure the Remote Talon's selected sensor as a remote sensor for the right Talon */
        rightMaster.configRemoteFeedbackFilter(leftMaster.getDeviceID(), 
                                               RemoteSensorSource.TalonSRX_SelectedSensor, 
                                               Constants.REMOTE_0, 
                                               Constants.kTimeoutMs);		

        /* Configure the Pigeon IMU to the other remote slot available on the right Talon */
        rightMaster.configRemoteFeedbackFilter(pigeon.getDeviceID() , 
                                                RemoteSensorSource.GadgeteerPigeon_Yaw,
                                             //  RemoteSensorSource.Pigeon_Yaw, 
                                               Constants.REMOTE_1, 
                                               Constants.kTimeoutMs);

        /* Setup Sum signal to be used for Distance */
        rightMaster.configSensorTerm(SensorTerm.Sum0, 
                                     FeedbackDevice.RemoteSensor0, 
                                     Constants.kTimeoutMs);		// Feedback Device of Remote Talon
        rightMaster.configSensorTerm(SensorTerm.Sum1, 
                                     FeedbackDevice.IntegratedSensor, 
                                     Constants.kTimeoutMs);	// Integrated Encoder of current Talon

        /* Configure Sum [Sum of both Integrated Encoders] to be used for Primary PID Index */
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, 
                                                Constants.PID_PRIMARY, 
                                                Constants.kTimeoutMs);

        /* Scale Feedback by 0.5 to half the sum of Distance */
        rightMaster.configSelectedFeedbackCoefficient(0.5,
                                                      Constants.PID_PRIMARY, 
                                                      Constants.kTimeoutMs);

        /* Configure Remote 1 [Pigeon IMU's Yaw] to be used for Auxiliary PID Index */
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, 
                                                Constants.PID_TURN, 
                                                Constants.kTimeoutMs);

        /* Scale the Feedback Sensor using a coefficient */
        rightMaster.configSelectedFeedbackCoefficient(1, 
                                                      Constants.PID_TURN, 
                                                      Constants.kTimeoutMs);


        /* Invert right side */
        rightMaster.setInverted(true);
        rightFollower.setInverted(true);
        
         /* Set relevant frame periods to be at least as fast as periodic rate & don't have stale data */
      rightMaster.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, Constants.kTimeoutMs);
      rightMaster.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeoutMs);
      rightMaster.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, Constants.kTimeoutMs);
      rightMaster.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20, Constants.kTimeoutMs);
      leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, Constants.kTimeoutMs);
      pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR , 5, Constants.kTimeoutMs);

      /* Configure neutral deadband */
      rightMaster.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);
      leftMaster.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);    

      /* Motion Magic Configurations */
      rightMaster.configMotionAcceleration(2000, Constants.kTimeoutMs);
      rightMaster.configMotionCruiseVelocity(2000, Constants.kTimeoutMs);

      /**
      * Max out the peak output (for all modes).  
      * However you can limit the output of a given PID object with configClosedLoopPeakOutput().
      */
      leftMaster.configPeakOutputForward(+1.0, Constants.kTimeoutMs);
      leftMaster.configPeakOutputReverse(-1.0, Constants.kTimeoutMs);
      rightMaster.configPeakOutputForward(+1.0, Constants.kTimeoutMs);
      rightMaster.configPeakOutputReverse(-1.0, Constants.kTimeoutMs);
      leftFollower.configPeakOutputForward(+1.0, Constants.kTimeoutMs);
      leftFollower.configPeakOutputReverse(-1.0, Constants.kTimeoutMs);
      rightFollower.configPeakOutputForward(+1.0, Constants.kTimeoutMs);
      rightFollower.configPeakOutputReverse(-1.0, Constants.kTimeoutMs);

   
      /* FPID Gains for distance servo */
      rightMaster.config_kP(Constants.kSlot_Distanc, Constants.kGains_Distanc.kP, Constants.kTimeoutMs);
      rightMaster.config_kI(Constants.kSlot_Distanc, Constants.kGains_Distanc.kI, Constants.kTimeoutMs);
      rightMaster.config_kD(Constants.kSlot_Distanc, Constants.kGains_Distanc.kD, Constants.kTimeoutMs);
      rightMaster.config_kF(Constants.kSlot_Distanc, Constants.kGains_Distanc.kF, Constants.kTimeoutMs);
      rightMaster.config_IntegralZone(Constants.kSlot_Distanc, Constants.kGains_Distanc.kIzone, Constants.kTimeoutMs);
      rightMaster.configClosedLoopPeakOutput(Constants.kSlot_Distanc, Constants.kGains_Distanc.kPeakOutput, Constants.kTimeoutMs);

      /* FPID Gains for turn servo */
      rightMaster.config_kP(Constants.kSlot_Turning, Constants.kGains_Turning.kP, Constants.kTimeoutMs);
      rightMaster.config_kI(Constants.kSlot_Turning, Constants.kGains_Turning.kI, Constants.kTimeoutMs);
      rightMaster.config_kD(Constants.kSlot_Turning, Constants.kGains_Turning.kD, Constants.kTimeoutMs);
      rightMaster.config_kF(Constants.kSlot_Turning, Constants.kGains_Turning.kF, Constants.kTimeoutMs);
      rightMaster.config_IntegralZone(Constants.kSlot_Turning, Constants.kGains_Turning.kIzone, Constants.kTimeoutMs);
      rightMaster.configClosedLoopPeakOutput(Constants.kSlot_Turning, Constants.kGains_Turning.kPeakOutput, Constants.kTimeoutMs);

    
        /* Set Motion Magic gains in slot0 - see documentation */
        leftMaster.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
        leftMaster.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
        leftMaster.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
        leftMaster.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
        leftMaster.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);
        rightMaster.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
        rightMaster.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
        rightMaster.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
        rightMaster.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
        rightMaster.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);
    
        /**
        * 1ms per loop.  PID loop can be slowed down if need be.
        * For example,
        * - if sensor updates are too slow
        * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
        * - sensor movement is very slow causing the derivative error to be near zero.
        */
        int closedLoopTimeMs = 1;
        rightMaster.configClosedLoopPeriod(0, closedLoopTimeMs, Constants.kTimeoutMs);
        rightMaster.configClosedLoopPeriod(1, closedLoopTimeMs, Constants.kTimeoutMs);

        /**
        * configAuxPIDPolarity(boolean invert, int timeoutMs)
        * false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
        * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
        */
        rightMaster.configAuxPIDPolarity(false, Constants.kTimeoutMs);

          
        /* WPI drivetrain classes assume by default left & right are opposite */
        /* - call this to apply + to both sides when moving forward           */
        tankDrive.setRightSideInverted(false);
       
       
        /* Initialize */
 	
        /* Determine which slot affects which PID */
		rightMaster.selectProfileSlot(Constants.kSlot_Distanc, Constants.PID_PRIMARY);
		rightMaster.selectProfileSlot(Constants.kSlot_Turning, Constants.PID_TURN);

        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_Targets, 10);
        zeroSensors();

    }

    /** Zero all sensors, both Talons and Pigeon */
	public void zeroSensors() {
		leftMaster.getSensorCollection().setIntegratedSensorPosition(0, Constants.kTimeoutMs);
		rightMaster.getSensorCollection().setIntegratedSensorPosition(0, Constants.kTimeoutMs);
		pigeon.setYaw(0, Constants.kTimeoutMs);
		pigeon.setAccumZAngle(0, Constants.kTimeoutMs);
		System.out.println("Itegrated Encoders + Pigeon] All sensors are zeroed.\n");
	}
	/** Zero Integrated Encoders, used to reset position when initializing Motion Magic */
	private void zeroDistance(){
		leftMaster.getSensorCollection().setIntegratedSensorPosition(0, Constants.kTimeoutMs);
		rightMaster.getSensorCollection().setIntegratedSensorPosition(0, Constants.kTimeoutMs);
		System.out.println("[Integrated Encoders] All encoders are zeroed.\n");
	}
	
	/** Deadband 5 percent, used on the gamepad --NEED TO USE */
	private double Deadband(double value) {
		/* Upper deadband */
		if (value >= +0.05) 
			return value;
		
		/* Lower deadband */
		if (value <= -0.05)
			return value;
		
		/* Outside deadband */
		return 0;
	}
    public void driveToEncoderUnits(double target_sensorUnits) {
             double target_turn = rightMaster.getSelectedSensorPosition(1); // no turn
             System.out.println("***********");
             double angleInput = SmartDashboard.getNumber("TargetAngle",0);
             if (angleInput != 0) { target_turn = angleInput; } // Angle specified on screen

             System.out.println("Target sensor units:" + target_sensorUnits);
             System.out.println("Target turn:" + target_turn);
             
             /* Configured for MotionMagic on Integrated Encoders' Sum and Auxiliary PID on Pigeon */
             rightMaster.set(ControlMode.MotionMagic, target_sensorUnits, DemandType.AuxPID, target_turn);
             leftMaster.follow(rightMaster, FollowerType.AuxOutput1);
             rightFollower.follow(rightMaster);
             leftFollower.follow(leftMaster);
 
    }

    public boolean atTarget(double encoderUnits) {
        double leftCurrentEncoderUnits = leftMaster.getSelectedSensorPosition(Constants.kPIDLoopIdx);
        double rightCurrentEncoderUnits = rightMaster.getSelectedSensorPosition(Constants.kPIDLoopIdx);
        double remainingLeft = Math.abs(leftCurrentEncoderUnits - encoderUnits);
        double remainingRight = Math.abs(rightCurrentEncoderUnits - encoderUnits);
        if ((remainingRight < 1000) && (remainingLeft < 1000)) {
            System.out.println("true");
            return true;
        }
        return false;   
    }

    public void stop() {
        tankDrive.arcadeDrive(0, 0);
    }
}
