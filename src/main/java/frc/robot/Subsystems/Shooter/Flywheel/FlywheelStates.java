package frc.robot.Subsystems.Shooter.Flywheel;

import static edu.wpi.first.units.Units.RotationsPerSecond;

public enum FlywheelStates {
    SAFE(0),
    CUSTOMFIRE(0),
    TRENCHFIRE(37),
    FIXEDFIRE(45.25), //maybe subtract 1.5
    FIXEDCLOSE(34);
    
    public final double enumVelocity;
    private FlywheelStates(double enumVelocity){
        this.enumVelocity = enumVelocity;
    }
}
