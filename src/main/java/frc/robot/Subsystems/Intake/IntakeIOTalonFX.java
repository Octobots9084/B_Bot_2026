package frc.robot.Subsystems.Intake;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import java.lang.reflect.Field;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.Constants;
import yams.gearing.GearBox;
import yams.gearing.MechanismGearing;
import yams.mechanisms.config.PivotConfig;
import yams.mechanisms.positional.Pivot;
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
        .withTelemetry("Intake", TelemetryVerbosity.HIGH);
        
    


    public TalonFX rollerMover;
    public TalonFX rollerSpinner;
    public TalonFX rollerSpinnerF;

    MotionMagicVoltage moverMagic; 
    MotionMagicVelocityVoltage spinnerMagic;

    Follower rollerMoverFollower;
    Follower rollerSpinnerFollower;




    public IntakeIOTalonFX() {
        //todo get actual device ids that probably arent taken
        rollerMover = new TalonFX(0, Constants.krakenBus);
        rollerSpinner = new TalonFX(1, Constants.krakenBus);
        rollerSpinnerF = new TalonFX(3, Constants.krakenBus);

        rollerMoverFollower = new Follower(0, MotorAlignmentValue.Opposed); //todo this is my guess but idk
        rollerSpinnerFollower = new Follower(1, MotorAlignmentValue.Opposed); //again

        moverMagic = new MotionMagicVoltage(0);
        spinnerMagic = new MotionMagicVelocityVoltage(0);

        config();

        rollerMover.setControl(moverMagic);
        rollerSpinner.setControl(spinnerMagic);

        rollerSpinnerF.setControl(rollerSpinnerFollower);

    
    }

    //excuse the weird setup - this is to create slots to fill 
    public void config() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.CurrentLimits.StatorCurrentLimit = 40;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        
        rollerMover.getConfigurator().apply(config);


        config = new TalonFXConfiguration(); 

        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.CurrentLimits.StatorCurrentLimit = 40;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        
        rollerSpinner.getConfigurator().apply(config);

    }


    public void spinRollers(double b) {
        spinnerMagic.Velocity = b;
        rollerSpinner.setControl(spinnerMagic);
    }

    
    /**TODO figure out actual positions */
    public void moveRollerToPos(Double pos) {
        moverMagic.Position = pos;
        rollerSpinner.setControl(moverMagic);
    }

    public void log() {
        Logger.recordOutput("mover/", rollerMover.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("mover/", rollerMover.getPosition().getValueAsDouble());
        Logger.recordOutput("mover/", rollerMover.getMotorVoltage().getValueAsDouble());

        Logger.recordOutput("spinner/", rollerSpinner.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("spinner/", rollerSpinner.getPosition().getValueAsDouble());
        Logger.recordOutput("spinner/", rollerSpinner.getMotorVoltage().getValueAsDouble());

        Logger.recordOutput("spinnerF/", rollerSpinnerF.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("spinnerF/", rollerSpinnerF.getPosition().getValueAsDouble());
        Logger.recordOutput("spinnerF/", rollerSpinnerF.getMotorVoltage().getValueAsDouble());
    }

    public Pivot motorInstPivot(IntakeSubsystem sub) {

       
        SmartMotorControllerConfig smcc =  new SmartMotorControllerConfig(sub)
            .withControlMode(ControlMode.CLOSED_LOOP)
            // Feedback Constants (PID Constants)
            .withClosedLoopController(50, 0, 0)
            .withTrapezoidalProfile(DegreesPerSecond.of(90), DegreesPerSecondPerSecond.of(45))
            .withSimClosedLoopController(50, 0, 0)
            // Feedforward Constants (? TODO no clue what these are)
            // Telemetry name and verbosity level
            .withTelemetry("IntakePivot", TelemetryVerbosity.HIGH)
            // Gearing from the motor rotor to final shaft.
            // In this example GearBox.fromReductionStages(3,4) is the same as GearBox.fromStages("3:1","4:1") which corresponds to the gearbox attached to your motor.
            .withGearing(new MechanismGearing(GearBox.fromReductionStages(5, 4)))
            // Motor properties to prevent over currenting.
            .withMotorInverted(false)
            .withIdleMode(MotorMode.COAST)
            .withStatorCurrentLimit(Amps.of(40))
            .withClosedLoopRampRate(Seconds.of(0.25))
            .withOpenLoopRampRate(Seconds.of(0.25))
            // Starting position is where your arm starts
            .withStartingPosition(Degrees.of(0));
            // Soft limit is applied to the SmartMotorControllers PID
            //screw soft limits, this is a roller
            //.withSoftLimits(Degrees.of(-20), Degrees.of(10));


        SmartMotorController SMC = new TalonFXWrapper(rollerMover,DCMotor.getKrakenX60(1),smcc);
        return new Pivot(config, SMC);

    }

    public Pivot motorInstRoller(IntakeSubsystem sub) {


        SmartMotorControllerConfig smcc = new SmartMotorControllerConfig(sub)
            .withControlMode(ControlMode.CLOSED_LOOP)
            // Feedback Constants (PID Constants)
            .withClosedLoopController(50, 0, 0)
            .withTrapezoidalProfile(DegreesPerSecond.of(90), DegreesPerSecondPerSecond.of(45))
            .withSimClosedLoopController(50, 0, 0)
            // Feedforward Constants (? TODO no clue what these are)
            // Telemetry name and verbosity level
            .withTelemetry("IntakePivot", TelemetryVerbosity.HIGH)
            // Gearing from the motor rotor to final shaft.
            // In this example GearBox.fromReductionStages(3,4) is the same as GearBox.fromStages("3:1","4:1") which corresponds to the gearbox attached to your motor.
            .withGearing(new MechanismGearing(GearBox.fromReductionStages(30, 1)))
            // Motor properties to prevent over currenting.
            .withMotorInverted(false)
            .withIdleMode(MotorMode.COAST)
            .withStatorCurrentLimit(Amps.of(40))
            .withClosedLoopRampRate(Seconds.of(0.25))
            .withOpenLoopRampRate(Seconds.of(0.25))
            // Starting position is where your arm starts
            .withStartingPosition(Degrees.of(0))
            .withFollowers(Pair.of(this.rollerSpinner, false));

        SmartMotorController SMC = new TalonFXWrapper(rollerMover,DCMotor.getKrakenX60(1),smcc);    
        return new Pivot(config.clone(), SMC);

    }
}



