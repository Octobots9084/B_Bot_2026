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

public class FlywheelTalonFX{
    private TalonFX flywheelTalonFX = new TalonFX(1);
    public SmartMotorControllerConfig flywheelSmc = new SmartMotorControllerConfig(FlywheelSubsystem.getInstance())
    .withControlMode(ControlMode.CLOSED_LOOP)                                                   
    .withClosedLoopController(50,0,0)
    .withSimClosedLoopController(50,0,0)
    .withFeedforward(new SimpleMotorFeedforward(0,0,0))
    .withSimFeedforward(new SimpleMotorFeedforward(0,0, 0))
    .withTelemetry("Shooter Motor", TelemetryVerbosity.HIGH)
    .withGearing(new MechanismGearing(GearBox.fromReductionStages(1,3)))
    .withMotorInverted(false)
    .withIdleMode(MotorMode.COAST)
    .withStatorCurrentLimit(Amps.of(FlywheelSubsystem.FlywheelStatorLimit));
    
    public SmartMotorController flywheelTalonSMC = new TalonFXWrapper(flywheelTalonFX, DCMotor.getKrakenX60(1), flywheelSmc);


}