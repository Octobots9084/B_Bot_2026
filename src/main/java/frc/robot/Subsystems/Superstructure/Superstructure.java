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

        if (currentState != wantedState)        
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
                if (intake.currentIntakeState == IntakeStates.REVERSEINTAKE) intake.wantedIntakeState = IntakeStates.INTAKING;
                currentState = wantedState;
                break;
            case AUTOHUB:
                if (intake.currentIntakeState == IntakeStates.REVERSEINTAKE) intake.wantedIntakeState = IntakeStates.INTAKING;
                currentState = wantedState;
                break;
            case BUMP:
                currentState = wantedState;
                break;
            case FERRY:
                if (intake.currentIntakeState == IntakeStates.REVERSEINTAKE) intake.wantedIntakeState = IntakeStates.INTAKING;
                currentState = wantedState;
                break;
            case HUB:
                if (intake.currentIntakeState == IntakeStates.REVERSEINTAKE) intake.wantedIntakeState = IntakeStates.INTAKING;
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
                floor.wantedRollerState = RollerFloorStates.SHOOT;

            case AUTOHUB:
                shooter.wantedShooterState = ShooterStates.FIXEDFIRE;
                floor.wantedRollerState = RollerFloorStates.SHOOT;

                floor.wantedRollerState = switch(shooter.currentShooterState) {
                    case FERRY -> RollerFloorStates.SHOOT;
                    case FIXEDFIRE -> RollerFloorStates.SHOOT;
                    case HUB -> RollerFloorStates.SHOOT;
                    case SAFE -> RollerFloorStates.SAFE;
                    case TRENCH -> RollerFloorStates.SAFE;
                    case ZEROING -> RollerFloorStates.SAFE;
                    default -> floor.wantedRollerState;        
                };

            case BUMP:
                if (shooter.currentShooterState == ShooterStates.FIXEDFIRE || shooter.currentShooterState == ShooterStates.FERRY)
                    shooter.wantedShooterState = ShooterStates.SAFE;
                    
                floor.wantedRollerState = RollerFloorStates.SAFE;

                floor.wantedRollerState = switch(intake.currentIntakeState) {
                    case ELEPHANTIASIS -> RollerFloorStates.SAFE;
                    case EXTENDED -> RollerFloorStates.SAFE;
                    case INTAKING -> RollerFloorStates.SAFE;
                    case REVERSEINTAKE -> RollerFloorStates.REVERSE;
                    case SAFE -> RollerFloorStates.SAFE;
                    case ZERO -> RollerFloorStates.SAFE;
                    default -> floor.wantedRollerState;
            
                };
                
            case FERRY:
                shooter.wantedShooterState = ShooterStates.FIXEDFIRE;
                floor.wantedRollerState = RollerFloorStates.SHOOT;
                
                floor.wantedRollerState = switch(shooter.currentShooterState) {
                    case FERRY -> RollerFloorStates.SHOOT;
                    case FIXEDFIRE -> RollerFloorStates.SHOOT;
                    case HUB -> RollerFloorStates.SHOOT;
                    case SAFE -> RollerFloorStates.SAFE;
                    case TRENCH -> RollerFloorStates.SAFE;
                    case ZEROING -> RollerFloorStates.SAFE;
                    default -> floor.wantedRollerState;        
                };

                //"unless intake commanded otherwise no reverse intake" isnt that the only way the intake reverses
                break;

            case HUB:
                shooter.wantedShooterState = ShooterStates.FIXEDFIRE;
                floor.wantedRollerState = RollerFloorStates.SHOOT;

                floor.wantedRollerState = switch(shooter.currentShooterState) {
                    case FERRY -> RollerFloorStates.SHOOT;
                    case FIXEDFIRE -> RollerFloorStates.SHOOT;
                    case HUB -> RollerFloorStates.SHOOT;
                    case SAFE -> RollerFloorStates.SAFE;
                    case TRENCH -> RollerFloorStates.SAFE;
                    case ZEROING -> RollerFloorStates.SAFE;
                    default -> floor.wantedRollerState;        
                };

                break;

            case SAFE:
                shooter.wantedShooterState = ShooterStates.SAFE;
                floor.wantedRollerState = RollerFloorStates.SAFE;

                floor.wantedRollerState = switch(intake.currentIntakeState) {
                    case ELEPHANTIASIS -> RollerFloorStates.SAFE;
                    case EXTENDED -> RollerFloorStates.SAFE;
                    case INTAKING -> RollerFloorStates.SAFE;
                    case REVERSEINTAKE -> RollerFloorStates.REVERSE;
                    case SAFE -> RollerFloorStates.SAFE;
                    case ZERO -> RollerFloorStates.SAFE;
                    default -> floor.wantedRollerState;
            
                };

                break;

            case TRENCH:
                shooter.wantedShooterState = ShooterStates.TRENCH;
                
                floor.wantedRollerState = switch(intake.currentIntakeState) {
                    case ELEPHANTIASIS -> RollerFloorStates.SAFE;
                    case EXTENDED -> RollerFloorStates.SAFE;
                    case INTAKING -> RollerFloorStates.SAFE;
                    case REVERSEINTAKE -> RollerFloorStates.REVERSE;
                    case SAFE -> RollerFloorStates.SAFE;
                    case ZERO -> RollerFloorStates.SAFE;
                    default -> floor.wantedRollerState;
            
                };
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
