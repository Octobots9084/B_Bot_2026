package frc.robot.Subsystems.Shooter.Feeder;

import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;

public class FeederSubsystem extends SubsystemBase{
   public FeederStates wantedFeederState = FeederStates.SAFE;
   public FeederStates FeederState = FeederStates.SAFE;
   public static double feederStatorLimit = 60; //TODO replace
   private Distance FeederDiameter = Centimeters.of(2.54);
   public FeederTalonFX TX;
   public static FeederSubsystem instance;
   public FlyWheel feederFlywheel;

   public FeederSubsystem(){
      instance = this;
      TX = new FeederTalonFX(this);
      TX.init();
      final FlyWheelConfig FeederConfig = new FlyWheelConfig()
      .withDiameter(FeederDiameter)
      .withTelemetry("feederMech", TelemetryVerbosity.HIGH);
      feederFlywheel = new FlyWheel(FeederConfig, TX.FeederMotor);

   }
  public static FeederSubsystem getInstance(){
    return instance;
  }

   public AngularVelocity getFeederVelocity() {
      return feederFlywheel.getSpeed();
   }
   public void setDutyCycle(double cycle){
      feederFlywheel.setDutyCycleSetpoint(cycle);
   }
   public Command FeederRun(AngularVelocity speed){
      return feederFlywheel.run(speed);
   }

   public void setFeederVelocitySetpoint(AngularVelocity speed){
      feederFlywheel.setMechanismVelocitySetpoint(speed);
      // feederFlywheel.setVoltageSetpoint(Volts.of(5));
      //feederFlywheel.setVoltage(Voltage.ofBaseUnits(5, Volts));
   }

   @Override
   public void periodic(){
      feederFlywheel.updateTelemetry();
      log();
   }
     
   public void simulationPeriodic(){
      feederFlywheel.simIterate();
   }

    public void log() {
        Logger.recordOutput("FeederwantedState", wantedFeederState);
        Logger.recordOutput("FeedercurrentState", FeederState);
        Logger.recordOutput("Feeder Velocity", getFeederVelocity());
        Logger.recordOutput("Feeder Motor Voltage", TX.FeederMotor.getVoltage());
        Logger.recordOutput("Feeder Motor Temperature", TX.FeederMotor.getTemperature());
    }

}

