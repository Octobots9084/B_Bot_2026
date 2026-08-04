package frc.robot.Subsystems.Shooter.Feeder;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class FeederSubsystem {
  public static double feederStatorLimit = 40; //TODO replace
  public FeederTalonFX FeederTX = new FeederTalonFX();
  public static FeederSubsystem instance;
  public static FeederSubsystem GetFeederInstance(){
    return instance;
  }
public static Subsystem getInstance() {
    return new Subsystem() {}; // shut up ide. TODO SOMEONE FIX THIS BROKEN CODE AS FAST AS POSSIBLE
}
}
