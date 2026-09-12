package frc.robot.Subsystems.Shooter.Feeder;

import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.system.plant.DCMotor;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXWrapper;

public class FeederTalonFX {
    public SmartMotorControllerConfig FeederFollowerSMCConfig = new SmartMotorControllerConfig(FeederSubsystem.getInstance())
    .withClosedLoopController(0.1,0,0)
    .withSimClosedLoopController(0.1,0,0)
    .withControlMode(ControlMode.CLOSED_LOOP)
    .withTelemetry("feeder", TelemetryVerbosity.HIGH)
    .withMotorInverted(false)
    .withGearing(1)
    .withClosedLoopRampRate(Seconds.of(0.25))
    .withOpenLoopRampRate(Seconds.of(0.25))
    .withIdleMode(MotorMode.BRAKE)
    //.withSubsystem(FeederSubsystem.getInstance())
    ;

    public SmartMotorController FeederFollowerSmc;

    public SmartMotorControllerConfig FeederSMCConfig = FeederFollowerSMCConfig.clone()
    .withLooselyCoupledFollowers(FeederFollowerSmc);

    public SmartMotorController FeederMotor;

    public void init(){
        FeederMotor = new TalonFXWrapper(new TalonFX(6), DCMotor.getKrakenX44(1), FeederSMCConfig);
        FeederFollowerSmc = new TalonFXWrapper(new TalonFX(7), DCMotor.getKrakenX44(1), FeederFollowerSMCConfig);    
    }
}
