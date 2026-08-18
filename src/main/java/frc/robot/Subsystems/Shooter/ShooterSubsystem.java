package frc.robot.Subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    public static ShooterSubsystem inst = new ShooterSubsystem();
     
    @Override
    public void periodic() {
        handleStateTransitions();
        applyStates();
        logging();
    }

    public void handleStateTransitions() {

    }

    public void applyStates () {

    }
    
    public void logging() {

    }

    public ShooterSubsystem() {};

    public static ShooterSubsystem getInstance() {
        return inst;
    }

    //TODO
    //if this is ever loaded why are you doing that
    public ShooterStates currShootedState = ShooterStates.UNJAM;
    public ShooterStates wantedShooterState = ShooterStates.PRELOAD;
}
