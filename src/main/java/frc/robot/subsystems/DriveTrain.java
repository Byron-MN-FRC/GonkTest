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
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
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

	private WPI_TalonFX rightFollower;
	private WPI_TalonFX leftFollower;
	private WPI_TalonFX leftMaster;
	private WPI_TalonFX rightMaster;
	// private WPI_TalonSRX rightFollower;
	// private WPI_TalonSRX leftFollower;
	// private WPI_TalonSRX leftMaster;
	// private WPI_TalonSRX rightMaster;
	private PigeonIMU pigeon;
	private DifferentialDrive tankDrive;

	/** Invert Directions for Left and Right */
	TalonFXInvertType _leftInvert = TalonFXInvertType.CounterClockwise; // Same as invert = "false"
	TalonFXInvertType _rightInvert = TalonFXInvertType.Clockwise; // Same as invert = "true"

	/** Config Objects for motor controllers */
	TalonFXConfiguration _leftConfig = new TalonFXConfiguration();
	TalonFXConfiguration _rightConfig = new TalonFXConfiguration();

	public DriveTrain() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
rightFollower = new WPI_TalonFX(0);


        
leftFollower = new WPI_TalonFX(2);


        
pigeon = new PigeonIMU(4);


        
leftMaster = new WPI_TalonFX(3);


        
rightMaster = new WPI_TalonFX(1);


        
tankDrive = new DifferentialDrive(leftMaster, rightMaster);
addChild("TankDrive",tankDrive);
tankDrive.setSafetyEnabled(false);
tankDrive.setExpiration(0.1);
tankDrive.setMaxOutput(1.0);

        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		/* rightFollower = new WPI_TalonSRX(0);

		leftFollower = new WPI_TalonSRX(2);

		leftMaster = new WPI_TalonSRX(3);

		rightMaster = new WPI_TalonSRX(1); */
		
		// config();
		SmartDashboard.putNumber("Amp limit", 2);
		SmartDashboard.putNumber("Highset amp level",3);
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
		SmartDashboard.putNumber("Left encoder", leftMaster.getSensorCollection().getIntegratedSensorPosition());
		SmartDashboard.putNumber( "Left velocity", leftMaster.getSensorCollection().getIntegratedSensorVelocity());
		SmartDashboard.putNumber("Right encoder", rightMaster.getSensorCollection().getIntegratedSensorPosition());
		SmartDashboard.putNumber("Right velocity", rightMaster.getSensorCollection().getIntegratedSensorVelocity());
		/* SmartDashboard.putNumber("Left encoder", leftMaster.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("Left velocity", leftMaster.getSensorCollection().getQuadratureVelocity());
		SmartDashboard.putNumber("Right encoder", rightMaster.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("Right velocity", rightMaster.getSensorCollection().getQuadratureVelocity()); */
		SmartDashboard.putNumber("Right Temp", rightMaster.getTemperature());
		SmartDashboard.putNumber("Right Amps", rightMaster.getStatorCurrent());
		SmartDashboard.putNumber("Right Input Amps", rightMaster.getSupplyCurrent());
		SmartDashboard.putNumber("Left Temp", leftMaster.getTemperature());
		SmartDashboard.putNumber("Left Amps", leftMaster.getStatorCurrent());
		SmartDashboard.putNumber("Left Input Amps", leftMaster.getSupplyCurrent());
		
		PigeonIMU.GeneralStatus genStatus = new PigeonIMU.GeneralStatus();
		PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
		double[] xyz_dps = new double[3];
		/* grab some input data from Pigeon and gamepad */
		pigeon.getGeneralStatus(genStatus);
		pigeon.getRawGyro(xyz_dps);
		pigeon.getFusedHeading(fusionStatus);
		double currentAngularRate = xyz_dps[2];
		SmartDashboard.putBoolean("reading?", (pigeon.getState() == PigeonIMU.PigeonState.Ready) ? true : false);
		SmartDashboard.putNumber("angle", fusionStatus.heading);
		double[] ypr = new double[3];
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
		// else if (change < -0.01) {change = -0.01;}
		// limitedJoystick += change;
		// twist = 0;
		tankDrive.arcadeDrive(y, twist);
	}

	// /* public void motorConfigTalon() {
	// 	/* Disable all motor controllers */
	// 	rightMaster.set(ControlMode.PercentOutput, 0);
	// 	leftMaster.set(ControlMode.PercentOutput, 0);

	// 	/* Factory Default all hardware to prevent unexpected behavior */
	// 	rightMaster.configFactoryDefault();
	// 	leftMaster.configFactoryDefault();
	// 	pigeon.configFactoryDefault();

	// 	/* Set Neutral Mode */
	// 	leftMaster.setNeutralMode(NeutralMode.Brake);
	// 	rightMaster.setNeutralMode(NeutralMode.Brake);

	// 	/** Feedback Sensor Configuration */

	// 	/* Configure the left Talon's selected sensor as local QuadEncoder */
	// 	leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, // Local Feedback Source
	// 			Constants.PID_PRIMARY, // PID Slot for Source [0, 1]
	// 			Constants.kTimeoutMs); // Configuration Timeout

	// 	/*
	// 	 * Configure the Remote Talon's selected sensor as a remote sensor for the right
	// 	 * Talon
	// 	 */
	// 	rightMaster.configRemoteFeedbackFilter(leftMaster.getDeviceID(), // Device ID of Source
	// 			RemoteSensorSource.TalonSRX_SelectedSensor, // Remote Feedback Source
	// 			Constants.REMOTE_0, // Source number [0, 1]
	// 			Constants.kTimeoutMs); // Configuration Timeout

	// 	/*
	// 	 * Configure the Pigeon IMU to the other remote slot available on the right
	// 	 * Talon
	// 	 */
	// 	rightMaster.configRemoteFeedbackFilter(pigeon.getDeviceID(), RemoteSensorSource.Pigeon_Yaw,
	// 			Constants.REMOTE_1, Constants.kTimeoutMs);

	// 	/* Setup Sum signal to be used for Distance */
	// 	rightMaster.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, Constants.kTimeoutMs); // Feedback
	// 																										// Device of
	// 																										// Remote
	// 																										// Talon
	// 	rightMaster.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kTimeoutMs); // Quadrature
	// 																													// Encoder
	// 																													// of
	// 																													// current
	// 																													// Talon

	// 	/* Configure Sum [Sum of both QuadEncoders] to be used for Primary PID Index */
	// 	rightMaster.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, Constants.PID_PRIMARY,
	// 			Constants.kTimeoutMs);

	// 	/* Scale Feedback by 0.5 to half the sum of Distance */
	// 	rightMaster.configSelectedFeedbackCoefficient(0.5, // Coefficient
	// 			Constants.PID_PRIMARY, // PID Slot of Source
	// 			Constants.kTimeoutMs); // Configuration Timeout

	// 	/* Configure Remote 1 [Pigeon IMU's Yaw] to be used for Auxiliary PID Index */
	// 	rightMaster.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, Constants.PID_TURN,
	// 			Constants.kTimeoutMs);

	// 	/* Scale the Feedback Sensor using a coefficient */
	// 	rightMaster.configSelectedFeedbackCoefficient(1, Constants.PID_TURN, Constants.kTimeoutMs);

	// 	/* Configure output and sensor direction */
	// 	leftMaster.setInverted(false);
	// 	leftMaster.setSensorPhase(true);
	// 	rightMaster.setInverted(true);
	// 	rightMaster.setSensorPhase(true);

	// 	/* Set status frame periods to ensure we don't have stale data */
	// 	rightMaster.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, Constants.kTimeoutMs);
	// 	rightMaster.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeoutMs);
	// 	rightMaster.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, Constants.kTimeoutMs);
	// 	rightMaster.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20, Constants.kTimeoutMs);
	// 	leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, Constants.kTimeoutMs);
	// 	pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR, 5, Constants.kTimeoutMs);

	// 	/* Configure neutral deadband */
	// 	rightMaster.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);
	// 	leftMaster.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);

	// 	/* Motion Magic Configurations */
	// 	rightMaster.configMotionAcceleration(2000, Constants.kTimeoutMs);
	// 	rightMaster.configMotionCruiseVelocity(2000, Constants.kTimeoutMs);

	// 	/**
	// 	 * Max out the peak output (for all modes). However you can limit the output of
	// 	 * a given PID object with configClosedLoopPeakOutput().
	// 	 */
	// 	double max = 0.2;
	// 	leftMaster.configPeakOutputForward(max, Constants.kTimeoutMs);
	// 	leftMaster.configPeakOutputReverse(-max, Constants.kTimeoutMs);
	// 	rightMaster.configPeakOutputForward(max, Constants.kTimeoutMs);
	// 	rightMaster.configPeakOutputReverse(-max, Constants.kTimeoutMs);

	// 	/* FPID Gains for distance servo */
	// 	rightMaster.config_kP(Constants.kSlot_Distanc, Constants.kGains_Distanc.kP, Constants.kTimeoutMs);
	// 	rightMaster.config_kI(Constants.kSlot_Distanc, Constants.kGains_Distanc.kI, Constants.kTimeoutMs);
	// 	rightMaster.config_kD(Constants.kSlot_Distanc, Constants.kGains_Distanc.kD, Constants.kTimeoutMs);
	// 	rightMaster.config_kF(Constants.kSlot_Distanc, Constants.kGains_Distanc.kF, Constants.kTimeoutMs);
	// 	rightMaster.config_IntegralZone(Constants.kSlot_Distanc, Constants.kGains_Distanc.kIzone,
	// 			Constants.kTimeoutMs);
	// 	rightMaster.configClosedLoopPeakOutput(Constants.kSlot_Distanc, Constants.kGains_Distanc.kPeakOutput,
	// 			Constants.kTimeoutMs);

	// 	/* FPID Gains for turn servo */
	// 	rightMaster.config_kP(Constants.kSlot_Turning, Constants.kGains_Turning.kP, Constants.kTimeoutMs);
	// 	rightMaster.config_kI(Constants.kSlot_Turning, Constants.kGains_Turning.kI, Constants.kTimeoutMs);
	// 	rightMaster.config_kD(Constants.kSlot_Turning, Constants.kGains_Turning.kD, Constants.kTimeoutMs);
	// 	rightMaster.config_kF(Constants.kSlot_Turning, Constants.kGains_Turning.kF, Constants.kTimeoutMs);
	// 	rightMaster.config_IntegralZone(Constants.kSlot_Turning, Constants.kGains_Turning.kIzone,
	// 			Constants.kTimeoutMs);
	// 	rightMaster.configClosedLoopPeakOutput(Constants.kSlot_Turning, Constants.kGains_Turning.kPeakOutput,
	// 			Constants.kTimeoutMs);

	// 	/**
	// 	 * 1ms per loop. PID loop can be slowed down if need be. For example, - if
	// 	 * sensor updates are too slow - sensor deltas are very small per update, so
	// 	 * derivative error never gets large enough to be useful. - sensor movement is
	// 	 * very slow causing the derivative error to be near zero.
	// 	 */
	// 	int closedLoopTimeMs = 1;
	// 	rightMaster.configClosedLoopPeriod(0, closedLoopTimeMs, Constants.kTimeoutMs);
	// 	rightMaster.configClosedLoopPeriod(1, closedLoopTimeMs, Constants.kTimeoutMs);

	// 	/**
	// 	 * configAuxPIDPolarity(boolean invert, int timeoutMs) false means talon's local
	// 	 * output is PID0 + PID1, and other side Talon is PID0 - PID1 true means talon's
	// 	 * local output is PID0 - PID1, and other side Talon is PID0 + PID1
	// 	 */
	// 	rightMaster.configAuxPIDPolarity(false, Constants.kTimeoutMs);

	// 	/* Initialize */

	// 	rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_Targets, 10);
	// 	// WPI drivetrain classes assume by default left & right are opposite
	// 	// - call this to apply + to both sides when moving forward
	// 	tankDrive.setRightSideInverted(false);

	// 	// set on call from autonomous
	// 	rightMaster.selectProfileSlot(Constants.kSlot_Distanc, Constants.PID_PRIMARY);
	// 	rightMaster.selectProfileSlot(Constants.kSlot_Turning, Constants.PID_TURN);
	// 	rightFollower.follow(rightMaster);
	// 	leftFollower.follow(leftMaster);
	// 	zeroSensors();
	// }

	public final SupplyCurrentLimitConfiguration currentLimitingFalcons = new SupplyCurrentLimitConfiguration(Constants.enable, SmartDashboard.getNumber("Amp limit",2), SmartDashboard.getNumber("Highest amp level", 3), Constants.threshholdTime);
 
	public void motorConfigFalcon() {
		rightMaster.configSupplyCurrentLimit(currentLimitingFalcons);

		// Set Neutral Mode
		leftMaster.setNeutralMode(NeutralMode.Brake);
		leftFollower.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
		rightFollower.setNeutralMode(NeutralMode.Brake);

		// Configure output and sensor direction
		leftMaster.setInverted(_leftInvert);
		leftFollower.setInverted(_leftInvert);
		rightMaster.setInverted(_rightInvert);
		rightFollower.setInverted(_rightInvert);

		// Reset Pigeon Configs
		pigeon.configFactoryDefault();

		// Feedback Sensor Configuration

		// Distance Configs

		// Configure the left Talon's selected sensor as integrated sensor
		_leftConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor; // Local Feedback Source

		
		//  * Configure the Remote (Left) Talon's selected sensor as a remote sensor for
		//  * the right Talon
		
		_rightConfig.remoteFilter0.remoteSensorDeviceID = leftMaster.getDeviceID(); // Device ID of Remote Source
		_rightConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.TalonFX_SelectedSensor; // Remote Source Type

		
		//  * Now that the Left sensor can be used by the master Talon, set up the Left
		//  * (Aux) and Right (Master) distance into a single Robot distance as the
		//  * Master's Selected Sensor 0.
		
		setRobotDistanceConfigs(_rightInvert, _rightConfig);

		// FPID for Distance
		_rightConfig.slot0.kF = Constants.kGains_Distanc.kF;
		_rightConfig.slot0.kP = Constants.kGains_Distanc.kP;
		_rightConfig.slot0.kI = Constants.kGains_Distanc.kI;
		_rightConfig.slot0.kD = Constants.kGains_Distanc.kD;
		_rightConfig.slot0.integralZone = Constants.kGains_Distanc.kIzone;
		_rightConfig.slot0.closedLoopPeakOutput = Constants.kGains_Distanc.kPeakOutput;

		// * Heading Configs
		_rightConfig.remoteFilter1.remoteSensorDeviceID = pigeon.getDeviceID(); // Pigeon Device ID
		_rightConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw; // This is for a Pigeon over CAN
		_rightConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor1; // Set as the Aux Sensor
		_rightConfig.auxiliaryPID.selectedFeedbackCoefficient = 3600.0 / Constants.kPigeonUnitsPerRotation; // Convert
																											// Yaw to
																											// tenths of
																											// a degree

		
		//  * false means talon's local output is PID0 + PID1, and other side Talon is PID0
		//  * - PID1 This is typical when the master is the right Talon FX and using Pigeon
		//  * 
		//  * true means talon's local output is PID0 - PID1, and other side Talon is PID0
		//  * + PID1 This is typical when the master is the left Talon FX and using Pigeon
		
		_rightConfig.auxPIDPolarity = false;

		// FPID for Heading
		_rightConfig.slot1.kF = Constants.kGains_Turning.kF;
		_rightConfig.slot1.kP = Constants.kGains_Turning.kP;
		_rightConfig.slot1.kI = Constants.kGains_Turning.kI;
		_rightConfig.slot1.kD = Constants.kGains_Turning.kD;
		_rightConfig.slot1.integralZone = Constants.kGains_Turning.kIzone;
		_rightConfig.slot1.closedLoopPeakOutput = Constants.kGains_Turning.kPeakOutput;

		// Config the neutral deadband.
		_leftConfig.neutralDeadband = Constants.kNeutralDeadband;
		_rightConfig.neutralDeadband = Constants.kNeutralDeadband;

		// *
		//  * 1ms per loop. PID loop can be slowed down if need be. For example, - if
		//  * sensor updates are too slow - sensor deltas are very small per update, so
		//  * derivative error never gets large enough to be useful. - sensor movement is
		//  * very slow causing the derivative error to be near zero.
		
		int closedLoopTimeMs = 1;
		rightMaster.configClosedLoopPeriod(0, closedLoopTimeMs, Constants.kTimeoutMs);
		rightMaster.configClosedLoopPeriod(1, closedLoopTimeMs, Constants.kTimeoutMs);

		// Motion Magic Configs
		_rightConfig.motionAcceleration = 2000; // (distance units per 100 ms) per second
		_rightConfig.motionCruiseVelocity = 2000; // distance units per 100 ms

		// APPLY the config settings
		leftMaster.configAllSettings(_leftConfig);
		leftFollower.configAllSettings(_leftConfig);
		rightMaster.configAllSettings(_rightConfig);
		rightFollower.configAllSettings(_rightConfig);

		// Set status frame periods to ensure we don't have stale data
		
		//  * These aren't configs (they're not persistant) so we can set these after the
		//  * configs.
		
		rightMaster.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, Constants.kTimeoutMs);
		rightMaster.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeoutMs);
		rightMaster.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, Constants.kTimeoutMs);
		rightMaster.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10, Constants.kTimeoutMs);
		leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, Constants.kTimeoutMs);
		pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR, 5, Constants.kTimeoutMs);

		// artificial limit for testing
		double max = 0.5;
		leftMaster.configPeakOutputForward(max, Constants.kTimeoutMs);
		leftMaster.configPeakOutputReverse(-max, Constants.kTimeoutMs);
		rightMaster.configPeakOutputForward(max, Constants.kTimeoutMs);
		rightMaster.configPeakOutputReverse(-max, Constants.kTimeoutMs);

		// WPI drivetrain classes assume by default left & right are opposite
		// - call this to apply + to both sides when moving forward
		tankDrive.setRightSideInverted(false);

		// set on call from autonomous
		rightMaster.selectProfileSlot(Constants.kSlot_Distanc, Constants.PID_PRIMARY);
		rightMaster.selectProfileSlot(Constants.kSlot_Turning, Constants.PID_TURN);
		rightFollower.follow(rightMaster);
		leftFollower.follow(leftMaster);
		zeroSensors();
		// rightMaster.configStatorCurrentLimit(currLimitCfg)
		// (Constants.kPeakCurrentAmps, Constants.kTimeoutMs);
		// rightMaster.configPeakCurrentDuration(Constants.kPeakTimeMs,
		// Constants.kTimeoutMs);
		// rightMaster.configContinuousCurrentLimit(Constants.kContinCurrentAmps,
		// Constants.kTimeoutMs);
		// rightMaster.enableCurrentLimit(_currentLimEn); // Honor initial setting

	}

	/** Zero all sensors, both Talons and Pigeon */

	
	public void zeroSensors() {
		SupplyCurrentLimitConfiguration changeLimit = new SupplyCurrentLimitConfiguration(Constants.enable, SmartDashboard.getNumber("Amp limit",2), SmartDashboard.getNumber("Highest amp level", 3), Constants.threshholdTime);
		rightMaster.configSupplyCurrentLimit(changeLimit);
		leftMaster.getSensorCollection().setIntegratedSensorPosition(0, Constants.kTimeoutMs);
		rightMaster.getSensorCollection().setIntegratedSensorPosition(0, Constants.kTimeoutMs);
		pigeon.setYaw(0, Constants.kTimeoutMs);
		pigeon.setAccumZAngle(0, Constants.kTimeoutMs);
		System.out.println("Integrated Encoders + Pigeon] All sensors are zeroed.\n");
	}
	/* public void zeroSensors() {
		leftMaster.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMs);
		rightMaster.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMs);
		pigeon.setYaw(0, Constants.kTimeoutMs);
		pigeon.setAccumZAngle(0, Constants.kTimeoutMs);
		System.out.println("[Quadrature Encoders + Pigeon] All sensors are zeroed.\n");
	}
 */
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
		// double target_turn = rightMaster.getSensorCollection.get(1); // no turn
		double target_turn = 0;
		System.out.println("***********");
		double angleInput = SmartDashboard.getNumber("TargetAngle", 0);
		if (angleInput != 0) {
			target_turn = angleInput;
		} // Angle specified on screen

		System.out.println("Target sensor units:" + target_sensorUnits);
		System.out.println("Target turn:" + target_turn);

		/*
		 * Configured for MotionMagic on Integrated Encoders' Sum and Auxiliary PID on
		 * Pigeon
		 */
		rightMaster.set(ControlMode.MotionMagic, target_sensorUnits, DemandType.AuxPID, target_turn);
		leftMaster.follow(rightMaster, FollowerType.AuxOutput1);
		rightFollower.follow(rightMaster);
		leftFollower.follow(leftMaster);

	}

	public boolean atTarget(double encoderUnits) {
		// fix it
		double leftCurrentEncoderUnits = leftMaster.getSelectedSensorPosition(Constants.kPIDLoopIdx);
		// leftCurrentEncoderUnits = leftMaster.getSensorCollection().getQuadraturePosition();
		leftCurrentEncoderUnits = leftMaster.getSensorCollection().getIntegratedSensorPosition();
		double rightCurrentEncoderUnits = rightMaster.getSelectedSensorPosition(Constants.kPIDLoopIdx);
		rightCurrentEncoderUnits = rightMaster.getSensorCollection().getIntegratedSensorPosition();
		// rightCurrentEncoderUnits = rightMaster.getSensorCollection().getQuadraturePosition();
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

	/**
	 * Determines if SensorSum or SensorDiff should be used for combining left/right
	 * sensors into Robot Distance.
	 * 
	 * Assumes Aux Position is set as Remote Sensor 0.
	 * 
	 * configAllSettings must still be called on the master config after this
	 * function modifies the config values.
	 * 
	 * @param masterInvertType Invert of the Master Talon
	 * @param masterConfig     Configuration object to fill
	 */
	void setRobotDistanceConfigs(TalonFXInvertType masterInvertType, TalonFXConfiguration masterConfig) {
		/**
		 * Determine if we need a Sum or Difference.
		 * 
		 * The auxiliary Talon FX will always be positive in the forward direction
		 * because it's a selected sensor over the CAN bus.
		 * 
		 * The master's native integrated sensor may not always be positive when forward
		 * because sensor phase is only applied to *Selected Sensors*, not native sensor
		 * sources. And we need the native to be combined with the aux (other side's)
		 * distance into a single robot distance.
		 */

		/*
		 * THIS FUNCTION should not need to be modified. This setup will work regardless
		 * of whether the master is on the Right or Left side since it only deals with
		 * distance magnitude.
		 */

		/* Check if we're inverted */
		if (masterInvertType == TalonFXInvertType.Clockwise) {
			/*
			 * If master is inverted, that means the integrated sensor will be negative in
			 * the forward direction.
			 * 
			 * If master is inverted, the final sum/diff result will also be inverted. This
			 * is how Talon FX corrects the sensor phase when inverting the motor direction.
			 * This inversion applies to the *Selected Sensor*, not the native value.
			 * 
			 * Will a sensor sum or difference give us a positive total magnitude?
			 * 
			 * Remember the Master is one side of your drivetrain distance and Auxiliary is
			 * the other side's distance.
			 * 
			 * Phase | Term 0 | Term 1 | Result Sum: -1 *((-)Master + (+)Aux )| NOT OK, will
			 * cancel each other out Diff: -1 *((-)Master - (+)Aux )| OK - This is what we
			 * want, magnitude will be correct and positive. Diff: -1 *((+)Aux - (-)Master)|
			 * NOT OK, magnitude will be correct but negative
			 */

			masterConfig.diff0Term = FeedbackDevice.IntegratedSensor; // Local Integrated Sensor
			masterConfig.diff1Term = FeedbackDevice.RemoteSensor0; // Aux Selected Sensor
			masterConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorDifference; // Diff0 - Diff1
		} else {
			/* Master is not inverted, both sides are positive so we can sum them. */
			masterConfig.sum0Term = FeedbackDevice.RemoteSensor0; // Aux Selected Sensor
			masterConfig.sum1Term = FeedbackDevice.IntegratedSensor; // Local IntegratedSensor
			masterConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorSum; // Sum0 + Sum1
		}

		/*
		 * Since the Distance is the sum of the two sides, divide by 2 so the total
		 * isn't double the real-world value
		 */
		masterConfig.primaryPID.selectedFeedbackCoefficient = 0.5;
	}

	public void turn(final double angle){
		final double[] ypr = new double[3];
		pigeon.getYawPitchRoll(ypr);
		double remainingDistance = 0;
		final double targetHeading = ypr[0] + angle;
		double remainingAngle = Math.abs(targetHeading - ypr[0]);
		while (remainingAngle > Constants.kTurn_Tolerance)
		{
			SmartDashboard.putNumber("remaining angle", remainingAngle);
			SmartDashboard.putNumber("yaw angle", ypr[0]);
			
			if (angle > 0.0) {
				tankDrive.arcadeDrive(0.0, -0.2);
			}
			else {
				tankDrive.arcadeDrive(0.0, 0.2);
			}
			//tankDrive.arcadeDrive(0.0, (angle > 0.0)? -0.2: 0.2);
			pigeon.getYawPitchRoll(ypr);
			remainingAngle = Math.abs(targetHeading - ypr[0]);
		}
	}
}
