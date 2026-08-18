package frc.robot.Subsystems.Shooter.Flywheel;

public enum FlywheelStates {
    SAFE(0),
    CUSTOMFIRE(0),
    FIXEDFIRE(0);
    public final double enumVelocity;
    private FlywheelStates(double enumVelocity){
        this.enumVelocity = enumVelocity;
    }
}
