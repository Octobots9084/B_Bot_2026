
package frc.robot.Subsystems.Intake;

public enum IntakeStates{
    SAFE(0d,0d),
    EXTENDED(1d,0d),
    INTAKING(1d,1d),
    ELEPHANTIASIS(null, 0d),//MK 3!
    ZERO(0d,0d),
    REVERSEINTAKE(1d,-1d);


    public final Double pos;
    public final Double vel;
    
    IntakeStates(Double pos, Double vel) {
        this.pos = pos;
        this.vel = vel;
    }
}
