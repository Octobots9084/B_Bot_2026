package frc.robot.Subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;


public class IntakeSubsystem extends SubsystemBase {
    public IntakeStates state = IntakeStates.SAFE;
    public IntakeStates wantedIntakeState = IntakeStates.ZERO;
    public IntakeStates currentIntakeState = IntakeStates.ZERO;
    public IntakeIO io;


    public IntakeSubsystem() {
        io = new IntakeIOSystem();
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

    public void applyStates () {
        if (currentIntakeState.pos != null) io.moveRollerToPos(currentIntakeState.pos);
        if (currentIntakeState.vel != null) io.spinRollers(currentIntakeState.vel);

        switch(currentIntakeState) {
            case ELEPHANTIASIS:
                // i was going to name this elephantimer but variableless worked just fine :(
                io.moveRollerToPos((double) (System.currentTimeMillis() % 1000 > 500 ? 1 : 0));
                break;
                
            case EXTENDED, INTAKING, REVERSEINTAKE, SAFE, ZERO: break;
            default: throw new RuntimeException("If you see this message, current state is probably null: " + currentIntakeState + ". Anyway, this should never be reached.");

        }
    }

    public void logging() {
        Logger.recordOutput("currState", currentIntakeState);
        Logger.recordOutput("wantState", wantedIntakeState);

        io.log();
    }
}