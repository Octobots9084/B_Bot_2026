package frc.robot;

import com.thethriftybot.wrappers.DriverStationWrapper.defaultDriversStation;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Vision.VisionIOSystem;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;

public class DriverCommunications {
    //static boolean CurrentHubState = ShooterSubsystem.getInstance().isHubActive();
    public static Field2d fieldPose = new Field2d();
    public static double TeleopTimer = Timer.getMatchTime();
    public static double TeleopTimePassed = 160 - Timer.getMatchTime();
    public static VisionIOSystem vision = new VisionIOSystem();
    public static void pushToElastic() {

        // Did we win Auto
        SmartDashboard.putBoolean("Won Auto", Robot.wonAuto());

        // Are the cameras connected
        SmartDashboard.putBoolean("left camera connected", vision.cameraConnected(0));
        SmartDashboard.putBoolean("right camera connected", vision.cameraConnected(1));
        SmartDashboard.putBoolean("shooter camera connected", vision.cameraConnected(2));

        // Match times
        SmartDashboard.putNumber("Time Passed", TeleopTimePassed);
        SmartDashboard.putNumber("Time Left", TeleopTimer);
        
        // Phase names
        String phase1 = (Robot.wonAuto() ? "Opposing Shift" : "Our Shift");
        String phase2 = (Robot.wonAuto() ? "Our Shift" : "Opposing Shift");
        String[] phaseNames = {"Transition Phase", phase1, phase2, phase1, phase2, "Endgame", "Match end"};
        int phaseNumber = 0;
        if (TeleopTimer <= 10) {
            phaseNumber = 0; // Transition Phase
        } else if (TeleopTimer <= 40) {
            phaseNumber = 1;
        } else if (TeleopTimer <= 70) {
            phaseNumber = 2;
        } else if (TeleopTimer <= 100) {
            phaseNumber = 3;
        } else if (TeleopTimer <= 130) {
            phaseNumber = 4;
        } else {
            phaseNumber = 5; // Endgame
        }
        SmartDashboard.putString("Current Phase", phaseNames[phaseNumber]);
        SmartDashboard.putString("Next Phase", phaseNames[phaseNumber + 1]);
        SmartDashboard.putNumber("Phase number", phaseNumber);

        //Can we score points?
        if (Robot.wonAuto()) {
            SmartDashboard.putBoolean("Can Score?", (phaseNumber == 1 || phaseNumber == 3 || phaseNumber == 5 || phaseNumber == 6));
        } else {
            SmartDashboard.putBoolean("Can Score?", (phaseNumber == 1 || phaseNumber == 2 || phaseNumber == 4 || phaseNumber == 6));
        }

        // Field
        SmartDashboard.putData(fieldPose);
    }
}
