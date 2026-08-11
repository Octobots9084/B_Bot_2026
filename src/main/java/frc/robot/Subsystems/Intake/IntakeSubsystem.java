package frc.robot.Subsystems.Intake;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
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
import static edu.wpi.first.units.Units.Radian;
import static edu.wpi.first.units.Units.Rotation;
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
    public IntakeIOTalonFX io = new IntakeIOTalonFX();
    public static IntakeSubsystem instance;
    
        public Pivot pivotYAM;
        public Pivot rollerYAM;
    
    
    
        public IntakeSubsystem() {
            instance = this;
            pivotYAM = io.motorInstPivot(this);
            rollerYAM = io.motorInstRoller(this);
        }
    
        public void rotateP(double d) {
            pivotYAM.setAngle(Rotation.of(d));
        }
        public void rotateR(double d) {
            rollerYAM.setAngle(Rotation.of(d));
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
    
            Logger.recordOutput("currRPS", ((IntakeIOTalonFX) io).rollerSpinner.getVelocity().getValueAsDouble());
            Logger.recordOutput("currAngle (0-130)", ((IntakeIOTalonFX) io).rollerMover.getPosition().getValueAsDouble());
    
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
    
        public static IntakeSubsystem getInstance() {
            return instance;
    }
}