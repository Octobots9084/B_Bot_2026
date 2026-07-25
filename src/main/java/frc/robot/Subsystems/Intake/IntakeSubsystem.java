package frc.robot.Subsystems.Intake;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.gearing.MechanismGearing;
import yams.mechanisms.config.ArmConfig;
import yams.mechanisms.config.PivotConfig;
import yams.mechanisms.positional.Arm;
import yams.mechanisms.positional.Pivot;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.local.SparkWrapper;
import yams.motorcontrollers.remote.TalonFXWrapper;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.Seconds;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.spark.SparkLowLevel.MotorType;
//import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.spark.SparkMax;

import yams.gearing.GearBox;


public class IntakeSubsystem extends SubsystemBase {
    public IntakeStates state = IntakeStates.SAFE;
    public IntakeStates wantedIntakeState = IntakeStates.ZERO;
    public IntakeStates currentIntakeState = IntakeStates.ZERO;
    public IntakeIO io;

    private SmartMotorControllerConfig smcConfigPivot = new SmartMotorControllerConfig(this)
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

private SmartMotorControllerConfig smcConfigRoller = new SmartMotorControllerConfig(this)
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
        .withFollowers(Pair.of(((IntakeIOSystem) io).rollerSpinner, false));

    // Create our SmartMotorController from our Spark and config with the NEO.
    public SmartMotorController pivotSMC = new TalonFXWrapper(((IntakeIOSystem) io).rollerMover,DCMotor.getKrakenX60(1),smcConfigPivot);
    public SmartMotorController rollerSMC = new TalonFXWrapper(((IntakeIOSystem) io).rollerMover,DCMotor.getKrakenX60(1),smcConfigRoller);

    private PivotConfig config = new PivotConfig()
    // Hard limit is applied to the simulation.
    // Length and mass of your arm for sim.
    // Telemetry name and verbosity for the arm.
    .withTelemetry("Intake", TelemetryVerbosity.HIGH);

    yams.mechanisms.positional.Pivot asd = new Pivot(config, pivotSMC);




    public IntakeSubsystem() {
        io = new IntakeIOSystem();
    }


    @Override
    public void periodic() {
        handleStateTransitions();
        applyStates();
        logging();
    }

    public void handleStateTransitions() {
         switch (wantedIntakeState) {
            case SAFE:
                
                currentIntakeState = wantedIntakeState;
            break;
            case ZERO:
                if (currentIntakeState != IntakeStates.SAFE) break;
                currentIntakeState = wantedIntakeState;
            break;
            case ELEPHANTIASIS:
                if (currentIntakeState != IntakeStates.SAFE &&
                    currentIntakeState != IntakeStates.REVERSEINTAKE &&
                    currentIntakeState != IntakeStates.INTAKING)
                        break;

                currentIntakeState = wantedIntakeState;
            break;
            case EXTENDED:
                if (currentIntakeState != IntakeStates.INTAKING &&
                    currentIntakeState != IntakeStates.SAFE &&
                    currentIntakeState != IntakeStates.REVERSEINTAKE) 
                        break;
            currentIntakeState = wantedIntakeState;
            break;
            case INTAKING:
                if (currentIntakeState != IntakeStates.EXTENDED && 
                    currentIntakeState != IntakeStates.ELEPHANTIASIS &&
                    currentIntakeState != IntakeStates.REVERSEINTAKE && 
                    currentIntakeState != IntakeStates.SAFE)
                        break;
            currentIntakeState = wantedIntakeState;

            break;
            case REVERSEINTAKE:
                if (currentIntakeState != IntakeStates.EXTENDED && 
                    currentIntakeState != IntakeStates.ELEPHANTIASIS &&
                    currentIntakeState != IntakeStates.INTAKING && 
                    currentIntakeState != IntakeStates.SAFE)
                        break;
            currentIntakeState = wantedIntakeState;

            break;
        }

    }

    public void applyStates() {
        if (currentIntakeState.pos != null) io.moveRollerToPos(currentIntakeState.pos /*always 0 or 1 ¯\_(ツ)_/¯ */ * 130 * 1 /* gear ratio */ / 360 /* degrees to rotations*/);
        if (currentIntakeState.vel != null) io.spinRollers(currentIntakeState.vel * 5/4);

        switch(currentIntakeState) {
            case ELEPHANTIASIS:
                // i was going to name this elephantimer but variableless worked just fine :(
                io.moveRollerToPos((double) (System.currentTimeMillis() % 1000 > 500 ? 130 * 1 / 360 : 0));
                break;
                
            case EXTENDED, INTAKING, REVERSEINTAKE, SAFE, ZERO: break;
            default: throw new RuntimeException("If you see this message, current state is probably null: " + currentIntakeState + ". Anyway, this should never be reached.");

        }
    }

    public void logging() {
        Logger.recordOutput("currState", currentIntakeState);
        Logger.recordOutput("wantState", wantedIntakeState);

        Logger.recordOutput("currRPS", ((IntakeIOSystem) io).rollerSpinner.getVelocity().getValueAsDouble());
        Logger.recordOutput("currAngle (0-130)", ((IntakeIOSystem) io).rollerMover.getPosition().getValueAsDouble());

        io.log();
    }


    public IntakeStates getState() {
        return state;
    }


    public void setState(IntakeStates state) {
        this.state = state;
    }


    public IntakeStates getWantedIntakeState() {
        return wantedIntakeState;
    }


    public void setWantedIntakeState(IntakeStates wantedIntakeState) {
        this.wantedIntakeState = wantedIntakeState;
    }


    public IntakeStates getCurrentIntakeState() {
        return currentIntakeState;
    }

    /*Don't even think about it. */
    public void setCurrentIntakeState(IntakeStates currentIntakeState) {
        this.currentIntakeState = currentIntakeState;
    }
}