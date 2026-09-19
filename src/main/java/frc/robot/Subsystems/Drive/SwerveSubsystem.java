package frc.robot.Subsystems.Drive;


import frc.robot.Subsystems.Drive.TunerConstants.TunerSwerveDrivetrain;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.SwerveDriveBrake;
import com.fasterxml.jackson.databind.node.NullNode;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.controllers.PPLTVController;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.lang.reflect.Array;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveDrivetrain;

import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstantsFactory;

import frc.robot.Constants;
import frc.robot.Subsystems.Intake.IntakeStates;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.ShooterStates;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import frc.robot.Subsystems.Vision.ShooterCalculator;

public class SwerveSubsystem extends TunerSwerveDrivetrain implements Subsystem{

    private static final double kSimLoopPeriod = 0.005; // 5 ms
    public SwerveStates wantedState = SwerveStates.IDLE;
    public SwerveStates currentState = SwerveStates.IDLE;

    public CommandXboxController driverController;
    public CommandXboxController coDriverController;

    public double maxVelocity;
    public double maxAngularVelocity;

    public static PIDController angularPidcontroller = new PIDController(0, 0, 0);

    public double rotLockAngle = 0;

    public CommandSwerveDrivetrain commandSwerveDrivetrain;
    private SlewRateLimiter rotlimiter;

    public SwerveDriveBrake xLockbrake = new SwerveRequest.SwerveDriveBrake();

    public ShooterCalculator calculator;

    long timer = 0l;
    boolean debounce = true;

    public static SwerveSubsystem instance;

    PIDController rotationPidController = new PIDController(0, 0, 0);
    private final SwerveRequest.ApplyRobotSpeeds m_pathApplyRobotSpeeds = new SwerveRequest.ApplyRobotSpeeds();


