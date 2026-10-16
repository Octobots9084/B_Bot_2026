
package frc.robot.Subsystems.Intake;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;


public enum IntakeStates{
    SAFE(Degrees.of(0), RotationsPerSecond.of(0)),
    EXTENDED(Degrees.of(130), RotationsPerSecond.of(0)),
    INTAKING(Degrees.of(130), RotationsPerSecond.of(1)),
    ELEPHANTIASIS(Degrees.of(40), RotationsPerSecond.of(0)),//MK 3!
    ZERO(Degrees.of(0), RotationsPerSecond.of(0)),
    REVERSEINTAKE(Degrees.of(130), RotationsPerSecond.of(-1));
    

    public final Angle pos;
    public final AngularVelocity vel;
    
    IntakeStates(Angle pos, AngularVelocity vel) {
        this.pos = pos;
        this.vel = vel;
    }
}
 