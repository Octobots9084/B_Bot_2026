package frc.robot.Subsystems.Shooter.Hood;

public enum HoodStates {
    SAFE(0),
    FIXEDFIRE(0);
    public final double enumAngle;
    private HoodStates(double enumAngle){
        this.enumAngle = enumAngle;
    }
}
