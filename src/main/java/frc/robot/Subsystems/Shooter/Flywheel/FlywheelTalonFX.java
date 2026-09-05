package frc.robot.Subsystems.Shooter.Flywheel;

import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;


import yams.gearing.GearBox;
import yams.gearing.MechanismGearing;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXWrapper;

public class FlywheelTalonFX{
    private TalonFX flywheelTalonFX = new TalonFX(1);
    public SmartMotorControllerConfig flywheelFollowerSmc = new SmartMotorControllerConfig()
    .withControlMode(ControlMode.CLOSED_LOOP)                                                   
    .withClosedLoopController(50,0,0)
    .withSimClosedLoopController(50,0,0)
    .withFeedforward(new SimpleMotorFeedforward(0,0,0))
    .withSimFeedforward(new SimpleMotorFeedforward(0,0, 0))
    .withTelemetry("Shooter Motor", TelemetryVerbosity.HIGH)
    .withGearing(new MechanismGearing(GearBox.fromReductionStages(1,3)))
    .withMotorInverted(false)
    .withIdleMode(MotorMode.COAST)
    .withSubsystem(FlywheelSubsystem.getInstance())
    .withTrapezoidalProfile(FlywheelSubsystem.maxVelocity, FlywheelSubsystem.maxAcceleration)
    .withStatorCurrentLimit(Amps.of(FlywheelSubsystem.FlywheelStatorLimit));
    public SmartMotorController flywheelTalon2 = new TalonFXWrapper(new TalonFX(2), DCMotor.getKrakenX60(1), flywheelFollowerSmc);
    public SmartMotorController flywheelTalon3 = new TalonFXWrapper(new TalonFX(3), DCMotor.getKrakenX60(1), flywheelFollowerSmc);
    public SmartMotorController flywheelTalon4 = new TalonFXWrapper(new TalonFX(4), DCMotor.getKrakenX60(1), flywheelFollowerSmc);
    public SmartMotorControllerConfig flywheelMainSmc = flywheelFollowerSmc.clone()
    .withLooselyCoupledFollowers(flywheelTalon2, flywheelTalon3, flywheelTalon4);
    public SmartMotorController flywheelTalonSMC = new TalonFXWrapper(flywheelTalonFX, DCMotor.getKrakenX60(1), flywheelMainSmc);
   

}