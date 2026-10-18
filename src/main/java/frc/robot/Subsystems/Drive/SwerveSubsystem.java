package frc.robot.Subsystems.Drive;

import frc.robot.Subsystems.Drive.TunerConstants.TunerSwerveDrivetrain;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.SwerveDriveBrake;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstantsFactory;

import frc.robot.Constants;
import frc.robot.Subsystems.Intake.IntakeStates;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Superstructure.Superstructure;
import frc.robot.Subsystems.Superstructure.SuperstructureStates;
import frc.robot.Subsystems.Vision.ShooterCalculator;

public class SwerveSubsystem extends TunerSwerveDrivetrain implements Subsystem {

    private static final double kSimLoopPeriod = 0.005; // 5 ms
    @AutoLogOutput public SwerveStates wantedState = SwerveStates.IDLE;
    @AutoLogOutput public SwerveStates currentState = SwerveStates.IDLE;
    @AutoLogOutput Pose2d robotPose;
    public CommandXboxController driverController;
    public CommandXboxController coDriverController;

    public double maxVelocity;
    public double maxAngularVelocity;

    public static PIDController angularPidcontroller = new PIDController(4.0, 0, 0.1);

    public double rotLockAngle = 0;

    public CommandSwerveDrivetrain commandSwerveDrivetrain;
    private SlewRateLimiter rotlimiter;

    public SwerveDriveBrake xLockbrake = new SwerveRequest.SwerveDriveBrake();

    public ShooterCalculator calculator;

    long timer = 0l;
    boolean debounce = true;

    public static SwerveSubsystem instance;

    PIDController rotationPidController = new PIDController(4.0, 0, 0.1);
    private final SwerveRequest.ApplyRobotSpeeds m_pathApplyRobotSpeeds = new SwerveRequest.ApplyRobotSpeeds();

    private synchronized static SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> swerveModuleConstants() {
        SwerveModuleConstantsFactory<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> factory = new SwerveModuleConstantsFactory<>();
        SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> constants = 
        factory.createModuleConstants( 
            13,
            14,
            15,
            0,
            0,
            0,
            false,
            false,
            false
        );
        constants.DriveMotorGearRatio = 1;
        constants.SteerMotorGearRatio = 1;
        constants.CouplingGearRatio = 1;

        return constants;
    }
    
    public SwerveSubsystem(CommandSwerveDrivetrain commandSwerveDrivetrain, CommandXboxController driverController2, CommandXboxController coDriverController2, double maxVelocity, double maxAngularVelocity) {
        super(TunerConstants.DrivetrainConstants, TunerConstants.FrontLeft, TunerConstants.FrontRight, TunerConstants.BackLeft, TunerConstants.BackRight);
        this.driverController = driverController2;
        this.commandSwerveDrivetrain = commandSwerveDrivetrain;
        this.maxVelocity = maxVelocity;
        this.maxAngularVelocity = maxAngularVelocity;
        this.rotlimiter = new SlewRateLimiter(Math.PI*10);
        instance = this;

        // NamedCommands MUST be registered before AutoBuilder is configured
        registerNamedCommands();
        configureAutoBuilder();
    }

    public static SwerveSubsystem setInstance(CommandSwerveDrivetrain commandSwerveDrivetrain, CommandXboxController driverController, CommandXboxController coDriverController2,
            double maxAngularVelocity, double maxVelocity) {
        SwerveSubsystem instance = new SwerveSubsystem(commandSwerveDrivetrain, driverController, coDriverController2, maxAngularVelocity, maxVelocity);
        return instance;
    }

    public Pose2d getPose2d() {
        return commandSwerveDrivetrain.getPose2d();   
    }

    public void zeroGyro() {
        if (Constants.isBlueAlliance) {
            this.resetRotation(new Rotation2d(0));
        } else {
            this.resetRotation(new Rotation2d(Math.PI));
        }
    }

