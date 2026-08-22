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
            FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(FlywheelStates.SAFE.enumVelocity));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.SAFE.enumVelocity));
            break;
            case HUB:
            //FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(visionStuff));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.FIRE.enumVelocity));
            //HoodSubsystem.getInstanceHood().setAngleWithTolerance(Degrees.of(visionStuff), HoodSubsystem.HoodTolerance);
            break;     
            case FERRY:
            //FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(visionStuff));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.FIRE.enumVelocity));
            //HoodSubsystem.getInstanceHood().setAngleWithTolerance(Degrees.of(visionStuff), HoodSubsystem.HoodTolerance);
            break;
            case TRENCH:
            HoodSubsystem.getInstanceHood().setAngleWithTolerance(HoodStates.SAFE.enumAngle, HoodSubsystem.hoodTolerance);
            break;  
            case ZEROING:
            //TODO zeroing stuff
            break;    
            case FIXEDFIRE:
            FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(FlywheelStates.FIXEDFIRE.enumVelocity));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.FIRE.enumVelocity));
            break;
            default:
            FlywheelSubsystem.getInstance().setFlywheelVelocitySetpoint(RPM.of(FlywheelStates.SAFE.enumVelocity));
            FeederSubsystem.getInstance().setFeederVelocitySetpoint(RPM.of(FeederStates.SAFE.enumVelocity));
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
