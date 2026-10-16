package frc.robot.Subsystems.Shooter.Feeder;

import static edu.wpi.first.units.Units.KilogramMetersPerSecond;
import static edu.wpi.first.units.Units.KilogramSquareMeters;
import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.Constants;
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
    .withSimClosedLoopController(0.25,0,0)
    .withSimFeedforward(new SimpleMotorFeedforward(0, 0.15, 0))
    .withFeedforward(new SimpleMotorFeedforward(0, 0.05, 0))
    .withControlMode(ControlMode.CLOSED_LOOP)
    .withMomentOfInertia(KilogramSquareMeters.of(0.0009))
    .withTelemetry("feeder", TelemetryVerbosity.HIGH)
    .withMotorInverted(false)
    .withGearing(2/3)
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
        FeederFollowerSmc = new TalonFXWrapper(new TalonFX(Constants.feederLeftID, Constants.krakenBus), DCMotor.getKrakenX44(1), FeederFollowerSMCConfig);    

        FeederSMCConfig = FeederFollowerSMCConfig.clone()
    .withLooselyCoupledFollowers(FeederFollowerSmc);
        FeederMotor = new TalonFXWrapper(new TalonFX(Constants.feederRightID, Constants.krakenBus), DCMotor.getKrakenX44(1), FeederSMCConfig);
    }
}
