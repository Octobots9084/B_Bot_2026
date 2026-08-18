package frc.robot.Subsystems.Shooter.Hood;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.units.measure.Angle;

public enum HoodStates {
    SAFE(Degrees.of(0)),
    FIXEDFIRE(Degrees.of(0));
    public final Angle enumAngle;
    private HoodStates(Angle enumAngle){
        this.enumAngle = enumAngle;
    }
}
