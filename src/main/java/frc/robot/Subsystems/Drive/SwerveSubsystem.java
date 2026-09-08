package frc.robot.Subsystems.Drive;

<<<<<<< HEAD

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.SwerveDriveBrake;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.controllers.PPLTVController;
=======
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.SwerveDriveBrake;
>>>>>>> 936cb99 (resolved merge conflicts)

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
=======
import edu.wpi.first.wpilibj.XboxController;
>>>>>>> 936cb99 (resolved merge conflicts)
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModule;

import frc.robot.Constants;
<<<<<<< HEAD
import frc.robot.Subsystems.Intake.IntakeStates;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.ShooterStates;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
=======
>>>>>>> 936cb99 (resolved merge conflicts)

public class SwerveSubsystem extends SubsystemBase {
    public SwerveStates wantedState = SwerveStates.IDLE;
    public SwerveStates currentState = SwerveStates.IDLE;

    public XboxController driverController;
    public XboxController coDriverController;

    public double maxVelocity;
    public double maxAngularVelocity;

    public static PIDController angularPidcontroller = new PIDController(0, 0, 0);

    public double rotLockAngle = 0;

    public CommandSwerveDrivetrain commandSwerveDrivetrain;
    private SlewRateLimiter rotlimiter;

    public SwerveDriveBrake xLockbrake = new SwerveRequest.SwerveDriveBrake();


<<<<<<< HEAD

=======
>>>>>>> 936cb99 (resolved merge conflicts)
    long timer = 0l;

    boolean debounce = true;

<<<<<<< HEAD
    public static SwerveSubsystem instance;
    
    //santi is a nerd🤓
=======
    
>>>>>>> 936cb99 (resolved merge conflicts)
    
        
    
    public SwerveSubsystem(XboxController driverController, XboxController coDriverController, double maxVelocity, double maxAngularVelocity) {
        this.driverController = driverController;
        this.coDriverController = driverController;
        this.maxVelocity = maxVelocity;
        this.maxAngularVelocity = maxAngularVelocity;
        this.rotlimiter = new SlewRateLimiter(Math.PI*10);
        initCommandSwerveDrivetrain();
<<<<<<< HEAD
        instance = this;
    }

    private void configureAutoBuilder() {
        // SwerveSubsystem.getInstance().registerNamedCommands();
        try {
            var config = RobotConfig.fromGUISettings();
            AutoBuilder.configure(
                    getRobotPose(), // Supplier of current robot pose
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
    
=======
    }

>>>>>>> 936cb99 (resolved merge conflicts)

    public void periodic() {
        handleStateTransitions();
        applyStates();
    }

    public void handleStateTransitions() {
        switch (wantedState) {
<<<<<<< HEAD
            case ALIGNHUB:
=======
            case ALIGNCLIMB:
>>>>>>> 936cb99 (resolved merge conflicts)
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
<<<<<<< HEAD
            case ALIGNHUB:
            


=======
            case ALIGNCLIMB:
>>>>>>> 936cb99 (resolved merge conflicts)
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

<<<<<<< HEAD

    public void registerNamedCommands () {

    NamedCommands.registerCommand("StartShoot", new InstantCommand(() -> {ShooterSubsystem.getInstance().wantedShooterState = ShooterStates.HUB;}));
    NamedCommands.registerCommand("StopShoot", new InstantCommand(() -> {ShooterSubsystem.getInstance().wantedShooterState = ShooterStates.SAFE;}));

    NamedCommands.registerCommand("StartIntake", new InstantCommand(() -> {IntakeSubsystem.getInstance().wantedIntakeState = IntakeStates.INTAKING;}));
    NamedCommands.registerCommand("StopIntake", new InstantCommand(() -> {IntakeSubsystem.getInstance().wantedIntakeState = IntakeStates.EXTENDED;}));
    }

=======
>>>>>>> 936cb99 (resolved merge conflicts)
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

<<<<<<< HEAD
    public  static SwerveSubsystem getInstance(){
        return instance;
    }

=======
>>>>>>> 936cb99 (resolved merge conflicts)
    public void initCommandSwerveDrivetrain() {

        SwerveDrivetrainConstants dtC = new SwerveDrivetrainConstants()
            .withCANBusName(Constants.krakenBus.getName())
            .withPigeon2Id(0)
            .withPigeon2Configs(null);


        commandSwerveDrivetrain = new CommandSwerveDrivetrain(
            dtC, Constants.FrontLeft, Constants.FrontRight, Constants.BackLeft, Constants.BackRight);
        }


}

