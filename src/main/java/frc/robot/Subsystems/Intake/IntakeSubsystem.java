package frc.robot.Subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;


public class IntakeSubsystem extends SubsystemBase {
    public IntakeStates state = IntakeStates.SAFE;

    public TalonFX leader;


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