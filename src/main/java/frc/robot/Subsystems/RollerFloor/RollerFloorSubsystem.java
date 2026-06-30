package frc.robot.Subsystems.RollerFloor;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.RollerFloor.RollerFloorStates;

public class RollerFloorSubsystem extends SubsystemBase {
    public RollerFloorStates state = RollerFloorStates.SAFE;
    public RollerFloorStates wantedRollerState = RollerFloorStates.SAFE;
    public RollerFloorStates currentRollerState = RollerFloorStates.SAFE;
    @Override
    public void periodic() {
        handleStateTransitions();
        applyStates();
        logging();
    }

    private void handleStateTransitions() {
 switch (wantedRollerState) {
            case SAFE:

            break;
            case PRELOAD:

            break;
            case SHOOT:

            break;
            case REVERSE:

            break;
            case UNJAM:

            break;
           
        }
    }

    private void applyStates () {

    }
    
    private void logging() {

    }
}
