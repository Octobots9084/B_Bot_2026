package frc.robot.Subsystems.Intake;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import java.lang.reflect.Field;

import org.dyn4j.geometry.Rotation;
import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import frc.robot.Constants;
import yams.gearing.GearBox;
import yams.gearing.MechanismGearing;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.config.PivotConfig;
import yams.mechanisms.positional.Pivot;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXWrapper;

public class IntakeIOTalonFX {

        // Create our SmartMotorController from our Spark and config with the NEO.
    
        private PivotConfig config = new PivotConfig()
        // Hard limit is applied to the simulation.
        // Length and mass of your arm for sim.
        // Telemetry name and verbosity for the arm.
        .withHardLimits(Degrees.of(0), Degrees.of(360))
        .withTelemetry("IntakePivot", TelemetryVerbosity.HIGH);
        
      private FlyWheelConfig roller = new FlyWheelConfig()
        // Hard limit is applied to the simulation.
        // Length and mass of your arm for sim.
        // Telemetry name and verbosity for the arm.
        .withTelemetry("IntakeRoller", TelemetryVerbosity.HIGH);
      


    public TalonFX pivotMotor;
    public TalonFX pivotFollowerMotor;
    public TalonFX rollerMotor;
    public TalonFX rollerFollowerMotor;




    public IntakeIOTalonFX() {
        //todo get actual device ids that probably arent taken
        pivotMotor = new TalonFX(Constants.intakePivotID, Constants.krakenBus);
        pivotFollowerMotor = new TalonFX(Constants.intakePivotFollowerID, Constants.krakenBus);
        rollerMotor = new TalonFX(Constants.intakeRollerID, Constants.krakenBus);
        rollerFollowerMotor = new TalonFX(Constants.intakeRollerFollowerID, Constants.krakenBus);
    }

    public void log() {
        Logger.recordOutput("Pivot Temp/", pivotMotor.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("Pivot Position/", pivotMotor.getPosition().getValueAsDouble());
        Logger.recordOutput("Pivot Voltage/", pivotMotor.getMotorVoltage().getValueAsDouble());

        Logger.recordOutput("Roller Temp", rollerMotor.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("Roller Position", rollerMotor.getPosition().getValueAsDouble());
        Logger.recordOutput("Roller Voltage", rollerMotor.getMotorVoltage().getValueAsDouble());

        Logger.recordOutput("Roller Follower Temp", rollerFollowerMotor.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("Roller Follower Position", rollerFollowerMotor.getPosition().getValueAsDouble());
        Logger.recordOutput("Roller Follower Voltage", rollerFollowerMotor.getMotorVoltage().getValueAsDouble());

        Logger.recordOutput("Pivot Follower Temp", rollerFollowerMotor.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("Pivot Follower Position", rollerFollowerMotor.getPosition().getValueAsDouble());
        Logger.recordOutput("Pivot Follower Voltage", rollerFollowerMotor.getMotorVoltage().getValueAsDouble());
    }

    public Pivot motorInstPivot(IntakeSubsystem sub) {

        SmartMotorControllerConfig smcc = new SmartMotorControllerConfig(sub)
            .withControlMode(ControlMode.CLOSED_LOOP)
            // Feedback Constants (PID Constants)
            .withClosedLoopController(75, 0, 0)
            .withTrapezoidalProfile(RotationsPerSecond.of(5), RotationsPerSecondPerSecond.of(2.5))
            .withSimClosedLoopController(1, 0, 0)
            // Feedforward Constants (? TODO no clue what these are)
            // Telemetry name and verbosity level
            .withTelemetry("IntakePivot", TelemetryVerbosity.HIGH)
            // Gearing from the motor rotor to final shaft.
            .withGearing(new MechanismGearing(GearBox.fromReductionStages(5, 5, 1.2)))
            // Motor properties to prevent over currenting.
            .withMotorInverted(true)
            .withIdleMode(MotorMode.COAST)
            .withStatorCurrentLimit(Amps.of(60))
            .withSupplyCurrentLimit(Amps.of(40))
            .withClosedLoopRampRate(Seconds.of(0.25))
            .withOpenLoopRampRate(Seconds.of(0.25))
            // Starting position is where your arm starts
            .withStartingPosition(Rotations.of(0))
            // Pass the follower motor and whether it's inverted relative to the main motor
            .withFollowers(Pair.of(this.pivotFollowerMotor, true)); // Or use .withLooselyCoupledFollowers() depending on your exact YAMS version needs, making sure to pass the Pair

        SmartMotorController SMC = new TalonFXWrapper(pivotMotor, DCMotor.getKrakenX60(1), smcc);
        return new Pivot(config, SMC);
    }

    public FlyWheel motorInstRoller(IntakeSubsystem sub) {


        SmartMotorControllerConfig smcc = new SmartMotorControllerConfig(sub)
            .withControlMode(ControlMode.CLOSED_LOOP)
            // Feedback Constants (PID Constants)
            .withClosedLoopController(0.5, 0, 0)
            .withFeedforward(new SimpleMotorFeedforward(0, 0.5))
            .withSimClosedLoopController(1, 0, 0)
            // Feedforward Constants (? TODO no clue what these are)
            // Telemetry name and verbosity level
            .withTelemetry("IntakeRoller", TelemetryVerbosity.HIGH)
            // Gearing from the motor rotor to final shaft.
            // In this example GearBox.fromReductionStages(3,4) is the same as GearBox.fromStages("3:1","4:1") which corresponds to the gearbox attached to your motor.
            .withGearing(new MechanismGearing(GearBox.fromReductionStages(12.0/11.0)))
            // Motor properties to prevent over currenting.
            .withMotorInverted(false)
            .withIdleMode(MotorMode.COAST)
            .withStatorCurrentLimit(Amps.of(60))
            .withSupplyCurrentLimit(Amps.of(20))
            .withClosedLoopRampRate(Seconds.of(0.25))
            .withOpenLoopRampRate(Seconds.of(0.25))
            // Starting position is where your arm starts
            .withFollowers(Pair.of(this.rollerFollowerMotor, true));

        SmartMotorController SMC = new TalonFXWrapper(rollerMotor,DCMotor.getKrakenX60(1),smcc);    
        return new FlyWheel(roller, SMC);

    }
}