package frc.robot.Subsystems.Intake;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import yams.gearing.MechanismGearing;
import yams.mechanisms.config.ArmConfig;
import yams.mechanisms.config.PivotConfig;
import yams.mechanisms.positional.Arm;
import yams.mechanisms.positional.Pivot;
import yams.mechanisms.velocity.FlyWheel;
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
    public static IntakeStates wantedIntakeState = IntakeStates.SAFE;
    public static Angle intakeZeroingDegree = Degrees.of(0); //todo replace with real values
    public IntakeStates currentIntakeState = IntakeStates.SAFE;
    public IntakeIOTalonFX io = new IntakeIOTalonFX();
    public static IntakeSubsystem instance;
    public static boolean alreadyZeroed = false;
        public Pivot pivotYAM;
        public FlyWheel rollerYAM;
    
    
    
        public IntakeSubsystem() {
            instance = this;
            pivotYAM = io.motorInstPivot(this);
            rollerYAM = io.motorInstRoller(this);
        }
    
        public Command rotateP(double d) {
            return pivotYAM.set(d);
        }
        public Command rotateR(double d) {
            return rollerYAM.set(d);
        }
        @Override
        public void simulationPeriodic() {
            pivotYAM.updateTelemetry();
            rollerYAM.updateTelemetry();
            pivotYAM.simIterate();
            rollerYAM.simIterate();
        }
    
        @Override
        public void periodic() {
            handleStateTransitions();
            applyStates();
            logging();
        }

        int iterator = 0;
        public Command swapState() {
            return new InstantCommand(() -> {
                wantedIntakeState = IntakeStates.values()[iterator++];
                iterator %= IntakeStates.values().length;
                SmartDashboard.putString("Intake State", wantedIntakeState.toString());
            });
        }
    
        public void handleStateTransitions() {
             switch (wantedIntakeState) {
                case SAFE:
                    currentIntakeState = wantedIntakeState;
                break;
                case ZERO:
                    if (currentIntakeState == IntakeStates.SAFE){
                    currentIntakeState = wantedIntakeState;
                    }
                break;
                case ELEPHANTIASIS:
                    if (currentIntakeState == IntakeStates.EXTENDED || 
                    currentIntakeState == IntakeStates.INTAKING || 
                    currentIntakeState == IntakeStates.REVERSEINTAKE){
                        currentIntakeState = wantedIntakeState;
                    }
                    break;
                case EXTENDED:
                    if (currentIntakeState != IntakeStates.ZERO){
                        currentIntakeState = wantedIntakeState;
                    }
                    break;
                case INTAKING:
                    if (currentIntakeState != IntakeStates.ZERO){
                        currentIntakeState = wantedIntakeState;
                    }
                    break;
                case REVERSEINTAKE:
                    if (currentIntakeState != IntakeStates.ZERO){
                        currentIntakeState = wantedIntakeState;
                    }
                    break;
            }
    
        }
    
        public void applyStates() {
            if (currentIntakeState.pos != null){
            simFriendlyPos(currentIntakeState.pos/6 /*always 0 or 1 ¯\_(ツ)_/¯ */ * 130 * 1 /* gear ratio */ / 360 /* degrees to rotations*/);
            }
            if (currentIntakeState.vel != null){ 
                simFriendlySpin(currentIntakeState.vel/6 * 5/4);
            }
    
    
            switch(currentIntakeState) {
                case ELEPHANTIASIS:
                    // i was going to name this elephantimer but variableless worked just fine :(
                    simFriendlyPos((double) (System.currentTimeMillis() % 1000 > 500 ? 1 : -1));
                    simFriendlySpin((double) (System.currentTimeMillis() + 250 % 1000 > 500 ? 1*130*1/360/6 : -1*130*1/360/6));

                    break;
                    
                case EXTENDED, INTAKING, REVERSEINTAKE, SAFE: 
                break;

                case ZERO:
                if(!alreadyZeroed){
                    alreadyZeroed = true;
                    pivotYAM.setAngle(intakeZeroingDegree); 
                    //we know where we are cuz we set pos so now just go to safe because thats where wed be if intake was pushed all the way in
                    wantedIntakeState = IntakeStates.SAFE;
                }

                default: throw new RuntimeException("If you see this message, current state is probably null: " + currentIntakeState + ". Anyway, this should never be reached.🙈");
    
            }
        }

        public void simFriendlyPos(double d) {
            if (Robot.isSimulation()) rotateP(d).execute();
            else io.moveRollerToPos(d);

        }

        public void simFriendlySpin(double d) {
            if (Robot.isSimulation()) rotateR(d).execute();
            else io.spinRollers(d);
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

        public Command setDutyCycle(double d) {
           
        return Commands.sequence(pivotYAM.set(d), rollerYAM.set(d));
           
        }
}