    //TODO get constants
    private synchronized static SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> swerveModuleConstants() {
        SwerveModuleConstantsFactory<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> factory = new SwerveModuleConstantsFactory<>();
        SwerveModuleConstants<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> constants = 
        factory.createModuleConstants( 
            0,
            0,
            0,
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

    //    if (1==1) throw new RuntimeException(constants.CouplingGearRatio + "");

        return constants;
    }
    
    public SwerveSubsystem(CommandXboxController driverController, CommandXboxController coDriverController, double maxVelocity, double maxAngularVelocity) {
        super(TunerConstants.DrivetrainConstants, 0,swerveModuleConstants(),swerveModuleConstants());

      
        
    



        this.driverController = driverController;
        this.coDriverController = driverController;
        this.maxVelocity = maxVelocity;
        this.maxAngularVelocity = maxAngularVelocity;
        this.rotlimiter = new SlewRateLimiter(Math.PI*10);
        initCommandSwerveDrivetrain();
        instance = this;
        configureAutoBuilder();
    }

    private void configureAutoBuilder() {
        // SwerveSubsystem.getInstance().registerNamedCommands();
        try {
            var config = RobotConfig.fromGUISettings();
            AutoBuilder.configure(
                    () -> getState().Pose, // Supplier of current robot pose
                    this::resetPose, // Consumer for seeding pose against auto
                    () -> getState().Speeds, // Supplier of current robot speeds
                    // Consumer of ChassisSpeeds and feedforwards to drive the robot
                    (speeds, feedforwards) -> setControl(
                            m_pathApplyRobotSpeeds.withSpeeds(speeds)
                                    .withWheelForceFeedforwardsX(feedforwards.robotRelativeForcesXNewtons())
                                    .withWheelForceFeedforwardsY(feedforwards.robotRelativeForcesYNewtons())),
                    new PPHolonomicDriveController(
                            // PID constants for translation
                            new PIDConstants(10, 0, 0),
                            // PID constants for rotation
                            new PIDConstants(7, 0, 0)),
                    config,
                    // Assume the path needs to be flipped for Red vs Blue, this is normally the
                    // case
                    () -> DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red,
                    this // Subsystem for requirements
            );
        } catch (Exception ex) {
            DriverStation.reportError("Failed to load PathPlanner config and configure AutoBuilder",
                    ex.getStackTrace());
        }
    }
    

    public void periodic() {
        handleStateTransitions();
        applyStates();
    }

    public void handleStateTransitions() {
        switch (wantedState) {
            case ALIGNHUB:
                break;
            case IDLE:
                break;
            case MANUAL:
                break;
            case REVERSE:
                break;
            case ROTATION_LOCK:
                break;
            case SLOW:
                break;
            case XLOCK:
                break;
            default:
                break;
           
           
        }
    }

    public void applyStates() {
        switch (currentState) {
            case ALIGNHUB:
                Translation2d pos = new Translation2d();
                Pose2d robotPose = SwerveSubsystem.getInstance().getRobotPose();
                double globalAngle = Math.toDegrees(Math.atan2(pos.getY() - robotPose.getY(), pos.getX() - robotPose.getX()));
                double change = globalAngle - robotPose.getRotation().getDegrees();
            
                calculator = ShooterCalculator.getInstance().calculateShot(rotLockAngle, maxAngularVelocity, maxVelocity, maxVelocity); // TODO get done later
                double calc = calculator.calculateShot(rotLockAngle, maxAngularVelocity, maxVelocity, maxVelocity).getRotation();

            //double absoluteRotation = calculator.getRotation();

            //idk if this math is right i dont remember waht these angles are

                commandSwerveDrivetrain.setSwerveState(
                    new SwerveRequest.ApplyFieldSpeeds().withSpeeds(new ChassisSpeeds(0, 0, rotationPidController.calculate(getRobotPose().getRotation().getRadians(), calc - change) * 1 /*todo tune */)));
                
                break;
            case IDLE:
                break;
            case MANUAL:

                shouldXLock();
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
                shouldXLock();
                ChassisSpeeds speeds = calculateSpeedsBasedOnJoystickInputs().div(1.5);

              
                // speeds.vxMetersPerSecond = x;
                // speeds.vyMetersPerSecond = y;

                speeds.omegaRadiansPerSecond = rotlimiter.calculate(speeds.omegaRadiansPerSecond);

                //end segment

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


    public void registerNamedCommands () {

    NamedCommands.registerCommand("StartShoot", new InstantCommand(() -> {ShooterSubsystem.getInstance().wantedShooterState = ShooterStates.HUB;}));
    NamedCommands.registerCommand("StopShoot", new InstantCommand(() -> {ShooterSubsystem.getInstance().wantedShooterState = ShooterStates.SAFE;}));

    NamedCommands.registerCommand("StartIntake", new InstantCommand(() -> {IntakeSubsystem.getInstance().wantedIntakeState = IntakeStates.INTAKING;}));
    NamedCommands.registerCommand("StopIntake", new InstantCommand(() -> {IntakeSubsystem.getInstance().wantedIntakeState = IntakeStates.EXTENDED;}));
    }

    public void shouldXLock() {
        if (MathUtil.applyDeadband(driverController.getLeftX(), Constants.leftXDeadband) != 0 || 
            MathUtil.applyDeadband(driverController.getLeftY(), Constants.leftYDeadband) != 0 ||
            MathUtil.applyDeadband(driverController.getRightX(), Constants.rightXDeadband) != 0 ||
            MathUtil.applyDeadband(driverController.getRightY(), Constants.rightYDeadband) != 0) {

            if (debounce) {
            timer = System.currentTimeMillis();

            debounce = false;
            }

            if (System.currentTimeMillis() - timer > 500) 
                wantedState = SwerveStates.XLOCK;
            
        } else {
            debounce = true;
        };
        

    }

        public ChassisSpeeds calculateSpeedsBasedOnJoystickInputs() {
        // double yMagnitude = MathUtil.applyDeadband(driverLeft.getRawAxis(0),
        // Constants.leftYDeadband);
        // double xMagnitude = -MathUtil.applyDeadband(driverLeft.getRawAxis(1),
        // Constants.leftXDeadband);
        // double angularMagnitude = -MathUtil.applyDeadband(driverRight.getRawAxis(0),
        // Constants.rightXDeadband);
        double yMagnitude = MathUtil.applyDeadband(driverController.getLeftX(), Constants.leftYDeadband);
        double xMagnitude = MathUtil.applyDeadband(driverController.getLeftY(), Constants.leftXDeadband);
        double angularMagnitude = -MathUtil.applyDeadband(driverController.getRightX(), Constants.rightXDeadband);
        angularMagnitude = Math.copySign(angularMagnitude * angularMagnitude, angularMagnitude);
        double xVelocity = xMagnitude * maxVelocity;
        double yVelocity = yMagnitude * maxVelocity;

        double angularVelocity = angularMagnitude * maxAngularVelocity;

        if (Constants.isBlueAlliance) {   
            return new ChassisSpeeds(-xVelocity, -yVelocity, angularVelocity);
        }
        return new ChassisSpeeds(xVelocity, yVelocity, angularVelocity);
    }

    public ChassisSpeeds calculateRotLockSpeedsBasedOnJoystickInputs() {
        // double yMagnitude = MathUtil.applyDeadband(driverLeft.getRawAxis(0),
        // Constants.leftYDeadband);
        // double xMagnitude = -MathUtil.applyDeadband(driverLeft.getRawAxis(1),
        // Constants.leftXDeadband);
        // double angularMagnitude = -MathUtil.applyDeadband(driverRight.getRawAxis(0),
        // Constants.rightXDeadband);
        double yMagnitude = MathUtil.applyDeadband(driverController.getLeftX(), Constants.leftYDeadband);
        double xMagnitude = MathUtil.applyDeadband(driverController.getLeftY(), Constants.leftXDeadband);
        if (getRobotPose().getRotation().getRadians()-rotLockAngle<0.3){
            if (-MathUtil.applyDeadband(driverController.getRightX(), Constants.rightXDeadband)>0.9){
                rotLockAngle = (rotLockAngle + Math.PI/2) % (Math.PI*2);
            }
            else if (-MathUtil.applyDeadband(driverController.getRightX(), Constants.rightXDeadband)<-0.9){
                rotLockAngle = (rotLockAngle - Math.PI/2) % (Math.PI*2);
                
            }
        }
        double angularMagnitude = -MathUtil.applyDeadband(driverController.getRightX(), Constants.rightXDeadband);
        double xVelocity = xMagnitude * maxVelocity;
        double yVelocity = yMagnitude * maxVelocity;

        double RotVelocity = angularPidcontroller.calculate(getRobotPose().getRotation().getRadians(),rotLockAngle);

        if (Constants.isBlueAlliance) {   
            return new ChassisSpeeds(-xVelocity, -yVelocity, RotVelocity);
        }
        return new ChassisSpeeds(xVelocity, yVelocity, RotVelocity);
    }
    public Pose2d getRobotPose() {
        return commandSwerveDrivetrain.getPose2d();
    }

    public  static SwerveSubsystem getInstance(){
        return instance;
    }

    public void initCommandSwerveDrivetrain() {

        SwerveDrivetrainConstants dtC = new SwerveDrivetrainConstants()
            .withCANBusName(Constants.krakenBus.getName())
            .withPigeon2Id(0)
            .withPigeon2Configs(null);
            
            


        commandSwerveDrivetrain = new CommandSwerveDrivetrain(
            dtC, Constants.FrontLeft, Constants.FrontRight, Constants.BackLeft, Constants.BackRight);
        }


}

