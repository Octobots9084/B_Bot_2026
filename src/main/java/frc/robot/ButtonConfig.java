package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Superstructure.Superstructure;

public class ButtonConfig {
    public static CommandXboxController driverController = new CommandXboxController(0);
    public static CommandXboxController coDriverController = new CommandXboxController(1);
    Superstructure superstructure = Superstructure.getInstance();

    public void initTeleop() {
        //TODO button maps arent decided yet. Technically we haven't chosen what the joysticks do as of the time of writing this.
            SmartDashboard.putBoolean("AAAAA", false);

        //TODO Remove - these are intake YASS things
        driverController.button(1).whileTrue(new InstantCommand(() -> {
            IntakeSubsystem.getInstance().rotateP(0);
            SmartDashboard.putBoolean("AAAAA", true);
        }));
        driverController.button(2).whileTrue(new InstantCommand(() -> {
            IntakeSubsystem.getInstance().rotateP(0.25);
        }));
        driverController.button(3).whileTrue(new InstantCommand(() -> {
            IntakeSubsystem.getInstance().rotateP(0.77);
        }));

        driverController.button(4).whileTrue(new InstantCommand(() -> {
            IntakeSubsystem.getInstance().rotateR(0);
        }));
        driverController.button(5).whileTrue(new InstantCommand(() -> {
            IntakeSubsystem.getInstance().rotateR(0.25);
        }));
        driverController.button(6).whileTrue(new InstantCommand(() -> {
            IntakeSubsystem.getInstance().rotateR(0.77);
        }));
    }
    
}
