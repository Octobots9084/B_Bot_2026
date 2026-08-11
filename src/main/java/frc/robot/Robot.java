// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Subsystems.Shooter.Flywheel.FlywheelSubsystem;
import frc.robot.Subsystems.Superstructure.Superstructure;
import frc.robot.Subsystems.Superstructure.SuperstructureStates;
import yams.mechanisms.velocity.FlyWheel;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends LoggedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private RobotContainer container;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    container = new RobotContainer(this);

    switch(Constants.currentMode) { //TODO log
      case REAL:
        Logger.addDataReceiver(new WPILOGWriter());
        Logger.addDataReceiver(new NT4Publisher());
      case SIM:
        Logger.addDataReceiver(new NT4Publisher());
      case REPLAY:
     // Replaying a log, set up replay source
        setUseTiming(false); // Run as fast as possible
        String logPath = LogFileUtil.findReplayLog();
        Logger.setReplaySource(new WPILOGReader(logPath));
        Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        break;
       }
      CommandScheduler.getInstance().enable();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    FlywheelSubsystem.getInstance().flywheelPeriodic();
    CommandScheduler.getInstance().run();
    Logger.recordOutput("IsBlueAlliance",Constants.isBlueAlliance);
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    Superstructure.getInstance().wantedState = SuperstructureStates.ZEROING;
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    if (container.autoChooser.getSelected() != null) CommandScheduler.getInstance().schedule(container.autoChooser.getSelected()); //awkward command
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    Superstructure.getInstance().wantedState = SuperstructureStates.ZEROING;
    if (container.autoChooser.getSelected() != null) CommandScheduler.getInstance().cancel(container.autoChooser.getSelected());
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    Superstructure.getInstance().wantedState = SuperstructureStates.SAFE;
    ButtonConfig.driverController.setRumble(RumbleType.kBothRumble, 0);

  }

  /** This function is called periodically when disabled. Copy pasted. */
  @Override
  public void disabledPeriodic() {
    if (Constants.isBlueAlliance != null) return;
    Optional<Alliance> ally = DriverStation.getAlliance();
    if (!ally.isPresent()) return;

    Constants.isBlueAlliance = (ally.get() == Alliance.Blue);
  }

  
  public static Boolean wonAuto() {
    String gameData = DriverStation.getGameSpecificMessage();
    if (gameData.length() < 0) return null;
    boolean blueVictory = gameData.charAt(0) == 'B';
    return blueVictory == Constants.isBlueAlliance;
  }

  /**Degrees for some reason. */
  public static boolean onRamp(double wanted, double tolerance) {
    tolerance = Units.degreesToRadians(tolerance);
    double tilt = SmartDashboard.getNumber("autotest/TILT INPUT", tolerance);
  
    return tilt > wanted + tolerance || tilt < wanted - tolerance;
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    Superstructure.getInstance().wantedState = SuperstructureStates.ZEROING;
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    FlywheelSubsystem.getInstance().flywheelSimPeriodic();
  }
}
