package frc.robot.Subsystems.RollerFloor;

import static edu.wpi.first.units.Units.Centimeter;
import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.RPM;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.RollerFloor.RollerFloorStates;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;

public class RollerFloorSubsystem extends SubsystemBase {
    private Distance RollerDiameter = Centimeters.of(0);//TODO replace
    public RollerFloorStates state = RollerFloorStates.SAFE;
    public RollerFloorStates wantedRollerState = RollerFloorStates.SAFE;
    public RollerFloorStates currentRollerState = RollerFloorStates.SAFE;
    public RollerFloorTalonFX TX = new RollerFloorTalonFX();
    public static RollerFloorSubsystem instance;

    @Override
    public void periodic() {
        RollerFlywheel.updateTelemetry();
        setFeederVelocitySetpoint(RPM.of(currentRollerState.enumRollerVelocity));
        handleStateTransitions();
        applyStates();
        logging();
    }
    public static RollerFloorSubsystem getInstance(){
        return instance;
    }
    private void handleStateTransitions() {
 switch (wantedRollerState) {
            case SAFE:
                currentRollerState = RollerFloorStates.SAFE;
            break;
            case PRELOAD:
                currentRollerState = RollerFloorStates.PRELOAD;
            break;
            case SHOOT:
              //  if(shootable){
                    currentRollerState = RollerFloorStates.SHOOT;
               // }
            break;
            case REVERSE:
                currentRollerState = RollerFloorStates.REVERSE;
            break;
            default:
                 currentRollerState = RollerFloorStates.SAFE;
            break;
           
        }
    }

    private void applyStates () {
        
    }
    
    private void logging() {

    }
     private final FlyWheelConfig RollerFloorConfig = new FlyWheelConfig()
  .withDiameter(RollerDiameter)
  .withTelemetry("rollerFloorMech", TelemetryVerbosity.HIGH);
   public FlyWheel RollerFlywheel = new FlyWheel(RollerFloorConfig, TX.FloorMotor);

     public AngularVelocity getFeederVelocity() {
        return RollerFlywheel.getSpeed();
     }

     public Command FeederRun(AngularVelocity speed){
        return RollerFlywheel.run(speed);
     }

     public void setFeederVelocitySetpoint(AngularVelocity speed){
        RollerFlywheel.setMechanismVelocitySetpoint(speed);
     }

}
