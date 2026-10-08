package frc.robot.Subsystems.Shooter.Flywheel;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.KilogramSquareMeters;

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
public class FlywheelTalonFX {
    public TalonFX flywheelMotor = new TalonFX(3);
    public TalonFX flywheelMot2 = new TalonFX(4);
    public TalonFX flywheelMot3 = new TalonFX(5);
    public TalonFX flywheelMot4 = new TalonFX(6);
    public SmartMotorController flywheelTalon2;
    public SmartMotorController flywheelTalon3;
    public SmartMotorController flywheelTalon4;
    public SmartMotorController flywheelTalonSMC;
    public SmartMotorControllerConfig flywheelFollowerSmc;
    public SmartMotorControllerConfig flywheelMainSmc;

    public FlywheelTalonFX(FlywheelSubsystem subsystem) {
        flywheelFollowerSmc = new SmartMotorControllerConfig()
            .withControlMode(ControlMode.CLOSED_LOOP)
            .withClosedLoopController(0.25,0,0)
            .withSimClosedLoopController(0.25,0,0)
            .withFeedforward(new SimpleMotorFeedforward(0,0.1,0))
            .withSimFeedforward(new SimpleMotorFeedforward(0,0.1, 0))
            .withMomentOfInertia(KilogramSquareMeters.of(0.005))
            .withTelemetry("Shooter Motor", TelemetryVerbosity.HIGH)
            .withGearing(new MechanismGearing(GearBox.fromReductionStages(1,3)))
            .withMotorInverted(false)
            .withIdleMode(MotorMode.BRAKE)
            .withSubsystem(subsystem) 
            //.withTrapezoidalProfile(FlywheelSubsystem.maxVelocity, FlywheelSubsystem.maxAcceleration)
            .withStatorCurrentLimit(Amps.of(FlywheelSubsystem.FlywheelStatorLimit));
    }
   
   public void init() {

    flywheelTalon2 = new TalonFXWrapper(flywheelMot2, DCMotor.getKrakenX60(1), flywheelFollowerSmc);
    flywheelTalon3 = new TalonFXWrapper(flywheelMot3, DCMotor.getKrakenX60(1), flywheelFollowerSmc);
    flywheelTalon4 = new TalonFXWrapper(flywheelMot4, DCMotor.getKrakenX60(1), flywheelFollowerSmc);

    flywheelMainSmc = flywheelFollowerSmc.clone()
    .withLooselyCoupledFollowers(flywheelTalon2, flywheelTalon3, flywheelTalon4);
    flywheelTalonSMC = new TalonFXWrapper(flywheelMotor, DCMotor.getKrakenX60(1), flywheelMainSmc);
}

}
