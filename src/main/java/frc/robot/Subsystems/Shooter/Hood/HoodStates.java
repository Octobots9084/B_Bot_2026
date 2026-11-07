  package frc.robot.Subsystems.Shooter.Hood;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.units.measure.Angle;
   
                                                                                                                                                                                                                                                                  

//TODO ADD UNITS
public enum HoodStates {
    SAFE(Radians.of(0)), 
    UPPIES(Rotations.of(0.65)),
    FIXEDFIRE(Radians.of(Math.PI/2));
    public final Angle enumAngle;
    private HoodStates(Angle enumAngle){
        this.enumAngle = enumAngle;
    }
}
