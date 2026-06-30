package frc.robot.Subsystems.Superstructure;

public class Superstructure {
    public SuperstructureStates currentState = SuperstructureStates.SAFE;
    public SuperstructureStates wantedState = SuperstructureStates.SAFE;
    public SuperstructureStates prevState = SuperstructureStates.SAFE;
    //public IntakeStates userRequestedIntakeState = IntakeStates.SAFE;


    public static Superstructure currentInstance;
   //private SwerveSubsystem swerve = SwerveSubsystem.getInstance();
    // public Climb climb = Climb.getInstance();
    // public Shooter shooter = Shooter.getInstance();
    // public Intake intake = Intake.getInstance();

    //@Override
    // public void periodic() {
    //     // Logger.recordOutput("currentState", this.currentState);
    //     // Logger.recordOutput("wantedState", this.wantedState);
    //     handleStateTransitions();
    //     applyStates();
    // }

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
        return false;
    }
    private boolean stateAUTO(){
        return false;
    }
}
