package frc.robot.Subsystems.Vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public class Alignment {
    //needs tuning
    public static PIDController rotationPidController = new PIDController(0,0,0);
    double wantedDegree;
    double currentDegree;

    public ChassisSpeeds getRotation(double wantedDegree){
        //currentDegree = Pose2d.direction();
        double rotationSpeed = rotationPidController.calculate(currentDegree, wantedDegree);
        ChassisSpeeds chassisSpeed = new ChassisSpeeds(0,0, rotationSpeed);

        return chassisSpeed;
    }
}
