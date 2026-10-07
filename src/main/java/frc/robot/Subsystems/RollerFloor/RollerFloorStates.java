
package frc.robot.Subsystems.RollerFloor;

public enum RollerFloorStates{
    SAFE(0),//TODO replace with
    SHOOT(120),
    PRELOAD(35),
    REVERSE(-45);
        public final double enumRollerVelocity;
    private RollerFloorStates(double enumRollerVelocity){
        this.enumRollerVelocity = enumRollerVelocity;
    }
}
