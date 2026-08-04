package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radian;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.Second;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.AngularVelocityUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Mode;
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

    public ButtonConfig buttons;

    public final SendableChooser<Command> autoChooser; //dont touch this


    public RobotContainer(Robot robot) {

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
   autoChooser = /* AutoBuilder.buildAutoChooser() */ null; //TODO put back when an auto decides excistence is a good idea
    // NAMED COMMANDS IN SWERVE
  // SmartDashboard.putData("Auto", autoChooser); //TODO same here
    buttons.initTeleop();


    }
}

