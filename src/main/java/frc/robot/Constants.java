package frc.robot;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstantsFactory;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;


public class Constants {
    
    //general
    public static CANBus krakenBus = new CANBus("krakenbus");
  public static Boolean isBlueAlliance = null;

    public static double redTrenchX = 11.7;
    public static double blueTrenchX = 4.8;
    public static double outpostTrenchY = 7.4375;
    public static double depotTrenchY = 0.625;
    public static double leftYDeadband;
    public static double leftXDeadband;
    public static double rightXDeadband;
    public static double rightYDeadband;

   private static final double highCamPitch = Units.degreesToRadians(20);

    //thank you ethan
  public static final Transform3d shooterCam = new Transform3d(new Translation3d(0,0.32,0.29162),
      new Rotation3d(0, -highCamPitch, 0));
  public static final Transform3d robotToCamLeft = new Transform3d(new Translation3d(-0.349,0.092,0.215),
      new Rotation3d(0, -highCamPitch, 3*(Math.PI)/2));
  public static final Transform3d robotToCamRight = new Transform3d(new Translation3d(0.349, 0.092, 0.215),
      new Rotation3d(0, -highCamPitch, Math.PI/2));

    public static final Mode simMode = Mode.SIM;
    public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;
    public static final AprilTagFieldLayout kTagLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded);


    public static enum Mode {
        /** Running on a real robot. */
        REAL,

        /** Running a physics simulator. */
        SIM,

        /** Replaying from a log file. */
        REPLAY
  }


    public static enum RobotTypes {
    // alpha
    COMP, // swerve bot

    // beta
    BETA, // turret bot

    // swervebot
    SWERVE
  }


  private static final SwerveModuleConstantsFactory<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> constantCreator =
        new SwerveModuleConstantsFactory<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration>()

        //TODO tune please
            // .withDriveMotorGearRatio(0)
            // .withSteerMotorGearRatio(0)
            // .withCouplingGearRatio(0)
            // .withWheelRadius(0)
            // .withSteerMotorGains(0)
            // .withDriveMotorGains(0)
            // .withSteerMotorClosedLoopOutput(0)
            // .withDriveMotorClosedLoopOutput(0)
            // .withSlipCurrent(0)
            // .withSpeedAt12Volts(0)
            // .withDriveMotorType(0)
            // .withSteerMotorType(0)
            // .withFeedbackSource(0)
            // .withDriveMotorInitialConfigs(0)
            // .withSteerMotorInitialConfigs(0)
            // .withEncoderInitialConfigs(0)
            // .withSteerInertia(0)
            // .withDriveInertia(0)
            // .withSteerFrictionVoltage(0)
            // .withDriveFrictionVoltage(0);
            ;



    private static final int kFrontLeftDriveMotorId = 7;
    private static final int kFrontLeftSteerMotorId = 8;
    private static final int kFrontLeftEncoderId = 11;
    private static final Angle kFrontLeftEncoderOffset = Rotations.of(0.325439453125);
    private static final boolean kFrontLeftSteerMotorInverted = false;
    private static final boolean kFrontLeftEncoderInverted = false;

    private static final Distance kFrontLeftXPos = Inches.of(9.875);
    private static final Distance kFrontLeftYPos = Inches.of(12);

    // Front Right
    private static final int kFrontRightDriveMotorId = 5;
    private static final int kFrontRightSteerMotorId = 6;
    private static final int kFrontRightEncoderId = 12;
    private static final Angle kFrontRightEncoderOffset = Rotations.of(0.01953125);
    private static final boolean kFrontRightSteerMotorInverted = false;
    private static final boolean kFrontRightEncoderInverted = false;

    private static final Distance kFrontRightXPos = Inches.of(9.875);
    private static final Distance kFrontRightYPos = Inches.of(-12);

    // Back Left
    private static final int kBackLeftDriveMotorId = 3;
    private static final int kBackLeftSteerMotorId = 4;
    private static final int kBackLeftEncoderId = 10;
    private static final Angle kBackLeftEncoderOffset = Rotations.of(-0.437744140625);
    private static final boolean kBackLeftSteerMotorInverted = false;
    private static final boolean kBackLeftEncoderInverted = false;

    private static final Distance kBackLeftXPos = Inches.of(-9.875);
    private static final Distance kBackLeftYPos = Inches.of(12);

    // Back Right
    private static final int kBackRightDriveMotorId = 1;
    private static final int kBackRightSteerMotorId = 2;
    private static final int kBackRightEncoderId = 9;
    private static final Angle kBackRightEncoderOffset = Rotations.of(-0.48291015625);
    private static final boolean kBackRightSteerMotorInverted = false;
    private static final boolean kBackRightEncoderInverted = false;

    private static final Distance kBackRightXPos = Inches.of(-9.875);
    private static final Distance kBackRightYPos = Inches.of(-12);

    private static final boolean kInvertLeftSide = false;
    private static final boolean kInvertRightSide = true;


     public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> FrontLeft =
        constantCreator.createModuleConstants(
            kFrontLeftSteerMotorId, kFrontLeftDriveMotorId, kFrontLeftEncoderId, kFrontLeftEncoderOffset,
            kFrontLeftXPos, kFrontLeftYPos, kInvertLeftSide, kFrontLeftSteerMotorInverted, kFrontLeftEncoderInverted
        );
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> FrontRight =
        constantCreator.createModuleConstants(
            kFrontRightSteerMotorId, kFrontRightDriveMotorId, kFrontRightEncoderId, kFrontRightEncoderOffset,
            kFrontRightXPos, kFrontRightYPos, kInvertRightSide, kFrontRightSteerMotorInverted, kFrontRightEncoderInverted
        );
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> BackLeft =
        constantCreator.createModuleConstants(
            kBackLeftSteerMotorId, kBackLeftDriveMotorId, kBackLeftEncoderId, kBackLeftEncoderOffset,
            kBackLeftXPos, kBackLeftYPos, kInvertLeftSide, kBackLeftSteerMotorInverted, kBackLeftEncoderInverted
        );
    public static final SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> BackRight =
        constantCreator.createModuleConstants(
            kBackRightSteerMotorId, kBackRightDriveMotorId, kBackRightEncoderId, kBackRightEncoderOffset,
            kBackRightXPos, kBackRightYPos, kInvertRightSide, kBackRightSteerMotorInverted, kBackRightEncoderInverted
        );

 

}
