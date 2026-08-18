package frc.robot.Subsystems.Superstructure;

import org.littletonrobotics.junction.Logger;

import frc.robot.Subsystems.Intake.IntakeStates;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.RollerFloor.RollerFloorStates;
import frc.robot.Subsystems.RollerFloor.RollerFloorSubsystem;
import frc.robot.Subsystems.Shooter.ShooterStates;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;


public class Superstructure {
    public SuperstructureStates currentState = SuperstructureStates.ZEROING;
    public SuperstructureStates wantedState = SuperstructureStates.ZEROING;

    public IntakeSubsystem intake = IntakeSubsystem.getInstance();
    public ShooterSubsystem shooter = ShooterSubsystem.getInstance();
    public RollerFloorSubsystem floor = RollerFloorSubsystem.getInstance();


    public static Superstructure currentInstance;
  

    //@Override
     public void periodic() {
         handleStateTransitions();
         applyStates();

        Logger.recordOutput("wantedState", this.wantedState);
        Logger.recordOutput("currentState", this.currentState);
        
     }

    public Superstructure() {
        currentInstance = this;
    }

    public static void setInstance(Superstructure instance) {
        currentInstance = instance;
    }

    public static Superstructure getInstance() {
        return currentInstance;
    }

    public SuperstructureStates getCurrentState() {
        return currentState;
    }

    public SuperstructureStates getWantedState() {
        return wantedState;
    }

    public void setCurrentState(SuperstructureStates state) {
        currentState = state;
    }

    public void setWantedState(SuperstructureStates state) {
        wantedState = state;
    }

    private void handleStateTransitions() {
        switch (wantedState) {
            case AUTOFERRY:
                currentState = wantedState;
                break;
            case AUTOHUB:
                currentState = wantedState;
                break;
            case BUMP:
                currentState = wantedState;
                break;
            case FERRY:
                currentState = wantedState;
                break;
            case HUB:
                currentState = wantedState;
                break;
            case SAFE:
                currentState = wantedState;
                break;
            case TRENCH:
                currentState = wantedState;
                break;
            case ZEROING:
                currentState = wantedState;
                break;
            default: 
                if (wantedState != null) currentState = wantedState;
                break;
        }
    }
    private void applyStates() {
    
        switch (currentState) {
            case AUTOFERRY:
                shooter.wantedShooterState = ShooterStates.SAFE;
                intake.wantedIntakeState = IntakeStates.ELEPHANTIASIS;
                floor.wantedRollerState = RollerFloorStates.SHOOT;

            case AUTOHUB:
                shooter.wantedShooterState = ShooterStates.SHOOT;
                intake.wantedIntakeState = IntakeStates.ELEPHANTIASIS;
                floor.wantedRollerState = RollerFloorStates.SHOOT;

            case BUMP:
                shooter.wantedShooterState = ShooterStates.PRELOAD;
                intake.wantedIntakeState = IntakeStates.SAFE;
                floor.wantedRollerState = RollerFloorStates.SAFE;
                
            case FERRY:
                shooter.wantedShooterState = ShooterStates.SHOOT;
                intake.wantedIntakeState = IntakeStates.ELEPHANTIASIS;
                floor.wantedRollerState = RollerFloorStates.SHOOT;
                break;

            case HUB:
                shooter.wantedShooterState = ShooterStates.SHOOT;
                intake.wantedIntakeState = IntakeStates.ELEPHANTIASIS;
                floor.wantedRollerState = RollerFloorStates.SHOOT;
                break;

            case SAFE:
                shooter.wantedShooterState = ShooterStates.SAFE;
                intake.wantedIntakeState = IntakeStates.SAFE;
                floor.wantedRollerState = RollerFloorStates.SAFE;
                break;

            case TRENCH:
                shooter.wantedShooterState = ShooterStates.SAFE;
                intake.wantedIntakeState = IntakeStates.INTAKING;
                floor.wantedRollerState = RollerFloorStates.SAFE;
                break;
            case ZEROING:
                //TODO when something actually exists for the zeroing algorithm
                break;

            default: throw new IllegalStateException("Illegal current state for Superstructure! State: " + currentState);   
        }
                
        // if (shooter.wantedShooterState == ShooterStates.SHOOT && false /*Detect if bot is not ready; uses CANRange?*/)
        //     shooter.wantedShooterState = ShooterStates.PRELOAD; 

        // if (false /*some key pressed? */) {
        //     shooter.wantedShooterState = ShooterStates.UNJAM;
        //     intake.wantedIntakeState = IntakeStates.ELEPHANTIASIS;
        //     floor.wantedRollerState = RollerFloorStates.UNJAM;
        // }
        // else if (false /* some key pressed? */) {
        //     shooter.wantedShooterState = ShooterStates.REVERSE;
        //     intake.wantedIntakeState = IntakeStates.REVERSEINTAKE;
        //     floor.wantedRollerState = RollerFloorStates.REVERSE;
        // }
    }
}
