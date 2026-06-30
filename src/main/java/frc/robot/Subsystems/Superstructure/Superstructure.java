package frc.robot.Subsystems.Superstructure;

public class Superstructure {
    public States currentState = States.SAFE;
    public States wantedState = States.SAFE;
    public States prevState = States.SAFE;
    public IntakeStates userRequestedIntakeState = IntakeStates.SAFE;


    public static Superstructure currentInstance;
    private SwerveSubsystem swerve = SwerveSubsystem.getInstance();
    // public Climb climb = Climb.getInstance();
    public Shooter shooter = Shooter.getInstance();
    public Intake intake = Intake.getInstance();

    @Override
    public void periodic() {
        Logger.recordOutput("currentState", this.currentState);
        Logger.recordOutput("wantedState", this.wantedState);
        handleStateTransitions();
        applyStates();
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

    public States getCurrentState() {
        return currentState;
    }

    public States getWantedState() {
        return wantedState;
    }

    public void setCurrentState(States state) {
        currentState = state;
    }

    public void setWantedState(States state) {
        wantedState = state;
    }

    public void handleStateTransitions() {
        switch (wantedState) {
            case SAFE:

            break;
            case ZEROING:
            
            break;
            case AUTONOMOUS:
            
            break;
            case NEUTRAL:
            
            break;
            case ALLIANCE:
            
            break;
            case TRENCH:
            
            break;
            case BUMP:
            
            break;
        }
    }
    public void applyStates() {
        switch (currentState) {
            case SAFE:
                stateSAFE();
                break;
            case AUTONOMOUS:
                stateAUTO();
                break;
            case ZEROING:
                stateZERO();
                break;

            default:
        }


    }

    private void stateSAFE() {

    }
   
    private boolean stateZERO(){
    }
    private boolean stateAUTO(){
    }
}
