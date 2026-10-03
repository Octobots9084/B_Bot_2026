package frc.robot.Subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Seconds;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.LinearVelocityUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Per;
import edu.wpi.first.units.measure.Distance.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Drive.SwerveSubsystem;
import frc.robot.Constants;
import frc.robot.Subsystems.Drive.SwerveSubsystem;
import frc.robot.Subsystems.Shooter.Feeder.FeederStates;
import frc.robot.Subsystems.Shooter.Feeder.FeederSubsystem;
import frc.robot.Subsystems.Shooter.Flywheel.FlywheelStates;
import frc.robot.Subsystems.Shooter.Flywheel.FlywheelSubsystem;
import frc.robot.Subsystems.Shooter.Hood.HoodStates;
import frc.robot.Subsystems.Shooter.Hood.HoodSubsystem;
import frc.robot.Subsystems.Vision.Alignment;
import frc.robot.Subsystems.Vision.ShooterCalculator;

public class ShooterSubsystem extends SubsystemBase {
    public ShooterStates wantedShooterState = ShooterStates.SAFE;
    public ShooterStates currentShooterState = ShooterStates.SAFE;
    public SwerveSubsystem swerve =  SwerveSubsystem.getInstance();
    public static ShooterSubsystem inst = new ShooterSubsystem();
    private Translation2d hubPoseBlue = new Translation2d(4.6228, 4.02082);
    private Translation2d hubPoseRed = new Translation2d(11.88974, 4.02082);
    private Translation2d blueFerryOutpost = new Translation2d(4.6239 - 2,2.011-0.5);
    private Translation2d redFerryOutpost = new Translation2d(11.917 + 2,6.031+0.5);
    private Translation2d blueFerryDepot = new Translation2d(4.6239 - 2,6.03+0.5);
    private Translation2d redFerryDepot = new Translation2d(11.917 + 2,2.011-0.5);
    private Translation2d ourHub;


    public static int flywheelDebouncer = 10;
    //public double hubBallSpeed = 6.7;
    //public double hubFlywheelSpeed = 10;
    //public double ferryBallSpeed = 6.7;
    //public double ferryFlywheelSpeed = 10;
    ShooterCalculator hubShot;
    ShooterCalculator ferryShot;
    @Override
    public void periodic() {
       // if(InTrenchLane() && inDangerOfTrench()){
         //   wantedShooterState = ShooterStates.TRENCH;
        //}
        handleStateTransitions();
        applyStates();
        log();
    }

    public void handleStateTransitions() {

        switch(wantedShooterState){
            case SAFE:
                currentShooterState = ShooterStates.SAFE;
            break;
            case HUB:
                //if(swerve.isInAllianceZone()){ TODO
                currentShooterState = ShooterStates.HUB;
               // }
            break;     
            case TRENCH:
            currentShooterState = ShooterStates.TRENCH;
            break;  
            case ZEROING:
            currentShooterState = ShooterStates.ZEROING;
            break;    
            case FIXEDFIRE:
            break;
            case UNJAM:
            currentShooterState = ShooterStates.UNJAM;
            break;
            case SPINUP:
            currentShooterState = ShooterStates.SPINUP;
            break;
            default:
            break;
            }
        } 

    public void applyStates () {
        switch(currentShooterState){
            case SAFE:
                shooterPartsControl(FlywheelStates.SAFE.enumVelocity, FeederStates.SAFE.enumVelocity, HoodStates.SAFE.enumAngle);
            break;
            case HUB:
                hubShot = ShooterCalculator.getInstance().calculateShot(ourHub.getX() - swerve.getPose2d().getTranslation().getX(), ourHub.getY() - swerve.getPose2d().getTranslation().getY(), SwerveSubsystem.getInstance().getChassisSpeeds().vxMetersPerSecond,SwerveSubsystem.getInstance().getChassisSpeeds().vyMetersPerSecond);
                //if this code doesnt work fiddle with the X and Y distance from hubs <3
                Alignment.getInstance().getRotation(hubShot.getRotation());
                shooterPartsControl(hubShot.getflywheelSpeed(), FeederStates.FIRE.enumVelocity, Degrees.of(hubShot.getHoodAngle()));
            break;     
            case FERRY:
                ferryShot = ShooterCalculator.getInstance().calculateShot(0, 0, 0, 0);
                Alignment.getInstance().getRotation(ferryShot.getRotation());
                shooterPartsControl(ferryShot.getflywheelSpeed(), FeederStates.FIRE.enumVelocity, Degrees.of(ferryShot.getHoodAngle()));
            break;
            case TRENCH:
                shooterPartsControl(FlywheelStates.SAFE.enumVelocity, FeederStates.SAFE.enumVelocity, HoodStates.SAFE.enumAngle);
            break;  
            case ZEROING:
            //TODO zeroing stuff
            break;    
            case FIXEDFIRE:
                shooterPartsControl(FlywheelStates.FIXEDFIRE.enumVelocity, FeederStates.FIRE.enumVelocity, HoodStates.FIXEDFIRE.enumAngle);
            break;
            case UNJAM:
                shooterPartsControl(FlywheelStates.SAFE.enumVelocity, FeederStates.REVERSE.enumVelocity, null);//TODO
            break;
            case SPINUP:
                shooterPartsControl(FlywheelStates.FIXEDFIRE.enumVelocity, FeederStates.SAFE.enumVelocity, null);//TODO not null
            break;
            default:
                shooterPartsControl(FlywheelStates.SAFE.enumVelocity, FeederStates.SAFE.enumVelocity, HoodStates.SAFE.enumAngle);
            break;
            }
    }
    public static void shooterPartsControl(double flywheelSpeed, double feederSpeed, Angle angle){
        FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(flywheelSpeed));
        FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(feederSpeed));
        HoodSubsystem.getInstanceHood().setAngleWithTolerance(angle, HoodSubsystem.hoodTolerance);
    }

    public ShooterSubsystem() {
            if (Constants.isBlueAlliance) {
        ourHub = hubPoseBlue;
    } else
    {
        ourHub = hubPoseRed;
    }
    }

    public static ShooterSubsystem getInstance() {
        return inst;
    }

    public void log() {
        Logger.recordOutput("Wanted State", wantedShooterState);
        Logger.recordOutput("Current State", currentShooterState);
        Logger.recordOutput("Hub Shot Angle", hubShot.getHoodAngle());
        Logger.recordOutput("Hub Shot Rotation", hubShot.getRotation());
        Logger.recordOutput("Hub Shot Flywheel Speed", hubShot.getflywheelSpeed());
        Logger.recordOutput("Ferry Shot Hood Angle", ferryShot.getHoodAngle());
        Logger.recordOutput("Ferry Shot Rotation", ferryShot.getRotation());
        Logger.recordOutput("Ferry Shot Flywheel Speed", ferryShot.getflywheelSpeed());
        //hood angle, rotation, flywheel
        
   }
}
