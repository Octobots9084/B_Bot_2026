
package frc.robot.Subsystems.RollerFloor;

public enum RollerFloorStates{
    SAFE(0),//TODO replace with
    SHOOT(0),
    PRELOAD(0),
    REVERSE(-0);
        public final double enumRollerVelocity;
    private RollerFloorStates(double enumRollerVelocity){
        this.enumRollerVelocity = enumRollerVelocity;
    }
}
