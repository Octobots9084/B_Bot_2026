package frc.robot.Subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;


public class IntakeSubsystem extends SubsystemBase {
    public IntakeStates state = IntakeStates.SAFE;
    public IntakeStates wantedIntakeState = IntakeStates.SAFE;
    public IntakeStates currentIntakeState = IntakeStates.SAFE;
    public TalonFX leader;


    @Override
    public void periodic() {
        handleStateTransitions();
        applyStates();
        logging();
    }

    public void handleStateTransitions() {
         switch (wantedIntakeState) {
            case SAFE:

            break;
            case ZERO:

            break;
            case ELEPHANTIASIS:

            break;
            case EXTENDED:

            break;
            case INTAKING:

            break;
            case REVERSEINTAKE:

            break;
        }

    }

    public void applyStates () {

    }

    public void logging() {

    }
}