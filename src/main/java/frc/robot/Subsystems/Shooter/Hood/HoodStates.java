package frc.robot.Subsystems.Shooter.Hood;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.units.measure.Angle;

public enum HoodStates {
    SAFE(0),
    FIXEDFIRE(30);
    public final double enumAngle;
    private HoodStates(double enumAngle){
        this.enumAngle = enumAngle;
    }
}
