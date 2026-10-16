
package frc.robot.Subsystems.Intake;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import org.dyn4j.geometry.Rotation;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;


public enum IntakeStates{
    SAFE(Rotations.of(0), RotationsPerSecond.of(0)),
    EXTENDED(Rotations.of(0.4), RotationsPerSecond.of(0)),
    INTAKING(Rotations.of(0.4), RotationsPerSecond.of(30)),
    ELEPHANTIASIS(Rotations.of(0.2), RotationsPerSecond.of(15)),//MK 3!
    ZERO(Rotations.of(0), RotationsPerSecond.of(0)),
    REVERSEINTAKE(Rotations.of(0.4), RotationsPerSecond.of(-30));
    

    public final Angle pos;
    public final AngularVelocity vel;
    
    IntakeStates(Angle pos, AngularVelocity vel) {
        this.pos = pos;
        this.vel = vel;
    }
}
 