
package frc.robot.Subsystems.RollerFloor;

public enum RollerFloorStates{
    SAFE(0),//TODO replace with
    SHOOT(60),
    PRELOAD(12),
    REVERSE(-45);
        public final double enumRollerVelocity;
    private RollerFloorStates(double enumRollerVelocity){
        this.enumRollerVelocity = enumRollerVelocity;
    }
}
