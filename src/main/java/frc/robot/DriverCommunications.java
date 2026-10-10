package frc.robot;

import com.thethriftybot.wrappers.DriverStationWrapper.defaultDriversStation;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Vision.VisionIOSystem;
import frc.robot.Constants.Mode;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;

public class DriverCommunications {
    //static boolean CurrentHubState = ShooterSubsystem.getInstance().isHubActive();
    public static Field2d fieldPose = new Field2d();
    public static double TeleopTimer = Timer.getMatchTime();
    public static double TeleopTimePassed = 160 - Timer.getMatchTime();
    public static VisionIOSystem vision = new VisionIOSystem();
    public static double testFlywheelSpeed = 0;
    public static double testHoodAngle = 0;


    public static void pushToElastic() {
        // Are the cameras connected
        SmartDashboard.putBoolean("Cam/ Left cam", vision.cameraConnected(0));
        SmartDashboard.putBoolean("Cam/ Right cam", vision.cameraConnected(1));
        SmartDashboard.putBoolean("Cam/ Shooter cam", vision.cameraConnected(2));

        // Match times
        SmartDashboard.putNumber("Timer", Constants.timer.get());
        SmartDashboard.putNumber("Match Time", DriverStation.getMatchTime());
        testFlywheelSpeed = SmartDashboard.getNumber("Flywheel Speed", 0);
        testHoodAngle = SmartDashboard.getNumber("Hood Angle", 0);

        
        // Field
        SmartDashboard.putData(fieldPose);

        //TODO remove santi smartdashboard stuff because its logged differently
    }
}
