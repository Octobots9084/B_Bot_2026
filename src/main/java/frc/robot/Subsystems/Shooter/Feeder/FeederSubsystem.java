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
   public static double feederShootVelocity = 200;
   public static double feederReverseVelocity = -100;
   public FeederStates wantedFeederState = FeederStates.SAFE;
   public FeederStates FeederState = FeederStates.SAFE;
   public static double feederStatorLimit = 40; //TODO replace
   private Distance FeederDiameter = Centimeters.of(60);
   public FeederTalonFX TX;
   public static FeederSubsystem instance;
   FlyWheel feederFlywheel;

   public FeederSubsystem(){
      instance = this;
      TX = new FeederTalonFX();
      TX.init();
      final FlyWheelConfig FeederConfig = new FlyWheelConfig()
      .withDiameter(FeederDiameter)
      .withTelemetry("feederMech", TelemetryVerbosity.HIGH);
      FlyWheel feederFlywheel = new FlyWheel(FeederConfig, TX.FeederMotor);

   }
  public static FeederSubsystem getInstance(){
    return instance;
  }

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
      logging();
   }
     
   public void simulationPeriodic(){
      feederFlywheel.simIterate();
   }

   public void logging() {
      //TODO log stuff
    }
}

