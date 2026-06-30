package frc.robot.Subsystems.Superstructure;

public class Superstructure {
    public SuperstructureStates currentState = SuperstructureStates.SAFE;
    public SuperstructureStates wantedState = SuperstructureStates.SAFE;


    public static Superstructure currentInstance;
  

    //@Override
     public void periodic() {
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
    private void applyStates() {
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
