package frc.robot;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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
    //         SmartDashboard.putBoolean("AAAAA", false);

    //     //TODO Remove - these are intake YASS things

    //     InstantCommand comm = new InstantCommand(() -> {
    //         IntakeSubsystem.getInstance().rotateP(0);
    //         SmartDashboard.putBoolean("AAAAA", true);
    //     });

    //     driverController.button(1).whileTrue(comm);
        
    //     Class<CommandScheduler> clazz = CommandScheduler.class;

    //     try {
    //         clazz.getField("m_scheduledCommands").setAccessible(true);
    //         clazz.getField("m_composedCommands").setAccessible(true);
    //         SmartDashboard.putBoolean("nulll", false);
    //         SmartDashboard.putBoolean("CCCCC",!((Set<Command>) (clazz.getField("m_scheduledCommands").get(CommandScheduler.getInstance()))).isEmpty());
    //         SmartDashboard.putBoolean("BBBBB", !((Map<Subsystem, Command>) clazz.getField("m_requirements").get(CommandScheduler.getInstance())).isEmpty());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         SmartDashboard.putString("ERR", e.toString());
            
    //     }
        
    //     String s = "";
    //     for (Field field : clazz.getDeclaredFields()) {
    //         if (field.getName() == "m_scheduledCommands") {
    //             field.setAccessible(true);
    //             SmartDashboard.putBoolean("Reached scheduledCommands!", true);
    //             try {
    //                 SmartDashboard.putBoolean("CCCCCQ",!((Set<Command>) field.get(CommandScheduler.getInstance())).isEmpty());
    //             } catch (IllegalArgumentException | IllegalAccessException e) {
    //                 // TODO Auto-generated catch block
    //                 e.printStackTrace();
    //             }

    //         }

    //             if (field.getName() == "m_composedCommands") {
    //                 field.setAccessible(true);
    //                 SmartDashboard.putBoolean("Reached scheduledCommands!", true);
    //             try {

    //                 AtomicReference<String> cont = new AtomicReference<>();
    //                 for (Entry<Subsystem, Command> entry : ((Map<Subsystem, Command>) field.get(CommandScheduler.getInstance())).entrySet()) {
                        
    //                 if (entry.getValue().equals(comm))                    SmartDashboard.putString("DDDDDQ","ACTIVE");
    //                 }
    //             } catch (IllegalArgumentException | IllegalAccessException e) {
    //                 // TODO Auto-generated catch block
    //                 e.printStackTrace();
    //             }

    //         }


    //         s += field.getName() + ", ";
    //     };
        


    //     SmartDashboard.putString("classes", s);





    //     driverController.button(2).whileTrue(new InstantCommand(() -> {
    //         IntakeSubsystem.getInstance().rotateP(0.25);
    //     }));
    //     driverController.button(3).whileTrue(new InstantCommand(() -> {
    //         IntakeSubsystem.getInstance().rotateP(0.77);
    //     }));

    //     driverController.button(4).whileTrue(new InstantCommand(() -> {
    //         IntakeSubsystem.getInstance().rotateR(0);
    //     }));
    //     driverController.button(5).whileTrue(new InstantCommand(() -> {
    //         IntakeSubsystem.getInstance().rotateR(0.25);
    //     }));
    //     driverController.button(6).whileTrue(new InstantCommand(() -> {
    //         IntakeSubsystem.getInstance().rotateR(0.77);
    //     }));
    // }
    }
}
