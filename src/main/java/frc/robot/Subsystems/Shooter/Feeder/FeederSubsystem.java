package frc.robot.Subsystems.Shooter.Feeder;

public class FeederSubsystem {
  public static double feederStatorLimit = 40; //TODO replace
  public FeederTalonFX FeederTX = new FeederTalonFX();
  public static FeederSubsystem instance;
  public static FeederSubsystem GetFeederInstance(){
    return instance;
  }
}
