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
import frc.robot.Subsystems.Drive.SwerveSubsystem;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.RollerFloor.RollerFloorSubsystem;
import frc.robot.Subsystems.Shooter.ShooterStates;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import frc.robot.Subsystems.Shooter.Feeder.FeederSubsystem;
import frc.robot.Subsystems.Shooter.Flywheel.FlywheelSubsystem;
import frc.robot.Subsystems.Shooter.Hood.HoodSubsystem;
import frc.robot.Subsystems.Superstructure.Superstructure;

public class RobotContainer {
    public IntakeSubsystem intake;
    public RollerFloorSubsystem rollerFloor;
    public ShooterSubsystem shooter;
    public Superstructure superstructure;
    public FlywheelSubsystem flywheel;
    public FeederSubsystem feeder;
    public HoodSubsystem hood;
    public ButtonConfig buttons;

    private SwerveSubsystem swerve;



// Dashboard inputs
  final SendableChooser<Command> autoChooser;


    public RobotContainer(Robot robot) {


      // this.swerve = SwerveSubsystem.setInstance(BetaConstants.createDrivetrain(), ButtonConfig.driverController,
      //     Constants.maxAngularVelocity, Constants.maxVelocity);

        buttons = new ButtonConfig();

        
        switch(Constants.currentMode) {
            case REAL: {
                intake = new IntakeSubsystem();
                rollerFloor = new RollerFloorSubsystem();
                shooter = new ShooterSubsystem();
                superstructure = new Superstructure();
                flywheel = new FlywheelSubsystem();
                feeder = new FeederSubsystem();
                hood = new HoodSubsystem();
            }

            case SIM: {
                intake = new IntakeSubsystem();
                rollerFloor = new RollerFloorSubsystem();
                shooter = new ShooterSubsystem();
                superstructure = new Superstructure();
                flywheel = new FlywheelSubsystem();
                feeder = new FeederSubsystem();
                hood = new HoodSubsystem();
            }
            case REPLAY:
            //laugh
        }
  
    buttons.initTeleop();



    // autoChooser = AutoBuilder.buildAutoChooser();
    // // NAMED COMMANDS IN SWERVE
    // SmartDashboard.putData("Auto", autoChooser);
    // ButtonConfig buttons = new ButtonConfig();
    // buttons.initTeleop();

     autoChooser = AutoBuilder.buildAutoChooser();
    // NAMED COMMANDS IN SWERVE
    SmartDashboard.putData("Auto", autoChooser);

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
    // return new InstantCommand();
  }
}

