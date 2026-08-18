package frc.robot.Subsystems.Shooter.Feeder;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.mechanisms.SmartMechanism;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;

public enum FeederStates {

    SAFE(0),
    FIRE(FeederSubsystem.feederShootVelocity),
    REVERSE(FeederSubsystem.feederReverseVelocity);
    public final double enumVelocity;
    private FeederStates(double enumVelocity){
        this.enumVelocity = enumVelocity;
    }

}
