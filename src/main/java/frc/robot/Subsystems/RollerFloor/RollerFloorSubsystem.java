package frc.robot.Subsystems.RollerFloor;

import static edu.wpi.first.units.Units.Centimeter;
import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.RPM;

import java.util.Optional;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.RollerFloor.RollerFloorStates;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;

public class RollerFloorSubsystem extends SubsystemBase {
    private Distance RollerDiameter = Centimeters.of(20);//TODO replace all of these values
    public RollerFloorStates state = RollerFloorStates.SAFE;
    public RollerFloorStates wantedRollerState = RollerFloorStates.SAFE;
    public RollerFloorStates currentRollerState = RollerFloorStates.SAFE;
    public RollerFloorTalonFX TX;
    public static RollerFloorSubsystem instance;
    private FlyWheel RollerFlywheel;
    private final FlyWheelConfig RollerFloorConfig;
    public FlyWheel floorFlyWheel;

    public RollerFloorSubsystem() {
        instance = this;

        TX = new RollerFloorTalonFX();
        TX.init();

        RollerFloorConfig = new FlyWheelConfig()
        .withDiameter(RollerDiameter)
        .withTelemetry("rollerFloorMech", TelemetryVerbosity.HIGH);
        floorFlyWheel = new FlyWheel(RollerFloorConfig, TX.FloorMotor);
    }
      @Override
    public void periodic() {
        RollerFlywheel.updateTelemetry();
        handleStateTransitions();
        applyStates();
        logging();
    }
    public void simulationPeriodic() {
        RollerFlywheel.updateTelemetry();
        RollerFlywheel.simIterate();
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
              //  if(shootable){ TODO
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
        setRollorFloorVelocitySetpoint(RPM.of(currentRollerState.enumRollerVelocity));
    }
    
    private void logging() {

    }



  

    public FlyWheel getRollerFlywheel() {
        if (RollerFlywheel == null) RollerFlywheel = new FlyWheel(RollerFloorConfig, TX.FloorMotor);
        return RollerFlywheel;
    }

     public AngularVelocity getRollerVelocity() {
        return RollerFlywheel.getSpeed();
     }

     public Command Run(AngularVelocity speed){
        return RollerFlywheel.run(speed);
     }

     public void setRollorFloorVelocitySetpoint(AngularVelocity speed){
        RollerFlywheel.setMechanismVelocitySetpoint(speed);
     }

}
