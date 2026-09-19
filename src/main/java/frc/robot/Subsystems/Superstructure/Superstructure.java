package frc.robot.Subsystems.Superstructure;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants;
import frc.robot.Subsystems.Drive.SwerveSubsystem;
import frc.robot.Subsystems.Intake.IntakeStates;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.RollerFloor.RollerFloorStates;
import frc.robot.Subsystems.RollerFloor.RollerFloorSubsystem;
import frc.robot.Subsystems.Shooter.ShooterStates;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;


public class Superstructure {
    public SuperstructureStates currentState = SuperstructureStates.ZEROING;
    public SuperstructureStates wantedState = SuperstructureStates.ZEROING;
    public IntakeStates superstructureWantedIntakeState = IntakeStates.SAFE;

    public IntakeSubsystem intake = IntakeSubsystem.getInstance();
    public ShooterSubsystem shooter = ShooterSubsystem.getInstance();
    public RollerFloorSubsystem floor = RollerFloorSubsystem.getInstance();
    public boolean InAlignedZone = true;
    public IntakeStates driverRequestedIntakeState = null;


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

    public void IsInAllianceZone(){
        //TODO Owen please add get instance to swerve
        Pose2d robotPose = SwerveSubsystem.getInstance().getRobotPose();
        if(Constants.isBlueAlliance){
            if(robotPose.getX() < Constants.blueTrenchX){
                InAlignedZone = true;
            }
            else{
                InAlignedZone = false;
            }
        }
        else{
            if(robotPose.getX() > Constants.redTrenchX) {
                InAlignedZone = true;   
            }
            else{
                InAlignedZone = false;
            }
        }
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
            case BUMP:
                if(currentState != SuperstructureStates.ZEROING && currentState != SuperstructureStates.TRENCH){
                    currentState = wantedState;
                }
                break;
            case FERRY:
                if(currentState != SuperstructureStates.ZEROING && currentState != SuperstructureStates.HUB){
                    currentState = wantedState;
                }
                break;
            case HUB:
                if(currentState != SuperstructureStates.ZEROING && currentState != SuperstructureStates.FERRY){
                    currentState = wantedState;
                }
                break;
            case SAFE:
                currentState = wantedState;
                break;
            case TRENCH:
                if(currentState != SuperstructureStates.ZEROING && currentState != SuperstructureStates.BUMP){
                    currentState = wantedState;
                }
                break;
            case ZEROING:
                if (currentState == SuperstructureStates.SAFE){
                    currentState = wantedState;
                }
                break;
            case FIXEDFIRE:
                if(currentState != SuperstructureStates.ZEROING && currentState != SuperstructureStates.TRENCH){
                    currentState = wantedState;
                }
    
            break;
            case AUTOHUB:
                if(currentState != SuperstructureStates.ZEROING && currentState != SuperstructureStates.TRENCH){
                    currentState = wantedState;
                }
            break;
            case AUTOFERRY:
                if(currentState != SuperstructureStates.ZEROING && currentState != SuperstructureStates.TRENCH){
                    currentState = wantedState;
                }
            break;
            default: 
                if (wantedState != null) currentState = wantedState;
                break;
        }
    }
    private void applyStates() {
    
        switch (currentState) {
            case BUMP:
                if (shooter.currentShooterState == ShooterStates.FIXEDFIRE || shooter.currentShooterState == ShooterStates.FERRY){
                    shooter.wantedShooterState = ShooterStates.SAFE;
                }
                    
                floor.wantedRollerState = RollerFloorStates.PRELOAD;

                if(superstructureWantedIntakeState != IntakeSubsystem.wantedIntakeState){
                    if(superstructureWantedIntakeState != null){
                        IntakeSubsystem.getInstance().setWantedIntakeState(superstructureWantedIntakeState);
                    }
                }
                break;
                
            case FERRY:
                shooter.wantedShooterState = ShooterStates.FERRY;
                floor.wantedRollerState = RollerFloorStates.SHOOT;
                
                if(superstructureWantedIntakeState != IntakeSubsystem.wantedIntakeState){
                    if(superstructureWantedIntakeState != null){
                        IntakeSubsystem.getInstance().setWantedIntakeState(superstructureWantedIntakeState);
                    }
                }

                //"unless intake commanded otherwise no reverse intake" isnt that the only way the intake reverses
                break;

            case HUB:
                shooter.wantedShooterState = ShooterStates.HUB;
                floor.wantedRollerState = RollerFloorStates.SHOOT;

                if(superstructureWantedIntakeState != IntakeSubsystem.wantedIntakeState){
                    if(superstructureWantedIntakeState != null){
                        IntakeSubsystem.getInstance().setWantedIntakeState(superstructureWantedIntakeState);
                    }
                }

                break;

            case SAFE:
                shooter.wantedShooterState = ShooterStates.SAFE;
                floor.wantedRollerState = RollerFloorStates.PRELOAD;

            
                IntakeSubsystem.getInstance().setWantedIntakeState(IntakeStates.SAFE);
                break;

            case TRENCH:
                shooter.wantedShooterState = ShooterStates.TRENCH;
                
                if(superstructureWantedIntakeState != IntakeSubsystem.wantedIntakeState){
                    if(superstructureWantedIntakeState != null){
                        IntakeSubsystem.getInstance().setWantedIntakeState(superstructureWantedIntakeState);
                    }
                }
                break;
            case ZEROING:
                //TODO when something actually exists for the zeroing algorithm
                break;
            case AUTOHUB:
                shooter.wantedShooterState = ShooterStates.HUB;
                RollerFloorSubsystem.getInstance().wantedRollerState = RollerFloorStates.SHOOT;

                if(superstructureWantedIntakeState != IntakeStates.INTAKING || superstructureWantedIntakeState != IntakeStates.ZERO){
                    if(superstructureWantedIntakeState != null){
                        IntakeSubsystem.getInstance().setWantedIntakeState(IntakeStates.ELEPHANTIASIS);
                    }
                }
            break;
            case AUTOFERRY:
                shooter.wantedShooterState = ShooterStates.HUB;
                RollerFloorSubsystem.getInstance().wantedRollerState = RollerFloorStates.SHOOT;
                if(superstructureWantedIntakeState != IntakeStates.INTAKING || superstructureWantedIntakeState != IntakeStates.ZERO){
                    if(superstructureWantedIntakeState != null){
                        IntakeSubsystem.getInstance().setWantedIntakeState(IntakeStates.ELEPHANTIASIS);
                    }
                }
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
