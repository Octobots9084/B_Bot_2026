package frc.robot.Subsystems.Shooter.Flywheel;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.RPM;

import org.littletonrobotics.junction.Logger;
import frc.robot.Constants;


import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
public class FlywheelSubsystem extends SubsystemBase{
   public static LinearAcceleration maxAcceleration = MetersPerSecondPerSecond.of(0);
   public static LinearVelocity maxVelocity = MetersPerSecond.of(20);
   public static double FlywheelStatorLimit = 40; //replace with real
   public static FlywheelStates currentState = FlywheelStates.SAFE;
   public static FlywheelStates wantedFlywheelState = FlywheelStates.SAFE;
   public static FlywheelSubsystem instance = null;
   public static double FlywheelCustomVelocity = 0.0;
   public static double FlywheelDiameter = 10.16;
   public FlyWheel shooterFlywheel;
   private final FlyWheelConfig flyWheelConfig;
   public FlywheelTalonFX TX;
   
    public FlywheelSubsystem(){
      instance = this;
      TX = new FlywheelTalonFX(this);
      TX.init();
      flyWheelConfig = new FlyWheelConfig()
      .withDiameter(Centimeters.of(FlywheelDiameter))
      .withTelemetry("flywheelMech", TelemetryVerbosity.HIGH);
      shooterFlywheel = new FlyWheel(flyWheelConfig, TX.flywheelTalonSMC);
    }
    
    public static FlywheelSubsystem getInstance(){
      return instance;
    }

     public AngularVelocity getFlywheelVelocity() {
        return shooterFlywheel.getSpeed();
     }
   public void setDutyCycle(double cycle){
         shooterFlywheel.setDutyCycleSetpoint(cycle);
      }
     public Command FlywheelRun(double rpm){
        return shooterFlywheel.run(RPM.of(rpm));
     }

     public void setFlywheelVelocitySetpoint(double rpm){ 
         shooterFlywheel.setMechanismVelocitySetpoint(RPM.of(rpm));
     }
     @Override
     public void periodic(){
      shooterFlywheel.updateTelemetry();
      log();
     }
     @Override
   public void simulationPeriodic(){
      shooterFlywheel.updateTelemetry();
      shooterFlywheel.simIterate();
     
        }

   public void log() {
        Logger.recordOutput("wantedState", wantedFlywheelState);
        Logger.recordOutput("currentState", currentState);
        Logger.recordOutput("Flywheel Velocity", getFlywheelVelocity());
        Logger.recordOutput("Motor Temperature", TX.flywheelTalonSMC.getTemperature());
        Logger.recordOutput("Motor Voltage", TX.flywheelTalonSMC.getVoltage());
        Logger.recordOutput("Motor Position", TX.flywheelTalonSMC.getRotorPosition());
        
   }
   
}     