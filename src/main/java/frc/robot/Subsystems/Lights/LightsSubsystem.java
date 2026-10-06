package frc.robot.Subsystems.Lights;

public class LightsSubsystem {
    public LightAnimations lightsCurrentState = LightAnimations.SAFE;
    public LightAnimations lightsWantedState = LightAnimations.SAFE;
    private static LightsSubsystem instance;

    public static LightsDevice candle;
    public LightsSubsystem(){
        instance = this;
    }
    public static LightsSubsystem getInstance() {
        return instance;
    }
    public static void setInstance(LightsSubsystem val) {
        instance = val;
    }

    // BUMP,
    // HUB,
    // FERRY,
    // TRENCH,
    // ZEROING,
    // UNJAM,
    // SPINUP,
    // SAFE,
    // FIXEDFIRE,
    //AUTOHUB,
     //AUTOFERRY,


    public void lightStateTransitions() {
        switch (lightsWantedState) {
            case DEFAULT:
                lightsCurrentState = LightAnimations.DEFAULT;
                break;
            case HUB:
                lightsCurrentState = LightAnimations.HUB;
                break;
            case FERRY:
                lightsCurrentState = LightAnimations.FERRY;
                break;
            case TRENCH:
                lightsCurrentState = LightAnimations.TRENCH;
                break;
            case SAFE:
                lightsCurrentState = LightAnimations.SAFE;
                break;
            case BUMP:
                lightsCurrentState = LightAnimations.BUMP;
            break;
            
            
          //case XYZ:
          //    lightsCurrentState = LightAnimations.XYZ;
        }
    }

    public void applyStates() {
        candle.device.setControl(lightsCurrentState.color);
    
    }
}