package frc.robot.Subsystems.Shooter.Hood;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Pounds;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.ArmFeedforward;
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

public class HoodTalonFX {
    public TalonFX hoodTalonFX = new TalonFX(5);
    public static double hoodRadius = 15.24; //TODO harass jett about ts
    public static double hoodMass = 1;
    public SmartMotorControllerConfig HoodSMC = new SmartMotorControllerConfig(HoodSubsystem.getInstanceHood())
    .withControlMode(ControlMode.CLOSED_LOOP)
    .withGearing(new MechanismGearing(GearBox.fromReductionStages(1,1)))
    .withClosedLoopController(0.5,0,0.01)
    .withFeedforward(new ArmFeedforward(0.1, 0, 0))
    //.withSoftLimit()
    //withMomentOfInertia()
    .withStatorCurrentLimit(Amps.of(HoodSubsystem.HoodStatorLimit))
    .withClosedLoopTolerance(Degrees.of(2))
    .withIdleMode(MotorMode.BRAKE)
    .withMomentOfInertia(Centimeters.of(hoodRadius), Pounds.of(hoodMass))
    .withTelemetry("armMotor", TelemetryVerbosity.HIGH);
    public SmartMotorController HoodMotorSMC = new TalonFXWrapper(hoodTalonFX, DCMotor.getKrakenX60(1), HoodSMC);


}