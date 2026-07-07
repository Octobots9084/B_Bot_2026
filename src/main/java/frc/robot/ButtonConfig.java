package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Superstructure.Superstructure;

public class ButtonConfig {
    public static CommandXboxController driverController = new CommandXboxController(0);
    public static CommandXboxController coDriverController = new CommandXboxController(1);
    Superstructure superstructure = Superstructure.getInstance();

    public void initTeleop() {
        //TODO button maps arent decided yet. Technically we haven't chosen what the joysticks do as of the time of writing this.
    }
    
}
