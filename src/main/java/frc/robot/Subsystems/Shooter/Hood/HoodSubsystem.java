package frc.robot.Subsystems.Shooter.Hood;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Pounds;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.mechanisms.config.ArmConfig;
import yams.mechanisms.positional.Arm;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;

public class HoodSubsystem extends SubsystemBase{
    public static double HoodStatorLimit = 40; //TODO replace with real stuff
    public static Angle hoodTolerance = Degrees.of(0);
    
    public HoodTalonFX HoodTX = new HoodTalonFX();
    public static HoodSubsystem instance;
    public double HoodCustomVelocity = 0d;
    public static HoodSubsystem getInstanceHood(){
        return instance;
    }

    ArmConfig HoodConfig = new ArmConfig()
    .withLength(Feet.of(HoodTX.hoodRadius))
    .withTelemetry("HoodMech", TelemetryVerbosity.HIGH);
    public Arm hood = new Arm(HoodConfig, HoodTX.HoodMotorSMC);
    
    public Command setAngle(Angle angle){
        return hood.setAngle(angle);
    }
    public Command setAngleWithTolerance(Angle angle, Angle tolerance){
        return hood.runTo(angle, tolerance);
    }
   public Angle getAngle(){
    return hood.getAngle();
   }
   public void hoodPeriodic(){
    hood.updateTelemetry();
   }
   public void hoodSimPeriodic(){
    hood.simIterate();
   }
}
