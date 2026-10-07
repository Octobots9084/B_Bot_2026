package frc.robot.Subsystems.Shooter.Feeder;

import static edu.wpi.first.units.Units.KilogramMetersPerSecond;
import static edu.wpi.first.units.Units.KilogramSquareMeters;
import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXWrapper;

public class FeederTalonFX {
    public SmartMotorControllerConfig FeederFollowerSMCConfig;
    public FeederTalonFX(FeederSubsystem subsystem){
     FeederFollowerSMCConfig = new SmartMotorControllerConfig()
    .withClosedLoopController(1,0,0)
    .withSimClosedLoopController(1,0,0)
    .withSimFeedforward(new SimpleMotorFeedforward(0, 0.05, 0))
    .withFeedforward(new SimpleMotorFeedforward(0, 0.05, 0))
    .withControlMode(ControlMode.CLOSED_LOOP)
    .withMomentOfInertia(KilogramSquareMeters.of(0.009))
    .withTelemetry("feeder", TelemetryVerbosity.HIGH)
    .withMotorInverted(false)
    .withGearing(1)
    .withClosedLoopRampRate(Seconds.of(0.125))
    .withOpenLoopRampRate(Seconds.of(0.125))
    .withIdleMode(MotorMode.BRAKE)
    .withSubsystem(subsystem)
    ;

    }

    public SmartMotorController FeederFollowerSmc;

    public SmartMotorControllerConfig FeederSMCConfig;

    public SmartMotorController FeederMotor;

    public void init(){
        FeederFollowerSmc = new TalonFXWrapper(new TalonFX(8), DCMotor.getKrakenX44(1), FeederFollowerSMCConfig);    

        FeederSMCConfig = FeederFollowerSMCConfig.clone()
    .withLooselyCoupledFollowers(FeederFollowerSmc);
        FeederMotor = new TalonFXWrapper(new TalonFX(7), DCMotor.getKrakenX44(1), FeederSMCConfig);
    }
}
