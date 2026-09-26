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
import edu.wpi.first.math.trajectory.constraint.MaxVelocityConstraint;
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


public static final double maxAngularVelocity = 540;
public static final double maxVelocity = 4.5;
}
