package frc.robot.Subsystems.Shooter.Flywheel;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.Inches;

import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;


public class FlywheelSubsystem extends SubsystemBase{
    public static double FlywheelStatorLimit = 40; //replace with real

    public static FlywheelSubsystem instance;
    public static double FlywheelCustomVelocity = 0.0;
    public static double FlywheelDiameter = 10.16;
    public FlywheelTalonFX TX = new FlywheelTalonFX();

    public FlywheelSubsystem(){}

    public static FlywheelSubsystem getInstance(){
      return instance;
    }
    private final FlyWheelConfig flyWheelConfig = new FlyWheelConfig()
    .withDiameter(Centimeters.of(FlywheelDiameter))
    .withTelemetry("flywheelMech", TelemetryVerbosity.HIGH);

     public FlyWheel shooterFlywheel = new FlyWheel(flyWheelConfig, TX.flywheelTalonSMC);

     public AngularVelocity getVelocity() {
        return shooterFlywheel.getSpeed();
     }

     public Command run(AngularVelocity speed){
        return shooterFlywheel.run(speed);
     }

     public void setVelocitySetpoint(AngularVelocity speed){
        shooterFlywheel.setMechanismVelocitySetpoint(speed);
     }
     public void flywheelPeriodic(){
      shooterFlywheel.updateTelemetry();
     }
   public void flywheelSimPeriodic(){
      shooterFlywheel.simIterate();
     }
   
}