    private void configureAutoBuilder() {
        try {
            var config = RobotConfig.fromGUISettings();
            AutoBuilder.configure(
                    this::getPose2d, // Supplier of current robot pose (uses subsystem pose estimation)
                    commandSwerveDrivetrain::resetPose,                      // was this::resetPose
                    () -> commandSwerveDrivetrain.getState().Speeds,         // was getState().Speeds
                    (speeds, feedforwards) -> commandSwerveDrivetrain.setControl(m_pathApplyRobotSpeeds.withSpeeds(speeds)
                    .withWheelForceFeedforwardsX(feedforwards.robotRelativeForcesXNewtons())
                    .withWheelForceFeedforwardsY(feedforwards.robotRelativeForcesYNewtons())),
                    new PPHolonomicDriveController(
                            // PID constants for translation
                            new PIDConstants(10, 0, 0),
                            // PID constants for rotation
                            new PIDConstants(7, 0, 0)),
                    config,
                    // Path flipping condition for Red vs Blue
                    () -> DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red,
                    this // Subsystem requirements
            );
        } catch (Exception ex) {
            DriverStation.reportError("Failed to load PathPlanner config and configure AutoBuilder", ex.getStackTrace());
        }
    }

    public void periodic() {
        handleStateTransitions();
        applyStates();
        log();
    }

    public void handleStateTransitions() {
        // Force state to IDLE during autonomous so teleop joystick transitions don't interfere
        if (DriverStation.isAutonomous()) {
            wantedState = SwerveStates.IDLE;
            currentState = SwerveStates.IDLE;
            return;
        }

        switch (wantedState) {
            case ALIGNHUB:
                break;
            case IDLE:
                currentState = wantedState;
                break;
            case MANUAL:
                currentState = wantedState;
                break;
            case REVERSE:
                currentState = wantedState;
                break;
            case ROTATION_LOCK:
                currentState = wantedState;
                break;
            case SLOW:
                if (currentState != SwerveStates.IDLE)
                    currentState = wantedState;
                break;
            case XLOCK:
                currentState = wantedState;
                break;
            default:
                break;
        }
    }

    public void applyStates() {
        switch (currentState) {
            case ALIGNHUB:
                Translation2d pos = new Translation2d();
                robotPose = SwerveSubsystem.getInstance().getRobotPose();
                double globalAngle = Math.toDegrees(Math.atan2(pos.getY() - robotPose.getY(), pos.getX() - robotPose.getX()));
                double change = globalAngle - robotPose.getRotation().getDegrees();
            
                calculator = ShooterCalculator.getInstance().calculateShot(rotLockAngle, maxAngularVelocity, maxVelocity, maxVelocity);
                double calc = calculator.calculateShot(rotLockAngle, maxAngularVelocity, maxVelocity, maxVelocity).getRotation();

                commandSwerveDrivetrain.setSwerveState(
                    new SwerveRequest.ApplyFieldSpeeds().withSpeeds(new ChassisSpeeds(0, 0, rotationPidController.calculate(getRobotPose().getRotation().getRadians(), calc - change) * 1)));
                break;

            case IDLE:
                // During Auto, PathPlanner drives setControl() directly.
                // Leave empty so no swerve state overwrites PathPlanner outputs.
                break;

            case MANUAL:
                commandSwerveDrivetrain.setSwerveState(new SwerveRequest.ApplyFieldSpeeds()
                        .withSpeeds(calculateSpeedsBasedOnJoystickInputs())
                        .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage));
                break;

            case REVERSE:
                commandSwerveDrivetrain.setSwerveState(new SwerveRequest.ApplyFieldSpeeds().withSpeeds(new ChassisSpeeds(-0.3, 0, 0))
                    .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage));
                break;

