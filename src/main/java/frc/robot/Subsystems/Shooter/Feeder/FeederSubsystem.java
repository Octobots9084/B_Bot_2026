package frc.robot.Subsystems.Shooter.Feeder;

import static edu.wpi.first.units.Units.Centimeters;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;

public class FeederSubsystem extends SubsystemBase{
  public static double feederStatorLimit = 40; //TODO replace
  private Distance FeederDiameter = Centimeters.of(60);
  public FeederTalonFX FeederTX = new FeederTalonFX();
  public static FeederSubsystem instance;
  public static FeederSubsystem GetFeederInstance(){
    return instance;
  }
  private final FlyWheelConfig FeederConfig = new FlyWheelConfig()
  .withDiameter(FeederDiameter)
  .withTelemetry("feederMech", TelemetryVerbosity.HIGH);
   public FlyWheel feederFlywheel = new FlyWheel(FeederConfig, FeederTX.FeederMotor);

     public AngularVelocity getFeederVelocity() {
        return feederFlywheel.getSpeed();
     }

     public Command FeederRun(AngularVelocity speed){
        return feederFlywheel.run(speed);
     }

     public void setFeederVelocitySetpoint(AngularVelocity speed){
        feederFlywheel.setMechanismVelocitySetpoint(speed);
     }
     public void feederPeriodic(){
      feederFlywheel.updateTelemetry();
     }
   public void feederSimPeriodic(){
      feederFlywheel.simIterate();
     }
  
}
