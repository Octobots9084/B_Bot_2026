package frc.robot.Subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.RPM;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.Shooter.Feeder.FeederStates;
import frc.robot.Subsystems.Shooter.Feeder.FeederSubsystem;
import frc.robot.Subsystems.Shooter.Flywheel.FlywheelStates;
import frc.robot.Subsystems.Shooter.Flywheel.FlywheelSubsystem;
import frc.robot.Subsystems.Shooter.Hood.HoodStates;
import frc.robot.Subsystems.Shooter.Hood.HoodSubsystem;
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
                //if(swerve.isInAllianceZone()){
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
            default:
            break;
            }
        }

    public void applyStates () {
        switch(currentShooterState){
            case SAFE:
            HoodSubsystem.getInstanceHood().setAngleWithTolerance(HoodStates.SAFE.enumAngle, HoodSubsystem.hoodTolerance);
            FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(FlywheelStates.SAFE.enumVelocity));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.SAFE.enumVelocity));
            break;
            case HUB:
            hubShot = ShooterCalculator.getInstance().calculateShot(0, 0, 0, 0);
            FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(hubShot.getflywheelSpeed()));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.FIRE.enumVelocity));
            HoodSubsystem.getInstanceHood().setAngleWithTolerance(Degrees.of(hubShot.getHoodAngle()), HoodSubsystem.hoodTolerance);
            break;     
            case FERRY:
            ferryShot = ShooterCalculator.getInstance().calculateShot(0, 0, 0, 0);
            FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(ferryShot.getflywheelSpeed()));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.FIRE.enumVelocity));
            HoodSubsystem.getInstanceHood().setAngleWithTolerance(Degrees.of(ferryShot.getHoodAngle()), HoodSubsystem.hoodTolerance);
            break;
            case TRENCH:
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.SAFE.enumVelocity));
            FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(FlywheelStates.SAFE.enumVelocity));
            HoodSubsystem.getInstanceHood().setAngleWithTolerance(HoodStates.SAFE.enumAngle, HoodSubsystem.hoodTolerance);
            break;  
            case ZEROING:
            //TODO zeroing stuff
            break;    
            case FIXEDFIRE:
            FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(FlywheelStates.FIXEDFIRE.enumVelocity));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.FIRE.enumVelocity));
            HoodSubsystem.getInstanceHood().setAngleWithTolerance(HoodStates.FIXEDFIRE.enumAngle, HoodSubsystem.hoodTolerance);
            break;
            default:
            FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(FlywheelStates.SAFE.enumVelocity));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.SAFE.enumVelocity));
            HoodSubsystem.getInstanceHood().setAngleWithTolerance(HoodStates.SAFE.enumAngle, HoodSubsystem.hoodTolerance);
            break;
            }
    }
    
    public void logging() {

    }

    public ShooterSubsystem() {};

    public static ShooterSubsystem getInstance() {
        return inst;
    }
}