            case ROTATION_LOCK:
                commandSwerveDrivetrain.setSwerveState(new SwerveRequest.ApplyFieldSpeeds()
                        .withSpeeds(calculateRotLockSpeedsBasedOnJoystickInputs())
                        .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage));
                break;

            case SLOW:
                ChassisSpeeds speeds = calculateSpeedsBasedOnJoystickInputs().div(1.5);
                speeds.omegaRadiansPerSecond = rotlimiter.calculate(speeds.omegaRadiansPerSecond);

                commandSwerveDrivetrain.setSwerveState(new SwerveRequest.ApplyFieldSpeeds()
                        .withSpeeds(speeds)
                        .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage));
                break;

            case XLOCK:
                commandSwerveDrivetrain.setSwerveState(xLockbrake);
                break;

            default:
                break;
        }
    }

    public void registerNamedCommands() {
        NamedCommands.registerCommand("StartShoot", new InstantCommand(() -> Superstructure.getInstance().setWantedState(SuperstructureStates.HUB)));
        NamedCommands.registerCommand("StopShoot", new InstantCommand(() -> Superstructure.getInstance().setWantedState(SuperstructureStates.SAFE)));
        NamedCommands.registerCommand("TrenchShoot", new InstantCommand(() -> Superstructure.getInstance().setWantedState(SuperstructureStates.FIXEDTRENCH)));
        NamedCommands.registerCommand("Tower Shoot", new InstantCommand(() -> Superstructure.getInstance().setWantedState(SuperstructureStates.FIXEDFIRE)));

        NamedCommands.registerCommand("StartIntake", new InstantCommand(() -> {IntakeSubsystem.getInstance().wantedIntakeState = IntakeStates.INTAKING;}));
        NamedCommands.registerCommand("ElephantiasisMKII", new InstantCommand(() -> {IntakeSubsystem.getInstance().wantedIntakeState = IntakeStates.ELEPHANTIASIS;}));
        NamedCommands.registerCommand("StopIntake", new InstantCommand(() -> {IntakeSubsystem.getInstance().wantedIntakeState = IntakeStates.SAFE;}));
        NamedCommands.registerCommand("DriveOverBumpFromAlliance", new InstantCommand());
        NamedCommands.registerCommand("DriveOverBumpToAlliance", new InstantCommand());
        NamedCommands.registerCommand("DriveOverBump", new InstantCommand());
    }

    public ChassisSpeeds calculateRotLockSpeedsBasedOnJoystickInputs() {
        double yMagnitude = MathUtil.applyDeadband(driverController.getLeftX(), Constants.leftYDeadband);
        double xMagnitude = MathUtil.applyDeadband(driverController.getLeftY(), Constants.leftXDeadband);
        if (getRobotPose().getRotation().getRadians() - rotLockAngle < 0.3) {
            if (-MathUtil.applyDeadband(driverController.getRightX(), Constants.rightXDeadband) > 0.9) {
                rotLockAngle = (rotLockAngle + Math.PI / 2) % (Math.PI * 2);
            } else if (-MathUtil.applyDeadband(driverController.getRightX(), Constants.rightXDeadband) < -0.9) {
                rotLockAngle = (rotLockAngle - Math.PI / 2) % (Math.PI * 2);
            }
        }
        double xVelocity = xMagnitude * maxVelocity;
        double yVelocity = yMagnitude * maxVelocity;

        double RotVelocity = angularPidcontroller.calculate(getRobotPose().getRotation().getRadians(), rotLockAngle);

        if (Constants.isBlueAlliance) {   
            return new ChassisSpeeds(-xVelocity, -yVelocity, RotVelocity);
        }
        return new ChassisSpeeds(xVelocity, yVelocity, RotVelocity);
    }

    public void shouldXLock() {
        if (MathUtil.applyDeadband(driverController.getLeftX(), Constants.leftXDeadband) != 0 || 
            MathUtil.applyDeadband(driverController.getLeftY(), Constants.leftXDeadband) != 0 ||
            MathUtil.applyDeadband(driverController.getRightX(), Constants.rightXDeadband) != 0 ||
            MathUtil.applyDeadband(driverController.getRightY(), Constants.rightYDeadband) != 0) {

            if (debounce) {
                timer = System.currentTimeMillis();
                debounce = false;
            }

            if (System.currentTimeMillis() - timer > 500) 
                wantedState = SwerveStates.MANUAL;
            
        } else {
            debounce = true;
        }
    }

    public ChassisSpeeds calculateSpeedsBasedOnJoystickInputs() {
        double yMagnitude = MathUtil.applyDeadband(driverController.getLeftX(), Constants.leftYDeadband);
        double xMagnitude = MathUtil.applyDeadband(driverController.getLeftY(), Constants.leftXDeadband);
        double angularMagnitude = MathUtil.applyDeadband(driverController.getRightX(), Constants.rightXDeadband);
        angularMagnitude = Math.copySign(angularMagnitude * angularMagnitude, angularMagnitude);
        double xVelocity = xMagnitude * maxVelocity;
        double yVelocity = yMagnitude * maxVelocity;

        double angularVelocity = angularMagnitude * maxAngularVelocity;

        if (Constants.isBlueAlliance) {   
            return new ChassisSpeeds(-xVelocity, -yVelocity, angularVelocity);
        }
        return new ChassisSpeeds(xVelocity, yVelocity, angularVelocity);
    }

    public Pose2d getRobotPose() {
        return commandSwerveDrivetrain.getPose2d();
    }

    public static SwerveSubsystem getInstance() {
        return instance;
    }

    public void log() {
        Logger.recordOutput("RobotPose", commandSwerveDrivetrain.getPose2d());
        Logger.recordOutput("Pose X", commandSwerveDrivetrain.getPose2d().getX());
        Logger.recordOutput("Pose Y", commandSwerveDrivetrain.getPose2d().getY());
        Logger.recordOutput("Pose Rotation (Degrees)", commandSwerveDrivetrain.getPose2d().getRotation().getDegrees());
        Logger.recordOutput("Pose Rotation (Radians)", commandSwerveDrivetrain.getPose2d().getRotation().getRadians());
        ChassisSpeeds mango = calculateSpeedsBasedOnJoystickInputs();
        Logger.recordOutput("X Velocity", mango.vxMetersPerSecond);
        Logger.recordOutput("Y Velocity", mango.vyMetersPerSecond);
        Logger.recordOutput("Rotational Velocity (Degrees)", Math.toDegrees(mango.omegaRadiansPerSecond));
        Logger.recordOutput("Rotational Velocity (Radians)", mango.omegaRadiansPerSecond);
        Logger.recordOutput("currentSwerveState", currentState);
        Logger.recordOutput("wantedSwerveState", wantedState);
        Logger.recordOutput("Front Left Drive Motor Voltage", commandSwerveDrivetrain.getModule(0).getDriveMotor().getMotorVoltage().getValueAsDouble());
        Logger.recordOutput("Front Right Drive Motor Voltage", commandSwerveDrivetrain.getModule(1).getDriveMotor().getMotorVoltage().getValueAsDouble());
        Logger.recordOutput("Back Left Drive Motor Voltage", commandSwerveDrivetrain.getModule(2).getDriveMotor().getMotorVoltage().getValueAsDouble());
        Logger.recordOutput("Back Right Drive Motor Voltage", commandSwerveDrivetrain.getModule(3).getDriveMotor().getMotorVoltage().getValueAsDouble());
    }

    public ChassisSpeeds getChassisSpeeds() {
        return this.getState().Speeds;
    } 

    public static boolean InTrenchLane() {
        Pose2d robPose = SwerveSubsystem.getInstance().getRobotPose();
        if (robPose.getY() > Constants.outpostTrenchY || robPose.getY() < Constants.depotTrenchY) {
            return true;
        }
        return false;
    }

    public static LinearVelocity trenchVelocity() {
        Pose2d robPose = SwerveSubsystem.getInstance().getRobotPose();
        ChassisSpeeds speeds = SwerveSubsystem.getInstance().getChassisSpeeds();
        if (robPose.getX() <= Constants.blueTrenchX || robPose.getX() >= Constants.fieldMidline && robPose.getX() <= Constants.redTrenchX) {
            return MetersPerSecond.of(speeds.vxMetersPerSecond);
        } else if ((robPose.getX() >= Constants.blueTrenchX && robPose.getX() <= Constants.fieldMidline) || robPose.getX() > Constants.redTrenchX) {
            return (MetersPerSecond.of(speeds.vxMetersPerSecond)).times(-1);
        } else {
            return MetersPerSecond.of(0);
        }
    }

    public static boolean inDangerOfTrench() {
        LinearVelocity robTrenchVelocity = trenchVelocity();

        Distance baseDistanceRelationship = robTrenchVelocity.times(Seconds.of(Constants.shooterLoweringTime));
        Distance minimumTrenchDist = baseDistanceRelationship.plus(Meters.of(Constants.trenchDangerZone));
        return minimumTrenchDist.gte(distanceFromTrench());
    }

    public static Distance distanceFromTrench() {
        Pose2d robPose = SwerveSubsystem.getInstance().getRobotPose();

        if (robPose.getY() > Constants.fieldMidline) {
            return Meters.of(Math.abs(robPose.getX() - Constants.redTrenchX));
        } 
        return Meters.of(Math.abs(robPose.getX() - Constants.blueTrenchX));
    }
}