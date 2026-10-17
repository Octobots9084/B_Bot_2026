package frc.robot.Subsystems.Shooter.Flywheel;

import static edu.wpi.first.units.Units.RotationsPerSecond;

public enum FlywheelStates {
    SAFE(0),
    CUSTOMFIRE(0),
    FIXEDFIRE(40);
    public final double enumVelocity;
    private FlywheelStates(double enumVelocity){
        this.enumVelocity = enumVelocity;
    }
}
