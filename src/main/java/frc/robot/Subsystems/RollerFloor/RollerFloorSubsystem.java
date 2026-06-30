package frc.robot.Subsystems.RollerFloor;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RollerFloorSubsystem extends SubsystemBase {
    
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
}
