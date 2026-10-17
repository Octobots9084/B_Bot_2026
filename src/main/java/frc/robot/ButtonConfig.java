package frc.robot;

import static edu.wpi.first.units.Units.RPM;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Drive.SwerveSubsystem;
import frc.robot.Subsystems.Intake.IntakeStates;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.Flywheel.FlywheelStates;
import frc.robot.Subsystems.Shooter.Flywheel.FlywheelSubsystem;
import frc.robot.Subsystems.Superstructure.Superstructure;
import frc.robot.Subsystems.Superstructure.SuperstructureStates;
import frc.robot.Subsystems.Vision.Alignment;
import frc.robot.Subsystems.Vision.ShooterCalculator;

public class ButtonConfig {
    public static CommandXboxController driverController;
    public static CommandXboxController coDriverController;
    Superstructure superstructure = Superstructure.getInstance();
    //private ShooterCalculator alignRotation;
    public ButtonConfig(){

    }
    
    public void initTeleop() {
        driverController = new CommandXboxController(0);
        coDriverController = new CommandXboxController(1);
        //initCalculator()u;
//         driverController.rightTrigger().onTrue(new ConditionalCommand(new InstantCommand(()-> 
//             superstructure.setWantedState(SuperstructureStates.FIXEDTRENCH)), 
// \            .onTrue((new InstantCommand(()-> superstructure.driverRequestedIntakeState = IntakeStates.ELEPHANTIASIS)))
//             .onFalse(new InstantCommand(()->
//             superstructure.setWantedState(SuperstructureStates.SAFE)));

        driverController.leftTrigger().onTrue(new InstantCommand(()-> superstructure.driverRequestedIntakeState = IntakeStates.INTAKING))
        .onFalse(new InstantCommand(()-> superstructure.driverRequestedIntakeState = IntakeStates.EXTENDED));       
        // driverController.leftTrigger().onTrue(new InstantCommand(() -> {
        //     IntakeSubsystem.getInstance().wantedIntakeState = IntakeStates.INTAKING;
        // }));

        driverController.povLeft().onTrue(new InstantCommand( () -> {
            SwerveSubsystem.getInstance().zeroGyro();
        }));


        driverController.leftBumper().onTrue(new InstantCommand(()-> superstructure.driverRequestedIntakeState = IntakeStates.REVERSEINTAKE))
        .onFalse(new InstantCommand(()-> superstructure.driverRequestedIntakeState = IntakeStates.EXTENDED));
        
        //driverController.rightBumper().onTrue(new InstantCommand(() -> Alignment.getInstance().getRotation(alignRotation.getRotation())));

        driverController.a().onTrue(new InstantCommand(() -> superstructure.driverRequestedIntakeState = IntakeStates.SAFE));

        driverController.b().onTrue(new InstantCommand(() -> superstructure.setWantedState(SuperstructureStates.UNJAM)));
        driverController.b().onFalse(new InstantCommand(() -> superstructure.setWantedState(SuperstructureStates.SAFE)));
        
        driverController.x().onTrue(new InstantCommand(() -> superstructure.setWantedState(SuperstructureStates.SPINUP)));
        driverController.x().onFalse(new InstantCommand(() -> superstructure.setWantedState(SuperstructureStates.SAFE)));
        
        driverController.rightTrigger().onTrue(new InstantCommand(() -> superstructure.setWantedState(SuperstructureStates.FIXEDTRENCH)))
        //.onTrue(new InstantCommand(() -> superstructure.driverRequestedIntakeState = IntakeStates.ELEPHANTIASIS))
        .onFalse(new InstantCommand(() -> superstructure.setWantedState(SuperstructureStates.SAFE)));

        driverController.rightBumper().onTrue(new InstantCommand(() -> superstructure.setWantedState(SuperstructureStates.FIXEDFIRE)))
        .onFalse(new InstantCommand(() -> superstructure.setWantedState(SuperstructureStates.SAFE)));

        driverController.povDown().onTrue(new InstantCommand(() -> DriverCommunications.testFlywheelSpeed -= 0.5));
        driverController.povUp().onTrue(new InstantCommand(() -> DriverCommunications.testFlywheelSpeed += 0.5));
        driverController.povLeft().onTrue(new InstantCommand(() -> DriverCommunications.testFlywheelSpeedTower -= 0.5));
        driverController.povRight().onTrue(new InstantCommand(() -> DriverCommunications.testFlywheelSpeedTower += 0.5));
     

    }
}
