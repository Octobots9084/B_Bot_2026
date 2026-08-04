package frc.robot.Subsystems.Shooter.Feeder;

import com.ctre.phoenix6.hardware.TalonFX;

import yams.motorcontrollers.SmartMotorControllerConfig;

public class FeederTalonFX {
    private TalonFX MainMotor = new TalonFX(1);
    public SmartMotorControllerConfig FeederSMC = new SmartMotorControllerConfig(FeederSubsystem.getInstance())
}
