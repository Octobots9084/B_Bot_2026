package frc.robot.Subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Seconds;

import org.littletonrobotics.junction.AutoLogOutput;
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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonConfig;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.Constants.Mode;
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
    @AutoLogOutput public ShooterStates wantedShooterState = ShooterStates.SAFE;
    @AutoLogOutput public ShooterStates currentShooterState = ShooterStates.SAFE;
    public SwerveSubsystem swerve;
    public static ShooterSubsystem inst;
    private Translation2d hubPoseBlue = new Translation2d(4.6228, 4.02082);
    private Translation2d hubPoseRed = new Translation2d(11.88974, 4.02082);
    private Translation2d blueFerryOutpost = new Translation2d(4.6239 - 2,2.011-0.5);
    private Translation2d redFerryOutpost = new Translation2d(11.917 + 2,6.031+0.5);
    private Translation2d blueFerryDepot = new Translation2d(4.6239 - 2,6.03+0.5);
    private Translation2d redFerryDepot = new Translation2d(11.917 + 2,2.011-0.5);
    private Translation2d ourHub;
    private FeederSubsystem feeder;
    private HoodSubsystem hood;
    private FlywheelSubsystem shooterFlywheel;
    private boolean initDone = false;
    public boolean alreadyZeroed = false;
    
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
        if(!initDone){
            return;
        }
        handleStateTransitions();
        applyStates();
        log();
        
     
    }
     public ShooterSubsystem() {
        Constants.isBlueAlliance = true; //TODO remove this and then errors
        if (Constants.isBlueAlliance) {
            ourHub = hubPoseBlue;
        } else{
            ourHub = hubPoseRed;
        }
        shooterFlywheel = new FlywheelSubsystem();
        feeder = new FeederSubsystem();
        hood = new HoodSubsystem();
        swerve = new SwerveSubsystem(ButtonConfig.driverController, ButtonConfig.coDriverController, Constants.maxVelocity,
        Constants.maxAngularVelocity);
        initDone = true;
    }
    
 
    public void shooterPartsControl(double flywheelSpeed, double feederSpeed, double angle){
        shooterFlywheel.setFlywheelVelocitySetpoint(flywheelSpeed);
        feeder.setFeederVelocitySetpoint(RPM.of(feederSpeed));
        hood.setAngleWithTolerance(Degrees.of(angle));
    }
    public void shooterPartsControl(double flywheelSpeed, double feederSpeed){
        shooterFlywheel.setFlywheelVelocitySetpoint(flywheelSpeed);
        feeder.setFeederVelocitySetpoint(RPM.of(feederSpeed));
    }
   
    public static ShooterSubsystem getInstance() {
        if(inst == null){
            inst = new ShooterSubsystem();
        }
        return inst;
    } 
    public FlywheelSubsystem getFlywheel(){
        return shooterFlywheel;
    }
    public HoodSubsystem getHood(){
        return hood;
    }
    public FeederSubsystem getFeeder(){
        return feeder;
    }
    public SwerveSubsystem getSwerve(){
        return swerve;
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
            currentShooterState = ShooterStates.FIXEDFIRE;
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
                shooterPartsControl(hubShot.getflywheelSpeed(), FeederStates.FIRE.enumVelocity, hubShot.getHoodAngle());
            break;     
            case FERRY:
                ferryShot = ShooterCalculator.getInstance().calculateShot(0, 0, 0, 0);
                Alignment.getInstance().getRotation(ferryShot.getRotation());
                shooterPartsControl(ferryShot.getflywheelSpeed(), FeederStates.FIRE.enumVelocity, ferryShot.getHoodAngle());
            break;
            case TRENCH:
                shooterPartsControl(FlywheelStates.SAFE.enumVelocity, FeederStates.SAFE.enumVelocity, HoodStates.SAFE.enumAngle);
            break;  
            case ZEROING:
                if(hood.zero()){
                    alreadyZeroed = true;
                }
                
            break;    
            case FIXEDFIRE:
                
                shooterPartsControl(FlywheelStates.FIXEDFIRE.enumVelocity, FeederStates.FIRE.enumVelocity, HoodStates.FIXEDFIRE.enumAngle);
            break;
            case UNJAM:
                shooterPartsControl(FlywheelStates.SAFE.enumVelocity, FeederStates.REVERSE.enumVelocity);
            break;
            case SPINUP:
                shooterPartsControl(FlywheelStates.FIXEDFIRE.enumVelocity, FeederStates.SAFE.enumVelocity);
            break;
            default:
                shooterPartsControl(FlywheelStates.SAFE.enumVelocity, FeederStates.SAFE.enumVelocity, HoodStates.SAFE.enumAngle);
            break;
            }
    }
    public void log() {
        //Logger.recordOutput("Hub Shot Angle", hubShot.getHoodAngle());
        //Logger.recordOutput("Hub Shot Rotation", hubShot.getRotation());
        //Logger.recordOutput("Hub Shot Flywheel Speed", hubShot.getflywheelSpeed());
        //Logger.recordOutput("Ferry Shot Hood Angle", ferryShot.getHoodAngle());
        //Logger.recordOutput("Ferry Shot Rotation", ferryShot.getRotation());
        //Logger.recordOutput("Ferry Shot Flywheel Speed", ferryShot.getflywheelSpeed());
        //hood angle, rotation, flywheel
        
   }
}
