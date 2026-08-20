package frc.robot.Subsystems.Shooter.Feeder;

import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.RPM;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;

public class FeederSubsystem extends SubsystemBase{
  public static double feederShootVelocity = 0;
  public static double feederReverseVelocity = -0;
  public FeederStates wantedFeederState = FeederStates.SAFE;
  public FeederStates FeederState = FeederStates.SAFE;
  public static double feederStatorLimit = 40; //TODO replace
  private Distance FeederDiameter = Centimeters.of(60);
  public FeederTalonFX FeederTX = new FeederTalonFX();
  public static FeederSubsystem instance;
  public static FeederSubsystem getInstance(){
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
     @Override
     public void periodic(){
      feederFlywheel.updateTelemetry();
      setFeederVelocitySetpoint(RPM.of(FeederState.enumVelocity));
      logging();
    }
     
   public void simulationPeriodic(){
      feederFlywheel.simIterate();
      FeederRun(RPM.of(FeederState.enumVelocity));
     }
    public void logging() {
//TODO log stuff
    }
}

