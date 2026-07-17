package frc.robot;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotBase;

public class Constants {
    
    //general
    public static CANBus krakenBus = new CANBus("krakenbus");
  public static Boolean isBlueAlliance = null;

    public static double redTrenchX = 11.7;
    public static double blueTrenchX = 4.8;
    public static double outpostTrenchY = 7.4375;
    public static double depotTrenchY = 0.625;

    //TODO set to real values
    public static double robotToCamFrontRight = 1;
    public static double robotToCamFrontLeft = 1;
    public static double robotToCamRight = 1;
    public static double robotToCamLeft = 1;
    public static double robotToCamBack = 1;

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
