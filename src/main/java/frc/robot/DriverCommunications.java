package frc.robot;

import com.thethriftybot.wrappers.DriverStationWrapper.defaultDriversStation;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Vision.VisionIOSystem;

public class DriverCommunications {
    // static boolean CurrentHubState = Shooter.getInstance().isHubActive();
    static String NextPhaseIndication = "Transition Period";
    static String PhaseIndication = "Autonomous";
    public static Field2d fieldPose = new Field2d();
    public static double PhaseClock = 0;
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
        // String phase1 = (Robot.wonAuto() ? "Opposing Shift" : "Our Shift");
        // String phase2 = (Robot.wonAuto() ? "Our Shift" : "Opposing Shift");
        // String[] phaseNames = {"Transition Phase", phase1, phase2, phase1, phase2, "Endgame", "Match end"};
        // short phaseNumber = 0;
        // if (TeleopTimer <= 10) {
        //     phaseNumber = 0; // Transition Phase
        // } else if (TeleopTimer <= 40) {
        //     phaseNumber = 1;
        // } else if (TeleopTimer <= 70) {
        //     phaseNumber = 2;
        // } else if (TeleopTimer <= 100) {
        //     phaseNumber = 3;
        // } else if (TeleopTimer <= 130) {
        //     phaseNumber = 4;
        // } else {
        //     phaseNumber = 5; // Endgame
        // }
        // SmartDashboard.putString("Current Phase", phaseNames[phaseNumber]);
        // SmartDashboard.putString("Next Phase", phaseNames[phaseNumber + 1]);

    }
}
