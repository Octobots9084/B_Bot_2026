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
    public SmartMotorControllerConfig flywheelFollowerSmc = new SmartMotorControllerConfig()
    .withControlMode(ControlMode.CLOSED_LOOP)                                                   
    .withClosedLoopController(50,0,0)
    .withSimClosedLoopController(50,0,0)
    .withFeedforward(new SimpleMotorFeedforward(0,0,0))
    .withSimFeedforward(new SimpleMotorFeedforward(0,0, 0))
    .withTelemetry("Shooter Motor", TelemetryVerbosity.HIGH)
    .withGearing(new MechanismGearing(GearBox.fromReductionStages(1,3)))
    .withMotorInverted(false)
    .withIdleMode(MotorMode.BRAKE)
    .withSubsystem(FlywheelSubsystem.getInstance())
    .withTrapezoidalProfile(FlywheelSubsystem.maxVelocity, FlywheelSubsystem.maxAcceleration)
    .withStatorCurrentLimit(Amps.of(FlywheelSubsystem.FlywheelStatorLimit));
    public SmartMotorController flywheelTalon2;
    public SmartMotorController flywheelTalon3;
    public SmartMotorController flywheelTalon4;
    public SmartMotorControllerConfig flywheelMainSmc = flywheelFollowerSmc.clone()
    .withLooselyCoupledFollowers(flywheelTalon2, flywheelTalon3, flywheelTalon4);
    public SmartMotorController flywheelTalonSMC;
   
    public void init(){
        flywheelTalon2 = new TalonFXWrapper(new TalonFX(2), DCMotor.getKrakenX60(1), flywheelFollowerSmc);
        flywheelTalon3 = new TalonFXWrapper(new TalonFX(3), DCMotor.getKrakenX60(1), flywheelFollowerSmc);
        flywheelTalon4 = new TalonFXWrapper(new TalonFX(4), DCMotor.getKrakenX60(1), flywheelFollowerSmc);
        flywheelTalonSMC = new TalonFXWrapper(new TalonFX(1), DCMotor.getKrakenX60(1), flywheelMainSmc);

    }
}