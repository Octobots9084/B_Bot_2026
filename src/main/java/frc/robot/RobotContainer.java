package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.RollerFloor.RollerFloorSubsystem;
import frc.robot.Subsystems.Shooter.ShooterStates;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import frc.robot.Subsystems.Superstructure.Superstructure;

public class RobotContainer {
    public IntakeSubsystem intake;
    public RollerFloorSubsystem rollerFloor;
    public ShooterSubsystem shooter;
    public Superstructure superstructure;

    ButtonConfig buttons;

    public final SendableChooser<Command> autoChooser; //dont touch this


    public RobotContainer() {

        buttons = new ButtonConfig();

        
        switch(Constants.currentMode) {
            case REAL: {
                intake = new IntakeSubsystem();
                rollerFloor = new RollerFloorSubsystem();
                shooter = new ShooterSubsystem();
                superstructure = new Superstructure();
            }

            case SIM: {
                intake = new IntakeSubsystem();
                rollerFloor = new RollerFloorSubsystem();
                shooter = new ShooterSubsystem();
                superstructure = new Superstructure();
            }
            case REPLAY:
            //laugh
        }
    autoChooser = AutoBuilder.buildAutoChooser();
    // NAMED COMMANDS IN SWERVE
    SmartDashboard.putData("Auto", autoChooser);
    buttons.initTeleop();



    }
}

