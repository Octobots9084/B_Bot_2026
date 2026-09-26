package frc.robot.Subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.RPM;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
    public static ShooterSubsystem inst = new ShooterSubsystem();
    ShooterCalculator hubShot;
    ShooterCalculator ferryShot;
    @Override
    public void periodic() {
        handleStateTransitions();
        applyStates();
        logging();
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
                hubShot = ShooterCalculator.getInstance().calculateShot(0, 0, 0, 0);
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
    
    public void logging() {

    }

    public ShooterSubsystem() {};

    public static ShooterSubsystem getInstance() {
        return inst;
    }
}
