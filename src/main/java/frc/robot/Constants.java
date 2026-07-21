package frc.robot;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.math.util.Units;


public class Constants {
    
    //general
    public static CANBus krakenBus = new CANBus("krakenbus");
  public static Boolean isBlueAlliance = null;

    public static double redTrenchX = 11.7;
    public static double blueTrenchX = 4.8;
    public static double outpostTrenchY = 7.4375;
    public static double depotTrenchY = 0.625;

   private static final double highCamPitch = Units.degreesToRadians(20);
  private static final double LowCamPitch = Units.degreesToRadians(28.5);

    //TODO set to real values i lowkey stole this from rui
    public static final Transform3d robotToCamFrontRight = new Transform3d(new Translation3d(-0.28,0.195,0.41),
      new Rotation3d(0, -highCamPitch, (-30/180.0)*Math.PI+Math.PI));
  //-20 +30
  public static final Transform3d robotToCamFrontLeft = new Transform3d(new Translation3d(-0.28,-0.135,0.24),
      new Rotation3d(0, -LowCamPitch, (30/180.0)*Math.PI + Math.PI));
  public static final Transform3d robotToCamRight = new Transform3d(new Translation3d(-0.265, 0.36, .465),
      new Rotation3d(0, -highCamPitch, Math.PI/2));
  public static final Transform3d robotToCamLeft = new Transform3d(new Translation3d(-0.27,-0.36, 0.245),
      new Rotation3d(0, -LowCamPitch, 3.0*Math.PI/2.0));
  public static final Transform3d robotToCamBack = new Transform3d(new Translation3d(-0.28,0.09, 0.42), //set these values
      new Rotation3d(0, -highCamPitch,0));

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
}
