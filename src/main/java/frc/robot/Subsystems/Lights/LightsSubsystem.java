package frc.robot.Subsystems.Lights;

public class LightsSubsystem {
    public LightAnimations lightsCurrentState = LightAnimations.DEFAULT;
    public LightAnimations lightsWantedState = LightAnimations.DEFAULT;
    private static LightsSubsystem instance;

    public static LightsDevice candle;

    public static LightsSubsystem getInstance() {
        return instance;
    }
    public static void setInstance(LightsSubsystem val) {
        instance = val;
    }

    public void lightStateTransitions() {
        switch (lightsWantedState) {
            case DEFAULT:
                lightsCurrentState = LightAnimations.DEFAULT;
                break;
            case DISABLED:
                lightsCurrentState = LightAnimations.DISABLED;
                break;
            case IDLE:
                lightsCurrentState = LightAnimations.IDLE;
                break;
            case HUB:
                lightsCurrentState = LightAnimations.HUB;
                break;
            case FERRY:
                lightsCurrentState = LightAnimations.FERRY;
                break;
            
          //case XYZ:
          //    lightsCurrentState = LightAnimations.XYZ;
        }
    }

    public void applyStates() {
        candle.device.setControl(lightsCurrentState.color);
    
    }
}