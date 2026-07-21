package frc.robot.Subsystems.Shooter.Flywheel;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.ArmFeedforward;
import yams.gearing.GearBox;
import yams.gearing.MechanismGearing;
import yams.mechanisms.SmartMechanism;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXWrapper;


public class FlywheelSubsystem extends SubsystemBase{
    public static double FlywheelStatorLimit = 40; //replace with real

    public static FlywheelSubsystem instance;
    public static double FlywheelCustomVelocity = 0.0;
    public FlywheelTalonFX TX = new FlywheelTalonFX();

    public FlywheelSubsystem(){}

    public static FlywheelSubsystem getInstance(){
      return instance;
    }
    private final FlyWheelConfig flyWheelConfig = new FlyWheelConfig()
    .withDiameter(Inches.of(4))
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
