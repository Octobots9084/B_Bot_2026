
package frc.robot.Subsystems.RollerFloor;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.units.measure.AngularVelocity;

public enum RollerFloorStates{
    SAFE(RotationsPerSecond.of(0)),//TODO replace with
    SHOOT(RotationsPerSecond.of(25)),
    PRELOAD(RotationsPerSecond.of(2)),
    REVERSE(RotationsPerSecond.of(-15));
    public final AngularVelocity enumRollerVelocity;
    private RollerFloorStates(AngularVelocity enumRollerVelocity){
        this.enumRollerVelocity = enumRollerVelocity;
    }
}
